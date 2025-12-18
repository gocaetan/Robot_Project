

import java.util.concurrent.Semaphore;

public class Tampao {
    private final int CAPACIDADE = 16;
    private final Object[] buffer = new Object[CAPACIDADE];
    private int idxPut = 0;
    private int idxGet = 0;
    private int numElementos = 0; // Para ser usado em isCheio() e isVazio()
    
    // Semáforos 
    
    // SM: Semáforo Mutex (Inicializado a 1). Protege o acesso ao buffer e aos contadores.
    private final Semaphore SM = new Semaphore(1); 
    
    // sP: Semáforo Produtor (Vazio)
    // Usado aqui como um semáforo de condição para o Produtor
    private final Semaphore sP = new Semaphore(0); 
    
    // sG: Semáforo Consumidor (Vazio)
    // Usado aqui como um semáforo de condição para o Consumidor
    private final Semaphore sG = new Semaphore(0); 
    
    // numEspP: Número de Produtores em espera
    private int numEspP = 0; 
    
    // numEspG: Número de Consumidores em espera
    private int numEspG = 0;

    // --- Métodos Auxiliares ---
    private boolean isCheio() {
        return numElementos == CAPACIDADE;
    }

    public boolean isVazio() {
        return numElementos == 0;
    }
    
    private void insert(Object o) {
        buffer[idxPut] = o;
        idxPut = (idxPut + 1) % CAPACIDADE;
        numElementos++;
    }

    private Object remove() {
        Object o = buffer[idxGet];
        buffer[idxGet] = null;
        idxGet = (idxGet + 1) % CAPACIDADE;
        numElementos--;
        return o;
    }
    
    // --- Método PUT (Produtor)  ---
    public void put(Object o) throws InterruptedException {
        SM.acquire(); // [Linha 1] Entra na região crítica
        
        while (isCheio()) { // [Linha 2] Enquanto o buffer estiver CHEIO...
            
            numEspP++; // [Linha 3] Incrementa Produtores em Espera
            SM.release(); // [Linha 4] Libera o Mutex (para permitir que o Consumidor remova)
            
            sP.acquire(); // [Linha 5] Bloqueia a thread no Semáforo de Espera do Produtor
            
            SM.acquire(); // [Linha 6] Readquire o Mutex ao ser despertado
            numEspP--; // [Linha 6] Decrementa Produtores em Espera
        }
        
        // [Linha 7] Inserção do Elemento
        insert(o);
        
        // [Linha 8] Se houver Consumidor(es) em espera (numEspG > 0), acorda um
        if (numEspG > 0) { 
            sG.release(); 
        }
        
        SM.release(); // [Linha 9] Libera a região crítica
    }

    // --- Método GET (Consumidor)  ---
    public Object get() throws InterruptedException {
        SM.acquire(); // [Linha 1] Entra na região crítica
        
        while (isVazio()) { // [Linha 2] Enquanto o buffer estiver VAZIO...
            
            numEspG++; // [Linha 3] Incrementa Consumidores em Espera
            SM.release(); // [Linha 4] Libera o Mutex (para permitir que o Produtor insira)
            
            sG.acquire(); // [Linha 5] Bloqueia a thread no Semáforo de Espera do Consumidor
            
            SM.acquire(); // [Linha 6] Readquire o Mutex ao ser despertado
            numEspG--; // [Linha 6] Decrementa Consumidores em Espera
        }
        
        // [Linha 7] Remoção do Elemento
        Object o = remove();
        
        // [Linha 8] Se houver Produtor(es) em espera (numEspP > 0), acorda um
        if (numEspP > 0) { 
            sP.release(); 
        }
        
        SM.release(); // [Linha 9] Libera a região crítica
        
        return o; // [Linha 10] Retorna o objeto
    }
}
