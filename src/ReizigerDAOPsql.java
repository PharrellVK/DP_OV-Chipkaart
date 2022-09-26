import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection conn;

    public ReizigerDAOPsql(Connection connection) {
        this.conn = connection;
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "postgres");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean save(Reiziger reiziger) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO reiziger (reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) " +
                    "VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException sql) {
            sql.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?," +
                    " achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?;");
            ps.setInt(5, reiziger.getId());
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?;");
            ps.setInt(1, id);
            ResultSet myResult = ps.executeQuery();
            myResult.next();

            int rId = myResult.getInt("reiziger_id");
            String voorletters = myResult.getString("voorletters");
            String tussenvoegsel = myResult.getString("tussenvoegsel");
            String achternaam = myResult.getString("achternaam");
            Date geboortedatum = myResult.getDate("geboortedatum");
            Reiziger newReiziger = new Reiziger(rId, voorletters, tussenvoegsel, achternaam, geboortedatum);

            ps.close();
            myResult.close();
            return newReiziger;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?;");
            ps.setDate(1, java.sql.Date.valueOf(datum));
            ResultSet myResult = ps.executeQuery();

            List<Reiziger> reizigers = new ArrayList<>();

            while (myResult.next()) {
                int id = myResult.getInt("reiziger_id");
                String voorletters = myResult.getString("voorletters");
                String tussenvoegsel = myResult.getString("tussenvoegsel");
                String achternaam = myResult.getString("achternaam");
                Date geboortedatum = myResult.getDate("geboortedatum");

                reizigers.add(new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum));
            }

            ps.close();
            myResult.close();
            return reizigers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM reiziger WHERE reiziger_id = ?;");
            ps.setInt(1, reiziger.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Reiziger> findAll() {
        try {
            List<Reiziger> alleReizigers = new ArrayList<>();
            String query = "select * from reiziger;";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet myResult = pst.executeQuery();

            while (myResult.next()) {
                int id = myResult.getInt("reiziger_id");
                String voorletters = myResult.getString("voorletters");
                String tussenvoegsel = myResult.getString("tussenvoegsel");
                String achternaam = myResult.getString("achternaam");
                Date geboortedatum = myResult.getDate("geboortedatum");

                Reiziger newReiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);

                alleReizigers.add(newReiziger);
            }

            return alleReizigers;
        } catch (Exception exc) {
            exc.printStackTrace();
            return new ArrayList<>();
        }
    }
}

