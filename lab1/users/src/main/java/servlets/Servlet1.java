package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import users.Mock;
import users.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/Servlet1/*")
public class Servlet1 extends HttpServlet {
    private FileIO fio = new FileIO();

    private List<User> lu = UserSetup();
    public Servlet1(){
        super();
    }

    private List<User> UserSetup() {
         List<User> userList = (List<User>) fio.readFromFile();
         if (userList == null) userList = new Mock().getUserList();
         return userList;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setContentType("application/json");
        response.getWriter().println(lu);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        User user = Helpers.userParse(request);
        user.setId(Helpers.getNextId(lu));
        lu.add(user);
        fio.saveToFile(lu);
        doGet(request,response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        User user = Helpers.userParse(request);
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        System.out.println(id);
        response.setContentType("application/json");
        int index = Helpers.getIndexByUserId(id, lu);
        lu.set(index,user);
        fio.saveToFile(lu);
        doGet(request,response);
    }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        int id = Integer.parseInt(request.getPathInfo().substring(1));
        System.out.println(id);
        response.setContentType("application/json");
        int index = Helpers.getIndexByUserId(id,lu);
        lu.remove(index);
        fio.saveToFile(lu);
        doGet(request,response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp){
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods","*");
        resp.setHeader("Access-Control-Allow-Headers","*");
    }
}
