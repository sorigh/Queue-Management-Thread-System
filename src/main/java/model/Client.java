package model;

public class Client {
    private final int id;
    private final int arrivalTime;
    private int serviceTime;


    public Client(int id, int arrivalTime, int serviceTime){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getId() {
        return id;
    }

    public void decreaseServiceTime() {
        serviceTime--;
    }

    @Override
    public String toString() {
        return "(" + this.id + " , " + this.arrivalTime + " , " + this.serviceTime + "); ";
    }
}
