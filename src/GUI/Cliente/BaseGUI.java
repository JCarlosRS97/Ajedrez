package GUI.Cliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BaseGUI {
    private JPanel panel;
    private JPanel partida;
    private JPanel central;
    private JList list;
    private JButton btnConectar;
    private JButton btnRetar;
    private JTextField txtUser;
    private JLabel lblError;
    private String user = null;
    private DefaultListModel<String> model;
    private Queue<RematchDialog> dialogs; //Acumula los dialogs de retos
    private Controlador controlador;

    public static final String HOME = "Home";
    public static final String PARTIDA = "Partida";
    private final String[] opciones = {"Aceptar",
            "Rechazar"};

    public BaseGUI(PanelCliente panelCliente) {
        partida.add(panelCliente);
        model = new DefaultListModel<>();
        list.setModel(model);
        dialogs = new LinkedList<>();


    }

    public JPanel getPanel() {
        return panel;
    }

    public void controlador(Controlador controlador) {
        this.controlador = controlador;
        btnConectar.setActionCommand("CONECTAR");
        btnRetar.setActionCommand("RETAR");
        btnRetar.addActionListener(controlador);
        btnConectar.addActionListener(controlador);
    }

    public String getUser(){
        user = txtUser.getText();
        return user;
    }

    public String getSelectedItem(){
        return (String)list.getSelectedValue();
    }

    public void setEnableBtnConectar(boolean enable){
        btnConectar.setEnabled(enable);
    }

    public void setEnableBtnRetar(boolean enable) {
        btnRetar.setEnabled(enable);
    }

    public void setEnableTxtUser(boolean enable) { txtUser.setEditable(enable); }

    public void setTextError(String s){
        lblError.setText(s);
        lblError.setVisible(true);
    }

    public void setTextErrorVisible(boolean visible){
        lblError.setVisible(visible);
    }

    public void addList(String[] users) {
        for (String user1 : users) {
            if (!user.equals(user1))
                model.addElement(user1);
        }
    }

    public void deleteUserList(String[] users) {
        for (String user1 : users) {
            model.removeElement(user1);
        }
    }


    public void askForMatch(String retador) {
        RematchDialog dialog = new RematchDialog( retador, controlador);
        dialogs.add(dialog);
        dialog.pack();
        dialog.setLocationRelativeTo(panel);
        dialog.setVisible(true);
    }

    public void changePanel(String card){
        //Primero se deben eliminar los dialogs
        RematchDialog dialog = dialogs.poll();
        while(dialog!= null){
            dialog.dispose();
            dialog = dialogs.poll();
        }
        CardLayout c1 = (CardLayout) panel.getLayout();
        c1.show(panel, card);
    }

    public void showWinnerDialog() {
        JOptionPane.showMessageDialog(panel, "Has ganado!", "Fin de partida", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showLooserDialog() {
        JOptionPane.showMessageDialog(panel, "Has perdido.", "Fin de partida", JOptionPane.INFORMATION_MESSAGE);
    }

}
