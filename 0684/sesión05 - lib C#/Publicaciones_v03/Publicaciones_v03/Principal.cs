﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Publicaciones;
using Publicaciones.EstandardAPA;
using Publicaciones.EstandardIEEE;

namespace Publicaciones_v02
{
    public class Principal
    {
        static void Main(string[] args)
        {
            AbstractFactoryPublicacion factory = new ConcreteFactoryAPA();   
            Publicacion[] publicaciones = new Publicacion[3];
            publicaciones[0] = factory.crearArticulo("A relational model of data for large shared data banks", "Codd, Edgar Frank", 1970, "Communications of the ACM", 13, 6, "junio");

            string[] autores = { "Gamma, Erich", "Helm, Richard", "Johnson, Ralph", "Vlissides, John" };
            publicaciones[1] = factory.crearLibro("Design Patterns: Elements of Reusable Object-Oriented Software", autores, 1970, "1er edición", "Massachusetts", "Addison-Wesley Professional");

            publicaciones[2] = factory.crearTesis("A system of logic based on ordinals", "Turing, Alan Mathison", 1938, Clase_de_Tesis.DOCTORADO, "Departamento de Matemáticas", "Universidad de Princeton", "Princeton", "NJ");

            for (int i = 0; i < publicaciones.Length; i++)
            {
                publicaciones[i].Referenciar();
                Publicacion.orden_presentacion++;
            }
            Console.ReadLine();
        }
    }
}
