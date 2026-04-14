package teamgym;

import java.io.IOException;

public interface Saveable {
    void lagreTilFil(TeamGymCalculator calculator, String filnavn) throws IOException;
    void lesFraFil(TeamGymCalculator calculator, String filnavn) throws IOException;
}