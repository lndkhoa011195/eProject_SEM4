using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Customer
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public string Password { get; set; }
        public string Address { get; set; }
        [JsonIgnore]
        public DateTime ModifiedDate { get; set; }
        [JsonIgnore]
        public int LoginAttemptCount { get; set; }
        [JsonIgnore]
        public bool IsActive { get; set; }  
        [JsonIgnore]
        public ICollection<Order> Orders { get; set; }
        [JsonIgnore]
        public ICollection<Cart> Carts { get; set; }
    }
}
