package space.vakar.bugtracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {

  private final AppUserRepository repository;

  @Autowired
  public AppUserController(AppUserRepository repository) {
    this.repository = repository;
  }

  @GetMapping("/users")
  public List<AppUserEntity> findAll() {
    return repository.findAll();
  }

  @GetMapping("/users/{userId}")
  public AppUserEntity find(@PathVariable int userId) {
    return repository.findById(userId).orElse(AppUserEntity.EMPTY);
  }

  @PostMapping("/users")
  public AppUserEntity save(@RequestBody AppUserDto userDto) {
    userDto.setId(0);
    AppUserEntity user = AppUserMapper.from(userDto);
    return repository.save(user);
  }

  @PutMapping("/users")
  public AppUserEntity update(@RequestBody AppUserDto userDto) {
    AppUserEntity userEntity = repository.getOne(userDto.getId());
    userEntity.setName(userDto.getName());
    return repository.save(userEntity);
  }

  @DeleteMapping("/users/{userId}")
  public void delete(@PathVariable int userId) {
    repository.deleteById(userId);
  }
}
