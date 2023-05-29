package proyectoEvaluacion;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lista_Jugadores {
    private ArrayList<Jugador> listaDeJugadores;
    
    public Lista_Jugadores(){
        listaDeJugadores = new ArrayList<>();
    }
    public void añadirJugador(String nombre){
        if(getJugador(nombre)==null){
            Jugador jugador;
            if(nombre.substring(0,3).equals("CPU")){
                jugador = new Jugador(nombre,true);
            }else{
                jugador = new Jugador(nombre,false);
            }
            listaDeJugadores.add(jugador);
        }else System.out.println("Este jugador ya está en la lista.");
    }
    public void añadirJugador(Jugador jugador){
        if(getJugador(jugador.getNombre())==null){
            listaDeJugadores.add(jugador);
        }
    }
    public void sumarPuntos(String nombre,int puntos){
        if(getJugador(nombre)==null){
            añadirJugador(nombre);
        }
        getJugador(nombre).sumarPuntosTotales(puntos);
    }
    
    //Clase Lista_Jugadores, función eliminarJugador
    //se genera un fichero con los nombres de los jugadores eliminados y a 
    //la hora de mostrar el ranking, los nombres que aparezcan en ese fichero no se muestran
    public void eliminarJugador(String nombre){
        Jugador aEliminar = getJugador(nombre);
        if(aEliminar!=null){
            listaDeJugadores.remove(aEliminar);
            añadirAFicheroDeEliminados(nombre);
        }else System.out.println("No se ha encontrado al jugador.");
    }
    public Jugador getJugador(String nombre){
        Jugador resultado = null;
        for(int i=0;i<listaDeJugadores.size();i++){
            if(listaDeJugadores.get(i).getNombre().equals(nombre)){
                resultado = listaDeJugadores.get(i);
            }
        }
        return resultado;
    }
    public ArrayList<Jugador> ranking(){
        ArrayList<Jugador> copia = new ArrayList<>();
        for(int i=0;i<listaDeJugadores.size();i++){
            copia.add(listaDeJugadores.get(i));
        }
        ArrayList<Jugador> resultado = new ArrayList<>();
        while(copia.size()>0){
            Jugador aEliminar = copia.get(0);
            for(int i=0;i<copia.size();i++){ //Busca al jugador de copia con más puntos y se guarda en aEliminar
                if(copia.get(i).getPuntosTotales()>aEliminar.getPuntosTotales()){
                    aEliminar = copia.get(i);
                }
            }
            resultado.add(aEliminar);
            copia.remove(aEliminar);
        }
        return resultado;
    }
    public void mostrar(){
        for(int i=0;i<listaDeJugadores.size();i++){
            Jugador actual = listaDeJugadores.get(i);
            System.out.println(actual.getNombre());
        }
    }
    public void añadirAFicheroDeEliminados(String nombre){
        FileWriter f;
        try {
            f = new FileWriter("src/proyectoEvaluacion/eliminados.txt",true);
            f.write(nombre+"\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}