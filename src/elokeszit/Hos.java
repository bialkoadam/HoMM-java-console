package elokeszit;

import java.util.List;
import java.util.Scanner;

/**
 * A Hos osztalyban talalhato meg a Hos konstruktor, mely alapbol az osszes adattag (tamadas, vedekezes, stb) erteket 1-re allitja. A hos tulajdonsagpontjainak vasarlasa a vasarol metodusban valosul meg
 */
public class Hos {
    private int tamadas;
    private int vedekezes;
    private int varazsero;
    private int tudas;
    private int moral;
    private int szerencse;

    public Hos(){
        this.tamadas = 1;
        this.vedekezes = 1;
        this.varazsero = 1;
        this.tudas = 1;
        this.moral = 1;
        this.szerencse = 1;
    }

    /**
     * Megvasarolja a jatekos hos szamara a tulajdonsagpontokat
     * @param arak listaban tarolt tulajdonsagpont arak
     * @param az tulajdonsagpont azonositoja
     * @param start a listaban hanyadik elemtol vasarolunk most
     * @return a listaban hanyadik elemtol vasarolunk legkozelebb
     */
    public int vasarol(List<Integer> arak, String az, int start){
        System.out.println("Hany egyseggel erosited ezt a tulajdonsagot?");
        Scanner sc = new Scanner(System.in);
        String be = sc.next();

        int pont = 0;
        try{
            pont = Integer.parseInt(be);
        } catch (NumberFormatException e){
            System.out.println("Szamot irj be!");
        }
        int osszesAktualisAr = 0;
        boolean lehet = true;

        if(pont > 0) {
            for (int i = start; i < start + pont; i++) {
                if (i < arak.size()) {
                    osszesAktualisAr += arak.get(i);
                } else {
                    lehet = false;
                }
            }
        } else{
            lehet = false;
        }

        if(lehet) {
            if (az.equals("1")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getTamadas() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setTamadas(getTamadas() + pont);
                        System.out.println("Tamadasod mostantol " + getTamadas() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            if (az.equals("2")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getVedekezes() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setVedekezes(getVedekezes() + pont);
                        System.out.println("Vedekezesed mostantol " + getVedekezes() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            if (az.equals("3")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getVarazsero() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setVarazsero(getVarazsero() + pont);
                        System.out.println("Varazserod mostantol " + getVarazsero() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            if (az.equals("4")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getTudas() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setTudas(getTudas() + pont);
                        System.out.println("Tudasod mostantol " + getTudas() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            if (az.equals("5")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getMoral() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setMoral(getMoral() + pont);
                        System.out.println("Moralod mostantol " + getMoral() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            if (az.equals("6")) {
                if (Elokeszit.getArany() >= osszesAktualisAr) {
                    if(getSzerencse() + pont <= 10) {
                        Elokeszit.setArany(Elokeszit.getArany() - osszesAktualisAr);
                        setSzerencse(getSzerencse() + pont);
                        System.out.println("Szerencsed mostantol " + getSzerencse() + " erossegu. " + Elokeszit.getArany() + " aranyad maradt!");
                    } else{
                        System.out.println("Maximum 10 erossegu lehet egy tulajdonsag!");
                    }
                } else {
                    System.out.println("Nincs eleg aranyad: " + Elokeszit.getArany() + "/" + osszesAktualisAr);
                }
            }
            System.out.println("Ha vasarolnal meg tulajdonsagpontokat, ird be a tulajdonsag sorszamat, ha vegeztel, ird be a 0 (nulla) karaktert!");

            return start+pont;
        } else{
            System.out.println("Erre mar nincs eleg aranyad!");
            System.out.println("Ha vasarolnal meg tulajdonsagpontokat, ird be a tulajdonsag sorszamat, ha vegeztel, ird be a 0 (nulla) karaktert!");
            return start;
        }
    }

    public int getTamadas() {
        return tamadas;
    }

    public void setTamadas(int tamadas) {
        this.tamadas = tamadas;
    }

    public int getVedekezes() {
        return vedekezes;
    }

    public void setVedekezes(int vedekezes) {
        this.vedekezes = vedekezes;
    }

    public int getVarazsero() {
        return varazsero;
    }

    public void setVarazsero(int varazsero) {
        this.varazsero = varazsero;
    }

    public int getTudas() {
        return tudas;
    }

    public void setTudas(int tudas) {
        this.tudas = tudas;
    }

    public int getMoral() {
        return moral;
    }

    public void setMoral(int moral) {
        this.moral = moral;
    }

    public int getSzerencse() {
        return szerencse;
    }

    public void setSzerencse(int szerencse) {
        this.szerencse = szerencse;
    }
}