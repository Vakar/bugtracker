package space.vakar.bugtracker.user;

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
import space.vakar.bugtracker.invitation.Acceptance;
import space.vakar.bugtracker.invitation.InvitationEntity;
import space.vakar.bugtracker.project.ProjectEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("APPLICATION USER FETCH REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class AppUserRepositoryFetchTest {

  @Autowired private AppUserRepository repository;

  private final AppUserEntity alice = EntityTemplates.ALICE.toBuilder().build();
  private final AppUserEntity bob = EntityTemplates.BOB.toBuilder().build();

  private final ProjectEntity aliceProject = EntityTemplates.ALICE_PROJECT.toBuilder().build();
  private final ProjectEntity bobProject = EntityTemplates.BOB_PROJECT.toBuilder().build();

  private final InvitationEntity invitationFromAliceToBob =
      InvitationEntity.builder()
          .fromUser(alice)
          .toUser(bob)
          .project(aliceProject)
          .acceptance(Acceptance.NEW)
          .build();
  private final InvitationEntity invitationFromBobToAlice =
      InvitationEntity.builder()
          .fromUser(bob)
          .toUser(alice)
          .project(bobProject)
          .acceptance(Acceptance.ACCEPT)
          .build();

  @Test
  @DisplayName("Retrieve all projects that was created by user.")
  @DatabaseSetup("classpath:dbunit/user/initialStateForFetchTest.xml")
  void fetchProjects() {
    AppUserEntity user = repository.getOne(alice.getId());
    List<ProjectEntity> projects = user.getProjects();
    assertThat(projects).contains(aliceProject);
  }

  @Test
  @DisplayName("Retrieve all invitations from user.")
  @DatabaseSetup("classpath:dbunit/user/initialStateForFetchTest.xml")
  void fetchInvitationsFromUser() {
    AppUserEntity user = repository.getOne(alice.getId());
    List<InvitationEntity> invitationsFromAlice = user.getInvitationsFromUser();
    assertThat(invitationsFromAlice).contains(invitationFromAliceToBob);
  }

  @Test
  @DisplayName("Retrieve all invitations to user.")
  @DatabaseSetup("classpath:dbunit/user/initialStateForFetchTest.xml")
  void fetchInvitationsToUser() {
    AppUserEntity user = repository.getOne(alice.getId());
    List<InvitationEntity> invitationsToAlice = user.getInvitationsToUser();
    assertThat(invitationsToAlice).contains(invitationFromBobToAlice);
  }
}
