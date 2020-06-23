using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Request
{
    public class CheckOutRequest
    {
        public int CustomerId { get; set; }
        public string Name { get; set; }
        public string Phone { get; set; }
        public string ShippingAddress { get; set; }
        public string Note { get; set; }
    }
}
