package GUI.Servidor;

import GUI.Cliente.Controlador;
import GUI.Cliente.PanelCliente;

import javax.swing.*;

public class Servidor {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("Ajedrez");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " - "
                        + SwingUtilities.isEventDispatchThread());
                crearGUI(frame);
            }
        });
    }

    private static void crearGUI(JFrame frame){
        PanelServer panel = new PanelServer();
       /* Controlador controlador = new Controlador(panel);
        panel.controlador(controlador);*/

        //frame.setSize(1400, 1400);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
