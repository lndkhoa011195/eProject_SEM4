using System;
using System.Collections.Generic;
using System.Text;

namespace eProject.DataAccess.Models.Request
{
    public class ChangePassRequest
    {
        public int CustomerId { get; set; }
        public string OldPassword { get; set; }
        public string NewPassword { get; set; }

    }
}
