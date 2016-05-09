package MainApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jkyju_000 on 5/6/2016.
 */
public class BusyTime {
    // EFFECTS: reads a line of text from standard input and returns it
    private static String getUserResponse() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = null;
        try {
            line = br.readLine();
        } catch (IOException ioe) {
            System.out.println("IO error trying to read a line of text.");
            System.exit(1);
        }
        return line;
    }

    // Main program -- Note: Still need to implement robust input parameters with exceptions and error messages.
    public static void main(String args[]) {
        System.out.println("Welcome to BusyTime!");
        System.out.println("This application will calculate the average length of a line up from a time period specified by the user.");
        System.out.println("To start, please type in the answers to the following questions:");

        System.out.println("Please enter the capacity rate of your business in customers per hour:");
        String capacityRateResponse = getUserResponse();
        double capacityRate = Double.parseDouble(capacityRateResponse);

        System.out.println("Please enter the starting year:");
        System.out.println("eg. 1995");
        String startYearResponse = getUserResponse();
        int givenStartYear = Integer.parseInt(startYearResponse);

        System.out.println("Please enter the ending year (inclusive):");
        System.out.println("(Note: To look at a single year, please enter the same value as the starting year)");
        String endYearResponse = getUserResponse();
        int givenEndYear = Integer.parseInt(endYearResponse);

        System.out.println("Please enter the starting month:");
        System.out.println("eg. 1 for January");
        String startMonthResponse = getUserResponse();
        int givenStartMonth = Integer.parseInt(startMonthResponse);

        System.out.println("Please enter the ending month (inclusive):");
        System.out.println("(Note: To look at a single month, please enter the same value as the starting year)");
        String endMonthResponse = getUserResponse();
        int givenEndMonth = Integer.parseInt(endMonthResponse);

        System.out.println("Please enter the starting day:");
        System.out.println("eg. 2 for the second day of a month");
        String startDayResponse = getUserResponse();
        int givenStartDay = Integer.parseInt(startDayResponse);

        System.out.println("Please enter the ending day (inclusive):");
        System.out.println("(Note: To look at a single day, please enter the same value as the starting day)");
        String endDayResponse = getUserResponse();
        int givenEndDay = Integer.parseInt(endDayResponse);

        System.out.println("Please enter the starting hour:");
        System.out.println("eg. 7 for 7am, and 15 for 3pm");
        String startHourResponse = getUserResponse();
        int givenStartHour = Integer.parseInt(startHourResponse);

        System.out.println("Please enter the ending hour (inclusive):");
        System.out.println("(Note: To look at a single hour, please enter the same value as the starting hour)");
        String endHourResponse = getUserResponse();
        int givenEndHour = Integer.parseInt(endHourResponse);

        System.out.println("Thank you for your inputs. Calculating average line up length of the given period...");

        SQLParser sqlParser = new SQLParser();
        List<Entry> allEntries =  sqlParser.parseSQL(givenStartYear, givenEndYear, givenStartMonth, givenEndMonth,
                givenStartDay, givenEndDay, givenStartHour, givenEndHour);

        List<List<Entry>> binnedEntries = sqlParser.binEntryList(allEntries);
        int numberOfDays = binnedEntries.size();
        QueueCalculator calculator = new QueueCalculator();
        List<Double> listOfInventory = new ArrayList<>();

        for (List<Entry> entryList: binnedEntries) {
            double averageInterarrivalRate = calculator.dayAverageInterarrivalRate(entryList);
            double interarrivalSTDev = calculator.dayInterarrivalRateSTDev(entryList);
            double averageServiceRate = calculator.dayAverageServiceRate(entryList);
            double serviceRateSTDev = calculator.dayServiceRateSTDev(entryList);
            double inventory = calculator.calculateDayInventory(averageInterarrivalRate, interarrivalSTDev,
                    averageServiceRate, serviceRateSTDev, capacityRate);
            listOfInventory.add(inventory);
        }

        double sumOfInvs = 0;
        for (double inventory : listOfInventory) {
            sumOfInvs = sumOfInvs + inventory;
        }

        double averageInventory = sumOfInvs/(double)numberOfDays;
        DecimalFormat df = new DecimalFormat("#.###");
        String finalNumber = df.format(averageInventory);

        System.out.println("The average length of the queue in your specified date is:" + finalNumber);
    }
}