package GUI.Cliente;

import Utils.RecursosCliente;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    private JPasswordField txtPassword;
    private JButton noTienesCuentaClickButton;
    private JTextField txtMsg;
    private JButton btnSend;
    private JLabel infoPlayer;
    private JEditorPane txtPane;
    private String user = null;
    private String oponente;
    private DefaultListModel<String> model;
    private ConcurrentLinkedQueue<RematchDialog> dialogs; //Acumula los dialogs de retos
    private Controlador controlador;
    private final HTMLDocument doc;

    public static final String HOME = "Home";
    public static final String PARTIDA = "Partida";

    public BaseGUI(PanelCliente panelCliente) {
        tablero.add(panelCliente);
        model = new DefaultListModel<>();
        list.setModel(model);
        dialogs = new ConcurrentLinkedQueue<>();
        lblBlancas.setIcon(new ImageIcon(RecursosCliente.getSpriteUser()));
        lblNegras.setIcon(new ImageIcon(RecursosCliente.getSpriteUser()));
        noTienesCuentaClickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("http://localhost"));
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar() == '\n')
                   btnConectar.doClick();
            }
        });
        txtMsg.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(e.getKeyChar() == '\n')
                    btnSend.doClick();
            }
        });
        doc = (HTMLDocument) txtPane.getDocument();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void controlador(Controlador controlador) {
        this.controlador = controlador;
        btnConectar.setActionCommand("CONECTAR");
        btnRetar.setActionCommand("RETAR");
        btnSend.setActionCommand("SEND");
        btnRetar.addActionListener(controlador);
        btnConectar.addActionListener(controlador);
        btnSend.addActionListener(controlador);
    }

    public String getUser(){
        user = txtUser.getText();
        return user;
    }

    public String getPassword(){
        return String.valueOf(txtPassword.getPassword());
    }

    public void setEnableTxtPassword(boolean enable) { txtPassword.setEditable(enable); }

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

    public String getMsg(){ return txtMsg.getText(); }

    public void setChatMsg(String str){
        try {
            doc.insertBeforeEnd(doc.getElement(doc.getDefaultRootElement(), StyleConstants.NameAttribute, HTML.Tag.P), str);
            System.out.println(txtPane.getText());
        } catch (IOException| BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void clearMsg(){ txtMsg.setText(""); }

    public void setInfoTxt(String puntuacion) {
        infoPlayer.setText("<html><body>Jugador: " + user + "<br>Puntuacion: " + puntuacion + "</body></html>");
    }
}
