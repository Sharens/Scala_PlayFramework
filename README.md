# Scala_PlayFramework

## Opis Zadania

Należy stworzyć aplikację na frameworku Play w Scali 3. 
- ✅ 3.0 Należy stworzyć kontroler do Produktów
- ✅ 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD ✅ dane pobierane z listy
- ✅ 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD
- ✅ 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok (nie podawać tokena ngroka w gotowym rozwiązaniu)
- ❌ 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD

Kontrolery mogą bazować na listach zamiast baz danych. CRUD: show all, show by id (get), update (put), delete (delete), add (post).

## Opis projektu

Jest to prosta aplikacja sklepowa składająca się z następujących modułów:
- Zarządzanie produktami (dodawanie, wyświetlanie, edycja, usuwanie)
- Zarządzanie kategoriami produktów (dodawanie, wyświetlanie, edycja, usuwanie)
- System koszyków zakupowych (tworzenie, dodawanie produktów, aktualizacja ilości, usuwanie produktów)

Aplikacja jest zbudowana zgodnie z wzorcem MVC (Model-View-Controller) i wykorzystuje:
- Play Framework 2.8.x
- Scala 2.13
- Twirl jako silnik szablonów

## Funkcjonalności

### Produkty
- Wyświetlanie listy wszystkich produktów
- Wyświetlanie szczegółów pojedynczego produktu
- Dodawanie nowych produktów
- Edycja istniejących produktów
- Usuwanie produktów

### Kategorie
- Wyświetlanie listy wszystkich kategorii
- Wyświetlanie szczegółów pojedynczej kategorii
- Dodawanie nowych kategorii
- Edycja istniejących kategorii
- Usuwanie kategorii

### Koszyk zakupowy
- Wyświetlanie listy wszystkich koszyków
- Tworzenie nowego koszyka
- Dodawanie produktów do koszyka
- Aktualizacja ilości produktów w koszyku
- Usuwanie produktów z koszyka
- Czyszczenie i usuwanie całego koszyka

## Struktura projektu

```
.
├── app                     # Główny katalog aplikacji
│   ├── controllers         # Kontrolery (obsługa żądań HTTP)
│   ├── models              # Modele danych
│   └── views               # Szablony widoków (HTML)
├── conf                    # Konfiguracja aplikacji
├── public                  # Statyczne zasoby (CSS, JS, obrazy)
├── test                    # Testy aplikacji
├── Dockerfile              # Konfiguracja obrazu Docker
├── docker-compose.yaml     # Konfiguracja usług Docker Compose
├── Makefile                # Skrypty pomocnicze
└── run-with-ngrok.sh       # Skrypt do uruchamiania z ngrokiem
```

### Opis głównych katalogów:

#### app/controllers
Zawiera kontrolery obsługujące żądania HTTP:
- `HomeController.scala` - obsługa strony głównej
- `ProductController.scala` - zarządzanie produktami
- `CategoryController.scala` - zarządzanie kategoriami
- `CartController.scala` - zarządzanie koszykami zakupowymi

#### app/models
Zawiera modele danych:
- `Product.scala` - model produktu
- `Category.scala` - model kategorii
- `Cart.scala` - model koszyka zakupowego

#### app/views
Zawiera szablony HTML:
- `index.scala.html` - strona główna
- `main.scala.html` - szablon główny (layout)
- `products/*.scala.html` - widoki związane z produktami
- `categories/*.scala.html` - widoki związane z kategoriami
- `carts/*.scala.html` - widoki związane z koszykami

#### conf
Zawiera pliki konfiguracyjne:
- `application.conf` - główna konfiguracja aplikacji
- `routes` - definicje tras (routing)
- `messages` - pliki z tłumaczeniami

## Wymagania

Aby uruchomić projekt lokalnie, potrzebujesz:
- [Java JDK](https://www.oracle.com/java/technologies/downloads/) w wersji 17
- [SBT](https://www.scala-sbt.org/download.html) (Scala Build Tool)
- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [ngrok](https://ngrok.com/download) (opcjonalnie, dla tunelowania)

## Uruchamianie aplikacji

### Lokalnie (bez Dockera)

1. Sklonuj repozytorium:
   ```
   git clone [adres-repozytorium]
   cd Scala_PlayFramework
   ```

2. Uruchom aplikację:
   ```
   sbt run
   ```

3. Otwórz przeglądarkę pod adresem [http://localhost:9000](http://localhost:9000)

### Z użyciem Dockera

Ta metoda pozwala na uruchomienie aplikacji w izolowanym środowisku kontenera Docker, bez konieczności instalowania SBT czy Javy bezpośrednio na Twojej maszynie (poza samym Dockerem).

**1. Budowanie obrazu Docker:**

Najpierw musisz zbudować obraz Docker na podstawie pliku `Dockerfile` znajdującego się w głównym katalogu projektu. Obraz będzie zawierał wszystkie zależności i skompilowaną aplikację.

*   **Za pomocą Makefile (zalecane):**
    ```bash
    make build
    ```
    Ta komenda wykona za Ciebie odpowiednie polecenie `docker build`.

*   **Bezpośrednio komendą Docker:**
    Przejdź do głównego katalogu projektu (tam, gdzie znajduje się `Dockerfile`) i wykonaj polecenie:
    ```bash
    docker build -t scala_app:latest .
    ```
    *   `docker build`: Podstawowa komenda do budowania obrazów.
    *   `-t scala_app:latest`: Nadaje obrazowi nazwę (`scala_app`) i tag (`latest`). Możesz użyć innej nazwy i tagu.
    *   `.`: Wskazuje, że kontekstem budowania (miejscem, gdzie Docker szuka `Dockerfile` i plików do skopiowania) jest bieżący katalog.

**2. Uruchamianie kontenera z obrazu:**

Po zbudowaniu obrazu możesz uruchomić z niego kontener.

*   **Za pomocą Makefile (zalecane):**
    ```bash
    make run
    ```
    Ta komenda uruchomi kontener w tle i zmapuje odpowiednie porty.

*   **Bezpośrednio komendą Docker:**
    Aby uruchomić kontener i mieć dostęp do aplikacji z przeglądarki na hoście, użyj polecenia:
    ```bash
    docker run -d -p 9000:9000 --name scala-play-app scala_app:latest
    ```
    Lub, jeśli chcesz widzieć logi aplikacji bezpośrednio w terminalu (tryb interaktywny):
    ```bash
    docker run -it -p 9000:9000 --rm --name scala-play-app scala_app:latest
    ```
    *   `docker run`: Podstawowa komenda do uruchamiania kontenerów.
    *   `-d` (opcjonalnie): Uruchamia kontener w tle (detached mode). Bez `-d` (lub z `-it`), kontener działa na pierwszym planie, a jego logi są widoczne w terminalu.
    *   `-it` (opcjonalnie, zamiast `-d`): Uruchamia kontener w trybie interaktywnym (`-i`) z podłączonym pseudo-TTY (`-t`). Przydatne do debugowania lub gdy aplikacja wymaga interakcji.
    *   `-p 9000:9000`: Mapuje port. Publikuje port `9000` kontenera na porcie `9000` Twojej maszyny hosta. Format to `<port-hosta>:<port-kontenera>`. Dzięki temu możesz uzyskać dostęp do aplikacji.
    *   `--name scala-play-app` (opcjonalnie): Nadaje kontenerowi czytelną nazwę, co ułatwia zarządzanie nim (np. zatrzymywanie przez `docker stop scala-play-app`).
    *   `--rm` (opcjonalnie, często używane z `-it`): Automatycznie usuwa kontener po jego zatrzymaniu. Przydatne, aby nie zostawiać niepotrzebnych kontenerów.
    *   `scala_app:latest`: Nazwa i tag obrazu, z którego ma zostać utworzony kontener (musi pasować do tego użytego w `docker build`).

**3. Dostęp do aplikacji:**

Po uruchomieniu kontenera z mapowaniem portu (`-p 9000:9000`), aplikacja będzie dostępna w przeglądarce pod adresem:
http://localhost:9000

**4. Zatrzymywanie kontenera:**

*   **Za pomocą Makefile:**
    ```bash
    make stop
    ```

*   **Bezpośrednio komendą Docker:**
    Jeśli uruchomiłeś kontener z nazwą (np. `--name scala-play-app`):
    ```bash
    docker stop scala-play-app
    ```
    Jeśli uruchomiłeś w trybie `-it`, wystarczy nacisnąć `Ctrl+C` w terminalu, w którym działa kontener. Jeśli użyłeś opcji `--rm`, kontener zostanie automatycznie usunięty po zatrzymaniu.

**5. Czyszczenie zasobów Dockera:**

*   **Za pomocą Makefile:**
    ```bash
    make clean
    ```
    Ta komenda zazwyczaj zatrzymuje i usuwa kontener oraz może usuwać inne zasoby (np. sieci).

*   **Bezpośrednio komendą Docker:**
    Aby usunąć zatrzymany kontener (jeśli nie użyłeś `--rm`):
    ```bash
    docker rm scala-play-app
    ```
    Aby usunąć obraz:
    ```bash
    docker rmi scala_app:latest
    ```


### Uruchamianie z ngrokiem (dla udostępnienia aplikacji publicznie)

1. Upewnij się, że masz konto na [ngrok.com](https://ngrok.com) i token uwierzytelniający.

2. Uruchom aplikację z ngrokiem:
   ```
   make ngrok TOKEN=twoj_token_ngrok
   ```

   Lub bezpośrednio skryptem:
   ```
   ./run-with-ngrok.sh twoj_token_ngrok
   ```

## Dostępne komendy Makefile

- `make build` - buduje obraz Docker
- `make run` - uruchamia aplikację w kontenerze Docker
- `make stop` - zatrzymuje kontenery Docker
- `make clean` - zatrzymuje i usuwa zasoby Dockera
- `make ngrok TOKEN=twoj_token` - uruchamia aplikację z tunelowaniem ngrok
- `make help` - wyświetla dostępne komendy

## Dostępne adresy URL

### Strona główna
- http://localhost:9000 - Strona główna z linkami do wszystkich sekcji

### Produkty
- http://localhost:9000/products - Lista wszystkich produktów
- http://localhost:9000/products/new - Formularz dodawania nowego produktu
- http://localhost:9000/products/[id] - Szczegóły produktu o podanym ID
- http://localhost:9000/products/[id]/edit - Edycja produktu o podanym ID

### Kategorie
- http://localhost:9000/categories - Lista wszystkich kategorii
- http://localhost:9000/categories/new - Formularz dodawania nowej kategorii
- http://localhost:9000/categories/[id] - Szczegóły kategorii o podanym ID
- http://localhost:9000/categories/[id]/edit - Edycja kategorii o podanym ID

### Koszyki
- http://localhost:9000/carts - Lista wszystkich koszyków
- http://localhost:9000/carts/new - Tworzenie nowego koszyka
- http://localhost:9000/carts/[id] - Szczegóły koszyka o podanym ID
- http://localhost:9000/carts/[id]/items/add - Dodawanie produktu do koszyka