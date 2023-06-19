class DataGenerator: Thread() {
    var value = 0
        private set

    init {
        start()
    }

    override fun run() {
        while(this.isAlive){
            value = (Math.random()*100).toInt()
            sleep(1000)
        }
    }
}