package GUI.Cliente;

import Logica.Comandos;

import javax.swing.*;
import java.awt.event.*;

public class RematchDialog extends JDialog {
    private JPanel contentPane;
    private JButton btnAceptar;
    private JButton btnRechazar;
    private JLabel lblInfo;
    private String retador;
    private Controlador controlador;

    public RematchDialog(String retador, Controlador controlador) {
        setContentPane(contentPane);
        setModal(false);
        getRootPane().setDefaultButton(btnAceptar);
        this.retador = retador;
        this.controlador = controlador;

        setTitle("Reto");
        lblInfo.setText("Te ha retado " + retador);
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        btnRechazar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

    }

    private void onOK() {
        // add your code here
        controlador.sendln(Comandos.MATCH_ACK + " " + retador);
        // dispose(); No es necesario ya se realiza mas adelante
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
