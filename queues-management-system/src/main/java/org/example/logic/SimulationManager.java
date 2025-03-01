package org.example.logic;

import org.example.gui.SimulationFrame;
import org.example.model.Server;
import org.example.model.Task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SimulationManager implements Runnable {
    private int simulationTime;
    private int noQueues;
    private int noClients;
    private  LinkedBlockingQueue<Task> tasks;
    private SimulationFrame simulationFrame;
    private Scheduler scheduler;
    private SelectionType selectionType;


    public SimulationManager(SimulationFrame simulationFrame, int noQueues, int noClients,int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime, int simulationTime, SelectionType selectionType){
        this.simulationFrame = simulationFrame;
        this.simulationTime = simulationTime;
        this.noQueues = noQueues;
        this.noClients = noClients;
        this.selectionType = selectionType;

        this.tasks = new LinkedBlockingQueue<>();
        this.generateRandomTasks(noClients, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);
    }

    private void generateRandomTasks(int size, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime) {
        int[] arrivalTimes = new int[size];
        int[] serviceTimes = new int[size];

        for (int i = 0; i < size; i++) {
            arrivalTimes[i] = (int) (Math.random() * (maxArrivalTime - minArrivalTime + 1)) + minArrivalTime;
            serviceTimes[i] = (int) (Math.random() * (maxServiceTime - minServiceTime + 1)) + minServiceTime;
        }

        Arrays.sort(arrivalTimes);

        for (int i = 0; i < size; i++) {
            Task task = new Task(i + 1, arrivalTimes[i], serviceTimes[i]);
            this.tasks.add(task);
        }
    }


    private void clearFile(String fileName) {
        try {
            Files.write(Paths.get(fileName), new byte[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() { // functia asta i mai lunga pt ca tot folosesc write ul

        try {
            File file = new File("display.txt");

            FileWriter fw = new FileWriter(file);
            fw.write("");
            fw.flush();
        }
        catch(IOException e) {
            System.out.println("couldn't empty the file!");
        }

        StringBuilder fileContent = new StringBuilder();

        fileContent.append("Generated tasks:\n");
        for (Task t : tasks) {
            fileContent.append("(").append(t.getId()).append(",").append(t.getArrivalTime()).append(",").append(t.getServiceTime()).append(")\n");
        }
        fileContent.append("\n");

        FileWriter writer = null;
        try {
            writer = new FileWriter("display.txt", true);
            writer.write(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        scheduler = new Scheduler(noQueues, selectionType);

        int currentTime = 0;
        int peakTime = -1;
        int peakSize = -1;

        while (currentTime < simulationTime) {
            for (Task task : tasks) {
                if (task.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(task);
                }
            }

            fileContent.setLength(0);
            fileContent.append("Current time = ").append(currentTime).append("\n");
            int index = 1;
            for (Server server : scheduler.getServers()) {
                fileContent.append("\tServer ").append(index).append(": ").append(server.toString()).append("\n");
                index++;
            }

            try {
                writer = new FileWriter("display.txt", true);
                writer.write(fileContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            simulationFrame.displayStatus(scheduler.getServers(), currentTime, tasks, peakTime);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currentTime++;

            final int activeServers = scheduler.getNoActiveServers();
            if(peakSize < activeServers)
            {
                peakTime = currentTime;
                peakSize = activeServers;
            }
        }

        int sumServiceTime = 0;
        int sumWaitingPeriods = 0;
        int noFinishedClients = 0;

        for (Task t : tasks) {

            sumWaitingPeriods += t.getTaskWaitTime();
            if(t.getServiceTime() == 0){
                noFinishedClients++;
                sumServiceTime+= t.getInitServiceTime();
            }
        }
        System.out.println(sumWaitingPeriods);
        float averageWaitingPeriod = (float) sumWaitingPeriods / noClients;
        float averageServiceTime = (float) sumServiceTime / noFinishedClients;
        simulationFrame.displayAverageWaitingTime(averageWaitingPeriod);
        fileContent.setLength(0);
        fileContent.append("\nAverage waiting time: ").append(averageWaitingPeriod).append(" seconds\n");
        fileContent.append("\nAverage service time: ").append(averageServiceTime).append(" seconds\n");
        if (peakTime != -1) {
            fileContent.append("Peak hour: ").append(peakTime).append("\n");
        }
        else{
            fileContent.append("bad idea, you did sth wrong with the peak hour");
        }

        try {
            writer = new FileWriter("display.txt", true);
            writer.write(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
