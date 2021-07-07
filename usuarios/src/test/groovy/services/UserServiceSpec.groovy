package services

import com.globallogic.usuarios.dto.PhoneRequestDTO
import com.globallogic.usuarios.dto.UserRequestDTO
import com.globallogic.usuarios.dto.UserResponseDTO
import com.globallogic.usuarios.entities.Phone
import com.globallogic.usuarios.entities.User
import com.globallogic.usuarios.repository.UserRepository
import com.globallogic.usuarios.security.JwtTokenUtil
import com.globallogic.usuarios.services.UserServiceImpl
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceSpec extends Specification {
    private UserServiceImpl userService
    private UserRepository userRepository
    private JwtTokenUtil jwtTokenUtil

    def setup() {
        this.userRepository = Stub(UserRepository.class)
        this.jwtTokenUtil = Stub(JwtTokenUtil.class)
        this.userService= new UserServiceImpl(userRepository,jwtTokenUtil)
    }
    def "validate user saved"() {


        given: "se ingresan los datos del usuario a registrar"

        List <Phone> phonesEntity  = new ArrayList<Phone>()
        phonesEntity.add(Phone.builder().countrycode("56").citycode("9").number("93091060").build())
        phonesEntity.add(Phone.builder().countrycode("56").citycode("9").number("34349898").build())

        this.userRepository.save(_)>>{ User.builder().
                id("").
                name("Jose").
                email("joseprieto1858@gmail.com").
                password("J0s3Pr13t0").
                phones(phonesEntity).
                dateOfCreation(LocalDateTime.now()).
                dateOfModified(LocalDateTime.now()).
                lastLogin(LocalDateTime.now()).
                token("123ljhlk2ij13").
                isActive(true).
                build()}

        this.jwtTokenUtil.generateToken(_)>>{"123ljhlk2ij13"}

        List <PhoneRequestDTO> phones  = new ArrayList<PhoneRequestDTO>()
        phones.add(PhoneRequestDTO.builder().countrycode("56").citycode("9").number("93091060").build())
        phones.add(PhoneRequestDTO.builder().countrycode("56").citycode("9").number("34349898").build())

        UserRequestDTO userRequestDTO = UserRequestDTO.builder().
                name("Jose").
                email("joseprieto1858@gmail.com").
                password("J0s3Pr13t0").
                phones(phones).
                build()

        when: "se invoca al registro del usuario"
        UserResponseDTO responseDTO=this.userService.save(userRequestDTO)

        then: "se evalua la respuesta del registro"
        responseDTO.getId()!=null
        responseDTO.getToken()!=null
        responseDTO.getName() == userRequestDTO.getName()
        responseDTO.getDateOfCreation()!=null
        responseDTO.getDateOfModified()!=null
        responseDTO.getLastLogin()!=null
        responseDTO.isActive()


    }
}
