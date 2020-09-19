package space.vakar.bugtracker;

import space.vakar.bugtracker.bug.BugEntity;
import space.vakar.bugtracker.bug.FixStatus;
import space.vakar.bugtracker.comment.BugCommentEntity;
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.user.AppUserEntity;

import java.util.Collections;

public class EntityTemplates {

  public static final AppUserEntity ALICE =
      AppUserEntity.builder()
          .id(1)
          .name("Alice")
          .facebookId(1111111111111111L)
          .projects(Collections.emptyList())
          .invitationsFromUser(Collections.emptyList())
          .invitationsToUser(Collections.emptyList())
          .build();
  public static final AppUserEntity BOB =
      AppUserEntity.builder()
          .id(2)
          .name("Bob")
          .facebookId(2222222222222222L)
          .projects(Collections.emptyList())
          .invitationsFromUser(Collections.emptyList())
          .invitationsToUser(Collections.emptyList())
          .build();
  public static final AppUserEntity CAROL =
      AppUserEntity.builder()
          .id(3)
          .name("Carol")
          .facebookId(3333333333333333L)
          .projects(Collections.emptyList())
          .invitationsFromUser(Collections.emptyList())
          .invitationsToUser(Collections.emptyList())
          .build();

  public static final ProjectEntity ALICE_PROJECT =
      new ProjectEntity(1, "alice's math project", "math project", ALICE, Collections.emptyList());
  public static final ProjectEntity BOB_PROJECT =
      new ProjectEntity(
          2, "bob's physics project", "physics project", BOB, Collections.emptyList());

  public static final BugEntity ALICE_BUG =
      BugEntity.builder()
          .id(1)
          .title("alice's bug")
          .stepsToReproduce("steps to reproduce")
          .expectedResults("expected results")
          .actualResults("actual results")
          .creator(ALICE)
          .fixer(BOB)
          .project(ALICE_PROJECT)
          .comments(Collections.emptyList())
          .fixStatus(FixStatus.NEW)
          .build();
  public static final BugEntity BOB_BUG =
      BugEntity.builder()
          .id(2)
          .title("bob's bug")
          .stepsToReproduce("steps to reproduce")
          .expectedResults("expected results")
          .actualResults("actual results")
          .creator(BOB)
          .fixer(ALICE)
          .project(ALICE_PROJECT)
          .comments(Collections.emptyList())
          .fixStatus(FixStatus.IN_WORK)
          .build();
  public static final BugEntity NEW_BUG =
      BugEntity.builder()
          .id(0)
          .title("second bob's bug")
          .stepsToReproduce("steps to reproduce")
          .expectedResults("expected results")
          .actualResults("actual results")
          .creator(BOB)
          .fixer(ALICE)
          .project(ALICE_PROJECT)
          .comments(Collections.emptyList())
          .fixStatus(FixStatus.NEW)
          .build();

  public static final BugCommentEntity ALICE_COMMENT =
      BugCommentEntity.builder()
          .id(1)
          .content("alice comment bug")
          .creator(ALICE)
          .bug(ALICE_BUG)
          .build();
  public static final BugCommentEntity BOB_COMMENT =
      BugCommentEntity.builder()
          .id(2)
          .content("bob comment bug")
          .creator(BOB)
          .bug(ALICE_BUG)
          .build();
  public static final BugCommentEntity NEW_COMMENT =
      BugCommentEntity.builder()
          .id(0)
          .content("carol comment bug")
          .creator(CAROL)
          .bug(ALICE_BUG)
          .build();

  private EntityTemplates() {}
}
