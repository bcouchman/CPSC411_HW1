package org.csuf.cspc411

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.contentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.utils.io.readAvailable
import org.csuf.cspc411.Dao.Claim.Claim
import org.csuf.cspc411.Dao.Claim.ClaimDao
import java.util.*


fun main(args: Array<String>): Unit {
    // Register PersonStore callback functions

    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    // extension
    // @annotation
    // routing constructor takes only one parameter which is a lambda function
    // DSL - Domain Specific Language
    routing{
        this.get("/ClaimService/add"){
            // Not required for HW1 (Method:get Service:add)
            println("HTTP message is using GET method with /get ")
            val myTitle = call.request.queryParameters["title"]
            val myDate : String? = call.request.queryParameters["date"]
            val response = String.format("Title %s and date %s", myTitle, myDate)
            //
            val dObj = Claim(myTitle, myDate)
            val dao = ClaimDao().addClaim(dObj)
            call.respondText(response, status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

        get("/ClaimService/getAll"){
            //Method:get Service:getAll
            val dList = ClaimDao().getAll()
            println("The number of claims : ${dList.size}")
            // JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(dList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType= ContentType.Application.Json)
        }

        post("/ClaimService/add"){
            //Method:Post Service:add
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            var output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)
            val cType = object : TypeToken<Claim>() { }.type  //Claim Type
            ClaimDao().addClaim(Gson().fromJson<Claim>(str, cType))

            println("HTTP message is using POST method with /post ${contType} ${str}")
            call.respondText("The POST request was successfully processed. ",
                    status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
    }

}

