package elokeszit;

import csata.Csata;

import java.util.Scanner;

import static terkep.Elhelyez.terkep;
/**
 * Tartalmazza a parameteres konstruktort, mely letrehozza a megadott ertekekkel a peldanyokat, valamint tartalmazza es az egysegVasarlas es az egysegMozog metodusokat
 */
public class Egyseg {
    private char id;
    private String nev;
    private int ar;
    private int minSebzes;
    private int maxSebzes;
    private int eletEro;
    private int sebesseg;
    private int kezdemenyezes;
    private int darab;
    private int darabGep;
    private int eleteroOsszesen;
    private int eleteroGepOsszesen;
    private boolean volt;
    private boolean voltGep;
    private boolean tavolsagi;
    private boolean visszatamadott;
    private boolean visszatamadottGep;
    private int eredetiDb;
    private int eredetiGepDb;
    private boolean atok;
    private boolean atokGep;
    private boolean gyengites;
    private boolean gyengitesGep;

    public Egyseg(char id, String nev, int ar, int minSebzes, int maxSebzes,  int eletEro, int sebesseg, int kezdemenyezes, int darab, int darabGep, boolean tavolsagi){
        this.id = id;
        this.nev = nev;
        this.ar = ar;
        this.minSebzes = minSebzes;
        this.maxSebzes = maxSebzes;
        this.eletEro = eletEro;
        this.sebesseg = sebesseg;
        this.kezdemenyezes = kezdemenyezes;
        this.darab = darab;
        this.darabGep = darabGep;
        this.eleteroOsszesen = 0;
        this.eleteroGepOsszesen = 0;
        this.volt = false;
        this.voltGep = false;
        this.tavolsagi = tavolsagi;
        this.visszatamadott = false;
        this.visszatamadottGep = false;
        this.eredetiDb = darab;
        this.eredetiGepDb = darabGep;
        this.atok = false;
        this.atokGep = false;
        this.gyengites = false;
        this.gyengitesGep = false;
    }

    /**
     * egysegVasarlas metodus
     * @param egyseg a vasarolando Egyseg tipusu valtozo
     */
    public void egysegVasarlas(Egyseg egyseg) {
        System.out.println("Hany darab " + egyseg.getNev() + " egyseget vasarolnal? (" + egyseg.getAr() + " arany/db). Jelenleg " + egyseg.getDarab() + " darabod van belole es " + Elokeszit.getArany() + " arannyal rendelkezel!");
        Scanner scan = new Scanner(System.in);
        String be = scan.nextLine();
        int db = 0;
        try{
            db = Integer.parseInt(be);
        } catch(NumberFormatException e){
            System.out.println("Hibas formatum: csak szamot irhatsz be!");
        }
        if(db <= 0){
            System.out.println("Nem vasaroltal " + egyseg.getNev() + " egyseget! Uj vasarlas probalkozasahoz ird be az egyseg sorszamat, befejezeshez a nullat (0)!");
        } else if(Elokeszit.getArany() >= egyseg.getAr() * db){
            egyseg.setDarab(egyseg.getDarab() + db);
            egyseg.setEredetiDb(egyseg.getEredetiDb() + db);
            Elokeszit.setArany(Elokeszit.getArany() - db * egyseg.getAr());
            System.out.println("Sikeres vasarlas! Most mar " + egyseg.getDarab() + " " + egyseg.getNev() + " egyseged van. Meg " + Elokeszit.getArany() + " aranyad maradt! Uj vasarlás probalkozasahoz ird be az egyeég sorszamat, befejezeshez a nullat (0)!");
            egyseg.setEleteroOsszesen(egyseg.getDarab()* egyseg.getEletEro());
        } else {
            System.out.println("Sikertelen vasarlas: Nincs eleg aranyad! Uj vasarlas probalkozasahoz ird be az egyseg sorszamat, befejezeshez a nullat (0)!");
        }
    }

    /**
     * A jatekos egysegeinek mozgasat vezerli: megnezi, hogy oda tud-e lepni a jatekos egysege, ha igen, a matrixban atallitja az ertekeket
     * @param egyseg a mozgatni kivant egyseg
     * @param matrix a matrix, amely tartalmazza a megfelelo helyeken az egysegeket
     * @param gep true/false, gep-e az adott egyseg (csak akkor tud mozogni ezen metodus altal, ha nem gep)
     * @param x a cel x koordinata
     * @param y a cel y koordinata
     */
    public void egysegMozog(Egyseg egyseg, String[][] matrix, boolean gep, int x, int y){
        String az;
        if(gep){
            az = egyseg.getId() + "" + egyseg.getDarabGep() + "*";
        } else{
            az = egyseg.getId() + "" + egyseg.getDarab();
        }

        int x_forras = 0;
        int y_forras = 0;

        for (int j = 0; j < 10; j++) {
            for (int k = 0; k < 12; k++) {
                if (matrix[j][k].equals(az)) {
                    x_forras = j;
                    y_forras = k;
                }
            }
        }

        if (Math.abs(x - x_forras) + Math.abs(y - y_forras) <= egyseg.getSebesseg()) {
            if (matrix[x][y].equals("-")) {
                matrix[x][y] = az;
                matrix[x_forras][y_forras] = "-";
                terkep(matrix);
            } else {
                System.out.println("Itt mar van egyseg!");
                Csata.egysegMozog(matrix, az, egyseg);
            }
        } else if (Math.abs(x - x_forras) + Math.abs(y - y_forras) > egyseg.getSebesseg()) {
            System.out.println("Ide nem tudsz lepni az egyseggel, mivel a tavolsag nagyobb, mint az egyseg sebessege!");
            Csata.egysegMozog(matrix, az, egyseg);
        }
    }

    public char getId() {
        return id;
    }


    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    public int getMinSebzes() {
        return minSebzes;
    }

    public void setMinSebzes(int minSebzes) {
        this.minSebzes = minSebzes;
    }

    public int getMaxSebzes() {
        return maxSebzes;
    }

    public void setMaxSebzes(int maxSebzes) {
        this.maxSebzes = maxSebzes;
    }

    public int getEletEro() {
        return eletEro;
    }

    public void setEletEro(int eletEro) {
        this.eletEro = eletEro;
    }

    public int getSebesseg() {
        return sebesseg;
    }

    public void setSebesseg(int sebesseg) {
        this.sebesseg = sebesseg;
    }

    public int getKezdemenyezes() {
        return kezdemenyezes;
    }

    public void setKezdemenyezes(int kezdemenyezes) {
        this.kezdemenyezes = kezdemenyezes;
    }

    public int getDarab() {
        return darab;
    }

    public void setDarab(int darab) {
        this.darab = darab;
    }

    public int getDarabGep() { return darabGep; }

    public boolean isTavolsagi() { return tavolsagi; }

    public void setDarabGep(int darabGep) {
        this.darabGep = darabGep;
    }

    public boolean isVolt() {
        return volt;
    }

    public void setVolt(boolean volt) {
        this.volt = volt;
    }

    public boolean isVoltGep() {
        return voltGep;
    }

    public void setVoltGep(boolean voltGep) {
        this.voltGep = voltGep;
    }

    public int getEleteroOsszesen() {
        return eleteroOsszesen;
    }

    public void setEleteroOsszesen(int eleteroOsszesen) {
        if(eleteroOsszesen > 0){
            this.eleteroOsszesen = eleteroOsszesen;
        } else{
            this.eleteroOsszesen = 0;
        }
    }

    public int getEleteroGepOsszesen() {
        return eleteroGepOsszesen;
    }

    public void setEleteroGepOsszesen(int eleteroGepOsszesen) {
        if(eleteroGepOsszesen > 0){
            this.eleteroGepOsszesen = eleteroGepOsszesen;
        } else{
            this.eleteroGepOsszesen = 0;
        }
    }

    public boolean isVisszatamadott() {
        return visszatamadott;
    }

    public void setVisszatamadott(boolean visszatamadott) {
        this.visszatamadott = visszatamadott;
    }

    public boolean isVisszatamadottGep() {
        return visszatamadottGep;
    }

    public void setVisszatamadottGep(boolean visszatamadottGep) {
        this.visszatamadottGep = visszatamadottGep;
    }

    public int getEredetiDb() {
        return eredetiDb;
    }

    public int getEredetiGepDb() {
        return eredetiGepDb;
    }

    public void setEredetiDb(int eredetiDb) {
        this.eredetiDb = eredetiDb;
    }

    public void setEredetiGepDb(int eredetiGepDb) {
        this.eredetiGepDb = eredetiGepDb;
    }

    public boolean isAtok() {
        return atok;
    }

    public void setAtok(boolean atok) {
        this.atok = atok;
    }

    public boolean isAtokGep() {
        return atokGep;
    }

    public void setAtokGep(boolean atokGep) {
        this.atokGep = atokGep;
    }

    public boolean isGyengites() {
        return gyengites;
    }

    public void setGyengites(boolean gyengites) {
        this.gyengites = gyengites;
    }

    public boolean isGyengitesGep() {
        return gyengitesGep;
    }

    public void setGyengitesGep(boolean gyengitesGep) {
        this.gyengitesGep = gyengitesGep;
    }
}
