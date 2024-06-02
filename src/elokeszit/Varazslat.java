package elokeszit;

/**
 * A Varazslat osztalyban talalhato meg a Varazslat parameteres konstruktor, letrehozza a peldanyokat, beallitja a megadott ertekuket
 */
public class Varazslat {
    private String nev;
    private int ar;
    private int manna;
    private int szorzo; //varazsero szorzo
    private int darab;
    private int darabGep;

    public Varazslat(String nev, int ar, int manna, int szorzo, int darab, int darabGep){
        this.nev = nev;
        this.ar = ar;
        this.manna = manna;
        this.szorzo = szorzo;
        this.darab = darab;
        this.darabGep = darabGep;
    }

    /**
     * Megvasarolja a jatekos szamara a kivant varazslatokat
     * @param varazslat a megvasarolando Varazslat varazslat objektum
     */
    public void varazslatVasarlas(Varazslat varazslat){
        if(varazslat.getDarab() == 0) {
            if(Elokeszit.getArany() >= varazslat.getAr()) {
                varazslat.setDarab(1);
                Elokeszit.setArany(Elokeszit.getArany() - varazslat.getAr());
                System.out.println("Sikeres vasarlas! Most mar van " + varazslat.getDarab() + " db " + varazslat.getNev() + " varazslatod! Meg " + Elokeszit.getArany() + " aranyad maradt. Uj vasarlashoz ird be a varazslat sorszamat, befejezeshez a nullat (0)!");
            } else{
                System.out.println("Sikertelen vasarlas: Nincs eleg aranyad. A varazslat ara " + varazslat.getAr() + ", de neked csak " + Elokeszit.getArany() + " aranyad van! Masik varazslat vasarlasahoz ird be a sorszamat, befejezeshez a nullat (0)!");
            }
        } else {
            System.out.println("Sikertelen vasarlas: Mar van ebbol a varazslatbol 1 darabod! Vasarlas folytatasahoz ird be a vasarolando varazslat sorszamat, befejezeshez a nullat (0)!");
        }
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

    public int getManna() {
        return manna;
    }

    public void setManna(int manna) {
        this.manna = manna;
    }

    public int getSzorzo() {
        return szorzo;
    }

    public void setSzorzo(int szorzo) {
        this.szorzo = szorzo;
    }

    public int getDarab() {
        return darab;
    }

    public void setDarab(int darab) {
        this.darab = darab;
    }

    public int getDarabGep() {
        return darabGep;
    }

    public void setDarabGep(int darabGep) {
        this.darabGep = darabGep;
    }
}
