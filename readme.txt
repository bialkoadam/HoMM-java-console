A program belépési pontja (main) a Main osztályban található.
Program lefordítása és futtatása történhet az IDEA-ban vagy terminálon keresztül a megfelelő könyvtárakba való belépés után az alábbi parancsokkal:
javac Main.java
java Main

Nehézségi szint:
A fordítás és futtatás után a játék nehézségét kell kiválasztani a megfelelő sorszám beírásával:
Könnyű: 1
Közepes: 2
Nehéz: 3

Tulajdonságpontok vásárlása:
Először be kell írni a tulajdonságra utaló sorszámot, mely a konzolon kerül részletezésre. A 0 sorszám beírásakor a tulajdonságpontok vásárlásának fázisa lezárul.
A sorszám beírása után kell beírni azt a mennyiséget, hogy mennyi tulajdonságpontot szeretnénk a megadott tulajdonságból vásárolni.
A tulajdonságpont sikeres/sikertelen vásárlása után új tulajdonságpont megvásárlásához újra be kell írni az adott tulajdonságra utaló sorszámot. A vásárlás befejezéséhez a 0 számot.

Varázslatok vásárlása:
Varázslatokból típusonként csak egyet lehet vásárolni.
A vásárlás a varázslatra utaló sorszám beírásával történik. A befejezés a 0 sorszám beírásával zárul le.

Egységek vásárlása:
Az egységtípus kiválasztásához a konzolon megjelenő sorszámot kell beírni. A szám beírása után kéri be a program a mennyiséget, itt is egy számot kell beírni.
A vásárlás után új/korábban vásárolt egység vásárlásához újra be kell írni az egységre utaló sorszámot. A 0 szám beírásával véget ér a vásárlás fázisa.

Egységek elhelyezése:
A vásárlások után megjelenik a csatatér. A program minden vásárolt egységtípushoz bekéri a koordinátát, hogy hova helyezze el az adott egységet.
Helyes koordináta: sor azonosító betűje mellett az oszlop száma (pl.: C2)
A helyes koordinátát kell beírni a konzolba.
A csatatéren a kiválasztott pozícióban láthatóvá válik az egység. A csatatér mezőben látott azonosító első betűje az egység azonosítója (nevének első betűje), mellette a birtokolt darabszám. Gép egység esetén csillag karakter található a végén.

Csata
Az elhelyezés után a gép is elhelyezi egységeit, majd elkezdődik a csata. Ekkor a csatatér felett láthatóvá válik az ellenfél tulajdonságpontjai és varázslatai is.
A támadás sorrendbe minden kör elején jelenik meg az azonosítókkal. A sorrendnek megfelelően jönnek az egységek.
A játékos egységeinél dönthet az egység használata (várakozás, mozgás, támadás) mellett, valamint a hős használata (támadás, varázslás) mellett.
Az adott cselekvés a konzolon megjelenő megfelelő sorszám beírásával történik.
1 - Várakozás (az egység kimarad a körből, következő egység következik)
2 - Egység mozgatása 
	A szám beírása után be kell írni azt a helyes koordinátát, amely mezőre lépni akarunk az adott egységgel. Ha az adott mezőre nem tud lépni a sebessége miatt vagy ott már van egység, addig kéri a koordinátákat, míg a mozgás meg nem történik.
3 - Egység támadása
	A szám beírása után be kell írni azt a helyes koordinátát, amely pozíción lévő ellenséges egységet meg akarjuk támadni az adott egységgel.
	Távolsági harcos bármelyik ellenséges egységet megtámadhatja, ha szomszédjában nincs ellenséges egység.
	Közelharci harcos csak szomszédos mezőben lévő egységet tud megtámadni.
	Az egység kimarad, ha a támadás meghiúsul (közelharci harcos közelében nincs ellenséges egység/az íjász közelében ellenfél van).
4 - Hős támadás
	Ha a körben még nem használtuk a hősünket, a szám beírása után azt a koordinátát kell beírni, ahol a megtámandó ellenséges egység van.
5 - Hős varázslása
	Ha a körben még nem használtuk a hősünket, varázsláshoz az ötös számot kell beírni. A beírás után megjelennek a varázslatok, ezután azt a számot kell beírni, amelyik varázslatot használni akarjuk (és van belőle). A szám beírása után kiválasztódik a varázslat. Feltámasztás esetén azt a helyes koordinátát kell beírni, ahol a feltámasztandó, gyógyítandó saját egység van, a többi varázslatnál az ellenséges egység koordinátáját kell beírni.


A játék addig tart, míg mindkét játékosnak van legalább 1 élő egysége. Ha csak az egyik játékosnak van élő egysége, akkor a játék véget ér, s az élő egységet birtokló játékos nyer.
Döntetlennel zárul a játék, ha az utolsó élő egységek egyszerre halnak meg mindkét félnél.


Átok varázslat: A kiválasztott ellenséges egység csak az aktuális körben biztosan minimumsebzését fogja okozni (visszatámadás esetén is).
Gyengítés varázslat: A kiválasztott ellenséges egységre varázserő * 15 sebzést okoz + csak az aktuális körben 50%-kal kevesebbet sebez (visszatámadáskor is).

Ogre egység: ár: 40, minimum sebzés: 2, maximum sebzés 6, életerő 60, sebesség 3, kezdeményezés 2. Speciális tulajdonság: elrettenti az ellenfeleit, így azok nem támadnak vissza
Lovag egység: ár: 20, minimum sebzés: 4, maximum sebzés 15, életerő 30, sebesség 3, kezdeményezés 12. Speciális tulajdonság: 20%-kal kevesebbet sebeznek ellene az egységek