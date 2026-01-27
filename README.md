# Projekt ZTP - Aplikacja do Nauki Słówek

Aplikacja desktopowa (Java Swing) wspomagająca naukę języków obcych poprzez system fiszek i minigry. Program wykorzystuje zaawansowane wzorce projektowe (MVC, Observer, Proxy, Memento).

## Uruchomienie

### Wymagania
* **Java JDK 17** lub nowsza.
* Maven / Gradle (opcjonalnie, do budowania).

### Instrukcja
1. **Pobierz kod:** Sklonuj repozytorium.
2. **Otwórz w IDE:** Zaimportuj folder projektu do programu budującego program.
3. **Uruchom:** Znajdź i uruchom główną klasę aplikacji Main.java.

---

## Baza Danych (Pliki Tekstowe)

Aplikacja nie używa SQL. Dane przechowywane są w plikach tekstowych w katalogu `resources`.

### 1. Zestawy słówek
**Lokalizacja:** `/resources/wordSets`  
**Nazwa pliku:** Nazwa zestawu (np. `Natura.txt`).  
**Format:**
* Pierwsza linia: Poziom trudności (np. A1, B2).
* Kolejne linie: Pary `słowo - tłumaczenie`.

**Przykład:**
```text
A2
apple - jabłko
car - samochód
house - dom
