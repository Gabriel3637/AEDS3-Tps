package Entidades.aed3.ListaInvertida;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class TextoUtil {

    public static List<String> processarTexto(String texto) {
        List<String> termos = new ArrayList<>();
        texto = texto.toLowerCase();
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("[^a-z0-9 ]", " ");
        String[] palavras = texto.split("\\s+");
        for (String palavra : palavras) {
            if (palavra.length() > 2 && !Stopwords.isStopword(palavra)) {
                termos.add(palavra);
            }
        }
        return termos;
    }
}
