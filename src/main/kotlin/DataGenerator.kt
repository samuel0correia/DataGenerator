import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.InetAddress
import java.net.Socket

//throws?
class DataGenerator(val targetAddress: InetAddress, val targetPort: Int) {
    @Throws(IOException::class)
    fun connectAndSendData(){
        val socket = Socket(targetAddress, targetPort)
        val output = ObjectOutputStream(socket.getOutputStream())
        //val input = ObjectInputStream(socket.getInputStream())

        while(!socket.isClosed){
            output.writeInt((Math.random()*100).toInt())
            Thread.sleep(5000)
        }
    }
}

fun main(args: Array<String>) {
    if (args.size < 2)
        throw IllegalArgumentException("Invalid arguments!")
    if(args[1].toInt() < 0)
        throw IllegalArgumentException("Invalid port number!")

    val ipAddress = InetAddress.getByName(args.first())
    val port = args[1].toInt()

    val generator = DataGenerator(ipAddress, port)

    try {
        generator.connectAndSendData()
    } catch (e: IOException) {
        throw ConnectException("Connection failed!")
    }
}