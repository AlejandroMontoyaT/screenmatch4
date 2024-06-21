package com.alura.screenmatch.modelos;

import com.alura.screenmatch.excepcion.ErrorEnConversionDeDuracionException;
import com.google.gson.annotations.SerializedName;

public class Titulo implements Comparable<Titulo>{
    //se ocupan anotaciones para que funcione el json para que sepa que se refiere a un atributo
    @SerializedName("Title")
    private String nombre;
    @SerializedName("Year")
    private int fechaDeLanzamiento;
    private boolean incluidoEnElPlan;
    private double sumaDeLasEvaluaciones;
    private int totalDeEvaluaciones;
  //  @SerializedName("Runtime")
    private int duracionEnMinutos;

    public Titulo(String nombre, int fechaDeLanzamiento) {
        this.nombre = nombre;
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }
//esta estructura de codigo viene de principal para la configuracion del json
public Titulo(TituloOmdb miTituloOmdb) {
    this.nombre = miTituloOmdb.title();
    this.fechaDeLanzamiento = Integer.valueOf(miTituloOmdb.year());
        if (miTituloOmdb.runtime().contains("N/A")) {
        //se crea exception personalizado en este caso es ErrorEnConversionDeDuracionException
        throw new ErrorEnConversionDeDuracionException("No puede convertir la duracion de pelicula por que tiene un N/A");
    }
    this.duracionEnMinutos = Integer.valueOf(
            //                                    se crea para que muestre la duracion de la pelicula con sifras de 2 digitos
            miTituloOmdb.runtime().substring(0,3).replace(" ",""  )
    );
}

    public String getNombre() {
        return nombre;
    }

    public int getFechaDeLanzamiento() {
        return fechaDeLanzamiento;
    }

    public boolean isIncluidoEnElPlan() {
        return incluidoEnElPlan;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public int getTotalDeEvaluaciones() {
        return totalDeEvaluaciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaDeLanzamiento(int fechaDeLanzamiento) {
        this.fechaDeLanzamiento = fechaDeLanzamiento;
    }

    public void setIncluidoEnElPlan(boolean incluidoEnElPlan) {
        this.incluidoEnElPlan = incluidoEnElPlan;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public void muestraFichaTecnica(){
        System.out.println("Nombre de la película: " + nombre);
        System.out.println("Año de lanzamiento: " + fechaDeLanzamiento);
    }

    public void evalua(double nota){
        sumaDeLasEvaluaciones += nota;
        totalDeEvaluaciones++;
    }

    public double calculaMediaEvaluaciones(){
        return sumaDeLasEvaluaciones / totalDeEvaluaciones;
    }

    @Override
    public int compareTo(Titulo otroTitulo) {
        return this.getNombre().compareTo(otroTitulo.getNombre());
    }
//esta liea es para que se muestre el nombre y la fecha de lanzamiento del json
@Override
public String toString() {
    return "nombre='" + nombre + '\'' +
            ", fechaDeLanzamiento=" + fechaDeLanzamiento+
            ", duracion="+duracionEnMinutos;
}
}

