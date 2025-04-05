.PHONY: build run stop clean ngrok

APP_NAME = scala-play-app
DOCKER_IMAGE = $(APP_NAME):latest

build:
	@echo "Budowanie obrazu Docker..."
	docker-compose build

run:
	@echo "Uruchamianie aplikacji w Dockerze..."
	docker-compose up -d

stop:
	@echo "Zatrzymywanie aplikacji..."
	docker-compose down

clean: stop
	@echo "Czyszczenie zasobów Dockera..."
	docker-compose down --rmi all --volumes --remove-orphans

ngrok:
	@echo "Upewnij się, że masz token ngrok!"
	@echo "Uruchamianie aplikacji z ngrokiem..."
	chmod +x run-with-ngrok.sh
	./run-with-ngrok.sh $(TOKEN)

help:
	@echo "Dostępne polecenia:"
	@echo "  make build  - Buduje obraz Docker"
	@echo "  make run    - Uruchamia aplikację w Dockerze"
	@echo "  make stop   - Zatrzymuje aplikację"
	@echo "  make clean  - Zatrzymuje aplikację i czyści zasoby Dockera"
	@echo "  make ngrok TOKEN=twoj_token - Uruchamia aplikację z ngrokiem"
	@echo "  make help   - Wyświetla tę pomoc"