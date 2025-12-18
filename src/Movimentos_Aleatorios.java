import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
public class Movimentos_Aleatorios implements Runnable {

    private Dados dados;
    private Random random;
    private Estado estado;
    private boolean acaba;
    private My_Robot myrobo;
    private ArrayList<Comando> cmdList;
    private GestorDeAcesso gestor;
    public enum Estado {
        Pause,
        Run,
        Fim
    }

    public Movimentos_Aleatorios(Dados dados, My_Robot myrobo) {
        this.dados = dados;
        this.random = new Random();
        this.estado = Estado.Pause;
        this.myrobo = myrobo;
        this.acaba = false;
        this.cmdList = new ArrayList<Comando>();
        this.gestor = new GestorDeAcesso(1);
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    @Override
    public void run() {
        System.out.println("Thread iniciada: " + Thread.currentThread().getName() + " | Estado inicial: " + estado);

        while (!acaba) {
            switch (estado) {

                case Run:
					try {
						Executar(dados.getRandomMoves());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					while(!myrobo.bufferMovAleatorio.isVazio())
					{
						try {
							Thread.sleep(50);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
                    estado = Estado.Pause;
                    break;
                case Pause:
                    System.out.println(Thread.currentThread().getName() + " - PAUSE");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case Fim:
                    System.out.println(Thread.currentThread().getName() + " - FIM");
                    acaba = true;
                    break;
            }
        }

        System.out.println("Thread terminada: " + Thread.currentThread().getName());
    }

    public void Executar(int n) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " - A executar " + n + " movimentos aleatórios");
        int i = 0;
        cmdList.clear();
        while (i < n) {
            int comando = random.nextInt(3); // 0=Reta, 1=Curva Esquerda, 2=Curva Direita 3 = Parar

            int tempo = 0;
            int r = 0;
            int a = 0;
            int d = 0;
            
            Comando cmd = new Comando(Comando.Tipo.PARAR);
            switch (comando) {
                case 0:
                	d = dados.getRandomDistancia();
                    cmd = new Comando(Comando.Tipo.RETA, d);
                    tempo = (d / 20) * 1000 + 100;
                    break;
                case 1:
                	r = dados.getRandomRaio();
                    a = dados.getRandomAngulo();
                    cmd = new Comando(Comando.Tipo.CE, r, a);
                    tempo = (int) (((r * Math.PI * a) / (180.0 * 20.0)) * 1000 + 100);
                    break;
                case 2:
                	r = dados.getRandomRaio();
                    a = dados.getRandomAngulo();
                    cmd = new Comando(Comando.Tipo.CD, r, a);
                    tempo = (int) (((r * Math.PI * a) / (180.0 * 20.0)) * 1000 + 100);
                    break;
            }
            cmdList.add(cmd);
            //System.out.println("Comando adicionado à lista");
            cmdList.add(new Comando(Comando.Tipo.ESPERAR, tempo));
            cmdList.add(new Comando(Comando.Tipo.PARAR));
            cmdList.add(new Comando(Comando.Tipo.ESPERAR, 100));
            i++;
        }
        //cmdList.add(new Comando(Comando.Tipo.ESPERAR, 3000));
        myrobo.ExecutarMovimentosAleatorios(cmdList);
    }
}
