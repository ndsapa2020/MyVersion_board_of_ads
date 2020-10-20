$(document).ready(function () {
    getCategoryTable();
})

function getCategoryTable3(categoryName, simpleName) {
    console.log("table3 " + categoryName);
    $.ajax({
        type: "GET",
        url: '/api/category',
        dataType: "json",
        async: true,
        success: function (result) {
            let array = result.data;
            $("#cascade-table-3").css("display", "block");
            document.getElementById("cascade-table-3").innerHTML =
                `<div class="category-table-title">
                    ${simpleName}
                 </div>`;
            for (let i = 0; i < array.length; i++) {
                let x = array[i];
                let arr = x.name.split(":");
                let simpleName = arr[arr.length - 1]
                if (x.parentName == categoryName) {
                    document.getElementById("cascade-table-3").innerHTML +=
                        `<div class="category-table-button-3 unactive-category-table-button-3" id="category-table-button-3"
                            onclick="clickOnCategoryButton3(this)" onmouseover="hoverOnCategoryButton3()">
                        ${simpleName}
                    </div>`
                }
            }
        }
    });
}

function getCategoryTable() {
    $.ajax({
        type: "GET",
        url: '/api/category',
        dataType: "json",
        async: true,
        success: function (result) {
            let array = result.data;
            for (let i = 0; i < array.length; i++) {
                let x = array[i];
                if (x.layer === 1) {
                    document.getElementById("cascade-table").innerHTML +=
                        `<div class="category-table-button inactive-category-table-button" id="category-table-button" 
                            onclick="clickOnCategoryButton(this,'${x.name}')" onmouseover="hoverOnCategoryButton()">
                        ${x.name}
                    </div>`
                }
            }
        }
    })
}

function getCategoryTable2(categoryName) {
    $.ajax({
        type: "GET",
        url: "/api/category",
        dataType: "json",
        async: true,
        success: function (result) {
            let array = result.data;
            $("#cascade-table-2").css("display", "block");
            document.getElementById("cascade-table-2").innerHTML =
                `<div class="category-table-title">
                    ${categoryName}
                 </div>`;
            for (let i = 0; i < array.length; i++) {
                let x = array[i];
                if (x.parentName == categoryName) {
                    let arr = x.name.split(":");
                    let simpleName = arr[arr.length - 1]
                    document.getElementById("cascade-table-2").innerHTML +=
                        `<div class="category-table-button-2 unactive-category-table-button-2" id="category-table-button-2" 
                            onclick="clickOnCategoryButton2(this, '${x.name}', '${simpleName}')"  onmouseover="hoverOnCategoryButton2()">
                        ${simpleName}
                    </div>`
                }
            }
        }
    })
}

function clickOnCategoryButton(o, category) {
    $(".category-table-button").removeClass("active-category-table-button")
        .addClass("inactive-category-table-button").css("background-color", "#fff");
    $(o).removeClass("inactive-category-table-button")
        .addClass("active-category-table-button").css("background-color", "#0af", "color", "#fff");
    $("#cascade-table-3").css("display", "none");
    getCategoryTable2(category);
}

function clickOnCategoryButton2(o, category, simpleName) {
    console.log(category);
    $(".category-table-button-2").removeClass("active-category-table-button-2")
        .addClass("inactive-category-table-button-2").css("background-color", "#fff");
    $(o).removeClass("inactive-category-table-button-2")
        .addClass("active-category-table-button-2").css("background-color", "#0af", "color", "#fff");
    getCategoryTable3(category, simpleName)
}

function hoverOnCategoryButton() {
    $(".category-table-button").hover(
        function () {
            if (!this.classList.contains("active-category-table-button")) {
                $(this).css("background-color", "#a5eaf8");
            }
        }, function () {
            if (!this.classList.contains("active-category-table-button")) {
                $(this).css("background-color", "#fff");
            }
        });
}

function hoverOnCategoryButton2() {
    $(".category-table-button-2").hover(
        function () {
            if (!this.classList.contains("active-category-table-button-2")) {
                $(this).css("background-color", "#a5eaf8");
            }
        }, function () {
            if (!this.classList.contains("active-category-table-button-2")) {
                $(this).css("background-color", "#fff");
            }
        });
}

function clickOnCategoryButton3(o) {
    $(".category-table-button-3").removeClass("active-category-table-button-3")
        .addClass("inactive-category-table-button-3").css("background-color", "#fff");
    $(o).removeClass("inactive-category-table-button-3")
        .addClass("active-category-table-button-3").css("background-color", "#0af", "color", "#fff");
    // todo add action
}

function hoverOnCategoryButton3() {
    $(".category-table-button-3").hover(
        function () {
            if (!this.classList.contains("active-category-table-button-3")) {
                $(this).css("background-color", "#a5eaf8");
            }
        }, function () {
            if (!this.classList.contains("active-category-table-button-3")) {
                $(this).css("background-color", "#fff");
            }
        });
}
