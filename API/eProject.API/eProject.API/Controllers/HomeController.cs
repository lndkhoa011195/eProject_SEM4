using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.Infastructure.Helpers;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace eProject.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class HomeController : ControllerBase
    {
        [HttpGet]
        public string Test()
        {
            string key = "khoa";
            var pass = "123456";
            var encrypted = CrossPlatformSecurity.Encrypt(pass, key);
            pass = "123";
            encrypted = CrossPlatformSecurity.Encrypt(pass, key);
            pass = "khoa";
            encrypted = CrossPlatformSecurity.Encrypt(pass, key);
            pass = "huynh";
            encrypted = CrossPlatformSecurity.Encrypt(pass, key);
            return "Welcome";
        }
    }
}