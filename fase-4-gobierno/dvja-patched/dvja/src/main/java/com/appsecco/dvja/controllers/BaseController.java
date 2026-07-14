package com.appsecco.dvja.controllers;

import com.appsecco.dvja.Constant;
import com.appsecco.dvja.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public class BaseController extends ActionSupport implements ServletRequestAware, SessionAware {

    private HttpServletRequest servletRequest;
    private Map<String, Object> session;

    public Map<String, Object> getSession() {
        if(session == null)
            session = ActionContext.getContext().getSession();
        return session;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    public boolean isAuthenticated() {
        return (sessionGetUser() != null);
    }

    /**
     * Verifica si el usuario autenticado tiene rol de administrador.
     */
    public boolean isAdmin() {
        User user = sessionGetUser();
        return user != null &&
                "admin".equalsIgnoreCase(user.getRole());
    }

    /**
     * Verifica si el usuario autenticado tiene rol de cliente.
     */
    public boolean isCliente() {
        User user = sessionGetUser();
        return user != null &&
                "cliente".equalsIgnoreCase(user.getRole());
    }

    /**
     * Verifica si el usuario autenticado tiene rol de auditor.
     */
    public boolean isAuditor() {
        User user = sessionGetUser();
        return user != null &&
                "auditor".equalsIgnoreCase(user.getRole());
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public void sessionSetUser(User user) {
        session.put(Constant.SESSION_USER_HANDLE, user);
    }

    public User sessionGetUser() {
        return ((User) getSession().get(Constant.SESSION_USER_HANDLE));
    }

    public void sessionRemoveUser() {
        session.remove(Constant.SESSION_USER_HANDLE);
    }

    public String renderText(String txtMessage) {
        try {
            ServletActionContext.getResponse().getWriter().write(txtMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

    public String renderJSON(Map jsonMap) {
        Gson gson = new GsonBuilder().create();

        try {
            ServletActionContext.getResponse().addHeader("Content-Type", "application/json");
            ServletActionContext.getResponse().getWriter().write(gson.toJson(jsonMap));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }
}
