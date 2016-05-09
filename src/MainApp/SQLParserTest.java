package MainApp;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by jkyju_000 on 5/6/2016.
 */
public class SQLParserTest {
    // NOTE TO READER: These tests are based on a randomly generated PostgreSQL database from the
    //      methods in GeneralSampleDatasetSQL package.
    //      Trying to run these tests will most likely not work on your randomly generated SQL server from
    //      the code in that package, as our random results will likely not be the same.
    SQLParser parser;

    @Before
    public void setUp() {
        parser = new SQLParser();
    }

    @Test
    public void testParseSQL1() {
        List<Entry> queryOne = parser.parseSQL(2012, 2015, 1, 12, 1, 30, 7, 19);

        // 80000 entries exist within generated database -- checks out!
        assertEquals(80000, queryOne.size());

        // let's test if datey is sorted in descending order
        // edge case is at index 19896 and 19897
        assertEquals(19, queryOne.get(19896).getTimeh());
        assertEquals(34, queryOne.get(19896).getTimem());
        assertEquals(1, queryOne.get(19896).getDated());
        assertEquals(1, queryOne.get(19896).getDatem());
        assertEquals(2015, queryOne.get(19896).getDatey());

        assertEquals(7, queryOne.get(19897).getTimeh());
        assertEquals(11, queryOne.get(19897).getTimem());
        assertEquals(30, queryOne.get(19897).getDated());
        assertEquals(12, queryOne.get(19897).getDatem());
        assertEquals(2014, queryOne.get(19897).getDatey());
    }

    @Test
    public void testParseSQL2() {
        // 4 entries exist on Dec. 30th, 2015 at 7am -- checks out!
        List<Entry> queryOne = parser.parseSQL(2015, 2015, 12, 12, 30, 30, 7, 7);
        assertEquals(4, queryOne.size());

        // now let's test if timem is being sorted in ascending order
        // first item should be at 7:01
        assertEquals(1, queryOne.get(0).getTimem());

        // second item at 7:12
        assertEquals(12, queryOne.get(1).getTimem());

        // third item at 7:17
        assertEquals(17, queryOne.get(2).getTimem());

        // fourth item at 7:30
        assertEquals(30, queryOne.get(3).getTimem());
    }

    @Test
    public void testParseSQL3() {
        // let's test if timeh is being sorted in ascending order
        List<Entry> queryOne = parser.parseSQL(2015, 2015, 12, 12, 30, 30, 7, 8);

        // 6 entries on 2015-12-30 between 7:00 and 8:59
        assertEquals(6, queryOne.size());

        // index 3 and 4 should be edge case where timeh increases from 7 to 8. Let's see if it's in order

        assertEquals(7, queryOne.get(3).getTimeh());
        assertEquals(30, queryOne.get(3).getTimem());

        assertEquals(8, queryOne.get(4).getTimeh());
        assertEquals(14, queryOne.get(4).getTimem());
    }

    @Test
    public void testParseSQL4() {
        // let's test if dated is sorted in descending order
        // grab a list of entries between 2015-12-29 and 2015-12-30 at time 7 to 7:59
        List<Entry> queryOne = parser.parseSQL(2015, 2015, 12, 12, 29, 30, 7, 7);

        // 9 entries at that specified time period. Checks out!
        assertEquals(9, queryOne.size());

        // Edge case of day change: index 3 and 4
        // Dated is sorted in descending order -- checks out!
        assertEquals(7, queryOne.get(3).getTimeh());
        assertEquals(30, queryOne.get(3).getTimem());
        assertEquals(30, queryOne.get(3).getDated());

        assertEquals(7, queryOne.get(4).getTimeh());
        assertEquals(3, queryOne.get(4).getTimem());
        assertEquals(29, queryOne.get(4).getDated());
    }

    @Test
    public void testBinEntryList1() {
        // lets test whether entries can be binned correctly
        List<Entry> queryOne = parser.parseSQL(2015, 2015, 12, 12, 29, 30, 7, 19);
        assertEquals(121, queryOne.size());

        List<List<Entry>> binnedList = parser.binEntryList(queryOne);
        assertEquals(2, binnedList.size());

        // size of first binned list
        assertEquals(60, binnedList.get(0).size());

        // first entry of first binned list
        assertEquals(1, binnedList.get(0).get(0).getTimem());
        assertEquals(6, binnedList.get(0).get(0).getServiceTime());
        assertEquals(7, binnedList.get(0).get(0).getTimeh());
        assertEquals(30, binnedList.get(0).get(0).getDated());

        // second entry of first binned list
        assertEquals(12, binnedList.get(0).get(1).getTimem());
        assertEquals(4, binnedList.get(0).get(1).getServiceTime());
        assertEquals(7, binnedList.get(0).get(1).getTimeh());
        assertEquals(30, binnedList.get(0).get(1).getDated());

        // third entry of first binned list
        assertEquals(17, binnedList.get(0).get(2).getTimem());
        assertEquals(6, binnedList.get(0).get(2).getServiceTime());
        assertEquals(7, binnedList.get(0).get(2).getTimeh());
        assertEquals(30, binnedList.get(0).get(2).getDated());

        // third LAST entry of first binned list
        assertEquals(30, binnedList.get(0).get(57).getTimem());
        assertEquals(10, binnedList.get(0).get(57).getServiceTime());
        assertEquals(19, binnedList.get(0).get(57).getTimeh());
        assertEquals(30, binnedList.get(0).get(57).getDated());

        // second LAST entry of first binned list
        assertEquals(59, binnedList.get(0).get(58).getTimem());
        assertEquals(5, binnedList.get(0).get(58).getServiceTime());
        assertEquals(19, binnedList.get(0).get(58).getTimeh());
        assertEquals(30, binnedList.get(0).get(58).getDated());

        // LAST entry of first binned list
        assertEquals(59, binnedList.get(0).get(59).getTimem());
        assertEquals(12, binnedList.get(0).get(59).getServiceTime());
        assertEquals(19, binnedList.get(0).get(59).getTimeh());
        assertEquals(30, binnedList.get(0).get(59).getDated());


        // size of second binned list
        assertEquals(61, binnedList.get(1).size());

        // first entry of second binned list
        assertEquals(3, binnedList.get(1).get(0).getTimem());
        assertEquals(12, binnedList.get(1).get(0).getServiceTime());
        assertEquals(7, binnedList.get(1).get(0).getTimeh());
        assertEquals(29, binnedList.get(1).get(0).getDated());

        // second entry of second binned list
        assertEquals(38, binnedList.get(1).get(1).getTimem());
        assertEquals(8, binnedList.get(1).get(1).getServiceTime());
        assertEquals(7, binnedList.get(1).get(1).getTimeh());
        assertEquals(29, binnedList.get(1).get(1).getDated());

        // third entry of second binned list
        assertEquals(40, binnedList.get(1).get(2).getTimem());
        assertEquals(4, binnedList.get(1).get(2).getServiceTime());
        assertEquals(7, binnedList.get(1).get(2).getTimeh());
        assertEquals(29, binnedList.get(1).get(2).getDated());

        // third LAST entry of second binned list
        assertEquals(23, binnedList.get(1).get(58).getTimem());
        assertEquals(9, binnedList.get(1).get(58).getServiceTime());
        assertEquals(19, binnedList.get(1).get(58).getTimeh());
        assertEquals(29, binnedList.get(1).get(58).getDated());

        // second LAST entry of second binned list
        assertEquals(42, binnedList.get(1).get(59).getTimem());
        assertEquals(6, binnedList.get(1).get(59).getServiceTime());
        assertEquals(19, binnedList.get(1).get(59).getTimeh());
        assertEquals(29, binnedList.get(1).get(59).getDated());

        // LAST entry of second binned list
        assertEquals(51, binnedList.get(1).get(60).getTimem());
        assertEquals(8, binnedList.get(1).get(60).getServiceTime());
        assertEquals(19, binnedList.get(1).get(60).getTimeh());
        assertEquals(29, binnedList.get(1).get(60).getDated());
    }
}
