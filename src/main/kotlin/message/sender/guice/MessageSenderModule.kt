package message.sender.guice

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import message.sender.infrastructure.RabbitMessageSender
import message.sender.reader.PeopleReader
import message.sender.writer.PersonMessageSender

class MessageSenderModule : AbstractModule() {

    @Provides
    @Singleton
    fun mapper() = ObjectMapper().registerModule(KotlinModule())

    @Provides
    @Singleton
    fun rabbitMessageSender() = RabbitMessageSender("amqp://guest:guest@localhost:5672/", "person_queue")

    @Provides
    @Singleton
    fun peopleReader(mapper: ObjectMapper) = PeopleReader(mapper)

    @Provides
    @Singleton
    fun personMessageSender(mapper: ObjectMapper, rabbitMessageSender: RabbitMessageSender) =
        PersonMessageSender(mapper, rabbitMessageSender)
}