
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
	}
	
	public void setEstado(Estado estado)
	{
		this.estado = estado;
	}
	
	public void run()
	{
		switch(estado)
		{
			case Run:
				System.out.println("RUN");
				Executar(dados.getRandomMoves());
				break;
			case Pause:
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
				break;
			case Fim:
				dados.getRobo().Parar(true);
				acaba = true;
				break;
		}
	}
	
	public void Executar(int n)
	{
		System.out.println("A executar");
		int i = 0;
		while(i < n && !acaba)
		{
			int comando = random.nextInt(3);
			switch(comando)
			{
				case 0:
					dados.getRobo().Reta(dados.getRandomDistancia());
					break;
				case 1:
					dados.getRobo().CurvarEsquerda(dados.getRandomRaio(), dados.getRandomAngulo());
					break;
				case 2:
					dados.getRobo().CurvarDireita(dados.getRandomRaio(), dados.getRandomAngulo());
					break;
			}
			i++;
		}
	}
}
