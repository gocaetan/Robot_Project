
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
