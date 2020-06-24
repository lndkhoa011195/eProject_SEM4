/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CustomerFacadeLocal;
import model.OrderDetail;
import model.OrderDetailFacadeLocal;
import model.Orders;
import model.OrdersFacadeLocal;
import model.ProductFacadeLocal;
import util.NumberManager;

/**
 *
 * @author ASUS
 */
public class dashbordServlet extends HttpServlet {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CustomerFacadeLocal customerFacade;

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

        int year = Year.now().getValue();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        // out.print(ordersFacade.getOrderByDate(day1, "1/1/2021").size());

        //xu li thang 
        String fromM = "1/" + month + "/" + year;
        String toM = null;
        if (month + 1 > 12) {
            toM = "1/" + 1 + "/" + (year + 1);
        } else {
            toM = "1/" + (month + 1) + "/" + (year);
        }
        //end xu li thang

        List<Orders> listMonth = ordersFacade.getOrderByDate(fromM, toM);
        List<Orders> listYear = ordersFacade.getOrderByDate(("1/1/" + year), ("1/1/" + (year + 1)));
        System.out.println(listMonth.size()+" "+listYear.size());

        float _mPrice = 0, _yPrice = 0;

        for (Orders orders : listMonth) {
            List<OrderDetail> list = (List<OrderDetail>) orderDetailFacade.getOrderDetailByOrder(orders.getId());
            request.setAttribute("listOD", list);
          
            for (OrderDetail orderDetail : list) {

                _mPrice = (float) (_mPrice + orderDetail.getSellingPrice() * orderDetail.getQuantity());

            }
           

        }

        //year 
        for (Orders orders : listYear) {
            List<OrderDetail> list = (List<OrderDetail>) orderDetailFacade.getOrderDetailByOrder(orders.getId());
            request.setAttribute("listOD", list);
            
            for (OrderDetail orderDetail : list) {

                _yPrice = (float) (_yPrice + orderDetail.getSellingPrice() * orderDetail.getQuantity());

            }
        }
        request.setAttribute("mMoney",NumberManager.getInstance().format((int)_mPrice));
        request.setAttribute("yMoney",NumberManager.getInstance().format((int)_yPrice));
        request.setAttribute("countCustomer",customerFacade.findAll().size());
        request.setAttribute("countProduct",productFacade.findAll().size());
        
        int countTotal=ordersFacade.findAll().size();
        int countNew=ordersFacade.CountByStatus(1);
        int countProcess=ordersFacade.CountByStatus(2);
        int countDone=ordersFacade.CountByStatus(3);
        int countCan=ordersFacade.CountByStatus(0);
        
        float barNew=(float) (countNew*1.0/(countTotal*1.0));
        request.setAttribute("barNew", barNew*100);
        
        float barProceess=(float) (countProcess*1.0/(countTotal*1.0));
        request.setAttribute("barProcess", barProceess*100);
        
        float barDone=(float) (countDone*1.0/(countTotal*1.0));
        request.setAttribute("barDone", barDone*100);
        
        float barCan=(float) (countCan*1.0/(countTotal*1.0));
        request.setAttribute("barCan", barCan*100);

        
        request.getRequestDispatcher("dashbord.jsp").forward(request, response);

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
