using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;
using eProject.DataAccess.EF;
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

        [HttpPost("Authenticate")]
        public async Task<string> Authenticate(CustomerLoginRequest loginRequest)
        {
            var cus = _context.Customers.FirstOrDefault(x => x.Email == loginRequest.Email && x.Password == loginRequest.Password);
            CustomerLoginResponse response = new CustomerLoginResponse();
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

        private bool CheckLogin(CustomerLoginRequest loginRequest)
        {
            bool check = false;

            return check;
        }
    }
}