using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class CategoryAndSubCategoryResponse
    {
        public int SubCategoryID { get; set; }
        public string SubCategoryName { get; set; }
        public string CategoryName { get; set; }
    }
}
