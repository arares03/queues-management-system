package org.example.logic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;

public interface StrategyTemplate {
    public void assignTask(ArrayList<Server> servers, Task task);
}

