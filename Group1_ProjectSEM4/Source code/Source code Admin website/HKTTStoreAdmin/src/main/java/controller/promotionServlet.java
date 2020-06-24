/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Product;
import model.ProductFacadeLocal;
import model.Promotion;
import model.PromotionFacadeLocal;

/**
 *
 * @author ASUS
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class promotionServlet extends HttpServlet {

    @EJB
    private PromotionFacadeLocal promotionFacade;

    @EJB
    private ProductFacadeLocal productFacade;

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

        //Data ip Cloud
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "sakata1301",
                "api_key", "464934748453224",
                "api_secret", "gmpmF1l6i_qGKQPrghbfRdhmnAE"));

        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("showPage")) {

            request.setAttribute("list", promotionFacade.findAll());
            request.getRequestDispatcher("showPromotion.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("showInsert")) {
            if (promotionFacade.findAll().size() >= 5) {
                request.setAttribute("noteShowPage", "Promotion quantity exceeds 5 products");
                request.getRequestDispatcher("promotionServlet?action=showPage").forward(request, response);
            } else {

                List<Product> listProduct = productFacade.findAll();
                List<Product> listProductShow = new ArrayList<Product>();
                List<Promotion> listPromotion = promotionFacade.findAll();

                int check;
                for (Product product : listProduct) {
                    check = 0;

                    for (Promotion promotion : listPromotion) {
                        if (product.getId() == promotion.getProductId().getId()) {

                            check = 1;

                        }
                    }
                    if (check != 1) {
                        listProductShow.add(product);

                    } else {
                        check = 0;
                    }

                }

                request.setAttribute("listS", listProductShow);
                request.getRequestDispatcher("showProductForPromotion.jsp").forward(request, response);
            }
        } else if (action.equalsIgnoreCase("selectProduct")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            request.setAttribute("ds", productFacade.find(_code));
            request.getRequestDispatcher("insertPromotion.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("insert")) {

            int _code = Integer.parseInt(request.getParameter("prdID"));
            Date dNow = new Date();

            //upanh
            String save_dir = "image";
            String app_path = request.getServletContext().getRealPath("");

            Part p = request.getPart("image");
            String _filename = extractFileName(p);

            System.out.println(app_path);
            String save_path = app_path + File.separator + save_dir;
            File f = new File(save_path);
            if (!f.exists()) {
                f.mkdir();
            }

            File f1 = new File(save_path + "/" + _filename);

            FileOutputStream fos = new FileOutputStream(f1);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            String image = _filename;

            byte b[] = new byte[p.getInputStream().available()];

            System.out.println(p.getInputStream().available());

            p.getInputStream().read(b);

            bos.write(b);
            bos.close();
            fos.close();

            Map upload = c.uploader().upload(f1, ObjectUtils.emptyMap());
            String url = upload.get("secure_url").toString();
            //  String test = upload.get("url").toString();
            out.print(upload.get("secure_url"));

            Promotion promotion = new Promotion(url, dNow, productFacade.find(_code));
            promotionFacade.create(promotion);
            request.setAttribute("noteShowPage", "Insert Promotion Success!!");
            request.getRequestDispatcher("promotionServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            int _code = Integer.parseInt(request.getParameter("code"));
            Promotion delPro = promotionFacade.find(_code);
            promotionFacade.remove(delPro);
            request.setAttribute("noteShowPage", "Delete Success!!");
            request.getRequestDispatcher("promotionServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("detail")) {
            int _code = Integer.parseInt(request.getParameter("code"));

            Promotion promotion = promotionFacade.find(_code);
            request.setAttribute("d", promotion);
            request.getRequestDispatcher("detailPromotion.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {
            int _code = Integer.parseInt(request.getParameter("proID"));

            String url = "";
            Date dNow = new Date();
            Part p = request.getPart("image");
            String _filename = extractFileName(p);

            if (_filename.toString().length() == 0) {
                Promotion promotion = promotionFacade.find(_code);
                url = promotion.getImageURL();
                out.print("null");
            } else {
                out.print("cos du lieu mhs" + p);
                String save_dir = "image";
                String app_path = request.getServletContext().getRealPath("");

                System.out.println(app_path);
                String save_path = app_path + File.separator + save_dir;
                File f = new File(save_path);
                if (!f.exists()) {
                    f.mkdir();
                }

                File f1 = new File(save_path + "/" + _filename);

                FileOutputStream fos = new FileOutputStream(f1);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                String image = _filename;

                byte b[] = new byte[p.getInputStream().available()];

                System.out.println(p.getInputStream().available());

                p.getInputStream().read(b);

                bos.write(b);
                bos.close();
                fos.close();

                Map upload = c.uploader().upload(f1, ObjectUtils.emptyMap());
                url = upload.get("secure_url").toString();
                //  String test = upload.get("url").toString();
                out.print(upload.get("secure_url"));

            }
            Promotion promotion = promotionFacade.find(_code);
            promotion.setImageURL(url);
            promotion.setModifiedDate(dNow);
            promotionFacade.edit(promotion);

            request.setAttribute("noteShowPage", "Update success!!");
            request.getRequestDispatcher("promotionServlet?action=showPage").forward(request, response);

        }

    }

    private String extractFileName(Part part) {
        // form-data; name="file"; filename="C:\file1.zip"
        // form-data; name="file"; filename="C:\Note\file2.zip"
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                // C:\file1.zip
                // C:\Note\file2.zip
                String clientFileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                clientFileName = clientFileName.replace("\\", "/");
                int i = clientFileName.lastIndexOf('/');
                // file1.zip
                // file2.zip
                return clientFileName.substring(i + 1);
            }
        }
        return null;
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
