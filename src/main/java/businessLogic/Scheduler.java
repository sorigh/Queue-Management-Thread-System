package businessLogic;

import model.Client;
import model.Server;

import java.util.ArrayList;

import static businessLogic.Statistic.addWaitingTime;

public class Scheduler {
    private final ArrayList<Server> servers = new ArrayList<>();
    private Strategy strategy;

    public Scheduler(int numberOfServers,SelectionPolicy sel) {
        changeStrategy(sel);
        for (int i = 0; i < numberOfServers; i++) {
            Server s = new Server();
            servers.add(s);
            Thread t = new Thread(s);
            t.setName(String.valueOf(i+1));
            t.start();
        }
    }
    public void changeStrategy(SelectionPolicy s){
        if (s == SelectionPolicy.SHORTEST_TIME){
            strategy = new ShortestTimeStrategy();
        }
        else {
            strategy = new ShortestQueueStrategy();
        }
    }
    public String topOfQueues(){
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Server server : servers) {

            sb.append("Queue ").append(i).append(": ");
            if (server.getCurrentClient() == null ) {
                sb.append("closed\n");
            }
            else {
                sb.append(server.getCurrentClient()).append("\n");
            }
            i++;
        }
        return String.valueOf(sb);
    }

    public void dispatchClient(Client client) {
        strategy.addTask(servers,client);
    }

    public int amountOfPeopleWaiting(){
        int peopleWaitingForService = 0;
        for (Server server : servers) {
            peopleWaitingForService += server.getClients().size();
            if (server.getCurrentClient() != null)
                peopleWaitingForService +=1;
        }

        return peopleWaitingForService;
    }

    public static int amountOfPeopleWaitingInS(Server s){
        int people = 0;
        people += s.getClients().size();
            if (s.getCurrentClient() != null)
                people +=1;
        return people;
    }


    public String contentInQueues(){
        StringBuilder sb = new StringBuilder();
        int i = 1;
        for (Server server : servers) {
            sb.append("Queue ").append(i);
            sb.append(" wt:").append(server.getWaitingPeriod());
            sb.append(" people: ").append(amountOfPeopleWaitingInS(server));
            sb.append("  =  ");
            if (server.getCurrentClient() == null ) {
                sb.append("closed\n");
            }
            else {
                if (server.getCurrentClient() != null)
                    sb.append("c:").append(server.getCurrentClient()).append("; ");
                for (Client c: server.getClients())
                    sb.append("c:").append(c).append("; ");

                sb.append("\n");
            }
            i++;
        }
        return sb.toString();
    }

    public void averageTime(int currentTime){
        addWaitingTime(currentTime,servers);
    }


}
