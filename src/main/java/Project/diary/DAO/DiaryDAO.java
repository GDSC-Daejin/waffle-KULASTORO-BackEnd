package Project.diary.DAO;

import Project.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiaryDAO extends JpaRepository<Diary, Long > {

}

