package message.sender.reader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import message.sender.model.Person
import java.nio.file.Paths

class PeopleReader(private val mapper: ObjectMapper){

    fun read(path: String): Array<Person> = mapper.readValue(
        Paths.get(path).toFile(),
        Array<Person>::class.java
    )
}