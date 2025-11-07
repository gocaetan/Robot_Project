import java.util.concurrent.Semaphore;

public class GestorDeAcesso {
    private final Semaphore semaforo;
    
    public GestorDeAcesso(int permissoes) {
        this.semaforo = new Semaphore(permissoes);
    }
    public void pedirAcesso(String nomeThread) {
        try {
            //System.out.println(nomeThread + " tentando adquirir acesso...");
            semaforo.acquire();
            //System.out.println(nomeThread + " obteve acesso!");
        } catch (InterruptedException e) {
            //System.err.println(nomeThread + " foi interrompida ao tentar adquirir acesso.");
            Thread.currentThread().interrupt();
        }
    }

    public void libertarAcesso(String nomeThread) {
        semaforo.release();
        System.out.println(nomeThread + " libertou o acesso!");
    }
}
