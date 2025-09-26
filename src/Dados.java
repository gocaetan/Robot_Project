
public class Dados {
	private int raio;
	private int angulo;
	private int distancia;
	private String robotName;
	
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
	
	public void moverFrente()
	{
		robot.Reta(distancia);
	}
	
	public void virarEsquerda()
	{
		robot.CurvarEsquerda(raio, angulo);
	}
	
	public void virarDireita()
	{
		robot.CurvarDireita(raio, angulo);
	}
	
	public void moverTras()
	{
		robot.Reta(-distancia);
	}
	
	public void parar()
	{
		robot.Parar(true);
	}
	
	public void ligar() throws Exception
	{
		if(robotName == null || robotName.isEmpty())
		{
			throw new Exception("Nome do robô invalido");
		}
		robot.OpenEV3(robotName);
	}
	
	public void desligar()
	{
		robot.CloseEV3();
	}
}
