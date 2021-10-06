package message.sender.infrastructure

import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

class RabbitMessageSender(private val connectionName: String, private val queueName: String) {
    fun send(message: String) = ConnectionFactory().newConnection(connectionName).use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare(queueName, false, false, false, null)
            channel.basicPublish(
                "",
                queueName,
                null,
                message.toByteArray(StandardCharsets.UTF_8)
            )
            println(" [x] Sent '$message'")
        }
    }
}