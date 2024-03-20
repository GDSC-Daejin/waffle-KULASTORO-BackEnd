
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>회원정보 수정</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous">
</head>

<body>
<div class="container">
    <form class="form-signin" id='update_form'>
        <h2 class="form-signin-heading text-center mb-5">회원정보 수정</h2>

        <p>
            <label for="username" class="sr-only">아이디</label>
            <input type="text" id="username" name="userid" class="form-control" placeholder="아이디" required="" autofocus="" value="${login_userid}" readonly>
        </p>
        <p>
            <label for="password" class="sr-only">새로운 비밀번호</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="새로운 비밀번호" required="">
        </p>

        <p>
            <label for="nickname" class="sr-only">새로운 닉네임</label>
            <input type="text" id="nickname" name="nickname" class="form-control" placeholder="새로운 닉네임" required="" value="${login_nickname}">
        </p>

        <button class="btn btn-lg btn-primary btn-block" type="submit">수정 완료</button>
    </form>

    <script>
        const form = document.getElementById('update_form');

        form.addEventListener('submit', e => {
            e.preventDefault();

            const data = new FormData(form);
            const param = JSON.stringify(Object.fromEntries(data));

            fetch('/auth/update', {
                method: 'PUT',
                body: param,
                headers: {
                    'Content-Type': 'application/json'
                }
            })
            .then(response => {
                if (response.status == 200) {
                    alert("회원정보가 성공적으로 수정되었습니다. 재로그인 바랍니다!");
                    window.location.href = '/view/login'; // 수정 후 대시보드 페이지로 이동
                } else {
                    alert("회원정보 수정에 실패했습니다.");
                }
            })
            .catch(error => console.log(error))
        });
    </script>
</div>
</body>
</html>
