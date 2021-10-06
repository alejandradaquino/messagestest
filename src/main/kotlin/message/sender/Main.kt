package message.sender

import com.google.inject.Guice
import com.google.inject.Injector
import com.rabbitmq.client.*
import message.sender.guice.MessageSenderModule
import message.sender.reader.PeopleReader
import message.sender.writer.PersonMessageSender
import java.nio.charset.StandardCharsets

const val QUEUE_NAME: String = "new_queue"

fun main(args: Array<String>) {

    val path = args[0]

    val injector = Guice.createInjector(MessageSenderModule())

    val personMessageSender = injector.getInstance(PersonMessageSender::class.java)
    val peopleReader = injector.getInstance(PeopleReader::class.java)

    peopleReader.read(path).forEach { personMessageSender.send(it) }
}