# Proyecto de Criptografía y Seguridad: Remediación Segura de DVJA

## 🏫 Universidad Central del Ecuador
* **Facultad:** Ingeniería y Ciencias Aplicadas
* **Carrera:** Computación
* **Período:** 2026-2026

## 👥 Integrantes y Roles (Segregación de Funciones)
* **Integrante 1 (Pentester / QA):** Edwin de la Cruz - Encargado del diagnóstico ofensivo, levantamiento del entorno inicial (Docker Compose), explotación controlada de vulnerabilidades OWASP Top Ten y documentación de evidencias técnicas (Fase 1).
* **Integrante 2 (Secure Developer / Dev):** David Ortega - Encargado de la modificación del código fuente en Java, implementación de hashing robusto para contraseñas (BCrypt) y sanitización de consultas dinámicas mediante parametrización SQL (Fase 2).
* **Integrante 3 (SecOps / Infraestructura):** Alexis Carvajal - Encargado del cifrado simétrico (AES) en la base de datos, hardening de la infraestructura del contenedor (JVM Non-Root) y depuración de dependencias vulnerables en el archivo `pom.xml` (Fase 2 y 3).
* **Integrante 4 (Gobernanza / Legal):** Jostyn Palacios - Encargado del desarrollo de la matriz de riesgos (NIST CSF / ISO 27001), segregación de funciones por roles de usuario en código y adecuación del módulo de consentimiento bajo la LOPDP de Ecuador (Fase 4).

---

## 🛠️ Requisitos Previos para el Despliegue
Antes de levantar el entorno, asegúrate de tener instalado:
* Docker & Docker Compose
* Java 17 o superior
* Maven

## 🚀 Instrucciones de Ejecución (Línea Base)
1. Clonar este repositorio.
