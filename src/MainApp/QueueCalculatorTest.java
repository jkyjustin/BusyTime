package MainApp;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Created by jkyju_000 on 5/7/2016.
 */

public class QueueCalculatorTest {
    QueueCalculator calculator;
    List<Entry> dayEntryList;
    Entry e1, e2, e3, e4, e5;

    @Before
    public void setUp() {
        calculator = new QueueCalculator();
        dayEntryList = new ArrayList<>();
        e1 = new Entry(2015, 12, 30, 7, 55, 3);
        e2 = new Entry(2015, 12, 30, 8, 0, 7);
        e3 = new Entry(2015, 12, 30, 8, 13, 5);
        e4 = new Entry(2015, 12, 30, 8, 15, 9);
        e5 = new Entry(2015, 12, 30, 8, 23, 8);
        dayEntryList.add(e1);
        dayEntryList.add(e2);
        dayEntryList.add(e3);
        dayEntryList.add(e4);
        dayEntryList.add(e5);
    }

    @Test
    public void test1() {
        // test average inter arrival rate calculation
        assertEquals(7, calculator.dayAverageInterarrivalRate(dayEntryList), 0.1);
    }

    @Test
    public void test2() {
        // test inter arrival rate STDev calculation
        assertEquals(4.06, calculator.dayInterarrivalRateSTDev(dayEntryList), 0.1);
    }

    @Test
    public void test3() {
        // test average service rate calculation
        assertEquals(6.4, calculator.dayAverageServiceRate(dayEntryList), 0.1);
    }

    @Test
    public void test4 () {
        // test service rate STDev calculation
        assertEquals(2.15, calculator.dayServiceRateSTDev(dayEntryList), 0.1);
    }

    @Test
    public void test5 () {
        // test queue calculation of a single day
        double capacityRate = 12;
        double averageInterarrivalRate = calculator.dayAverageInterarrivalRate(dayEntryList);
        double interArrivalSTDev = calculator.dayInterarrivalRateSTDev(dayEntryList);
        double averageServiceRate = calculator.dayAverageServiceRate(dayEntryList);
        double serviceRateSTDev = calculator.dayServiceRateSTDev(dayEntryList);


        assertEquals(0.4018, calculator.calculateDayInventory(averageInterarrivalRate, interArrivalSTDev,
                averageServiceRate, serviceRateSTDev, capacityRate), 0.1);
    }
}
