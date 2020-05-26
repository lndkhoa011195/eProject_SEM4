using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json;

namespace eProject.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SubCategoryController : ControllerBase
    {
        private readonly ShopDbContext _context;

        public SubCategoryController(ShopDbContext context)
        {
            _context = context;
        }


        [HttpGet("GetSubCategories")]
        public async Task<string> GetCategories()
        {
            var list = await _context.SubCategories.ToListAsync();
            return JsonConvert.SerializeObject(list);
        }


    }
}