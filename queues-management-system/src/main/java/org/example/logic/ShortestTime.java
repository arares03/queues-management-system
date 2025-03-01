package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public class ShortestTime implements StrategyTemplate {
    @Override
    public void assignTask(ArrayList<Server> servers, Task task) {
        Server chosenServer = findServerWithSmallestQueue(servers);

        if (chosenServer != null) {
            task.setTaskWaitTime(chosenServer.getWaitingTime().get());
            chosenServer.addTask(task);
        }
    }

    private Server findServerWithSmallestQueue(ArrayList<Server> servers) {
        int smallestQueueSize = Integer.MAX_VALUE;
        Server chosenServer = null;

        for (Server server : servers) {
            int currentQueueSize = server.getTasks().size();
            if (currentQueueSize < smallestQueueSize) {
                smallestQueueSize = currentQueueSize;
                chosenServer = server;
            }
        }

        return chosenServer;
    }
}
