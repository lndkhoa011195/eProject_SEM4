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
import model.CategoryFacadeLocal;
import model.ProductFacadeLocal;
import model.SubCategory;
import model.SubCategoryFacadeLocal;

/**
 *
 * @author ASUS
 */
public class subCateServlet extends HttpServlet {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

    @EJB
    private SubCategoryFacadeLocal subCategoryFacade;

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
        
        String action=request.getParameter("action");
        
        if (action.equalsIgnoreCase("showInsert")){
            
            request.setAttribute("listCate",categoryFacade.findAll());
            out.print(categoryFacade.findAll().size());
            request.getRequestDispatcher("insertSubCate.jsp").forward(request, response);
            
        }else if (action.equalsIgnoreCase("insert")){
            
            String subCateName=request.getParameter("subCateName").trim();
            int selectCate=Integer.parseInt(request.getParameter("selectCate"));
            
            if (subCategoryFacade.checknameSub(subCateName) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertSubCatePage", "SubCate name already exists");
                request.getRequestDispatcher("subCateServlet?action=showInsert").forward(request, response);
                return;

            }
            
            Date dNow = new Date();
            
            SubCategory subCategory=new SubCategory(subCateName,dNow,categoryFacade.find(selectCate));
            subCategoryFacade.create(subCategory);
            request.setAttribute("noteShowPage","Insert SubCategory Success!!");
            request.getRequestDispatcher("subCateServlet?action=showPage").forward(request, response);
            
        }else if(action.equalsIgnoreCase("showPage")){
            request.setAttribute("list", subCategoryFacade.findAll());
            request.getRequestDispatcher("showSubCate.jsp").forward(request, response);
        }else if (action.equalsIgnoreCase("delete")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            SubCategory delSub=subCategoryFacade.find(_code);
            
            

            if (productFacade.getProductBySubCate(_code).size()==0) {

                subCategoryFacade.remove(delSub);
                request.setAttribute("noteShowPage", "Delete SubCategory Success!!");

            } else {

                request.setAttribute("noteShowPage", "Delete SubCategory Failed,It was used");

            }

            request.setAttribute("list", subCategoryFacade.findAll());
            request.getRequestDispatcher("showSubCate.jsp").forward(request, response);

        }else if (action.equalsIgnoreCase("detail")){
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            request.setAttribute("d",subCategoryFacade.find(_code));
            request.setAttribute("listCate",categoryFacade.findAll());
            
            request.getRequestDispatcher("detailSubCate.jsp").forward(request, response);
            
            
        }else if (action.equalsIgnoreCase("update")){
            String nameID = request.getParameter("subID");
            int _nameID = Integer.parseInt(nameID);
            
            String nameSub = request.getParameter("subName").trim();
            int selectCate=Integer.parseInt(request.getParameter("subCate"));
            
            SubCategory SubCateO = subCategoryFacade.find(Integer.parseInt(nameID));
            if ((subCategoryFacade.checknameSub(nameSub)!= 0) && !(nameSub).equalsIgnoreCase(SubCateO.getName())) {

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "SubCategory name already exists");
                request.setAttribute("d",SubCateO);
                request.setAttribute("listCate",categoryFacade.findAll());
                request.getRequestDispatcher("detailSubCate.jsp").forward(request, response);

                return;

            } else {
                out.print("khong vao");
            }
            
            
            Date dNow = new Date();
            
            SubCategory subCategory=new SubCategory(_nameID,nameSub,dNow,categoryFacade.find(selectCate));
            subCategoryFacade.edit(subCategory);
            
            request.setAttribute("noteShowPage","update SubCategory Success!!");
            request.getRequestDispatcher("subCateServlet?action=showPage").forward(request, response);
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
