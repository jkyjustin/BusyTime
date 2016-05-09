package MainApp;

import java.util.List;

/**
 * Created by jkyju_000 on 5/7/2016.
 */
public class QueueCalculator {

    // computes average inter-arrival rate of customers per minute for a given day of specified hours
    // main function needs to convert the output of this number by given hour period
    public double dayAverageInterarrivalRate(List<Entry> dayEntryList) {
        double averageInterarrivalRate = 0;
        if (dayEntryList.size()>0) {
            int sum = 0;
            for (int i=0; i<dayEntryList.size()-1; i++) {
                Entry entry1 = dayEntryList.get(i);
                Entry entry2 = dayEntryList.get(i+1);
                int interArrivalRateMins = (entry2.getTimeh()-entry1.getTimeh())*60+(entry2.getTimem()-entry1.getTimem());
                sum = sum + interArrivalRateMins;
            }
            averageInterarrivalRate = ((double)sum)/((double)dayEntryList.size()-1);
            return averageInterarrivalRate;
        }
        return averageInterarrivalRate;
    }

    public double dayInterarrivalRateSTDev(List<Entry> dayEntryList) {
        double mean = dayAverageInterarrivalRate(dayEntryList);
        double sum = 0;

        if (dayEntryList.size()>0) {
            for (int i=0; i<dayEntryList.size()-1; i++) {
                Entry entry1 = dayEntryList.get(i);
                Entry entry2 = dayEntryList.get(i+1);
                int interArrivalRateMins = (entry2.getTimeh()-entry1.getTimeh())*60+(entry2.getTimem()-entry1.getTimem());
                sum = sum + Math.pow(((double)interArrivalRateMins-mean), 2);
            }
            double STDev = Math.sqrt(sum/((double)(dayEntryList.size()-1)));
            return STDev;
        } else return 0;
    }

    public double dayAverageServiceRate(List<Entry> dayEntryList) {
        double averageServiceRate=0;
        if (dayEntryList.size()>0) {
            int sum = 0;
            for (Entry e : dayEntryList) {
                sum = sum + e.getServiceTime();
            }
            averageServiceRate = (double)sum/(double)dayEntryList.size();
            return averageServiceRate;
        }
        return averageServiceRate;
    }

    public double dayServiceRateSTDev(List<Entry> dayEntryList) {
        double averageServiceRate = dayAverageServiceRate(dayEntryList);
        double sum = 0;
        if (dayEntryList.size()>0) {
            for (Entry e : dayEntryList) {
                sum = sum + Math.pow((e.getServiceTime()-averageServiceRate),2);
            }
            double STDev = Math.sqrt(sum/dayEntryList.size());
            return STDev;
        } else return 0;
    }

    public double calculateDayInventory (double dayAverageInterarrivalRate, double dayInterarrivalSTDev,
                                         double dayAverageServiceRate, double dayServiceRateSTDev, double capacityRate) {
        double interArrivalRatePerHour = dayAverageInterarrivalRate/60;
        double inputRatePerHour = 1/interArrivalRatePerHour;
        double utilization = inputRatePerHour/capacityRate;
        if (utilization>1) {
            utilization = 1;
        }
        double cA = dayInterarrivalSTDev/dayAverageInterarrivalRate;
        double cS = dayServiceRateSTDev/dayAverageServiceRate;
        double inventory = (Math.pow(utilization,2)/(1-utilization))*(Math.pow(cA, 2) + Math.pow(cS, 2))/2;
        return inventory;
    }
}
