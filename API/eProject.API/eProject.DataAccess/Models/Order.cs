using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Order
    {
        public int Id { get; set; }
        public string OrderCode { get; set; }
        public string ShippingAddress { get; set; }
        public DateTime OrderDate { get; set; }
        public string Note { get; set; }
        public int Status { get; set; }
        [ForeignKey("CustomerId")]
        public Customer Customer { get; set; }
        public int CustomerId { get; set; }
        [JsonIgnore]
        public ICollection<OrderDetail> OrderDetails { get; set; }
    }
}
