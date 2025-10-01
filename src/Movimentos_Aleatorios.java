
import java.util.Random;
enum Estado{
	Pause, Run, Fim
}
public class Movimentos_Aleatorios{
	private Dados dados;
	private Random random;
	private Estado estado;
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
				Executar(dados.getRandomMoves());
			case Pause:
				break;
			case Fim:
				acaba = true;
		}
	}
	
	public void Executar(int n)
	{
		int i = 0;
		while(i < n && !acaba)
		{
			int comando = random.nextInt(3);
			switch(comando)
			{
				case 0:
					dados.getRobo().Reta(dados.getDistancia());
					break;
				case 1:
					dados.getRobo().CurvarEsquerda(dados.getRaio(), dados.getAngulo());
					break;
				case 2:
					dados.getRobo().CurvarDireita(dados.getRaio(), dados.getAngulo());
					break;
				//falta gerar as distancias os raios e os angulos aleatorios
			}
			i++;
		}
			
	}
}
