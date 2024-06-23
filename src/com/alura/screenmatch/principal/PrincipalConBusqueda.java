package com.alura.screenmatch.principal;

import com.alura.screenmatch.excepcion.ErrorEnConversionDeDuracionException;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);

        List<Titulo> titulos = new ArrayList<>();
        //se coloca fuera del titulo
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting() // para ver mejor la lista.
                .create();

        while(true){
            System.out.println("Escriba el nombre de una pelicula: ");
            var busqueda = lectura.nextLine();

            if(busqueda.equalsIgnoreCase("salir")){
                break;
            }

            String direccion = "https://www.omdbapi.com/?t="+
                    busqueda.replace(" ", "+") +
                    "&apikey=d4d0bf92";

            try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);
//la biblioteca json convierte el json en un objeto
                //Gson gson = new Gson(); ESTA LINEA SOLO CONSULTA EL Gson pero no le cambia el tamaño de la letra
                //lo que ara el nuevo gson es cambiar el tamaño de la letra
              /**
               * Esta parte es para que el gson cre la estructura al final y no al principio y se sacara fuera de try
               * Gson gson = new GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                        .create();**/
                TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(miTituloOmdb);
                //se crea de titulo omdb a titulo
                // Titulo miTitulo = new Titulo(miTituloOmdb); esta linea se comenta por la parte de que se crea un nuevo objeto para un try catch
                // System.out.println(miTitulo);
// este try catch es para la esception del objeto titulo.
                // try {
                Titulo miTitulo = new Titulo(miTituloOmdb);
                System.out.println("Titulo ya convertido" + miTitulo);
// se crea un archivo de texto con el nombre de la pelicula

                titulos.add(miTitulo);
            }catch (NumberFormatException e){
                System.out.println("Ocurrió un error: ");
                System.out.println(e.getMessage());
            }catch(IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la dirección.");
            }catch (ErrorEnConversionDeDuracionException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println(titulos);
// guarda los archivos en un json con el nombre de titulos con la clase FileWariter
        FileWriter escritura = new FileWriter("titulos.json");
        escritura.write(gson.toJson(titulos));
        escritura.close();
        System.out.println("Finalizó la ejecución del programa!");
    }
}

