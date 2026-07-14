FROM eclipse-temurin:8-jdk

LABEL maintainer="Abhisek Datta <abhisek@appsecco.com>"
LABEL security.phase="Fase 3 - Hardening"

# Instalar únicamente las herramientas necesarias y limpiar caché
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        default-mysql-client \
        maven \
    && rm -rf /var/lib/apt/lists/*

# Crear grupo y usuario sin privilegios
RUN groupadd --system --gid 10001 dvja \
    && useradd \
        --system \
        --uid 10001 \
        --gid dvja \
        --create-home \
        --home-dir /app \
        --shell /usr/sbin/nologin \
        dvja

WORKDIR /app

COPY pom.xml /app/pom.xml
RUN mvn dependency:resolve

COPY . /app

RUN mvn clean package \
    && sed -i 's/\r$//' /app/scripts/start.sh \
    && chmod 750 /app/scripts/start.sh \
    && chown -R dvja:dvja /app

# A partir de aquí, el contenedor deja de usar root
USER 10001:10001

EXPOSE 8080

CMD ["bash", "/app/scripts/start.sh"]