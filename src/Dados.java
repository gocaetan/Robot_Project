
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
	
	public void ligar()
	{
		robot.OpenEV3(robotName);
	}
}
