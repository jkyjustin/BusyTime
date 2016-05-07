package GeneralSampleDatasetSQL;
import java.sql.*;


/**
 * Created by jkyju_000 on 2/16/2016.
 */
public class GenerateArrivalSqlDatabase {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "adfadf12");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();

            for (int i = 0; i < 80000; i++) {
                String sql = "INSERT INTO ARRIVALS (DATEY,DATEM,DATED,TIMEH,TIMEM,SERVICETIME) "
                        + "VALUES ( " + (2012 + (int)(Math.random() * ((2015 - 2012) + 1))) + ", " + (1 + (int)(Math.random() * ((12 - 1) + 1))) + ", " + (1 + (int)(Math.random() * ((30 - 1) + 1))) + ", " + (7 + (int)(Math.random() * ((19 - 7) + 1))) + ", " + (0 + (int)(Math.random() * ((59 - 0) + 1))) + ", " + (3 + (int)(Math.random() * ((12 - 3) + 1))) + ");";
                stmt.executeUpdate(sql);
            }

            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }
}
