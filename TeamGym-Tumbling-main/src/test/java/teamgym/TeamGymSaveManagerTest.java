package teamgym;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamGymSaveManagerTest {

    private TeamGymCalculator calculator;
    private TeamGymSaveManager saveManager;
    private final String filnavn = "test_teamgym_lagring.txt";

    @BeforeEach
    public void setup() {
        calculator = new TeamGymCalculator();
        saveManager = new TeamGymSaveManager();

        // Sletter gammel testfil hvis den finnes
        File fil = new File(filnavn);
        if (fil.exists()) {
            fil.delete();
        }
    }

    @Test
    public void testLagreTilFil() throws IOException {
        // Legger inn noen data
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");
        calculator.leggTilElement("bakover", 2, "araber");

        // Lagrer til fil
        saveManager.lagreTilFil(calculator, filnavn);

        // Sjekker at filen finnes
        File fil = new File(filnavn);
        assertTrue(fil.exists());

        // Leser innholdet og sjekker at det ikke er tomt
        String innhold = Files.readString(Path.of(filnavn));
        assertFalse(innhold.isEmpty());

        // Sjekker at noen forventede verdier faktisk er med
        assertTrue(innhold.contains("forover;1;hjul,stift"));
        assertTrue(innhold.contains("bakover;2;araber"));
    }

    @Test
    public void testLesFraFil() throws IOException {
        // Legger inn noen data i første calculator
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");
        calculator.leggTilElement("mix", 3, "salto");

        // Lagrer til fil
        saveManager.lagreTilFil(calculator, filnavn);

        // Lager en ny calculator som skal lese fra fil
        TeamGymCalculator nyCalculator = new TeamGymCalculator();

        // Leser fra fil
        saveManager.lesFraFil(nyCalculator, filnavn);

        // Sjekker at dataene kom riktig inn
        assertEquals(2, nyCalculator.hentElementer("forover", 1).size());
        assertEquals("hjul", nyCalculator.hentElementer("forover", 1).get(0));
        assertEquals("stift", nyCalculator.hentElementer("forover", 1).get(1));

        assertEquals(1, nyCalculator.hentElementer("mix", 3).size());
        assertEquals("salto", nyCalculator.hentElementer("mix", 3).get(0));
    }
}