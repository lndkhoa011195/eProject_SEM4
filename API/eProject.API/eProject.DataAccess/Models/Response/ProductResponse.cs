using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class ProductResponse
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string ManufacturerName { get; set; }
        public double Price { get; set; }
        public string ImageURL { get; set; }
    }
}
