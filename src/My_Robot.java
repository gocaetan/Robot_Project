

public class My_Robot implements Runnable{
	private Tampao buffer;
	private boolean running;
	private Dados dados;
	
	public My_Robot(Tampao buffer, Dados dados)
	{
		this.buffer = buffer;
		this.running = true;
		this.dados = dados;
	}
	
	public void StopRobot()
	{
		running = false;
	}
    public void Reta(int distancia, Dados dados) {
        System.out.println("Movendo em linha reta por " + distancia + " cm");
        dados.getRobo().Reta(distancia);
    }

    public void CurvarEsquerda(int raio, int angulo, Dados dados) {
        System.out.println("↰ Curvando à esquerda (raio=" + raio + ", ângulo=" + angulo + ")");
        dados.getRobo().CurvarEsquerda(raio, angulo);
    }

    public void CurvarDireita(int raio, int angulo, Dados dados) {
        System.out.println("↱ Curvando à direita (raio=" + raio + ", ângulo=" + angulo + ")");
        dados.getRobo().CurvarEsquerda(raio, angulo);
    }

    public void Parar(Dados dados) {
        System.out.println("⛔ Parar movimento");
        dados.getRobo().Parar(true);;
    }
    
    public void run()
    {
    	while(running)
    	{
    		try
    		{
    			Object cmd = buffer.get();
    			
    			if(cmd instanceof Comando)
    			{
    				Executar((Comando)cmd);
    			}
    		} catch (InterruptedException e)
    		{
    			Thread.currentThread().interrupt();
    		}
    	}
    	System.out.println("Thread MyRobot Terminada");
    }
    
    public void Executar(Comando c)
    {
    	switch(c.tipo)
    	{
    		case RETA:
    			Reta(c.valor1, dados);
    			break;
    		case CE:
    			CurvarEsquerda(c.valor1, c.valor2, dados);
    			break;
    		case CD:
    			CurvarDireita(c.valor1, c.valor2, dados);
    			break;
    		case PARAR:
    			Parar(dados);
    			break;
    	}
    }
}
