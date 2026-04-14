// Ansvar: ta imot knappetrykk og oppdatere skjermen.
// Denne skal koble GUI til logikken, men ikke gjøre selve beregningene.

package teamgym;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class TeamGymController {

// ---------------- FELTER ----------------

    private TeamGymCalculator calculator = new TeamGymCalculator();
    private TeamGymSaveManager saveManager = new TeamGymSaveManager();
    private String valgtSerie = null;
    private int valgtGymnast = 0;

// ---------------- FXML-KNAPPER OG LABELS ----------------

    @FXML private Label g1fPoeng;
    @FXML private Label g2fPoeng;
    @FXML private Label g3fPoeng;

    @FXML private Label g1fElementer;
    @FXML private Label g3fElementer;
    @FXML private Label g2fElementer;

    @FXML private Label g1bPoeng;
    @FXML private Label g2bPoeng;
    @FXML private Label g3bPoeng;

    @FXML private Label g1bElementer;
    @FXML private Label g2bElementer;
    @FXML private Label g3bElementer;

    @FXML private Label g2mPoeng;
    @FXML private Label g3mPoeng;
    @FXML private Label g1mPoeng;

    @FXML private Label g1mElementer;
    @FXML private Label g2mElementer;
    @FXML private Label g3mElementer;

    @FXML private Label trekkLabel;
    @FXML private Label totalLabel;

    @FXML private Button foroverserieKnapp;
    @FXML private Button bakoverserieKnapp;
    @FXML private Button mixserieKnapp;

    @FXML private Button gymnast1Knapp;
    @FXML private Button gymnast2Knapp;
    @FXML private Button gymnast3Knapp;

    @FXML private Button hjulKnapp;
    @FXML private Button stiftKnapp;
    @FXML private Button saltoKnapp;
    @FXML private Button saltoSkruKnapp;
    @FXML private Button dobbelSaltoKnapp;

    @FXML private Button araberKnapp;
    @FXML private Button flikkKnapp;
    @FXML private Button backKnapp;
    @FXML private Button backSkruKnapp;
    @FXML private Button dobbelBackKnapp;
    @FXML private Button beregnKnapp;

// ---------------- INITIALISERING ----------------

    @FXML
    public void initialize() {
        try {
            saveManager.lesFraFil(calculator, "teamgym_lagring.txt");
            oppdaterVisning();
            oppdaterTrekk();
            oppdaterTotal();
        } catch (Exception e) {
        }
    }

// ---------------- HJELPEMETODER FOR GUI ----------------

    private void oppdaterVisning() {
        // Foroverserie poeng
        g1fPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("forover", 1)));
        g2fPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("forover", 2)));
        g3fPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("forover", 3)));

        // Foroverserie elementer
        g1fElementer.setText(calculator.hentElementerSomTekst("forover", 1));
        g2fElementer.setText(calculator.hentElementerSomTekst("forover", 2));
        g3fElementer.setText(calculator.hentElementerSomTekst("forover", 3));

        // Bakoverserie poeng
        g1bPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("bakover", 1)));
        g2bPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("bakover", 2)));
        g3bPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("bakover", 3)));

        // Bakoverserie elementer
        g1bElementer.setText(calculator.hentElementerSomTekst("bakover", 1));
        g2bElementer.setText(calculator.hentElementerSomTekst("bakover", 2));
        g3bElementer.setText(calculator.hentElementerSomTekst("bakover", 3));

        // Mixserie poeng
        g1mPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("mix", 1)));
        g2mPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("mix", 2)));
        g3mPoeng.setText(String.valueOf(calculator.regnUtSeriePoeng("mix", 3)));

        // Mixserie elementer
        g1mElementer.setText(calculator.hentElementerSomTekst("mix", 1));
        g2mElementer.setText(calculator.hentElementerSomTekst("mix", 2));
        g3mElementer.setText(calculator.hentElementerSomTekst("mix", 3));
    
    }

    private void oppdaterTrekk() {
        trekkLabel.setText(calculator.hentTrekkTekst());
    }

    private void oppdaterTotal() {
        double total = calculator.regnUtTotalPoeng() - calculator.regnUtTrekk();
        double avrundetTotal = Math.round(total * 10.0) / 10.0;

        totalLabel.setText("Totalt " + avrundetTotal + " poeng");

        if (avrundetTotal < 0) {
            totalLabel.setStyle("-fx-text-fill: red;");
        } else {
            totalLabel.setStyle("-fx-text-fill: black;");
        }
    }

    private void markerValgtSerie() {
        // Nullstill alle serieknapper
        foroverserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        bakoverserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        mixserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        // Marker valgt serie
        if ("forover".equals(valgtSerie)) {
            foroverserieKnapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        } else if ("bakover".equals(valgtSerie)) {
            bakoverserieKnapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        } else if ("mix".equals(valgtSerie)) {
            mixserieKnapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        }
    }

    private void markerValgtGymnast() {
        // Nullstill alle gymnastknapper
        gymnast1Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        gymnast2Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        gymnast3Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        // Marker valgt gymnast
        if (valgtGymnast == 1) {
            gymnast1Knapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        } else if (valgtGymnast == 2) {
            gymnast2Knapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        } else if (valgtGymnast == 3) {
            gymnast3Knapp.setStyle("-fx-background-color: #B6E2A1; -fx-text-fill: black;");
        }

    }

        private void flashKnapp(Button knapp) {
        knapp.setStyle("-fx-background-color: #006400; -fx-text-fill: black;");

        PauseTransition pause = new PauseTransition(Duration.millis(180));
        pause.setOnFinished(e -> knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;"));
        pause.play();
    }

// ---------------- SERIEVALG ----------------

    @FXML
    private void velgForover() {
        valgtSerie = "forover";
        markerValgtSerie();
    }

    @FXML
    private void velgBakover() {
        valgtSerie = "bakover";
        markerValgtSerie();
    }

    @FXML
    private void velgMixserie() {
        valgtSerie = "mix";
        markerValgtSerie();
    }

// ---------------- GYMNASTVALG ----------------

    @FXML
    private void velgGymnast1() {
        valgtGymnast = 1;
        markerValgtGymnast();
    }

    @FXML
    private void velgGymnast2() {
        valgtGymnast = 2;
        markerValgtGymnast();
    }

    @FXML
    private void velgGymnast3() {
        valgtGymnast = 3;
        markerValgtGymnast();
    }

// ---------------- ELEMENTKNAPPER ----------------

    private void leggTilElement(String element) {
        if (valgtSerie == null || valgtGymnast == 0) {
            return;
        }

        calculator.leggTilElement(valgtSerie, valgtGymnast, element);
        oppdaterVisning();
    }

    @FXML
    private void leggTilHjul() {
        leggTilElement("hjul");
        flashKnapp(hjulKnapp);
    }

    @FXML
    private void leggTilStift() {
        leggTilElement("stift");
        flashKnapp(stiftKnapp);
    }

    @FXML
    private void leggTilSalto() {
        leggTilElement("salto");
        flashKnapp(saltoKnapp);
    }

    @FXML
    private void leggTilSaltoSkru() {
        leggTilElement("salto_skru");
        flashKnapp(saltoSkruKnapp);
    }

    @FXML
    private void leggTilDobbelSalto() {
        leggTilElement("dobbel_salto");
        flashKnapp(dobbelSaltoKnapp);
    }

    @FXML
    private void leggTilAraber() {
        leggTilElement("araber");
        flashKnapp(araberKnapp);
    }

    @FXML
    private void leggTilFlikk() {
        leggTilElement("flikk");
        flashKnapp(flikkKnapp);
    }

    @FXML
    private void leggTilBack() {
        leggTilElement("back");
        flashKnapp(backKnapp);
    }

    @FXML
    private void leggTilBackSkru() {
        leggTilElement("back_skru");
        flashKnapp(backSkruKnapp);
    }    

    @FXML
    private void leggTilDobbelBack() {
        leggTilElement("dobbel_back");
        flashKnapp(dobbelBackKnapp);
    }  

// ---------------- BACKSPACE-KNAPPER ----------------

    @FXML
    private void velgBackspaceSerie() {
        valgtSerie = null;
        markerValgtSerie();   // fjerner grønn markering
        trekkLabel.setText("\n\nSerievalg nullstilt");
    }

    @FXML
    private void velgBackspaceGymnast() {
        valgtGymnast = 0;
        markerValgtGymnast();   // fjerner grønn markering
        trekkLabel.setText("\n\nGymnastvalg nullstilt");
    }

    @FXML
    private void velgBackspaceElement() {
        if (valgtSerie == null || valgtGymnast == 0) {
            trekkLabel.setText("\n\nVelg serie og gymnast først");
            return;
        }

        calculator.fjernSisteElement(valgtSerie, valgtGymnast);
        oppdaterVisning();   // oppdaterer poeng og elementtekst med en gang
    }

// ---------------- BEREGNING ----------------

    @FXML
    private void handleBeregn() {
        oppdaterVisning();
        oppdaterTrekk();
        oppdaterTotal();
        flashKnapp(beregnKnapp);
    }

// ---------------- MENYVALG ----------------

    @FXML
    private void velgLagre() {
        try {
            System.out.println("forover 1: " + calculator.hentElementer("forover", 1));
            System.out.println("forover 2: " + calculator.hentElementer("forover", 2));
            System.out.println("forover 3: " + calculator.hentElementer("forover", 3));

            saveManager.lagreTilFil(calculator, "teamgym_lagring.txt");
            trekkLabel.setText("\n\nLagret til fil");
            trekkLabel.setStyle("-fx-text-fill: black;");
        } catch (Exception e) {
            trekkLabel.setText("\n\nFeil ved lagring");
            trekkLabel.setStyle("-fx-text-fill: black;");
            e.printStackTrace();
        }
    }

    @FXML
    private void velgUtforsk() {
        trekkLabel.setText("\n\nUtforsk er ikke ferdig enda");
    }

    @FXML
    private void velgNullstill() {
        calculator.nullstillAlt();
        valgtSerie = null;
        valgtGymnast = 0;

        trekkLabel.setText("\n\nAlt ble nullstilt");
        trekkLabel.setStyle("-fx-text-fill: black;");

        totalLabel.setText("Totalt 0.0 poeng");
        totalLabel.setStyle("-fx-text-fill: black;");

        foroverserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        bakoverserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        mixserieKnapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        gymnast1Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        gymnast2Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        gymnast3Knapp.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        oppdaterVisning();
    }

    @FXML
    private void velgLoggUt() {
        trekkLabel.setText("\n\nLogg ut er ikke ferdig enda");
    }

}
