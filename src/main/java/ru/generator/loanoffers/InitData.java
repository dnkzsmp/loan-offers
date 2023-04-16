package ru.generator.loanoffers;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.generator.loanoffers.database.Offer;
import ru.generator.loanoffers.enums.Answer;
import ru.generator.loanoffers.service.OfferService;
import ru.generator.loanoffers.registration.JwtRole;
import ru.generator.loanoffers.registration.JwtUser;
import ru.generator.loanoffers.registration.JwtUserService;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {
    private final JwtUserService jwtUserService;
    private final PasswordEncoder passwordEncoder;
    private final OfferService offerService;

    @Override
    public void run(String... args) {
        if (jwtUserService.findJwtUserByEmail("admin@test.com").isEmpty()) {
            JwtUser user = jwtUserService.save(JwtUser.builder()
                    .username("Admin")
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("test123"))
                    .role(Set.of(JwtRole.ROLE_ADMIN, JwtRole.ROLE_USER))
                    .build());
            user.setEnabled(true);
            jwtUserService.save(user);
        }

        if (jwtUserService.findJwtUserByEmail("user@test.com").isEmpty()) {
            JwtUser user = jwtUserService.save(JwtUser.builder()
                    .username("User")
                    .email("user@test.com")
                    .password(passwordEncoder.encode("test123"))
                    .role(Set.of(JwtRole.ROLE_USER))
                    .build());
            user.setEnabled(true);
            jwtUserService.save(user);
        }

        offerService.generateNewOffer(Offer.builder()
                .title("Предложение 1")
                .content("Дефолтный кредит нада?")
                .build());

        offerService.generateNewOffer(Offer.builder()
                .title("Предложение 2")
                .content("А выгодный кредит нада?")
                .answer(Answer.YES)
                .build());
    }
}
