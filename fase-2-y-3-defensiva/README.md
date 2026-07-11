# Código Fuente Corregido y Hardening

Este módulo contiene la implementación práctica de controles criptográficos avanzados y la remediación de vulnerabilidades de inyección de código dentro de la aplicación DVJA.

## 🛠️ Controles de Hardening Aplicados

### 1. Robustecimiento de Autenticación (Hashing)
* **Algoritmo:** Se migró el almacenamiento de credenciales de MD5 (obsoleto y vulnerable a colisiones/tablas de arcoíris) a **BCrypt**.
* **Configuración:** Se estableció un factor de costo adaptativo de `12` para ralentizar ataques de fuerza bruta por hardware, añadiendo un salt aleatorio por cada registro de usuario.
* **Clase Modificada:** `UserService.java` (métodos de registro y validación de login).

### 2. Protección de Datos Sensibles en Reposo (Cifrado Simétrico)
* **Algoritmo:** Implementación de cifrado reversible **AES** (Advanced Encryption Standard) en modo **GCM** (Galois/Counter Mode).
* **Propósito:** Proteger la Información de Identificación Personal (PII), específicamente la columna `email` en la base de datos MySQL, garantizando confidencialidad e integridad (autenticación del texto cifrado).
* **Clase Modificada:** `EncryptionUtils.java` y el mapeo de la entidad `User.java`.

### 3. Mitigación de SQL Injection (Sanitización y Parametrización)
* **Mecanismo:** Reemplazo de consultas dinámicas por interpolación de cadenas mediante el uso de **Prepared Statements** (Consultas Parametrizadas).
* **Efecto:** El motor de la base de datos precompila la consulta, tratando las entradas de los usuarios estrictamente como literales y no como código ejecutable, neutralizando escapes maliciosos.
