# TeamGym-Tumbling

TeamGym-Tumbling er en JavaFX-applikasjon utviklet i prosjektet i **TDT4100 – Objektorientert programmering**.  
Appen brukes til å registrere tumblingserier for tre gymnaster i TeamGym, og regner automatisk ut poeng, trekk og totalsum.

## Hva appen gjør

Brukeren kan:
- velge serie: **forover**, **bakover** eller **mix**
- velge gymnast: **1, **2** eller **3**
- legge til elementer i serien
- fjerne siste element
- beregne poeng og trekk
- nullstille registrerte data
- lagre til fil
- lese inn tidligere lagret data ved oppstart

Appen viser poeng for hver gymnast i hver serie, samt trekk og total poengsum.

## Prosjektstruktur

Prosjektet består av følgende hovedklasser:

### `TeamGymApp`
Starter JavaFX-applikasjonen og laster inn FXML-filen.

### `TeamGymController`
Håndterer brukerinput fra GUI-et og oppdaterer visningen.  
Denne klassen fungerer som bindeledd mellom brukergrensesnittet og modellklassene.

### `TeamGymCalculator`
Inneholder logikken i programmet.  
Denne klassen:
- lagrer serier og elementer
- regner ut seriepoeng
- regner ut totalpoeng
- regner ut trekk
- sjekker regler som manglende serier og like serier

### `TeamGymSaveManager`
Har ansvar for lagring til og lesing fra fil.

### `Saveable`
Et interface som definerer metodene for lagring og innlesing av data.  
`TeamGymSaveManager` implementerer dette interfacet.

## Hvordan appen er bygget opp

Prosjektet er bygget etter **Model-View-Controller-prinsippet (MVC)**:

- **Model:** `TeamGymCalculator` og `TeamGymSaveManager`
- **View:** `TeamGym.fxml`
- **Controller:** `TeamGymController`

Dette gir et tydelig skille mellom logikk, brukergrensesnitt og håndtering av brukerinput.

## Lagring

Appen kan lagre data til tekstfil og lese dem inn igjen senere.  
Lagringen skjer i filen:

`teamgym_lagring.txt`

Formatet i filen er:
```text
serie;gymnastNr;element1,element2,element3