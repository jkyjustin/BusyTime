package GeneralSampleDatasetSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by jkyju_000 on 2/16/2016.
 */
public class MakeArrivalSqlDatabase {
    public static void main( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "adfadf12");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE ARRIVALS " +
                    "(DATEY         INT     NOT NULL," +
                    " DATEM         INT     NOT NULL, " +
                    " DATED         INT     NOT NULL, " +
                    " TIMEH         INT     NOT NULL, " +
                    " TIMEM         INT     NOT NULL, " +
                    " SERVICETIME   INT NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
