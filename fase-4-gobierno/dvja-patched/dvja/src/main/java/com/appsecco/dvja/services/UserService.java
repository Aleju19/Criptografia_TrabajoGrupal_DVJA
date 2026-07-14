package com.appsecco.dvja.services;

import com.appsecco.dvja.models.User;
import com.appsecco.dvja.util.PasswordHasher; //Importamos la clase utilitaria de BCrypt
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class);
    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }
    public EntityManager getEntityManager() { return this.entityManager; }

    //Modificamos para aplicar Hashing Seguro
    public void save(User user) {
        logger.debug("Saving user with login: " + user.getLogin() + " id: " + user.getId());

        // Si la contraseña no es nula, la hasheamos de forma segura con BCrypt
        if(user.getPassword() != null)
            user.setPassword(PasswordHasher.hashPassword(user.getPassword()));

        if(user.getId() != null) {
            entityManager.merge(user);
        }
        else {
            entityManager.persist(user);
        }
    }

    public User find(int id) {
        return entityManager.find(User.class, id);
    }

    //Modificado para verificar usando BCrypt
    public boolean checkPassword(User user, String password) {
        if(user == null)
            return false;
        if(StringUtils.isEmpty(password))
            return false;

        // Compara de forma segura el texto plano con el hash guardado en la Base de Datos
        return PasswordHasher.checkPassword(password, user.getPassword());
    }

    public List<User> findAllUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        List<User> resultList = query.getResultList();

        return resultList;
    }

    public User findByLogin(String login) {
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login").
                setParameter("login", login).
                setMaxResults(1);
        List<User> resultList = query.getResultList();

        if(resultList.size() > 0)
            return resultList.get(0);
        else
            return null;
    }

    //Sanitización de consulta dinámica
    //Se elimina la concatenación directa de cadenas para mitigar Inyección SQL (SQLi)
     
    public User findByLoginUnsafe(String login) {
        // Corregido usando consultas parametrizadas seguras de JPA
        Query query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login").
                setParameter("login", login);
        List<User> resultList = query.getResultList();

        if(resultList.size() > 0)
            return resultList.get(0);
        else
            return null;
    }

    public boolean resetPasswordByLogin(String login, String key,
                                        String password, String passwordConfirmation) {

        if(!StringUtils.equals(password, passwordConfirmation))
            return false;

        // Nota: Mantenemos el MD5 únicamente para validar el token 'key' si es requerido por el flujo del software, 
        // pero la persistencia de la contraseña final pasa por el método save() que ya usa BCrypt de forma segura.
        if(!StringUtils.equalsIgnoreCase(DigestUtils.md5DigestAsHex(login.getBytes()), key))
            return false;

        logger.info("Changing password for login: " + login +
                " New password: " + password);

        User user = findByLogin(login);
        if(user != null) {
            user.setPassword(password);
            save(user); // Llama a save() que internamente aplica BCrypt

            return true;
        }

        logger.info("Failed to find user with login: " + login);
        return false;
    }
}