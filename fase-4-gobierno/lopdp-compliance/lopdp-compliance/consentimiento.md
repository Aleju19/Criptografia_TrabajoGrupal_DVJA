# Consentimiento para el Tratamiento de Datos Personales

## Objetivo

Garantizar que todo usuario otorgue su consentimiento antes del tratamiento de sus datos personales durante el proceso de registro.

---

# Datos solicitados

Durante el registro se recopilan:

- Nombre
- Usuario
- Correo electrónico
- Contraseña

---

# Consentimiento informado

Antes de finalizar el proceso de registro, el usuario deberá aceptar el tratamiento de sus datos personales.

El consentimiento debe ser:

- Libre
- Informado
- Específico
- Inequívoco

Si el usuario no acepta el tratamiento de sus datos personales, el sistema rechazará el registro.

---

# Implementación técnica

Se implementó una validación del consentimiento en dos niveles:

## Interfaz de usuario

El formulario de registro incorpora una casilla para aceptar la Política de Privacidad.

## Servidor

El controlador Register.java valida nuevamente el consentimiento antes de crear el usuario.

Esto evita que el consentimiento pueda omitirse mediante modificaciones del navegador.

---

# Beneficios

- Cumplimiento de la LOPDP.
- Mayor transparencia.
- Protección de los derechos del usuario.
- Evidencia del consentimiento otorgado.

---

# Base legal

Ley Orgánica de Protección de Datos Personales (LOPDP).
