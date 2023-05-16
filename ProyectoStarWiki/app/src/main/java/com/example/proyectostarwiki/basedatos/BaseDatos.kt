package com.example.proyectostarwiki.basedatos

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyectostarwiki.models.NavesData
import com.example.proyectostarwiki.models.PilotosData
import com.example.proyectostarwiki.models.naves

class BaseDatos(c: Context): SQLiteOpenHelper(c, DB, null, VERSION) {
    companion object{
        const val DB="starWiki"
        const val VERSION=1
        const val TABLANAVES="naves"
        const val TABLAPILOTOS="pilotos"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val qnaves= "create table $TABLANAVES(" +
                "nombre text primary key not null unique, " +
                "clase text not null, " +
                "capacidad text not null, " +
                "longitud text not null, " +
                "pasajeros text not null, " +
                "creditos text not null, " +
                "piloto text not null)"
        p0?.execSQL(qnaves)

        val qpilotos= "create table $TABLAPILOTOS(" +
                "nombre text primary key not null unique, " +
                "genero text not null, " +
                "altura text not null, " +
                "peso text not null, " +
                "nombreNave text not null)"
        p0?.execSQL(qpilotos)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    //Metodos para gestionar la tabla de naves
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

    fun borrarNaves(nombre: String){
        val conexion=this.writableDatabase
        val q="delete from $TABLANAVES where nombre=nombre"
        conexion.close()
    }

    //Metodos para gestionar la tabla de pilotos
    fun createPilotos(piloto: PilotosData): Long{
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("nombre", piloto.nombre)
            put("genero", piloto.genero)
            put("altura", piloto.altura)
            put("peso", piloto.peso)
            put("nombreNave", piloto.nombreNave)
        }
        val ins = conexion.insert(TABLAPILOTOS, null, valores)
        conexion.close()
        return ins
    }

    fun readPilotos(): MutableList<PilotosData>{
        val lista = mutableListOf<PilotosData>()
        val q="select * from $TABLAPILOTOS order by nombre"
        val conexion=this.readableDatabase
        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()){
                do {
                    val piloto=PilotosData(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
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

    fun borrarPilotos(nombre: String){
        val conexion=this.writableDatabase
        val q="delete from $TABLAPILOTOS where nombre=nombre"
        conexion.close()
    }


}