import java.util.concurrent.Semaphore;

public class GestorDeAcesso {
    public static void main(String[] args) {
        
        RobotLegoEV3 robo1 = new RobotLegoEV3();
        RobotLegoEV3 robo2 = new RobotLegoEV3();

       
        Dados dados1 = new Dados(robo1);
        Dados dados2 = new Dados(robo2);

       
        Semaphore semaforo = new Semaphore(1);

        
        Movimentos_Aleatorios mov1 = new Movimentos_Aleatorios(dados1, semaforo);
        Movimentos_Aleatorios mov2 = new Movimentos_Aleatorios(dados2, semaforo);

        
        Thread t1 = new Thread(mov1, "Robô 1");
        Thread t2 = new Thread(mov2, "Robô 2");

        t1.start();
        t2.start();

       
        mov1.setEstado(Movimentos_Aleatorios.Estado.Run);
        mov2.setEstado(Movimentos_Aleatorios.Estado.Run);
    }
}
