// Ansvar: holde på dataene og regne ut poeng og trekk.
// Her skal all “teamgym-regel-logikk” ligge.

package teamgym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeamGymCalculator {

// ---------------- DATASTRUKTURER ----------------

    private final Map<String, Double> elementPoeng = new HashMap<>();
    private final Map<String, Map<Integer, List<String>>> serier = new HashMap<>();

// ---------------- DATASTRUKTURER ----------------

    // serier

    private void opprettSerier() {
        serier.put("forover", opprettGymnastMap());
        serier.put("bakover", opprettGymnastMap());
        serier.put("mix", opprettGymnastMap());
    }

    // elementPoeng

    private void leggTilElementPoeng() {
        elementPoeng.put("hjul", 0.1);
        elementPoeng.put("stift", 0.2);
        elementPoeng.put("salto", 0.2);
        elementPoeng.put("salto_skru", 0.4);
        elementPoeng.put("dobbel_salto", 1.2);

        elementPoeng.put("araber", 0.1);
        elementPoeng.put("flikk", 0.2);
        elementPoeng.put("back", 0.2);
        elementPoeng.put("back_skru", 0.4);
        elementPoeng.put("dobbel_back", 0.8);
    }

// ---------------- KONSTRUKTØR ----------------

    public TeamGymCalculator() {
        leggTilElementPoeng();
        opprettSerier();
    }

// ---------------- HJELPEMETODER FOR DATASTRUKTUR ----------------

    private Map<Integer, List<String>> opprettGymnastMap() {
        Map<Integer, List<String>> gymnaster = new HashMap<>();
        gymnaster.put(1, new ArrayList<>());
        gymnaster.put(2, new ArrayList<>());
        gymnaster.put(3, new ArrayList<>());
        return gymnaster;
    }

// ---------------- LEGG TIL / HENT / FJERN DATA ----------------

    public void leggTilElement(String serie, int gymnastNr, String element) {
        serier.get(serie).get(gymnastNr).add(element);
    }

    public void fjernSisteElement(String serie, int gymnastNr) {
        List<String> elementer = serier.get(serie).get(gymnastNr);

        if (!elementer.isEmpty()) {
            elementer.remove(elementer.size() - 1);
        }
    }

    public Set<String> hentSerier() {
        return serier.keySet();
    }

    public Set<Integer> hentGymnaster(String serie) {
        return serier.get(serie).keySet();
    }

    public void nullstillAlt() {

        serier.get("forover").get(1).clear();
        serier.get("forover").get(2).clear();
        serier.get("forover").get(3).clear();

        serier.get("bakover").get(1).clear();
        serier.get("bakover").get(2).clear();
        serier.get("bakover").get(3).clear();

        serier.get("mix").get(1).clear();
        serier.get("mix").get(2).clear();
        serier.get("mix").get(3).clear();
    }

    public void nullstillSerieForGymnast(String serie, int gymnastNr) {
        serier.get(serie).get(gymnastNr).clear();
    }

// ---------------- HENT DATA ----------------

    public List<String> hentElementer(String serie, int gymnastNr) {
        return serier.get(serie).get(gymnastNr);
    }

    public String hentElementerSomTekst(String serie, int gymnastNr) {
        return String.join(" ", serier.get(serie).get(gymnastNr));
    }

// ---------------- POENGBEREGNING ----------------

    public double regnUtSeriePoeng(String serie, int gymnastNr) {
        List<Double> poengListe = new ArrayList<>();

        for (String element : serier.get(serie).get(gymnastNr)) {
            poengListe.add(elementPoeng.getOrDefault(element, 0.0));
        }

        poengListe.sort(Collections.reverseOrder());

        double sum = 0.0;
        for (int i = 0; i < Math.min(3, poengListe.size()); i++) {
            sum += poengListe.get(i);
        }

        return Math.round(sum * 10.0) / 10.0;
    }

    public double regnUtTotalPoeng() {
        double total = 0.0;

        total += regnUtSeriePoeng("forover", 1);
        total += regnUtSeriePoeng("forover", 2);
        total += regnUtSeriePoeng("forover", 3);

        total += regnUtSeriePoeng("bakover", 1);
        total += regnUtSeriePoeng("bakover", 2);
        total += regnUtSeriePoeng("bakover", 3);

        total += regnUtSeriePoeng("mix", 1);
        total += regnUtSeriePoeng("mix", 2);
        total += regnUtSeriePoeng("mix", 3);

        return total;
    }

// ---------------- TREKKREGLER ----------------

    private double avrundTrekk(double tall) {
        return Math.round(tall * 10.0) / 10.0;
    }

    public boolean erLikSerie(String serie) {

        List<String> g1 = serier.get(serie).get(1);
        List<String> g2 = serier.get(serie).get(2);
        List<String> g3 = serier.get(serie).get(3);

        if (g1.isEmpty() || g2.isEmpty() || g3.isEmpty()) {
            return false;
        }

        return g1.equals(g2) && g2.equals(g3);
    }

    public boolean harObligatoriskLikSerie() {
        return erLikSerie("forover") || erLikSerie("bakover") || erLikSerie("mix");
    }

    public boolean manglerSerie(String serie, int gymnastNr) {
        return serier.get(serie).get(gymnastNr).isEmpty();
    }

    public int antallManglendeElementer(String serie, int gymnastNr) {
        int antall = serier.get(serie).get(gymnastNr).size();
        return Math.max(0, 3 - antall);
    }

    public int antallGymnasterSomManglerSerie(String serie) {
        int antall = 0;

        for (int gymnastNr = 1; gymnastNr <= 3; gymnastNr++) {
            if (manglerSerie(serie, gymnastNr)) {
                antall++;
            }
        }

        return antall;
    }

    public int antallManglendeElementerISerie(String serie) {
        int antall = 0;

        for (int gymnastNr = 1; gymnastNr <= 3; gymnastNr++) {
            antall += antallManglendeElementer(serie, gymnastNr);
        }

        return antall;
    }

    public double regnUtTrekk() {
        double trekk = 0.0;

        if (!harObligatoriskLikSerie()) {
            trekk += 0.4;
        }

        for (int gymnastNr = 1; gymnastNr <= 3; gymnastNr++) {
            if (manglerSerie("forover", gymnastNr)) {
                trekk += 0.2;
            }
            if (manglerSerie("bakover", gymnastNr)) {
                trekk += 0.2;
            }
        }

        for (String serie : List.of("forover", "bakover", "mix")) {
            for (int gymnastNr = 1; gymnastNr <= 3; gymnastNr++) {
                trekk += antallManglendeElementer(serie, gymnastNr) * 0.3;
            }
        }

        return Math.round(trekk * 10.0) / 10.0;
    }

    public String hentTrekkTekst() {
        StringBuilder tekst = new StringBuilder();

        if (!harObligatoriskLikSerie()) {
            tekst.append("0.4 trekk: Ingen serie er lik for alle gymnaster\n");
        }

        int manglerForover = antallGymnasterSomManglerSerie("forover");
        if (manglerForover > 0) {
            double trekk = avrundTrekk(manglerForover * 0.2);
            tekst.append(trekk).append(" trekk: Manglende foroverserie\n");
        }

        int manglerBakover = antallGymnasterSomManglerSerie("bakover");
        if (manglerBakover > 0) {
            double trekk = avrundTrekk(manglerBakover * 0.2);
            tekst.append(trekk).append(" trekk: Manglende bakoverserie\n");
        }

        int manglendeElementerForover = antallManglendeElementerISerie("forover");
        if (manglendeElementerForover > 0) {
            double trekk = avrundTrekk(manglendeElementerForover * 0.3);
            tekst.append(trekk).append(" trekk: Manglende elementer i foroverserie\n");
        }

        int manglendeElementerBakover = antallManglendeElementerISerie("bakover");
        if (manglendeElementerBakover > 0) {
            double trekk = avrundTrekk(manglendeElementerBakover * 0.3);
            tekst.append(trekk).append(" trekk: Manglende elementer i bakoverserie\n");
        }

        int manglendeElementerMix = antallManglendeElementerISerie("mix");
        if (manglendeElementerMix > 0) {
            double trekk = avrundTrekk(manglendeElementerMix * 0.3);
            tekst.append(trekk).append(" trekk: Manglende elementer i mixserie\n");
        }

        if (tekst.isEmpty()) {
            return "";
        }

        return tekst.toString().trim();
    }
}
