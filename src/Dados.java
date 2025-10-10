import java.util.Random;
public class Dados {
	private int raio;
	private int angulo;
	private int distancia;
	private String robotName;
	private int RandomMoves;
	private RobotLegoEV3 robot;
	private Random random;
	
	public Dados(RobotLegoEV3 robot)
	{
		this.robot = robot;
	}
	
	public RobotLegoEV3 getRobo()
	{
		return robot;
	}
	
	public String getRoboName()
	{
		return robotName;
	}
	
	public int getDistancia()
	{
		return distancia;
	}
	
	public int getAngulo()
	{
		return angulo;
	}
	
	public int getRaio()
	{
		return raio;
	}
	
	public void setRaio(int raio)
	{
		this.raio = raio;
	}
	
	public void setAngulo(int angulo)
	{
		this.angulo = angulo;
	}
	
	public void setDistancia(int distancia)
	{
		this.distancia = distancia;
	}
	
	public void setRobotName(String robotName)
	{
		this.robotName = robotName;
	}
	
	public int getRandomMoves()
	{
		return RandomMoves;
	}
	
	public int getRandomDistancia()
	{
		return 10 + random.nextInt(41);	
	}
	
	public int getRandomRaio()
	{
		return 10 + random.nextInt(21);
	}
	
	public int getRandomAngulo()
	{
		return 20 + random.nextInt(81);
	}
	
	public void setRandomMoves(int RandomMoves)
	{
		this.RandomMoves = RandomMoves;
	}
}
