package businessLogic;

import model.Client;
import gui.Display;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable{
    private final Display display;
    private Scheduler scheduler;
    private final ArrayList<Client> clients = new ArrayList<>();

    //simulation data
    private int nbOfClients;
    private int nbOfServers;
    private int simulationInterval;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minServiceTime;
    private int maxServiceTime;
    private SelectionPolicy s;

    public SimulationManager(Display display) {
        this.display = display;
    }

    public void simMan(){
        System.out.println(s);
        this.scheduler = new Scheduler(nbOfServers,s);
        //showClients();
        generateRandomClients();
        clients.sort(Comparator.comparingInt(Client::getArrivalTime));
    }

    public void generateRandomClients(){
        Random r = new Random();
        int serviceTimeTotal = 0;
        //int result = r.nextInt(high-low) + low;
        for (int i=0;i<nbOfClients; i++){
            int arrival = r.nextInt(maxArrivalTime-minArrivalTime) + minArrivalTime;
            int service = r.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            Client c = new Client(i+1,arrival,service);
            clients.add(c);
            serviceTimeTotal+= service;
        }
        Statistic.setAverageST((double) serviceTimeTotal / nbOfClients);
    }

    @Override
    public void run() {
        int currentTime = 0;
        while (currentTime <= simulationInterval){
            synchronized (display) {
                appendToFile1("\n\n" + "Time " + currentTime + "\n\n");
                appendToFile2("\n\n" + "Time " + currentTime + "\n\n");
                appendToFile1(remainingClients());
                display.setTimerLabel(currentTime);
                display.addToMainTextArea("\n\n" + "Time " + currentTime + "\n\n");
                display.addToMainTextArea(remainingClients());
                display.notifyAll(); //notify waiting server threads
            }
            Iterator<Client> iterator = clients.iterator();
            while (iterator.hasNext()){
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    scheduler.dispatchClient(client);
                    iterator.remove();
                }
            }
            scheduler.averageTime(currentTime);
            appendToFile1(scheduler.topOfQueues());
            display.addToMainTextArea(scheduler.topOfQueues());
            appendToFile2(scheduler.contentInQueues());
            display.setContentTextArea(scheduler.contentInQueues());
            currentTime++;
            synchronized (this) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Statistic.peakHourDecider(scheduler.amountOfPeopleWaiting(), currentTime);
        }
        Statistic.averageWaitingTime(simulationInterval);
        display.addToMainTextArea(Statistic.showStats());
        appendToFile1(Statistic.showStats());
    }
    public String remainingClients() {
        StringBuilder sb = new StringBuilder();
        sb.append("Waiting clients: ");
        for (Client c: clients){
            sb.append(c);
        }
        sb.append("\n");
        return String.valueOf(sb);
    }

    public void appendToFile1(String content) {
        try (FileWriter writer = new FileWriter("testpeakp.txt", true)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void appendToFile2(String content) {
        try (FileWriter writer = new FileWriter("testprogressp.txt", true)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setStrategy(SelectionPolicy s) {
        this.s = s;
    }

    public void setNbOfClients(int nbOfClients) {
        this.nbOfClients = nbOfClients;
    }

    public void setNbOfServers(int nbOfServers) {
        this.nbOfServers = nbOfServers;
    }

    public void setSimulationInterval(int simulationInterval) {
        this.simulationInterval = simulationInterval;
    }

    public void setMinArrivalTime(int minArrivalTime) {
        this.minArrivalTime = minArrivalTime;
    }

    public void setMaxArrivalTime(int maxArrivalTime) {
        this.maxArrivalTime = maxArrivalTime;
    }

    public void setMinServiceTime(int minServiceTime) {
        this.minServiceTime = minServiceTime;
    }
    public void setMaxServiceTime(int maxServiceTime) {
        this.maxServiceTime = maxServiceTime;
    }
}
