package proyectoEvaluacion;

import java.util.Scanner;

public class Jugador {
    private String nombre;
    private int puntosDePartida, puntosTotales;
    private boolean cpu;
    public Jugador(String nombre,boolean cpu){
        this.nombre = nombre;
        puntosDePartida = puntosTotales = 0;
        this.cpu = cpu;
    }
    public String getNombre(){
        return nombre;
    }
    //METODO RESPONDER PREGUNTA
    //COMO TENGO UN ATRIBUTO BOOLEAN DE "CPU" , DIRECTAMENTE, SI ES CPU, LE SUMO UN PUNTO EN MATES
    // SI ES DE LENGUA LA FALLO SIEMPRE, Y SI ES INGLES UTILIZO LA CLASE Math.random, entre las 4 opciones que hay.
    public boolean responderPregunta(Pregunta pregunta){
        Scanner s = new Scanner(System.in);
        if(cpu){
            if(pregunta.getTipo()==Pregunta.TIPO_DE_PREGUNTA.MATES)
                return true;
            else if(pregunta.getTipo()==Pregunta.TIPO_DE_PREGUNTA.LETRAS)
                return false;
            else{
                if((int)(Math.random()*4)==1) return true;
                else return false;
            }
        }
        else{
        	//AQUI COMPARO RESPUESTA HUMANO.
            if(pregunta.getRespuesta().equals(s.nextLine())) return true;
            else return false;
        }
    }
    public void sumarPuntoPartida(){
        puntosDePartida++;
    }
    public void resetearPuntosPartida(){
        puntosDePartida = 0;
    }
    public void sumarPuntosTotales(int puntos){
        puntosTotales+=puntos;
    }
    public int getPuntosDePartida(){
        return puntosDePartida;
    }
    public int getPuntosTotales(){
        return puntosTotales;
    }
    public boolean esCpu(){
        return cpu;
    }
}