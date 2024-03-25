package Project.diary.service;

import Project.diary.DAO.DiaryDAO;
import Project.diary.dto.DiaryRegisterDTO;
import Project.diary.dto.DiaryResponseDTO;
import Project.diary.entity.Diary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryDAO diaryDAO;



//    public List<Diary> getDiariesByUserid(String userid) {
//        return diaryRepository.findByProfileUserid(userid);
//    }

    @Transactional
    public List<Diary> getAllDiary() { // 모든 일기보다는 캘린더 형식이니까 아이디만?
        return diaryDAO.findAll();
    }

    @Transactional
    public List<Long> getDiaryById() {
        List<Diary> diaryList = diaryDAO.findAll();
        return diaryList.stream().map(Diary::getId).collect(Collectors.toList()); // id만 가져오도록

    }

    @Transactional
    public DiaryResponseDTO getDiaryById(Long id) {
        Diary diary = diaryDAO.findById(id).orElseThrow(() ->
                new IllegalArgumentException("잘못된 아이디입니다."));
        return new DiaryResponseDTO(diary);
    }

    @Transactional
    public Diary createDiary(DiaryRegisterDTO dto) {
        return diaryDAO.save(dto.toEntity());
    }

    @Transactional
    public void  updateDiary(DiaryRegisterDTO updateDTO) {
        Diary diary = diaryDAO.findById(updateDTO.toEntity().getId()).
                orElseThrow(() -> new IllegalArgumentException("잘못된 일기 id 입니다."));
        diary.updateDiary(updateDTO.getTitle(), updateDTO.getContext(), new Date());
    }

    @Transactional
    public void deleteDiary(Long id) {
        diaryDAO.deleteById(id);
    }
}
