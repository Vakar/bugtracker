package space.vakar.bugtracker.user;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import space.vakar.bugtracker.DbUnitConfiguration;
import space.vakar.bugtracker.EntityTemplates;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("APPLICATION USER REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class AppUserRepositoryTest {

  @Autowired private AppUserRepository repository;

  private final AppUserEntity alice = EntityTemplates.ALICE.toBuilder().build();
  private final AppUserEntity bob = EntityTemplates.BOB.toBuilder().build();

  @Test
  @DisplayName("Spring injected components should not be null.")
  void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  @DisplayName("Retrieve all application users from database.")
  @DatabaseSetup("classpath:dbunit/user/initialState.xml")
  void findAll() {
    List<AppUserEntity> users = repository.findAll();
    assertThat(users).contains(alice, bob);
  }

  @Test
  @DisplayName("Retrieve one application user from database by id.")
  @DatabaseSetup("classpath:dbunit/user/initialState.xml")
  void findById() {
    AppUserEntity user = repository.getOne(bob.getId());
    assertThat(user).isEqualTo(bob);
  }

  @Test
  @DisplayName("Save application user to database.")
  @DatabaseSetup("classpath:dbunit/user/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/user/afterSave.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void save() { // TODO: ENTITY ID SHOULD BE 3 BUT IT 4 WHEN MVN TEST
    AppUserEntity carol = EntityTemplates.CAROL.toBuilder().build();
    carol.setId(0);
    repository.saveAndFlush(carol);
  }

  @Test
  @DisplayName("Update application user in database.")
  @DatabaseSetup("classpath:dbunit/user/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/user/afterUpdate.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void update() {
    alice.setName(bob.getName());
    repository.saveAndFlush(alice);
  }

  @Test
  @DisplayName("Delete application user by id from database.")
  @DatabaseSetup("classpath:dbunit/user/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/user/afterDelete.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void deleteById() {
    repository.deleteById(alice.getId());
    repository.flush();
  }
}
