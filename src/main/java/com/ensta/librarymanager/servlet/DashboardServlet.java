package com.ensta.librarymanager.servlet;

import com.ensta.librarymanager.exception.ServiceException;
import com.ensta.librarymanager.model.Emprunt;
import com.ensta.librarymanager.service.EmpruntImplService;
import com.ensta.librarymanager.service.LivreImplService;
import com.ensta.librarymanager.service.MembreImplService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAll(request, response);
    }

    private void showAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpruntImplService empruntImplService = EmpruntImplService.getInstance();
        List<Emprunt> emprunt_list = new ArrayList<>();

        MembreImplService membreImplService = MembreImplService.getInstance();
        LivreImplService livreImplService = LivreImplService.getInstance();
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/View/dashboard.jsp");
        int nbr_livre=0;
        int nbr_membre=0;
        int nbr_emprunt=0;
        try {
            nbr_livre = livreImplService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("nombre_livres", nbr_livre);

        try {
            nbr_emprunt = empruntImplService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("nombre_emprunts", nbr_emprunt);
        try {
            nbr_membre = membreImplService.count();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("nombre_membres", nbr_membre);

        try {
            emprunt_list = empruntImplService.getListCurrent();
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        request.setAttribute("emprunt_list", emprunt_list);




        System.out.println(emprunt_list);
        dispatcher.forward(request, response);
    }
}
