package FilmyProjekt;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistsManager {

    private List<Integer> film_ids = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private List<ArtistsTable> tables = new ArrayList<>();

    public List<ArtistsTable> getTables() {
        return tables;
    }

    public void setTables(List<ArtistsTable> tables) {
        this.tables = tables;
    }

    public void load_database(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(select_query);
        while (rs.next()) {
            List<Artist> actors = new ArrayList<>();
            ArtistsTable table = new ArtistsTable();
            String serializedData = rs.getString("Umelci");
            try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedData.getBytes("ISO-8859-1"));
                 ObjectInputStream ois = new ObjectInputStream(bis)) {

                actors = (ArrayList<Artist>) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                continue;
            }
            table.setArtists(actors);
            table.setFilm_id(rs.getInt("film_ID"));
            tables.add(table);
        }
    }

    public void saveDB(Connection conn) throws SQLException {

        for (ArtistsTable dat : tables) {
            PreparedStatement stmt = conn.prepareStatement(insert_query);
            String serializedData;
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {

                oos.writeObject(dat.getArtists());
                serializedData = bos.toString(StandardCharsets.ISO_8859_1);

            } catch (IOException e) {
                System.out.println("Nepovedlo se :((");
                continue;
            }

            stmt.setString(1, serializedData);
            stmt.setInt(2, dat.film_id);
            stmt.executeUpdate();
        }
    }

    public void addArtist(List<Artist> artist, Film film){
        ArtistsTable table = new ArtistsTable();
        table.setFilm_id(film.getId());
        table.setArtists(artist);

        tables.add(table);
    }

    private class ArtistsTable{
        private int film_id;
        private List<Artist> artists;

        public int getFilm_id() {
            return film_id;
        }

        public List<Artist> getArtists() {
            return artists;
        }

        public void setArtists(List<Artist> artists) {
            this.artists = artists;
        }

        public void setFilm_id(int film_id) {
            this.film_id = film_id;
        }


    }

    public List<Artist> getArtistByFilmId(Film film){
        for(ArtistsTable dat : tables){
            if(film.getId() == dat.film_id){
                return dat.getArtists();
            }
        }
        return null;
    }

    public static final String select_query = "SELECT * FROM artists";
    public static final String insert_query = "INSERT INTO artists(Umelci, film_ID) VALUES (?, ?)";
}
