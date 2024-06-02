package csata;

import elokeszit.Egyseg;
import elokeszit.Hos;
import elokeszit.Varazslat;

/**
 * A gep egysegeinek tevekenysegeit valositja meg: mozgas, varazslas, hos tamadas, egyseg tamadas
 */
public class GepCsata {
    /**
     * Ezen metodus alapjan dol el, hogy a gep hose varazsolni vagy tamadni fog-e, vagy egyseget fogja mozgatni, varakoztatni (ha mar varazsolt/tamadott a korben, biztos varakozni vagy mozogni fog az egyseg)
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
     * @param aktualis aktualis, most kovetkezo gepi egyseg
     * @param gepHasznalt hasznalt-e a gep hose mar a korben varazslast vagy hosi tamadast
     * @param mannaGep mennyi mannaja van meg a gepnek
     * @return 3 elemu String tombot ad vissza, melynek elso eleme a megmaradt manna, masodik, hogy a gep hasznalta-e hoset a korben, harmadik, hogy az aktualis egysegnel hasznalta-e a hoset
     */
    public static String[] gepEgyseg(String[][] matrix, Hos hos, Hos gep, Varazslat villamcsapas, Varazslat tuzlabda, Varazslat feltamasztas, Varazslat atok, Varazslat gyengites, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag, Egyseg aktualis, boolean gepHasznalt, int mannaGep){
        boolean mostVoltHos = false;
        if(!gepHasznalt){
            String[] elerhetoVarazslatok = new String[10]; //5 varazslat: nev - igen/nem    nev - igen/nem
            int elerhetoDb = 0;
            if(villamcsapas.getDarab() > 0 && villamcsapas.getManna() <= mannaGep){
                elerhetoVarazslatok[elerhetoDb] = "villamcsapas";
                elerhetoVarazslatok[elerhetoDb+1] = "igen";
                elerhetoDb += 2;
            }
            if(tuzlabda.getDarab() > 0 && tuzlabda.getManna() <= mannaGep){
                elerhetoVarazslatok[elerhetoDb] = "tuzlabda";
                elerhetoVarazslatok[elerhetoDb+1] = "igen";
                elerhetoDb += 2;
            }
            if(feltamasztas.getDarab() > 0 && feltamasztas.getManna() <= mannaGep){
                elerhetoVarazslatok[elerhetoDb] = "feltamasztas";
                elerhetoVarazslatok[elerhetoDb+1] = "igen";
                elerhetoDb += 2;
            }
            if(atok.getDarab() > 0 && atok.getManna() <= mannaGep){
                elerhetoVarazslatok[elerhetoDb] = "atok";
                elerhetoVarazslatok[elerhetoDb+1] = "igen";
                elerhetoDb += 2;
            }
            if(gyengites.getDarab() > 0 && gyengites.getManna() <= mannaGep){
                elerhetoVarazslatok[elerhetoDb] = "gyengites";
                elerhetoVarazslatok[elerhetoDb+1] = "igen";
                elerhetoDb += 2;
            }
            //21% esely, hogy varazsol az egyseg lepese elott, 17%, hogy tamad, 62%, hogy meg nem hasznalja a host
            int randomSzam = (int) ((Math.random() * (99)) + 1);
            if(elerhetoDb > 0) {
                if (randomSzam <= 21) { //hos varazsol
                    randomSzam = (int) (Math.random() * (elerhetoDb/2 -1) + 1);
                    Varazslat varazslat = villamcsapas;
                    switch (elerhetoVarazslatok[randomSzam * 2 -2]) {
                        case "tuzlabda":
                            varazslat = tuzlabda;
                            break;
                        case "feltamasztas":
                            varazslat = feltamasztas;
                            break;
                        case "atok":
                            varazslat = atok;
                            break;
                        case "gyengites":
                            varazslat = gyengites;
                            break;
                    }
                    Egyseg egyseg = egysegKivalaszt(varazslat, gep, foldmuves, ijasz, griff, ogre, lovag);
                    mannaGep = gepHosVarazsol(matrix, gep, varazslat, mannaGep, egyseg, foldmuves, ijasz, griff, ogre, lovag);
                    gepHasznalt = true;
                    mostVoltHos = true;
                } else if (randomSzam <= 38) { //hos tamad
                    int sebzes = gep.getTamadas() * 10;
                    Egyseg egyseg = egysegKivalaszt2(sebzes, gep, foldmuves, ijasz, griff, ogre, lovag);
                    gepHosTamad(egyseg, sebzes, matrix);
                    gepHasznalt = true;
                    mostVoltHos = true;
                } else {
                    gepEloszto(aktualis, gep, hos, matrix, foldmuves, ijasz, griff, ogre, lovag);
                }
            } else{
                randomSzam = (int) ((Math.random() * (99)) + 1);

                if(randomSzam < 40){ //hos tamad
                    int sebzes = gep.getTamadas() * 10;
                    Egyseg egyseg = egysegKivalaszt2(sebzes, gep, foldmuves, ijasz, griff, ogre, lovag);
                    gepHosTamad(egyseg, sebzes, matrix);
                    gepHasznalt = true;
                    mostVoltHos = true;
                } else{
                    gepEloszto(aktualis, gep, hos, matrix, foldmuves, ijasz, griff, ogre, lovag);
                }
            }
        } else{
            gepEloszto(aktualis, gep, hos, matrix, foldmuves, ijasz, griff, ogre, lovag);
        }
        String[] vissza = new String[3];
        vissza[0] = String.valueOf(mannaGep);
        if(gepHasznalt){
            vissza[1] = "true";
        } else{
            vissza[1] = "false";
        }
        if(mostVoltHos){
            vissza[2] = "true";
        } else{
            vissza[2] = "false";
        }

        return vissza;
    }

    /**
     * ha a hos nem tamad es nem is varazsol, akkor az egyegen a sor: varakozik, tamad vagy mozog - a metodus ezt donti el (pl. van-e a szomszedos mezon ellenfel, ha igen, tamad, ha nem, lep fele, stb)
     * @param egyseg az aktualis egyseg
     * @param gep a gep hose
     * @param hos a jatekos hose
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     */
    public static void gepEloszto(Egyseg egyseg, Hos gep, Hos hos, String[][] matrix, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        /*
            100%, hogy tamad, ha tud: kozelharci, ha szomszedban van ellenseg, tavolsagi mindig
            10% varakozik
            90% mozog
         */

        String aktualis;
        int[] ko = egysegHolVan(matrix, egyseg);
        int x = ko[0];
        int y = ko[1];


        boolean talalt = false;
        Egyseg ellenfelEgyseg = null;

        if(egyseg != ijasz) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x - i >= 0 && x - i < 10 && y - j >= 0 && y - j < 12) {
                        if (!matrix[x - i][y - j].equals("-") && !matrix[x - i][y - j].contains("*") && !talalt) {
                            talalt = true;
                            aktualis = matrix[x - i][y - j];
                            ellenfelEgyseg = Csata.egysegKivalasztas(aktualis, foldmuves, ijasz, griff, ogre, lovag);
                            int t_x = x-i;
                            int t_y = y-j;
                        }
                    }
                }
            }

            if (talalt && ellenfelEgyseg != null) {
                egysegTamad(matrix, hos, gep, egyseg, ellenfelEgyseg);
            } else{
                int randomSzam = (int) ((Math.random() * (99)) + 1);
                if(randomSzam <= 10){
                    varakozik(egyseg);
                } else{
                    mozog(matrix, egyseg);
                }
            }
        } else{
            //ijasz mindig tamad, ha nincs szomszedban ellenfel
            int db = 0;
            int nincskinn = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x - i >= 0 && x - i < 10 && y - j >= 0 && y - j < 12) {
                        nincskinn++;
                        if (matrix[x - i][y - j].equals("-") || matrix[x-i][y-j].contains("*")) {
                            db++;
                        }
                    }
                }
            }
            if(db == nincskinn) {
                System.out.println("ELLENFEL IJASZ TAMAD!");
                int sebzes = ijasz.getMinSebzes() * ijasz.getDarabGep(); //leheto legkisebb sebzest figyelembe veve
                ellenfelEgyseg = egysegKivalaszt2(sebzes, gep, foldmuves, ijasz, griff, ogre, lovag);
                egysegTamad(matrix, hos, gep, egyseg, ellenfelEgyseg);
            } else{
                mozog(matrix, ijasz);
            }
        }

    }

    /**
     * lehetosege van az egysegeknek varakozni is, a metodus ezt valositja meg
     * @param egyseg a varakozasra itelt egyseg
     */
    public static void varakozik(Egyseg egyseg){
        System.out.println("Az ellenfel " + egyseg.getNev() + " egysege varakozott");
    }

    /**
     * az egyseg tamadasat valositja meg a metodus
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param hos a jatekos hose
     * @param gep a gep hose
     * @param tamado a tamado egyseg
     * @param tamadott a tamadott egyseg
     */
    public static void egysegTamad(String[][] matrix, Hos hos, Hos gep, Egyseg tamado, Egyseg tamadott){
        int[] ko = ellenfelEgysegHolVan(matrix, tamadott);
        int x = ko[0];
        int y = ko[1];

        int[] ko_gep = egysegHolVan(matrix, tamado);
        int x_g = ko_gep[0];
        int y_g = ko_gep[1];

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
        alapsebzes *= 1 + (((double) gep.getTamadas()) / 10);
        alapsebzes *= 1 - (((double) hos.getVedekezes()) * 0.05);

        int sebzes = (int) Math.round(alapsebzes);
        int szerencse = (int) ((Math.random() * (99)) + 1);

        if(szerencse <= gep.getSzerencse()*5){
            sebzes *= 2;
            System.out.println("Kritikus talalat!");
        }

        if(tamadott.getId() == 'L'){ //lovag specialis kepesseg
            sebzes *= 1-0.2;
            System.out.println("Lovag specialis tulajdonsag: 20% kevesebb elszenvedett sebzes");
        }

        if(tamado.isGyengitesGep()){ //gyengites varazslat
            sebzes *= 0.5;
            System.out.println("Gyengites varazslat miatt 50%-kal kevesebbet sebez az egyseg");
        }

        int tamadasUtan = tamadott.getEleteroOsszesen() - sebzes;

        egysegetSebez(tamadasUtan, tamadott);
        int eletben = tamadott.getDarab();

        if(eletben > 0){
            tamadott.setDarab(eletben);
            System.out.println("Eletben maradt " + eletben + " db " + tamadott.getNev() + " egyseg!");
            matrix[x][y] = tamadott.getId() + "" + eletben;
            System.out.println("VISSZATAMADAS");
            if(tamado.getId() != 'O') {
                if(!tamado.isTavolsagi()) {
                    if (!tamadott.isVisszatamadott()) {
                        if (tamadott.getId() != 'G') { //griff vegtelenszer visszatamadhat
                            tamadott.setVisszatamadott(true);
                        }
                        visszatamad(matrix, x_g, y_g, hos, gep, tamado, tamadott);
                    }
                } else{
                    System.out.println("Az egyseg tavol van a visszatamadashoz!");
                }
            } else{
                System.out.println("Az ogre ellenfel elrettentette az egyseget, ellenuk nem tudunk visszatamadni!");
            }
        } else{
            tamadott.setDarab(0);
            System.out.println("A megtamadott egysegek meghaltak!");
            matrix[x][y] = "-";
        }

    } //ELLENFEL LOVAG MEGTAMADTA SAJAT GRIFFET
    //  MOST SAJAT GRIFF VISSZATAMADJA ELLENFEL LOVAGOT

    /**
     * tamadas utan a hos (ha nem ogre volt a tamado) visszatamad
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param x_g a gepi egyseg (jelenleg tamadott) poziciojanak x koordinataja
     * @param y_g a gepi egyseg (jelenleg tamadott) poziciojanak y koordinataja
     * @param hos a jatekos hose
     * @param gep a gep hose
     * @param tamadott a tamadott egyseg - jelenleg gep egysege
     * @param tamado a tamado egyseg - jelenleg jatekos egysege
     */
    public static void visszatamad(String[][] matrix, int x_g, int y_g, Hos hos, Hos gep, Egyseg tamadott, Egyseg tamado) {
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
        alapsebzes *= 1 + (((double) hos.getTamadas()) / 10);
        alapsebzes *= 1 - (((double) gep.getVedekezes()) * 0.05);

        int sebzes = (int) Math.round(alapsebzes);
        int szerencse = (int) ((Math.random() * (99)) + 1);

        if(szerencse <= hos.getSzerencse()*5){
            sebzes *= 2;
            System.out.println("Kritikus talalat!");
        }

        sebzes /= 2; //visszatamadas
        System.out.println("Visszatamadas miatt 50%-kal kevesebbet sebez az egyseg");

        if(tamado.isGyengites()){ //gyengites varazslat
            sebzes *= 0.5;
            System.out.println("Gyengites varazslat miatt 50%-kal kevesebbet sebez az egyseg");
        }

        int tamadasUtan = tamadott.getEleteroGepOsszesen() - sebzes;

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
        tamadott.setEleteroGepOsszesen(tamadasUtan);
        if(eletben > 0){
            tamadott.setDarabGep(eletben);
            System.out.println("A visszatamadas utan eletben maradt " + eletben + " db " + tamadott.getNev() + " egyseg!");
            matrix[x_g][y_g] = tamadott.getId() + "" + eletben + "*";
        } else{
            tamadott.setDarabGep(0);
            System.out.println("Az osszes ellenfel " + tamadott.getNev() + " meghalt!");
            matrix[x_g][y_g] = "-";
        }
    }

    /**
     * a gepi egysegek mozgasat valositja meg, ha az adott egyseg sebessegen beluli tavban van egy ellenfel egyseg, melle lep (ha tud), ha nem, akkor veletlenszeruen mozog
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param egyseg a mozgatni kivant egyseg
     */
    public static void mozog(String[][] matrix, Egyseg egyseg) {
        int[] ko = egysegHolVan(matrix, egyseg);
        int x = ko[0];
        int y = ko[1];

        boolean talalt = false;
        int x_e = 0; //talalt ellenfel x koordinataja
        int y_e = 0; //talalt ellenfel y koordinataja

        int vizsgal = (int) Math.floor((double)egyseg.getSebesseg()/2); //az egyseg korul sebesseg+1 nagysagu negyzetben keres ellenfeleket --> balra tole seb/2 tavban, jobbra is

        for (int i = -vizsgal; i <= vizsgal; i++) {
            for (int j = -vizsgal; j <= vizsgal; j++) {
                if (x - i >= 0 && x - i < 10 && y - j >= 0 && y - j < 12  && !talalt) {
                    if (!matrix[x - i][y - j].equals("-") && !matrix[x - i][y - j].contains("*")) {
                        talalt = true;
                        x_e = x-i;
                        y_e = y-j;
                    }
                }
            }
        }

        boolean leptunk = false;
        if(talalt){ //ha van a bejarhato zonan belul, akkor megnezzuk, szomszedba tudunk-e lepni
            for(int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x_e - i >= 0 && x_e - i < 10 && y_e - j >= 0 && y_e - j < 12 && !leptunk) {
                        if (matrix[x_e - i][y_e - j].equals("-") && Math.abs(x-x_e) + Math.abs(y-y_e) <= egyseg.getSebesseg()) {
                            leptunk = true;
                            matrix[x_e-i][y_e-j] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                            matrix[x][y] = "-";
                        }
                    }
                }
            }
        }

        if(!talalt || !leptunk){ //nem talaltunk egyseget vagy nem birtunk melle lepni, mert mar van a lehetseges szomszedokban egyseg
            int randomSzam = (int) ((Math.random() * (egyseg.getSebesseg()-3)) + 3); //minimum harmat lep, max a sebesseggel megegyezot [nincs egyseg 3 sebesseg alatt]
            boolean sikerultLepni = false;
            int proba = 0;

            while(!sikerultLepni) {
                if(proba < 10) {
                    int x_fele = (int) ((Math.random() * (randomSzam - 1)) + 1); //x iranyban lepjunk a kapott randomszambol x mezot
                    int y_fele = randomSzam - x_fele; //y iranyban pedig a kapott randomszam - x_fele mezot

                    //alapbol le lep, 50% esellyel lepjen fel
                    int le = (int) ((Math.random() * (99)) + 1);

                    if (le >= 50) {
                        x_fele = -x_fele;
                    }

                    //alapbol balra lep, 35% esellyel lepjen jobbra
                    int jobbra = (int) ((Math.random() * (99)) + 1);

                    if (jobbra >= 75) {
                        y_fele = -y_fele;
                    }

                    if (x - x_fele >= 0 && x - x_fele < 10 && y - y_fele >= 0 && y - y_fele < 12) {
                        if (matrix[x - x_fele][y - y_fele].equals("-")) {
                            matrix[x - x_fele][y - y_fele] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                            matrix[x][y] = "-";
                            sikerultLepni = true;
                        }
                    } else {
                        proba++;
                    }
                } else{
                    sikerultLepni = true;
                    varakozik(egyseg);
                }
            }
        }
    }

    /**
     * a gep hosenek tamadasat valositja meg
     * @param egyseg a megtamadott egyseg
     * @param sebzes a sebzes merteke
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     */
    public static void gepHosTamad(Egyseg egyseg, int sebzes, String[][] matrix) {
        String teljesAzonosito = egyseg.getId() + "" + egyseg.getDarab();
        int eletero = egyseg.getEleteroOsszesen() - sebzes;
        egysegetSebez(eletero, egyseg);
        System.out.print("A sajat " + egyseg.getNev() + " egysegre " + sebzes + " sebzest mert az ellenfel hose!");
        if(egyseg.getDarab() > 0){
            System.out.println("Meg eletben maradt " + egyseg.getDarab() + " " + egyseg.getNev() + ".");
        } else{
            System.out.println("Az osszes sajat " + egyseg.getNev() + " egyseg meghalt a tamadastol!");
        }

        for (int i = 0; i < 10; i++){
            for (int j = 0; j < 12; j++){
                if(matrix[i][j].equals(teljesAzonosito)){
                    if(egyseg.getDarab() > 0){
                        matrix[i][j] = egyseg.getId() + "" + egyseg.getDarab();
                    } else{
                        matrix[i][j] = "-";
                    }
                }
            }
        }
    }

    /**
     * kivalasztja az optimalis ellenfel egyseget, olyat, aminek a megtamadasa halalt okoz, ha nincs jelenleg olyan, veletlen dont
     * @param sebzes a sebzes merteke
     * @param gep a gep hose
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return visszaadja az optimalis, megtamando ellenfel egyseget
     */
    public static Egyseg egysegKivalaszt2(int sebzes, Hos gep, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        //jatekos egysegeinek eleterejenek alapjan rendez csokkenobe
        String[][] eleterok = new String[5][2];
        eleterok[0][0] = "foldmuves";
        eleterok[0][1] = String.valueOf(foldmuves.getEleteroOsszesen());
        eleterok[1][0] = "ijasz";
        eleterok[1][1] = String.valueOf(ijasz.getEleteroOsszesen());
        eleterok[2][0] = "griff";
        eleterok[2][1] = String.valueOf(griff.getEleteroOsszesen());
        eleterok[3][0] = "ogre";
        eleterok[3][1] = String.valueOf(ogre.getEleteroOsszesen());
        eleterok[4][0] = "lovag";
        eleterok[4][1] = String.valueOf(lovag.getEleteroOsszesen());

        String tmp;
        String tmp2;

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) {
                if (Integer.parseInt(eleterok[i][1]) < Integer.parseInt(eleterok[i + 1][1])) {
                    tmp = eleterok[i][0];
                    tmp2 = eleterok[i][1];
                    eleterok[i][0] = eleterok[i + 1][0];
                    eleterok[i][1] = eleterok[i + 1][1];
                    eleterok[i + 1][0] = tmp;
                    eleterok[i + 1][1] = tmp2;
                }
            }
        }

        boolean talalt = false;
        int db = 0;
        String egysegNev = "";

        while(!talalt){
            if(Integer.parseInt(eleterok[db][1]) <= sebzes){
                egysegNev = eleterok[db][0];
            }
            talalt = true;
        }

        Egyseg egyseg = foldmuves;
        switch(egysegNev){
            case "ijasz": egyseg = ijasz; break;
            case "griff": egyseg = griff; break;
            case "ogre": egyseg = ogre; break;
            case "lovag": egyseg = lovag; break;
            default:talalt=false;
        }

        if(!talalt){ //ha nincs olyan egyseg, melynek eletereje kisebb, mint a leendo sebzes, akkor random kivalasztunk egy elot
            //alapbol foldmuvesre van allitva, legalabb abbol letezik 1 db, ha a tobbibol nincs, mert nem ert veget a jatek
            if(ijasz.getDarab() > 0){
                egyseg = ijasz;
            } else if(griff.getDarab() > 0){
                egyseg = griff;
            } else if(ogre.getDarab() > 0){
                egyseg = ogre;
            } else if(lovag.getDarab() > 0){
                egyseg = lovag;
            }
        }

        return egyseg;
    }

    public static Egyseg egysegKivalaszt(Varazslat varazslat, Hos gep, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        //jatekos egysegeinek eleterejenek alapjan rendez csokkenobe
        String[][] eleterok = new String[5][2];
        eleterok[0][0] = "foldmuves";
        eleterok[0][1] = String.valueOf(foldmuves.getEleteroOsszesen());
        eleterok[1][0] = "ijasz";
        eleterok[1][1] = String.valueOf(ijasz.getEleteroOsszesen());
        eleterok[2][0] = "griff";
        eleterok[2][1] = String.valueOf(griff.getEleteroOsszesen());
        eleterok[3][0] = "ogre";
        eleterok[3][1] = String.valueOf(ogre.getEleteroOsszesen());
        eleterok[4][0] = "lovag";
        eleterok[4][1] = String.valueOf(lovag.getEleteroOsszesen());

        String tmp;
        String tmp2;

        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 4; i++) {
                if (Integer.parseInt(eleterok[i][1]) < Integer.parseInt(eleterok[i + 1][1])) {
                    tmp = eleterok[i][0];
                    tmp2 = eleterok[i][1];
                    eleterok[i][0] = eleterok[i + 1][0];
                    eleterok[i][1] = eleterok[i + 1][1];
                    eleterok[i + 1][0] = tmp;
                    eleterok[i + 1][1] = tmp2;
                }
            }
        }

        boolean talalt = false;
        int db = 0;
        String egysegNev = "";

        while(!talalt){
            if(Integer.parseInt(eleterok[db][1]) <= varazslat.getSzorzo() * gep.getVarazsero()){
                egysegNev = eleterok[db][0];
            }
            talalt = true;
        }

        Egyseg egyseg = foldmuves;
        switch(egysegNev){
            case "ijasz": egyseg = ijasz; break;
            case "griff": egyseg = griff; break;
            case "ogre": egyseg = ogre; break;
            case "lovag": egyseg = lovag; break;
            default:talalt=false;
        }

        if(!talalt){ //ha nincs olyan egyseg, melynek eletereje kisebb, mint a leendo sebzes, akkor random kivalasztunk egy elot
            //alapbold foldmuvesre van allitva, legalabb abbol letezik 1 db, ha a tobbibol nincs, mert nem ert veget a jatek
            if(ijasz.getDarab() > 0){
                egyseg = ijasz;
            } else if(griff.getDarab() > 0){
                egyseg = griff;
            } else if(ogre.getDarab() > 0){
                egyseg = ogre;
            } else if(lovag.getDarab() > 0){
                egyseg = lovag;
            }
        }

        return egyseg;
    }

    /**
     * a gep hosenek varazslasat valositja meg
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param gep gep hose
     * @param varazslat a valasztott varazslat
     * @param mannaGep aktualis gep manna
     * @param egyseg egyseg
     * @param foldmuves egyseg
     * @param ijasz egyseg
     * @param griff egyseg
     * @param ogre egyseg
     * @param lovag egyseg
     * @return a megmaradt manna mennyiseget adja vissza
     */
    public static int gepHosVarazsol(String[][] matrix, Hos gep, Varazslat varazslat, int mannaGep, Egyseg egyseg, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag) {
        int[] ko = ellenfelEgysegHolVan(matrix, egyseg); //2 elemu tomb: x es y koordinata
        int x = ko[0];
        int y = ko[1];

        if(varazslat.getNev().equals("villamcsapas")){
            int varazslatUtan = egyseg.getEleteroOsszesen() - gep.getVarazsero() * 30;
            mannaGep = mannaGep - varazslat.getManna();
            System.out.println("VILLAMCSAPAS: Ellenfel villamcsapas " + gep.getVarazsero() * 30 + " sebzest okozott a(z) " + egyseg.getNev() + " egysegednek!");
            if (varazslatUtan > 0) {
                egysegetSebez(varazslatUtan, egyseg);
                System.out.println("Eletben maradt: " + egyseg.getDarab() + " egyseg");
                matrix[x][y] = egyseg.getId() + "" + egyseg.getDarab();
            } else {
                System.out.println("Az osszes egyseg meghalt a villamcsapasban!");
                egyseg.setEleteroOsszesen(0);
                egyseg.setDarab(0);
                matrix[x][y] = "-";
            }
        }

        if(varazslat.getNev().equals("tuzlabda")){
            String aktualis;
            System.out.println("TŰZLABDA");
            for(int i = -1; i <= 1; i++){
                for(int j = -1; j <= 1; j++){
                    if(x-i >= 0 && x-i < 10 && y-j >= 0 && y-j < 12){
                        if(!matrix[x-i][y-j].equals("-")){
                            aktualis = matrix[x-i][y-j];

                            egyseg = Csata.egysegKivalasztas(aktualis, foldmuves, ijasz, griff, ogre, lovag);

                            boolean gepEgyseg;
                            if(aktualis.contains("*")){
                                gepEgyseg = true;
                            } else{
                                gepEgyseg = false;
                            }

                            int varazslatUtan;

                            if(gepEgyseg) {
                                varazslatUtan = egyseg.getEleteroGepOsszesen() - gep.getVarazsero() * 20;
                            } else{
                                varazslatUtan = egyseg.getEleteroOsszesen() - gep.getVarazsero() * 20;
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

        if(varazslat.getNev().equals("feltamasztas")){ //legserultebb sajat egyseget
            if(egyseg.getEredetiGepDb() > 0) {
                if (egyseg.getEredetiGepDb() > egyseg.getDarabGep()) {
                    int gyogyitasUtan = egyseg.getEleteroGepOsszesen() + gep.getVarazsero() * 50;
                    if (gyogyitasUtan > egyseg.getEredetiGepDb() * egyseg.getEletEro()) {
                        egyseg.setEleteroGepOsszesen(egyseg.getEredetiGepDb() * egyseg.getEletEro());
                        egyseg.setDarabGep(egyseg.getEredetiGepDb());
                    } else {
                        egyseg.setEleteroGepOsszesen(gyogyitasUtan);
                        int eletben = (int) Math.floor((double) egyseg.getEleteroGepOsszesen() / egyseg.getEletEro());

                        if (egyseg.getEleteroGepOsszesen() > eletben * egyseg.getEletEro()) {
                            eletben++;
                        }
                        egyseg.setDarabGep(eletben);
                    }
                    matrix[x][y] = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
                    System.out.println("FELTAMASZTAS: A feltamasztas utan " + egyseg.getDarabGep() + " darab " + egyseg.getNev() + " ellenseges egyseg el!");
                } else {
                    System.out.println("Az eredeti darabszamu " + egyseg.getNev() + " egyseged van. Mind teljesen ep, ezert nem gyogyithato!");
                }
            }
        }

        if(varazslat.getNev().equals("atok")){
            int sebzesUtan = egyseg.getEleteroOsszesen() - gep.getVarazsero() * 15;
            if(sebzesUtan > 0){
                egysegetSebez(sebzesUtan, egyseg);
                System.out.println("ATOK: Eletben maradt: " + egyseg.getDarab() + " sajat egyseg, a kovetkezo alkalommal minimumsebzesuket alkalmazzak");
                matrix[x][y] = egyseg.getId() + "" + egyseg.getDarab();
                egyseg.setAtok(true);
            } else{
                System.out.println("Az osszes egyseg meghalt a sebzesben!");
                egyseg.setEleteroOsszesen(0);
                egyseg.setDarab(0);
                matrix[x][y] = "-";
            }
        }

        if(varazslat.getNev().equals("gyengites")){
            System.out.println("GYENGITES: Az aktualis korben a(z) " + egyseg.getNev() + " egysegunk meg van gyengitve, igy 50%-kal kevesebbet sebez (visszatamadaskor is)");
            egyseg.setGyengites(true);
        }
        return mannaGep;
    }

    /**
     * az ellenfel, jatekos egysegek sebzeset valositja meg, beallitja a maradek osszeleterot, s mennyiseget
     * @param eletero a sebzes utan megmaradt eletero
     * @param egyseg a megsebzett jatekos egyseg
     */
    public static void egysegetSebez(int eletero, Egyseg egyseg) {
        if(eletero > 0){
            egyseg.setEleteroOsszesen(eletero);
        } else{
            egyseg.setEleteroOsszesen(0);
        }

        int ep = 0;
        int eletben = 0;

        if(egyseg.getEleteroOsszesen() > 0) {
            ep = (int) Math.floor((double)egyseg.getEleteroOsszesen() / egyseg.getEletEro());
            eletben = ep;

            if (egyseg.getEleteroOsszesen() > ep * egyseg.getEletEro()) {
                eletben++;
            }
        }

        if(eletben == 0){
            egyseg.setDarab(0);
        } else{
            egyseg.setDarab(eletben);
        }
    }

    /**
     * visszaadja ket elemu tombben az ellenfel egyseg poziciojanak x es y koordinatajat
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param egyseg az ellenfel egyseg
     * @return x es y koordinatak
     */
    public static int[] ellenfelEgysegHolVan(String[][] matrix, Egyseg egyseg) {
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

    /**
     * visszaadja ket elemu tombben a sajat, gepi egyseg poziciojanak x es y koordinatajat
     * @param matrix az egysegek azonositojat tarolja a megfelelo pozicioban
     * @param egyseg a sajat, gepi egyseg
     * @return x es y koordinatak
     */
    public static int[] egysegHolVan(String[][] matrix, Egyseg egyseg) {
        int[] ko = new int[2];
        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 12; j++){
                if(matrix[i][j].equals(egyseg.getId() + "" + egyseg.getDarabGep() + "*")){
                    ko[0] = i;
                    ko[1] = j;
                }
            }
        }

        return ko;
    }

}
