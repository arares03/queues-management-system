package org.example.gui;
import org.example.logic.SelectionType;
import org.example.logic.SimulationManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private final SimulationFrame simulationFrame;
    private SimulationManager simulationManager;

    public Controller (SimulationFrame simulationFrame){
        this.simulationFrame = simulationFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("START")) {
            int noClients = Integer.parseInt(simulationFrame.getClientsCount());
            int noQueues = Integer.parseInt(simulationFrame.getQueuesCount());
            String arrivalTimeInterval = simulationFrame.getArrivalTimeInterval();
            String serviceTimeInterval = simulationFrame.getServiceTimeInterval();

            assert arrivalTimeInterval.matches("\\d+-\\d+") : "Arrival time interval format should be %d-%d";
            assert serviceTimeInterval.matches("\\d+-\\d+") : "Service time interval format should be %d-%d";

            String[] arrivalTime = arrivalTimeInterval.split("-");
            int minArrivalTime = Integer.parseInt(arrivalTime[0]);
            int maxArrivalTime = Integer.parseInt(arrivalTime[1]);
            String[] serviceTime = serviceTimeInterval.split("-");
            int minServiceTime = Integer.parseInt(serviceTime[0]);
            int maxServiceTime = Integer.parseInt(serviceTime[1]);

            assert minArrivalTime <= maxArrivalTime : "Minimum arrival time should be less than or equal to maximum arrival time";
            assert minServiceTime <= maxServiceTime : "Minimum service time should be less than or equal to maximum service time";
            assert minArrivalTime >= 0 && minServiceTime >= 0 && noQueues >= 0 && noClients >= 0 : "Input values cannot be negative";

            int simulationTime = Integer.parseInt(simulationFrame.getSimulationTime());
            SelectionType selectionType = simulationFrame.getSelectionType();

            this.simulationManager = new SimulationManager(simulationFrame, noClients, noQueues,minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, simulationTime, selectionType);
            Thread thread = new Thread(simulationManager);
            thread.start();
        }
    }
}
