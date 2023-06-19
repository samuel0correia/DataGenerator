import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.isActive
import java.net.InetAddress

val generator = DataGenerator()
const val port = 9999

fun main() {
    embeddedServer(Netty, port = port, module = Application::applicationModule).start(wait = true)
}

fun Application.applicationModule() {
    install(WebSockets) {
        //timeout = ofSeconds(15)
        println("Server: ws://${InetAddress.getLocalHost().hostAddress}:$port")
    }
    install(Routing)

    routing {
        webSocket("/data") {
            println("User Connected.")
            while(isActive){
                val value = generator.value
                send(value.toString())
                println("Sent message to client: $value")
                Thread.sleep(5000)
            }
        }
    }
}
