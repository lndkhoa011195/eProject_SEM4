using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
using eProject.DataAccess.Models.Response;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;

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

        [HttpGet("GetStoreInfo")]
        public async Task<RequestResult> GetStoreInfo()
        {
            var emp = _context.Employees.SingleOrDefault(x => x.Username == "admin");
            if(emp!= null)
            {
                StoreResponse storeResponse = new StoreResponse();
                storeResponse.Name = emp.Name;
                storeResponse.Phone = emp.Phone;
                storeResponse.Email = emp.Email;
                return new RequestResult
                {
                    StatusCode = DataAccess.Models.Enum.StatusCode.Success,
                    Content = JsonConvert.SerializeObject(storeResponse)
                };
            }
            return new RequestResult
            {
                StatusCode = DataAccess.Models.Enum.StatusCode.Failed,
                Content = "Can not find store infomation."
            };
        }

        [HttpGet("Test")]
        public string Test()
        {
            return "Welcome";
        }
    }
}