package FilmyProjekt;

public class Review {
    private int body_filmy, film_id;
    private String hodnoceni_text;
    private final int max_body;

    public Review(){
        max_body = 5;
    }
    public Review(boolean if_true){
        max_body = 10;
    }

    public int getFilm_id() {
        return film_id;
    }

    public void setFilm_id(int film_id) {
        this.film_id = film_id;
    }

    public int getBody_filmy() {
        return body_filmy;
    }

    public void setBody_filmy(int body_filmy) throws Exception {
        if(body_filmy > max_body){throw new Exception("Cislo je mimo rozsah!");}
        else
            this.body_filmy = body_filmy;
    }

    public String getHodnoceni_text() {
        return hodnoceni_text;
    }

    public void setHodnoceni_text(String hodnoceni_text) {
        this.hodnoceni_text = hodnoceni_text;
    }
}