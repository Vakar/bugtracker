package space.vakar.bugtracker.project;

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
import space.vakar.bugtracker.bug.BugEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("PROJECT FETCH REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class ProjectRepositoryFetchTest {

  @Autowired private ProjectRepository repository;

  private final ProjectEntity aliceProject = EntityTemplates.ALICE_PROJECT.toBuilder().build();
  private final BugEntity aliceBug = EntityTemplates.ALICE_BUG.toBuilder().build();

  @Test
  @DisplayName("Retrieve all bugs belong to project.")
  @DatabaseSetup("classpath:dbunit/project/initialStateForFetchTest.xml")
  void fetchBugs() {
    ProjectEntity project = repository.getOne(aliceProject.getId());
    List<BugEntity> bugs = project.getBugs();
    assertThat(bugs).contains(aliceBug);
  }
}
