/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.OrderDetail;
import model.OrderDetailFacadeLocal;
import model.Orders;
import model.OrdersFacadeLocal;

/**
 *
 * @author ASUS
 */
public class orderServlet extends HttpServlet {

    @EJB
    private OrderDetailFacadeLocal orderDetailFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

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

        if (action.equalsIgnoreCase("showPage")) {

            request.setAttribute("list", ordersFacade.getOrderBySelect());
            out.print("show page");
            request.getRequestDispatcher("showOrder.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("detail")) {
            int _code = Integer.parseInt(request.getParameter("code"));
            request.setAttribute("d", ordersFacade.find(_code));

            float _oPrice = 0, _sPrice = 0;

            Orders orders = ordersFacade.find(_code);
            List<OrderDetail> list = (List<OrderDetail>) orderDetailFacade.getOrderDetailByOrder(_code);
            request.setAttribute("listOD", list);

            for (OrderDetail orderDetail : list) {
                _oPrice = (float) (_oPrice + orderDetail.getOriginalPrice() * orderDetail.getQuantity());
                _sPrice = (float) (_sPrice + orderDetail.getSellingPrice() * orderDetail.getQuantity());

            }
            request.setAttribute("oPrice",_oPrice);
            request.setAttribute("sPrice",_sPrice);
            request.getRequestDispatcher("detailOrder.jsp").forward(request, response);

        }else if (action.equalsIgnoreCase("update")){
            int _code = Integer.parseInt(request.getParameter("orderID"));
            
            int _status = Integer.parseInt(request.getParameter("orderStatus"));
            
            Orders o=ordersFacade.find(_code);
            o.setStatus(_status);
            
            ordersFacade.edit(o);
            request.setAttribute("noteShowPage","Update Order Status Success!!");
            request.getRequestDispatcher("orderServlet?action=showPage").forward(request, response);
            
           
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
