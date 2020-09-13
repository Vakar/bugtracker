package space.vakar.bugtracker.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.vakar.bugtracker.user.AppUserEntity;
import space.vakar.bugtracker.user.AppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/**")
public class ProjectController {

  private final AppUserRepository userRepository;
  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectController(AppUserRepository userRepository, ProjectRepository projectRepository) {
    this.userRepository = userRepository;
    this.projectRepository = projectRepository;
  }

  @GetMapping("/projects")
  public List<ProjectEntity> findAll(@PathVariable int userId) {
    AppUserEntity user = userRepository.findById(userId).orElse(AppUserEntity.EMPTY);
    return user.getProjects();
  }

  @GetMapping("/projects/{projectId}")
  public ProjectEntity find(@PathVariable int projectId) {
    return projectRepository.findById(projectId).orElse(ProjectEntity.EMPTY);
  }

  @PostMapping("/projects")
  public ProjectEntity save(@PathVariable int userId, @RequestBody ProjectDto projectDto) {
    AppUserEntity user = userRepository.findById(userId).orElse(AppUserEntity.EMPTY);
    ProjectEntity project = ProjectMapper.from(projectDto);
    project.setOwner(user);
    return projectRepository.save(project);
  }

  @PutMapping("/projects")
  public ProjectEntity update(@RequestBody ProjectDto projectDto) {
    ProjectEntity projectEntity = projectRepository.getOne(projectDto.getId());
    ProjectEntity project = ProjectMapper.merge(projectEntity, projectDto);
    return projectRepository.save(project);
  }

  @DeleteMapping("/projects/{projectId}")
  public void delete(@PathVariable int projectId) {
    projectRepository.deleteById(projectId);
  }
}
