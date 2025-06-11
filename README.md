# README.md

# Ülesanded

1. Edetabelit ei saa vaadata mängu ajal.
2. Mängulaua suurust ei saa muuta mängu ajal.
3. Mängu lõppede ei kao lõpp seis ekraanilt. (pilt peaks jääma kui nimi kirjas)
4. Kui muudetakse mängulauasuurust, siis eelmise mängu seis kustub (hetkel jooskseb kokku)
5. Kogu mängu aknal võiks olla miiniimum suurus, millest väiksemaks ei saa (10x10). (üks rida koodi...)
   (vt GameBoard failis  getPreferredSize())
6. "Eraldi aknas" pole valitud, siis ilmub tekst vaate peale, mitte eraldi message aknasse
   Koos Nupuga sulgemiseks!
   näiteks: edetabel kollases alas, siis muud elemendid enam ei paista
   näiteks: kolmas paneel java aknas, kus edetabel sees (paneel ainult edetabeli jaoks.. ?)
   näiteks olemasoleva JFrame peal eraldi, ise valin, kuidas teen... flow layoutmanager?

**Kui on küsimusi, siis küsida Õpetajalt Teamsis Chat ...** 


# Muudatused

**Programmi testimiseks** saab laevade asukohad värvida nähtavaks ja laevade arvu vähendada 
Tuleks teha muudatused: 
a) failis 'Game' kus saab laevade hulka muuta kui rida 11 (eemalda kommentaar) ja reale 12 (lisa kommentaar)
b) failis 'Model' saab muuta värvi kui asendada rida 101 (eemalda kommentaar) reaga 102 (lisa kommentaar)

1. "Edetabel" ja "Vali laua suurus" mitteaktiivsed mängu ajal. 
 (_'MyNewGameListener'_ - nupud mittekatiivseks mängu ajal  ning _'Controller'_ - tagasi aktiivseks).
2. Mängu pilt jääb ekraanile, kui mäng lõppeb ja küsitakse nime. 
  _'GameBoard'_ failis _paintComponent_ - if lausest eemaldasin '&&' osa. 
3. Kui muudetakse laua suurust, siis eelmine mäng eemaldatakse. _'Model'_ failis setteri lisamine
4. Mänguaknal miinimum suuruse seadmine 10*10. _'View'_ failis lisasin 'gameBoard.getPreferredSize'
5. Edetabel mängu akanas koos "Sulge" nupuga. Mis võimaldab vaadata edetabelit kuni vajutatakse "Sulge" nuppu.
_'View'_ failis - a) Lisatud uus paneel edetabeli vaatamiseks (_JPanel 'leaderboardPanelRef'_)
_'View'_ failis - b) Lisatud uus meetod - _(showLeaderboardInMainWindow(JPanel leaderboardPanel))_
_'MyScoreBoardListener'_ failis - muudetud meetodit _'actionPerformed'_ 
if koodi osas muudatus. Andmed loetakse kas failist või andmebaasist, sõltuvalt kus on valik.
6. Taustapilt. 
 Vasakule sinisele alale lisatud taustapilt "Background.jpg", mida näidatakse enne mänguga alustamist.
 failis _'GameBoard'_ JPanel-il ja _paintComponendis_ if lause muudatus - kui pildifail on olemas, siis näidatakse pilti, kui ei siis sinist värvi.



