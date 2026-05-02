package bulletinBoardService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Proxy;
import java.net.InetAddress;

public class ChatGUI extends JFrame {
    private JTextArea textArea;
    private JTextField textFieldMsg;
    private JTextField nameField;
    private JButton btnConnect;
    private JButton btnDisconnect;
    private JButton btnSend;
    private Messanger messanger = null;

    public ChatGUI() {
        setTitle("Текстова конференція");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Панель налаштувань
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Ім'я:"));
        nameField = new JTextField("User", 10);
        topPanel.add(nameField);
        btnConnect = new JButton("З'єднати");
        btnDisconnect = new JButton("Відключити");
        btnDisconnect.setEnabled(false);
        topPanel.add(btnConnect);
        topPanel.add(btnDisconnect);
        add(topPanel, BorderLayout.NORTH);

        // Область повідомлень
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Панель вводу
        JPanel bottomPanel = new JPanel();
        textFieldMsg = new JTextField(20);
        btnSend = new JButton("Відправити");
        btnSend.setEnabled(false);
        bottomPanel.add(textFieldMsg);
        bottomPanel.add(btnSend);
        add(bottomPanel, BorderLayout.SOUTH);

        // Слухачі кнопок
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InetAddress addr = InetAddress.getByName("224.0.0.1");
                    int port = 3456;
                    String name = nameField.getText();

                    UITasks ui = (UITasks) Proxy.newProxyInstance(getClass().getClassLoader(),
                            new Class[]{UITasks.class},
                            new EDTInvocationHandler(new UITasksImpl()));

                    messanger = new MessangerImpl(addr, port, name, ui);
                    messanger.start();

                    btnConnect.setEnabled(false);
                    btnDisconnect.setEnabled(true);
                    btnSend.setEnabled(true);
                    nameField.setEnabled(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnDisconnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (messanger != null) {
                    messanger.stop();
                    btnConnect.setEnabled(true);
                    btnDisconnect.setEnabled(false);
                    btnSend.setEnabled(false);
                    nameField.setEnabled(true);
                }
            }
        });

        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (messanger != null && !textFieldMsg.getText().isEmpty()) {
                    messanger.send();
                }
            }
        });
    }

    private class UITasksImpl implements UITasks {
        @Override
        public String getMessage() {
            String res = textFieldMsg.getText();
            textFieldMsg.setText("");
            return res;
        }

        @Override
        public void setText(String txt) {
            textArea.append(txt + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatGUI().setVisible(true);
        });
    }
}