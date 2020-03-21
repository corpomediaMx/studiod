package mx.corpomedia.studiod.sqlite;

public class Utilidades {

    public static final String TABLE_DIENTES = "dientes";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_ORDEN_ID = "orden_id";
    public static final String CAMPO_TIPOS_INDICACIONES_ID = "tipos_indicaciones_id";
    public static final String CAMPO_DIENTES = "dientes";
    public static final String CAMPO_MATERIALES_ID = "materiales_id";
    public static final String CAMPO_MATERIALES_NOMBRE = "materiales_nombre";
    public static final String CAMPO_DISENOS_ID = "disenos_id";
    public static final String CAMPO_DISENOS_NOMBRE = "disenos_nombre";
    public static final String CAMPO_COLOR = "color";
    public static final String CAMPO_PRUEBA = "prueba";
    public static final String CAMPO_OBSERVACIONES = "observaciones";
    public static final String CAMPO_USER_ID = "user_id";
    public static final String CAMPO_PRECIO = "precio";


    public static final String TABLE_ORDENES = "ordenes";
    public static final String CAMPO_PACIENTE = "paciente";
    public static final String CAMPO_TIPO_ENTREGA = "tipo_entrega";
    public static final String CAMPO_REGISTRO_MORDIDA = "registro_mordida";
    public static final String CAMPO_ANTAGONISTA = "antagonista";
    public static final String CAMPO_TOTAL = "total";
    public static final String CAMPO_STATUS = "status";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_USUARIOS_ID = "usuarios_id";


    public static final String TABLE_AUDIOS = "audios";
    public static final String CAMPO_DIENTES_ID = "dientes_id";
    public static final String CAMPO_ARCHIVO = "archivo";

    public static final String TABLE_FOTOS = "fotos";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final  String CAMPO_ESTADO = "estado";

    public  static final String CREAR_TABLA_DIENTES = "CREATE TABLE "+TABLE_DIENTES+" ("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_ORDEN_ID+" INTEGER, "+CAMPO_TIPOS_INDICACIONES_ID+" INTEGER, "+CAMPO_DIENTES+" TEXT, "+CAMPO_MATERIALES_ID+" INTEGER, "+CAMPO_MATERIALES_NOMBRE+" TEXT, "+CAMPO_DISENOS_ID+" INTEGER, "+CAMPO_DISENOS_NOMBRE+" TEXT, "+CAMPO_COLOR+" TEXT, "+CAMPO_PRUEBA+" TEXT, "+CAMPO_OBSERVACIONES+" TEXT, "+CAMPO_USER_ID+" INTEGER,"+CAMPO_PRECIO+" DOUBLE )";
    public  static final String CREAR_TABLA_ORDENES = "CREATE TABLE "+TABLE_ORDENES+" ("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_ORDEN_ID+" INTEGER, "+CAMPO_USUARIOS_ID+" INTEGER, "+CAMPO_PACIENTE+" TEXT, "+CAMPO_TIPO_ENTREGA+" TEXT, "+CAMPO_REGISTRO_MORDIDA+" TEXT, "+CAMPO_ANTAGONISTA+" TEXT, "+CAMPO_TOTAL+" TEXT, "+CAMPO_STATUS+" TEXT, "+CAMPO_FECHA+" TEXT )";
    public  static final String CREAR_TABLA_AUDIOS = "CREATE TABLE "+TABLE_AUDIOS+" ("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_DIENTES_ID+" INTEGER, "+CAMPO_DIENTES+" TEXT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_ARCHIVO+" TEXT, "+CAMPO_ESTADO+" TEXT, "+CAMPO_FECHA+" TEXT )";
    public  static final String CREAR_TABLA_FOTOS = "CREATE TABLE "+TABLE_FOTOS+" ("+CAMPO_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_DIENTES_ID+" INTEGER, "+CAMPO_DIENTES+" TEXT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_ARCHIVO+" TEXT, "+CAMPO_ESTADO+" TEXT, "+CAMPO_FECHA+" TEXT )";
}
