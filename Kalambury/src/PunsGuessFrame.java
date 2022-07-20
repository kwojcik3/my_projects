import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class PunsGuessFrame extends JFrame implements Runnable {
    private TraceServer Trace = new TraceServer();
    private String key;
    private JLabel paintLabel = new JLabel();
    private Klient Client = new Klient();
    private JPanel Panel3 = new JPanel();
    private JPanel Panel2 = new JPanel();
    private JTextArea textAreaGuessed = new JTextArea("Chat", 10, 20);
    private JTextField textFieldGuess = new JTextField("Zgadnij hasło");
    private JButton newGameButton = new JButton("Losuj nowe hasło");
    private JScrollPane scroll = new JScrollPane(textAreaGuessed);
    private JLabel guesspaintLabel = new JLabel();
    public PunsGuessFrame() {
        super("Zgadujący - Kalambury");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Panel3.setSize(500, 500);
        setSize(500, 620);
        setLocation(10, 0);
        Panel3.setLayout(new BorderLayout());
        add(Panel3);

        textFieldGuess.setBackground(Color.white);
        textAreaGuessed.setBackground(Color.GRAY);
        textAreaGuessed.setEditable(false);
        paintLabel.setSize(200, 200);
        scroll.setHorizontalScrollBarPolicy(scroll.HORIZONTAL_SCROLLBAR_NEVER);

        Panel2.add(newGameButton, BorderLayout.LINE_START);
        Panel2.add(paintLabel,BorderLayout.CENTER);
        Panel2.add(textFieldGuess, BorderLayout.PAGE_END);
        Panel2.add(scroll, BorderLayout.LINE_END);
        Panel3.add(Panel2, BorderLayout.PAGE_START);
        Panel3.add(guesspaintLabel, BorderLayout.CENTER);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    key = Client.losskey();
                    textAreaGuessed.setText(null);
                    Panel2.setUI(Panel2.getUI());
                    Panel3.setUI(Panel3.getUI());
                    textFieldGuess.setSize(30,3);
                } catch (Exception ex) {
                }
            }
        });

        textFieldGuess.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textAreaGuessed.setText(textAreaGuessed.getText() + "\n" + textFieldGuess.getText());
                    Client.setChat(textFieldGuess.getText());
                    if (textFieldGuess.getText().equals(key))
                        JOptionPane.showMessageDialog(null, "wygrałeś");
                    textFieldGuess.setText(null);
                }
            }
        });
    }

    public void painting(){
        Graphics g = guesspaintLabel.getGraphics();
        g.setColor(Color.blue);
        while(!Trace.traceX.empty()&&!Trace.traceY.empty())
            g.fillOval(Trace.traceX.pop(),Trace.traceY.pop(),10,10);
    }

    @Override
    public void run() {
        setVisible(true);
        new Thread(Trace).start();
        while(true)
        {
            try {Thread.sleep(30);} catch (Exception e) {}
            painting();
        }

    }
}