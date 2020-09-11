package space.vakar.bugtracker.bug;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import space.vakar.bugtracker.DbUnitConfiguration;
import space.vakar.bugtracker.EntityTemplates;
import space.vakar.bugtracker.comment.BugCommentEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("BUG FETCH REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class BugRepositoryFetchTest {

  @Autowired private BugRepository repository;

  private final BugEntity aliceBug = EntityTemplates.ALICE_BUG.toBuilder().build();
  private final BugCommentEntity aliceComment = EntityTemplates.ALICE_COMMENT.toBuilder().build();

  @Test
  @DisplayName("Retrieve all comments belong to bug.")
  @DatabaseSetup("classpath:dbunit/bug/initialStateForFetchTest.xml")
  void fetchComments() {
    BugEntity bug = repository.getOne(aliceBug.getId());
    List<BugCommentEntity> comments = bug.getComments();
    assertThat(comments).contains(aliceComment);
  }
}
