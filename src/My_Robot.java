import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class My_Robot implements Runnable{
	public Tampao bufferPrioritario;
	public Tampao bufferManual;
	public Tampao bufferMovAleatorio;
	private boolean running;
	private Dados dados;
	private GestorDeAcesso gestor;
	private Semaphore semaforo;
	private ArrayList<Comando> cmdLst;
	private RobotLegoEV3 robot;
	private Gravador gravador;
	public My_Robot(Dados dados)
	{
		this.bufferPrioritario = new Tampao();
		this.bufferManual = new Tampao();
		this.bufferMovAleatorio = new Tampao();
		this.gestor = new GestorDeAcesso(1);
		this.running = true;
		this.dados = dados;
		this.semaforo = new Semaphore(1);
		this.cmdLst = new ArrayList<Comando>();
		this.robot = dados.getRobo();
	}
	
	public void setGravador(Gravador g) {
	    this.gravador = g;
	}
	
	public Gravador getGravador() {
	    return gravador;
	}
	public GestorDeAcesso getGestor()
	{
		return gestor;
	}
	
	public void StopRobot()
	{
		running = false;
	}
	public void evitarObstaculo(List<Comando> comandos) throws InterruptedException {
	    try {
	        gestor.pedirAcesso("Buffer prioritario");
	        for(Comando c : comandos){
	            bufferPrioritario.put(c);
	        }
	    } finally {
	        gestor.libertarAcesso("Buffer prioritario");
	    }
	}
	
	public void enviarSensorPrioritario(Comando cmd) throws InterruptedException {
	    try {
	        gestor.pedirAcesso("Buffer prioritario");
	        bufferPrioritario.put(cmd);
	    } finally {
	        gestor.libertarAcesso("Buffer prioritario");
	    }
	}
	public void enviarComando(Comando cmd) throws InterruptedException {
		try {
		    gestor.pedirAcesso("Movimentos manuais");
		    bufferManual.put(cmd);
		} finally {
		    gestor.libertarAcesso("Movimentos manuais");
		}
	}
	 
    public void Reta(int distancia, Dados dados) {
        System.out.println("Movendo em linha reta por " + distancia + " cm");
        robot.Reta(distancia);
    }

    public void CurvarEsquerda(int raio, int angulo, Dados dados) {
        System.out.println("↰ Curvando à esquerda (raio=" + raio + ", ângulo=" + angulo + ")");
        robot.CurvarEsquerda(raio, angulo);
    }

    public void CurvarDireita(int raio, int angulo, Dados dados) {
        System.out.println("↱ Curvando à direita (raio=" + raio + ", ângulo=" + angulo + ")");
        robot.CurvarDireita(raio, angulo);
    }

    public void Parar(Dados dados) {
        System.out.println("⛔ Parar movimento");
        robot.Parar(true);;
    }
    
    public int lerSensor(int id)
    {
    	return(robot.SensorToque(id));
    }
    
    public void ExecutarMovimentosAleatorios(List<Comando> comandos)
    {
        for (Comando c : comandos)
        {
            try {
                gestor.pedirAcesso("Movimentos Aleatórios");
                bufferMovAleatorio.put(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                gestor.libertarAcesso("Movimentos Aleatórios");
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
        }
    }
    public void run()
    {
    	while(running)
    	{
    		if(!bufferPrioritario.isVazio())
    		{
    			try {
					Executar((Comando)bufferPrioritario.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			continue;
    		}
    		if(!bufferMovAleatorio.isVazio())
    		{
    			try {
					Executar((Comando)bufferMovAleatorio.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			continue;
    		}
    		if(!bufferManual.isVazio())
    		{
    			try {
					Executar((Comando)bufferManual.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			continue;
    		}
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	System.out.println("Thread MyRobot Terminada");
    }
    
    private void Executar(Comando c)
    {
    	
    	if (gravador != null && gravador.estaAtivo()) {
            gravador.gravar(c);
        }
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
	    		case LER_SENSOR:
	    			int valor = robot.SensorToque(c.valor1);
	    			c.resultado = valor;
	    			break;
	    		case ESPERAR:
	    			//System.out.println("A esperar por" + c.valor1);
					try {
						Thread.sleep(c.valor1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	}
	    	//gestor.libertarAcesso("Executar Comandos");
    }
}
