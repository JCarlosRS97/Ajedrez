package GUI.Cliente;

import javax.swing.*;
import java.awt.*;

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

    public static final String HOME = "Home";
    public static final String PARTIDA = "Partida";
    private final String[] opciones = {"Aceptar",
            "Rechazar"};

    public BaseGUI(PanelCliente panelCliente) {
        partida.add(panelCliente);
        model = new DefaultListModel<>();
        list.setModel(model);


    }

    public JPanel getPanel() {
        return panel;
    }

    public void controlador(Controlador controlador) {
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
        lblError.setVisible(false);
    }

    public void addList(String[] users) {
        for(int i = 0; i < users.length; i++){
            if(!user.equals(users[i]))
                model.addElement(users[i]);
        }
    }

    public void deleteUserList(String[] users) {
        for(int i = 0; i < users.length; i++){
            model.removeElement(users[i]);
        }
    }


    public int askForMatch(String retador) {
        return JOptionPane.showOptionDialog(panel,
                "Te ha retado " + retador,
                "Reto",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,     //do not use a custom Icon
                opciones,  //the titles of buttons
                opciones[0]);
    }

    public void changePanel(String card){
        CardLayout c1 = (CardLayout) panel.getLayout();
        c1.show(panel, card);
    }
}
