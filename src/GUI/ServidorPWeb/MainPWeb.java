package GUI.ServidorPWeb;

import PWeb.ServerPWeb;

import javax.swing.*;

public class MainPWeb {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Servidor http");
        GUIPWeb guiServidor = new GUIPWeb();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - "
                        + SwingUtilities.isEventDispatchThread());
                crearGUI(frame, guiServidor.getPanel());
            }
        });
        ServerPWeb serverNetManager = new ServerPWeb(80, guiServidor);
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
