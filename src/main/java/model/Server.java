package model;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{

    private final BlockingQueue<Client> clients;
    private final AtomicInteger waitingPeriod;
    private Client currentClient;


    public Server(){
        this.clients = new ArrayBlockingQueue<>(500);
        waitingPeriod = new AtomicInteger(0);
    }

    public void addClient(Client newClient){
            clients.add(newClient);
            waitingPeriod.addAndGet(newClient.getServiceTime());
    }
    @Override
    public void run() {
        while (true) {
            try {
                currentClient = clients.take();
                serveClient(currentClient);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }


    private void serveClient(Client client) throws InterruptedException {
        //display.addToMainTextArea("Serving: " + client + "\n");
        while (client.getServiceTime() > 0) {
            Thread.sleep(1000); // Wait for 1 second
            client.decreaseServiceTime();
            waitingPeriod.decrementAndGet();
            //display.addToMainTextArea("queue " + Thread.currentThread().getName() + ": " + client + "\n");
        }
        //display.addToMainTextArea("Finished serving: " + client + "\n");
        clients.remove(client);
        currentClient = null;

    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }
    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Client getCurrentClient() {
        return currentClient;
    }



}
