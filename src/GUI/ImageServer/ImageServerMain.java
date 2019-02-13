package GUI.ImageServer;

import ImageServer.ImageServerManager;

import javax.swing.*;

public class ImageServerMain {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Servidor de imagenes");
        ImageServerGUI guiServidor = new ImageServerGUI();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - "
                        + SwingUtilities.isEventDispatchThread());
                crearGUI(frame, guiServidor.getPanel());
            }
        });
        ImageServerManager serverNetManager = new ImageServerManager(9001, guiServidor);
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
