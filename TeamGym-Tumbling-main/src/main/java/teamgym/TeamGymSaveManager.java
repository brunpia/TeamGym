// Ansvar: lagre til fil og lese fra fil.
// Ingen GUI her, ingen poengregning her.

package teamgym;

import java.io.*;
import java.util.List;

// ---------------- LAGRING ----------------

public class TeamGymSaveManager implements Saveable {

    public void lagreTilFil(TeamGymCalculator calculator, String filnavn) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filnavn));

        String[] serier = {"forover", "bakover", "mix"};

        for (String serie : serier) {
            for (int gymnastNr = 1; gymnastNr <= 3; gymnastNr++) {
                List<String> elementer = calculator.hentElementer(serie, gymnastNr);

                String linje = serie + ";" + gymnastNr + ";" + String.join(",", elementer);
                writer.println(linje);
            }
        }

        writer.close();
    }

// ---------------- INNLESING ----------------

    public void lesFraFil(TeamGymCalculator calculator, String filnavn) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filnavn));

        calculator.nullstillAlt();

        String linje;
        while ((linje = reader.readLine()) != null) {
            String[] deler = linje.split(";");

            String serie = deler[0];
            int gymnastNr = Integer.parseInt(deler[1]);

            if (deler.length > 2 && !deler[2].isEmpty()) {
                String[] elementer = deler[2].split(",");

                for (String element : elementer) {
                    calculator.leggTilElement(serie, gymnastNr, element);
                }
            }
        }

        reader.close();
    }
}