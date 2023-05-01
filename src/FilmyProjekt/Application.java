package FilmyProjekt;

import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    private JuAj ui;
    private FilmManager film_mng;
    private ArtistsManager art_mng;

    public Application(FilmManager film_mng, ArtistsManager art_mng){
        ui = new JuAj(film_mng.getFilms());
        this.film_mng = film_mng;
        this.art_mng = art_mng;
    }

    public void run() throws Exception {
        Scanner sc = new Scanner(System.in);
        int choice;

        boolean run = true;
        while(run) {

        System.out.println("Vítejte na SCFD! (Super česká filmová databáze)");
        System.out.println("Vyberte jednu z následujících možností: ");
        System.out.println("1. Přidání nového filmu");
        System.out.println("2. Upravení existujícího filmu");
        System.out.println("3. Smazání existujícího filmu");
        System.out.println("4. Přidání hodnocení danému filmu");
        System.out.println("5. Výpis všech filmů");
        System.out.println("6. Vyhledání filmu");
        System.out.println("7. Vypis hercu/animatoru podle filmu");
        System.out.println("8. Vypis vsech filmu podle herce/animatora");
        System.out.println("9. Uložení filmu");
        System.out.println("10. Načtení filmu");
        System.out.println("0. Ukončit\n");
        System.out.println("Váš výběr: ");

            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    ui.pridaniFilmu(art_mng);
                    break;
                case 2:
                    ui.upravaFilmu();
                    break;
                case 3:
                    ui.smazaniFilmu(art_mng, film_mng);
                    break;
                case 4:
                    ui.pridaniHodnoceni();
                    break;
                case 5:
                    ui.vypisFilmu();
                    break;
                case 6:
                    ui.vyhledaniFilmu();
                    break;
                case 7:
                    break;
                case 8:
                    ui.filmyPodleArtisty();
                    break;
                case 9:
                    ui.ulozeniFilmu();
                    break;
                case 10:
                    ui.nacteniFilmu();
                    break;
                case 0:
                    run = false;
                    break;
                default:
                    System.out.println("Nastala chyba! Opakujte akci...\n");
                    break;
            }
        }
    }

}
