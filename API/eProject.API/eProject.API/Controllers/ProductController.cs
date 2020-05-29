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
            var list = await _context.Products.ToListAsync();
            return JsonConvert.SerializeObject(list);
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

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       select new ProductResponse
                       {
                           Id = product.Id,
                           Name = product.Name,
                           ManufacturerName = manufacturer.Name,
                           Price = product.Price,
                           ImageURL = product.ImageURL
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
            var products = await _context.Products.ToListAsync();
            var manufacturers = await _context.Manufacturers.ToListAsync();

            var list = from product in products
                       join manufacturer in manufacturers on product.ManufacturerId equals manufacturer.Id
                       orderby product.Id descending
                       select new ProductResponse
                       {
                           Id = product.Id,
                           Name = product.Name,
                           ManufacturerName = manufacturer.Name,
                           Price = product.Price,
                           ImageURL = product.ImageURL
                       };
            return JsonConvert.SerializeObject(list.Take(12));
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