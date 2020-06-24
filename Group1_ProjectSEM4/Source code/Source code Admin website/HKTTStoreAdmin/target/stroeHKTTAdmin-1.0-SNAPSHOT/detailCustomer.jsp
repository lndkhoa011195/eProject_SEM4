

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Employee"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Customer</title>

        <!-- Custom fonts for this template-->
        <link href="dataWeb/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="dataWeb/css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body id="page-top">
        <%
            Employee loginedInfo
                    = (Employee) session.getAttribute("acc");
            if (loginedInfo == null) {%>
        <jsp:forward page="index.jsp" />

        <% } %>



        <!-- Page Wrapper -->
        <div id="wrapper">

            <!-- Sidebar -->
            <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

                <!-- Sidebar - Brand -->
                <a class="sidebar-brand d-flex align-items-center justify-content-center" href="dashbordServlet">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-laugh-wink"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3">Store Management</div>
                </a>

                <!-- Divider -->
                <hr class="sidebar-divider my-0">

                <!-- Nav Item - Dashboard -->
                <li class="nav-item">
                    <a class="nav-link" href="dashbordServlet">
                        <i class="fas fa-fw fa-tachometer-alt"></i>
                        <span>Dashboard</span></a>
                </li>

                <!-- Divider -->
                <hr class="sidebar-divider">
                <!-- Nav Item - Product Management Collapse Menu -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>Product Management</span>
                    </a>
                    <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <h6 class="collapse-header">Product Management:</h6>
                            <a class="collapse-item" href="caterogyServlet?action=showPage">Category</a>
                            <a class="collapse-item" href="subCateServlet?action=showPage">SubCategory</a>
                            <a class="collapse-item" href="unitServlet?action=showPage">Unit</a> 
                            <a class="collapse-item" href="manufacturerServlet?action=showPage">Manufacturer</a>
                            <a class="collapse-item" href="productServlet?action=showPage">Product</a> 
                            <a class="collapse-item" href="promotionServlet?action=showPage">Promotion</a>
                        </div>
                    </div>
                </li>



                <!-- Nav Item - Customer Collapse Menu -->
                <li class="nav-item active">
                    <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapseCus" aria-expanded="true" aria-controls="collapseCus">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>Customer Management</span>
                    </a>
                    <div id="collapseCus" class="collapse show" aria-labelledby="headingCus" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <h6 class="collapse-header">Customer Management:</h6>
                            <a class="collapse-item active" href="customerServlet?action=showPage">Customer</a>                            
                        </div>
                    </div>
                </li>
                <!-- Nav Item - Order Collapse Menu -->
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOrder" aria-expanded="true" aria-controls="collapseOrder">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>Order Mamagement</span>
                    </a>
                    <div id="collapseOrder" class="collapse" aria-labelledby="headingOrder" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <h6 class="collapse-header">Order Management:</h6>
                            <a class="collapse-item" href="orderServlet?action=showPage">Order</a>                            
                        </div>
                    </div>
                </li>
                <li class="nav-item">
                    <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseReport" aria-expanded="true" aria-controls="collapseReport">
                        <i class="fas fa-fw fa-cog"></i>
                        <span>Report Mamagement</span>
                    </a>
                    <div id="collapseReport" class="collapse" aria-labelledby="headingReport" data-parent="#accordionSidebar">
                        <div class="bg-white py-2 collapse-inner rounded">
                            <h6 class="collapse-header">Report Management:</h6>
                            <a class="collapse-item" href="reportServlet?action=showPage">Report</a>                            
                        </div>
                    </div>
                </li>
            </ul>
            <!-- End of Sidebar -->

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                        <!-- Sidebar Toggle (Topbar) -->
                        <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                            <i class="fa fa-bars"></i>
                        </button>


                        <!-- Topbar Navbar -->
                        <ul class="navbar-nav ml-auto">
                            <div class="topbar-divider d-none d-sm-block"></div>

                            <!-- Nav Item - User Information -->
                            <li class="nav-item dropdown no-arrow">
                                <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span class="mr-2 d-none d-lg-inline text-gray-600 small">
                                        <%
                                            loginedInfo
                                                    = (Employee) session.getAttribute("acc");
                                            out.print(loginedInfo.getName());%>
                                    </span>
                                    <img class="img-profile rounded-circle" src="https://source.unsplash.com/QAB-WJcbgJk/60x60">
                                </a>
                                <!-- Dropdown - User Information -->
                                <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in" aria-labelledby="userDropdown">
                                    <a class="dropdown-item" href="employeeServlet?action=detail">
                                        <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                                        Edit Profile
                                    </a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">
                                        <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                        Logout
                                    </a>
                                </div>
                            </li>

                        </ul>

                    </nav>
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <h1 class="h3 mb-4 text-gray-800">${noteInsertCatePage}</h1>


                        <!--          edit phan insert-->
                        <div class="container">

                            <!-- Outer Row -->
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4" style="font-weight: bold">UPDATE DETAIL CUSTOMER</h1>
                                </div>

                                <form class="user" action="customerServlet?action=update" onsubmit = "return validateForm()" method="POST">

                                    <div class="form-group">
                                         <input type="text" name="cusID" value="${d.id}" hidden="true" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter ID Customer...">
                                    </div>

                                    <div class="form-group row">
                                        <span style="font-weight: bold;width: 17%; margin:auto">Customer's name:</span><input type="text" name="cusName" pattern="[a-zA-Z ]{0,50}" required="true" value="${d.name}"class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Name Customer..." style="width:83%">
                                    </div>

                                    <div class="form-group row">
                                         <span style="font-weight: bold;width: 17%; margin:auto">Customer's phone:</span><input type="text" name="cusPhone" pattern="[0-9]{1}[1-9]{1}[0-9]{8,11}" required="true" value="${d.phone}"class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Phone Customer..." style="width:83%">
                                    </div>

                                    <div class="form-group row">
                                         <span style="font-weight: bold;width: 17%; margin:auto">Customer's address:</span><input type="text" name="cusAddress"  pattern=".{0,200}"required="true" value="${d.address}"class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Phone Customer.." style="width:83%">
                                    </div>

                                    <div class="form-group row">
                                         <span style="font-weight: bold;width: 17%; margin:auto">Customer's email:</span><input type="email" name="cusEmail" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required="true" value="${d.email}"class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Email Customer..." style="width:83%">
                                    </div>

                                    <div class="form-group row">
                                        <span style="font-weight: bold;width: 17%; margin:auto">Password:</span><input type="password" id="empPass" name="cusPass" required="true" value=""  class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Pass first..." style="width:83%">
                                    </div>

                                    <div class="form-group row">
                                         <span style="font-weight: bold;width: 17%; margin:auto">Confirm password:</span><input type="password" id="empPass2" name="cusPass2" required="true" value="" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp" placeholder="Enter Pass Second..." style="width:83%">
                                    </div>

                                    <!--is Active-->
                                    <div class="form-group row">
                                         <span style="font-weight: bold;width: 17%; margin:auto">Status</span>
                                        <select id="cusIsActive" required="true" class="form-control" name="cusIsAc" style="width:83%">
                                            <c:choose>
                                                <c:when test="${d.isActive==0}">
                                                    <option value="1" >Active</option>
                                                    <option value="0" selected="true" >Not active</option>
                                                </c:when>  

                                                <c:otherwise>
                                                    <option value="1" selected="true" >Active</option>
                                                    <option value="0" >Not active</option>
                                                </c:otherwise>
                                            </c:choose>


                                        </select>
                                    </div>
                                    <!--End is Active-->


                                    <input type="submit" value="Update" class="btn btn-primary btn-user btn-block">

                                </form>

                                <script type = "text/javascript">
                                    function validateForm() {
                                        var pass = document.getElementById("cusPass").value;
                                        var pass2 = document.getElementById("cusPass2").value;

                                        if (pass != pass2) {
                                            alert("The pass is not the same");
                                            return false;
                                        }

                                        return true;
                                    }
                                </script>

                                <hr>

                            </div>

                        </div>



                    </div>
                    <!-- /.container-fluid -->

                </div>
                <!-- End of Main Content -->

                <!-- Footer -->
                <footer class="sticky-footer bg-white">
                    <div class="container my-auto">
                        <div class="copyright text-center my-auto">
                            <span>HKTT Store</span>
                        </div>
                    </div>
                </footer>
                <!-- End of Footer -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->

        <!-- Scroll to Top Button-->
        <a class="scroll-to-top rounded" href="#page-top">
            <i class="fas fa-angle-up"></i>
        </a>

        <!-- Logout Modal-->
        <div class="modal fade" id="logoutModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">Select "Logout" below if you are ready to end your current session.</div>
                    <div class="modal-footer">
                        <button class="btn btn-secondary" type="button" data-dismiss="modal">Cancel</button>
                        <a class="btn btn-primary" href="logoutServlet?action=logout">Logout</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap core JavaScript-->
        <script src="dataWeb/vendor/jquery/jquery.min.js"></script>
        <script src="dataWeb/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="dataWeb/vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="dataWeb/js/sb-admin-2.min.js"></script>

    </body>

</html>
