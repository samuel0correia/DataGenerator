import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetAddress
import java.net.Socket

//throws?
class DataGenerator(ipAddress: InetAddress, port: Int) {
    init {
        val socket = Socket(ipAddress, port)
        val output = ObjectOutputStream(socket.getOutputStream())
        val input = ObjectInputStream(socket.getInputStream())

        while(!socket.isClosed){
            output.write(ByteArray(0))
            Thread.sleep(5000)
        }
    }
}

fun main(args: Array<String>) {
    if (args.size < 2)
        throw IllegalArgumentException("Invalid arguments!");
    if(args[1].toInt() < 0)
        throw IllegalArgumentException("Invalid port number!");

    val ipAddress = InetAddress.getByName(args.first())
    val port = args[1].toInt()

    DataGenerator(ipAddress, port)
}


