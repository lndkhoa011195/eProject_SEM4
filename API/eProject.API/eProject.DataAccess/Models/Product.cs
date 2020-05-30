using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Product
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }
        public double OriginalPrice { get; set; }
        public double SellingPrice { get; set; }
        public string Description { get; set; }
        public string MadeIn { get; set; }
        public string ImageURL { get; set; }

        [JsonIgnore]
        public DateTime ModifiedDate { get; set; }
        public bool IsActive { get; set; }

        [ForeignKey("CategoryId")]
        public Category Category { get; set; }
        public int CategoryId { get; set; }

        [ForeignKey("SubCategoryId")]
        public SubCategory SubCategory { get; set; }
        public int SubCategoryId { get; set; }

        [ForeignKey("UnitId")]
        public Unit Unit { get; set; }
        public int UnitId { get; set; }

        [ForeignKey("ManufacturerId")]
        public Manufacturer Manufacturer { get; set; }
        public int ManufacturerId { get; set; }
        [JsonIgnore]
        public ICollection<OrderDetail> OrderDetails { get; set; }
        [JsonIgnore]
        public ICollection<CartDetail> CartDetails { get; set; }
    }
}
