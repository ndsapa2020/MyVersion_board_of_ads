let viewAllUsersUrl = 'http://localhost:5556/api/admin/allUsers';
let getUserById = 'http://localhost:5556/api/admin/user';
let deleteUserById = 'http://localhost:5556/api/admin/user';
let createNewUser = 'http://localhost:5556/api/admin/newUser';
let updateUser = 'http://localhost:5556/api/admin/newUserData';

let adminUsersTable = $('#userTableJs tbody');
let deleteButtonInModalForm = $('#deleteButtonInModal div');
let saveButtonInModalForm = $('#updateButtonInModal div');

let elementCloseDeleteModal1 = document.getElementById('closeDeleteModal');
let elementCloseDeleteModal2 = document.getElementById('closeDeleteModal2');
let elementCreateUserRoles = document.getElementById('roleSelect');
let elementCreateUser = document.getElementById('createUser');
let elementCloseUpdateModal1 = document.getElementById('closeUpdateModal');
let elementCloseUpdateModal2 = document.getElementById('closeUpdateModal2');
let elementCloseCreateNewUserModal = document.getElementById('closeNewUserModal');
let elementCreateNewUserHref = document.getElementById('addUser');

let elementUserTable = document.getElementById('userTableAtAdminPanel');

$(document).ready(function () {
    showAllUsersTable();
});

$(document).mouseup(function (e) {
    let modalUpd = $('#updateModal');
    let modalDel = $('#deleteModal');
    let modalNew = $('#newUserModal');

    if (modalUpd.is(e.target)) {
        document.getElementById('updButtInModal').remove();
        clearTheValidateUpdate();
    }
    if (modalDel.is(e.target)) {
        document.getElementById('delButtInModal').remove();
    }
    if (modalNew.is(e.target)) {
        clearTheValidateCreate();
    }
});

//ОСНОВНЫЕ ФУНКЦИИ

//Функция заполняющая таблицу пользователей
function showAllUsersTable() {

    // document.getElementById('hideTheCat').hidden = true;

    let userIdForDelete = 0;
    let userIdForUpdate = 0;

    fetch(viewAllUsersUrl)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            if (data.success) {
                data.data.map(user => {

                    let buttonDelete = document.createElement('button');
                    let buttonEdit = document.createElement('button');
                    let tdEdit = document.createElement('td');
                    let tdDelete = document.createElement('td');
                    let tr = document.createElement('tr');
                    let counter = 0;

                    tr.setAttribute('id', "userDataTable");

                    const userRoles = user.roles.map(role => {
                        return role.name;
                    }).join(", ");

                    for (let o in user) {

                        let td = document.createElement('td');
                        let text = document.createTextNode(user[o]);

                        if (user[o] === null) {
                            text = document.createTextNode("No Data");
                        }

                        if (counter === 0) {
                            userIdForDelete = "fillingModalFormDelete" + "(" + user[o] + ")";
                            userIdForUpdate = "fillingModalFormUpdate" + "(" + user[o] + ")";
                        }
                        if (counter !== 6 && counter !== 7 && counter !== 2 && counter !== 10) {
                            td.appendChild(text);
                            tr.appendChild(td);
                        }
                        if (counter === 10) {
                            td.appendChild(document.createTextNode(userRoles));
                            tr.appendChild(td);
                            break;
                        }
                        counter++;
                    }

                    buttonEdit.setAttribute('id', "updateButton");
                    buttonEdit.setAttribute('class', "btn btn-info");
                    buttonEdit.setAttribute('role', "button");
                    buttonEdit.setAttribute('data-toggle', "modal");
                    buttonEdit.setAttribute('data-target', "#updateModal");
                    buttonEdit.setAttribute('onclick', `${userIdForUpdate}`);
                    buttonEdit.appendChild(document.createTextNode("Update"));

                    buttonDelete.setAttribute('id', "deleteButton");
                    buttonDelete.setAttribute('class', "btn btn-danger");
                    buttonDelete.setAttribute('role', "button");
                    buttonDelete.setAttribute('data-toggle', "modal");
                    buttonDelete.setAttribute('data-target', "#deleteModal");
                    buttonDelete.setAttribute('onclick', `${userIdForDelete}`);
                    buttonDelete.appendChild(document.createTextNode("Delete"));

                    tdEdit.appendChild(buttonEdit);
                    tdDelete.appendChild(buttonDelete);
                    tr.appendChild(tdEdit);
                    tr.appendChild(tdDelete);
                    adminUsersTable.append(tr);
                });
            }
        })
        .catch(error => {
            console.log(error);
        })
}

/*создание нового пользователя*/
async function newUser() {

    let roleSelectedValues = Array.from(elementCreateUserRoles.selectedOptions).map(el => el.value);
    let roleArray = convertToRoleSet(roleSelectedValues);

    let data = {

        email: $('#AdminPanelUserEmail').val(),
        password: $('#AdminPanelUserPassword').val(),
        firsName: $('#AdminPanelUserFirstName').val(),
        lastName: $('#AdminPanelUserLastName').val(),

        roles: roleArray

    };

    let userEmailFormData = document.forms["userCreationFormCP"]["userEmailCP"].value;
    let userPasswordFormData = document.forms["userCreationFormCP"]["userPasswordCP"].value;
    let userFirstNameFormData = document.forms["userCreationFormCP"]["userFirstNameCP"].value;
    let userLastNameFormData = document.forms["userCreationFormCP"]["userLastNameCP"].value;

    if (userEmailFormData !== "" &&
        (userPasswordFormData.length >= 6 && userPasswordFormData.length <= 60 && userPasswordFormData.match(/\s/) === null)
        && userFirstNameFormData !== "" && userLastNameFormData !== "" && roleArray.length !== 0) {
        const response = await fetch(createNewUser, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        })
            .catch(error => {
                console.log(error);
            });

        clearTheValidateCreate();
        document.getElementById("createUserResult").innerText = "Successful Creation";
        clearTable();
        showAllUsersTable();
    } else {
        if (userEmailFormData === "") {
            document.getElementById("emailErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("emailErrorsNU").innerText = "";
        }
        if (userPasswordFormData.length < 6 || userPasswordFormData.length > 60) {
            document.getElementById("passwordErrorsNU").innerText = "Required between 6 and 60 symbols";
        } else {
            document.getElementById("passwordErrorsNU").innerText = "";
        }
        if (userPasswordFormData.match(/\s/) !== null) {
            document.getElementById("passwordSpaceNotAllow").innerText = "Space not allowed";
        } else {
            document.getElementById("passwordSpaceNotAllow").innerText = "";
        }
        if (userFirstNameFormData === "") {
            document.getElementById("firstnameErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("firstnameErrorsNU").innerText = "";
        }
        if (userLastNameFormData === "") {
            document.getElementById("lastnameErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("lastnameErrorsNU").innerText = "";
        }
        if (roleArray.length === 0) {
            document.getElementById("rolesErrorsNU").innerText = "Need to select role";
        } else {
            document.getElementById("rolesErrorsNU").innerText = "";
        }
        document.getElementById("createUserResult").innerText = "Creation failed";
    }
}

async function updateUsers(value) {

    let elementUpdateUserRoles = document.getElementById('userUpdRoles');

    let roleSelectedValues = Array.from(elementUpdateUserRoles.selectedOptions).map(el => el.value);
    let roleArray = convertToRoleSet(roleSelectedValues);

    let data = {

        id: $('#updUserID').val(),
        firsName: $('#updUserName').val(),
        lastName: $('#updUserLastName').val(),
        email: $('#updUserEmail').val(),
        password: $('#updUserPassword').val(),


        roles: roleArray

    };

    let userEmailFormData = document.forms["userUpdateFormCP"]["email"].value;
    let userPasswordFormData = document.forms["userUpdateFormCP"]["password"].value;
    let userFirstNameFormData = document.forms["userUpdateFormCP"]["name"].value;
    let userLastNameFormData = document.forms["userUpdateFormCP"]["surname"].value;

    if (userEmailFormData !== "" &&
        (userPasswordFormData.length >= 6 && userPasswordFormData.length <= 60 && userPasswordFormData.match(/\s/) === null)
        && userFirstNameFormData !== "" && userLastNameFormData !== "" && roleArray.length !== 0) {

        const response = await fetch(updateUser, {
            method: 'PUT',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        });

        clearTheValidateUpdate();
        document.getElementById("updateUserResult").innerText = "User updated";

        clearTable();
        showAllUsersTable();

    } else {
        if (userEmailFormData === "") {
            document.getElementById("emailErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("emailErrorsUS").innerText = "";
        }
        if (userPasswordFormData.length < 6 || userPasswordFormData.length > 60) {
            document.getElementById("passwordErrorsUS").innerText = "Required between 6 and 60 symbols";
        } else {
            document.getElementById("passwordErrorsUS").innerText = "";
        }
        if (userPasswordFormData.match(/\s/) !== null) {
            document.getElementById("passwordSpaceNotAllowUS").innerText = "Space not allowed";
        } else {
            document.getElementById("passwordSpaceNotAllowUS").innerText = "";
        }
        if (userFirstNameFormData === "") {
            document.getElementById("firstnameErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("firstnameErrorsUS").innerText = "";
        }
        if (userLastNameFormData === "") {
            document.getElementById("lastnameErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("lastnameErrorsUS").innerText = "";
        }
        if (roleArray.length === 0) {
            document.getElementById("rolesErrorsUS").innerText = "Need to select role";
        } else {
            document.getElementById("rolesErrorsUS").innerText = "";
        }
        document.getElementById("updateUserResult").innerText = "Update failed";
    }

}

/*заполнение модальной формы на удаление*/
function fillingModalFormDelete(id) {

    let deleteButtonInModal = document.createElement('button');
    let userIdForDeleteButton = "deleteData" + "(" + id + ")";

    deleteButtonInModal.setAttribute('type', "button");
    deleteButtonInModal.setAttribute('id', "delButtInModal");
    deleteButtonInModal.setAttribute('class', "btn btn-danger");
    deleteButtonInModal.setAttribute('data-dismiss', "modal");
    deleteButtonInModal.setAttribute('onclick', `${userIdForDeleteButton}`);
    deleteButtonInModal.appendChild(document.createTextNode("Delete"));

    deleteButtonInModalForm.append(deleteButtonInModal);

    fetch(getUserById + "/" + id).then(function (response) {
        response.json().then(function (data) {

            const userRoles = data.data.roles.map(role => {
                return role.name;
            }).join(", ");

            $('#delUserID').val(id);
            $('#delUserName').val(data.data.firsName);
            $('#delUserDataReg').val(data.data.dataRegistration);
            $('#delUserEmail').val(data.data.email);
            $('#delUserRoles').val(userRoles);
        });
    });

}

//Удаление пользователя

async function deleteData(value) {

    await fetch(deleteUserById + "/" + value, {
        method: 'DELETE',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
    });

    document.getElementById('delButtInModal').remove();
    clearTable();
    showAllUsersTable()
}

/*заполнение модальной формы на редактирование*/
function fillingModalFormUpdate(id) {

    let updateButtonInModal = document.createElement('button');
    let userIdForUpdateButton = "updateUsers" + "(" + id + ")";

    updateButtonInModal.setAttribute('type', "button");
    updateButtonInModal.setAttribute('id', "updButtInModal");
    updateButtonInModal.setAttribute('class', "btn btn-success");
    updateButtonInModal.setAttribute('onclick', `${userIdForUpdateButton}`);
    updateButtonInModal.appendChild(document.createTextNode("Save"));

    saveButtonInModalForm.append(updateButtonInModal);

    fetch(getUserById + "/" + id).then(function (response) {
        response.json().then(function (data) {

            $('#updUserID').val(id);
            $('#updUserName').val(data.data.firsName);
            $('#updUserLastName').val(data.data.lastName);
            $('#updUserEmail').val(data.data.email);
            $('#updUserDataReg').val(data.data.dataRegistration);

        });
    });
}

// ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ НА ON.CLICK

elementCreateUser.onclick = function () {
    newUser();
};

elementCreateNewUserHref.onclick = function () {
    $('#newUserModal').modal('show');
    document.getElementById('AdminPanelUserEmail').value = '';
    document.getElementById('AdminPanelUserPassword').value = '';
    document.getElementById('AdminPanelUserFirstName').value = '';
    document.getElementById('AdminPanelUserLastName').value = '';
};

//Сокрытие информации о создании нового пользователя
elementUserTable.onclick = function () {
    clearTable();
    showAllUsersTable();
};

/*создаем массив из значений полученных с селектора при создании нового пользователя*/
function convertToRoleSet(Array) {
    let roleArray = [];

    if (Array.indexOf("USER") !== -1) {
        roleArray.unshift({id: 2, name: "USER"});
    }
    if (Array.indexOf("ADMIN") !== -1) {
        roleArray.unshift({id: 1, name: "ADMIN"});
    }
    return roleArray;
}


//Очистка таблиц при закрытии модального окна
function clearTable() {
    while (document.getElementById("userDataTable") != null) {
        document.getElementById("userDataTable").remove();
    }
}

/*необходимы для избежания дублирования кнопок при закрытии модального окна*/
elementCloseDeleteModal1.onclick = function () {
    document.getElementById('delButtInModal').remove();
};

elementCloseDeleteModal2.onclick = function () {
    document.getElementById('delButtInModal').remove();
};

elementCloseUpdateModal1.onclick = function () {
    document.getElementById('updButtInModal').remove();
    clearTheValidateUpdate();
};

elementCloseUpdateModal2.onclick = function () {
    document.getElementById('updButtInModal').remove();
    clearTheValidateUpdate();
};

elementCloseCreateNewUserModal.onclick = function () {
    clearTheValidateCreate();
};

function clearTheValidateUpdate() {

    document.getElementById("emailErrorsUS").innerText = "";
    document.getElementById("passwordErrorsUS").innerText = "";
    document.getElementById("passwordSpaceNotAllowUS").innerText = "";
    document.getElementById("firstnameErrorsUS").innerText = "";
    document.getElementById("rolesErrorsUS").innerText = "";
    document.getElementById("updateUserResult").innerText = "";
    document.getElementById("lastnameErrorsUS").innerText = "";

}

function clearTheValidateCreate() {

    document.getElementById("createUserResult").innerText = "";
    document.getElementById("emailErrorsNU").innerText = "";
    document.getElementById("passwordErrorsNU").innerText = "";
    document.getElementById("passwordSpaceNotAllow").innerText = "";
    document.getElementById("firstnameErrorsNU").innerText = "";
    document.getElementById("lastnameErrorsNU").innerText = "";
    document.getElementById("rolesErrorsNU").innerText = "";
}

$('#categoryPanel span').on('click', function() {
    console.log("bla");
    document.getElementById('nav-userlist').style.display = "none";
    document.getElementById('nav-category').style.display = "block";
    document.getElementById('nav-userlist').className = "tab-pane fade";
    document.getElementById('nav-category').className = "tab-pane fade active show";
})

$('#userTableAtAdminPanel span').on('click', function() {
    document.getElementById('nav-userlist').style.display = "block";
    document.getElementById('nav-category').style.display = "none";
    document.getElementById('nav-category').className = "tab-pane fade";
    document.getElementById('nav-userlist').className = "tab-pane fade active show";
})