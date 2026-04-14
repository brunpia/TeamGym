package teamgym;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TeamGymCalculatorTest {

    private TeamGymCalculator calculator;

    @BeforeEach
    public void setup() {
        calculator = new TeamGymCalculator();
    }

    @Test // 1
    public void testLeggTilElement() {
        calculator.leggTilElement("forover", 1, "hjul");

        assertEquals(1, calculator.hentElementer("forover", 1).size());
        assertEquals("hjul", calculator.hentElementer("forover", 1).get(0));
    }

    @Test // 2
    public void testFjernSisteElement() {
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");

        calculator.fjernSisteElement("forover", 1);

        assertEquals(1, calculator.hentElementer("forover", 1).size());
        assertEquals("hjul", calculator.hentElementer("forover", 1).get(0));
    }

    @Test // 3
    public void testRegnUtSeriePoeng() {
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");
        calculator.leggTilElement("forover", 1, "salto");
        assertEquals(0.5, calculator.regnUtSeriePoeng("forover", 1));
    }

    @Test // 4
    public void testNullstillAlt() {
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("bakover", 2, "back");

        calculator.nullstillAlt();

        assertTrue(calculator.hentElementer("forover", 1).isEmpty());
        assertTrue(calculator.hentElementer("bakover", 2).isEmpty());
    }

    @Test // 5
    public void testErLikSerieTrue() {
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 2, "hjul");
        calculator.leggTilElement("forover", 3, "hjul");

        assertTrue(calculator.erLikSerie("forover"));
    }

    @Test // 6
    public void testErLikSerieFalse() {
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 2, "stift");
        calculator.leggTilElement("forover", 3, "hjul");

        assertFalse(calculator.erLikSerie("forover"));
    }

    @Test  // 7
    public void testRegnUtTrekkIngenObligatoriskLikSerie() {
        // forover
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");
        calculator.leggTilElement("forover", 1, "salto");

        calculator.leggTilElement("forover", 2, "hjul");
        calculator.leggTilElement("forover", 2, "stift");
        calculator.leggTilElement("forover", 2, "salto_skru");

        calculator.leggTilElement("forover", 3, "hjul");
        calculator.leggTilElement("forover", 3, "salto");
        calculator.leggTilElement("forover", 3, "dobbel_salto");

        // bakover
        calculator.leggTilElement("bakover", 1, "araber");
        calculator.leggTilElement("bakover", 1, "flikk");
        calculator.leggTilElement("bakover", 1, "back");

        calculator.leggTilElement("bakover", 2, "araber");
        calculator.leggTilElement("bakover", 2, "flikk");
        calculator.leggTilElement("bakover", 2, "back_skru");

        calculator.leggTilElement("bakover", 3, "araber");
        calculator.leggTilElement("bakover", 3, "back");
        calculator.leggTilElement("bakover", 3, "dobbel_back");

        // mix
        calculator.leggTilElement("mix", 1, "hjul");
        calculator.leggTilElement("mix", 1, "flikk");
        calculator.leggTilElement("mix", 1, "back");

        calculator.leggTilElement("mix", 2, "stift");
        calculator.leggTilElement("mix", 2, "flikk");
        calculator.leggTilElement("mix", 2, "back_skru");

        calculator.leggTilElement("mix", 3, "salto");
        calculator.leggTilElement("mix", 3, "back");
        calculator.leggTilElement("mix", 3, "dobbel_back");

        assertEquals(0.4, calculator.regnUtTrekk(), 0.001);
    }

    @Test // 8
    public void testRegnUtTrekkManglendeElementer() {
        // forover
        calculator.leggTilElement("forover", 1, "hjul");

        calculator.leggTilElement("forover", 2, "hjul");
        calculator.leggTilElement("forover", 2, "stift");
        calculator.leggTilElement("forover", 2, "salto_skru");

        calculator.leggTilElement("forover", 3, "hjul");
        calculator.leggTilElement("forover", 3, "salto");
        calculator.leggTilElement("forover", 3, "dobbel_salto");

        // bakover
        calculator.leggTilElement("bakover", 1, "araber");
        calculator.leggTilElement("bakover", 1, "flikk");
        calculator.leggTilElement("bakover", 1, "back");

        calculator.leggTilElement("bakover", 2, "araber");
        calculator.leggTilElement("bakover", 2, "flikk");
        calculator.leggTilElement("bakover", 2, "back");

        calculator.leggTilElement("bakover", 3, "araber");
        calculator.leggTilElement("bakover", 3, "flikk");
        calculator.leggTilElement("bakover", 3, "back");

        // mix
        calculator.leggTilElement("mix", 1, "hjul");
        calculator.leggTilElement("mix", 1, "flikk");
        calculator.leggTilElement("mix", 1, "back");

        calculator.leggTilElement("mix", 2, "stift");
        calculator.leggTilElement("mix", 2, "flikk");
        calculator.leggTilElement("mix", 2, "back_skru");

        calculator.leggTilElement("mix", 3, "salto");
        calculator.leggTilElement("mix", 3, "back");
        calculator.leggTilElement("mix", 3, "dobbel_back");

        assertEquals(0.6, calculator.regnUtTrekk(), 0.001);
    }

    @Test // 9
    public void testRegnUtTotalPoeng() {
        // forover
        calculator.leggTilElement("forover", 1, "hjul");
        calculator.leggTilElement("forover", 1, "stift");
        calculator.leggTilElement("forover", 1, "salto");

        calculator.leggTilElement("forover", 2, "hjul");
        calculator.leggTilElement("forover", 2, "stift");
        calculator.leggTilElement("forover", 2, "salto_skru");

        calculator.leggTilElement("forover", 3, "hjul");
        calculator.leggTilElement("forover", 3, "salto");
        calculator.leggTilElement("forover", 3, "dobbel_salto");

        // bakover
        calculator.leggTilElement("bakover", 1, "araber");
        calculator.leggTilElement("bakover", 1, "flikk");
        calculator.leggTilElement("bakover", 1, "back");

        calculator.leggTilElement("bakover", 2, "araber");
        calculator.leggTilElement("bakover", 2, "flikk");
        calculator.leggTilElement("bakover", 2, "back_skru");

        calculator.leggTilElement("bakover", 3, "araber");
        calculator.leggTilElement("bakover", 3, "back");
        calculator.leggTilElement("bakover", 3, "dobbel_back");

        // mix
        calculator.leggTilElement("mix", 1, "hjul");
        calculator.leggTilElement("mix", 1, "flikk");
        calculator.leggTilElement("mix", 1, "back");

        calculator.leggTilElement("mix", 2, "stift");
        calculator.leggTilElement("mix", 2, "flikk");
        calculator.leggTilElement("mix", 2, "back_skru");

        calculator.leggTilElement("mix", 3, "salto");
        calculator.leggTilElement("mix", 3, "back");
        calculator.leggTilElement("mix", 3, "dobbel_back");

        assertEquals(7.5, calculator.regnUtTotalPoeng(), 0.001);
    }
}
