# Política de Roles y Permisos

## Objetivo

Definir los niveles de acceso dentro de la aplicación mediante un modelo de Control de Acceso Basado en Roles (RBAC).

---

# Roles definidos

## Administrador

Permisos:

- Administrar usuarios.
- Consultar usuarios.
- Modificar cualquier usuario.
- Administrar productos.
- Acceso completo al sistema.

---

## Cliente

Permisos:

- Iniciar sesión.
- Modificar únicamente su propio perfil.
- Consultar información autorizada.

Restricciones:

- No puede modificar otros usuarios.
- No puede consultar información administrativa.

---

## Auditor

Permisos:

- Consultar información.
- Buscar usuarios.
- Ver registros.

Restricciones:

- No puede modificar usuarios.
- No puede crear usuarios.
- No puede eliminar información.

---

# Principio aplicado

Se implementa el Principio de Mínimo Privilegio.

Cada usuario únicamente dispone de los permisos necesarios para realizar sus funciones.

---

# Implementación técnica

La aplicación verifica los permisos mediante:

- isAdmin()
- isCliente()
- isAuditor()

implementados en BaseController.java.

Las operaciones sensibles verifican estos permisos antes de ejecutarse.
