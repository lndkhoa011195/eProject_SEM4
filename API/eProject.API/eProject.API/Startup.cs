using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using eProject.API.Options;
using eProject.DataAccess.EF;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using Microsoft.OpenApi.Models;


namespace eProject.API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllers();
            string conn = Configuration.GetConnectionString("DefaultConnection");
            services.AddDbContext<ShopDbContext>(op => op.UseSqlServer(conn),ServiceLifetime.Scoped, ServiceLifetime.Singleton);
            #region Swagger

            services.AddSwaggerGen(x =>
            {
                x.SwaggerDoc("v1", new OpenApiInfo
                {
                    Title = "eProject API",
                    Version = "v1",
                    Description = "eProject API for Android app",
                    Contact = new OpenApiContact
                    {
                        Name = "Lê Ngọc Đăng Khoa",
                        Email = "lndkhoa95@gmail.com"
                    }
                });
                // Set the comments path for the Swagger JSON and UI.
                string xmlFile = $"{System.Reflection.Assembly.GetExecutingAssembly().GetName().Name}.xml";
                string xmlPath = System.IO.Path.Combine(AppContext.BaseDirectory, xmlFile);
                x.IncludeXmlComments(xmlPath);
            });

            #endregion Swagger
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });

            #region Swagger

            SwaggerOptions swaggerOptions = new SwaggerOptions();
            Configuration.GetSection(nameof(SwaggerOptions)).Bind(swaggerOptions);
            app.UseSwagger(opt =>
            {
                opt.RouteTemplate = swaggerOptions.JsonRoute;
            });
            app.UseSwaggerUI(opt =>
            {
                opt.SwaggerEndpoint(swaggerOptions.UiEndpoint, swaggerOptions.Description);
            });

            #endregion Swagger
        }
    }
}
