FROM ubuntu:latest

RUN apt-get update && \
    apt-get install -y \
    g++ \
    zlib1g-dev \
    libfreetype6-dev && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app/
COPY target/*-runner /app/application
RUN chmod 775 /app
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]
