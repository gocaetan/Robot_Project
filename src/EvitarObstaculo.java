import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvitarObstaculo implements Runnable {
	public interface ConsolaGUI { 
	    void imprimirNaConsola(String mensagem);
	}
    private Dados dados; 
    private My_Robot myRobot; 
    private Random random;
    private boolean running = true;
    private boolean ativo = true;
    //private LerSensorToque sensor;
    private Estado estado;
    List<Comando> cmds;
    private volatile boolean debugToque = false;
    private boolean debugAtivo = false;
    public enum Estado {
        Pause,
        Run,
        Fim
    }
    public EvitarObstaculo(Dados dados, My_Robot myRobot) {
        this.dados = dados;
        this.myRobot = myRobot;
        this.random = new Random();
        //this.sensor = new LerSensorToque(myRobot, 0);
        this.estado = Estado.Pause;
        this.cmds = new ArrayList<>();
    }
    public void ativarDebugSensor(boolean ativo) {
        this.debugAtivo = ativo;
    }

    public void simularToque() {
        if (debugAtivo) {
            debugToque = true;
        }
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public void stopRunning() {
        this.running = false;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    private boolean evitando = false;
    private boolean toqueDetectado() throws InterruptedException {

    	if (debugAtivo)
    	{
    		if(debugToque) {
            debugToque = false; // reset após detetar
            return true;
    		}
    		return false;
    	}
    	
        Comando ler = new Comando(Comando.Tipo.LER_SENSOR, 0);
        myRobot.enviarSensorPrioritario(ler);

        // espera até o robot executar
        while (ler.resultado == null) {
            Thread.sleep(5);
        }

        return ler.resultado == 1;
    }
    
    @Override
    public void run() {
        //System.out.println("Thread iniciada: " + Thread.currentThread().getName() + " | Estado inicial: " + estado);
        while(running)
        {
            switch (estado) {
                case Run:
                    try {
                        Evitarobstaculo();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case Pause:
                    //System.out.println(Thread.currentThread().getName() + " - PAUSE");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case Fim:
                    System.out.println(Thread.currentThread().getName() + " - FIM");
                    break;
            }
        }

        //System.out.println("Thread terminada: " + Thread.currentThread().getName());
    }
    
    
    private void Evitarobstaculo() throws InterruptedException {
        System.out.println("Thread EvitarObstaculo iniciada.");
        while (running) {
        	if (ativo && toqueDetectado() && !evitando) { 
                System.out.println("--- OBSTÁCULO DETETADO! ---");
                evitando = true;
                try {
                	//myRobot.getGestor().pedirAcesso("Exclusao Sensor");  
                	
                	cmds.clear();
                    cmds.add(new Comando(Comando.Tipo.PARAR));
                    cmds.add(new Comando(Comando.Tipo.ESPERAR, 100));

                    cmds.add(new Comando(Comando.Tipo.RETA, -20));
                    cmds.add(new Comando(Comando.Tipo.ESPERAR, 1500)); 
                    cmds.add(new Comando(Comando.Tipo.PARAR));
                    cmds.add(new Comando(Comando.Tipo.ESPERAR, 100));
                    int raio = dados.getRandomRaio();
                    int angulo = 90;
                    
                    Comando.Tipo tipoCurva;
                    if (random.nextBoolean()) {
                        tipoCurva = Comando.Tipo.CE;
                        System.out.println("Manobra: Curva à Esquerda");
                    } else {
                        tipoCurva = Comando.Tipo.CD;
                        System.out.println("Manobra: Curva à Direita");
                    }
                    cmds.add(new Comando(tipoCurva, raio, angulo));
                    cmds.add(new Comando(Comando.Tipo.ESPERAR, 2000));

                    cmds.add(new Comando(Comando.Tipo.PARAR));
                    
                    myRobot.evitarObstaculo(cmds);
                    Thread.sleep(100);
                    evitando = false;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); 
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
        System.out.println("Thread EvitarObstaculo terminada.");
    }
}
