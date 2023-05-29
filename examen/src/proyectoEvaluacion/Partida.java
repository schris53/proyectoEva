package proyectoEvaluacion;

import java.io.FileWriter;
import java.io.IOException;

public class Partida {
    private Jugador[] jugadores;
    private int numRondasTotales;
    public Partida(Jugador[] jugadores, int numRondas){
        this.jugadores = jugadores;
        this.numRondasTotales = numRondas;
        for(int i=0;i<numRondasTotales;i++){
        	
            System.out.println("COMIENZO DE LA RONDA "+(i+1));
            jugarRonda();
            mostrarPuntuaciones();
            
        }
        guardarPartida();
        for(int i=0;i<jugadores.length;i++){
            if(jugadores[i]!=null){
                jugadores[i].sumarPuntosTotales(jugadores[i].getPuntosDePartida());
            }
        }
    }
    public void jugarRonda(){
        for(int i=0;i<4;i++){
            if(jugadores[i]!=null){
                Pregunta pregunta = new Pregunta();
                System.out.println("Responde "+jugadores[i].getNombre()+":");
                System.out.println(pregunta.getEnunciado());
                if(jugadores[i].responderPregunta(pregunta)){
                    System.out.println("Correcto, la has acertado!");
                    jugadores[i].sumarPuntoPartida();
                }else{
                    System.out.println("No, la respuesta correcta era "+pregunta.getRespuesta());
                }
            }
        }
    }
    public void mostrarPuntuaciones(){
        for(int j=0;j<jugadores.length;j++){
            if(jugadores[j]!=null){
                System.out.println(jugadores[j].getNombre()+": "+jugadores[j].getPuntosDePartida()+" puntos.");
            }
        }
    }
    public void guardarPartida(){
        FileWriter f;
        try {
            f = new FileWriter("src/proyectoEvaluacion/historico.txt",true); //true para no sobreescribir (append)
            for(int i=0;i< jugadores.length;i++){
                if(jugadores[i]!=null){
                    f.write(jugadores[i].getNombre()+" "+jugadores[i].getPuntosDePartida()+" ");
                }
            }
                f.write("\n");
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