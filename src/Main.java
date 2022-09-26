import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            String url = "jdbc:postgresql://localhost:5432/ovchip";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            Connection mycon = DriverManager.getConnection(url, props);
            Statement myStat = mycon.createStatement();
            ResultSet myRs = myStat.executeQuery("select * from reiziger");
            System.out.println("Alle reizigers: ");

            while (myRs.next()) {
                String tussenvoegsel = myRs.getString("tussenvoegsel");
                if (tussenvoegsel == null) {
                    tussenvoegsel = "";
                } else {
                    tussenvoegsel = tussenvoegsel + " ";
                }
                System.out.println("#" + myRs.getString("reiziger_id") + ":" + " " + myRs.getString("voorletters")
                        + "." + " " + tussenvoegsel + myRs.getString("achternaam") +
                        " " + "(" + myRs.getString("geboortedatum") + ")");
            }

            mycon.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}