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
import javax.servlet.http.HttpSession;
import model.Employee;
import model.EmployeeFacadeLocal;

/**
 *
 * @author ASUS
 */
public class employeeServlet extends HttpServlet {

    @EJB
    private EmployeeFacadeLocal employeeFacade;

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

        if (action.equalsIgnoreCase("detail")) {
            HttpSession session = request.getSession();
            Employee loginedInfo
                    = (Employee) session.getAttribute("acc");
            // out.print(loginedInfo.getId());
            request.setAttribute("d", loginedInfo);
            request.getRequestDispatcher("detailEmployee.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("update")) {

            int _code = Integer.parseInt(request.getParameter("empID"));
            Employee emp = employeeFacade.find(_code);
            String currentPass = request.getParameter("empCurrentPass").trim();
            

            if (!currentPass.equalsIgnoreCase(emp.getPassword())) {
                request.setAttribute("noteInsertEmpPage", "Password does not exist");
                request.getRequestDispatcher("employeeServlet?action=detail").forward(request, response);
                
            } else {
                String name = request.getParameter("empName");
                String email = request.getParameter("empEmail");
                String pass = request.getParameter("empPass");
                String phone = request.getParameter("empPhone");
                Date dNow = new Date();

                emp.setEmail(email);
                emp.setPhone(phone);
                emp.setName(name);
                emp.setPassword(pass);
                emp.setModifiedDate(dNow);
                

                employeeFacade.edit(emp);

                HttpSession session = request.getSession();
                Employee acc = employeeFacade.find(_code);
                session.setAttribute("acc", acc);
                request.getRequestDispatcher("dashbordServlet").forward(request, response);
            }

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
