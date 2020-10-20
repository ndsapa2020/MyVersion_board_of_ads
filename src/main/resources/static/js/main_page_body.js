let selectedCategoryOption = "Любая категория";
let allPostings = [];
let selectedCity = $("#category-select-city option:selected").val();
let textInput = $("#search-main-text").val();
let photoOption = $("#image-select option:selected").val();

function getPostingsTable(posts) {
    if(posts === "undefined") {

    } else {
        for (let step = 0; step < posts.length; step++) {
            let postingDTO = posts[step];

            let date = postingDTO.datePosting.substring(8, 10) + "-" +
                postingDTO.datePosting.substring(5, 7) + "-" +
                postingDTO.datePosting.substring(0, 4) + " " +
                postingDTO.datePosting.substring(11, 16);

            document.getElementById('mainPageBody').innerHTML +=
                `<div id="main_page_posting" class="col-md-3">
                        <div id="cardPosting" class="card">
                            <div id="ImageSlider${step}" class="carousel slide" data-interval="false">
                                <ol id="carouselIndicators${step}" class="carousel-indicators">
                                    
                                </ol>
                                <div id="carouselInner${step}" class="carousel-inner">
                                    
                                </div>
                            </div>
                            <div id="postingCardBody" class="card-body">
                                <a id="postingTitle" class="text-primary" href="#">${postingDTO.title}</a>
                                <strong>
                                    <div id="price">${postingDTO.price} ₽</div>
                                </strong>
                                <div class="card-text text-muted">
                                    <div id="meetingPlace">${postingDTO.meetingAddress}</div>
                                    <div id="timeOfPosting">${date}</div>
                                </div>
                            </div>
                        </div>
                    </div>`

            if (postingDTO.images.length > 0) {
                for (let i = 0; i < postingDTO.images.length; i++) {
                    let indicator = step + "indicator" + i;
                    if (i === 0) {
                        document.getElementById("carouselIndicators" + step).innerHTML +=
                            `<li id="${indicator}" data-target="#ImageSlider${step}" data-slide-to="i" class="active"></li>`

                        document.getElementById("carouselInner" + step).innerHTML +=
                            `<div class="carousel-item active">
                                    <a href="#">
                                        <img id="postingImageRef" src="${postingDTO.images[i].pathURL}" class="card-img-top" alt="">
                                    </a>
                                </div>`
                        $("#" + indicator).on("mouseover", function () {
                            $("#" + indicator).click();
                        });
                    } else {
                        document.getElementById("carouselIndicators" + step).innerHTML +=
                            `<li id="${indicator}" data-target="#ImageSlider${step}" data-slide-to="i"></li>`

                        document.getElementById("carouselInner" + step).innerHTML +=
                            `<div class="carousel-item">
                                    <a href="#">
                                        <img id="postingImageRef" src="${postingDTO.images[i].pathURL}" class="card-img-top" alt="">
                                    </a>
                                </div>`
                        $("#" + indicator).on("mouseover", function () {
                            $("#" + indicator).click();
                        });
                    }
                }
            } else {
                document.getElementById("carouselInner" + step).innerHTML +=
                    `<div class="carousel-item active">
                                    <a href="#">
                                        <img id="postingImageRef" src="../images/empty_image.jpg" class="card-img-top" alt="">
                                    </a>
                                </div>`
            }
        }
    }
}

function getList() {

    selectedCity = $("#category-select-city option:selected").val();
    textInput = $("#search-main-text").val();
    photoOption = $("#image-select option:selected").val();

    reinstallTable(selectedCategoryOption, selectedCity, textInput, photoOption);
}

$(document).ready(function () {
    getList();
});

function getCategoryOption(categoryOption) {

    for (let i = 0; i < categoryOption.options.length; i++) {
        if (categoryOption.options[i].selected) {
            selectedCategoryOption = (categoryOption.options[i].text);
        }
    }

    selectedCity = $("#category-select-city option:selected").val()
    textInput = $("#search-main-text").val();
    photoOption = $("#image-select option:selected").val()

    reinstallTable(selectedCategoryOption, selectedCity, textInput, photoOption);
}

function searchPosts() {

    selectedCity = $("#category-select-city option:selected").val()
    textInput = $("#search-main-text").val();
    photoOption = $("#image-select option:selected").val()

    reinstallTable(selectedCategoryOption, selectedCity, textInput, photoOption);
}

async function reinstallTable(categoryOption, cityOption, searchTextOption, photoOption) {

    document.querySelectorAll('#main_page_posting').forEach(block => block.remove())

    allPostings = await getData(categoryOption, cityOption, searchTextOption, photoOption);

    getPostingsTable(allPostings);
}

async function getData(categoryOption, cityOption, searchTextOption, photoOption) {
    let response = await fetch("/api/search" + "?catSel=" + categoryOption
        + "&citSel=" + cityOption
        + "&searchT=" + searchTextOption
        + "&phOpt=" + photoOption, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    return (await response.json()).data;
}