using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text;

namespace eProject.DataAccess.Models
{
    public class Promotion
    {
        [Key]
        public int Id { get; set; }

        [JsonIgnore]
        [ForeignKey("ProductId")]
        public Product Product { get; set; }
        public int ProductId { get; set; }
        public string ImageURL { get; set; }

        [JsonIgnore]
        public DateTime ModifiedDate { get; set; }
    }
}
