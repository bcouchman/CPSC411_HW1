package org.csuf.cspc411.Dao.Claim

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.UUID


data class Claim (var title:String?, var date:String?, var id:UUID = UUID.randomUUID(), var isSolved:Boolean = false)

fun main() {
    // JSON Serialization
    var dObj = Claim("Test Claim", "2000 01-01")
    var jsonStr = Gson().toJson(dObj)
    println("The converted JSON string : ${jsonStr}")

    // Serialization of List<Claim>
    var dList : MutableList<Claim> = mutableListOf()
    dList.add(dObj)
    dList.add(Claim("Test 2", "2000 12-31"))
    val listJsonString = Gson().toJson(dList)
    //JSONArray object
    println("${listJsonString}")

    // List<Person> object deserialization
    val personListType : Type = object : TypeToken<Claim>(){}.type

    // JSON Deserialization
    var dObj1 : Claim
    jsonStr = "{\"title\":\"Test 1\", \"ssn\":\"1999 12-31\"}"
    dObj1 = Gson().fromJson(jsonStr, Claim::class.java)
    println("${dObj1.toString()}")

}