
import com.rabbitmq.client.*
import java.nio.charset.StandardCharsets

const val QUEUE_NAME: String ="test_queue"

fun main(args: Array<String>) {
    val factory = ConnectionFactory()
    factory.newConnection("amqp://guest:guest@localhost:5672/").use { connection ->
        val createChannel: Channel = connection.createChannel()
        createChannel.use { channel ->
            channel.queueDeclare(QUEUE_NAME, false, false, false, null)
            for (i in 1 .. 100){

                val message = "Hello World!"
                channel.basicPublish(
                    "",
                    QUEUE_NAME,
                    null,
                    (message + i).toByteArray(StandardCharsets.UTF_8)
                )
                println(" [x] Sent '$message' $i")
            }

        }
    }
    val connection = factory.newConnection("amqp://guest:guest@localhost:5672/")
    val channel = connection.createChannel()
    val consumerTag = "SimpleConsumer"

    channel.queueDeclare("test_queue", false, false, false, null)

    println("[$consumerTag] Waiting for messages...")
    val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println("[$consumerTag] Received message: '$message'")
    }
    val cancelCallback = CancelCallback { consumerTag: String? ->
        println("[$consumerTag] was canceled")
    }

    channel.basicConsume(QUEUE_NAME, true, consumerTag, deliverCallback, cancelCallback)

}