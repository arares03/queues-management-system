package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public class ShortestQueue implements StrategyTemplate {
    @Override
    public void assignTask(ArrayList<Server> servers, Task task) {
        Server selectedServer = findServerWithShortestQueue(servers);

        if (selectedServer != null) {
            task.setTaskWaitTime(selectedServer.getWaitingTime().get());
            selectedServer.addTask(task);
        }
    }

    private Server findServerWithShortestQueue(ArrayList<Server> servers) {
        int minQueueSize = Integer.MAX_VALUE;
        Server selectedServer = null;

        for (Server server : servers) {
            int currentQueueSize = server.getTasks().size();
            if (currentQueueSize < minQueueSize) {
                minQueueSize = currentQueueSize;
                selectedServer = server;
            }
        }

        return selectedServer;
    }
}
