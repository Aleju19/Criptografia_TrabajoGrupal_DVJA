<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
  <jsp:include page="common/Head.jsp"/>
</head>

<body>

<jsp:include page="common/Navigation.jsp"/>

<div class="container" style="margin-top:30px; margin-bottom:50px;">

  <h2>Política de Privacidad</h2>

  <hr/>

  <p>
    Esta aplicación recopila únicamente la información necesaria para el
    registro y autenticación de usuarios.
  </p>

  <h4>Datos recopilados</h4>

  <ul>
    <li>Nombre</li>
    <li>Usuario</li>
    <li>Correo electrónico</li>
    <li>Contraseña</li>
  </ul>

  <h4>Finalidad</h4>

  <p>
    Los datos son utilizados únicamente para administrar las cuentas de los
    usuarios y controlar el acceso al sistema.
  </p>

  <h4>Seguridad</h4>

  <ul>
    <li>Contraseñas protegidas mediante BCrypt.</li>
    <li>Correos electrónicos cifrados con AES.</li>
    <li>Control de Acceso Basado en Roles (RBAC).</li>
  </ul>

  <h4>Derechos del usuario</h4>

  <p>
    El usuario podrá solicitar la actualización o eliminación de sus datos
    personales conforme a la Ley Orgánica de Protección de Datos Personales
    (LOPDP).
  </p>

</div>

<jsp:include page="common/Footer.jsp"/>

</body>
</html>