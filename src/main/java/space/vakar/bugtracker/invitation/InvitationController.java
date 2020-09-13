package space.vakar.bugtracker.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.project.ProjectRepository;
import space.vakar.bugtracker.user.AppUserEntity;
import space.vakar.bugtracker.user.AppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/projects/{projectId}/**")
public class InvitationController {

  private final InvitationRepository invitationRepository;
  private final AppUserRepository userRepository;
  private final ProjectRepository projectRepository;

  @Autowired
  public InvitationController(
      InvitationRepository invitationRepository,
      AppUserRepository userRepository,
      ProjectRepository projectRepository) {
    this.invitationRepository = invitationRepository;
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/invitations/toUser/{userId}")
  public List<InvitationEntity> invitationsToUser(@PathVariable int userId) {
    AppUserEntity user = userRepository.findById(userId).orElse(AppUserEntity.EMPTY);
    return user.getInvitationsToUser();
  }

  @GetMapping("/invitations/fromUser/{userId}")
  public List<InvitationEntity> invitationsFromUser(@PathVariable int userId) {
    AppUserEntity user = userRepository.findById(userId).orElse(AppUserEntity.EMPTY);
    return user.getInvitationsFromUser();
  }

  @GetMapping("/invitations/from/{fromUserId}/to/{toUserId}/project/{projectId}")
  public InvitationEntity find(
      @PathVariable int fromUserId, @PathVariable int toUserId, @PathVariable int projectId) {
    InvitationEntityPK id =
        InvitationEntityPK.builder()
            .fromUser(fromUserId)
            .toUser(toUserId)
            .project(projectId)
            .build();
    return invitationRepository.findById(id).orElse(InvitationEntity.EMPTY);
  }

  @PostMapping("/invitations/from/{fromUserId}/to/{toUserId}/project/{projectId}")
  public InvitationEntity save(
      @PathVariable int fromUserId, @PathVariable int toUserId, @PathVariable int projectId) {
    AppUserEntity fromUser = userRepository.getOne(fromUserId);
    AppUserEntity toUser = userRepository.getOne(toUserId);
    ProjectEntity project = projectRepository.getOne(projectId);
    InvitationEntity invitation =
        InvitationEntity.builder()
            .fromUser(fromUser)
            .toUser(toUser)
            .project(project)
            .acceptance(Acceptance.NEW)
            .build();
    return invitationRepository.save(invitation);
  }

  @PutMapping("/invitations/from/{fromUserId}/to/{toUserId}/project/{projectId}")
  public InvitationEntity save(
      @PathVariable int fromUserId,
      @PathVariable int toUserId,
      @PathVariable int projectId,
      @RequestBody InvitationDto dto) {
    InvitationEntity invitation = InvitationMapper.from(dto);
    AppUserEntity fromUser = userRepository.getOne(fromUserId);
    AppUserEntity toUser = userRepository.getOne(toUserId);
    ProjectEntity project = projectRepository.getOne(projectId);
    invitation.setFromUser(fromUser);
    invitation.setToUser(toUser);
    invitation.setProject(project);
    return invitationRepository.save(invitation);
  }

  @DeleteMapping("/invitations/from/{fromUserId}/to/{toUserId}/project/{projectId}")
  public void delete(
      @PathVariable int fromUserId, @PathVariable int toUserId, @PathVariable int projectId) {
    InvitationEntityPK id =
        InvitationEntityPK.builder()
            .fromUser(fromUserId)
            .toUser(toUserId)
            .project(projectId)
            .build();
    invitationRepository.deleteById(id);
  }
}
