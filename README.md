# Sokoban - G85
Dit project is de indiening voor Project I binnen de opleiding TILE voor groep 85.

Groep 85 bestaat uit:
- Yarick Coppejans
- Christof Remeysen
- Wim Straetemans


## Opstarten
Het spel start standaard in GUI modus op, tenzij er een eerste launch argument wordt meegegeven genaamd "CLI", dan gaat de applicatie over naar CLI modus.


## Admin functies
Enkel een administrator kan een nieuw spel en spelbord aanmaken. Voorgemaakte admin account: wim.straetemans@student.hogent.be / HoGent123


## Spel spelen
Het mannetje wordt bestuurd via de pijltjestoetsen. Een kist kan enkel vooruit geduwd worden, wanneer het veld erachter geen kist of muur bevat.


## Regels spelbord aanmaken
De volgende regels gelden voor het aanmaken van een spelbord:
* Er moet minstens één doel en één kist zijn
* Er moet exact één mannetje zijn
* Het speelveld moet afgebakend zijn door een gesloten muur (dit wil zeggen elke muur grenst aan twee andere muren)
* Buiten de rand van het speelveld mogen geen velden of objecten voorkomen
* Binnen de rand mogen geen ongedefinieerde velden voorkomen
* Het mannetje en de doelen mogen niet ingesloten worden door muren
* Een kist mag niet in een hoek geplaatst worden (want dan is ze niet meer verplaatsbaar)
