using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class OrderDetailResponse
    {
        public string ShipName { get; set; }
        public string ShipPhone { get; set; }
        public string ShipAddress { get; set; }
        public string ShipNote { get; set; }
        public List<CartResponse> Details { get; set; }
    }
}
