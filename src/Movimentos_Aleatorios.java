
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Movimentos_Aleatorios implements Runnable {

    private Dados dados;
    private Random random;
    private Estado estado;
    private boolean acaba;
    private Semaphore semaforo;
    private GestorDeAcesso gestor;
    private Tampao buffer;
    
    public enum Estado {
        Pause,
        Run,
        Fim
    }

    public Movimentos_Aleatorios(Dados dados, Semaphore semaforo, GestorDeAcesso gestor, Tampao buffer) {
        this.dados = dados;
        this.random = new Random();
        this.estado = Estado.Pause;
        this.acaba = false;
        this.semaforo = semaforo;
        this.gestor = gestor;
        this.buffer = buffer;
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
                    System.out.println(Thread.currentThread().getName() + " - RUN");
                    gestor.pedirAcesso(Thread.currentThread().getName());
                    try {
                    	Executar(dados.getRandomMoves());
					} catch (InterruptedException e) {
					e.printStackTrace();
					}
                    gestor.libertarAcesso(Thread.currentThread().getName());
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
        dados.getRobo().Parar(false);
        while (i < n && !acaba) {
            int comando = random.nextInt(4); // 0=Reta, 1=Curva Esquerda, 2=Curva Direita 3 = Parar

            int tempo = 0;
            int r = dados.getRandomRaio();
            int a = dados.getRandomAngulo();
            int d = dados.getRandomDistancia();
            
            Comando cmd;
            switch (comando) {
                case 0:
                    cmd = new Comando(Comando.Tipo.RETA, d);
                    tempo = (d / 20) * 1000 + 100;
                    break;
                case 1:
                    cmd = new Comando(Comando.Tipo.CE, r, a);
                    tempo = (int) (((r * Math.PI * a) / (180.0 * 20.0)) * 1000 + 100);
                    break;
                case 2:
                    cmd = new Comando(Comando.Tipo.CD, r, a);
                    tempo = (int) (((r * Math.PI * a) / (180.0 * 20.0)) * 1000 + 100);
                    break;
                default:
                	cmd = new Comando(Comando.Tipo.PARAR);
                	tempo = 100;
                	break;
            }
            buffer.put(cmd);
            System.out.println("Comando aleatório colocado no buffer " + cmd.tipo);
            Thread.sleep(tempo);
            System.out.println("Tempo de execução " + tempo);
            i++;
        }

        System.out.println("Execução terminada — a parar o robô.");
        buffer.put(new Comando(Comando.Tipo.PARAR));
    }
}
