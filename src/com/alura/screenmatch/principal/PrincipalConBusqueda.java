package com.alura.screenmatch.principal;

import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class PrincipalConBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el nombre de una pelicula: ");
        var busqueda = lectura.nextLine();

        String direccion = "https://www.omdbapi.com/?t=" + busqueda + "&apikey=d4d0bf92";

        try {
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
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(miTituloOmdb);
            //se crea de titulo omdb a titulo
            // Titulo miTitulo = new Titulo(miTituloOmdb); esta linea se comenta por la parte de que se crea un nuevo objeto para un try catch
            // System.out.println(miTitulo);
// este try catch es para la esception del objeto titulo.
            // try {
            Titulo miTitulo = new Titulo(miTituloOmdb);
            System.out.println("Titulo ya comvertido" + miTitulo);
        } catch (NumberFormatException e) {
            System.out.println("No se pudo crear el objeto ocurrio un error");
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo crear el objeto ocurrio un error verifica la direccion de la api");
        } catch (Exception e) {
            System.out.println("Ocurrio un error inesperado");
        }
        System.out.println("Fin del programa");
    }
}

