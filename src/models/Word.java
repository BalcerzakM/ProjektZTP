package models;
/**
 * Reprezentuje pojedynczą parę słów:
 * słowo źródłowe oraz jego tłumaczenie.
 *
 * Obiekt Word jest podstawowym elementem danych
 * wykorzystywanym w trybach nauki, statystykach
 * oraz mechanizmach powtórek.
 */
public record Word(String source, String target) {

}
