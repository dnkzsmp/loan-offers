package ru.generator.loanoffers.registration;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtUserService {
    private final JwtUserRepository jwtUserRepository;

    public JwtUser save(JwtUser user) {
        return jwtUserRepository.save(user);
    }

    public Optional<JwtUser> findJwtUserByEmail(String email) {
        return jwtUserRepository.findJwtUserByEmail(email);
    }

    public JwtUser getJwtUserByEmail(String email) {
        return jwtUserRepository.findJwtUserByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException("Пользователь с почтой " + email + " не найден")
                );
    }

    public JwtUser getJwtUserByUsername(String username) {
        return jwtUserRepository.findJwtUserByUsername(username)
                .orElseThrow(
                        () -> new EntityNotFoundException("Пользователь с именем " + username + " не найден")
                );
    }
}
