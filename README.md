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

1. Zbuduj obraz Docker:
   ```
   make build
   ```

2. Uruchom aplikację:
   ```
   make run
   ```

3. Otwórz przeglądarkę pod adresem [http://localhost:9000](http://localhost:9000)

4. Zatrzymaj aplikację:
   ```
   make stop
   ```

5. Wyczyść zasoby Dockera:
   ```
   make clean
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