package ru.rmatyuk.parsemoto.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.rmatyuk.parsemoto.dto.InfoStartPage
import ru.rmatyuk.parsemoto.dto.MotoDto
import ru.rmatyuk.parsemoto.parser.HTMLParser
import ru.rmatyuk.parsemoto.parser.SiteParser
import ru.rmatyuk.parsemoto.service.TaskService
import ru.rmatyuk.parsemoto.service.motoOnline.MotoOnlineService
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.LocalTime


@RestController
class TestController(siteParser: SiteParser, motoOnlineService: MotoOnlineService, taskService: TaskService) {

    private final val siteParser: SiteParser
    private final val motoOnlineService: MotoOnlineService
    private final val taskService: TaskService
    var logger: Logger = LoggerFactory.getLogger(TestController::class.java)

    init {
        this.siteParser = siteParser
        this.motoOnlineService = motoOnlineService
        this.taskService = taskService
    }


    @GetMapping("/test")
    fun all(@RequestParam startPage: Int, @RequestParam countPage: Int): String {
        taskService.historyAuction(startPage, countPage)
        return "ОК"
    }

}