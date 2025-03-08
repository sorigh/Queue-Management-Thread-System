package gui;


import businessLogic.SimulationManager;
import businessLogic.SelectionPolicy;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Display extends JDialog implements ActionListener {
    private SimulationManager s;
    //panel
    private final JPanel mainPanel;
    private final JScrollPane progressScrollPane;
    private final JScrollPane queueContentScrollPane;

    //text fields
    private JTextField nrClientsTextField;
    private JTextField nrQueuesTextField;
    private JTextField simIntervalTextField;
    private JTextField arrMinTextField;
    private JTextField arrMaxTextField;
    private JTextField serMinTextField;
    private JTextField serMaxTextField;

    //text areas
    private final JTextArea progressTextArea;
    private final JTextArea queueContentTextArea;

    //label
    private final JLabel timerLabel = new JLabel();

    //buttons
    private JButton startButton;
    private JRadioButton queueStRadioButton;
    private JRadioButton timeStRadioButton;
    public Display(Frame parent){
        super(parent);
        setTitle("Queue Management");
        mainPanel = new JPanel();
        progressTextArea = new JTextArea();
        queueContentTextArea = new JTextArea();
        progressScrollPane = new JScrollPane(progressTextArea);
        progressScrollPane.setBounds(400,50,450,300);
        DefaultCaret caret = (DefaultCaret) progressTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        queueContentScrollPane = new JScrollPane(queueContentTextArea);
        queueContentScrollPane.setBounds(10,400,500,150);

        progressTextArea.setEditable(false);

        setContentPane(mainPanel);
        setMinimumSize(new Dimension(900, 650));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        createContent();
        mainPanel.setBackground(new Color(87,108,133));
        mainPanel.setLayout(null);

        startButton.addActionListener(ae -> {
            s = new SimulationManager(this);
            if (getData() == 1) {
                Thread thread = new Thread(s);
                thread.start();
            }
        });
        this.setVisible(true);
    }


    private int getData() {
        if (Objects.equals(nrClientsTextField.getText(), "") || Objects.equals(nrQueuesTextField.getText(), "") || Objects.equals(simIntervalTextField.getText(), "") ||
                Objects.equals(arrMinTextField.getText(), "") || Objects.equals(arrMaxTextField.getText(), "") || Objects.equals(serMinTextField.getText(), "") || Objects.equals(serMaxTextField.getText(), "")){
            JOptionPane.showMessageDialog(null, "Empty input!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        if (Integer.parseInt(nrClientsTextField.getText()) < 0 || Integer.parseInt(nrQueuesTextField.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "Something is wrong with the input!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        if (Integer.parseInt(arrMinTextField.getText()) > Integer.parseInt(arrMaxTextField.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong interval!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        if (Integer.parseInt(serMinTextField.getText()) > Integer.parseInt(serMaxTextField.getText())) {
            JOptionPane.showMessageDialog(null, "Wrong interval!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        s.setNbOfClients(Integer.parseInt(nrClientsTextField.getText()));
        s.setNbOfServers(Integer.parseInt(nrQueuesTextField.getText()));
        s.setSimulationInterval(Integer.parseInt(simIntervalTextField.getText()));
        s.setMinArrivalTime(Integer.parseInt(arrMinTextField.getText()));
        s.setMaxArrivalTime(Integer.parseInt(arrMaxTextField.getText()));
        s.setMinServiceTime(Integer.parseInt(serMinTextField.getText()));
        s.setMaxServiceTime(Integer.parseInt(serMaxTextField.getText()));

        if (!timeStRadioButton.isSelected() && !queueStRadioButton.isSelected()) {
            JOptionPane.showMessageDialog(null, "Select a strategy!", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        if (timeStRadioButton.isSelected())
            s.setStrategy(SelectionPolicy.SHORTEST_TIME);
        if (queueStRadioButton.isSelected())
            s.setStrategy(SelectionPolicy.SHORTEST_QUEUE);

        s.simMan();
    return 1;
    }

    private void createContent() {

        //text fields
        nrClientsTextField = new JTextField();
        nrClientsTextField.setFont(new Font("Georgia",Font.PLAIN,13));
        nrClientsTextField.setBounds(180, 60, 50, 20);

        nrQueuesTextField = new JTextField();
        nrQueuesTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        nrQueuesTextField.setBounds(180, 90, 50, 20);

        simIntervalTextField = new JTextField();
        simIntervalTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        simIntervalTextField.setBounds(180, 120, 50, 20);

        arrMinTextField = new JTextField();
        arrMinTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        arrMinTextField.setBounds(160, 150, 50, 20);

        arrMaxTextField = new JTextField();
        arrMaxTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        arrMaxTextField.setBounds(260, 150, 50, 20);

        serMinTextField = new JTextField();
        serMinTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        serMinTextField.setBounds(160, 180, 50, 20);

        serMaxTextField = new JTextField();
        serMaxTextField.setFont(new Font("Georgia",Font.PLAIN,14));
        serMaxTextField.setBounds(260, 180, 50, 20);




        //labels
        JLabel titleLabel = new JLabel();
        titleLabel.setFont(new Font("Georgia",Font.BOLD,20));
        titleLabel.setText("Queue Management");
        titleLabel.setBounds(100, 10, 300, 30);
        titleLabel.setForeground(new Color(223, 225, 229));

        JLabel queueProgressLabel = new JLabel();
        queueProgressLabel.setFont(new Font("Georgia",Font.PLAIN,15));
        queueProgressLabel.setText("Queue progress:");
        queueProgressLabel.setBounds(10, 380, 200, 20);
        queueProgressLabel.setForeground(new Color(223, 225, 229));

        JLabel inputLabel1 = new JLabel();
        inputLabel1.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel1.setText("Number of clients:");
        inputLabel1.setBounds(20, 60, 170, 20);
        inputLabel1.setForeground(new Color(223, 225, 229));

        JLabel inputLabel2 = new JLabel();
        inputLabel2.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel2.setText("Number of queues:");
        inputLabel2.setBounds(20, 90, 170, 20);
        inputLabel2.setForeground(new Color(223, 225, 229));

        JLabel inputLabel3 = new JLabel();
        inputLabel3.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel3.setText("Simulation interval:");
        inputLabel3.setBounds(20, 120, 170, 20);
        inputLabel3.setForeground(new Color(223, 225, 229));

        JLabel inputLabel4 = new JLabel();
        inputLabel4.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel4.setText("Arrival time");
        inputLabel4.setBounds(20, 150, 100, 20);
        inputLabel4.setForeground(new Color(223, 225, 229));

        JLabel inputLabel4_1 = new JLabel();
        inputLabel4_1.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel4_1.setText("min:");
        inputLabel4_1.setBounds(120, 150, 50, 20);
        inputLabel4_1.setForeground(new Color(223, 225, 229));

        JLabel inputLabel4_2 = new JLabel();
        inputLabel4_2.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel4_2.setText("max:");
        inputLabel4_2.setBounds(220, 150, 50, 20);
        inputLabel4_2.setForeground(new Color(223, 225, 229));

        JLabel inputLabel5 = new JLabel();
        inputLabel5.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel5.setText("Service time");
        inputLabel5.setBounds(20, 180, 170, 20);
        inputLabel5.setForeground(new Color(223, 225, 229));

        JLabel inputLabel5_1 = new JLabel();
        inputLabel5_1.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel5_1.setText("min:");
        inputLabel5_1.setBounds(120, 180, 50, 20);
        inputLabel5_1.setForeground(new Color(223, 225, 229));

        JLabel inputLabel5_2 = new JLabel();
        inputLabel5_2.setFont(new Font("Georgia",Font.PLAIN,15));
        inputLabel5_2.setText("max:");
        inputLabel5_2.setBounds(220, 180, 50, 20);
        inputLabel5_2.setForeground(new Color(223, 225, 229));


        JLabel timeLabelName = new JLabel();
        timeLabelName.setFont(new Font("Georgia",Font.BOLD,20));
        timeLabelName.setText("Timer:");
        timeLabelName.setBounds(400, 10, 80, 30);
        timeLabelName.setForeground(new Color(223, 225, 229));

        timerLabel.setFont(new Font("Georgia",Font.BOLD,20));
        timerLabel.setText("0");
        timerLabel.setBounds(500, 10, 170, 30);
        timerLabel.setForeground(new Color(223, 225, 229));

        //buttons
        queueStRadioButton = new JRadioButton();
        queueStRadioButton.setFont(new Font("Georgia",Font.PLAIN,15));
        queueStRadioButton.setText("Shortest Queue Strategy");
        queueStRadioButton.setBounds(10, 200, 200, 30);
        queueStRadioButton.setBackground(new Color(87,108,133));
        queueStRadioButton.setForeground(new Color(223, 225, 229));

        timeStRadioButton = new JRadioButton();
        timeStRadioButton.setFont(new Font("Georgia",Font.PLAIN,15));
        timeStRadioButton.setText("Shortest Time Strategy");
        timeStRadioButton.setBounds(10, 230, 200, 30);
        timeStRadioButton.setBackground(new Color(87,108,133));
        timeStRadioButton.setForeground(new Color(223, 225, 229));

        startButton = new JButton();
        startButton.setFont(new Font("Cambria",Font.BOLD,15));
        startButton.setText("Start simulation");
        startButton.setBounds(100, 280, 200, 30);
        startButton.setBackground(new Color(18,38,63));
        startButton.setForeground(new Color(197,198,201));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(queueStRadioButton);
        buttonGroup.add(timeStRadioButton);

        //text fields
        mainPanel.add(nrClientsTextField);
        mainPanel.add(nrQueuesTextField);
        mainPanel.add(simIntervalTextField);
        mainPanel.add(arrMinTextField);
        mainPanel.add(arrMaxTextField);
        mainPanel.add(serMinTextField);
        mainPanel.add(serMaxTextField);

        //scroll panes
        mainPanel.add(progressScrollPane);
        mainPanel.add(queueContentScrollPane);

        //labels
        mainPanel.add(titleLabel);
        mainPanel.add(inputLabel1);
        mainPanel.add(inputLabel2);
        mainPanel.add(inputLabel3);
        mainPanel.add(inputLabel4);
        mainPanel.add(inputLabel4_1);
        mainPanel.add(inputLabel4_2);
        mainPanel.add(inputLabel5);
        mainPanel.add(inputLabel5_1);
        mainPanel.add(inputLabel5_2);
        mainPanel.add(queueProgressLabel);
        mainPanel.add(timeLabelName);
        mainPanel.add(timerLabel);


        //buttons
        mainPanel.add(startButton);
        mainPanel.add(queueStRadioButton);
        mainPanel.add(timeStRadioButton);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void addToMainTextArea(String s) {
        this.progressTextArea.append(s);
    }

    public void setContentTextArea(String s) {
        this.queueContentTextArea.setText(s);
    }

    public void setTimerLabel(int time){
        timerLabel.setText(String.valueOf(time));
    }


}
