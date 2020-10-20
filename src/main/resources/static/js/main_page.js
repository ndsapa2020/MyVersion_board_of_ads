let buttonAdd = $('#searchCityDiv');

$("#region, #category-select-city").click(function () {
    $('#searchModel').modal('show');
});

async function addCategories() {
    let categoriesResponse = await userService.findAllCategories();
    let categories = categoriesResponse.json();
    let categorySelect = $('.categoriesSelect');
    categorySelect.append('<option th:text="Любая категория">Любая категория</option>');
    categories.then(categories => {
        categories.data.forEach((cat) => {
            if (cat.parentName == null) {
                let option = `<option class="category-parent" th:text="` + cat.name + `">` + cat.name + `</option>`;
                categorySelect.append(option);
            } else {
                let option = `<option th:text="` + cat.name + `">` + cat.name + `</option>`;
                categorySelect.append(option);
            }
        })
    });
}

let changedCityName;
let regionPosts;

function clickCountButton() {
    $('#category-select-city').empty();
    $('#cityInput').empty();
    $('#searchModel').modal('hide');
    let row = `<option>` + changedCityName + `</option>`;
    $('#category-select-city').append(row);
    reinstallTable(selectedCategoryOption, changedCityName, $("#search-main-text").val(), $("#image-select option:selected").val())
}

$('select#cities').on('change', function () {
    $('input[name="cityInput"]').val(this.value);
});

function onOptionHover() {
    $(".opt").mouseover(
        function () {
            $(this).css('background', '#99ccff')
        });
    $(".opt").mouseleave(
        function () {
            $(this).css('background', '#fff')
        });
}

async function onClickOpt(id) {
    changedCityName = id;
    $('.typeahead').val(id);
    $('#citiesSelect').remove();
    let usersResponse;
    if (id.includes('Область')
        || id.includes('Край')
        || id.includes('Республика')
        || id.includes('Автономный округ')
        || id.includes('Город')
    ) {
        usersResponse = await userService.findPostingByRegionName(id);
    } else {
        usersResponse = await userService.findPostingByCityName(id);
    }
    posts = usersResponse.json();
    $('#countPostButton').empty();
    let sizeArray = 0;
    posts.then(posts => {
        posts.data.forEach((posting) => {
            let temp = selectedCategoryOption;
            if(temp === "Любая категория") {
                temp = posting.category;
            }
            if(posting.category === temp) {
                sizeArray++;
            }
        })
    }).then(() => {
            $('#countPostButton').remove();
            let button = `<button
                                type="button"
                                class="btn btn-primary position-fixed"
                                onclick="clickCountButton()"
                                id="countPostButton">Показать ` + sizeArray + ` объявлений
                          </button>`;
            buttonAdd.append(button);
        }
    );
    regionPosts = (await posts).data;
}

$(document).ready(function () {
    viewCities();
    addCategories();
    $('#buttonAuth').on('click', function () {
        $('#emailAuth').addClass("redborder");
        authorization();
    });
});


async function authorization() {
    $('#emailAuth').removeClass("redborder");
    $('#passwordAuth').removeClass("redborder");
    let userAuth = {
        email: $("#emailAuth").val(),
        password: $("#passwordAuth").val()
    };
    try {
        const authResponse = await fetch('http://localhost:5556/api/auth', {
            method: "POST",
            credentials: 'same-origin',
            body: JSON.stringify(userAuth),
            headers: {
                'content-type': 'application/json'
            }
        })
        let authMessage = authResponse.json();
        authMessage.then(authMessage => {
            if (authMessage.success === true) {
                $('#registrationModalCenter').modal('hide');
                $('#emailAuth').val("");
                $('#passwordAuth').val("");
                location.reload();
            } else {
                let errorMessage = authMessage.error;
                if (errorMessage.text === "User not found!") {
                    $('#emailAuth').addClass("redborder");
                }
                if (errorMessage.text === "Incorrect password!") {
                    $('#passwordAuth').addClass("redborder");
                }
            }
        });
    } catch (error) {
        console.log('Возникла проблема с вашим fetch запросом: ', error.message);
    }
}

let cities;
let posts;

async function viewCities() {
    $('#category-select-city').empty();
    const usersResponse = await userService.findAllCity();
    cities = usersResponse.json();
    const postsResponse = await userService.findAllPostings();
    posts = postsResponse.json();
    let sizeArray = 0;
    posts.then(posts => {
        posts.data.forEach((posting) => {
            let temp = selectedCategoryOption;
            if(temp === "Любая категория") {
               temp = posting.category;
            }
            if(posting.category === temp) {
                sizeArray++;
            }
        })
    }).then(() => {
            let button = `<button 
                                type="button" 
                                class="btn btn-primary position-fixed"   
                                id="countPostButton">Показать ` + sizeArray + ` объявлений
                          </button>`;
            buttonAdd.append(button);
        }
    );
}

$('.typeahead').on('keyup', function () {
    addOptions();
    $('#countPostButton').attr("disabled", true);
});

function addOptions() {
    $('#citiesSelect').remove();
    $('#citiesSelect').empty();
    let select = `<select id="citiesSelect" size="7" class="form-control"></select>`;
    $('.citiesOptions').append(select);
    let addForm = $(".typeahead").val().toLowerCase();
    cities.then(cities => {
        cities.data.forEach(city => {
            if (city.name.toLowerCase().includes(addForm)) {
                let userRow = `<option onmouseover="onOptionHover()" 
                                       onclick="onClickOpt(this.id)"
                                       id="${city.name}"
                                       class="opt"                                
                                       text="${city.name}">
                                           <div>${city.name}</div>
                                           <div>${' ' + city.regionFormSubject}</div>
                                </option>`;
                $('#citiesSelect').append(userRow);
            }
        });
    });
}

const httpHeaders = {
    fetch: async function (url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
        return response;
    }
};

const userService = {
    findAllCity: async () => {
        return await httpHeaders.fetch('/api/city');
    },
    findPostingByCityName: async (name) => {
        return await httpHeaders.fetch('api/posting/city/' + name);
    },
    findPostingByRegionName: async (name) => {
        return await httpHeaders.fetch('api/posting/region/' + name);
    },
    findAllPostings: async () => {
        return await httpHeaders.fetch('api/posting/');
    },
    findAllCategories: async () => {
        return await httpHeaders.fetch("api/category")
    }
}

// $.get("/user", function (data) {
//     $("#user").html(data.userAuthentication.details.name);
//     $(".unauthenticated").hide()
//     $(".authenticated").show()
// });