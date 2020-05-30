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
            var cus = _context.Customers.FirstOrDefault(x => x.Email == loginRequest.Email && x.Password == loginRequest.Password);
            LoginResponse response = new LoginResponse();
            if (cus != null)
            {
                response.Id = cus.Id;
                response.Name = cus.Name;
                response.Email = cus.Email;
                response.Phone = cus.Phone;
                return JsonConvert.SerializeObject(response);
            }
            else
            {
                return JsonConvert.SerializeObject(response);
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
                    ErrorCode = ErrorCode.Failed,
                    Content = "Email is existed."
                };
            cus = _context.Customers.FirstOrDefault(x => x.Phone == signUpRequest.Phone);
            if (cus != null)
                return new RequestResult
                {
                    ErrorCode = ErrorCode.Failed,
                    Content = "Phone is existed."
                };
            Customer customer = new Customer()
            {
                Id = 0,
                Name = signUpRequest.Name,
                Email = signUpRequest.Email,
                Phone = signUpRequest.Phone,
                Password = signUpRequest.Password,
                LoginAttemptCount = 0,
                ModifiedDate = DateTime.Now,
                IsActive = true
            };
            _context.Customers.Add(customer);
            await _context.SaveChangesAsync();

            cus = _context.Customers.FirstOrDefault(x => x.Email == signUpRequest.Email && x.Phone == signUpRequest.Phone);

            LoginResponse response = new LoginResponse();
            if (cus != null)
            {
                response.Id = cus.Id;
                response.Name = cus.Name;
                response.Email = cus.Email;
                response.Phone = cus.Phone;
                return new RequestResult
                {
                    ErrorCode = ErrorCode.Success,
                    Content = JsonConvert.SerializeObject(response)
                };
            }
            else
            {
                return new RequestResult
                {
                    ErrorCode = ErrorCode.Failed,
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
                                   where product.IsActive == true
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
                    var productInCart = _context.CartDetails.FirstOrDefault(x => x.ProductId == addToCartRequest.ProductId);
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
    }
}