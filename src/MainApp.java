import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	//SistemaRobot sistema = new SistemaRobot();

        	
            GUIRobot guiRobot = new GUIRobot();
        	guiRobot.setLocation(100, 100);
        	guiRobot.setVisible(true);

        	GUIGravador guiGravador = new GUIGravador();
        	guiGravador.setLocation(950, 100);
        	guiGravador.setVisible(true);
        	guiRobot.setGravador(guiGravador.getGravador());
        });
    }
}
