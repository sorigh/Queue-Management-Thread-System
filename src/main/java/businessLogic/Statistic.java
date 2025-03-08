package businessLogic;

import model.Server;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Statistic {
    private static double averageST;
    private static double averageWT;
    private static final ArrayList<Integer> allWT = new ArrayList<>();
    private static int peakHour;
    private static int peakPeople = 0;

    public static void peakHourDecider(int people, int currentTime) {
        if (people > peakPeople){
            peakPeople = people;
            peakHour = currentTime;
        }
    }
    public static void addWaitingTime(int currentTime, ArrayList<Server> servers){
        int totalTime = 0;
        for (Server s:servers){
            totalTime += s.getWaitingPeriod();
        }
        allWT.add(currentTime, totalTime/servers.size());
    }
    public static void averageWaitingTime(int totalTime){
        int totalWait = 0;
        for (Integer i:allWT){
            totalWait += i;
        }
        averageWT = (double) totalWait /totalTime;
    }

    public static void setAverageST(double averageST) {
        Statistic.averageST = averageST;
    }
    public static String showStats(){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return "\nAverage waiting time: " + df.format(averageWT) + "\n" +
                "Average service time: " + df.format(averageST) + "\n" +
                "Peak hour: at second " + peakHour + " with " + peakPeople + " people \n";
    }



}
