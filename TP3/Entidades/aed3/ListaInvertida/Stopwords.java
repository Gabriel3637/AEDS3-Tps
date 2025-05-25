package Entidades.aed3.ListaInvertida;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Stopwords {
    private static final Set<String> stopwords = new HashSet<>(Arrays.asList(
        "a", "o", "e", "de", "do", "da", "dos", "das",
        "em", "um", "uma", "uns", "umas",
        "para", "com", "sem", "por", "na", "no", "nas", "nos",
        "ao", "aos", "às", "às", "que", "é", "ser", "foi", "são"
    ));

    public static boolean isStopword(String palavra) {
        return stopwords.contains(palavra);
    }
}
