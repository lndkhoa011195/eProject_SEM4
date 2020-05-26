using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Employee
    {
        public int Id { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public int Status { get; set; }
        public DateTime ModifiedDate { get; set; }
    }
}
