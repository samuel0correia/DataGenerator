import java.io.IOException
import java.net.ConnectException
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class DataGeneratorServer(private val serverPort: Int): Thread() {

    val generator = DataGenerator()

    @Throws(IOException::class)
    override fun run() {

        val serverSocket = ServerSocket(serverPort)

        while(!serverSocket.isClosed){
            val clientSocket = serverSocket.accept()
            ClientHandler(clientSocket).start()
            println("Client connected: ${clientSocket.inetAddress.toString().drop(1)}:${clientSocket.port}")
        }
    }


    inner class ClientHandler(private val client: Socket): Thread() {
        override fun run() {
            val output = client.getOutputStream()
            while(!client.isClosed){
                output.write(generator.value)
                println("Sent message to client")
                sleep(5000)
            }
        }
    }
}

fun main(args: Array<String>) {
    if (args.isEmpty())
        throw IllegalArgumentException("No arguments provided!")
    if(args.first().toInt() < 0)
        throw IllegalArgumentException("Invalid port number!")

    val ipAddress = InetAddress.getLocalHost()
    val port = args.first().toInt()

    val generator = DataGeneratorServer(port)

    try {
        generator.start()
    } catch (e: IOException) {
        throw ConnectException("Connection failed!")
    }

    println("Server running at: ws://${ipAddress.hostAddress}:$port")
}