package GUI.Cliente;

import javax.swing.*;

public class ClienteModificado {
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
        PanelCliente panelCliente = new PanelCliente();
        Controlador controlador = new Controlador(panelCliente);
        panelCliente.controlador(controlador);
        BaseGUI baseGUI = new BaseGUI(panelCliente);

        //frame.setSize(700, 700);
        frame.setLocation(200, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(baseGUI.getPanel1());
        frame.pack();
        frame.setVisible(true);
    }
}
