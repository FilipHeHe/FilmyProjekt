package FilmyProjekt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmManager {
    private List<Film> films = new ArrayList<>();
    private ReviewsManager rev_mng = new ReviewsManager();

    public void load_database(Connection conn, ArtistsManager art_mng) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(select_query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()){
            Film film;
            if(rs.getInt("JeAnim") == 1){
                Animated film_a = new Animated();
                film_a.setDoporuceny_vek(rs.getInt("Vek"));
                film = film_a;
            }
            else{
                film = new Played();
            }

            film.setId(rs.getInt("ID"));
            film.setNazev(rs.getString("Nazev"));
            film.setReziser(rs.getString("Reziser"));
            film.setRok_vydani(rs.getInt("Vydani"));
            film.setArtists(art_mng.getArtistByFilmId(film));
            film.setReviews(rev_mng.getReviewsByFilmId(conn, film));
            films.add(film);
        }
        stmt = conn.prepareStatement("DELETE FROM artists");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("DELETE FROM reviews");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("DELETE FROM films");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("VACUUM");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("UPDATE sqlite_sequence SET seq = 0 WHERE 'name' = 'films'");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("UPDATE sqlite_sequence SET seq = 0 WHERE 'name' = 'reviews'");
        stmt.executeUpdate();
        stmt = conn.prepareStatement("UPDATE sqlite_sequence SET seq = 0 WHERE 'name' = 'artists'");
        stmt.executeUpdate();
    }

    public void saveDB(Connection conn) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(insert_query);
        for(Film film : films){
            stmt.setString(1, film.getNazev());
            stmt.setString(2, film.getReziser());
            stmt.setInt(3, film.getRok_vydani());
            if(film instanceof Animated) {
                stmt.setInt(4, ((Animated) film).getDoporuceny_vek());
                stmt.setInt(5, 1);
            }
            else {
                stmt.setInt(5, 0);
            }
            rev_mng.saveDB(conn, film);
            stmt.executeUpdate();
        }
    }

    public List<Film> getFilms() {
        return films;
    }
    public void setFilms(List<Film> films) {
        this.films = films;
    }

    public static final String select_query = "SELECT * FROM films";
    public static final String insert_query = "INSERT INTO films(Nazev, Reziser, Vydani, Vek, JeAnim) VALUES (?, ?, ?, ?, ?)";
}
