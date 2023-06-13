/**
 * BaseDatos.kt
 * @author Luis Manuel Garrigosa Jimenez
 */

package com.example.proyectostarwiki.basedatos

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.proyectostarwiki.models.*

class BaseDatos(c: Context): SQLiteOpenHelper(c, DB, null, VERSION) {
    //Objetos con las variables de la base de datos
    companion object{
        const val DB="starWiki"
        const val VERSION=6
        const val TABLANAVES="naves"
        const val TABLAPILOTOS="pilotos"
        const val TABLAVEHICULOS="vehiculos"
        const val TABLAPLANETAS="planetas"
        const val TABLAPELICULAS="peliculas"
        //Query con la creación de la tabla naves
        const val qnaves= "create table $TABLANAVES(" +
                "nombre text primary key not null unique, " +
                "clase text not null, " +
                "capacidad text not null, " +
                "longitud text not null, " +
                "pasajeros text not null, " +
                "creditos text not null)"
        //Query con la creación de la tabla pilotos
        const val qpilotos= "create table $TABLAPILOTOS(" +
                "nombre text primary key not null unique, " +
                "genero text not null, " +
                "altura text not null, " +
                "peso text not null, " +
                "nombreNave text not null)"
        //Query con la creación de la tabla vehiculos
        const val qvehiculos= "create table $TABLAVEHICULOS(" +
                "nombre text primary key not null unique, " +
                "modelo text not null, " +
                "altura text not null, " +
                "velocidad text not null, " +
                "fabricante text not null)"
        //Query con la creación de la tabla planetas
        const val qplanetas= "create table $TABLAPLANETAS(" +
                "nombre text primary key not null unique, " +
                "gravedad text not null, " +
                "poblacion text not null, " +
                "diametro text not null, " +
                "terreno text not null)"
        //Query con la creación de la tabla peliculas
        const val qpeliculas= "create table $TABLAPELICULAS(" +
                "nombre text primary key not null unique, " +
                "director text not null, " +
                "productor text not null, " +
                "estreno text not null)"
    }

    /**
     * Sobreescritura de la función onCreate, que hace que al iniciar el activity
     * se lance lo que tenga dentro.
     *
     */
    override fun onCreate(p0: SQLiteDatabase?) {
        //Cración de la tabla naves
        p0?.execSQL(qnaves)
        Log.d("BaseDatos", "Creando tabla naves")
        //Cración de la tabla pilotos
        p0?.execSQL(qpilotos)
        Log.d("BaseDatos", "Creando tabla pilotos")
        //Cración de la tabla vehiculos
        p0?.execSQL(qvehiculos)
        Log.d("BaseDatos", "Creando tabla vehiculos")
        //Cración de la tabla planetas
        p0?.execSQL(qplanetas)
        Log.d("BaseDatos", "Creando tabla planetas")
        //Cración de la tabla peliculas
        p0?.execSQL(qpeliculas)
        Log.d("BaseDatos", "Creando tabla peliculas")

    }

    /**
     * Sobreescritura de la función onUpgrade, que al subir la versión de la
     * base de datos, borra las tablas existentes y vuelve a lanzar el método
     * onCreate
     *
     */
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE IF EXISTS $TABLANAVES")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLAPILOTOS")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLAPLANETAS")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLAVEHICULOS")
        p0?.execSQL("DROP TABLE IF EXISTS $TABLAPELICULAS")
        onCreate(p0)
    }

    //Metodos para gestionar la tabla peliculas

    /**
     * Función createPeliculas() para crear una pelicula nueva en la tabla
     *
     * @param pelicula Pelicula seleccionada para crear
     * @return insert de la tabla peliculas con los valores de la pelicula seleccionada
     *
     */
    fun createPeliculas(pelicula: PeliculasData): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", pelicula.nombre)
            put("director", pelicula.director)
            put("productor", pelicula.productor)
            put("estreno", pelicula.estreno)
        }
        val ins = conexion.insert(TABLAPELICULAS,null, valores)
        conexion.close()
        return ins
    }

    /**
     * Función readPeliculas() para leer todas las peliculas que hay
     * en la tabla peliculas ordenadas por nombre
     *
     * @return lista de la tabla peliculas ordenadas por nombre
     *
     */
    fun readPeliculas(): MutableList<PeliculasData>{
        val lista = mutableListOf<PeliculasData>()
        val q="select * from $TABLAPELICULAS order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val pelicula=PeliculasData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                    )
                    lista.add(pelicula)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    /**
     * Función borrarPeliculas() para borrar el contenido de la
     * tabla peliculas
     *
     */
    fun borrarPeliculas(){
        val conexion=this.writableDatabase
        val q="delete from $TABLAPELICULAS"
        conexion.execSQL(q)
        conexion.close()
    }

    //Metodos para gestionar la tabla planetas

    /**
     * Función createPlanetas() para crear un planeta nuevo en la tabla
     *
     * @param planeta Planeta seleccionado para crear
     * @return insert de la tabla planetas con los valores del planeta seleccionado
     *
     */
    fun createPlanetas(planeta: PlanetasData): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", planeta.nombre)
            put("gravedad", planeta.gravedad)
            put("poblacion", planeta.poblacion)
            put("diametro", planeta.diametro)
            put("terreno", planeta.terreno)
        }
        val ins = conexion.insert(TABLAPLANETAS,null, valores)
        conexion.close()
        return ins
    }

    /**
     * Función readPlanetas() para leer todos los planetas que hay
     * en la tabla planetas ordenados por nombre
     *
     * @return lista de la tabla planetas ordenados por nombre
     *
     */
    fun readPlanetas(): MutableList<PlanetasData>{
        val lista = mutableListOf<PlanetasData>()
        val q="select * from $TABLAPLANETAS order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val planeta=PlanetasData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                    lista.add(planeta)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    /**
     * Función borrarPlanetas() para borrar el contenido de la
     * tabla planetas
     *
     */
    fun borrarPlanetas(){
        val conexion=this.writableDatabase
        val q="delete from $TABLAPLANETAS"
        conexion.execSQL(q)
        conexion.close()
    }


    //Metodos para gestionar la tabla vehiculos

    /**
     * Función createVehiculos() para crear un vehiculo nuevo en la tabla
     *
     * @param vehiculo Vehiculo seleccionado para crear
     * @return insert de la tabla vehiculos con los valores del vehiculo seleccionado
     *
     */
    fun createVehiculos(vehiculo: VehiculosData): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", vehiculo.nombre)
            put("modelo", vehiculo.modelo)
            put("altura", vehiculo.altura)
            put("velocidad", vehiculo.velocidad)
            put("fabricante", vehiculo.fabricante)
        }
        val ins = conexion.insert(TABLAVEHICULOS,null, valores)
        conexion.close()
        return ins
    }

    /**
     * Función readVehiculos() para leer todos los vehiculos que hay
     * en la tabla vehiculos ordenados por nombre
     *
     * @return lista de la tabla vehiculos ordenados por nombre
     *
     */
    fun readVehiculos(): MutableList<VehiculosData>{
        val lista = mutableListOf<VehiculosData>()
        val q="select * from $TABLAVEHICULOS order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val vehiculo=VehiculosData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                    lista.add(vehiculo)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    /**
     * Función borrarVehiculos() para borrar el contenido de la
     * tabla vehiculos
     *
     */
    fun borrarVehiculos(){
        val conexion=this.writableDatabase
        val q="delete from $TABLAVEHICULOS"
        conexion.execSQL(q)
        conexion.close()
    }

    //Metodos para gestionar la tabla de naves

    /**
     * Función createNaves() para crear una nave nuevo en la tabla
     *
     * @param nave Nave seleccionada para crear
     * @return insert de la tabla naves con los valores de la nave seleccionada
     *
     */
    fun createNaves(nave: NavesData): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", nave.nombre)
            put("clase", nave.clase)
            put("capacidad", nave.capacidad)
            put("longitud", nave.longitud)
            put("pasajeros", nave.pasajeros)
            put("creditos", nave.creditos)
        }
        val ins = conexion.insert(TABLANAVES, null, valores)
        conexion.close()
        return ins
    }

    /**
     * Función readNaves() para leer todas las naves que hay
     * en la tabla naves ordenados por nombre
     *
     * @return lista de la tabla naves ordenados por nombre
     *
     */
    fun readNaves(): MutableList<NavesData>{
        val lista = mutableListOf<NavesData>()
        val q="select * from $TABLANAVES order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val nave=NavesData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                    )
                    lista.add(nave)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    /**
     * Función borrarNaves() para borrar el contenido de la
     * tabla naves
     *
     */
    fun borrarNaves(){
        val conexion=this.writableDatabase
        val q="delete from $TABLANAVES"
        conexion.execSQL(q)
        conexion.close()
    }

    //Metodos para gestionar la tabla de pilotos

    /**
     * Función createPilotos() para crear un piloto nuevo en la tabla
     *
     * @param piloto Piloto seleccionado para crear
     * @return insert de la tabla pilotos con los valores del piloto seleccionada
     *
     */
    fun createPilotos(piloto: PilotosData, nombreNave: String): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", piloto.nombre)
            put("genero", piloto.genero)
            put("altura", piloto.altura)
            put("peso", piloto.peso)
            put("nombreNave", nombreNave)
        }
        val ins = conexion.insert(TABLAPILOTOS, null, valores)
        conexion.close()
        return ins
    }

    /**
     * Función readPilotos() para leer todos los pilotos que hay
     * en la tabla pilotos ordenados por nombre
     *
     * @return lista de la tabla pilotos ordenados por nombre
     *
     */
    @SuppressLint("Range")
    fun readPilotos(): MutableList<PilotosData>{
        val lista = mutableListOf<PilotosData>()
        val q="select * from $TABLAPILOTOS order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val piloto=PilotosData(
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("genero")),
                        cursor.getString(cursor.getColumnIndex("altura")),
                        cursor.getString(cursor.getColumnIndex("peso")),
                        cursor.getString(cursor.getColumnIndex("nombreNave"))
                    )
                    lista.add(piloto)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()

        return lista

    }

    /**
     * Función readPilotosSeleccionados() para leer todos los pilotos que hay
     * en la tabla pilotos que contengan el mismo nombre de nave ordenados por nombre
     *
     * @param nombreNave Nombre de la nave que se busca en los pilotos
     * @return lista de la tabla pilotos ordenados por nombre
     *
     */
    @SuppressLint("Range")
    fun readPilotosSeleccionados(nombreNave: String): MutableList<PilotosData>{
        val lista = mutableListOf<PilotosData>()
        val q="select * from $TABLAPILOTOS where nombreNave='$nombreNave' order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val piloto=PilotosData(
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("genero")),
                        cursor.getString(cursor.getColumnIndex("altura")),
                        cursor.getString(cursor.getColumnIndex("peso")),
                        cursor.getString(cursor.getColumnIndex("nombreNave"))
                    )
                    lista.add(piloto)
                }while (cursor.moveToNext())
            }
            cursor.close()
        }catch (e: Exception){
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    /**
     * Función borrarPilotos() para borrar el contenido de la
     * tabla pilotos
     *
     */
    fun borrarPilotos(){
        val conexion=this.writableDatabase
        val q="delete from $TABLAPILOTOS"
        conexion.execSQL(q)
        conexion.close()
    }

    //Metodo para borrar todas la base de datos

    /**
     * Función borrarTodo() para borrar el contenido de las
     * tablas de la base de datos
     *
     */
    fun borrarTodo(){
        borrarNaves()
        borrarPilotos()
        borrarVehiculos()
        borrarPlanetas()
        borrarPeliculas()
    }

}