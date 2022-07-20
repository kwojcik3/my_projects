import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.concurrent.TimeUnit;

public class PunsPaintFrame extends JFrame implements Runnable, MouseMotionListener
{
    //----------------painting frame---------
    JButton clearButton = new JButton("clear");
    JLabel paintLabel = new JLabel();
    JPanel paintPanel = new JPanel();
    Label coordinatesLabel =new Label();
    Color traceColor = Color.BLUE;
    //--------------
    private ChatServer server = new ChatServer();
    private Klient Client = new Klient();
    private JPanel Panel4 = new JPanel();
    JTextArea chatText = new JTextArea("chat");
    private JScrollPane scroll = new JScrollPane(chatText);
    public JTextField textFieldKey = new JTextField("hasło",1);
    JButton refreshButton = new JButton("refresh");

    PunsPaintFrame() {
        super("Rysujący - Kalambury");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocation(500, 100);
        setLayout(new BorderLayout());

        paintPanel.setLayout(new BorderLayout());
        chatText.setBackground(Color.gray);
        //chatText.setSize(20,200);
        chatText.setEditable(false);
        scroll.setHorizontalScrollBarPolicy(scroll.HORIZONTAL_SCROLLBAR_NEVER);
        textFieldKey.setEditable(false);
        scroll.getAutoscrolls();

        coordinatesLabel.setBounds(20, 40, 10, 20);
       // paintLabel.setBounds(10,10,100,100);
       // textFieldKey.setSize(10, 3);

        add(Panel4,BorderLayout.EAST);
        add(paintPanel,BorderLayout.CENTER);
        add(coordinatesLabel, BorderLayout.PAGE_START);
        paintPanel.add(clearButton, BorderLayout.WEST);
        paintPanel.add(paintLabel, BorderLayout.CENTER);
        paintPanel.add(refreshButton, BorderLayout.AFTER_LAST_LINE);
        Panel4.setLayout(new BorderLayout());
        Panel4.add(scroll,BorderLayout.CENTER);
        Panel4.add(textFieldKey,BorderLayout.AFTER_LAST_LINE);

        addMouseMotionListener(this);

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                paintPanel.setUI(paintPanel.getUI());
                paintLabel.setUI(paintLabel.getUI());
            }
        });

        refreshButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                textFieldKey.setText(Client.getkey());
            }
        });

    }
    public void mouseDragged(MouseEvent e) {
        coordinatesLabel.setText("X="+e.getX()+", Y="+e.getY());
        Graphics g = paintLabel.getGraphics();
        g.setColor(traceColor);
        g.fillOval(e.getX()-73,e.getY()-66,10,10);
        Client.setTrace(e.getX()-73,e.getY()-60);
    }
    public void mouseMoved(MouseEvent e) {
        coordinatesLabel.setText("X="+e.getX()+", Y="+e.getY());
    }
    //--------------------------------------

    @Override
    public void run() {
        new Thread(server).start();
        Client.getkey();
        setVisible(true);
        String previous = "null";
        while (true){
            try {Thread.sleep(100);} catch (Exception e) {}

            if(!(previous.equals(server.saveMe)))
            {
                chatText.setText(chatText.getText()+"\n"+server.saveMe);
                previous = server.saveMe;
            }
            System.out.println(previous);
            System.out.println(server.saveMe);
    }}
}
