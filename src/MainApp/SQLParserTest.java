package MainApp;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jkyju_000 on 5/6/2016.
 */
public class SQLParserTest {

    SQLParser parser;

    @Before
    public void setUp() {
        parser = new SQLParser();
    }
    @Test
    public void test1() {
        // 4 entries exist on Dec. 30th, 2015 at 7am
        assertEquals(4, parser.parseSQL(2015, 2015, 12, 12, 30, 30, 7, 7).size());
    }
    @Test
    public void test2() {
        // 80000 entries exist within generated database -- checks out!
        assertEquals(80000, parser.parseSQL(2012, 2015, 1, 12, 1, 30, 7, 19).size());
    }
}
