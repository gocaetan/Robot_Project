/*
import java.util.Random;
public class Movimentos_Aleatorios implements Runnable{
	private Dados dados;
	private Random random;
	private Estado estado;
	public enum Estado
	{
		Pause,
		Run, 
		Fim;
	}
	private boolean acaba;
	public Movimentos_Aleatorios(Dados dados)
	{
		this.dados = dados;
		this.random = new Random();
		this.estado = Estado.Pause;
		this.acaba = false;
	}
	
	public void setEstado(Estado estado)
	{
		this.estado = estado;
	}
	
	public void run()
	{
		System.out.println("Thread iniciada. Estado inicial: " + estado);
		while(!acaba)
		{
			switch(estado)
			{
				case Run:
					System.out.println("RUN");
					Executar(dados.getRandomMoves());
					estado = Estado.Pause;
					break;
				case Pause:
					System.out.println("Pause");
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				case Fim:
					System.out.println("Fim");
					dados.getRobo().Parar(true);
					acaba = true;
					break;
			}
		}
	}
	
	public void Executar(int n)
	{
		System.out.println("A executar");
		int i = 0;
		System.out.println(n);
		while(i < n && !acaba)
		{
			int comando = random.nextInt(3);
			switch(comando)
			{
				case 0:
					System.out.println("Reta");
					dados.getRobo().Reta(dados.getRandomDistancia());
					break;
				case 1:
					System.out.println("Curva esquerda");
					dados.getRobo().CurvarEsquerda(dados.getRandomRaio(), dados.getRandomAngulo());
					break;
				case 2:
					System.out.println("Curva direita");
					dados.getRobo().CurvarDireita(dados.getRandomRaio(), dados.getRandomAngulo());
					break;
			}
			try {
				Thread.sleep(1000); // Espera 1 segundo entre movimentos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			i++;
		}
		System.out.println("Execução terminada — a parar o robô.");
		dados.getRobo().Parar(true);
	}
}
*/













































import java.util.Random;
import java.util.concurrent.Semaphore;

public class Movimentos_Aleatorios implements Runnable {

    private Dados dados;
    private Random random;
    private Estado estado;
    private boolean acaba;
    private Semaphore semaforo;

    public enum Estado {
        Pause,
        Run,
        Fim
    }

    public Movimentos_Aleatorios(Dados dados, Semaphore semaforo) {
        this.dados = dados;
        this.random = new Random();
        this.estado = Estado.Pause;
        this.acaba = false;
        this.semaforo = semaforo;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public void run() {
        System.out.println("Thread iniciada: " + Thread.currentThread().getName() +
                           " | Estado inicial: " + estado);

        while (!acaba) {
            switch (estado) {

                case Run:
                    System.out.println(Thread.currentThread().getName() + " - RUN");
                    try {
                    	System.out.println(Thread.currentThread().getName() + " tentando adquirir semáforo...");
                        semaforo.acquire(); // garante acesso exclusivo ao robô
                        Executar(dados.getRandomMoves());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                    	System.out.println(Thread.currentThread().getName() + " - Semáforo liberado!");
                        semaforo.release(); // libera o acesso
                    }

                    estado = Estado.Pause;
                    break;

                case Pause:
                    System.out.println(Thread.currentThread().getName() + " - PAUSE");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case Fim:
                    System.out.println(Thread.currentThread().getName() + " - FIM");
                    dados.getRobo().Parar(true);
                    acaba = true;
                    break;
            }
        }

        System.out.println("Thread terminada: " + Thread.currentThread().getName());
    }

    public void Executar(int n) {
        System.out.println(Thread.currentThread().getName() + " - A executar " + n + " movimentos aleatórios");
        int i = 0;

        while (i < n && !acaba) {
            int comando = random.nextInt(3); // 0=Reta, 1=Curva Esquerda, 2=Curva Direita

            switch (comando) {
                case 0:
                    System.out.println("→ Reta");
                    dados.getRobo().Reta(dados.getRandomDistancia());
                    break;
                case 1:
                    System.out.println("↰ Curva Esquerda");
                    dados.getRobo().CurvarEsquerda(dados.getRandomRaio(), dados.getRandomAngulo());
                    break;
                case 2:
                    System.out.println("↱ Curva Direita");
                    dados.getRobo().CurvarDireita(dados.getRandomRaio(), dados.getRandomAngulo());
                    break;
            }

            try {
                Thread.sleep(1000); // espera 1 segundo entre movimentos
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i++;
        }

        System.out.println("Execução terminada — a parar o robô.");
        dados.getRobo().Parar(true);
    }
}
