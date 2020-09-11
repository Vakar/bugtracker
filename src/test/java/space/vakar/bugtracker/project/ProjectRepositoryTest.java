package space.vakar.bugtracker.project;

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
import space.vakar.bugtracker.user.AppUserEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("PROJECT REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class ProjectRepositoryTest {

  @Autowired private ProjectRepository repository;

  private final AppUserEntity bob = EntityTemplates.BOB.toBuilder().build();
  private final ProjectEntity aliceProject = EntityTemplates.ALICE_PROJECT.toBuilder().build();
  private final ProjectEntity bobProject = EntityTemplates.BOB_PROJECT.toBuilder().build();

  @Test
  @DisplayName("Spring injected components should not be null.")
  void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  @DisplayName("Retrieve all projects from database.")
  @DatabaseSetup("classpath:dbunit/project/initialState.xml")
  void findAll() {
    List<ProjectEntity> projects = repository.findAll();
    assertThat(projects).contains(aliceProject, bobProject);
  }

  @Test
  @DisplayName("Retrieve one project from database by id.")
  @DatabaseSetup("classpath:dbunit/project/initialState.xml")
  void findById() {
    ProjectEntity project = repository.getOne(bobProject.getId());
    assertThat(project).isEqualTo(bobProject);
  }

  @Test
  @DisplayName("Save project to database.")
  @DatabaseSetup("classpath:dbunit/project/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/project/afterSave.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void save() {
    ProjectEntity carolProject =
        new ProjectEntity(
            0, "bob's literature project", "literature project", bob, Collections.emptyList());
    repository.saveAndFlush(carolProject);
  }

  @Test
  @DisplayName("Update project in database.")
  @DatabaseSetup("classpath:dbunit/project/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/project/afterUpdate.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void update() {
    bobProject.setTitle("bob's literature project");
    bobProject.setDescription("literature project");
    repository.saveAndFlush(bobProject);
  }

  @Test
  @DisplayName("Delete project by id from database.")
  @DatabaseSetup("classpath:dbunit/project/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/project/afterDelete.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void deleteById() {
    repository.delete(bobProject);
    repository.flush();
  }
}
