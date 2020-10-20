let token;

$(document).ready(function() {
    if (window.location.href.indexOf("/confirm/") > -1) {
        $('#pswdNewModal').modal('show');
        const urlParams = new URLSearchParams(window.location.search);
        token = urlParams.get('token');
    }
});

$("#password-recovery").click(function() {
    $('#registrationModalCenter').modal('hide');
    $('#pswdModal').modal('show');
});

$("#recoveryButton").click(function() {
    let email = $('input[name="recoveryEmail"]').val();
    $('#passwordModBlock').remove();
    let addedData = `<div class="recovery-form-padding text-center">
                        Ссылка для восстановления пароля отправлена на почту <strong>` + email + `</strong>.
                     </div>
                     <hr/>
                     <div class="h5 text-center">
                     <a href="/login">Войдите</a>, если вспомнили пароль
                     </div>`;
    $('#pswdBlock').append(addedData);
    emailService.sendEmail(email);
});

$("#newPswdButton").click(function() {
    let data = {
        password: $("#newPassInput").val(),
    };
    emailService.recoveryPassword(token, data).then(userResponse => {
        if (userResponse.status == 200) {
            $('#pswdNewModal').modal('hide');
            window.location.href = '/';
        }
    });
});

const emailService = {
    sendEmail: async (email) => {
        return await httpHeaders.fetch('/api/mail/' + email);
    },
    recoveryPassword: async (token, data) => {
        return await httpHeaders.fetch('/api/mail/?token=' + token, {
            body: JSON.stringify(data),
            method: 'PUT'
        });
    }
}