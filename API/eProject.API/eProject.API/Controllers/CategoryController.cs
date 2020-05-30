using System;
using System.Collections.Generic;
using System.ComponentModel;
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
    public class CategoryController : ControllerBase
    {
        private readonly ShopDbContext _context;

        public CategoryController(ShopDbContext context)
        {
            _context = context;
        }


        
        /// <summary>
        /// Lấy danh sách tất cả Category
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetCategories")]
        public async Task<string> GetCategories()
        {
            var list = await _context.Categories.ToListAsync();
            return JsonConvert.SerializeObject(list);
        }

        /// <summary>
        /// Lấy danh sách tất cả SubCategory + Category
        /// </summary>
        /// <returns></returns>
        [HttpGet("GetCategoryAndSubCategory")]
        public async Task<string> GetSubCategoryAndSubCategory()
        {
            var categories = await _context.Categories.ToListAsync();
            var subCategories = await _context.SubCategories.ToListAsync();
            var list = from subCategory in subCategories
                       join category in categories
                       on subCategory.CategoryId equals category.Id
                       select new CategoryAndSubCategoryResponse { SubCategoryID = subCategory.Id, SubCategoryName = subCategory.Name, CategoryName = category.Name };
            return JsonConvert.SerializeObject(list);
        }
    }

}