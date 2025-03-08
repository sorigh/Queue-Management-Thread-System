package businessLogic;

import model.Client;
import model.Server;

import java.util.List;

public interface Strategy {
    void addTask(List<Server> servers, Client client);
}
