# Criptografía - Trabajo Grupal DVJA (Fase 2 Defensiva)

Este repositorio contiene la versión parchada y segura de la aplicación DVJA, mitigando vulnerabilidades críticas mediante controles criptográficos y sanitización de código.

## 🔒 Mejoras de Seguridad Implementadas (Fase 2)
Cifrado de Contraseñas: Migración de MD5 a hashes robustos usando BCrypt (Factor de costo: 12).

Protección de PII (Datos Sensibles): Cifrado simétrico reversible mediante AES-GCM para la columna de correos electrónicos (email).

Mitigación de SQL Injection: Parametrización estricta de consultas en los repositorios y servicios de autenticación.

## 🚀 Instrucciones de Despliegue Local (¡Probado en Windows!)

Para evitar los errores comunes de colisión de puertos y asegurar que la base de datos se configure con el nuevo cifrado, sigan estos pasos estrictamente:

### 1. Limpiar el entorno y volúmenes previos (Obligatorio)
Si ya tenían contenedores o bases de datos viejas corriendo, ejecuten esto para limpiar los volúmenes corruptos:

```bash
docker-compose down -v
```

### 2. Construir y Levantar el Contenedor Seguro
Para compilar los nuevos cambios defensivos (BCrypt y AES) y levantar el entorno desde cero:

```bash
docker-compose up -d --build
```
### 3. Acceso a la Aplicación
Sitio Web: Naveguen a http://localhost:8080

### Acceso Directo a la BD (MySQL en Docker):

```bash
docker exec -it dvja-mysql-1 mysql -u root -p'ec95c258266b8e985848cae688effa2b'
```

