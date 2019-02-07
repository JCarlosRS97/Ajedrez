package GUI.Cliente;

import Utils.RecursosCliente;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BaseGUI {
    private JPanel panel;
    private JList list;
    private JButton btnConectar;
    private JButton btnRetar;
    private JTextField txtUser;
    private JLabel lblError;
    private JPanel tablero;
    private JLabel lblNegras;
    private JLabel lblBlancas;
    private String user = null;
    private String oponente;
    private DefaultListModel<String> model;
    private ConcurrentLinkedQueue<RematchDialog> dialogs; //Acumula los dialogs de retos
    private Controlador controlador;

    public static final String HOME = "Home";
    public static final String PARTIDA = "Partida";

    public BaseGUI(PanelCliente panelCliente) {
        tablero.add(panelCliente);
        model = new DefaultListModel<>();
        list.setModel(model);
        dialogs = new ConcurrentLinkedQueue<>();
        lblBlancas.setIcon(new ImageIcon(RecursosCliente.getSpriteUser()));
        lblNegras.setIcon(new ImageIcon(RecursosCliente.getSpriteUser()));
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
        user = txtUser.getText().replaceAll("[,. \n]", "");
        txtUser.setText(user);
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
        if(dialog != null){
            while(dialog!= null){
                dialog.dispose();
                dialog = dialogs.poll();
            }

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

    public void setTxtUsers(boolean blancas, String oponente){
        this.oponente = oponente;
        if(blancas){
            lblBlancas.setText(user);
            lblNegras.setText(oponente);
        }else {
            lblBlancas.setText(oponente);
            lblNegras.setText(user);
        }
    }

    public String getOponente() {
        return oponente;
    }
}
