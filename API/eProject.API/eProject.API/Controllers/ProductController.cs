using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
using eProject.DataAccess.Models;
using eProject.DataAccess.Models.Response;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace eProject.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProductController : ControllerBase
    {
        private readonly ShopDbContext _context;

        public ProductController(ShopDbContext context)
        {
            _context = context;
        }

        /// <summary>
        /// Lấy danh sách tất cả product
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetProducts")]
        public async Task<string> GetProducts()
        {
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       join unit in units on product.UnitId equals unit.Id
                       where product.IsActive == true
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

            return JsonConvert.SerializeObject(list);
        }

        /// <summary>
        /// Lấy danh sách sản phẩm có tên chứa chuỗi name tìm kiếm
        /// </summary>
        /// <param name="name"></param>
        /// <returns></returns>
        [HttpGet("GetProductsByName")]
        public async Task<string> GetProductsByName(string name)
        {
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();
            
            if (string.IsNullOrEmpty(name))
            {
                  var list = from product in products
                           join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                           join unit in units on product.UnitId equals unit.Id
                           where product.IsActive == true
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
                return JsonConvert.SerializeObject(list);
            }
            else
            {
                var list = from product in products
                           join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                           join unit in units on product.UnitId equals unit.Id
                           where product.IsActive == true
                           where product.Name.ToLower().Contains(name.ToLower())
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
                return JsonConvert.SerializeObject(list);
            }
        }

        /// <summary>
        /// Lấy danh sách sản phẩm dựa vào SubCategoryId
        /// </summary>
        /// <param name="SubCategoryId"></param>
        /// <returns></returns>
        [HttpGet("GetProductsBySubCategory")]
        public async Task<string> GetProductsBySubCategory(int SubCategoryId)
        {
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();

            if (SubCategoryId > 0)
            {
                var list = from product in products
                           join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                           join unit in units on product.UnitId equals unit.Id
                           where product.IsActive == true
                           where product.SubCategoryId == SubCategoryId
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
                return JsonConvert.SerializeObject(list);
            }
            else
            {
                return JsonConvert.SerializeObject(new List<ProductResponse>());
            }
        }


        /// <summary>
        /// Lấy thông tin của product dựa vào id
        /// </summary>
        /// <param name="id"></param>
        /// <returns></returns>
        [HttpGet("GetProduct")]
        public async Task<string> GetProduct(int id)
        {
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       join unit in units on product.UnitId equals unit.Id
                       where product.IsActive == true
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
            var response = list.FirstOrDefault(x => x.Id == id);
            if (response != null)
                return JsonConvert.SerializeObject(response);
            else
                return JsonConvert.SerializeObject(new ProductResponse());
        }

        /// <summary>
        /// Lấy 12 sản phẩm được thêm vào gần nhất
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetRecentProducts")]
        public async Task<string> GetRecentProducts()
        {
            var products =  _context.Products.ToListAsync().Result.Take(12);
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       join unit in units on product.UnitId equals unit.Id
                       where product.IsActive == true
                       orderby product.Id descending
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
            return JsonConvert.SerializeObject(list);
        }

        /// <summary>
        /// Lấy danh sách sản phẩm giảm giá nhiều nhất
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetBestDealProducts")]
        public async Task<string> GetBestDealProducts()
        {
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();
            var units = await _context.Units.ToListAsync();

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       join unit in units on product.UnitId equals unit.Id
                       where product.IsActive == true
                       orderby (product.OriginalPrice - product.SellingPrice) descending
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
            return JsonConvert.SerializeObject(list.Take(9));
        }

        /// <summary>
        /// Lấy danh sách các sản phẩm quảng cáo
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetPromotionProducts")]
        public async Task<string> GetPromotionProducts()
        {
            var promo = _context.Promotions.ToListAsync().Result;

            return JsonConvert.SerializeObject(promo);
        }
    }

    
}