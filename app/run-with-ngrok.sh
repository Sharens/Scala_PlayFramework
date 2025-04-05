#!/bin/bash


GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

if ! command -v ngrok &> /dev/null; then
    echo -e "${RED}Ngrok nie jest zainstalowany. Proszę zainstalować ngrok z https://ngrok.com/download${NC}"
    exit 1
fi

if ! command -v docker &> /dev/null; then
    echo -e "${RED}Docker nie jest zainstalowany. Proszę zainstalować Docker z https://docs.docker.com/get-docker/${NC}"
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo -e "${RED}Docker Compose nie jest zainstalowany. Proszę zainstalować Docker Compose z https://docs.docker.com/compose/install/${NC}"
    exit 1
fi

NGROK_TOKEN=${1:-}
if [ -z "$NGROK_TOKEN" ]; then
    echo -e "${YELLOW}Nie podano tokena ngrok. Uruchom skrypt ponownie z tokenem jako argument:${NC}"
    echo -e "${YELLOW}./run-with-ngrok.sh twoj_token_ngrok${NC}"
    exit 1
fi

echo -e "${GREEN}Uruchamianie aplikacji w Dockerze...${NC}"
docker-compose up -d --build

echo -e "${GREEN}Oczekiwanie na uruchomienie aplikacji (10 sekund)...${NC}"
sleep 10

echo -e "${GREEN}Konfiguracja ngrok...${NC}"
ngrok config add-authtoken "$NGROK_TOKEN"

echo -e "${GREEN}Uruchamianie tunelu ngrok dla portu 9000...${NC}"
ngrok http 9000

echo -e "${YELLOW}Ngrok został zatrzymany. Czy chcesz zatrzymać aplikację w Dockerze? (t/n)${NC}"
read -r answer
if [[ $answer =~ ^[Tt]$ ]]; then
    echo -e "${GREEN}Zatrzymywanie aplikacji w Dockerze...${NC}"
    docker-compose down
    echo -e "${GREEN}Aplikacja została zatrzymana.${NC}"
else
    echo -e "${GREEN}Aplikacja nadal działa w Dockerze. Aby ją zatrzymać, użyj 'docker-compose down'.${NC}"
fi