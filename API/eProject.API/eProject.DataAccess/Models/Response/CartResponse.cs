using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class CartResponse
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public double OriginalPrice { get; set; }
        public double SellingPrice { get; set; }
        public string Description { get; set; }
        public string MadeIn { get; set; }
        public string ImageURL { get; set; }
        public string ManufacturerName { get; set; }
        public string UnitName { get; set; }
        public int Quantity { get; set; }
    }
}
