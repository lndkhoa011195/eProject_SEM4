using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class OrderResponse
    {
        public int Id { get; set; }
        public string OrderCode { get; set; }
        public string OrderDate { get; set; }
        public int Status { get; set; }
        public double OriginalSum { get; set; }
        public double SellingSum { get; set; }
        public int ProductCount { get; set; }
    }
}
