package servlets;

import models.UserBean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServlet;

@WebListener
public class InitOnPortalStart extends HttpServlet implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent event){
        UserBean userBean = new UserBean();
        String state = String.valueOf(userBean.getStateType());
        event.getServletContext().setAttribute("userBean", userBean);
        event.getServletContext().setAttribute("userState", state);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce){
        ServletContextListener.super.contextDestroyed(sce);
    }
}
