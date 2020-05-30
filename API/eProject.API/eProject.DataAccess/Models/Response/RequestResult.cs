using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Response
{
    public class RequestResult
    {
        public ErrorCode ErrorCode { get; set; }
        public string Content { get; set; }
    }

    public enum ErrorCode
    {
        Success = 0,
        Failed = 1,
        NotFound = 2,
        Unknown = 3
    }
}
