package com.pcf_metrics.data

import org.omg.CORBA.Object
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.sleuth.Span
import org.springframework.cloud.sleuth.SpanAccessor
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@RestController
open class TraceIdExampleApplication {
    val log = LoggerFactory.getLogger(this.javaClass.name)

    @Bean
    open fun restTemplate() : RestTemplate {
        return RestTemplate()
    }

    @Autowired
    private val spanAccessor: SpanAccessor? = null

    @Autowired
    private val rt: RestTemplate? = null

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/")
    fun hello(@RequestParam(value = "name", defaultValue = "World") name: String): Trace {
        val traceId = Span.idToHex(spanAccessor?.currentSpan?.traceId!!)
        log.info("Handling trace {}: name {}", traceId, name)
        return Trace(name, traceId)
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/call")
    fun call(@RequestParam(value = "url") url: String): String {
        val response = rt!!.getForObject(url, Trace::class.java)

        val traceId = Span.idToHex(spanAccessor?.currentSpan?.traceId!!)
        log.info("Handling trace {}: calling url {}", traceId, url)
        return "GET ${url} from trace ${traceId}: ${response}"
    }
}

data class Trace(val name: String = "", val traceId: String = "")

fun main(args: Array<String>) {
    SpringApplication.run(TraceIdExampleApplication::class.java, *args)
}
