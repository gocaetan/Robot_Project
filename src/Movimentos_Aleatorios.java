import java.util.Random;
public class Movimentos_Aleatorios {
	private Dados dados;
	private Random random;
	public Movimentos_Aleatorios(Dados dados)
	{
		this.dados = dados;
		this.random = new Random();
	}
	public void Executar(int n)
	{
		int i = 0;
		while(i < n)
		{
			int comando = random.nextInt(3);
			switch(comando)
			{
				case 0:
					dados.getRobo().Reta(dados.getDistancia());
				case 1:
					dados.getRobo().CurvarEsquerda(dados.getRaio(), dados.getAngulo());
				case 2:
					dados.getRobo().CurvarDireita(dados.getRaio(), dados.getAngulo());
				//falta gerar as distancias os raios e os angulos aleatorios
			}
			i++;
		}
	}
}