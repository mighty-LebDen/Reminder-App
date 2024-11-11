package ru.lebedev.reminder.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ru.lebedev.reminder.dto.UserCreateEditDto;
import ru.lebedev.reminder.service.UserService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2UserServiceImpl implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userServiceImpl;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String email = oAuth2User.getAttribute("username");
        log.info(email);
        var userReadDto = userServiceImpl.findByUsername(email);

        if (userReadDto == null) {
            var newUser = UserCreateEditDto.builder()
                                           .username(email)
                                           .build();
            log.info("new user created, {}", userServiceImpl.create(newUser));

        } else {
            log.info("user was in bd, {}", email);

        }
        return oAuth2User;
    }

}
