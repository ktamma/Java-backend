package item;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class PasswordEncoderTest {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        String hash = encoder.encode("user");

        System.out.println(hash);
    }


}
