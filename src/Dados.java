
public class Dados {
	private int raio;
	private int angulo;
	private int distancia;
	private String robotName;
	private int RandomMoves;
	private RobotLegoEV3 robot;
	
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
	
	public void setRandomMoves(int RandomMoves)
	{
		this.RandomMoves = RandomMoves;
	}
}
