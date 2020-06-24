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
import model.Category;
import model.CategoryFacadeLocal;
import model.ProductFacadeLocal;
import model.SubCategoryFacadeLocal;

/**
 *
 * @author ASUS
 */
public class caterogyServlet extends HttpServlet {

    @EJB
    private SubCategoryFacadeLocal subCategoryFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

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

            request.getRequestDispatcher("insertCaterogy.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("insert")) {

            String nameCate = request.getParameter("cateName").trim();

            if (categoryFacade.checkNameCate(nameCate) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertCatePage", "Category name already exists");
                request.getRequestDispatcher("caterogyServlet?action=showInsert").forward(request, response);
                return;

            }

            Date dNow = new Date();
            Category category = new Category(nameCate, dNow);
            categoryFacade.create(category);
            request.setAttribute("noteShowPage", "Insert Category Success!!");
            out.print("insert thanh cong");
            request.getRequestDispatcher("caterogyServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("showPage")) {

            request.setAttribute("list", categoryFacade.findAll());
            request.getRequestDispatcher("showCategory.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Category delCate = categoryFacade.find(_code);

            if (productFacade.getProductByCate(_code).size() == 0 && subCategoryFacade.findByCateID(code).size() == 0) {

                categoryFacade.remove(delCate);
                // out.print("tao xoa do");
                // out.print(delCate.getSubCategoryCollection().size());
                request.setAttribute("noteShowPage", "Delete Category Success!!");

            } else {

                request.setAttribute("noteShowPage", "Delete Failed,It was used!!");

            }

            request.setAttribute("list", categoryFacade.findAll());
            request.getRequestDispatcher("showCategory.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("detail")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Category dCate = categoryFacade.find(_code);

            request.setAttribute("d", dCate);
            request.getRequestDispatcher("detailCategory.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {

            String nameID = request.getParameter("cateID");
            int _nameID = Integer.parseInt(nameID);
            String nameCate = request.getParameter("cateName").trim();

            Category CateO = categoryFacade.find(Integer.parseInt(nameID));
            if ((categoryFacade.checkNameCate(nameCate) != 0) && !(nameCate).equalsIgnoreCase(CateO.getName())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Category name already exists");
                request.setAttribute("d",CateO);
                request.getRequestDispatcher("detailCategory.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }

            Date dNow = new Date();
            Category eCategory = new Category(_nameID, nameCate, dNow);

            categoryFacade.edit(eCategory);

            request.setAttribute("noteShowPage", "Update Category Success!!");
            request.getRequestDispatcher("caterogyServlet?action=showPage").forward(request, response);

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
