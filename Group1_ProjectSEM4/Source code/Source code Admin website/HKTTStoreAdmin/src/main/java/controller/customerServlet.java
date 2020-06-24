/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.CartDetail;
import model.CartDetailFacadeLocal;
import model.CartFacadeLocal;
import model.Customer;
import model.CustomerFacadeLocal;
import model.OrdersFacadeLocal;

/**
 *
 * @author ASUS
 */
public class customerServlet extends HttpServlet {

    @EJB
    private CartDetailFacadeLocal cartDetailFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private CartFacadeLocal cartFacade;

    @EJB
    private CustomerFacadeLocal customerFacade;

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
            request.setAttribute("list", customerFacade.findAll());
            request.getRequestDispatcher("showCustomer.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("showInsert")) {
            request.getRequestDispatcher("insertCustomer.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {
            String name = request.getParameter("cusName");
            String email = request.getParameter("cusEmail").trim();
            String address = request.getParameter("cusAddress");
            String pass = request.getParameter("cusPass");
            String phone = request.getParameter("cusPhone").trim();
            
            if (customerFacade.checkEmail(email) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertCatePage", "Customer Email already exists");
                request.getRequestDispatcher("customerServlet?action=showInsert").forward(request, response);
                return;

            }
            
            if (customerFacade.checkPhone(phone) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertCatePage", "Customer Phone already exists");
                request.getRequestDispatcher("customerServlet?action=showInsert").forward(request, response);
                return;

            }
            
            

            int isAc = Integer.parseInt(request.getParameter("cusIsAc"));
            Date dNow = new Date();

            Customer customer = new Customer(name, email, phone, pass, address, dNow, 0, isAc);
            customerFacade.create(customer);

            request.setAttribute("noteShowPage", "Create Account Customer Success!!");
            request.getRequestDispatcher("customerServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            int _code = Integer.parseInt(request.getParameter("code"));

            Customer delCus = customerFacade.find(_code);

            if (ordersFacade.getOrderByCus(_code).size() != 0) {
                request.setAttribute("noteShowPage", "Delete Account Customer Failed,It was used!!!");
                request.getRequestDispatcher("customerServlet?action=showPage").forward(request, response);
            } else if (cartFacade.getCartByCus(_code).size() != 0 && ordersFacade.getOrderByCus(_code).size() == 0) {

                List<Cart> listCart = cartFacade.getCartByCus(_code);
                for (Cart cart : listCart) {
                    List<CartDetail> listCartDetail = cartDetailFacade.getCartDetailByCartID(cart.getId());

                    for (CartDetail cartDetail : listCartDetail) {
                        cartDetailFacade.remove(cartDetail);
                    }
                    
                    cartFacade.remove(cart);

                }
                
                customerFacade.remove(delCus);
                request.setAttribute("noteShowPage", "Delete Account Customer Success!!");
                request.getRequestDispatcher("customerServlet?action=showPage").forward(request, response);

            } else if (cartFacade.getCartByCus(_code).size() == 0 && ordersFacade.getOrderByCus(_code).size() == 0) {
                customerFacade.remove(delCus);
                request.setAttribute("noteShowPage", "Delete Account Customer Success!!");
                request.getRequestDispatcher("customerServlet?action=showPage").forward(request, response);

            }
        } else if (action.equalsIgnoreCase("detail")) {
            int _code = Integer.parseInt(request.getParameter("code"));

            request.setAttribute("d", customerFacade.find(_code));
            request.getRequestDispatcher("detailCustomer.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {
            int _code = Integer.parseInt(request.getParameter("cusID"));

            String name = request.getParameter("cusName");
            String email = request.getParameter("cusEmail").trim();
            String address = request.getParameter("cusAddress");
            String pass = request.getParameter("cusPass");
            String phone = request.getParameter("cusPhone").trim();
            
            Customer CusO = customerFacade.find(_code);
            if ((customerFacade.checkEmail(email)!= 0) && !(email).equalsIgnoreCase(CusO.getEmail())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Customer Email already exists");
                request.setAttribute("d",CusO);
                
                request.getRequestDispatcher("detailCustomer.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }
            
             if ((customerFacade.checkPhone(phone)!= 0) && !(phone).equalsIgnoreCase(CusO.getPhone())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Customer Phone already exists");
                request.setAttribute("d",CusO);
                
                request.getRequestDispatcher("detailCustomer.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }
            
            
            
            int count = customerFacade.find(_code).getLoginAttemptCount();

            int isAc = Integer.parseInt(request.getParameter("cusIsAc"));
            if (customerFacade.find(_code).getIsActive() == 0 && isAc == 1) {
                count = 0;

            }

            Date dNow = new Date();

            Customer customer = new Customer(_code, name, email, phone, pass, address, dNow, count, isAc);
            out.print(_code);
            customerFacade.edit(customer);

            request.setAttribute("noteShowPage", "Update Account Customer Success!!");
            request.getRequestDispatcher("customerServlet?action=showPage").forward(request, response);
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
