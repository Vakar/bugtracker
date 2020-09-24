package space.vakar.bugtracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.vakar.bugtracker.user.AppUserEntity;
import space.vakar.bugtracker.user.AppUserRepository;

import java.util.Objects;

@Configuration
@RestController
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String FACEBOOK_ID = "id";
  private static final String FACEBOOK_NAME = "name";

  private static final String APP_USER_ENTITY_ID_FIELD = "id";
  private static final String APP_USER_ENTITY_FACEBOOK_ID_FIELD = "facebookId";

  @Autowired private AppUserRepository userRepository;

  @GetMapping("/rest/user")
  public AppUserEntity user(@AuthenticationPrincipal OAuth2User auth2User) {
    String facebookIdStr = Objects.requireNonNull(auth2User.getAttribute(FACEBOOK_ID));
    long facebookIdLong = Long.parseLong(facebookIdStr);
    AppUserEntity user = AppUserEntity.builder().facebookId(facebookIdLong).build();
    ExampleMatcher userMatcher =
        ExampleMatcher.matching()
            .withIgnorePaths(APP_USER_ENTITY_ID_FIELD)
            .withMatcher(
                APP_USER_ENTITY_FACEBOOK_ID_FIELD, ExampleMatcher.GenericPropertyMatchers.exact());
    return userRepository.findOne(Example.of(user, userMatcher)).orElse(AppUserEntity.EMPTY);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests(a -> a.antMatchers("/rest/**").authenticated())
        .logout(l -> l.logoutSuccessUrl("/").permitAll())
        .exceptionHandling(
            e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
        .oauth2Login();
  }

  @Bean
  public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
    DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    return request -> {
      OAuth2User user = delegate.loadUser(request);
      saveUserIfNotExists(user);
      return user;
    };
  }

  void saveUserIfNotExists(OAuth2User auth2User) {
    String facebookName = auth2User.getAttribute(FACEBOOK_NAME);
    String facebookIdStr = Objects.requireNonNull(auth2User.getAttribute(FACEBOOK_ID));
    long facebookIdLong = Long.parseLong(facebookIdStr);
    AppUserEntity appUserEntity =
        AppUserEntity.builder().name(facebookName).facebookId(facebookIdLong).build();
    if (!isAppUserExists(appUserEntity)) {
      userRepository.save(appUserEntity);
    }
  }

  boolean isAppUserExists(AppUserEntity appUserEntity) {
    ExampleMatcher userMatcher =
        ExampleMatcher.matching()
            .withIgnorePaths(APP_USER_ENTITY_ID_FIELD)
            .withMatcher(
                APP_USER_ENTITY_FACEBOOK_ID_FIELD, ExampleMatcher.GenericPropertyMatchers.exact());
    return userRepository.exists(Example.of(appUserEntity, userMatcher));
  }
}
