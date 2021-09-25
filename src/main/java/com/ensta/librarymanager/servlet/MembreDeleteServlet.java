package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntImplService;
import com.ensta.librarymanager.service.MembreImplService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        showEmpruntsCourant(request, response);
    }
    private void showEmpruntsCourant(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();
        List<Emprunt> emprunt_list = new ArrayList<>();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/membre_delete.jsp");

        try {
            emprunt_list = empruntImplService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }



        request.setAttribute("emprunt_list", emprunt_list);

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MembreImplService membreImplService = MembreImplService.getInstance();
        String inputId = request.getParameter("id");
        try {
            membreImplService.delete(Integer.parseInt(inputId));
            response.sendRedirect("/TP3Ensta/membre_list");
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
