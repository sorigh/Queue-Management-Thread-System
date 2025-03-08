package businessLogic;

import model.Client;
import model.Server;

import java.util.List;

import static businessLogic.Scheduler.amountOfPeopleWaitingInS;

//shortest time strategy
public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Client client) {
        Server shortestQueueServer = servers.get(0);
        for (Server server : servers) {
            int serv = amountOfPeopleWaitingInS(server);
            int shortestServ =  amountOfPeopleWaitingInS(shortestQueueServer);

            if (serv  < shortestServ) {
                shortestQueueServer = server;
            }
        }
        shortestQueueServer.addClient(client);
    }
}
