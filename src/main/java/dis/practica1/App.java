package dis.practica1;

import com.google.gson.*;
import org.json.XML;


import javax.xml.stream.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;


public class App {

    //declaramos las diferentes variables
    public static FileOutputStream fileOutputStream = null;
    public static XMLStreamWriter xmlStreamWriter = null;
    public static File fichero = new File("XML_superheroes.xml");
    public static XMLOutputFactory xmlOutputFactory = null;
    public static InputStream inputStream = null;
    public static XMLStreamReader xmlStreamReader = null;
    public static XMLInputFactory xmlInputFactory = null;

    public static void main(String[] args) throws IOException, XMLStreamException {
        //llamamos a la funcion de lectura para mostrar los resultados

        //Antes comprobamos si el archivo json existe
        File archivo = new File("Superhereoes_JSON.json");
        if(!archivo.exists()) { // si no existe
            System.out.println("El archivo indicado no existe");
        }else {
            System.out.println("\nDispone del json PERFECTO");
        }

        int op;
        boolean salir = false; //inicializamos salir como false, para que no salga del menu

        //System.out.println("INFORMACION BUSCADA EN EL JSON\n\n");
        Scanner scanner = new Scanner(System. in);
        while(salir==false){
            System.out.println("         MENU IPCIONES INICIAL     ");
            System.out.println("\n 1- QUIERE AÑADIR MAS SUPERHEROES ");
            System.out.println("\n 2- QUIERE LEER CONTENIDO DEL JSON");
            System.out.println("\n 3  SALIR");
            op = scanner.nextInt();
            switch (op){

                case 1:

                    AñadirSuperheroes(); //llamamos a la funcion de añadir
                    ConvertiraJson(); //necesitamos llamar a esta funcion para que luego lo podamos leer bien
                    break;

                case 2:
                    lecturajson(); //llamamos a a funcion de leer el json

                    break;
                case 3:
                    System.out.println("Ha salido correctamente");
                    salir= true;
                    break;
                default:

                    System.out.println("error");
            }

        }


        //if (scanner.nextLine().equals("S"))
        //{
        //    escribimossuperheores();
        //}
        //ConvertiraJson();
        //lecturajson();

    }



    // esto sirve para escribir los nuevos superheroes en el xml
    public static void AñadirSuperheroes() throws XMLStreamException, IOException

    {
        //abrimos el archivo
        FileReader file = new FileReader("XML_superheroes.xml");
        BufferedReader reader = new BufferedReader(file);

        /*
        leemos el archivo y lo metemos todo en una variable string
        A continuacion este string sera leido por un event reader, de esta forma iremos introduciendo los datos segun necesitemos
        */

        String key = "";
        String line = reader.readLine();
        while (line != null) {
            key += line;
            line = reader.readLine();
        }
        reader.close();
        XMLInputFactory inFactory = XMLInputFactory.newInstance();
        XMLEventReader eventReader = inFactory.createXMLEventReader(new StringReader(key));
        XMLOutputFactory factory = XMLOutputFactory.newInstance();
        XMLEventWriter writer = factory.createXMLEventWriter(new FileWriter(("XML_superheroes.xml")));
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        int i =1;
        Boolean numerodebatallas=true;
        Boolean numerodehabilidades = true;
        Scanner scanner = new Scanner(System.in);
        while (eventReader.hasNext()) {
            XMLEvent event = eventReader.nextEvent();


            //Contamos la cantidad de superheroes que hay actualmente en el xml, de esta forma podemos asociar el id (key) correcto al siguiente superheroe que introduzcamos
            if (event.getEventType() == XMLEvent.START_ELEMENT) {
                if (event.asStartElement().getName().toString().equalsIgnoreCase("Superheroe")) {

                    i++; //vamos incrementandolo

                }
            }


            if (event.getEventType() == XMLEvent.END_ELEMENT) {

                if (event.asEndElement().getName().toString().equalsIgnoreCase("Superheroes")) {
                    // vamos pidiendo los datos correspondiente
                    writer.add(eventFactory.createStartElement("", null, "Superheroe"));
                    System.out.println("Indique la procedencia del superheroe: ");
                    writer.add(eventFactory.createAttribute("procedencia", scanner.nextLine()));
                    writer.add(eventFactory.createAttribute("id", Integer.toString(i)));
                    System.out.println("Indique el genero del superheroe: ");
                    writer.add(eventFactory.createAttribute("genero", scanner.nextLine()));
                    writer.add(eventFactory.createStartElement("", null, "Nombre"));
                    System.out.println("Nombre del superheroe: ");
                    writer.add(eventFactory.createCharacters(scanner.nextLine()));
                    writer.add(eventFactory.createEndElement("", null, "Nombre"));
                    writer.add(eventFactory.createStartElement("", null, "IdentidadS"));
                    System.out.println("Identidad del superheroe: ");
                    writer.add(eventFactory.createCharacters(scanner.nextLine()));
                    writer.add(eventFactory.createEndElement("", null, "IdentidadS"));
                    writer.add(eventFactory.createStartElement("", null, "Batallas"));
                    // el usuario ira introduciendo las batallas que quiera
                    while (numerodebatallas == true) {
                        writer.add(eventFactory.createStartElement("", null, "Batalla"));

                        writer.add(eventFactory.createStartElement("", null, "Fecha_comienzo"));
                        System.out.println("Fecha de comienzo de la batalla: ");
                        writer.add(eventFactory.createCharacters(scanner.nextLine()));
                        writer.add(eventFactory.createEndElement("", null, "Fecha_comienzo"));
                        writer.add(eventFactory.createStartElement("", null, "Fecha_fin"));
                        System.out.println("Fecha del fin de la batalla: ");
                        writer.add(eventFactory.createCharacters(scanner.nextLine()));
                        writer.add(eventFactory.createEndElement("", null, "Fecha_fin"));
                        writer.add(eventFactory.createStartElement("", null, "Lugar"));
                        System.out.println("Lugar de la batalla: ");
                        writer.add(eventFactory.createCharacters(scanner.nextLine()));
                        writer.add(eventFactory.createEndElement("", null, "Lugar"));

                        writer.add(eventFactory.createEndElement("", null, "Batalla"));
                        System.out.println("¿Quiere añadir otra batalla?(S/N)");
                        String comprobacion = scanner.nextLine();
                        if (comprobacion.equalsIgnoreCase("N")) {
                            numerodebatallas = false;
                        }
                    }
                    writer.add(eventFactory.createEndElement("", null, "Batallas"));

                    writer.add(eventFactory.createStartElement("", null, "Habilidades"));
                    // el usuario ira introduciendo las habilidades que quiera, de esta forma quedará como un array
                    while (numerodehabilidades == true) {
                        writer.add(eventFactory.createStartElement("", null, "Habilidad"));
                        writer.add(eventFactory.createStartElement("", null, "Tipo"));
                        System.out.println("Tipo de habilidad: ");
                        writer.add(eventFactory.createCharacters(scanner.nextLine()));
                        writer.add(eventFactory.createEndElement("", null, "Tipo"));
                        writer.add(eventFactory.createStartElement("", null, "Definicion"));
                        System.out.println("Definicion de la habilidad: ");
                        writer.add(eventFactory.createCharacters(scanner.nextLine()));
                        writer.add(eventFactory.createEndElement("", null, "Definicion"));
                        writer.add(eventFactory.createEndElement("", null, "Habilidad"));
                        System.out.println("¿Quiere añadir otra habilidad?(S/N)");
                        String comprobacion = scanner.nextLine();

                        if (comprobacion.equalsIgnoreCase("N")) {
                            numerodehabilidades = false;
                        }
                    }
                    writer.add(eventFactory.createEndElement("", null, "Habilidades"));

                }

            }
            writer.add(event);
        }

        writer.close();
        eventReader.close();





    }


    public static void ConvertiraJson() throws IOException, XMLStreamException{

        //EN VEZ DE CONVERTIR A MANO EL XML LO HACEMOS ASI PARA PODER ESCRIBIR LUEGO EN EL JSON
        //ESTE ES UNO DE LAS NUEVAS MODIFICACIONES

        String archivo = "XML_superheroes.xml";
        xmlInputFactory = XMLInputFactory.newFactory();
        inputStream = new FileInputStream(archivo);
        xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStream);


        String jsonPrettyPrintString= null; //variable para dejarlo con espacios en blanco y saltos de linea
        JSONObject objetoxml =null;
        try{
            byte[] content = Files.readAllBytes(Paths.get("XML_superheroes.xml"));
            //System.out.println(new String(content));
            String str = new String(content);
            //JSONObject objetoxml =null;

            try{

                objetoxml = XML.toJSONObject(str);
                // transformamos el objetojson y lo pasamos a un string
                jsonPrettyPrintString = objetoxml.toString();
                System.out.println(jsonPrettyPrintString);

            }catch (JSONException ex0){
                System.out.println(ex0.toString());
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        //como en toda l practica, vamos a usar la libreria de google Gson por lo que tenemos que cambiar de json a gson
        //Antes de oder usar GSON JsonParser se crea la instancia JsonParser
        JsonParser parser = new JsonParser();
        //podemos recorrer los objetos
        JsonObject gsonObj = (JsonObject) parser.parse(jsonPrettyPrintString).getAsJsonObject(); //Cambiamos de JSONObject a JsonObject
        //System.out.println(gsonObj.toString());


        //abrimos Stream y creamos el archivo en caso de que no exista
        try (Writer w = new FileWriter("Superhereoes_JSON.json")) {
            //creamos una instancia de gson para que el JSON se vea mejor
            //con espacios en blanco y saltos de linea
            Gson gson_xml = new GsonBuilder().setPrettyPrinting().create();

            gson_xml.toJson(gsonObj, w);
        }
        xmlStreamReader.close();
        inputStream.close();


    }


    //Funcion para pdoer leer el json con gson
    public static void lecturajson() throws JsonIOException, JsonSyntaxException, FileNotFoundException{

        JsonObject gson = null;
        Scanner scaner = null;
        try {

            JsonParser jparser = new JsonParser();
            Object objeto = jparser.parse(new FileReader("Superhereoes_JSON.json"));
            gson = (JsonObject) objeto;

            gson = gson.getAsJsonObject("Heroe"); //nos metemos dentro de Heores para poder comprobar ahora

            //comprobamos si lee el json correctamente
            //vamos a mostrar al superheroe Thor

            //gson = gson.getAsJsonObject("Superheroes");
            scaner = new Scanner(System.in);

            System.out.println("\n          M E N U lectura       \n\n");
            System.out.println("\n 1 - Mostrar batallas en las queha participado el superheroe");
            System.out.println("\n 2 - Mostrar toda la info ( menos batallas) del superheroe");

            Integer opcion = scaner.nextInt(); //esperamos que se pulse 1 o 2

            //cremos un switch para el menu de opciones
            //String Nombre_Superheroe  = scaner.nextLine();
            //gson = gson.getAsJsonObject("Superheroes");


            //Al tenerlo como array tenemos que poder ponerlo en JsonArray para poder leerlo bien



            switch (opcion){
                case 1:
                    scaner = new Scanner(System.in);
                    //String Nombre_Superheroe = "Thor";
                    System.out.println("Introduce el Nombre del Superheroe...:  ");
                    String Nombre_Superheroe = scaner.nextLine();

                    //entremosn en el objeto superheroes
                    gson = gson.getAsJsonObject("Superheroes");
                    //LO tratamos como si fuera un array, en caso de que solo tengamos uno va directamente como error al catch
                    try{

                        JsonArray resultado = gson.get("Superheroe").getAsJsonArray();
                        //hecemos un for de ese array para poder buscar al superheroe Thor
                        for(JsonElement elemento : resultado){
                            String nombre;
                            nombre = ((JsonObject) elemento).get("Nombre").getAsString(); //cogemos el nombre del superheroes para poder compararlo

                            //comparamos el nombre del superheroe con los nombre del array para ver si coincide
                            if(Nombre_Superheroe.equalsIgnoreCase(nombre)){
                                //si coincide vamos a buscar las batallas en las que ha participado
                                //volvemos a comprobar si es un array (que lo es)
                                JsonObject batallas = elemento.getAsJsonObject();
                                //loclizamos las batallas
                                batallas = batallas.getAsJsonObject("Batallas");

                                try{ //al ser array lo comprobamos como hemos hecho antes
                                    JsonArray batalla = batallas.get("Batalla").getAsJsonArray();
                                    for(JsonElement elemento2 : batalla){
                                        try{
                                            System.out.println("Lugar de la batalla: " +((JsonObject) elemento2).get("Lugar").getAsString());
                                            //y fecha inicio
                                            System.out.println("Fecha comienzo de la batalla: " +((JsonObject) elemento2).get("Fecha_comienzo").getAsString());
                                            //fecha fin
                                            System.out.println("Fecha fin de la batalla: " +((JsonObject) elemento2).get("Fecha_fin").getAsString());
                                        }catch (Exception e){
                                            System.out.println("El superherore inidcado no ha participado en batallas...");
                                        }

                                    }
                                    //si no es array y solo tengo una batalla...lo muestro asi
                                }catch (Exception ex){
                                    batallas = batallas.getAsJsonObject("Batalla");
                                    //System.out.println("\nLugar de la batalla: "+(batallas.get("Lugar").getAsString()));
                                    //System.out.println("Fecha comienzo de la batalla: "+(batallas.get("Fecha_comienzo").getAsString()));
                                    try{
                                        System.out.println("\nLugar de la batalla: "+(batallas.get("Lugar").getAsString()));
                                        //y fecha inicio
                                        System.out.println("Fecha comienzo de la batalla: "+(batallas.get("Fecha_comienzo").getAsString()));
                                        System.out.println("\nFecha fin de la batalla: "+(batallas.get("Fecha_fin").getAsString()));
                                    }catch (Exception e){
                                        System.out.println("El superherore inidcado no ha participado en batallas...");
                                    }
                                }

                            }

                        }
                    }catch (Exception ex2){ // en caso de que Superheroes no sea un array y solo tengamos uno
                        gson = gson.getAsJsonObject("Superheroe");
                        String nombre_super = gson.get("Nombre").getAsString();
                        gson = gson.getAsJsonObject("Batallas");
                        if (nombre_super.equalsIgnoreCase(Nombre_Superheroe)) {
                            try{
                                JsonArray autores = gson.get("Batalla").getAsJsonArray();
                                for (JsonElement demarc : autores) {
                                    try {
                                        System.out.println("\nLugar de la batalla: "+((JsonObject) demarc).get("Lugar").getAsString());
                                        System.out.println("Fecha comienzo de la batalla: "+((JsonObject) demarc).get("Fecha_comienzo").getAsString());
                                        System.out.println("Fecha fin de la batalla: "+((JsonObject) demarc).get("Fecha_fin").getAsString());
                                    }catch (Exception e2){
                                        System.out.println("El superherore inidcado no ha participado en batallas...");
                                    }

                                }
                            }catch (Exception e1) {
                                gson = gson.getAsJsonObject("Batalla");
                                try{
                                    System.out.println("\nLugar de la batalla: "+(gson.get("Lugar").getAsString()));
                                    System.out.println("Fecha comienzo de la batalla: "+(gson.get("Fecha_cominezo").getAsString()));
                                    System.out.println("Fecha fin de la batalla: "+(gson.get("Fecha_fin").getAsString()));
                                }catch (Exception e2){
                                    System.out.println("El superherore inidcado no ha participado en batallas...");
                                }

                            }
                        }

                    }

                    break;

                case 2:

                    scaner = new Scanner(System.in);
                    //String Nombre_Superheroe = "Thor";
                    System.out.println("Introduce el Nombre del Superheroe :   ");
                    String Nombree_Superheroe = scaner.nextLine();

                    //Vamos a mostrar toda la informacion de los superheroes
                    //excepto las batallas en las que ha partiipado

                    //nos colocamos en el objeto Superheroes
                    gson =  gson.getAsJsonObject("Superheroes");
                    //gson = gson.getAsJsonObject("Superheroe"); //metemos en superhreoes

                    try{
                        //lo tratmos como un array ya que tenemos mas de un superheroe
                        JsonArray array = gson.get("Superheroe").getAsJsonArray();
                        //recoremos un bucle for de ese array e superheroes
                        for(JsonElement element : array){

                            String nombre;
                            nombre = ((JsonObject) element).get("Nombre").getAsString(); //cogemos el nombre del superheroes para poder compararlo

                            if(nombre.equalsIgnoreCase(Nombree_Superheroe)) {
                                System.out.println("         FICHA SUPERHEROE:     " + ((JsonObject) element).get("Nombre").getAsString());
                                System.out.println("\n Nombre:  " + ((JsonObject) element).get("Nombre").getAsString());
                                System.out.println("\n ID:  " + ((JsonObject) element).get("id").getAsString());
                                System.out.println("\n Genero:  " + ((JsonObject) element).get("genero").getAsString());
                                System.out.println("\n Procedencia:  " + ((JsonObject) element).get("procedencia").getAsString());
                                System.out.println("\n Identidad Secreta:  " + ((JsonObject) element).get("IdentidadS").getAsString());

                                //nos encontramos con las habilidades, vemos si es un array
                                JsonObject habilidades = element.getAsJsonObject();
                                //loclizamos las batallas
                                habilidades = habilidades.getAsJsonObject("Habilidades");

                                try { //al ser array lo comprobamos como hemos hecho antes
                                    JsonArray habilidad = habilidades.get("Habilidad").getAsJsonArray();
                                    System.out.println("\n Habilidades: \n");
                                    for (JsonElement elemento2 : habilidad) {
                                        try {
                                            System.out.println("Tipo de habilidad: " + ((JsonObject) elemento2).get("Tipo").getAsString());
                                            //y fecha inicio
                                            System.out.println("Definicion: " + ((JsonObject) elemento2).get("Definicion").getAsString());
                                        } catch (Exception e) {
                                            System.out.println("El superherore inidcado no tiene habilidades especiales...");
                                        }

                                    }
                                    //si no es array y solo tengo una habilidad...lo muestro asi
                                }catch (Exception ex) {
                                    habilidades = habilidades.getAsJsonObject("Habilidad");
                                    //System.out.println("\nLugar de la batalla: "+(batallas.get("Lugar").getAsString()));
                                    //System.out.println("Fecha comienzo de la batalla: "+(batallas.get("Fecha_comienzo").getAsString()));
                                    try {
                                        System.out.println("\nTipo de habilidad:  " + (habilidades.get("Tipo").getAsString()));
                                        //y fecha inicio
                                        System.out.println("Definicion: " + (habilidades.get("Definicion").getAsString()));
                                    } catch (Exception e) {
                                        System.out.println("El superherore inidcado no tiene habilidades especiales...");
                                    }
                                }

                            }

                        }

                    }catch (Exception e3){

                        gson = gson.getAsJsonObject("Superheroe");
                        String nombre_superheroe = gson.get("Nombre").getAsString();
                        gson = gson.getAsJsonObject("Habilidades");

                        if(nombre_superheroe.equalsIgnoreCase(Nombree_Superheroe)){

                            System.out.println("\nNombre:  " +gson.get("Nombre").getAsString());
                            System.out.println("\nIdentidad Secreta:  " +gson.get("Nombre").getAsString());

                            //nos encontramos con las habilidades, vemos si es un array
                            try{
                                JsonArray hab = gson.get("Habilidad").getAsJsonArray();
                                for (JsonElement demarc : hab) {
                                    try {
                                        System.out.println("\nTipo de habilidad: "+((JsonObject) demarc).get("Tipo").getAsString());
                                        System.out.println("Descripcion habilidad:  "+((JsonObject) demarc).get("Descripcion").getAsString());
                                    }catch (Exception e2){
                                        System.out.println("El superherore inidcado no tiene habilidades");
                                    }

                                }
                            }catch (Exception e1) {
                                gson = gson.getAsJsonObject("Habilidad");
                                try{
                                    System.out.println("\nTipo de habilidad: "+(gson.get("Habilidad").getAsString()));
                                    System.out.println("Descripcion habilidad "+(gson.get("Descripcion").getAsString()));
                                }catch (Exception e2){
                                    System.out.println("El superherore inidcado no tiene habilidades...");
                                }

                            }


                        }
                    }

                    break;



                //FALTA PONER QUE CUANDO SOLO SEA UNO QUE LO MUESTRE (catch  Exception e3 ), METIENDO IF PARA COMPROBAR EL SUPERHEROE Y ARRAY DE HABILIDADES

                default: // si no coincide
                    System.out.println("Opcion no encontrada");
                    break;

            }




        }catch (FileNotFoundException e){
            System.out.println("No dispone de archivo json para su lectura...");
        }

    }




}

