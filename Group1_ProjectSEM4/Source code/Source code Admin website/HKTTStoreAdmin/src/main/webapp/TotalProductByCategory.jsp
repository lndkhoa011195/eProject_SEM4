
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="application/pdf" pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="net.sf.jasperreports.engine.*" %>

<%
    String DB_URL = "jdbc:sqlserver://localhost:1433;"
            + "databaseName=HKTTStoreDB;";
    String USER_NAME = "sa";
    String PASSWORD = "123";

    Connection conn = null;
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    try {
        String xmlFile = session.getServletContext().getRealPath("//report//TotalProductByCategory.jrxml");
        InputStream input = new FileInputStream(new File(xmlFile));

        JasperReport jasperReport = JasperCompileManager.compileReport(input);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

        response.getOutputStream().flush();
        response.getOutputStream().close();
    } catch (Exception ex) {
        ex.printStackTrace();
    } finally {
        if (conn != null) {
            conn.close();
        }
    }
%>

