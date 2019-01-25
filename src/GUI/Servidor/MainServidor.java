package GUI.Servidor;

import Server.PlayersManager;
import Server.ServerNetManager;

import javax.swing.*;

public class MainServidor {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Servidor");
        GUIServidor guiServidor = new GUIServidor();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - "
                        + SwingUtilities.isEventDispatchThread());
                crearGUI(frame, guiServidor.getPanel1());
            }
        });
        PlayersManager playersManager = new PlayersManager(guiServidor);
        ServerNetManager serverNetManager = new ServerNetManager(9000, guiServidor, playersManager);
        serverNetManager.listen();
    }

    private static void crearGUI(JFrame frame, JPanel panel){
        //frame.setSize(700, 700);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
