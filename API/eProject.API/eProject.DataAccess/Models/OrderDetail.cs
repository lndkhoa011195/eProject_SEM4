using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class OrderDetail
    {
        public int Id { get; set; }

        [ForeignKey("OrderId")]
        public Order Order { get; set; }
        public int OrderId { get; set; }

        [ForeignKey("ProductId")]
        public Product Product { get; set; }
        public int ProductId { get; set; }
        public string ProductName { get; set; }
        public double OriginalPrice { get; set; }
        public double SellingPrice { get; set; }
        public string Description { get; set; }
        public string UnitName { get; set; }
        public string ManufacturerName { get; set; }
        public string MadeIn { get; set; }
        public string ImageURL { get; set; }
        public int Quantity { get; set; }
    }
}
