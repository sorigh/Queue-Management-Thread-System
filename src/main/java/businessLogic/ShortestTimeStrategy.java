package businessLogic;

import model.Client;
import model.Server;

import java.util.List;

public class ShortestTimeStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Client client) {
        int shortestTime = servers.get(0).getWaitingPeriod();
        Server shortestTimeServer = servers.get(0);
        for (Server server : servers) {
            if (server.getWaitingPeriod() <= shortestTime) {
                shortestTime = server.getWaitingPeriod();
                shortestTimeServer = server;
            }
        }
        shortestTimeServer.addClient(client);
    }
}
