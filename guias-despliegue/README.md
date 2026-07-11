# 🐳 Guía de Despliegue Real (Fase 2 Defensiva)

Esta guía contiene los pasos exactos que funcionaron en nuestra terminal para levantar el entorno sin errores.
> 📂 **Nota sobre las rutas:** Todos los comandos de consola compartidos en esta guía deben ser ejecutados abriendo **PowerShell** directamente en la carpeta raíz de nuestro repositorio.

---

## ⚡ Paso 1: Liberar Puertos de Windows

Antes de tocar Docker, si tienes servicios locales encendidos, **DEBES APAGARLOS**. 

Si Docker intenta levantar la aplicación en el puerto `8080` o la base de datos en el `3306` y encuentra un servicio activo, el contenedor va a fallar silenciosamente o te lanzará un error de puertos duplicados (`port is already allocated`).

## 🧹 Paso 2: Limpieza Profunda de Volúmenes (Obligatorio)

Si ya habías levantado el proyecto antes, tu Docker tiene guardada la base de datos vieja.
> 📍 **Ruta de ejecución:** Raíz del proyecto

Para borrar la caché vieja y los volúmenes de raíz, ejecuta este comando en tu terminal (CMD o PowerShell) situado en la raíz del proyecto:

```bash
docker-compose down -v
```
## ⚠️ Paso 3: Corregir Saltos de Línea de Windows (Error en start.sh)
En Windows, Git tiende a cambiar automáticamente los finales de línea de los archivos a formato CRLF. Si el archivo de arranque scripts/start.sh se sube con formato de Windows, el 
contenedor de Linux fallará inmediatamente al iniciar porque no reconocerá los caracteres ocultos de retorno de carro

> 📍 **Ruta de ejecución:** Raíz del proyecto

Para forzar a Git a respetar el formato de Linux y restaurar el script de arranque limpio, ejecuta estos comandos en orden dentro de PowerShell o tu consola antes de continuar:

```bash
# 1. Apagar los contenedores actuales (si quedaba alguno colgado)
docker-compose down

# 2. Configurar Git para que respete los saltos de línea de Linux (LF)
git config core.autocrlf false

# 3. Forzar a Git a restaurar el archivo de arranque con el formato correcto
git checkout HEAD -- scripts/start.sh
```

## 🛠️ Paso 4: Corrección del Dockerfile (Evitar fallos de compilación)

El `Dockerfile` original del proyecto intenta descargar paquetes de servidores de Debian que ya están obsoletos, lo que hará que el comando `docker-compose build` falle por completo al intentar compilar Java.

Antes de levantar el entorno, abre el archivo `Dockerfile` (el que está en la raíz del proyecto) y asegúrate de cambiarlo por el siguiente:

```dockerfile
FROM eclipse-temurin:8-jdk
LABEL maintainer="Abhisek Datta <abhisek@appsecco.com>"

RUN apt-get update
RUN apt-get install -y default-mysql-client
RUN apt-get install -y maven

WORKDIR /app
COPY pom.xml pom.xml
RUN mvn dependency:resolve

COPY . .
RUN mvn clean package
RUN chmod 755 /app/scripts/start.sh

EXPOSE 8080
CMD ["sh", "-c", "/app/scripts/start.sh"]

```
## 🚀 Paso 5: Compilar y Levantar el Contenedor Desde Cero

Una vez que los puertos estén libres y el entorno limpio, es momento de construir las imágenes del proyecto. Este comando se encarga de descargar las librerías de Maven, compilar tu código Java con los parches de seguridad (BCrypt y AES) y configurar la base de datos MySQL inicial de forma automática.

> 📍 **Ruta de ejecución:** Raíz del proyecto
> 
Ejecuta el siguiente comando en la raíz del proyecto:

```bash
docker-compose up -d --build
```

## 🔍 Paso 6: Verificar que los Contenedores estén Activos

> 📍 **Ruta de ejecución:** Cualquier ruta (Recomendado mantenerse en la raíz del proyecto)

Justo después de lanzar el comando anterior, espera unos 10 o 15 segundos para que los servicios se estabilicen. Luego, ejecuta este comando para listar los contenedores que están corriendo:

```bash
docker ps
```

## 🌐 Paso 7: Pruebas de Funcionamiento y Verificación Criptográfica

### Acceso a la Aplicación Web
Abre tu navegador de preferencia (Chrome, Edge, Firefox) e ingresa a la siguiente dirección local:
👉 `http://localhost:8080`


