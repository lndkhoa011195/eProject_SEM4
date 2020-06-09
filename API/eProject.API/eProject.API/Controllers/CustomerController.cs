using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
using eProject.DataAccess.Models;
using eProject.DataAccess.Models.Request;
using eProject.DataAccess.Models.Response;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace eProject.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CustomerController : ControllerBase
    {

        private readonly ShopDbContext _context;

        public CustomerController(ShopDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Chứng thực đăng nhập
        /// </summary>
        /// <param name="loginRequest"></param>
        /// <returns></returns>
        [HttpPost("Authenticate")]
        public async Task<string> Authenticate(LoginRequest loginRequest)
        {
            //Tìm kiếm xem email có tồn tại hay không
            var cus = _context.Customers.FirstOrDefault(x => x.Email == loginRequest.Email);
            LoginResponse response = new LoginResponse();

            //Email có tồn tại
            if (cus != null)
            {
                //Tài khoản còn active hay không
                if (cus.IsActive == 0)
                {
                    return JsonConvert.SerializeObject(new Customer());
                }
                else if (cus.Password == loginRequest.Password) //Kiểm tra xem password có đúng hay không
                {
                    cus.LoginAttemptCount = 0;
                    _context.Customers.Update(cus);
                    _context.SaveChanges();

                    //response.Id = cus.Id;
                    //response.Name = cus.Name;
                    //response.Email = cus.Email;
                    //response.Phone = cus.Phone;
                    return JsonConvert.SerializeObject(cus);
                }
                else //Password không đúng
                {
                    //Nếu đã đăng nhập sai mật khẩu 3 lần, khoá tài khoản
                    if (cus.LoginAttemptCount == 3)
                    {
                        cus.LoginAttemptCount = 0;
                        cus.IsActive = 0;
                        _context.Customers.Update(cus);
                        _context.SaveChanges();
                    }
                    else //Đăng nhập sai < 3 lần, cộng LoginAttemptCount
                    {
                        cus.LoginAttemptCount++;
                        _context.Customers.Update(cus);
                        _context.SaveChanges();
                    }
                    return JsonConvert.SerializeObject(new Customer());
                }
            }
            else
            {
                return JsonConvert.SerializeObject(new Customer());
            }
        }

        /// <summary>
        /// Đăng ký customer mới
        /// </summary>
        /// <param name="signUpRequest"></param>
        /// <returns></returns>
        [HttpPost("SignUp")]
        public async Task<RequestResult> SignUp(SignUpRequest signUpRequest)
        {
            var cus = _context.Customers.FirstOrDefault(x => x.Email == signUpRequest.Email);
            if (cus != null)
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Email is existed."
                };
            cus = _context.Customers.FirstOrDefault(x => x.Phone == signUpRequest.Phone);
            if (cus != null)
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Phone is existed."
                };
            Customer customer = new Customer()
            {
                Id = 0,
                Name = signUpRequest.Name,
                Email = signUpRequest.Email,
                Phone = signUpRequest.Phone,
                Password = signUpRequest.Password,
                Address = signUpRequest.Address,
                LoginAttemptCount = 0,
                ModifiedDate = DateTime.Now,
                IsActive = 1
            };
            _context.Customers.Add(customer);
            await _context.SaveChangesAsync();

            cus = _context.Customers.FirstOrDefault(x => x.Email == signUpRequest.Email && x.Phone == signUpRequest.Phone);

            SignUpRequest response = new SignUpRequest();
            if (cus != null)
            {
                response.Id = cus.Id;
                response.Name = cus.Name;
                response.Email = cus.Email;
                response.Phone = cus.Phone;
                response.Password = cus.Password;
                response.Address = cus.Address;
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                    Content = JsonConvert.SerializeObject(response)
                };
            }
            else
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Invalid information."
                };
            }
        }


        /// <summary>
        /// Đổi password
        /// </summary>
        /// <param name="request"></param>
        /// <returns></returns>
        [HttpPost("ChangePassword")]
        public async Task<RequestResult> ChangePassword(ChangePassRequest request)
        {
            var customer = _context.Customers.Find(request.CustomerId);
            if (customer != null) //Có tồn tại
            {
                if (customer.IsActive == 1) //Tài khoản không bị khoá
                {
                    if (customer.Password.Equals(request.OldPassword)) //OldPassword trùng password cũ
                    {
                        customer.Password = request.NewPassword;
                        _context.Update(customer);
                        _context.SaveChanges();
                        customer = _context.Customers.Find(request.CustomerId);
                        return new RequestResult
                        {
                            StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                            Content = JsonConvert.SerializeObject(customer)
                        };
                    }
                    else
                    {
                        return new RequestResult
                        {
                            StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                            Content = "Old password is not correct."
                        };
                    }
                }
                else
                {
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                        Content = "Account is deactivated."
                    };
                }
            }
            else
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Invalid information."
                };
            }
        }



        /// <summary>
        /// Cập nhật lại thông tin người dùng
        /// </summary>
        /// <param name="updateRequest"></param>
        /// <returns></returns>
        [HttpPost("UpdateInfomation")]
        public async Task<RequestResult> UpdateInfomation(SignUpRequest updateRequest)
        {
            //Tìm kiếm xem Customer có tồn tại hay không
            var customer = _context.Customers.Find(updateRequest.Id);
            if (customer != null) //Có tồn tại
            {
                if (customer.IsActive == 1) //Tài khoản không bị khoá
                {
                    //kiểm tra xem Email mới có bị trùng với một tài khoản khác hay không
                    var checkMail = _context.Customers.SingleOrDefault(x => x.Email == updateRequest.Email && x.Id != updateRequest.Id);
                    if (checkMail != null) //Email đã tồn tại
                    {
                        return new RequestResult
                        {
                            StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                            Content = "Email is registed by other customer."
                        };
                    }
                    else
                    {
                        //kiểm tra xem Phone mới có bị trùng với một tài khoản khác hay không
                        var checkPhone = _context.Customers.SingleOrDefault(x => x.Phone == updateRequest.Phone && x.Id != updateRequest.Id);
                        if (checkPhone != null) //Phone đã tồn tại
                        {
                            return new RequestResult
                            {
                                StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                                Content = "Phone is registed by other customer."
                            };
                        }
                        else //Email và Phone mới không trùng
                        {
                            customer.Name = updateRequest.Name;
                            customer.Email = updateRequest.Email;
                            customer.Phone = updateRequest.Phone;
                            //customer.Password = updateRequest.Password;
                            customer.Address = updateRequest.Address;
                            customer.ModifiedDate = DateTime.Now;
                            _context.Customers.Update(customer);
                            _context.SaveChanges();

                            return new RequestResult
                            {
                                StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                                Content = JsonConvert.SerializeObject(customer)
                            };
                        }
                    }
                }
                else
                {
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                        Content = "Account is deactivated."
                    };
                }
            }
            else
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Invalid information."
                };
            }
        }

        /// <summary>
        /// Lấy danh sách sản phẩm trong giỏ hàng theo CustomerId
        /// </summary>
        /// <param name="_customerId"></param>
        /// <returns></returns>
        [HttpPost("GetProductsInCart")]
        public async Task<string> GetProductsInCart(int _customerId)
        {
            try
            {
                //Tìm kiếm xem CustomerId và ProductId có tồn tại hay không
                var customer = _context.Customers.Find(_customerId);
                if (customer != null)
                {
                    //Lấy thông tin cart dựa trên CustomerId
                    var cartD = _context.Carts.FirstOrDefault(x => x.CustomerId == _customerId);
                    if (cartD != null)
                    {
                        var products = _context.Products.ToList();
                        var cartDetails = _context.CartDetails.ToList();
                        var carts = _context.Carts.ToList();
                        var manufacturers = _context.Manufacturers.ToList();
                        var units = _context.Units.ToList();
                        var list = from cartDetail in cartDetails
                                   join cart in carts on cartDetail.CartId equals cart.Id
                                   join product in products on cartDetail.ProductId equals product.Id
                                   join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                                   join unit in units on product.UnitId equals unit.Id
                                   where product.IsActive == 1
                                   where cart.Id == cartD.Id
                                   select new CartResponse
                                   {
                                       Id = product.Id,
                                       Name = product.Name,
                                       OriginalPrice = product.OriginalPrice,
                                       SellingPrice = product.SellingPrice,
                                       Description = product.Description,
                                       MadeIn = product.MadeIn,
                                       ManufacturerName = manufacturer.Name,
                                       ImageURL = product.ImageURL,
                                       UnitName = unit.Name,
                                       Quantity = cartDetail.Quantity
                                   };

                        return JsonConvert.SerializeObject(list);
                    }
                    return "Failed";
                }
                else
                {
                    return "Failed";
                }

            }
            catch
            {
                return "Failed";
            }
        }

        /// <summary>
        /// Thêm sản phẩm vào giỏ hàng
        /// </summary>
        /// <param name="addToCartRequest"></param>
        /// <returns></returns>
        [HttpPost("AddToCart")]
        public async Task<string> AddToCart(CartRequest addToCartRequest)
        {
            try
            {
                int countCart = 0;
                //Tìm kiếm xem CustomerId và ProductId có tồn tại hay không
                var customer = _context.Customers.Find(addToCartRequest.CustomerId);
                var product = _context.Products.Find(addToCartRequest.ProductId);

                //Không tồn tại
                if (customer == null || product == null || addToCartRequest.Quantity == 0)
                {
                    return "Failed";
                }
                else
                {
                    //Kiểm tra xem CustomerId này đã có cart hay chưa
                    var cart = _context.Carts.FirstOrDefault(x => x.CustomerId == addToCartRequest.CustomerId);
                    //Chưa có thông tin cart, tạo 1 cart mới dựa vào CustomerId
                    if (cart == null)
                    {
                        Cart _cart = new Cart();
                        _cart.Id = 0;
                        _cart.CustomerId = addToCartRequest.CustomerId;
                        _context.Carts.Add(_cart);
                        await _context.SaveChangesAsync();
                    }
                    //Lấy lại thông tin cart
                    cart = _context.Carts.FirstOrDefault(x => x.CustomerId == addToCartRequest.CustomerId);
                    //Kiểm tra xem product này đã có tồn tại trong cart hay chưa?
                    var productInCart = _context.CartDetails.FirstOrDefault(x => x.ProductId == addToCartRequest.ProductId && x.CartId == cart.Id);
                    //Chưa tồn tại => thêm mới vào CartDetails
                    if (productInCart == null)
                    {
                        CartDetail cartDetail = new CartDetail();
                        cartDetail.Id = 0;
                        cartDetail.CartId = cart.Id;
                        cartDetail.ProductId = addToCartRequest.ProductId;
                        cartDetail.Quantity = addToCartRequest.Quantity;
                        _context.CartDetails.Add(cartDetail);
                        await _context.SaveChangesAsync();
                    }
                    else //đã tồn tại => cộng dồn số lượng
                    {
                        productInCart.Quantity += addToCartRequest.Quantity;
                        _context.CartDetails.Update(productInCart);
                        await _context.SaveChangesAsync();
                    }
                    //tính lại số lượng sản phẩm có trong cart
                    countCart = _context.CartDetails.Where(x => x.CartId == cart.Id).Count();
                    return countCart.ToString();
                }
            }
            catch
            {
                return "Failed";
            }

        }

        /// <summary>
        /// Giảm số lượng sản phẩm hoặc xoá sản phẩm khỏi giỏ hàng
        /// </summary>
        /// <param name="removeFromCartRequest"></param>
        /// <returns></returns>
        [HttpPost("RemoveFromCart")]
        public async Task<string> RemoveFromCart(CartRequest removeFromCartRequest)
        {
            try
            {
                int countCart = 0;
                //Tìm kiếm xem CustomerId và ProductId có tồn tại hay không
                var customer = _context.Customers.Find(removeFromCartRequest.CustomerId);
                var product = _context.Products.Find(removeFromCartRequest.ProductId);

                //Không tồn tại
                if (customer == null || product == null || removeFromCartRequest.Quantity == 0)
                {
                    return "Failed";
                }
                else
                {
                    //Lấy thông tin cart dựa vào CustomerId 
                    var cart = _context.Carts.FirstOrDefault(x => x.CustomerId == removeFromCartRequest.CustomerId);

                    //Lấy thông tin product từ trong CartDetails 
                    var productInCart = _context.CartDetails.FirstOrDefault(x => x.ProductId == removeFromCartRequest.ProductId);
                    //Nếu remove nhiều hơn đã có, out
                    if (productInCart != null)
                    {
                        if (productInCart.Quantity < removeFromCartRequest.Quantity)
                            return "Failed";

                        productInCart.Quantity -= removeFromCartRequest.Quantity;
                        //Nếu số lượng sau khi trừ > 0, chỉ update lại Quantity
                        if (productInCart.Quantity > 0)
                        {
                            _context.CartDetails.Update(productInCart);
                        }
                        else //Quantity = 0 => remove luôn
                        {
                            _context.CartDetails.Remove(productInCart);
                        }
                        await _context.SaveChangesAsync();

                        //tính lại số lượng sản phẩm có trong cart
                        countCart = _context.CartDetails.Where(x => x.CartId == cart.Id).Count();
                        return countCart.ToString();
                    }
                    return "Failed";
                }

            }
            catch
            {
                return "Failed";
            }
        }

        /// <summary>
        /// Lấy số lượng sản phẩm trong cart dựa vào CustomerId
        /// </summary>
        /// <param name="_customerId"></param>
        /// <returns></returns>
        [HttpPost("GetCartCount")]
        public async Task<int> GetCartCount(int _customerId)
        {
            int countCart = 0;
            try
            {

                var customer = _context.Customers.Find(_customerId);
                if (customer != null)
                {
                    //Lấy thông tin cart dựa trên CustomerId
                    var cartD = _context.Carts.FirstOrDefault(x => x.CustomerId == _customerId);
                    if (cartD != null)
                    {
                        var details = _context.CartDetails.Where(x => x.CartId == cartD.Id);
                        countCart = details.Count();
                    }
                    else
                    {
                        return countCart;
                    }
                }
                else
                {
                    return countCart;
                }
            }
            catch
            {
                return countCart;
            }
            return countCart;
        }

        /// <summary>
        /// Tạo Order mới và thêm thông tin vào OrderDetail
        /// </summary>
        /// <param name="checkOutRequest"></param>
        /// <returns></returns>
        [HttpPost("CheckOut")]
        public async Task<RequestResult> CheckOut(CheckOutRequest checkOutRequest)
        {
            try
            {
                //Kiểm tra CustomerId có tồn tại hay không
                var customer = _context.Customers.Find(checkOutRequest.CustomerId);
                //Có tồn tại
                if (customer != null)
                {
                    //Tìm CartID dựa vào CustomerId
                    var cart = _context.Carts.SingleOrDefault(x => x.CustomerId == checkOutRequest.CustomerId);
                    if (cart != null)
                    {
                        //Tìm danh sách sản phẩm hiện có trong cart
                        var cartDetails = _context.CartDetails.Where(x => x.CartId == cart.Id).ToList();
                        if (cartDetails.Count() > 0) //trong cart có hàng
                        {
                            //Tạo order mới
                            DateTime date = DateTime.Now;
                            string newOrderCode = date.ToString("yyyyMMddHHmmssfff") + "_" + checkOutRequest.CustomerId.ToString();
                            Orders newOrder = new Orders
                            {
                                Id = 0,
                                OrderCode = newOrderCode,
                                CustomerId = customer.Id,
                                ShipName = checkOutRequest.Name,
                                ShipPhone = checkOutRequest.Phone,
                                ShipAddress = checkOutRequest.ShippingAddress,
                                ShipNote = checkOutRequest.Note,
                                OrderDate = date,
                                Status = 1
                            };
                            _context.Orders.Add(newOrder);
                            _context.SaveChanges();
                            var order = _context.Orders.SingleOrDefault(x => x.CustomerId == checkOutRequest.CustomerId && x.OrderCode == newOrderCode);
                            if (order != null)
                            {
                                var products = _context.Products.ToList();
                                var manufacturers = _context.Manufacturers.ToList();
                                var units = _context.Units.ToList();

                                var list = from product in products
                                           join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                                           join unit in units on product.UnitId equals unit.Id
                                           where product.IsActive == 1
                                           select new ProductResponse
                                           {
                                               Id = product.Id,
                                               Name = product.Name,
                                               OriginalPrice = product.OriginalPrice,
                                               SellingPrice = product.SellingPrice,
                                               Description = product.Description,
                                               MadeIn = product.MadeIn,
                                               ManufacturerName = manufacturer.Name,
                                               ImageURL = product.ImageURL,
                                               UnitName = unit.Name
                                           };

                                foreach (var item in cartDetails)
                                {
                                    var temp = list.FirstOrDefault(x => x.Id == item.ProductId);
                                    OrderDetail orderDetail = new OrderDetail
                                    {
                                        Id = 0,
                                        OrderId = order.Id,
                                        ProductId = temp.Id,
                                        ProductName = temp.Name,
                                        OriginalPrice = temp.OriginalPrice,
                                        SellingPrice = temp.SellingPrice,
                                        Description = temp.Description,
                                        UnitName = temp.UnitName,
                                        ManufacturerName = temp.ManufacturerName,
                                        MadeIn = temp.MadeIn,
                                        ImageURL = temp.ImageURL,
                                        Quantity = item.Quantity
                                    };
                                    _context.OrderDetails.Add(orderDetail);
                                    _context.CartDetails.Remove(item);
                                    _context.SaveChanges();
                                }


                                return new RequestResult
                                {
                                    StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                                    Content = "Success"
                                };
                            }
                            else
                            {
                                return new RequestResult
                                {
                                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                                    Content = "Can not create order."
                                };
                            }
                        }
                        else
                        {
                            return new RequestResult
                            {
                                StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                                Content = "Can not find cart details."
                            };
                        }
                    }
                }
            }
            catch
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Failed"
                };
            }
            return new RequestResult
            {
                StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                Content = "Failed"
            };
        }


        /// <summary>
        /// Tạo Order mới và thêm thông tin vào OrderDetail
        /// </summary>
        /// <param name="checkOutRequest"></param>
        /// <returns></returns>
        [HttpPost("CheckOutTest")]
        public async Task<RequestResult> CheckOutTest(int CustomerId)
        {
            try
            {
                //Kiểm tra CustomerId có tồn tại hay không
                var customer = _context.Customers.Find(CustomerId);
                //Có tồn tại
                if (customer != null)
                {
                    //Tìm CartID dựa vào CustomerId
                    var cart = _context.Carts.SingleOrDefault(x => x.CustomerId == CustomerId);
                    if (cart != null)
                    {
                        //Tìm danh sách sản phẩm hiện có trong cart
                        var cartDetails = _context.CartDetails.Where(x => x.CartId == cart.Id).ToList();
                        if (cartDetails.Count() > 0) //trong cart có hàng
                        {
                            //Tạo order mới
                            DateTime date = DateTime.Now;
                            string newOrderCode = date.ToString("yyyyMMddHHmmssfff") + "_" + CustomerId.ToString();
                            Orders newOrder = new Orders
                            {
                                Id = 0,
                                OrderCode = newOrderCode,
                                CustomerId = customer.Id,
                                ShipName = customer.Name,
                                ShipPhone = customer.Phone,
                                ShipAddress = customer.Address,
                                ShipNote = "",
                                OrderDate = date,
                                Status = 1
                            };
                            _context.Orders.Add(newOrder);
                            _context.SaveChanges();
                            var order = _context.Orders.SingleOrDefault(x => x.CustomerId == CustomerId && x.OrderCode == newOrderCode);
                            if (order != null)
                            {
                                var products = _context.Products.ToList();
                                var manufacturers = _context.Manufacturers.ToList();
                                var units = _context.Units.ToList();

                                var list = from product in products
                                           join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                                           join unit in units on product.UnitId equals unit.Id
                                           where product.IsActive == 1
                                           select new ProductResponse
                                           {
                                               Id = product.Id,
                                               Name = product.Name,
                                               OriginalPrice = product.OriginalPrice,
                                               SellingPrice = product.SellingPrice,
                                               Description = product.Description,
                                               MadeIn = product.MadeIn,
                                               ManufacturerName = manufacturer.Name,
                                               ImageURL = product.ImageURL,
                                               UnitName = unit.Name
                                           };

                                foreach (var item in cartDetails)
                                {
                                    var temp = list.FirstOrDefault(x => x.Id == item.ProductId);
                                    OrderDetail orderDetail = new OrderDetail
                                    {
                                        Id = 0,
                                        OrderId = order.Id,
                                        ProductId = temp.Id,
                                        ProductName = temp.Name,
                                        OriginalPrice = temp.OriginalPrice,
                                        SellingPrice = temp.SellingPrice,
                                        Description = temp.Description,
                                        UnitName = temp.UnitName,
                                        ManufacturerName = temp.ManufacturerName,
                                        MadeIn = temp.MadeIn,
                                        ImageURL = temp.ImageURL,
                                        Quantity = item.Quantity
                                    };
                                    _context.OrderDetails.Add(orderDetail);
                                    _context.CartDetails.Remove(item);
                                    _context.SaveChanges();
                                }


                                return new RequestResult
                                {
                                    StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                                    Content = "Success"
                                };
                            }
                            else
                            {
                                return new RequestResult
                                {
                                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                                    Content = "Can not create order."
                                };
                            }
                        }
                        else
                        {
                            return new RequestResult
                            {
                                StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                                Content = "Can not find cart details."
                            };
                        }
                    }
                    else
                    {
                        return new RequestResult
                        {
                            StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                            Content = "Can not find cart."
                        };
                    }
                }
                else
                {
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                        Content = "Customer is not existed."
                    };
                }
            }
            catch
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Failed"
                };
            }
        }

        /// <summary>
        /// Lấy danh sách order dựa vào CustomerId
        /// </summary>
        /// <param name="OrderCode"></param>
        /// <returns></returns>
        [HttpPost("GetOrders")]
        public async Task<RequestResult> GetOrders(int CustomerId)
        {
            try
            {
                List<OrderResponse> orderResponses = new List<OrderResponse>();
                //Kiểm tra CustomerId có tồn tại hay không
                var customer = _context.Customers.Find(CustomerId);
                //Có tồn tại
                if (customer != null)
                {
                    //lấy danh sách order
                    var orders = _context.Orders.Where(x => x.CustomerId == customer.Id).ToList();
                    foreach (var order in orders)
                    {
                        double sellingSum = 0;
                        double originalSum = 0;
                        int count = 0;
                        var orderDetails = _context.OrderDetails.Where(x => x.OrderId == order.Id).ToList();
                        foreach (var detail in orderDetails)
                        {
                            sellingSum += detail.SellingPrice * detail.Quantity;
                            originalSum += detail.OriginalPrice * detail.Quantity;
                            count++;
                        }
                        OrderResponse orderResponse = new OrderResponse()
                        {
                            Id = order.Id,
                            OrderCode = order.OrderCode,
                            OrderDate = order.OrderDate.ToString("dd/MM/yyyy HH:mm:ss"),
                            Status = order.Status,
                            OriginalSum = originalSum,
                            SellingSum = sellingSum,
                            ProductCount = count
                        };
                        orderResponses.Add(orderResponse);
                    }
                    var temp = orderResponses.OrderByDescending(x => x.OrderCode);
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                        Content = JsonConvert.SerializeObject(temp)
                    };
                }
                else
                {
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                        Content = "Customer is not existed."
                    };
                }
            }
            catch
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Failed"
                };
            }
        }

        /// <summary>
        /// Lấy details dựa vào OrderCode
        /// </summary>
        /// <param name="OrderCode"></param>
        /// <returns></returns>
        [HttpPost("GetOrderDetails")]
        public async Task<RequestResult> GetOrderDetails(string OrderCode)
        {
            try
            {
                //Tìm kiếm orderId dựa vào OrderCode
                var order = _context.Orders.SingleOrDefault(x => x.OrderCode == OrderCode);
                if (order != null)
                {
                    //Lấy danh sách order details
                    //var details = _context.OrderDetails.Where(x => x.OrderId == order.Id);
                    var details = _context.OrderDetails.ToList();
                    var list = from detail in details
                               where detail.OrderId == order.Id
                               select new CartResponse
                               {
                                   Id = detail.ProductId,
                                   Name = detail.ProductName,
                                   OriginalPrice = detail.OriginalPrice,
                                   SellingPrice = detail.SellingPrice,
                                   Description = detail.Description,
                                   MadeIn = detail.MadeIn,
                                   ManufacturerName = detail.ManufacturerName,
                                   ImageURL = detail.ImageURL,
                                   UnitName = detail.UnitName,
                                   Quantity = detail.Quantity
                               };
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                        Content = JsonConvert.SerializeObject(list)
                    };
                }
                else
                {
                    return new RequestResult
                    {
                        StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                        Content = "Can not find order Id"
                    };
                }
            }
            catch
            {
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                    Content = "Failed"
                };
            }
        }
    }
}