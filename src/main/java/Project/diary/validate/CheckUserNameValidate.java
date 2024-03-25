package Project.diary.validate;

import Project.diary.dto.UserRequestDTO;
import Project.diary.DAO.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Component // bean 등록
public class CheckUserNameValidate extends AbstractValidator<UserRequestDTO> {


    private final UserRepository userRepository;

    @Override
    protected void doValidate(UserRequestDTO dto, Errors errors) {

        if (userRepository.existsByUserid(dto.toEntity().getUserid())) {
            errors.rejectValue("userid", "아이디 중복 오류", "이미 사용중인 아이디 입니다.");
        }
    }


}
