FROM eclipse-temurin:17-jdk

# Instalacja SBT
RUN apt-get update && \
    apt-get install -y curl gnupg && \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list && \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list && \
    curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add && \
    apt-get update && \
    apt-get install -y sbt && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Kopiowanie plików konfiguracyjnych
COPY build.sbt .
COPY build.sc .
COPY project/build.properties project/
COPY project/plugins.sbt project/

# Uruchomienie wstępnej kompilacji, aby pobrać zależności
RUN sbt update || echo "SBT update failed, but continuing..."

# Kopiowanie kodu źródłowego
COPY app/ app/
COPY conf/ conf/
COPY public/ public/
COPY test/ test/

# Kompilacja aplikacji
RUN sbt compile || echo "SBT compile failed, but continuing..."

# Otwórz port dla aplikacji Play (domyślnie 9000)
EXPOSE 9000

# Uruchom aplikację
CMD ["sbt", "run"]