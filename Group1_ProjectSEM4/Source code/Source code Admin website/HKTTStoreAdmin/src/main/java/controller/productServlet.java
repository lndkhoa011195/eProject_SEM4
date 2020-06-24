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
import model.CartDetail;
import model.CartDetailFacadeLocal;
import model.CategoryFacadeLocal;
import model.ManufacturerFacadeLocal;
import model.OrderDetailFacadeLocal;
import model.Product;
import model.ProductFacadeLocal;
import model.SubCategoryFacadeLocal;
import model.UnitFacadeLocal;

/**
 *
 * @author ASUS
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class productServlet extends HttpServlet {

    @EJB
    private CartDetailFacadeLocal cartDetailFacade;

    @EJB
    private OrderDetailFacadeLocal orderDetailFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private UnitFacadeLocal unitFacade;

    @EJB
    private SubCategoryFacadeLocal subCategoryFacade;

    @EJB
    private ManufacturerFacadeLocal manufacturerFacade;

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

        //Data ip Cloud
        Cloudinary c = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "sakata1301",
                "api_key", "464934748453224",
                "api_secret", "gmpmF1l6i_qGKQPrghbfRdhmnAE"));

        if (action.equalsIgnoreCase("showInsert")) {

            request.setAttribute("listCate", categoryFacade.findAll());
            request.setAttribute("listSubCate", null);

            request.setAttribute("listUnit", unitFacade.findAll());
            request.setAttribute("listManu", manufacturerFacade.findAll());

            request.getRequestDispatcher("insertProduct.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {

            int _cate = Integer.parseInt(request.getParameter("prdCate"));
            int _unit = Integer.parseInt(request.getParameter("prdUnit"));
            int _manu = Integer.parseInt(request.getParameter("prdManu"));
            int _sub = Integer.parseInt(request.getParameter("prdSubCate"));
            String name = request.getParameter("prdName");
            String oprice = request.getParameter("prdOPrice");
            Double _oprice = Double.parseDouble(oprice);
            String sprice = request.getParameter("prdSPrice");
            Double _sprice = Double.parseDouble(sprice);
            String dec = request.getParameter("prdDec");
            if (dec.length() == 0) {
                dec = " ";
            }
            String made = request.getParameter("prdMade");
            if (made.length() == 0) {
                made = " ";
            }
            int isActive = Integer.parseInt(request.getParameter("prdIsAc"));
            Date dNow = new Date();

            if (productFacade.checkNameProduct(name) != 0) {
                out.print("da vao day");
                request.setAttribute("noteInsertProductPage", "Product name already exists");
                request.getRequestDispatcher("productServlet?action=showInsert").forward(request, response);
                return;

            } else {
                out.print("khong vao");
            }

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

            Product product = new Product(name, _oprice, _sprice, dec, made, url, dNow, isActive,
                    categoryFacade.find(_cate),
                    manufacturerFacade.find(_manu),
                    subCategoryFacade.find(_sub),
                    unitFacade.find(_unit));

            productFacade.create(product);

            out.print("insert succ");
            request.setAttribute("noteShowPage", "Insert Product Success!!");
            request.getRequestDispatcher("productServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("addSubCate")) {

            String cate = request.getParameter("prdCate");
            String unit = request.getParameter("prdUnit");
            String manu = request.getParameter("prdManu");
            String name = request.getParameter("prdName");
            String oprice = request.getParameter("prdOPrice");
            String sprice = request.getParameter("prdSPrice");
            String dec = request.getParameter("prdDec");
            String made = request.getParameter("prdMade");
            String date = request.getParameter("prdDate");
            String isActive = request.getParameter("prdIsAc");

            request.setAttribute("listCate", categoryFacade.findAll());
            request.setAttribute("listSubCate", subCategoryFacade.findByCateID(cate));
            request.setAttribute("listUnit", unitFacade.findAll());
            request.setAttribute("listManu", manufacturerFacade.findAll());

            request.setAttribute("idCate", cate);
            request.setAttribute("idUnit", unit);
            request.setAttribute("idManu", manu);
            request.setAttribute("idName", name);
            request.setAttribute("idOPrice", oprice);
            request.setAttribute("idSPrice", sprice);
            request.setAttribute("idDec", dec);
            request.setAttribute("idMade", made);
            request.setAttribute("idIsAc", isActive);

            request.getRequestDispatcher("insertProduct.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("showPage")) {

            request.setAttribute("list", productFacade.findAll());
            request.getRequestDispatcher("showProduct.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("detail")) {

            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);

            Product p = productFacade.find(Integer.parseInt(code));

            request.setAttribute("listSubCate", subCategoryFacade.findByCateID(p.getCategoryId().getId().toString()));
            request.setAttribute("listCate", categoryFacade.findAll());
            request.setAttribute("listUnit", unitFacade.findAll());
            request.setAttribute("listManu", manufacturerFacade.findAll());

            request.setAttribute("d", p);
            request.setAttribute("noteIsAc", checkChangeActive(_code));

            //out.print(p.getCategoryId().getId().toString());
            request.getRequestDispatcher("detailProduct.jsp").forward(request, response);

        } else if (action.equals("addSubCate2")) {
            //xu li du lieu
            out.print("servet da den");
            String id = request.getParameter("prdID");
            String cate = request.getParameter("prdCate");
            String unit = request.getParameter("prdUnit");
            String manu = request.getParameter("prdManu");
            String name = request.getParameter("prdName");
            String sub = request.getParameter("prdSubCate");
            String dec = request.getParameter("prdDec");
            String made = request.getParameter("prdMade");
            String oprice = request.getParameter("prdOPrice");
            Double _oprice = Double.parseDouble(oprice);
            String sprice = request.getParameter("prdSPrice");
            Double _sprice = Double.parseDouble(sprice);
            int isActive = Integer.parseInt(request.getParameter("prdIsAc"));

            String url = "";
            Product product = productFacade.find(Integer.parseInt(id));
            url = product.getImageURL();
            //ket thuc du lieu

            Product _product = new Product(Integer.parseInt(id), name, _oprice, _sprice, dec, made, url, null, isActive,
                    categoryFacade.find(Integer.parseInt(cate)),
                    manufacturerFacade.find(Integer.parseInt(manu)),
                    null,
                    unitFacade.find(Integer.parseInt(unit)));

            request.setAttribute("d", _product);
            out.print("oklalalala ");

            request.setAttribute("listSubCate", subCategoryFacade.findByCateID(_product.getCategoryId().getId().toString()));
            request.setAttribute("listCate", categoryFacade.findAll());
            request.setAttribute("listUnit", unitFacade.findAll());
            request.setAttribute("listManu", manufacturerFacade.findAll());
            request.setAttribute("noteIsAc", checkChangeActive(Integer.parseInt(id)));
            request.getRequestDispatcher("detailProduct.jsp").forward(request, response);

        } else if (action.equalsIgnoreCase("update")) {

            String url = "";
            String id = request.getParameter("prdID");
            int _cate = Integer.parseInt(request.getParameter("prdCate"));
            int _unit = Integer.parseInt(request.getParameter("prdUnit"));
            int _manu = Integer.parseInt(request.getParameter("prdManu"));
            int _sub = Integer.parseInt(request.getParameter("prdSubCate"));
            String name = request.getParameter("prdName").trim();
            String oprice = request.getParameter("prdOPrice");
            Double _oprice = Double.parseDouble(oprice);
            String sprice = request.getParameter("prdSPrice");
            Double _sprice = Double.parseDouble(sprice);
            String dec = request.getParameter("prdDec");
            if (dec.length() == 0) {
                dec = " ";
            }
            String made = request.getParameter("prdMade");
            if (made.length() == 0) {
                made = " ";
            }
            int isActive = Integer.parseInt(request.getParameter("prdIsAc"));
            Date dNow = new Date();

            
            Product prO = productFacade.find(Integer.parseInt(id));
            if ((productFacade.checkNameProduct(name) != 0)&& !(name).equalsIgnoreCase(prO.getName())) {
                out.print("da vao day");
                

                request.setAttribute("listSubCate", subCategoryFacade.findByCateID(prO.getCategoryId().getId().toString()));
                request.setAttribute("listCate", categoryFacade.findAll());
                request.setAttribute("listUnit", unitFacade.findAll());
                request.setAttribute("listManu", manufacturerFacade.findAll());

                request.setAttribute("d", prO);
                request.setAttribute("noteIsAc", checkChangeActive(Integer.parseInt(id)));

                //out.print(p.getCategoryId().getId().toString());
                request.setAttribute("noteInsertCatePage", "Product name already exists");
                request.getRequestDispatcher("detailProduct.jsp").forward(request, response);

                
                
                return;

            } else {
                out.print("khong vao");
            }

            String changeNote = request.getParameter("noteIsAc");
            out.print("chang:" + changeNote);
            if (changeNote.equalsIgnoreCase("noneOrderHasCart")) {
                Product _product = productFacade.find(Integer.parseInt(id));
                List<CartDetail> list = (List<CartDetail>) cartDetailFacade.getCartDetailByPro(Integer.parseInt(id));
                out.print(list.size() + " " + _product.getId());
                for (CartDetail cartDetail : list) {
                    cartDetailFacade.remove(cartDetail);
                    out.print("xoa");
                }
            }

            Part p = request.getPart("image");
            String _filename = extractFileName(p);

            if (_filename.toString().length() == 0) {
                Product product = productFacade.find(Integer.parseInt(id));
                url = product.getImageURL();
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

            Product product = new Product(Integer.parseInt(id), name, _oprice, _sprice, dec, made, url, dNow, isActive,
                    categoryFacade.find(_cate),
                    manufacturerFacade.find(_manu),
                    subCategoryFacade.find(_sub),
                    unitFacade.find(_unit));

            productFacade.edit(product);

            out.print("insert succ");
            request.setAttribute("noteShowPage", "Update Product success!!");
            request.getRequestDispatcher("productServlet?action=showPage").forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            String code = request.getParameter("code");
            int _code = Integer.parseInt(code);
            System.out.print("TTTTTT:" + code);

            Product product = productFacade.find(_code);
            System.out.println("tt:" + orderDetailFacade.getOrderDetailByPro(_code));

            if (orderDetailFacade.getOrderDetailByPro(_code).size() != 0) {

                request.setAttribute("noteShowPage", "Delete Faild,Product already exists in OrderDetail!!");
                out.print("show delet");

                request.getRequestDispatcher("productServlet?action=showPage").forward(request, response);
            } else if (orderDetailFacade.getOrderDetailByPro(_code).size() == 0 && cartDetailFacade.getCartDetailByPro(_code).size() != 0) {

                List<CartDetail> list = (List<CartDetail>) cartDetailFacade.getCartDetailByPro(_code);
                out.print("xuat 2");
                for (CartDetail cartDetail : list) {
                    cartDetailFacade.remove(cartDetail);
                }
                productFacade.remove(product);
                request.setAttribute("noteShowPage", "Delete Product Success!!");
                request.getRequestDispatcher("productServlet?action=showPage").forward(request, response);

            } else if (orderDetailFacade.getOrderDetailByPro(_code).size() == 0 && cartDetailFacade.getCartDetailByPro(_code).size() == 0) {

                productFacade.remove(product);
                request.setAttribute("noteShowPage", "Delete Product Success!!!");
                request.getRequestDispatcher("productServlet?action=showPage").forward(request, response);

            }
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

    private String checkChangeActive(int code) {

        Product product = productFacade.find(code);

        if (product.getIsActive() == 0) {
            return "non";
        }

        if (orderDetailFacade.getOrderDetailByPro(code).size() != 0) {
            return "hasOrder";
        }
        if (orderDetailFacade.getOrderDetailByPro(code).size() == 0 && cartDetailFacade.getCartDetailByPro(code).size() != 0) {
            return "noneOrderHasCart";
        }

        if (orderDetailFacade.getOrderDetailByPro(code).size() == 0 && cartDetailFacade.getCartDetailByPro(code).size() == 0) {
            return "noneOrderNoneCart";
        }

        return "error";
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
