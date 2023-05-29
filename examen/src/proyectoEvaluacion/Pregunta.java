package proyectoEvaluacion;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;

public class Pregunta {
    enum TIPO_DE_PREGUNTA{MATES,LETRAS,INGLES}         //Un enumeradoTIPO_DE_PREGUNTAes elMATES,LETRASmiINGLESson los valores constantes
    private TIPO_DE_PREGUNTA tipo;
    private String enunciado, respuesta;

    public Pregunta(){
        int opcion = (int)(Math.random()*3); //devuelve un número entre el 0 y el 2
        switch (opcion){
            case 0:
                tipo = TIPO_DE_PREGUNTA.MATES;
                preguntaDeMates();
                break;
            case 1:
                tipo = TIPO_DE_PREGUNTA.LETRAS;
                preguntaDeLetras();
                break;
            case 2:
                tipo = TIPO_DE_PREGUNTA.INGLES;
                preguntaDeIngles();
        }
    }
    //codigo de como hacer esta parte buscado por internet
    //la clase que nos facilitaban , no encontre la libreria.
    public void preguntaDeMates(){
        enunciado = generarExpresion();
        respuesta = evaluarExpresion(enunciado);
    }

    public void preguntaDeLetras(){
        FileReader f;
        try {
            f = new FileReader("src/proyectoEvaluacion/diccionario.txt");//lee la pregunta de letras
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(f);
        int contadorLineas = 0;
        while(s.hasNext()){
            contadorLineas++;
            s.nextLine();
        }
        int lineaSeleccionada = (int)(Math.random()*contadorLineas);
        String palabraSeleccionada="";
        s.close();
        try {
            f = new FileReader("src/proyectoEvaluacion/diccionario.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        s = new Scanner(f);
        for(int i=0;i<lineaSeleccionada;i++){
            palabraSeleccionada = s.nextLine();
        }
        s.close();
        enunciado = tapaPalabra(palabraSeleccionada);
        respuesta = palabraSeleccionada;
    }

    public void preguntaDeIngles(){
        FileReader f;
        try {
            f= new FileReader("src/proyectoEvaluacion/ingles.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Scanner s = new Scanner(f);
        int contadorLineas = 0;
        while(s.hasNext()){
            contadorLineas++;
            s.nextLine();
        }
        //SIEMPRE SON MULTIPLOS DE 5, EN CASO DE CAMBIAR EL FICHERO , SOLO CAMBIAMOS LOS MULTIPLOS DE CUANDO SE GENERA LA PREGUNNTA

        int numPreguntas = contadorLineas/5;
        int preguntaSeleccionada = (int)(Math.random()*numPreguntas);
        s.close();
        try {
            f = new FileReader("src/proyectoEvaluacion/ingles.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        s = new Scanner(f);
        for(int i=0;i<(preguntaSeleccionada-1)*5;i++){
            s.nextLine();
        }
        enunciado = "";
        String[] arr = new String[4];
        for(int i=0;i<5;i++){
            String linea = s.nextLine();
            if(i==0){
                enunciado = linea + "\n";
            }
            if(i!=0){
                arr[i-1] = linea;
            }
            if(i==1){
                respuesta = linea;
            }
        }
        enunciado = enunciado + mezclaRespuestas(arr);
        s.close();
    }

    public static String mezclaRespuestas(String[] arr){
        String resultado = "";
        boolean [] respuestasImpresas = new boolean[4];
        int impresas = 0;
        while(impresas<4){
            int aleatorio = (int)(Math.random()*4);
            if(!respuestasImpresas[aleatorio]){
                resultado = resultado+ arr[aleatorio]+ "\n";
                impresas++;
                respuestasImpresas[aleatorio] = true;
            }
        }
        return resultado;
    }
//sustituir la palabra por (*)
    public static String tapaPalabra(String palabra){
        String pal = palabra;
        int numLetras = palabra.length();
        int numLetrasTapadas=0;
        while(numLetrasTapadas<(numLetras/3)){
            int posicionATapar = (int)(Math.random()*numLetras);
            if(pal.charAt(posicionATapar)!='*'){
                pal = pal.substring(0,posicionATapar)+'*'+pal.substring(posicionATapar+1); //substring toma una parte del string, el segundo parametro no está incluido.
                numLetrasTapadas++;
            }
        }
        return pal;
    }
    
    //metodo de mates buscado por internet, con prioridad de multiplicacion
    public static String generarExpresion() {
        Random random = new Random();
        int cantidadOperandos = random.nextInt(5) + 4; // Genera un número aleatorio entre 4 y 8
        StringBuilder expresion = new StringBuilder();

        for (int i = 0; i < cantidadOperandos; i++) {
            int operando = random.nextInt(11) + 2; // Genera un número aleatorio entre 2 y 12

            if (i == 0) {
                expresion.append(operando);
            } else {
                int operador = random.nextInt(3); // Genera un número aleatorio entre 0 y 2
                switch (operador) {
                    case 0:
                        expresion.append(" + ");
                        break;
                    case 1:
                        expresion.append(" - ");
                        break;
                    case 2:
                        expresion.append(" * ");
                        break;
                }
                expresion.append(operando);
            }
        }

        return expresion.toString();
    }

    public static String evaluarExpresion(String expresion) {
        String[] tokens = expresion.split(" ");

        int resultado = 0;
        int operando = 0;
        String operador = "+";

        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*")) {
                operador = token;
            } else {
                int numero = Integer.parseInt(token);
                switch (operador) {
                    case "+":
                        resultado += operando;
                        operando = numero;
                        break;
                    case "-":
                        resultado += operando;
                        operando = -numero;
                        break;
                    case "*":
                        operando *= numero;
                        break;
                }
            }
        }

        resultado += operando;
        return String.valueOf(resultado);
    }

    public String getEnunciado(){
        return enunciado;
    }
    public String getRespuesta(){
        return respuesta;
    }
    public TIPO_DE_PREGUNTA getTipo(){ //getter del tipo de pregunta (devuelve un tipo de dato enum)
        return tipo;
    }
}