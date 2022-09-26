import java.sql.*;
import java.util.List;

public class Main {
    private static Connection conn;

    public static void main(String [] args){
        try {
            ReizigerDAOPsql rdaosql = new ReizigerDAOPsql(getConnection());
            testReizigerDAO(rdaosql);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public static Connection getConnection() throws SQLException {
        if (conn == null)
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "postgres");
        return conn;
    }
    public static void closeConnection() throws SQLException {
        if(conn != null) {
            conn.close();
            conn = null;
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Bus", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        System.out.println(sietske);
        sietske.setAchternaam("Janssen");
        rdao.update(sietske);
        System.out.println("Achternaam Sietske is geupdate: \n" + sietske + "\n");



        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        System.out.println("[Test] find by Id geeft: ");
        Reiziger reiziger = rdao.findById(1);
        System.out.println(reiziger + "\n");




        List<Reiziger> rgbd = rdao.findByGbdatum("2002-12-03");
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : rgbd) {
            System.out.println(r);
        }
        System.out.println();


    }



}