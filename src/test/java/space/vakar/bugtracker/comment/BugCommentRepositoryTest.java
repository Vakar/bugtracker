package space.vakar.bugtracker.comment;

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
@DisplayName("BUG COMMENT REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class BugCommentRepositoryTest {

  @Autowired private BugCommentRepository repository;

  private final BugCommentEntity aliceComment = EntityTemplates.ALICE_COMMENT.toBuilder().build();
  private final BugCommentEntity bobComment = EntityTemplates.BOB_COMMENT.toBuilder().build();

  @Test
  @DisplayName("Spring injected components should not be null.")
  void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  @DisplayName("Retrieve all comments from database.")
  @DatabaseSetup("classpath:dbunit/comment/initialState.xml")
  void findAll() {
    List<BugCommentEntity> comments = repository.findAll();
    assertThat(comments).contains(aliceComment, bobComment);
  }

  @Test
  @DisplayName("Retrieve one comment from database by id.")
  @DatabaseSetup("classpath:dbunit/comment/initialState.xml")
  void findById() {
    BugCommentEntity comment = repository.getOne(bobComment.getId());
    assertThat(comment).isEqualTo(bobComment);
  }

  @Test
  @DisplayName("Save comment to database.")
  @DatabaseSetup("classpath:dbunit/comment/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/comment/afterSave.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void save() {
    BugCommentEntity comment = EntityTemplates.NEW_COMMENT.toBuilder().build();
    repository.saveAndFlush(comment);
  }

  @Test
  @DisplayName("Update comment in database.")
  @DatabaseSetup("classpath:dbunit/comment/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/comment/afterUpdate.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void update() {
    bobComment.setContent("bob change comment bug");
    repository.saveAndFlush(bobComment);
  }

  @Test
  @DisplayName("Delete comment by id from database.")
  @DatabaseSetup("classpath:dbunit/comment/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/comment/afterDelete.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void deleteById() {
    repository.deleteById(bobComment.getId());
    repository.flush();
  }
}
