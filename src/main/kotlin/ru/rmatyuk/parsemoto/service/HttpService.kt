package ru.rmatyuk.parsemoto.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.rmatyuk.parsemoto.parser.SiteParser
import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Component
class HttpService {

    val client: HttpClient = HttpClient.newBuilder().build()
    var logger: Logger = LoggerFactory.getLogger(HttpService::class.java)

    fun saveFile(urlFile: String, filePath: File) {
        val request = HttpRequest.newBuilder()
                .uri(URI.create(urlFile))
                .GET()
                .build()
        client.send(request, HttpResponse.BodyHandlers.ofFile(filePath.toPath()))
    }

    fun get(url: String): String {
        val request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        logger.info("Request with $url done")
        return response.body()
    }
}