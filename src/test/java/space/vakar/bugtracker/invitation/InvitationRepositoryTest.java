package space.vakar.bugtracker.invitation;

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
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(DbUnitConfiguration.class)
@DisplayName("INVITATION REPOSITORY TEST")
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class
})
class InvitationRepositoryTest {

  @Autowired private InvitationRepository repository;

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
  @DisplayName("Spring injected components should not be null.")
  void injectedComponentsAreNotNull() {
    assertThat(repository).isNotNull();
  }

  @Test
  @DisplayName("Retrieve all invitations from database.")
  @DatabaseSetup("classpath:dbunit/invitation/initialState.xml")
  void findAll() {
    List<InvitationEntity> invitations = repository.findAll();
    assertThat(invitations).contains(invitationFromAliceToBob, invitationFromBobToAlice);
  }

  @Test
  @DisplayName("Retrieve one invitation from database by id.")
  @DatabaseSetup("classpath:dbunit/invitation/initialState.xml")
  void findById() {
    InvitationEntityPK id =
        InvitationEntityPK.builder()
            .fromUser(alice.getId())
            .toUser(bob.getId())
            .project(aliceProject.getId())
            .build();
    InvitationEntity invitation = repository.getOne(id);
    assertThat(invitation).isEqualTo(invitationFromAliceToBob);
  }

  @Test
  @DisplayName("Save invitation to database.")
  @DatabaseSetup("classpath:dbunit/invitation/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/invitation/afterSave.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void save() {
    AppUserEntity carol = EntityTemplates.CAROL.toBuilder().build();
    InvitationEntity invitationFromAliceToCarol =
        InvitationEntity.builder()
            .fromUser(alice)
            .toUser(carol)
            .project(aliceProject)
            .acceptance(Acceptance.NEW)
            .build();
    repository.saveAndFlush(invitationFromAliceToCarol);
  }

  @Test
  @DisplayName("Update invitation in database.")
  @DatabaseSetup("classpath:dbunit/invitation/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/invitation/afterUpdate.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void update() {
    invitationFromAliceToBob.setAcceptance(Acceptance.DENY);
    repository.saveAndFlush(invitationFromAliceToBob);
  }

  @Test
  @DisplayName("Delete invitation by id from database.")
  @DatabaseSetup("classpath:dbunit/invitation/initialState.xml")
  @ExpectedDatabase(
      value = "classpath:dbunit/invitation/afterDelete.xml",
      assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
  void delete() {
    InvitationEntityPK id =
        InvitationEntityPK.builder()
            .fromUser(bob.getId())
            .toUser(alice.getId())
            .project(bobProject.getId())
            .build();
    repository.deleteById(id);
    repository.flush();
  }
}
