package message.sender.writer

import com.fasterxml.jackson.databind.ObjectMapper
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import message.sender.QUEUE_NAME
import message.sender.infrastructure.RabbitMessageSender
import message.sender.model.Person
import java.nio.charset.StandardCharsets

class PersonMessageSender(private val mapper: ObjectMapper, private val rabbitMessageSender: RabbitMessageSender) {
    fun send(person: Person) {
        val json = mapper.writeValueAsString(person)
        rabbitMessageSender.send(json)
    }
}