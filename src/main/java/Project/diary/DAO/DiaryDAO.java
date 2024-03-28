package Project.diary.DAO;

import Project.diary.dto.DiaryResponseDTO;
import Project.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryDAO extends JpaRepository<Diary, Long > {

    List<Diary> findByUser_Userid(String userId); // User 엔티티의 userid 필드 사용

}

