using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Request
{
    public class SignUpRequest
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public string Password { get; set; }
        public string Address { get; set; }
    }
}
