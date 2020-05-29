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


        //List<Category> categories = new List<Category>()
        //{
        //    new Category(){Id = 1, Name = "Baby Care", CreatedDate= Convert.ToDateTime("2018-03-22 17:22:12")},
        //    new Category(){Id = 2, Name = "Personal Care", CreatedDate= Convert.ToDateTime("2018-03-22 17:22:21")},
        //    new Category(){Id = 3, Name = "Grocery", CreatedDate= Convert.ToDateTime("2018-03-22 17:27:23")},
        //    new Category(){Id = 4, Name = "Household", CreatedDate= Convert.ToDateTime("2019-05-06 19:00:00")},
        //    new Category(){Id = 5, Name = "Women", CreatedDate= Convert.ToDateTime("2019-05-06 19:00:18")},
        //    new Category(){Id = 6, Name = "Men", CreatedDate= Convert.ToDateTime("2019-05-06 19:00:18")},
        //};

        //[HttpGet("GetCategories")]
        //public async Task<RequestResponse> GetCategories()
        //{
        //    return new RequestResponse
        //    {
        //        ErrorCode = ErrorCode.Success,
        //        Content = JsonConvert.SerializeObject(categories)
        //    };
        //}


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