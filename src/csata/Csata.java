package csata;

import elokeszit.*;
import java.util.Scanner;

import static terkep.Elhelyez.terkep;

/**
 * Itt hatarozodik meg, hogy melyik egyseg kovetkezik, valamint hivja meg az adott egysegre a megfelelo metodusokat. A jatekos egysegeinek tevekenysegeit is az osztalyon beluli metodusok valositjak meg
 */
public class Csata {
    /**
     * Meghatarozza, hogy melyik egyseg kovetkezik, majd annak megfeleloen hivja meg a metodusokat (jatekos egyseg eseten bekeri a tevekenyseg sorszamat, gepi egyseg eseten meghivja a GepCsata osztaly metodusat)
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param hos a jatekos hose
     * @param gep a gep hose
     * @param villamcsapas varazslat
     * @param tuzlabda varazslat
     * @param feltamasztas varazslat
     * @param atok varazslat
     * @param gyengites varazslat
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void csata(String[][] matrix, Hos hos, Hos gep, Varazslat villamcsapas, Varazslat tuzlabda, Varazslat feltamasztas, Varazslat atok, Varazslat gyengites, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        System.out.println(System.lineSeparator() +"******************************");
        System.out.println("Csata elotti terkep");
        terkep(matrix);

        System.out.println("******************************" + System.lineSeparator());
        int kor = 1;

        String[][] hosM = new String[5][2];
        String[][] gepM = new String[5][2];
        String[][] sorrendM = new String[10][2]; //vegso sorrend, matrixban gep egysegei *-gal megjelolve
        String[] sorrend = new String[10]; //max 10 elem lehet

        hosM[0][0] = "F";
        hosM[0][1] = String.valueOf(hos.getMoral() + foldmuves.getKezdemenyezes());
        hosM[1][0] = "I";
        hosM[1][1] = String.valueOf(hos.getMoral() + ijasz.getKezdemenyezes());
        hosM[2][0] = "G";
        hosM[2][1] = String.valueOf(hos.getMoral() + griff.getKezdemenyezes());
        hosM[3][0] = "O";
        hosM[3][1] = String.valueOf(hos.getMoral() + ogre.getKezdemenyezes());
        hosM[4][0] = "L";
        hosM[4][1] = String.valueOf(hos.getMoral() + lovag.getKezdemenyezes());

        gepM[0][0] = "F*";
        gepM[0][1] = String.valueOf(gep.getMoral() + foldmuves.getKezdemenyezes());
        gepM[1][0] = "I*";
        gepM[1][1] = String.valueOf(gep.getMoral() + ijasz.getKezdemenyezes());
        gepM[2][0] = "G*";
        gepM[2][1] = String.valueOf(gep.getMoral() + griff.getKezdemenyezes());
        gepM[3][0] = "O*";
        gepM[3][1] = String.valueOf(gep.getMoral() + ogre.getKezdemenyezes());
        gepM[4][0] = "L*";
        gepM[4][1] = String.valueOf(gep.getMoral() + lovag.getKezdemenyezes());

        boolean folytatodik = vanEloEgyseg(foldmuves, ijasz, griff, ogre, lovag); //folytatodik a csata addig, mig mindket felnek van legalabb 1 elo egysege
        int egysegSorszam = 0; //hanyadik egyseg jon a korben

        sorrend(sorrend, kor, hosM, gepM, sorrendM, foldmuves, ijasz, griff, ogre, lovag);
        boolean hosHasznalt = false; //az adott korben volt-e mar a hos hasznalva
        int manna = hos.getTudas()*10;

        boolean gepHasznalt = false;
        int mannaGep = gep.getTudas()*10;

        while (folytatodik) {
            boolean mostVoltHos = false; //gep hos
            String aktualis = sorrend[egysegSorszam];
            Egyseg egyseg = foldmuves; //valtozik az inputtol megfeleloen
            if(aktualis != null && !aktualis.contains("*")){
                switch(aktualis){
                    case "I": egyseg = ijasz; break;
                    case "G": egyseg = griff; break;
                    case "O": egyseg = ogre; break;
                    case "L": egyseg = lovag; break;
                }
            } else if(aktualis != null && aktualis.contains("*")){
                switch(aktualis){
                    case "I*": egyseg = ijasz; break;
                    case "G*": egyseg = griff; break;
                    case "O*": egyseg = ogre; break;
                    case "L*": egyseg = lovag; break;
                }
            }

            if (aktualis != null && aktualis.length() == 1) { //jatekos egysege kovetkezik
                if(egyseg.getDarab() > 0) {
                    terkep(matrix);
                    String az = String.valueOf(egyseg.getId()) + egyseg.getDarab();
                    System.out.println(System.lineSeparator() + egyseg.getNev().toUpperCase() + " egyseg kovetkezik!");
                    System.out.println("Cselekves kivalasztasahoz ird be a sorszamot!");
                    System.out.println("1: Egyseg hasznalata - varakozas");
                    System.out.println("2: Egyseg hasznalata - mozgas");
                    System.out.println("3: Egyseg hasznalata - tamadas");
                    if (!hosHasznalt) {
                        System.out.println("4: Hos hasznalata - tamadas");
                        System.out.println("5: Hos hasznalata - varazslas");
                    }

                    Scanner be = new Scanner(System.in);
                    int a = Integer.parseInt(be.next());

                    switch (a) {
                        case 1:
                            egysegVarakozik();
                            break;
                        case 2:
                            egysegMozog(matrix, az, egyseg);
                            break;
                        case 3: {
                            if (!egyseg.getNev().equals("ijasz")) { //ha nem ijasz, akkor tud tamadni, ha szomszedban van legalabb egy ellenfel
                                int[] ko = egysegHolVan(matrix, egyseg);
                                int x = ko[0];
                                int y = ko[1];
                                boolean vanEllenfel = false;

                                for (int i = -1; i <= 1; i++) {
                                    for (int j = -1; j <= 1; j++) {
                                        if (x - i >= 0 && x - i < 10 && y - j >= 0 && y - j < 12 && !vanEllenfel) {
                                            if (matrix[x - i][y - j].contains("*")) {
                                                vanEllenfel = true;
                                                egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
                                            }
                                        }
                                    }
                                }

                                if (!vanEllenfel) {
                                    System.out.println("Nincs tamadhato celpont a kozelben, ezert az egyseg kimarad");
                                }
                            } else {
                                egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
                            }
                        }
                        break;
                        case 4: {
                            if (!hosHasznalt) {
                                hosTamad(hos, matrix, foldmuves, ijasz, griff, ogre, lovag);
                                hosHasznalt = true;
                            } else {
                                System.out.println("A hosodet ebben a korben mar hasznaltad! Irj be egy masik sorszamot (1-3)");
                            }
                            egysegSorszam--; //ujra ugyanaz az egyseg lephet a kovetkezo korben
                        }
                        break;
                        case 5: {
                            if (!hosHasznalt) {
                                manna = hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                                hosHasznalt = true;
                            } else {
                                System.out.println("A hosodet ebben a korben mar hasznaltad! Irj be egy masik sorszamot (1-3)");
                            }
                            egysegSorszam--;
                        }
                        break;
                        default:
                            System.out.println("1 es 5 kozotti szamot adj meg!");
                    }
                }
            } else{ //gep egysege kovetkezik
                if(aktualis != null && egyseg.getDarabGep() > 0) {
                    String[] vissza = GepCsata.gepEgyseg(matrix, hos, gep, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag, egyseg, gepHasznalt, mannaGep);
                    mannaGep = Integer.parseInt(vissza[0]);
                    if(vissza[1].equals("true")){
                        gepHasznalt = true;
                    } else{
                        gepHasznalt = false;
                    }
                    if(vissza[2].equals("true")){
                        mostVoltHos = true;
                    }
                    if(mostVoltHos){
                        egysegSorszam--;
                    }
                }
            }

            if(!mostVoltHos && aktualis != null && aktualis.contains("*")) {
                System.out.println(System.lineSeparator() + "ELLENFEL " + egyseg.getNev().toUpperCase() + " VEGZETT" + System.lineSeparator());
            }
            folytatodik = vanEloEgyseg(foldmuves, ijasz, griff, ogre, lovag);
            if(egysegSorszam < 9){
                egysegSorszam++;
            } else{ //ha mindegyik egyseg lepett a korben, akkor ujra meghivjuk a sorrendet (lehetnek halott egysegek), elkezdodik a kovetkezo kor
                egysegSorszam = 0;
                kor++;

                korNullaz(foldmuves, ijasz, griff, ogre, lovag);
                gepHasznalt = false;
                hosHasznalt = false;
                sorrend(sorrend, kor, hosM, gepM, sorrendM, foldmuves, ijasz, griff, ogre, lovag);
            }
        }

        terkep(matrix);

        System.out.println("A JATEK VEGET ERT!");
        String gyoztes = gyozott(foldmuves, ijasz, griff, ogre, lovag);
        System.out.println(gyoztes);

    }

    /**
     * A jatekos hosenek tamadasat valositja meg a metodus
     * @param hos a jatekos hose
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void hosTamad(Hos hos, String[][] matrix, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        System.out.println("AKTUALIS TERKEP");
        terkep(matrix);

        System.out.println(System.lineSeparator());
        System.out.println("Ird be azt a koordinatat, amelyiken levo egyseget meg szeretned a hosoddel tamadni!");

        int[] ko = koordinata();
        int x = ko[0];
        int y = ko[1];

        String azonosito = "F"; //modosul

        if(!matrix[x][y].contains("*")){
            System.out.println("Itt nincs ellenseges egyseg! Probald meg ujra");
            hosTamad(hos, matrix, foldmuves, ijasz, griff, ogre, lovag);
        } else{
            azonosito = matrix[x][y];
        }

        Egyseg egyseg = egysegKivalasztas(azonosito, foldmuves, ijasz, griff, ogre, lovag);

        String teljesAzonosito = egyseg.getId() + "" + egyseg.getDarabGep() + "*";

        int eletero = egyseg.getEleteroGepOsszesen() - hos.getTamadas() * 10;

        gepetSebez(eletero, egyseg);

        System.out.print("Az ellenseges " + egyseg.getNev() + " egysegre " + hos.getTamadas()*10 + " sebzest mertel! ");

        if(egyseg.getDarabGep() > 0){
            System.out.println("Meg eletben maradt " + egyseg.getDarabGep() + " " + egyseg.getNev() + ".");
        } else{
            System.out.println("Az osszes ellenseges egyseg meghalt a tamadastol!");
        }


        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 12; j++){
                if(matrix[i][j].equals(teljesAzonosito)){
                    if(egyseg.getDarabGep() > 0){
                        matrix[i][j] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                    } else{
                        matrix[i][j] = "-";
                    }
                }
            }
        }

    }

    /**
     * Megvalosul a jatekos egyseg gepi egyseg megtamadasa
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param x a megtamadott egyseg x koordinataja
     * @param y a megtamadott egyseg y koordinataja
     * @param x_forras a sajat egysegunk x koordinataja
     * @param y_forras a sajat egysegunk y koordinataja
     * @param p1 a jatekos hose
     * @param p2 a gep hose
     * @param tamado a tamado egyseg
     * @param tamadott a megtamadott egyseg
     */
    public static void egysegTamad2(String[][] matrix, int x, int y, int x_forras, int y_forras, Hos p1, Hos p2, Egyseg tamado, Egyseg tamadott){
        //JATEKOS --> GEP
        int ertek;
        if(!tamado.isAtok()) {
            int min = tamado.getMinSebzes();
            int max = tamado.getMaxSebzes();
            ertek = (int) ((Math.random() * (max - min)) + min);
        } else{
            ertek = tamado.getMinSebzes();
            System.out.println("Az egyseget atok sujtja, minimum sebzeset okozza!");
        }
        double alapsebzes = ertek * tamado.getDarab();
        alapsebzes *= 1 + (((double) p1.getTamadas()) / 10);
        alapsebzes *= 1 - (((double) p2.getVedekezes()) * 0.05);

        int sebzes = (int) Math.round(alapsebzes);
        int szerencse = (int) ((Math.random() * (99)) + 1);

        if(szerencse <= p1.getSzerencse()*5){
            sebzes *= 2;
            System.out.println("Kritikus talalat!");

        }


        if(tamadott.getId() == 'L'){ //lovag specialis kepesseg
            sebzes *= 1-0.2;
            System.out.println("Lovag specialis tulajdonsag: 20% kevesebb elszenvedett sebzes");
        }

        if(tamado.isGyengites()){ //gyengites varazslat
            sebzes *= 0.5;
            System.out.println("Gyengites varazslat miatt 50%-kal kevesebbet sebez az egyseg");
        }

        int tamadasUtan = tamadott.getEleteroGepOsszesen() - sebzes;

        gepetSebez(tamadasUtan, tamadott);
        int eletben = tamadott.getDarabGep();

        if(eletben > 0){
            tamadott.setDarabGep(eletben);
            System.out.println("Eletben maradt " + eletben + " db " + tamadott.getNev() + " egyseg!");
            matrix[x][y] = tamadott.getId() + "" + eletben + "*";
            System.out.println("VISSZATAMADAS");
            if(tamado.getId() != 'O') {
                if(!tamado.isTavolsagi()) {
                    if (!tamadott.isVisszatamadottGep()) {
                        if (tamadott.getId() != 'G') { //griff vegtelenszer visszatamadhat
                            tamadott.setVisszatamadottGep(true);
                        }
                        gepVisszatamad(matrix, x_forras, y_forras, p1, p2, tamadott, tamado);
                    }
                } else{
                    System.out.println("Az egyseg tavol van a visszatamadashoz!");
                }
            } else{
                System.out.println("Az ellenfel elrettentve: az ogrek ellen nem tamadnak vissza!");
            }
        } else{
            tamadott.setDarabGep(0);
            System.out.println("A megtamadott egysegek meghaltak!");
            matrix[x][y] = "-";
        }

    }

    /**
     * A tamadas utan a gep egysegenek visszatamadasa valosul meg
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param x a jatekos egysegenek x koordinata
     * @param y a jatekos egysegenek y koordinataja
     * @param p1 a jatekos hose
     * @param p2 a gep hose
     * @param tamado a tamado (gepi) egyseg
     * @param tamadott a tamadott (jatekos) egyseg
     */
    public static void gepVisszatamad(String[][] matrix, int x, int y, Hos p1, Hos p2, Egyseg tamado, Egyseg tamadott) {
        //GEP VISSZATAMAD --> JATEKOS

        int ertek;
        if(!tamado.isAtok()) {
            int min = tamado.getMinSebzes();
            int max = tamado.getMaxSebzes();
            ertek = (int) ((Math.random() * (max - min)) + min);
        } else{
            ertek = tamado.getMinSebzes();
            System.out.println("Az egyseget atok sujtja, minimum sebzeset okozza!");
        }

        double alapsebzes = ertek * tamado.getDarabGep();
        alapsebzes *= 1 + (((double) p2.getTamadas()) / 10);
        alapsebzes *= 1 - (((double) p1.getVedekezes()) * 0.05);

        int sebzes = (int) Math.round(alapsebzes);
        int szerencse = (int) ((Math.random() * (99)) + 1);

        if(szerencse <= p2.getSzerencse()*5){
            sebzes *= 2;
            System.out.println("Kritikus talalat!");
        }

        sebzes /= 2; //visszatamadas
        if(tamado.isGyengitesGep()){ //gyengites varazslat
            sebzes *= 0.5;
            System.out.println("Gyengites varazslat miatt 50%-kal kevesebbet sebez az egyseg");
        }

        int tamadasUtan = tamadott.getEleteroOsszesen() - sebzes;
        int eletben = 0;

        if(tamadasUtan > 0) {
            eletben = (int) Math.floor((double)tamadasUtan / tamadott.getEletEro());

            if (tamadasUtan > eletben * tamadott.getEletEro()) {
                eletben++;
            }
        }
        if(tamadasUtan < 0){
            tamadasUtan = 0;
        }

        tamadott.setEleteroOsszesen(tamadasUtan);

        if(eletben > 0){
            tamadott.setDarab(eletben);
            System.out.println("A visszatamadas utan eletben maradt " + eletben + " db " + tamadott.getNev() + " egyseged!");
            matrix[x][y] = tamadott.getId() + "" + eletben;
        } else{
            tamadott.setDarab(0);
            System.out.println("Az egysegeid meghaltak!");
            matrix[x][y] = "-";
        }
    }

    /**
     * a jatekos ezen metodus segitsegevel valaszthatja ki, melyik varazslatot akarja hasznalni
     * @param hos a jatekos hose
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param manna a jatekos aktualis mannaja
     * @param villamcsapas varazslat
     * @param tuzlabda varazslat
     * @param feltamasztas varazslat
     * @param atok varazslat
     * @param gyengites varazslat
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return a megmaradt manna mennyiseget adja vissza
     */

    public static int hosVarazsol(Hos hos, String[][] matrix, int manna, Varazslat villamcsapas, Varazslat tuzlabda, Varazslat feltamasztas, Varazslat atok, Varazslat gyengites, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {

        if(villamcsapas.getDarab() > 0 || tuzlabda.getDarab() > 0 || feltamasztas.getDarab() > 0 || atok.getDarab() > 0 || gyengites.getDarab() > 0) {

            System.out.println("AKTUALIS TERKEP");
            terkep(matrix);
            System.out.println("Melyik varazslatot hasznalod?");
            System.out.println("1: Villamcsapas [" + (villamcsapas.getDarab() > 0 ? "van]" : "nincs]"));
            System.out.println("2: Tuzlabda [" + (tuzlabda.getDarab() > 0 ? "van]" : "nincs]"));
            System.out.println("3: Feltamasztas [" + (feltamasztas.getDarab() > 0 ? "van]" : "nincs]"));
            System.out.println("4: Atok [" + (atok.getDarab() > 0 ? "van]" : "nincs]"));
            System.out.println("5: Gyengites [" + (gyengites.getDarab() > 0 ? "van]" : "nincs]"));

            Scanner be = new Scanner(System.in);
            String v = be.next();

            if (v.equals("1")) {
                if (villamcsapas.getDarab() > 0 && villamcsapas.getManna() <= manna) {
                    manna -= villamcsapas.getManna();
                    hosVarazsolMegvalosit(hos, matrix, villamcsapas, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    if (villamcsapas.getDarab() == 0) {
                        System.out.println("Nincs ilyen varazslatod!");
                        hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                    } else {
                        System.out.println("Nincs eleg mannad! " + manna + "/" + villamcsapas.getManna());
                    }
                }
            } else if (v.equals("2")) {
                if (tuzlabda.getDarab() > 0 && tuzlabda.getManna() <= manna) {
                    manna -= tuzlabda.getManna();
                    hosVarazsolMegvalosit(hos, matrix, tuzlabda, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    if (tuzlabda.getDarab() == 0) {
                        System.out.println("Nincs ilyen varazslatod!");
                        hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                    } else {
                        System.out.println("Nincs eleg mannad! " + manna + "/" + tuzlabda.getManna());
                    }
                }
            } else if (v.equals("3")) {
                if (feltamasztas.getDarab() > 0 && feltamasztas.getManna() <= manna) {
                    manna -= feltamasztas.getManna();
                    hosVarazsolMegvalosit(hos, matrix, feltamasztas, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    if (feltamasztas.getDarab() == 0) {
                        System.out.println("Nincs ilyen varazslatod!");
                        hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                    } else {
                        System.out.println("Nincs eleg mannad! " + manna + "/" + feltamasztas.getManna());
                    }
                }
            } else if (v.equals("4")) {
                if (atok.getDarab() > 0 && atok.getManna() <= manna) {
                    manna -= atok.getManna();
                    hosVarazsolMegvalosit(hos, matrix, atok, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    if (atok.getDarab() == 0) {
                        System.out.println("Nincs ilyen varazslatod!");
                        hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                    } else {
                        System.out.println("Nincs eleg mannad! " + manna + "/" + atok.getManna());
                    }
                }
            } else if (v.equals("5")) {
                if (gyengites.getDarab() > 0 && gyengites.getManna() <= manna) {
                    manna -= gyengites.getManna();
                    hosVarazsolMegvalosit(hos, matrix, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    if (gyengites.getDarab() == 0) {
                        System.out.println("Nincs ilyen varazslatod!");
                        hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
                    } else {
                        System.out.println("Nincs eleg mannad! " + manna + "/" + gyengites.getManna());
                    }
                }
            } else {
                System.out.println("1 és 5 kozott irj be szamot!");
                hosVarazsol(hos, matrix, manna, villamcsapas, tuzlabda, feltamasztas, atok, gyengites, foldmuves, ijasz, griff, ogre, lovag);
            }
            return manna;
        } else{
            System.out.println("Nincs varázslatod!");
            return manna;
        }
    }

    /**
     * a jatekos altal kivalasztott varazslat konkret megvalositasa
     * @param hos a jatekos hose
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param varazslat egyseg
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void hosVarazsolMegvalosit(Hos hos, String[][] matrix, Varazslat varazslat, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        System.out.println("Ird be azt a koordinatat, ahol az egyseg(ek)re alkalmazni szeretned a varazslatot");

        String az;

        int[] ko = koordinata();
        int x = ko[0];
        int y = ko[1];

        az = matrix[x][y];
        if(!az.equals("-")){

            Egyseg egyseg = egysegKivalasztas(az, foldmuves, ijasz, griff, ogre, lovag);

            if(varazslat.getNev().equals("villamcsapas")){
                if(az.contains("*")) {
                    int varazslatUtan = egyseg.getEleteroGepOsszesen() - hos.getVarazsero() * 30;
                    System.out.println("Villamcsapas " + hos.getVarazsero() * 30 + " sebzest okozott a(z) " + egyseg.getNev() + " egysegnek!");
                    if (varazslatUtan > 0) {
                        gepetSebez(varazslatUtan, egyseg);
                        System.out.println("Eletben maradt: " + egyseg.getDarabGep() + " egyseg");
                        matrix[x][y] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                    } else {
                        System.out.println("Az osszes egyseg meghalt a villamcsapasban!");
                        egyseg.setEleteroGepOsszesen(0);
                        egyseg.setDarabGep(0);
                        matrix[x][y] = "-";
                    }
                } else{
                    System.out.println("Csak ellenseges egyre alkalmazhato a villamcsapas!");
                    hosVarazsolMegvalosit(hos, matrix, varazslat, foldmuves, ijasz, griff, ogre, lovag);
                }
            }

            if(varazslat.getNev().equals("tuzlabda")){
                String aktualis;

                for(int i = -1; i <= 1; i++){
                    for(int j = -1; j <= 1; j++){
                        if(x-i >= 0 && x-i < 10 && y-j >= 0 && y-j < 12){
                            if(!matrix[x-i][y-j].equals("-")){
                                aktualis = matrix[x-i][y-j];

                                egyseg = egysegKivalasztas(aktualis, foldmuves, ijasz, griff, ogre, lovag);

                                boolean gepEgyseg;
                                if(aktualis.contains("*")){
                                    gepEgyseg = true;
                                } else{
                                    gepEgyseg = false;
                                }

                                int varazslatUtan;

                                if(gepEgyseg) {
                                    varazslatUtan = egyseg.getEleteroGepOsszesen() - hos.getVarazsero() * 20;
                                } else{
                                    varazslatUtan = egyseg.getEleteroOsszesen() - hos.getVarazsero() * 20;
                                }

                                if(varazslatUtan > 0){
                                    if(gepEgyseg) {
                                        egyseg.setEleteroGepOsszesen(varazslatUtan);
                                        int eletben = 0;

                                        eletben = (int) Math.floor((double) varazslatUtan / egyseg.getEletEro());

                                        if (varazslatUtan > eletben * egyseg.getEletEro()) {
                                            eletben++;
                                        }

                                        egyseg.setDarabGep(eletben);
                                        System.out.println("Eletben maradt " + egyseg.getDarabGep() + " ellenfel "+ egyseg.getNev()+" egyseg!");
                                        matrix[x - i][y - j] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                                    } else{
                                        egyseg.setEleteroOsszesen(varazslatUtan);
                                        int eletben = 0;

                                        eletben = (int) Math.floor((double) varazslatUtan / egyseg.getEletEro());

                                        if (varazslatUtan > eletben * egyseg.getEletEro()) {
                                            eletben++;
                                        }

                                        egyseg.setDarab(eletben);
                                        System.out.println("Eletben maradt " + egyseg.getDarab() + " sajat "+ egyseg.getNev()+" egyseg!");
                                        matrix[x - i][y - j] = egyseg.getId() + "" + egyseg.getDarab();
                                    }
                                } else{
                                    if(gepEgyseg) {
                                        System.out.println("Az osszes " + egyseg.getNev() + " ellenfel egyseg megsemmisult!");
                                        egyseg.setEleteroGepOsszesen(0);
                                        egyseg.setDarabGep(0);
                                    } else{
                                        System.out.println("Az osszes " + egyseg.getNev() + " sajat egyseg megsemmisult!");
                                        egyseg.setEleteroOsszesen(0);
                                        egyseg.setDarab(0);
                                    }
                                    matrix[x - i][y - j] = "-";
                                }
                            }
                        }
                    }
                }
            }

            if(varazslat.getNev().equals("feltamasztas")){
                System.out.println("Ird be azt a koordinatat, ahol az egyseg(ek)re alkalmazni szeretned a varazslatot");

                if(!matrix[x][y].contains("*")){
                    if(egyseg.getEredetiDb() > egyseg.getDarab()){
                        int gyogyitasUtan = egyseg.getEleteroOsszesen() + hos.getVarazsero() * 50;
                        if(gyogyitasUtan > egyseg.getEredetiDb() * egyseg.getEletEro()){
                            egyseg.setEleteroOsszesen(egyseg.getEredetiDb() * egyseg.getEletEro());
                            egyseg.setDarab(egyseg.getEredetiDb());
                        } else{
                            egyseg.setEleteroOsszesen(gyogyitasUtan);
                            int eletben = (int) Math.floor((double)egyseg.getEleteroOsszesen() / egyseg.getEletEro());

                            if (egyseg.getEleteroOsszesen() > eletben * egyseg.getEletEro()) {
                                eletben++;
                            }
                            egyseg.setDarab(eletben);
                        }
                        matrix[x][y] = egyseg.getId() + "" + egyseg.getDarab();
                        System.out.println("A feltamasztas utan " + egyseg.getDarab() + " darab " + egyseg.getNev() + " egyseged el!");
                    } else{
                        System.out.println("Az eredeti darabszamu " + egyseg.getNev() + " egyseged van. Mind teljesen ep, ezert nem gyogyithato!");
                    }
                } else{
                    System.out.println("Ellenfel egyseget nem gyogyithatod!");
                }
            }

            if(varazslat.getNev().equals("atok")){
                if(matrix[x][y].contains("*")){
                    int sebzesUtan = egyseg.getEleteroGepOsszesen() - hos.getVarazsero() * 15;
                    if(sebzesUtan > 0){
                        gepetSebez(sebzesUtan, egyseg);
                        System.out.println("ATOK: Eletben maradt: " + egyseg.getDarabGep() + " egyseg, a kovetkezo alkalommal minimumsebzesuket alkalmazzak");
                        matrix[x][y] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                        egyseg.setAtokGep(true);
                    } else{
                        System.out.println("Az osszes egyseg meghalt a sebzesben!");
                        egyseg.setEleteroGepOsszesen(0);
                        egyseg.setDarabGep(0);
                        matrix[x][y] = "-";
                    }
                } else{
                    System.out.println("Sajat egysegre nem alkalmazhatod!");
                    hosVarazsolMegvalosit(hos, matrix, varazslat, foldmuves, ijasz, griff, ogre, lovag);
                }
            }

            if(varazslat.getNev().equals("gyengites")){
                if(matrix[x][y].contains("*")){
                    System.out.println("GYENGITES: Az aktualis korben az ellenfel ezen egysege meg van gyengitve, igy 50%-kal kevesebbet sebez (visszatamadaskor is)");
                    egyseg.setGyengitesGep(true);
                } else{
                    System.out.println("Sajat egysegre nem alkalmazhatod!");
                    hosVarazsolMegvalosit(hos, matrix, varazslat, foldmuves, ijasz, griff, ogre, lovag);
                }
            }

        } else{
            System.out.println("Ezen a koordinatan nincs egyseg!");
            hosVarazsolMegvalosit(hos, matrix, varazslat, foldmuves, ijasz, griff, ogre, lovag);
        }
    }

    /**
     * az egyseg varakozasat valositja meg
     */
    public static void egysegVarakozik() {
        System.out.println("Az egyseg kimaradt a korbol!");
    }

    /**
     * A jatekos altal inditott mozgast valositja meg, meghivja a koordinatara az egyseg osztalybali egysegMozog metodust
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param az az egyseg azonositoja
     * @param egyseg a mozgatni kivant egyseg
     */
    public static void egysegMozog(String[][] matrix, String az, Egyseg egyseg) {
        System.out.println(System.lineSeparator() + "AKTUALIS TERKEP");
        terkep(matrix);
        System.out.println("Aktualis jatekos egyseg: " + az);

        System.out.println(System.lineSeparator());
        System.out.println("Ird be azt a koordinatat, ahova lepni szeretnel (pl. A4)");
        int[] ko = koordinata();
        int x = ko[0];
        int y = ko[1];

        egyseg.egysegMozog(egyseg, matrix, false, x, y);

    }

    /**
     * Kivalasztja a felhasznalo altal megtamadni akart egyseget, s megallapitja a koordinatak, illetve az egyseg megtamadasanak helyesseget
     * @param hos jatekos hose
     * @param gep gep hose
     * @param az az egyseg azonositoja
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void egysegTamad(Hos hos, Hos gep, String az, String[][] matrix, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        Egyseg egyseg = egysegKivalasztas(az, foldmuves, ijasz, griff, ogre, lovag);

        System.out.println("Melyik koordinatan levo egyseget szeretned megtamadni a(z) " + egyseg.getDarab() + " db " + egyseg.getNev() + " " + (egyseg.isTavolsagi() ? "(tavolsagi)" : "(kozelharci)") + " egysegeddel?" + System.lineSeparator() + "Ird be a koordinatat (pl. C4)");

        int[] ko = koordinata();
        int x = ko[0];
        int y = ko[1];

        int x_forras = 0;
        int y_forras = 0;

        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 12; k++) {
                if(matrix[j][k].equals(az)){
                    x_forras = j;
                    y_forras = k;
                }
            }
        }

        if(!egyseg.isTavolsagi()) {
            if (Math.abs(x_forras - x) > 1 || Math.abs(y_forras - y) > 1) {
                System.out.println("A megadott koordinata nem szomszedos! Adj meg egy szomszedos koordinatat!");
                egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
            } else if(matrix[x][y].equals("-")) {
                System.out.println("A megadott koordinatan nincs ellenseges egyseg!");
                egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
            } else if(!matrix[x][y].contains("*")){
                System.out.println("A sajat egysegedet nem tamadhatod meg!");
                egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
            }
        }

        az = matrix[x][y];
        Egyseg ellenfel = egysegKivalasztas(az, foldmuves, ijasz, griff, ogre, lovag);


        if(egyseg.isTavolsagi()){
            int ellenfelSzomszedban = 0;
            for(int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x_forras - i >= 0 && x_forras - i < 10 && y_forras - j >= 0 && y_forras - j < 12) {
                        if(matrix[x_forras-i][y_forras-j].contains("*")){
                           ellenfelSzomszedban++;
                        }
                    }
                }
            }
            if(ellenfelSzomszedban == 0){
                if(matrix[x][y].equals("-")) {
                    System.out.println("A megadott koordinatan nincs ellenseges egyseg!");
                    egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
                } else if(!matrix[x][y].contains("*")){
                    System.out.println("A sajat egysegedet nem tamadhatod meg!");
                    egysegTamad(hos, gep, az, matrix, foldmuves, ijasz, griff, ogre, lovag);
                } else {
                    egysegTamad2(matrix, x, y, x_forras, y_forras, hos, gep, egyseg, ellenfel);
                }
            } else{
                System.out.println("A szomszedban van egy ellenseges egyseg, ezert nem tud tamadni az " + egyseg.getNev() + ". Az egyseg kimarad.");
                egysegVarakozik();
            }
        } else {
            egysegTamad2(matrix, x, y, x_forras, y_forras, hos, gep, egyseg, ellenfel);
        }
    }

    /**
     * meghatarozza kezdemenyezes es moral alapjan a gepi es jatekos egysegek sorrendjet
     * @param sorrend a feltoltendo/modositando tomb, csak azokat a sorrendM tombbeli egysegadatokat tartalmazzak, amibol van legalabb 1 db
     * @param kor az aktualis kor sorszama
     * @param hosM  tomb, melybe a hos egysegeinek kezdemenyezeset rakjuk sorba
     * @param gepM tomb, melybe a gep egysegeinek kezdemenyezeset rakjuk sorba
     * @param sorrendM tomb, mely a hosM es gepM adatai vannak sorbarendezve
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void sorrend(String[] sorrend, int kor, String[][] hosM, String[][] gepM, String[][] sorrendM, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        System.out.println("*************************" + System.lineSeparator() + kor + ". KOR");

        for(int i = 0; i<sorrend.length; i++){
            sorrend[i] = null;
        }

        String tmp;
        String tmp2;
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) { //hos kezdemenyezeseinek sorrendbe rakasa
                if (Integer.parseInt(hosM[i][1]) < Integer.parseInt(hosM[i + 1][1])) {
                    tmp = hosM[i][0];
                    tmp2 = hosM[i][1];
                    hosM[i][0] = hosM[i + 1][0];
                    hosM[i][1] = hosM[i + 1][1];
                    hosM[i + 1][0] = tmp;
                    hosM[i + 1][1] = tmp2;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) { //gep kezdemenyezeseinek sorrendbe rakasa
                if (Integer.parseInt(gepM[j][1]) < Integer.parseInt(gepM[j + 1][1])) {
                    tmp = gepM[j][0];
                    tmp2 = gepM[j][1];
                    gepM[j][0] = gepM[j + 1][0];
                    gepM[j][1] = gepM[j + 1][1];
                    gepM[j + 1][0] = tmp;
                    gepM[j + 1][1] = tmp2;
                }
            }
        }

        for (int i = 0; i < 5; i++){ //hos kezdemenyezeseinek sorrendbe rakasa
            sorrendM[i][0] = hosM[i][0];
            sorrendM[i][1] = hosM[i][1];
        }

        for (int i = 0; i < 5; i++){ //hos kezdemenyezeseinek sorrendbe rakasa
            sorrendM[i+5][0] = gepM[i][0];
            sorrendM[i+5][1] = gepM[i][1];
        }


        for (int i = 0; i < 9; i++){ //csokkeno sorba rendezes
            for (int j = 0; j < 9; j++){
                if (Integer.parseInt(sorrendM[j][1]) < Integer.parseInt(sorrendM[j + 1][1])) {
                    tmp = sorrendM[j][0];
                    tmp2 = sorrendM[j][1];
                    sorrendM[j][0] = sorrendM[j + 1][0];
                    sorrendM[j][1] = sorrendM[j + 1][1];
                    sorrendM[j + 1][0] = tmp;
                    sorrendM[j + 1][1] = tmp2;
                }
            }
        }

        String ertek;
        char egyseg;
        boolean gep;

        int db = 0;
        for (int i = 0; i < 10; i++){ //hos kezdemenyezeseinek sorrendbe rakasa
            ertek = sorrendM[i][0];
            egyseg = ertek.charAt(0);
            if(ertek.length() == 2){
                gep = true;
            } else{
                gep = false;
            }

            if(egyseg == 'F'){
                if(gep){
                    if(foldmuves.getDarabGep() > 0){
                        sorrend[db] = "F*";
                        db++;
                    }
                } else{
                    if(foldmuves.getDarab() > 0){
                        sorrend[db] = "F";
                        db++;
                    }
                }
            }
            if(egyseg == 'I'){
                if(gep){
                    if(ijasz.getDarabGep() > 0){
                        sorrend[db] = "I*";
                        db++;
                    }
                } else{
                    if(ijasz.getDarab() > 0){
                        sorrend[db] = "I";
                        db++;
                    }
                }
            }
            if(egyseg == 'G'){
                if(gep){
                    if(griff.getDarabGep() > 0){
                        sorrend[db] = "G*";
                        db++;
                    }
                } else{
                    if(griff.getDarab() > 0){
                        sorrend[db] = "G";
                        db++;
                    }
                }
            }
            if(egyseg == 'O'){
                if(gep){
                    if(ogre.getDarabGep() > 0){
                        sorrend[db] = "O*";
                        db++;
                    }
                } else{
                    if(ogre.getDarab() > 0){
                        sorrend[db] = "O";
                        db++;
                    }
                }
            }
            if(egyseg == 'L'){
                if(gep){
                    if(lovag.getDarabGep() > 0){
                        sorrend[db] = "L*";
                        db++;
                    }
                } else{
                    if(lovag.getDarab() > 0){
                        sorrend[db] = "L";
                        db++;
                    }
                }
            }
        }

        System.out.print("Tamadas sorrendje: ");

        for (int i = 0; i < sorrend.length; i++){
            if(sorrend[i] != null) {
                System.out.print(sorrend[i] + " ");
            }
        }
        System.out.println("");
    }

    /**
     * a felhasznalotol beker egy koordinatat, ha az helyes visszaadja
     * @return ketelemu tombben visszaadja az x es y koordinatat
     */
    public static int[] koordinata() {
        boolean helyes = false;
        int x = 0;
        int y = 0;
        while (!helyes) {
            Scanner be = new Scanner(System.in);
            String c = be.next();

            char x_kar = c.charAt(0);
            if (c.length() == 2) {
                y = Integer.parseInt(c.substring(1)) - 1; //matrix 0-tol indul
            } else if (c.length() == 3) {
                y = Integer.parseInt(c.substring(1, 3)) - 1;
            } else {
                System.out.println("Hibas bemenet! Probald ujra");
            }
            x = x_kar - 'A';

            if (x < 0 || x > 9 || y < 0 || y > 11) {
                System.out.println("Hibas koordinata! Probald meg ujra");
            } else {
                helyes = true;
            }
        }
        int[] ko = new int[2];
        ko[0] = x;
        ko[1] = y;
        return ko;
    }

    /**
     * kivalasztja azonosito alapjan az egyseget
     * @param azonosito az egysegre utalo azonosito
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return kivalasztott egyseg
     */
    public static Egyseg egysegKivalasztas(String azonosito, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        Egyseg egyseg = foldmuves; //modosul
        switch (azonosito.charAt(0)) {
            case 'F' : egyseg = foldmuves; break;
            case 'I' : egyseg = ijasz;break;
            case 'G' : egyseg = griff;break;
            case 'O' : egyseg = ogre;break;
            case 'L' : egyseg = lovag;break;
        }
        return egyseg;
    }

    /**
     * gepi egyseg eleterejet allitja be, s annak alapjan az eletben maradt egysegek mennyiseget
     * @param eletero maradt osszeletero
     * @param egyseg ellenfel egyseg
     */
    public static void gepetSebez(int eletero, Egyseg egyseg){
        if(eletero > 0){
            egyseg.setEleteroGepOsszesen(eletero);
        } else{
            egyseg.setEleteroGepOsszesen(0);
        }

        int ep = 0;
        int eletben = 0;

        if(egyseg.getEleteroGepOsszesen() > 0) {
            ep = (int) Math.floor((double)egyseg.getEleteroGepOsszesen() / egyseg.getEletEro());
            eletben = ep;

            if (egyseg.getEleteroGepOsszesen() > ep * egyseg.getEletEro()) {
                eletben++;
            }
        }

        if(eletben == 0){
            egyseg.setDarabGep(0);
        } else{
            egyseg.setDarabGep(eletben);
        }
    }

    /**
     * uj kornel minden gepi es jatekos egyseg visszatamadasa, atka es gyengitese false-ra modosul, mivel azok koronkent ervenyesek
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void korNullaz(Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        foldmuves.setVisszatamadottGep(false);
        foldmuves.setVisszatamadott(false);
        ijasz.setVisszatamadottGep(false);
        ijasz.setVisszatamadott(false);
        griff.setVisszatamadottGep(false);
        griff.setVisszatamadott(false);
        ogre.setVisszatamadottGep(false);
        ogre.setVisszatamadott(false);
        lovag.setVisszatamadottGep(false);
        lovag.setVisszatamadott(false);

        foldmuves.setAtokGep(false);
        ijasz.setAtokGep(false);
        griff.setAtokGep(false);
        ogre.setAtokGep(false);
        lovag.setAtokGep(false);

        foldmuves.setAtok(false);
        ijasz.setAtok(false);
        griff.setAtok(false);
        ogre.setAtok(false);
        lovag.setAtok(false);

        foldmuves.setGyengites(false);
        ijasz.setGyengites(false);
        griff.setGyengites(false);
        ogre.setGyengites(false);
        lovag.setGyengites(false);

        foldmuves.setGyengitesGep(false);
        ijasz.setGyengitesGep(false);
        griff.setGyengitesGep(false);
        ogre.setGyengitesGep(false);
        lovag.setGyengitesGep(false);
    }

    /**
     * meghatarozza, van-e meg mindket felnek elo egysege, ha nincs, a jatek veget ert
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return true/false: mindket fel rendelkezik elo egyseggel
     */
    public static boolean vanEloEgyseg(Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        boolean jatekosnakVan = false;
        boolean gepnekVan = false;
        if(foldmuves.getDarab() > 0 || ijasz.getDarab() > 0 || griff.getDarab() > 0 || ogre.getDarab() > 0 || lovag.getDarab() > 0){
            jatekosnakVan = true;
        }
        if(foldmuves.getDarabGep() > 0 || ijasz.getDarabGep() > 0 || griff.getDarabGep() > 0 || ogre.getDarabGep() > 0 || lovag.getDarabGep() > 0){
            gepnekVan = true;
        }
        if(jatekosnakVan && gepnekVan){
            return true;
        } else{
            return false;
        }
    }

    /**
     * megadja, hogy melyik jatekos nyert
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return String tipusu valtozot ad vissza, mely tartalmazza a gyoztes felet (vagy a dontetlen allapotot)
     */
    public static String gyozott(Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        boolean j = false;
        boolean g = false;

        String vissza;

        if(foldmuves.getDarab() > 0 || ijasz.getDarab() > 0 || griff.getDarab() > 0 || ogre.getDarab() > 0 || lovag.getDarab() > 0){
            j = true;
        }
        if(foldmuves.getDarabGep() > 0 || ijasz.getDarabGep() > 0 || griff.getDarabGep() > 0 || ogre.getDarabGep() > 0 || lovag.getDarabGep() > 0){
            g = true;
        }

        if(j && !g){
            vissza = "A jatekos nyert!";
        } else if(!j && g){
            vissza = "A gep nyert!";
        } else{
            vissza = "A jatek dontetlennel zarult!";
        }
        return vissza;
    }

    /**
     * meghatarozza, hol van a sajat, jatekos egyseg
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param egyseg a keresett egyseg
     * @return ketelemu tomb: x es y koordinata
     */
    public static int[] egysegHolVan(String[][] matrix, Egyseg egyseg) {
        int[] ko = new int[2];
        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 12; j++){
                if(matrix[i][j].equals(egyseg.getId() + "" + egyseg.getDarab())){
                    ko[0] = i;
                    ko[1] = j;
                }
            }
        }
        return ko;
    }
}