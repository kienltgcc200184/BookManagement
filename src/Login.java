
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame{
    private JPanel mainPanel;
    private JPasswordField txtPass;
    private JTextField txtUsr;
    private JButton loginButton;
    private JButton cancelButton;

    public Login(String title){
        super (title);
        this.setContentPane(mainPanel);
        this.setLocationRelativeTo(null);
        this.setSize(400,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void exit() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void login() {
        String name = txtUsr.getText();
        String pass = String.valueOf(txtPass.getPassword());
        BookManagement b  = null;
        User admin = new User("Admin","123");
        User checkUser = new User(name,pass);

        boolean login = false;

        if(admin.equals(checkUser)){
            b = new BookManagement(name,this);
            login = true;
        }
        if(login){
            b.setVisible(true);
            this.setVisible(false);
        }else {
            showMess("Login Failed");
        }
    }
    private void showMess(String mess) {
        JOptionPane.showMessageDialog(this,mess);
    }
}

