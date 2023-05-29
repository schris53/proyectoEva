package proyectoEvaluacion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Lista_Jugadores lista = new Lista_Jugadores();//CREO EL OBJETO LISTA 
        Jugador[] jugadores = new Jugador[4];
        Scanner s = new Scanner(System.in);
        inicializarListaJugadores(lista);
        int opcion;
        do{
            opcion = menu(s,jugadores,lista);
        }while(opcion!=5);////////////////////SI ES 5 O MAS , TERMINA EL PROGRAMA.
        System.out.println("PROGRAMA FINALIZADO");
    }
    //ELEJIMOS UNA OPCION!
    public static int menu(Scanner s,Jugador[] jugadores,Lista_Jugadores lista){
        System.out.println("SELECCIONA UNA OPCIÓN:");
        System.out.println("1. Jugar partida");
        System.out.println("2. Ranking");
        System.out.println("3. Histórico");
        System.out.println("4. Jugadores");
        System.out.println("5. Salir");
        //PINTAMOS EL MENU
        int opcion;
        do{
            opcion = s.nextInt();
            if(opcion<1||opcion>5){
                System.out.println("Número incorrecto. Introduce otro valor:");// si es menor que cero y mayor que cinco.
            }
        }while(opcion<1||opcion>5);
        switch (opcion){
            case 1: jugarPartida(s,jugadores,lista); break;//METODO DE jugarPartida-- terminar de implementar
            case 2: ranking(lista); break;
            case 3: historico(); break;
            case 4: jugadores(s,lista); break;
            case 5:
        }
        return opcion;
    }
    //METODO JUGAR PARTIDA.
    public static void jugarPartida(Scanner s, Jugador[] jugadores,Lista_Jugadores lista){
        //METODO jugarPartida-- paso scanner para no inicializar, paso array, objeto listajugadores
    	for(int i=0;i< jugadores.length;i++){
            jugadores[i] = null;
        }
        int numRondas, numJugadoresHumanos,numJugadoresCpu=0;
        do{
            System.out.println("¿Cuántos jugadores humanos? (1-4)");
            numJugadoresHumanos = s.nextInt();
            if(numJugadoresHumanos<1||numJugadoresHumanos>4){
                System.out.println("Valor incorrecto. Introduce otro valor");
            }
        }while(numJugadoresHumanos<1||numJugadoresHumanos>4);

        if(numJugadoresHumanos!=4){
            do{
                System.out.println("¿Cuántos jugadores CPU? (0-"+(4-numJugadoresHumanos)+")");
                numJugadoresCpu = s.nextInt();
                if(numJugadoresCpu<0||numJugadoresCpu>(4-numJugadoresHumanos)){
                    System.out.println("Valor incorrecto. Introduce otro valor");
                }
            }while(numJugadoresCpu<0||numJugadoresCpu>(4-numJugadoresHumanos));
        }
        for(int i=0;i<numJugadoresCpu;i++){
            String nombre = "CPU"+(i+1);
            jugadores[i] = new Jugador(nombre,true);
        }
        s.nextLine();
        for(int i=numJugadoresCpu;i<(numJugadoresHumanos+numJugadoresCpu);i++){
            String nombre;
            do{
                System.out.println("Intoduce un nombre para el siguiente jugador:");
                nombre = s.nextLine();
                if(contieneEspacios(nombre)) System.out.println("El nombre no puede contener espacios.");
            }while(contieneEspacios(nombre));
            Jugador jugador = lista.getJugador(nombre);
            if(jugador==null){
                jugador = new Jugador(nombre,false);
            }
            jugadores[i] = jugador;
            lista.añadirJugador(jugador);
        }
        //pinto menu para elegir las rondas.
        System.out.println("¿Cuántas rondas?\n" +
                  "Partida rápida (3)\n" +
                  "Partida corta (5)\n" +
                  "Partida normal (10)\n" +
                  "Partida larga (20)");
        do{
            numRondas = s.nextInt();
            //si el numero de rondas es distintode 3,5,10,20
            if(numRondas!=3&&numRondas!=5&&numRondas!=10&&numRondas!=20){
                System.out.println("Número de rondas incorrecto. Introduce otro valor:");
            }
        }while(numRondas!=3&&numRondas!=5&&numRondas!=10&&numRondas!=20);
        for(int i=0;i<4;i++){
            if(jugadores[i]!=null){
                jugadores[i].resetearPuntosPartida();
            }
        }
        Partida partida= new Partida(jugadores,numRondas);
    }
    //ranking muestra una lista actual de como va el ranking, sin tener en cuenta un historico de todos los jugadores.
    public static void ranking(Lista_Jugadores lista){
        ArrayList<Jugador> jugadoresEnOrden = lista.ranking();
        for(int i=0;i<jugadoresEnOrden.size();i++){
            Jugador actual = jugadoresEnOrden.get(i);
            if(!actual.esCpu() && !estaEliminado(actual.getNombre())){
                System.out.println(actual.getNombre()+" "+actual.getPuntosTotales());
            }
        }
    }
    public static void historico(){
        FileReader f;
        try {
            f = new FileReader("src/proyectoEvaluacion/historico.txt");// AQUI CORREGIR QUE FALLA, PENDIENTE
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            System.out.println(s.nextLine());
        }
        s.close();
    }
    public static void jugadores(Scanner s, Lista_Jugadores lista){
        subMenu(s,lista);
    }
    public static void inicializarListaJugadores(Lista_Jugadores lista){
        FileReader f;
        try {
            f = new FileReader("src/proyectoEvaluacion/eliminados.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            String linea = s.nextLine();
            String[] arr = linea.split(" ");
            for(int i=0;i<arr.length;i++){
                String nombre = arr[i];
                i++;
                int puntos = Integer.parseInt(arr[i]);
                lista.sumarPuntos(nombre,puntos);
            }
        }
        s.close();
    }
    //metodo subMenu que vemos el menu jugadores.
    public static void subMenu(Scanner s, Lista_Jugadores lista){
        System.out.println("SELECCIONA UNA OPCIÓN:");
        System.out.println("1. Ver jugadores");
        System.out.println("2. Añadir jugador");
        System.out.println("3. Eliminar jugador");
        System.out.println("4. Volver al menú principal");
        int opcion;
        do{
            opcion = s.nextInt();
            if(opcion<1||opcion>4){
                System.out.println("Número incorrecto. Introduce otro valor:");
            }
        }while(opcion<1||opcion>4);
        s.nextLine();
        switch (opcion){
            case 1:
                lista.mostrar();
                break;
            case 2:
                String nombre;
                do{
                    System.out.println("Intoduce un nombre para el jugador:");
                    nombre = s.nextLine();
                    if(contieneEspacios(nombre)) System.out.println("El nombre no puede contener espacios.");
                }while(contieneEspacios(nombre));
                lista.añadirJugador(nombre);
                break;
            case 3:
                System.out.println("Introduce un nombre del jugador a eliminar:");
                String nombreE = s.nextLine();
                lista.eliminarJugador(nombreE);
                break;
        }
    }
    //METODO PARA CONTROLAR LOS ESPACIOS.
    public static boolean contieneEspacios(String palabra){
        boolean resultado = false;
        for(int i=0;i<palabra.length();i++){
            if(palabra.charAt(i)==(' ')) resultado = true;
        }
        return resultado;
    }
    //METODO PARA CONFIRMAR SI ESTA ELIMINADO.
    public static boolean estaEliminado(String nombre){
        boolean resultado = false;
        FileReader f;
        try {
            f = new FileReader("src/proyectoEvaluacion/eliminados.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            if(s.nextLine().equals(nombre)) resultado = true;
        }
        s.close();
        return resultado;
    }
}