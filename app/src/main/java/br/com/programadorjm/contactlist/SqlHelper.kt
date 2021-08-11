package br.com.programadorjm.contactlist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlHelper private constructor(context: Context):SQLiteOpenHelper(context, DB_NAME, null, VERSION_NUMBER) {
    private val tableName = "contacts"
    private val columnId = "id"
    private val columnName = "name"
    private val columnPhone = "phone"

    companion object{
        private const val DB_NAME = "contactDatabase"
        private const val VERSION_NUMBER = 1

        private var INSTANCE: SqlHelper? = null
        fun getInstance(context: Context): SqlHelper {
            if (INSTANCE == null){
                INSTANCE = SqlHelper(context)
            }
            return INSTANCE as SqlHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $tableName (" +
                "$columnId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "$columnName TEXT NOT NULL, " +
                "$columnPhone TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){db?.execSQL("DROP TABLE IF EXISTS $tableName")}
        onCreate(db)
    }

    fun insertContact(contact: Contact){
        val db = writableDatabase ?: return
        val content = ContentValues()
        content.put(columnName, contact.name)
        content.put(columnPhone, contact.phone)
        db.insert(tableName, null, content)
        db.close()
    }

    fun updateContact(contact: Contact) {
        val db = writableDatabase ?: return
        val arg = arrayOf(contact.name, contact.phone, contact.id)
        db.execSQL("UPDATE $tableName SET $columnName = ?, $columnPhone = ? WHERE $columnId = ?", arg)
    }

    fun deleteContact(id:Int){
        val db = writableDatabase ?: return
        val arg = arrayOf("$id")
        db.execSQL("DELETE FROM $tableName WHERE $columnId = ?", arg)
        db.close()
    }

    fun getAllContacts(search:String, isById:Boolean = false):List<Contact>{
        val contactList = mutableListOf<Contact>()
        val db = readableDatabase ?: return contactList
        val where: String?
        val args: Array<String>
        if (isById){
            where = "$columnId = ?"
            args = arrayOf(search)
        }else{
            where = "$columnName LIKE ?"
            args = arrayOf("%$search%")
        }

        val cursor = db.query(tableName, null, where, args, null,null,null)

        if (cursor == null) {
            db.close()
            return contactList
        }

        while (cursor.moveToNext()){
            val contact = Contact(
                cursor.getInt(cursor.getColumnIndex(columnId)),
                cursor.getString(cursor.getColumnIndex(columnName)),
                cursor.getString(cursor.getColumnIndex(columnPhone))
            )
            contactList.add(contact)
        }
        cursor.close()
        db.close()
        return contactList
    }
}