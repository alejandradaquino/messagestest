package message.sender.writer

import com.fasterxml.jackson.databind.ObjectMapper
import message.sender.infrastructure.RabbitMessageSender
import message.sender.model.Person
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PersonMessageSenderTest {
    @Mock
    private lateinit var rabbitMessageSender: RabbitMessageSender

    @Mock
    private lateinit var mapper: ObjectMapper

    @InjectMocks
    private lateinit var personMessageSender: PersonMessageSender

    @Captor
    lateinit var messageCaptor: ArgumentCaptor<String>

    private val json = "{\"some json\":\"some json\"}"

    @Test
    fun `when send a person then serialize it and send it to rabbit sender`() {
        doReturn(json).`when`(mapper).writeValueAsString(Mockito.any(Person::class.java))

        personMessageSender.send(Person("name", 2 , "email@email.com"))

        verify(rabbitMessageSender).send(capture(messageCaptor))
        assertThat(messageCaptor.value).isEqualTo(json)
    }
}

fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()