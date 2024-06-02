import csata.Csata;
import elokeszit.*;
import gep.GepValaszt;
import terkep.Elhelyez;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Elokeszit.nehezseg();
        System.out.println("Valasztott nehezseg: " + Elokeszit.getNehezseg());
        System.out.println("Jelenleg ennyi aranyad van: " + Elokeszit.getArany());

        Hos hos = new Hos();
        Hos gep = new Hos();

        Varazslat villamcsapas = new Varazslat("villamcsapas", 60, 5, 30, 0, 0);
        Varazslat tuzlabda = new Varazslat("tuzlabda", 120, 9, 20, 0, 0);
        Varazslat feltamasztas = new Varazslat("feltamasztas", 120,6,50,0, 0);
        Varazslat atok = new Varazslat("atok", 100, 6, 1,0, 0); //az AKTUALIS korben a kivalasztott mezon levo egysegek biztosan a minimumsebzesuket alkalmazzak (visszatamadas eseten is)
        Varazslat gyengites = new Varazslat("gyengites", 150, 10, 1,0, 0); //varazsero*15 sebzes + az AKTUALIS korben az ellenfel a kivalasztott mezon levo egyseggel 50%-kal kevesebbet sebez (visszatamadaskor is)

        Egyseg foldmuves = new Egyseg('F', "foldmuves", 2, 1, 1, 3,4, 8,0, 0, false);
        Egyseg ijasz = new Egyseg('I', "ijasz", 6, 2,4,7,4,9,0, 0, true);
        Egyseg griff = new Egyseg('G', "griff", 15,5,10,30,7,15,0, 0, false);
        Egyseg ogre = new Egyseg('O', "ogre", 40,2,6,60,3,2,0, 0, false); //elrettenti ellenfeleit, nem tamadnak vissza
        Egyseg lovag = new Egyseg('L', "lovag", 20,4,15,30,3,12,0, 0, false); //20%-kal kevesebbet sebeznek ellene az egysegek

        System.out.println("Jatekos hosenek tulajdonsagpontjai");
        tulajdonsagaink(hos);

        //Tulajdonsagpont
        double ar = 5;
        List<Integer> arak = new ArrayList<>();
        int tulDb = 0;
        int elkar = Elokeszit.getArany();

        while(elkar > 0){
            if(tulDb == 0){
                arak.add((int) ar);
                tulDb++;
            }else {
                ar = (int) Math.ceil(ar * 1.1);
                if(elkar - ar >= 0) {
                    elkar -= ar;
                    arak.add((int) ar);
                    tulDb++;
                } else{
                    break;
                }
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("Melyik tulajdonsagot szeretned erositeni? Ird be a sorszamat!");
        System.out.println("1: Tamadas");
        System.out.println("2: Vedekezes");
        System.out.println("3: Varazsero");
        System.out.println("4: Tudas");
        System.out.println("5: Moral");
        System.out.println("6: Szerencse");

        String be = sc.nextLine();

        int start = 0;
        while(!be.equals("0")){
            if(be.equals("1")){
                start = hos.vasarol(arak, "1", start);
            }
            if(be.equals("2")){
                start = hos.vasarol(arak, "2", start);
            }
            if(be.equals("3")){
                start = hos.vasarol(arak, "3", start);
            }
            if(be.equals("4")){
                start = hos.vasarol(arak, "4", start);
            }
            if(be.equals("5")){
                start = hos.vasarol(arak, "5", start);
            }
            if(be.equals("6")){
                start = hos.vasarol(arak, "6", start);
            }
            System.out.println("Maradt " + Elokeszit.getArany());
            be = sc.nextLine();
        }

        System.out.println("Jatekos hosenek tulajdonsagpontjai");
        tulajdonsagaink(hos);

        int arany;
        arany = GepValaszt.tulajdonsagpontVasar(gep);
        arany = GepValaszt.varazslatotVesz(arany, villamcsapas, tuzlabda, feltamasztas, atok, gyengites);
        arany = GepValaszt.egysegetVesz(arany, foldmuves, ijasz, griff, ogre, lovag);

        System.out.println("************* Vasarolj varazslatokat! Ird be a sorszamat!");
        System.out.println("1: Villamcsapas (" + villamcsapas.getAr() + " - " + villamcsapas.getManna() + " manna)");
        System.out.println("2: Tuzlabda (" + tuzlabda.getAr() + " - " + tuzlabda.getManna() + " manna)");
        System.out.println("3: Feltamasztas (" + feltamasztas.getAr() + " - " + feltamasztas.getManna() + " manna)");
        System.out.println("4: Atok (" + atok.getAr() + " - " + atok.getManna() + " manna)");
        System.out.println("5: Gyengites (" + gyengites.getAr() + " - " + gyengites.getManna() + " manna)");
        System.out.println("0: Vasarlas befejezese");

        be = sc.nextLine();

        while(!be.equals("0")){
            if(be.equals("1")){
                villamcsapas.varazslatVasarlas(villamcsapas);
            }
            else if(be.equals("2")){
                tuzlabda.varazslatVasarlas(tuzlabda);
            }
            else if(be.equals("3")){
                feltamasztas.varazslatVasarlas(feltamasztas);
            }
            else if(be.equals("4")){
                atok.varazslatVasarlas(atok);
            }
            else if(be.equals("5")){
                gyengites.varazslatVasarlas(gyengites);
            }
            be = sc.nextLine();
        }

        egysegKiir();

        Scanner sc2 = new Scanner(System.in);
        String be2 = sc2.nextLine();

        boolean kilephet = false;
        while(!kilephet){
            if(be2.equals("1")){
                foldmuves.egysegVasarlas(foldmuves);
            }
            else if(be2.equals("2")){
                ijasz.egysegVasarlas(ijasz);
            }
            else if(be2.equals("3")){
                griff.egysegVasarlas(griff);
            }
            else if(be2.equals("4")){
                ogre.egysegVasarlas(ogre);
            }
            else if(be2.equals("5")){
                lovag.egysegVasarlas(lovag);
            } else if(be2.equals("0")){
                if(foldmuves.getDarab() != 0 || ijasz.getDarab() != 0 || griff.getDarab() != 0 || ogre.getDarab() != 0 || lovag.getDarab() != 0){
                    break;
                } else{
                    System.out.println("Legalabb egy egyseget kell vasarolnod! Ird be a vasarolando egyseg sorszamat!");
                }
            }
            be2 = sc2.nextLine();
        }

        String[][] matrix = new String[10][12];

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 12; j++){
                matrix[i][j] = "-";
            }
        }
        Elhelyez.elhelyezes(matrix, foldmuves, ijasz, griff, ogre, lovag);


        System.out.println("Gep hosenek tulajdonsagpontjai");
        tulajdonsagaink(gep);

        if(villamcsapas.getDarabGep() > 0){
            System.out.println("Az ellenfelnek van villamcsapas varazslata!");
        }
        if(tuzlabda.getDarabGep() > 0){
            System.out.println("Az ellenfelnek van tuzlabda varazslata!");
        }
        if(feltamasztas.getDarabGep() > 0){
            System.out.println("Az ellenfelnek van feltamasztas varazslata!");
        }
        if(atok.getDarabGep() > 0){
            System.out.println("Az ellenfelnek van atok varazslata!");
        }
        if(gyengites.getDarabGep() > 0){
            System.out.println("Az ellenfelnek van gyengites varazslata!");
        }

        Csata.csata(matrix, hos, gep, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
    }

    public static void tulajdonsagaink(Hos hos) {
        System.out.print(System.lineSeparator());
        System.out.println("Tamadas: " + hos.getTamadas());
        System.out.println("Vedekezes: " + hos.getVedekezes());
        System.out.println("Varazsero: " + hos.getVarazsero());
        System.out.println("Tudas: " + hos.getTudas());
        System.out.println("Moral: " + hos.getMoral());
        System.out.println("Szerencse: " + hos.getSzerencse());
        System.out.print(System.lineSeparator());
    }

    public static void egysegKiir(){
        System.out.println("************* Vasarolj egysegeket! Ird be a sorszamat!");
        System.out.println("1: Foldmuves (2 arany)");
        System.out.println("2: Ijasz (6 arany)");
        System.out.println("3: Griff (15 arany)");
        System.out.println("4: Ogre (40 arany)");
        System.out.println("5: Lovag (20 arany)");
        System.out.println("0: Vasarlas befejezese");
    }
}
