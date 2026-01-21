package models;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Klasa narzędziowa do normalizacji tekstu.
 *
 * Umożliwia porównywanie tekstów niezależnie od wielkości liter
 * oraz obecności znaków diakrytycznych, co jest szczególnie
 * przydatne w trybach nauki wymagających wpisywania tłumaczeń.
 *
 * Klasa statyczna, nieprzeznaczona do tworzenia instancji.
 */
public final class TextNormalizer {

    private TextNormalizer() {}

    /**
     * Normalizuje tekst poprzez usunięcie znaków diakrytycznych,
     * zamianę na małe litery oraz usunięcie zbędnych spacji.
     *
     * @param text tekst wejściowy
     * @return znormalizowany tekst
     */
    public static String normalize(String text) {
        if (text == null) return "";

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        return normalized
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }
}
