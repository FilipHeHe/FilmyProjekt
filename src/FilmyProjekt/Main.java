package FilmyProjekt;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws Exception {

        FilmManager film_mng = new FilmManager();
        ArtistsManager art_mng = new ArtistsManager();
        DBConnection.connect();
        try {
            art_mng.load_database(DBConnection.getConn());
            film_mng.load_database(DBConnection.getConn(), art_mng);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.disconnectConn();
        }

        Application app = new Application(film_mng, art_mng);
        app.run();

        DBConnection.connect();
        try {
            art_mng.saveDB(DBConnection.getConn());
            film_mng.saveDB(DBConnection.getConn());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.disconnectConn();
        }

    }
}

