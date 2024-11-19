package ru.lebedev.reminder.integration.database.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lebedev.reminder.TestData.*;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.lebedev.reminder.database.repository.UserRepository;
import ru.lebedev.reminder.integration.IntegrationTestBase;

@RequiredArgsConstructor
class UserRepositoryIT extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void findByUsername() {
        var maybeUser = userRepository.findByUsername(USERNAME);
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> {
            assertThat(user.getId()).isEqualTo(TEST_USER.getId());
            assertThat(user.getUsername()).isEqualTo(TEST_USER.getUsername());
            assertThat(user.getTelegram()).isEqualTo(TEST_USER.getTelegram());
            assertThat(user.getUsername()).isEqualTo(USERNAME);
        });

    }

    @Test
    void findByTelegram() {
        var maybeUser = userRepository.findByTelegram(TELEGRAM_USERNAME);
        assertThat(maybeUser).isNotEmpty();
        maybeUser.ifPresent(user -> {
            assertThat(user.getId()).isEqualTo(TEST_USER.getId());
            assertThat(user.getUsername()).isEqualTo(TEST_USER.getUsername());
            assertThat(user.getTelegram()).isEqualTo(TEST_USER.getTelegram());
            assertThat(user.getUsername()).isEqualTo(USERNAME);
        });
    }

    @Test
    void findByBadUsername() {
        var maybeUser = userRepository.findByUsername(BAD_USERNAME);
        assertThat(maybeUser).isEmpty();
    }

    @Test
    void findByBadTelegram() {
        var maybeUser = userRepository.findByTelegram(BAD_TELEGRAM_USERNAME);
        assertThat(maybeUser).isEmpty();
    }

}
