package service;

import java.util.Scanner;

public class MenuApplication {

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("Menu :");
            System.out.println("1. Affichage de la filmographie d’un acteur donné");
            System.out.println("2. Affichage du casting d’un film donné");
            System.out.println("3. Affichage des films sortis entre 2 années données");
            System.out.println("4. Affichage des films communs à 2 acteurs/actrices donnés");
            System.out.println("5. Affichage des acteurs communs à 2 films donnés");
            System.out.println("6. Affichage des films sortis entre 2 années données et qui ont un acteur/actrice donné au casting");
            System.out.println("7. Fin de l’application");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                	System.out.print("Nom et prenom de l'acteur : ");
                    String actor = scanner.nextLine();
                	AccessData.getFilmFromActor(actor);
                    break;
                case 2:
                	System.out.print("Nom du film : ");
                    String movie = scanner.nextLine();
                	AccessData.getCastingFromFilm(movie);
                    break;
                case 3:
                	System.out.print("Année de début : ");
                    int year1 = scanner.nextInt();
                    System.out.print("Année de fin : ");
                    int year2 = scanner.nextInt();
                	AccessData.getFilmFromYears(year1, year2);
                    break;
                case 4:
                	System.out.print("Nom et prenom de l'acteur 1 : ");
                    String actor1 = scanner.nextLine();
                    System.out.print("Nom et prenom de l'acteur 2 : ");
                    String actor2 = scanner.nextLine();
                	AccessData.getCommonFilms(actor1, actor2);
                    break;
                case 5:
                	System.out.print("Nom du film 1 : ");
                    String movie1 = scanner.nextLine();
                    System.out.print("Nom du film 2 : ");
                    String movie2 = scanner.nextLine();
                	AccessData.getCommonActors(movie1, movie2);
                    break;
                case 6:
                	System.out.print("Année de début : ");
                    int yearBeg = scanner.nextInt();
                    System.out.print("Année de fin : ");
                    int yearEnd = scanner.nextInt();
                	System.out.print("Nom et prenom de l'acteur : ");
                    String actorInCommon = scanner.nextLine();
                	AccessData.getMoviesByYearAndActor(yearBeg, yearEnd, actorInCommon);
                    break;
                case 7:
                    System.out.println("Fin de l'application. Merci !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        } while (choix != 7);

        scanner.close();
    }
}
