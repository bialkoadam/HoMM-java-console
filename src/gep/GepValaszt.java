package gep;

import elokeszit.*;

import java.util.Random;

/**
 * Ebben az osztalyban van megvalositva a gep tulajdonsagpontjainak, varazslatainak, egysegeinek megvasarlasa
 */
public class GepValaszt {
    /**
     * A gepnek vasarol tulajdonsagpontokat
     * @param gep a gep hose, neki osztunk ki tulajdonsagpontokat
     * @return a megmaradt aranymennyiseget adja vissza
     */
    public static int tulajdonsagpontVasar(Hos gep) {
        int arany = 1000; //1000 aranya van
        int min = 20;
        int max = 40;
        Random random = new Random();
        int ertek = random.nextInt(max - min) + min; //a tulajdonsagpontokra aranyanak legalabb 20, max 40%-at kolti el
        int elkolthetoTul = arany*ertek/100; //tulajdonsagpontokra elkoltheto
        int elkolthetoTulKalk = elkolthetoTul; //szetoszthato pont kalkulalasara

        double ertekKalk = 5;
        int tulDb = 0;

        while(elkolthetoTulKalk > 0){
            if(tulDb == 0){
                elkolthetoTulKalk -= 5;
                tulDb++;
            }else {
                ertekKalk = (int) Math.ceil(ertekKalk * 1.1);
                if(elkolthetoTulKalk - ertekKalk >= 0) {
                    elkolthetoTulKalk -= ertekKalk;
                    tulDb++;
                } else{
                    break;
                }
            }
        }


        int[] tulAr = new int[tulDb];
        tulAr[0] = 5;

        tulDb = 0;
        ertekKalk = 5;

        while(elkolthetoTul > 0){
            if(tulDb == 0){
                elkolthetoTul -= 5;
                tulDb++;
            }else {
                ertekKalk = (int) Math.ceil(ertekKalk * 1.1);
                if(elkolthetoTul - ertekKalk >= 0) {
                    elkolthetoTul -= ertekKalk;
                    tulAr[tulDb] = (int)ertekKalk;
                    tulDb++;
                } else{
                    break;
                }
            }
        }

        arany = arany - (arany*ertek/100) + elkolthetoTul; // utolso tulajdonsagpont vasarlasakor marad meg valamennyi elkoltheto arany

        max = 6;
        min = 1;

        for (int i = 0; i < tulDb; i++) {
            int mitFejleszt = random.nextInt(max) + min; //1-6-ig valaszt (tamadastol-szerencseig sorban, s azt erositi egy ponttal)
            fejleszt(gep, mitFejleszt);
        }
        return arany;
    }

    /**
     * A gepnek vasarolja meg a varazslatait, a megmaradt aranyanak 20%-at, maximum 40%-at kolti ra
     * @param arany az elozoleg megmaradt aranymennyiseg
     * @param villamcsapas megvasarolhato varazslat
     * @param tuzlabda megvasarolhato varazslat
     * @param feltamasztas megvasarolhato varazslat
     * @param atok megvasarolhato varazslat
     * @param gyengites megvasarolhato varazslat
     * @return a megmaradt aranymennyiseget adja vissza
     */
    public static int varazslatotVesz(int arany, Varazslat villamcsapas, Varazslat tuzlabda, Varazslat feltamasztas, Varazslat atok, Varazslat gyengites) {
        int min = 20;
        int max = 40;
        Random random = new Random();
        int ertek = random.nextInt(max - min) + min; //megmaradt aranyanak 20% es 40% kozotti ertekeert vesz varazslato(ka)t
        int elkolthet = arany*ertek/100;

        int kiMin = 60; //60 a minimum varazslat ara, valtozik, ha a varazslatbol mar van
        while(elkolthet >= kiMin){
            int randomVarazslat = random.nextInt(5-1)+1; //varazslat sorszama
            switch(randomVarazslat){
                case 1:
                    if (villamcsapas.getAr() <= elkolthet) {
                        if(villamcsapas.getDarabGep() == 0) {
                            villamcsapas.setDarabGep(villamcsapas.getDarabGep() + 1);
                            elkolthet -= villamcsapas.getAr();
                            arany -= villamcsapas.getAr();
                        }
                    }
                    break;
                case 2:
                    if (tuzlabda.getAr() <= elkolthet) {
                        if(tuzlabda.getDarabGep() == 0) {
                            tuzlabda.setDarabGep(tuzlabda.getDarabGep() + 1);
                            elkolthet -= tuzlabda.getAr();
                            arany -= tuzlabda.getAr();
                        }
                    }
                    break;
                case 3:
                    if (feltamasztas.getAr() <= elkolthet) {
                        if(feltamasztas.getDarabGep() == 0) {
                            feltamasztas.setDarabGep(feltamasztas.getDarabGep() + 1);
                            elkolthet -= feltamasztas.getAr();
                            arany -= feltamasztas.getAr();
                        }
                    }
                    break;
                case 4:
                    if (atok.getAr() <= elkolthet) {
                        if(atok.getDarabGep() == 0) {
                            atok.setDarabGep(atok.getDarabGep() + 1);
                            elkolthet -= atok.getAr();
                            arany -= atok.getAr();
                        }
                    }
                    break;
                case 5:
                    if (gyengites.getAr() <= elkolthet) {
                        if(gyengites.getDarabGep() == 0) {
                            gyengites.setDarabGep(gyengites.getDarabGep() + 1);
                            elkolthet -= gyengites.getAr();
                            arany -= gyengites.getAr();
                        }
                    }
                    break;
                default:
                    break;
            }

            if(villamcsapas.getDarabGep() > 0){
                kiMin = 100;
            }
            if(villamcsapas.getDarabGep() > 0 && atok.getDarabGep() > 0){
                kiMin = 120;
            }

        }
        return arany;
    }

    /**
     * Kivalasztja veletlenszammal, hogy milyen egyseget vegyen az egysegetVesz2 metodus
     * @param arany a felhasznalhato aranymennyiseg
     * @param foldmuves megvasarolhato egyseg
     * @param ijasz megvasarolhato egyseg
     * @param griff megvasarolhato egyseg
     * @param ogre megvasarolhato egyseg
     * @param lovag megvasarolhato egyseg
     * @return a megmaradt aranyat adja vissza
     */
    public static int egysegetVesz(int arany, Egyseg foldmuves, Egyseg ijasz, Egyseg griff, Egyseg ogre, Egyseg lovag){
        Random random = new Random();
        int min = 1;
        int max = 100;
        /*

        a veletlenszeru szam arak alapjan sulyozva intervallumokba kerul:
        1-40: foldmuves
        41-70: ijasz
        71-80: griff
        81-84: ogre
        85-100: lovag

         */
        while(arany > 20) {
            int ertek = random.nextInt(max - min) + min;
            if (ertek <= 40) {
                arany = egysegetVesz2(arany, foldmuves);
            } else if (ertek <= 70) {
                arany = egysegetVesz2(arany, ijasz);
            } else if (ertek <= 80) {
                arany = egysegetVesz2(arany, griff);
            } else if (ertek <= 81) {
                arany = egysegetVesz2(arany, ogre);
            } else {
                arany = egysegetVesz2(arany, lovag);
            }
        }
        return arany;
    }

    /**
     * A tulajdonsagpontVasar metodusban kivalasztott tulajdonsagpontot noveli eggyel
     * @param gep a gep szamara noveli a kapott tulajdonsagpontot egyet
     * @param mit - tulajdonsagpont, ezt noveli eggyel
     */
    public static void fejleszt(Hos gep, int mit){
        switch(mit){
            case 1: gep.setTamadas(gep.getTamadas() + 1); break;
            case 2: gep.setVedekezes(gep.getVedekezes() + 1); break;
            case 3: gep.setVarazsero(gep.getVarazsero() + 1); break;
            case 4: gep.setTudas(gep.getTudas() + 1); break;
            case 5: gep.setMoral(gep.getMoral() + 1); break;
            case 6: gep.setSzerencse(gep.getSzerencse() + 1); break;
        }
    }

    /**
     * Az egysegetVesz metodusban kivalasztott egyseg megvasarlasa kerul megvalositasra ebben a metodusban
     * @param arany a jelenlegi aranymennyiseg
     * @param egyseg melyik egyseg lett kivalasztva, ezt vasarolja meg
     * @return visszaadja a meg megmaradt aranymennyiseget
     */

    public static int egysegetVesz2(int arany, Egyseg egyseg){
        if(arany >= egyseg.getAr()){
            arany -= egyseg.getAr();
            egyseg.setDarabGep(egyseg.getDarabGep() + 1);
            egyseg.setEleteroGepOsszesen(egyseg.getDarabGep() * egyseg.getEletEro());
            egyseg.setEredetiGepDb(egyseg.getEredetiGepDb() + 1);
            return arany;
        } else{
            return 0;
        }
    }
}
