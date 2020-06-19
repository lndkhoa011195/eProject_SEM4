using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
using eProject.Infastructure.Helpers;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace eProject.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HomeController : ControllerBase
    {
        private readonly ShopDbContext _context;

        public HomeController(ShopDbContext context)
        {
            _context = context;
        }


        [HttpGet("Test")]
        public string Test()
        {
            return "Welcome";
        }
    }
}