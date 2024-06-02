package terkep;

import elokeszit.*;

import java.util.Random;
import java.util.Scanner;

/**
 * Az egysegek elhelyezesere szolgal, ha a jatekosnak, illetve a gepnek egy adott egysegbol van legalabb 1 db, akkor jatekos eseten a bekert koordinatara, gep eseten random helyre (az utolso ket oszlopba) helyezi el az egysegeket
 */
public class Elhelyez {
    public static void elhelyezes(String[][] matrix, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        terkep(matrix);

        System.out.println("*********************");
        System.out.println("EGYSEGEK ELHELYEZESE");

        if(foldmuves.getDarab() >= 1){
            elhelyez(matrix, foldmuves);
        }
        if(ijasz.getDarab() >= 1) {
            elhelyez(matrix, ijasz);
        }
        if(griff.getDarab() >= 1){
            elhelyez(matrix, griff);
        }
        if(ogre.getDarab() >= 1){
            elhelyez(matrix, ogre);
        }
        if(lovag.getDarab() >= 1){
            elhelyez(matrix, lovag);
        }

        if(foldmuves.getDarabGep() >= 1){
            gepElhelyez(matrix, foldmuves);
        }
        if(ijasz.getDarabGep() >= 1) {
            gepElhelyez(matrix, ijasz);
        }
        if(griff.getDarabGep() >= 1){
            gepElhelyez(matrix, griff);
        }
        if(ogre.getDarabGep() >= 1){
            gepElhelyez(matrix, ogre);
        }
        if(lovag.getDarabGep() >= 1){
            gepElhelyez(matrix, lovag);
        }

        System.out.println(System.lineSeparator());
    }
    public static void elhelyez(String[][] matrix, Egyseg egyseg){
        if(egyseg.getDarab() > 0) {
            System.out.println("Helyezd el a terkepre a(z) " + egyseg.getDarab() + " darab " + egyseg.getNev() + " egysegedet! Ird be a koordinatat, ahova tenni akarod (pl. F2)");

            Scanner be = new Scanner(System.in);
            String a = be.next();

            char x_kar = a.charAt(0);
            int y = 0;
            if ((a.substring(1).length() > 0)) {
                try{
                    y = Integer.parseInt(a.substring(1));
                } catch (NumberFormatException e){
                    System.out.println("A betu utan szamot kell beirni");
                }
            } else {
                System.out.println("Hibas bemenet! Probald ujra");
                elhelyez(matrix, egyseg);
                return;
            }
            int x = x_kar - 'A' + 1; //karakter szamma alakitasa ABC -> 123

            if (x >= 1 && x <= 12 && y > 0 && y < 10) {
                if (y > 2 || y < 1) {
                    System.out.println("Csak az elso ket oszlopba teheted le az egysegeket! Irj be egy ennek megfelelo koordinatat!");
                    elhelyez(matrix, egyseg);
                } else if (x < 1 || x > 10) {
                    System.out.println("Ez a terkepen kivul esik, A es J kozotti koordinatat adj meg (nagybetuvel)!");
                    elhelyez(matrix, egyseg);
                } else if (!matrix[x - 1][y - 1].equals("-")) {
                    System.out.println("Itt mar van masik egyseg!");
                    elhelyez(matrix, egyseg);
                } else {
                    matrix[x - 1][y - 1] = egyseg.getId() + "" + egyseg.getDarab(); //x-1, y-1 - 0-tol kezdodik
                    terkep(matrix);
                }
            } else {
                System.out.println("Hibas bemenet! Probald ujra");
                elhelyez(matrix, egyseg);
            }
        }
    }

    public static void gepElhelyez(String[][] matrix, Egyseg egyseg){
        boolean sikerult = false;
        while(!sikerult) {
            Random random = new Random();
            int x = random.nextInt(10 - 1) + 1;
            int y = (Math.random() <= 0.5) ? 1 : 2;

            if (matrix[x - 1][12 - y].equals("-")) {
                matrix[x - 1][12 - y] = egyseg.getId() + "" + egyseg.getDarabGep() + "*"; //x-1, y-1 - 0-tol kezdodik
                sikerult = true;
            }
        }
    }

    public static void terkep(String[][] matrix){
        char[] tomb = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        for (int i = 1; i <= 12; i++){
            if(i == 1) {
                System.out.format("  | %4s |", i);
            } else{
                System.out.format(" %4s |", i);
            }
        }
        System.out.println("");

        for (int i = 0; i < 10; i++){
            System.out.print(tomb[i] + " ");
            for (int j = 0; j < 12; j++){
                if(j == 0) {
                    System.out.format("| %4s |", matrix[i][j]);
                } else{
                    System.out.format(" %4s |", matrix[i][j]);
                }
            }
            System.out.println("");
        }
    }
}
