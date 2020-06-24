/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ProductFacadeLocal;
import model.Unit;
import model.UnitFacadeLocal;

/**
 *
 * @author ASUS
 */
public class unitServlet extends HttpServlet {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private UnitFacadeLocal unitFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");
        if (action.equalsIgnoreCase("showInsert")) {
            request.getRequestDispatcher("insertUnit.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {

            String nameUnit = request.getParameter("unitName").trim();

            if (unitFacade.checkNameUnit(nameUnit) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertUnitPage", "Unit name already exists");
                request.getRequestDispatcher("unitServlet?action=showInsert").forward(request, response);
                return;

            }
            Date dNow = new Date();
            Unit unit = new Unit(nameUnit, dNow);
            unitFacade.create(unit);

            request.setAttribute("noteShowPage", "Insert Unit Success!!");
            out.print("insert thanh cong");
            request.getRequestDispatcher("unitServlet?action=showPage").forward(request, response);
        } else if (action.equalsIgnoreCase("showPage")) {

            request.setAttribute("list", unitFacade.findAll());
            request.getRequestDispatcher("showUnit.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Unit delUnit = unitFacade.find(_code);

            if (productFacade.getProductByUnit(_code).size() == 0) {

                unitFacade.remove(delUnit);
                request.setAttribute("noteShowPage", "Delete Unit Success!!");

            } else {

                request.setAttribute("noteShowPage", "Delete Unit Failed,It was used!!");

            }

            request.setAttribute("list", unitFacade.findAll());
            request.getRequestDispatcher("showUnit.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("detail")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Unit dUnit = unitFacade.find(_code);

            request.setAttribute("d", dUnit);
            request.getRequestDispatcher("detailUnit.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {

            String nameID = request.getParameter("unitID");
            int _nameID = Integer.parseInt(nameID);
            String nameUnit = request.getParameter("unitName").trim();

            Unit UnitO = unitFacade.find(Integer.parseInt(nameID));
            if ((unitFacade.checkNameUnit(nameUnit) != 0) && !(nameID).equalsIgnoreCase(UnitO.getName())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Unit name already exists");
                request.setAttribute("d", UnitO);
                request.getRequestDispatcher("detailUnit.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }

            Date dNow = new Date();
            Unit eCategory = new Unit(_nameID, nameUnit, dNow);

            unitFacade.edit(eCategory);

            request.setAttribute("noteShowPage", "Update Unit Success!!");
            request.getRequestDispatcher("unitServlet?action=showPage").forward(request, response);

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
