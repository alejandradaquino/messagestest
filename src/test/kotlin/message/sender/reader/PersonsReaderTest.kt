package message.sender.reader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PersonsReaderTest{

    private val objectMapper = ObjectMapper().registerModule(KotlinModule())

    @Test
     fun `when deserializing file then people is returned`(){
         val path = "src/people.json"

         val people = PeopleReader(objectMapper).read(path)

         assertThat(people).hasSize(2)
         assertThat(people[0].name).isEqualTo("Christine")
         assertThat(people[0].id).isEqualTo(1)
         assertThat(people[0].email).isEqualTo("christine@nexthink.com")
     }
 }