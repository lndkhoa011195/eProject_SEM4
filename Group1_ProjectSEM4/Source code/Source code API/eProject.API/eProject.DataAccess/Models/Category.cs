using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Text;


namespace eProject.DataAccess.Models
{
    public class Category
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public DateTime ModifiedDate { get; set; }

        [JsonIgnore]
        public ICollection<SubCategory> SubCategories { get; set; }
        [JsonIgnore]
        public ICollection<Product> Products { get; set; }

    }
}
