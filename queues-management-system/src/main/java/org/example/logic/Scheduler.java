package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Scheduler {

    private ArrayList<Server> servers;
    private StrategyTemplate strategy;

    public Scheduler(int noQueues, SelectionType policy) {
        servers = new ArrayList<>();

        CyclicBarrier barrier = new CyclicBarrier(noQueues);
        for (int i = 0; i < noQueues; i++) {
            Server server = new Server(barrier);
            Thread thread = new Thread(server);
            thread.start();
            servers.add(server);
        }
     setStrategy(policy);
    }

    private void setStrategy(SelectionType policy) {
        switch (policy) {
            case SHORTEST_QUEUE:
                strategy = new ShortestQueue();
                break;
            case SHORTEST_TIME:
                strategy = new ShortestTime();
                break;
            default:
                throw new IllegalArgumentException("Unknown policy: " + policy); ///aici nu intra niciodata ^
        }
    }
    public void dispatchTask(Task task) {
        strategy.assignTask(servers, task);
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public int getNoActiveServers() {
        int activeServers = 0;
        for (Server server : servers) {
            if (server.isActive()) {
                activeServers++;
            }
        }
        return activeServers;
    }


}
