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
import model.Manufacturer;
import model.ManufacturerFacadeLocal;
import model.ProductFacadeLocal;

/**
 *
 * @author ASUS
 */
public class manufacturerServlet extends HttpServlet {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private ManufacturerFacadeLocal manufacturerFacade;

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
            request.getRequestDispatcher("insertManu.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {

            String name = request.getParameter("manuName").trim();
            String address = request.getParameter("manuAddress");
            String email = request.getParameter("manuEmail").trim();
            String phone = request.getParameter("manuPhone").trim();
            Date dNow = new Date();

            if (manufacturerFacade.checkNameManu(name) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertManuPage", "Manufacturer name already exists");
                request.getRequestDispatcher("manufacturerServlet?action=showInsert").forward(request, response);
                return;

            }
            
            if (manufacturerFacade.checkEmail(email) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertManuPage", "Manufacturer Email already exists");
                request.getRequestDispatcher("manufacturerServlet?action=showInsert").forward(request, response);
                return;

            }
            
            if (manufacturerFacade.checkPhone(phone) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertManuPage", "Manufacturer Phone already exists");
                request.getRequestDispatcher("manufacturerServlet?action=showInsert").forward(request, response);
                return;

            }

            Manufacturer manufacturer = new Manufacturer(name, address, email, phone, dNow);
            manufacturerFacade.create(manufacturer);

            request.setAttribute("noteShowPage", "Insert Manufacturer Success!!");
            out.print("insert thanh cong");
            request.getRequestDispatcher("manufacturerServlet?action=showPage").forward(request, response);
        } else if (action.equalsIgnoreCase("showPage")) {
            request.setAttribute("list", manufacturerFacade.findAll());
            request.getRequestDispatcher("showManu.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("delete")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Manufacturer delManu = manufacturerFacade.find(_code);
            if (productFacade.getProductByManu(_code).size() == 0) {

                manufacturerFacade.remove(delManu);
                request.setAttribute("noteShowPage", "Delete Manufacturer Success!!");

            } else {

                request.setAttribute("noteShowPage", "Delete Manufacturer Failed,It was used!!");

            }
//out.print("coll"+delManu.getProductCollection().size());
//out.print("facade"+productFacade.getProductByManu(_code));
//            try {
//                manufacturerFacade.remove(delManu);
//                request.setAttribute("noteShowPage", "Delete Success!!");
//            } catch (Exception e) {
//                request.setAttribute("noteShowPage", "Delete Failed!!");
//            }

            request.setAttribute("list", manufacturerFacade.findAll());
            request.getRequestDispatcher("showManu.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("detail")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Manufacturer dManu = manufacturerFacade.find(_code);

            request.setAttribute("d", dManu);
            request.getRequestDispatcher("detailManu.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {

            String nameID = request.getParameter("manuID");
            int _nameID = Integer.parseInt(nameID);
            String name = request.getParameter("manuName").trim();
            String address = request.getParameter("manuAddress");
            String email = request.getParameter("manuEmail").trim();
            String phone = request.getParameter("manuPhone").trim();
            Date dNow = new Date();

            Manufacturer ManuO = manufacturerFacade.find(Integer.parseInt(nameID));
            if ((manufacturerFacade.checkNameManu(name) != 0) && !(name).equalsIgnoreCase(ManuO.getName())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Manufacturer name already exists");
                request.setAttribute("d", ManuO);
                request.getRequestDispatcher("detailManu.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }
            
            if ((manufacturerFacade.checkEmail(email) != 0) && !(email).equalsIgnoreCase(ManuO.getEmail())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Manufacturer Email already exists");
                request.setAttribute("d", ManuO);
                request.getRequestDispatcher("detailManu.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }
            
            if ((manufacturerFacade.checkPhone(phone) != 0) && !(phone).equalsIgnoreCase(ManuO.getPhone())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Manufacturer Phone already exists");
                request.setAttribute("d", ManuO);
                request.getRequestDispatcher("detailManu.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }

            Manufacturer manufacturer = new Manufacturer(_nameID, name, address, email, phone, dNow);
            manufacturerFacade.edit(manufacturer);

            request.setAttribute("noteShowPage", "Update Manufacturer Success!!");
            request.getRequestDispatcher("manufacturerServlet?action=showPage").forward(request, response);

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
