package FilmyProjekt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

public class ReviewsManager {

    public List<Review> getReviewsByFilmId(Connection conn, Film film) throws Exception {
        List<Review> hodnoceni = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement(select_query);
        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
           Review review;
           if(rs.getInt("ID") == film.getId()){
               if(film instanceof Played){
                   review = new Review();
               }

               else{
                   review = new Review(true);
               }
               review.setBody_filmy(rs.getInt("Body"));
               review.setHodnoceni_text(rs.getString("Hodnoceni"));
               review.setFilm_id(rs.getInt("films_ID"));
               hodnoceni.add(review);
           }
        }
        return hodnoceni;
    }

    public void saveDB(Connection conn, Film film) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(insert_query);
        for(Review dat : film.getReviews()){
            stmt.setString(1, dat.getHodnoceni_text());
            stmt.setInt(2, dat.getBody_filmy());
            stmt.setInt(3, dat.getFilm_id());
            stmt.executeUpdate();
        }
    }

    private static final String select_query = "SELECT * FROM reviews";
    private static final String insert_query = "INSERT INTO reviews(Hodnoceni, Body, films_ID) VALUES (?, ?, ?)";

}
