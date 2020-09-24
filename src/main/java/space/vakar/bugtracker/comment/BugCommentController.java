package space.vakar.bugtracker.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import space.vakar.bugtracker.bug.BugEntity;
import space.vakar.bugtracker.bug.BugRepository;
import space.vakar.bugtracker.user.AppUserRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/users/{userId}/bugs/{bugId}/**")
public class BugCommentController {

  private final BugCommentRepository bugCommentRepository;
  private final BugRepository bugRepository;
  private final AppUserRepository userRepository;

  @Autowired
  public BugCommentController(
      BugCommentRepository bugCommentRepository,
      BugRepository bugRepository,
      AppUserRepository userRepository) {
    this.bugCommentRepository = bugCommentRepository;
    this.bugRepository = bugRepository;
    this.userRepository = userRepository;
  }

  @GetMapping("/comments")
  public List<BugCommentEntity> findAll(@PathVariable int bugId) {
    return bugRepository.findById(bugId).orElse(BugEntity.EMPTY).getComments();
  }

  @GetMapping("/comments/{commentId}")
  public BugCommentEntity find(@PathVariable int commentId) {
    return bugCommentRepository.findById(commentId).orElse(BugCommentEntity.EMPTY);
  }

  @PostMapping("/comments")
  public BugCommentEntity save(
      @PathVariable int userId, @PathVariable int bugId, @RequestBody BugCommentDto commentDto) {
    BugCommentEntity comment = BugCommentMapper.from(commentDto);
    comment.setCreator(userRepository.getOne(userId));
    comment.setBug(bugRepository.getOne(bugId));
    return bugCommentRepository.save(comment);
  }

  @PutMapping("/comments")
  public BugCommentEntity update(@RequestBody BugCommentDto commentDto) {
    BugCommentEntity commentEntity = bugCommentRepository.getOne(commentDto.getId());
    commentEntity.setContent(commentDto.getContent());
    return bugCommentRepository.save(commentEntity);
  }

  @DeleteMapping("/comments/{commentId}")
  public void delete(@PathVariable int commentId) {
    bugCommentRepository.deleteById(commentId);
  }
}
