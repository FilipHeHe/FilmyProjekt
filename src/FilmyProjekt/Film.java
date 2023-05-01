package FilmyProjekt;

import java.util.ArrayList;
import java.util.List;

public abstract class Film {

    private String nazev, reziser;
    private int rok_vydani, id;
    private List<Review> reviews = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private static int count = 0;

    public Film() {

    }

    public void setCount(int count) {
        Film.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public String getReziser() {
        return reziser;
    }

    public void setReziser(String reziser) {
        this.reziser = reziser;
    }

    public int getRok_vydani() {
        return rok_vydani;
    }

    public void setRok_vydani(int rok_vydani) {
        this.rok_vydani = rok_vydani;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
