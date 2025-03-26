# Scala_PlayFramework
Należy stworzyć aplikację na frameworku Play w Scali 3. 
- 3.0 Należy stworzyć kontroler do Produktów
- 3.5 Do kontrolera należy stworzyć endpointy zgodnie z CRUD - dane pobierane z listy
- 4.0 Należy stworzyć kontrolery do Kategorii oraz Koszyka + endpointy zgodnie z CRUD
- 4.5 Należy aplikację uruchomić na dockerze (stworzyć obraz) oraz dodać skrypt uruchamiający aplikację via ngrok (nie podawać tokena ngroka w gotowym rozwiązaniu)
- 5.0 Należy dodać konfigurację CORS dla dwóch hostów dla metod CRUD
Kontrolery mogą bazować na listach zamiast baz danych. CRUD: show all, show by id (get), update (put), delete (delete), add (post).


## Podstrony produktowe
- http://localhost:9000 - Strona główna z linkami do wszystkich sekcji
- http://localhost:9000/products - Lista wszystkich produktów
- http://localhost:9000/products/new - Formularz dodawania nowego produktu
- http://localhost:9000/products/1 - Szczegóły produktu o ID 1

## Podstrony kategorii
- http://localhost:9000/categories - Lista wszystkich kategorii
- http://localhost:9000/categories/new - Formularz dodawania nowej kategorii
- http://localhost:9000/categories/1 - Szczegóły kategorii o ID 1
- http://localhost:9000/categories/1/edit - Formularz edycji kategorii o ID 1