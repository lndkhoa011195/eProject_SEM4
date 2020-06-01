using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Request
{
    public class CheckOutRequest
    {
        public int CustomerId { get; set; }
        public string ShippingAddress { get; set; }
        public string Note { get; set; }
        public List<CheckOutCart> CartProducts { get; set; }
    }

    public class CheckOutCart
    {
        public int ProductId { get; set; }
        public int Quantity { get; set; }
    }

}
