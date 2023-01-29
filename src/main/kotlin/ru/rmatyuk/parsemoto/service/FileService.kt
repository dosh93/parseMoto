package ru.rmatyuk.parsemoto.service

import org.springframework.stereotype.Component
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@Component
class FileService(httpService: HttpService) {

    private final val httpService: HttpService

    init {
        this.httpService = httpService
    }

    fun init(pathDir: Path) {
        try {
            Files.createDirectories(pathDir)
        } catch (e: IOException) {
            throw RuntimeException("Could not create upload folder!")
        }
    }

    fun save(pathDir: String, urlFile: String): String {
        val get = Paths.get(pathDir)
        if (!Files.exists(get)) {
            init(get)
        }
        val uuidPhoto = UUID.randomUUID().toString()
        val file = File(pathDir + "\\${uuidPhoto}.jpg")
        httpService.saveFile(urlFile, file)
        return file.absolutePath
    }

    fun remove(paths: List<String>) {
        paths.forEach {
            Files.deleteIfExists(Paths.get(it))
        }
    }
}