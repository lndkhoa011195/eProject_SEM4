using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Manufacturer
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public string Email { get; set; }
        public string Phone { get; set; }
        public DateTime ModifiedDate { get; set; }
        [JsonIgnore]
        public ICollection<Product> Products { get; set; }

    }
}
