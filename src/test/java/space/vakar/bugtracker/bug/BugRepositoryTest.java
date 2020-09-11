package space.vakar.bugtracker.bug;

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
@DisplayName("BUG REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class BugRepositoryTest {

  @Autowired private BugRepository repository;

  private final BugEntity aliceBug = EntityTemplates.ALICE_BUG.toBuilder().build();
  private final BugEntity bobBug = EntityTemplates.BOB_BUG.toBuilder().build();

  @Test
  @DisplayName("Spring injected components should not be null.")
  void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  @DisplayName("Retrieve all bugs from database.")
  @DatabaseSetup("classpath:dbunit/bug/initialState.xml")
  void findAll() {
    List<BugEntity> bugs = repository.findAll();
    assertThat(bugs).contains(aliceBug, bobBug);
  }

  @Test
  @DisplayName("Retrieve one bug from database by id.")
  @DatabaseSetup("classpath:dbunit/bug/initialState.xml")
  void findById() {
    BugEntity bug = repository.getOne(bobBug.getId());
    assertThat(bug).isEqualTo(bobBug);
  }

  @Test
  @DisplayName("Save bug to database.")
  @DatabaseSetup("classpath:dbunit/bug/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/bug/afterSave.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void save() {
    BugEntity newBug = EntityTemplates.NEW_BUG.toBuilder().build();
    repository.saveAndFlush(newBug);
  }

  @Test
  @DisplayName("Update bug in database.")
  @DatabaseSetup("classpath:dbunit/bug/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/bug/afterUpdate.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void update() {
    bobBug.setFixStatus(FixStatus.FIXED);
    repository.saveAndFlush(bobBug);
  }

  @Test
  @DisplayName("Delete bug by id from database.")
  @DatabaseSetup("classpath:dbunit/bug/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/bug/afterDelete.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void deleteById() {
    repository.deleteById(bobBug.getId());
    repository.flush();
  }
}
