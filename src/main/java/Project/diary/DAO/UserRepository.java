package Project.diary.DAO;

import Project.diary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserid(String userid);

    /**
     * 중복검사에 사용되는 로직
     */
    boolean existsByUserid(String userid);
    boolean existsByNickname(String nickname);


}
