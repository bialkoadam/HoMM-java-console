package elokeszit;

import java.util.Scanner;

/**
 * A jatek nehezsegenek kivalasztasara szolgal, beallitja a nehezseghez tartozo aranymennyiseget
 */
public class Elokeszit {
    private static String nehezseg; //konnyu vagy kozepes vagy nehez
    private static int arany;
    public static void nehezseg(){
        System.out.println("Valassz nehezseget! Ird be a nehezseghez kapcsolodo szamot!  1: Konnyu  2: Kozepes  3: Nehez");
        Scanner sc = new Scanner(System.in);
        String be = sc.next();
        if(be.equals("1")){
            nehezseg = "konnyu";
            arany = 1300;
        } else if(be.equals("2")){
            nehezseg = "kozepes";
            arany = 1000;
        } else if(be.equals("3")){
            nehezseg = "nehez";
            arany = 700;
        } else{
            System.out.println("HIBAS ERTEK!");
            Elokeszit.nehezseg();
        }
    }

    public static void setArany(int arany) {
        Elokeszit.arany = arany;
    }

    public static String getNehezseg() {
        return nehezseg;
    }

    public static int getArany() {
        return arany;
    }
}
