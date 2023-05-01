package FilmyProjekt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuAj {

    Scanner sc = new Scanner(System.in);
    private List<Film> films;

    public JuAj(List<Film> films) {
        this.films = films;
    }


    public void pridaniFilmu(ArtistsManager art_mng) throws SQLException {
        Film film = null;
        System.out.println("1. Hrany film");
        System.out.println("2. Animovany film");
        int choice = intInput();
        if(choice == 1){
            film = new Played();
        }
        else if (choice == 2) {
            film = new Animated();
            print("Zadejte doporuceny vec:");
            ((Animated) film).setDoporuceny_vek(intInput());
        }
        else{
            print("Neplatny vyber!");
        }

        film.setId(films.lastIndexOf(film)+1);
        print("Zadejte nazev: ");
        film.setNazev(textInput());
        print("Zadejte rezisera: ");
        film.setReziser(textInput());
        print("Zadejte rok vyadni: ");
        film.setRok_vydani(intInput());

        List<Artist> actors = new ArrayList<>();
        boolean run = true;
        while(run){
            print("Zadejte umelce (X - pro ukonceni):");
            Artist artist = new Artist();
            String art_string = textInput();
            if(art_string.equals("X")){
                run = false;

            }
            else{
                artist.setJmeno(art_string);
                film.getArtists().add(artist);
                actors.add(artist);

            }
        }
        art_mng.addArtist(actors, film);
        films.add(film);
    }

   public void upravaFilmu() throws SQLException {
        print("Vyberte film, ktery chcete upravit: ");
        Film film = filmByName(textInput());
        if(film != null){
            if(film instanceof Played) {
                print("Chcete upravit:\n1) Nazev\n2) Rezisera\n3) Rok vydani");
                int edit = intInput();
                if(edit == 1){
                    print("Upravte nazev: ");
                    textInput();
                    film.setNazev(textInput());
                }
                else if(edit == 2) {

                } else if (edit == 3) {

                }
                else{
                    print("Nespravna volba!");
                }
            }
            else{
                print("Chcete upravit:\n1) Nazev\n2) Rezisera\n3) Rok vydani\n4) Vek");
            }
        }
        else{
            print("Zadany film se nepodarilo vyhledat!");
        }
   }
    public void smazaniFilmu(ArtistsManager art_mng, FilmManager film_mng) throws SQLException {
        print("Vyberte film, ktery chcete smazat");
        Film film = filmByName(textInput());
        if(film != null){
            films.remove(film.getId());
            art_mng.getTables().remove(film.getId());
        }
        else {
            print("Zadany film jsme nemohli najit");
        }

    }

   public void pridaniHodnoceni() throws Exception {
        Review review = new Review();
        print("Zadejte nazev filmu pro hodnoceni:");
        Film film = filmByName(textInput());
        if(film != null){
            print("Zadejte pocet bodu: ");;
            review.setBody_filmy(intInput());

            print("Zadejte hodnoceni (Stisknutim enter preskocite):");
            review.setHodnoceni_text(textInput());

            film.getReviews().add(review);
        }
        else{
            print("Zadany nazev nebyl nalezen!");
        }

   }


   public void vypisFilmu() throws SQLException {
       if(films.size() > 0) {
           for (Film film : films) {
               printFilm(film);
           }
       }
       else
           print("Zadne filmy v databazi");
   }

   public void filmyPodleArtisty(){
        print("Zadejte jmeno herce/animatora: ");
        String name = textInput();
        for(Film film : films){
            for(Artist artist : film.getArtists()){
                if(artist.getJmeno().equals(name)){
                    print(film.getNazev());
                    break;
                }
            }
        }
   }

   public void vyhledaniFilmu() throws SQLException {
        Film film;
        print("Zadejte nazev filmu, ktery chcete vyhledat: ");
        String text = textInput();
        film = filmByName(text);
        if(film != null) {
            printFilm(film);
        }
        else
            print("Nenalezli jsme zadany film!");
   }

   public void ulozeniFilmu(){

   }

   public void nacteniFilmu(){

   }

    private Film filmByName(String name){
        for(Film film : films){
            if(film.getNazev().equals(name)){
                return film;
            }
        }
        return null;
    }

   private String textInput(){
        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
   }

   private int intInput(){
        int integer = 0;
        try{
            Scanner sc = new Scanner(System.in);
            integer = sc.nextInt();

        } catch (Exception e) {
            print("Zadejte cislo: ");
            intInput();
        }
        return integer;
   }

   private void print(String vstup){
        System.out.println(vstup);
   }

    private void print(int vstup){
        System.out.println(vstup);
    }

   private void printFilm(Film film){
        print(film.getNazev());
        print(film.getReziser());
        print(film.getRok_vydani());

        for(Artist art : film.getArtists()){
            print(art.getJmeno());
        }

        for(Review rev : film.getReviews()){
            print(rev.getBody_filmy());
            print(rev.getHodnoceni_text());
        }

   }

}

