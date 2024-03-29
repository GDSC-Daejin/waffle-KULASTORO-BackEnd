<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>대시보드</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <h3 class="form-signin-heading text-center mb-5">Kulastro </h3>
    <img src="/images/profile.png" class="img-thumbnail" style="width: 200px;" alt="이 글이 보인다면 시큐리티 설정 잘못한거임!">

    <h3 class="overview-normalize">${login_nickname} 님 환영합니다!</h3>

    <button onclick="location.href='/view/user_change'" class="btn btn-sm btn-success">회원 정보 수정</button>
<%--    <button onclick="location.href='/auth/delete'" class="btn btn-sm btn-success">회원 탈퇴</button>--%>

    <form id="deleteForm">
        <button class="btn btn-sm btn-success" type="button" onclick="deleteUser()">회원 탈퇴</button>
    </form>

    <hr/>

    <hr/>
    <h3 class="overview-normalize">게시판리스트로 이동하기</h3>
    <p>
        <button onclick="location.href='/diary/list'" class="btn btn-sm btn-success">게시판 리스트로 이동</button>

    </p>
    <hr/>
    <form method="post" action="/logout">
        <button class="btn btn-sm btn-danger btn-block" type="submit" id="logout_btn">로그아웃</button>
    </form>


    <script>
        function deleteUser() {
            fetch('/auth/delete', {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        // 성공적으로 삭제되었을 때 수행할 작업
                        console.log('사용자가 성공적으로 삭제되었습니다.');
                        window.location.href = '/view/login';
                    } else {
                        // 삭제에 실패한 경우 처리
                        console.error('사용자 삭제에 실패했습니다.');
                    }
                })
                .catch(error => {
                    console.error('오류 발생:', error);
                });
        }
    </script>

</div>

</body>

</html>
