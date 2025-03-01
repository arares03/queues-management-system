package org.example.gui;

import org.example.logic.SelectionType;
import org.example.model.Server;
import org.example.model.Task;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class SimulationFrame extends JFrame {

    private JPanel mainPanel;
    private JTextField clientsField;
    private JLabel clientsLabel;
    private JTextField queuesField;
    private JLabel queuesLabel;
    private JTextField arrivalTimeField;
    private JLabel arrivalTimeLabel;
    private JTextField serviceTimeField;
    private JLabel serviceTimeLabel;
    private JButton startButton;
    private JLabel simulationTimeLabel;
    private JTextField simulationTimeField;
    private JLabel selectionTypeLabel;
    private ButtonGroup selectionTypeButtonGroup;
    private JRadioButton shortestTimeRadioButton;
    private JRadioButton shortestQueueRadioButton;
    private JEditorPane serverStatusPane;

    Controller controller = new Controller(this);

    public SimulationFrame(String title) {
        super(title);
        prepareGUI();
        setContentPane(mainPanel);
    }

    public void prepareGUI() {
        setSize(800, 600);

        mainPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        JPanel queuesPanel = new JPanel();
        JPanel tasksPanel = new JPanel();
        JPanel inputAndTaskPanel = new JPanel(new BorderLayout());

        clientsLabel = new JLabel("Number of queues:"); //sa nu uit sa inversez astea!!
        clientsField = new JTextField();
        queuesLabel = new JLabel("Number of clients:");
        queuesField = new JTextField();
        arrivalTimeLabel = new JLabel("Arrival time interval:");
        arrivalTimeField = new JTextField();
        serviceTimeLabel = new JLabel("Service time interval:");
        serviceTimeField = new JTextField();
        simulationTimeLabel = new JLabel("Simulation time:");
        simulationTimeField = new JTextField();
        selectionTypeLabel = new JLabel("Selection policy:");
        selectionTypeButtonGroup = new ButtonGroup();
        shortestTimeRadioButton = new JRadioButton("Shortest Time");
        shortestQueueRadioButton = new JRadioButton("Shortest Queue");
        selectionTypeButtonGroup.add(shortestTimeRadioButton);
        selectionTypeButtonGroup.add(shortestQueueRadioButton);
        startButton = new JButton("Start");
        serverStatusPane = new JEditorPane();
        serverStatusPane.setContentType("text/html");
        serverStatusPane.setEditable(false);
        serverStatusPane.setPreferredSize(new Dimension(500, 400));
        JScrollPane serverStatusScrollPane = new JScrollPane(serverStatusPane);

        inputPanel.add(clientsLabel);
        inputPanel.add(clientsField);
        inputPanel.add(queuesLabel);
        inputPanel.add(queuesField);
        inputPanel.add(arrivalTimeLabel);
        inputPanel.add(arrivalTimeField);
        inputPanel.add(serviceTimeLabel);
        inputPanel.add(serviceTimeField);
        inputPanel.add(simulationTimeLabel);
        inputPanel.add(simulationTimeField);
        inputPanel.add(selectionTypeLabel);
        inputPanel.add(shortestTimeRadioButton);
        inputPanel.add(new JLabel());
        inputPanel.add(shortestQueueRadioButton);
        inputPanel.add(startButton);

        startButton.setActionCommand("START");
        startButton.addActionListener(this.controller);

        inputAndTaskPanel.add(inputPanel, BorderLayout.NORTH);
        inputAndTaskPanel.add(tasksPanel, BorderLayout.CENTER);

        mainPanel.add(inputAndTaskPanel, BorderLayout.WEST);
        mainPanel.add(queuesPanel, BorderLayout.CENTER);
        queuesPanel.add(serverStatusScrollPane);
    }

    public void displayStatus(ArrayList<Server> servers, int currentTime, LinkedBlockingQueue<Task> tasks, int peakHour) {
        String status = "<h1 style='text-align:center;'>Now: " + currentTime + "</h1>";

        status += "<h2 style ='text-align:center;'>" + getTasksLabel(tasks) + "</h2>";
        int i = 1;
        for (Server server : servers) {
            String backgroundColor = (i % 2 == 0) ? "#f0f0f0" : "#ffffff";
            status += "<div style='background-color:" + backgroundColor + "; padding: 5px;'>";
            status += "<span style='font-weight:bold;'>Server " + i + ":</span> " + server.toString() + "<br>";
            status += "</div>";
            i++;
        }
        status += "<div> Peak hour: " + peakHour + "</div>";
        status += "</body></html>";
        serverStatusPane.setText(status);
    }
    public String getTasksLabel(LinkedBlockingQueue<Task> tasks){
        String str = "Generated tasks: ";
        for (Task task : tasks) {
            String taskString = "(" + task.getId() + "," + task.getArrivalTime() + "," + task.getServiceTime() + ")" + "  ";
            str += taskString;
        }
        return str;
    }



    public String getClientsCount() {
        return clientsField.getText();
    }

    public String getQueuesCount() {
        return queuesField.getText();
    }

    public String getArrivalTimeInterval() {
        return arrivalTimeField.getText();
    }

    public String getServiceTimeInterval() {
        return serviceTimeField.getText();
    }

    public String getSimulationTime() {
        return simulationTimeField.getText();
    }

    public void displayAverageWaitingTime(float averageWaitingTime) {
        JOptionPane.showMessageDialog(this, "Average Waiting Time: " + averageWaitingTime + " seconds", "Average Waiting Time", JOptionPane.INFORMATION_MESSAGE);
    }
    public SelectionType getSelectionType() {
        if (shortestTimeRadioButton.isSelected()) {
            return SelectionType.SHORTEST_TIME;
        } else {
            return SelectionType.SHORTEST_QUEUE;
        }
    }
}