package com.csanchez.practica2.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.csanchez.practica2.model.Monster

/**
 * Creado por Amaury Perea Matsumura el 21/10/22
 */
class DbMonsters(private val context: Context): DbHelper(context) {

    //Aquí se van a implementar las operaciones CRUD (Create, Read, Update and Delete)

    fun insertMonster(name: String, origin: String, description: String, genrer: Int): Long{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id: Long = 0

        try{
            val values = ContentValues()

            values.put("name", name)
            values.put("origin", origin)
            values.put("description", description)
            values.put("genrer", genrer)

            id = db.insert("monsters", null, values)

        }catch(e: Exception){
            //Manejo de excepción
        }finally {
            db.close()
        }

        return id
    }

    fun getMonsters(): ArrayList<Monster>{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listMonsters = ArrayList<Monster>()
        var monsterTmp: Monster? = null
        var cursorMonsters: Cursor? = null

        cursorMonsters = db.rawQuery("SELECT * FROM MONSTERS", null)

        if(cursorMonsters.moveToFirst()){
            do{
                monsterTmp = Monster(cursorMonsters.getInt(0), cursorMonsters.getString(1), cursorMonsters.getString(2), cursorMonsters.getString(3),cursorMonsters.getInt(4))
                listMonsters.add(monsterTmp)
            }while(cursorMonsters.moveToNext())
        }

        cursorMonsters.close()

        return listMonsters
    }

    fun getMonster(id: Int): Monster?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var game: Monster? = null

        var cursorMonsters: Cursor? = null

        cursorMonsters = db.rawQuery("SELECT * FROM MONSTERS WHERE id = $id LIMIT 1", null)

        if(cursorMonsters.moveToFirst()){
            game = Monster(cursorMonsters.getInt(0), cursorMonsters.getString(1), cursorMonsters.getString(2), cursorMonsters.getString(3),cursorMonsters.getInt(4) )
        }

        cursorMonsters.close()

        return game
    }

    fun updateMonster(id: Int, name: String, origin: String, description: String, genrer: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE MONSTERS SET name = '$name', origin = '$origin', description = '$description', genrer = '$genrer' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){
            //Manejo de la excepción
        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteMonster(id: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM MONSTERS WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}