using eProject.DataAccess.Models.Enum;
using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class RequestResult
    {
        public StatusCode StatusCode { get; set; }
        public string Content { get; set; }
    }


}
