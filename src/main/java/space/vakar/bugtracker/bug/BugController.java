package space.vakar.bugtracker.bug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.vakar.bugtracker.project.ProjectEntity;
import space.vakar.bugtracker.project.ProjectRepository;
import space.vakar.bugtracker.user.AppUserEntity;
import space.vakar.bugtracker.user.AppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/users/{userId}/projects/{projectId}/**")
public class BugController {

  private final AppUserRepository userRepository;
  private final BugRepository bugRepository;
  private final ProjectRepository projectRepository;

  @Autowired
  public BugController(
      AppUserRepository userRepository,
      BugRepository bugRepository,
      ProjectRepository projectRepository) {
    this.userRepository = userRepository;
    this.bugRepository = bugRepository;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/bugs")
  public List<BugEntity> findAll(@PathVariable int projectId) {
    ProjectEntity project = projectRepository.findById(projectId).orElse(ProjectEntity.EMPTY);
    return project.getBugs();
  }

  @GetMapping("/bugs/{bugId}")
  public BugEntity find(@PathVariable int bugId) {
    return bugRepository.findById(bugId).orElse(BugEntity.EMPTY);
  }

  @PostMapping("/bugs")
  public BugEntity save(
      @PathVariable int userId, @PathVariable int projectId, @RequestBody BugDto bugDto) {
    AppUserEntity user = userRepository.getOne(userId);
    ProjectEntity project = projectRepository.getOne(projectId);
    BugEntity bug = BugMapper.from(bugDto);
    bug.setCreator(user);
    bug.setProject(project);
    return bugRepository.save(bug);
  }

  @PutMapping("/bugs")
  public BugEntity update(@RequestBody BugDto bugDto) {
    BugEntity bugEntity = bugRepository.getOne(bugDto.getId());
    BugMapper.merge(bugEntity, bugDto);
    return bugRepository.save(bugEntity);
  }

  @PutMapping("/bugs/{bugId}/fixer/{fixerId}")
  public BugEntity setFixer(@PathVariable int bugId, @PathVariable int fixerId) {
    BugEntity bug = bugRepository.getOne(bugId);
    AppUserEntity user = userRepository.getOne(fixerId);
    bug.setFixer(user);
    return bugRepository.save(bug);
  }

  @DeleteMapping("/bugs/{bugId}")
  public void delete(@PathVariable int bugId) {
    bugRepository.deleteById(bugId);
  }
}
