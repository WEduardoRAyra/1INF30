﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Publicaciones_v02
{
    public class Tesis : Publicacion
    {
        private string clase;
        private string departamento;
        private string institucion;
        private string ciudad;
        private string estado;

        public string Clase { get => clase; set => clase = value; }
        public string Departamento { get => departamento; set => departamento = value; }
        public string Institucion { get => institucion; set => institucion = value; }
        public string Ciudad { get => ciudad; set => ciudad = value; }
        public string Estado { get => estado; set => estado = value; }

        public Tesis(string nombre, string autor, int año, string clase, string departamento, string institucion, string ciudad, string estado) : base(nombre, autor, año)
        {
            this.Clase = clase;
            this.Departamento = departamento;
            this.Institucion = institucion;
            this.Ciudad = ciudad;
            this.Estado = estado;
        }

        public Tesis(string nombre, string[] autores, int año, string clase, string departamento, string institucion, string ciudad, string estado) : base(nombre, autores, año)
        {
            this.Clase = clase;
            this.Departamento = departamento;
            this.Institucion = institucion;
            this.Ciudad = ciudad;
            this.Estado = estado;
        }

        public override string ToString()
        {
            string referencia = this.obtenAutoresParaReferenciar();
            referencia += ", ";
            referencia += this.Nombre;
            referencia += ", ";
            referencia += this.Clase;
            referencia += ", ";
            referencia += this.Departamento;
            referencia += ", ";
            referencia += this.Institucion;
            referencia += ", ";
            referencia += this.Ciudad;
            referencia += ", ";
            referencia += this.Estado;
            referencia += ", ";
            referencia += this.Año;
            referencia += ".";
            return referencia;
        }
    }
}
