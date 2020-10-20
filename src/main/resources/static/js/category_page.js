let categories;

$(document).ready(function () {
    showCategories();
});

$('#saveCategoryButton').on('click', function() {
    data = {
        id: $('#categoryID').val(),
        name: $('#categoryName').val(),
        parentName: $('#parent-category-select').val()
    }
    categoryService.updateCategory(data).then(categoryResponse => {
        if (categoryResponse.status === 200) {
            $('#updateCategoryModal').modal('hide');
            $('.list-group-item').remove();
            showCategories();
        }
    });
});

$('#addCategory').on('click', function() {
    $('#addCategoryModal').modal('show');
    $('select option').remove();
    let categorySelect = $('#parent-add-select');
    categories.then(categories => {
        categories.data.forEach((cat) => {
            if (cat.parentName == null) {
                let option = `<option th:value="${cat.name}" style="background: #b3cccc; color: #fff;">${cat.name}</option>`;
                categorySelect.append(option);
            } else {
                let option = `<option th:value="${cat.name}">${cat.name}</option>`;
                categorySelect.append(option);
            }
        });
        let option = `<option  th:value=null selected>Без категории</option>`;
        categorySelect.append(option);
    });
});

$('#addCategoryButton').on('click', function() {
    let data = {
        name: $('#categoryAddName').val(),
        parentName: $('#parent-add-select').val()
    }
    categoryService.createCategory(data).then(categoryResponse => {
        if (categoryResponse.status === 200) {
            $('#addCategoryModal').modal('hide');
            $('.list-group-item').remove();
            showCategories();
        }
    });
});

async function deleteCategory() {
    categoryService.deleteCategory($('#categoryDeleteID').val()).then(
        categoryResponse => {
            if (categoryResponse.status === 200) {
                $('#deleteCategoryModal').modal('hide');
                $('.list-group-item').remove();
                showCategories();
            }
        }
    );
}

async function showCategories() {
    let categoriesResponse = await categoryService.findAllCategories();
    categories = categoriesResponse.json();
    let categoryList = $('#listCategory');
    categories.then(categories => {
        categories.data.forEach((cat) => {
            if (cat.parentName == null) {
                let rowCategory = `<li class="list-group-item list-group-item-dark col-md-5" th:text="'${cat.name}'">
                                    <div class="far fa-edit" onclick="showEditCategoryModal(${cat.id})"></div>
                                    <div class="far fa-trash-alt mr-2" onclick="showDeleteCategoryModal(${cat.id})"></div>                                    
                                    ${cat.name}
                                    </li>`;
                categoryList.append(rowCategory);
            } else {
                let rowCategory = `<li class="list-group-item list-group-item-light col-md-5" th:text="'${cat.name}'">
                                    <div class="far fa-edit" onclick="showEditCategoryModal(${cat.id})"></div>
                                    <div class="far fa-trash-alt mr-2" onclick="showDeleteCategoryModal(${cat.id})"></div>                                    
                                    ${cat.name}
                                    </li>`;
                categoryList.append(rowCategory);
            }
        })
    });
}

async function showEditCategoryModal(catName) {
    let categoryResponse = await categoryService.findCategoryByName(catName);
    let categoryJson = categoryResponse.json();
    $('#updateCategoryModal').modal('show');
    categoryJson.then(category => {
        $('#categoryID').val(category.data.id);
        $('#categoryID').text(category.data.id);
        $('#categoryName').val(category.data.name);
        $('#categoryName').text(category.data.name);
        let categorySelect = $('#parent-category-select');
        $('select option').remove();
        categories.then(categories => {
            let option = `<option  th:value=null></option>`;
            categorySelect.append(option);
            categories.data.forEach((cat) => {
                if (category.data.parentName === cat.name) {
                    let option = `<option  th:value="${cat.name}" selected>${cat.name}</option>`;
                    categorySelect.append(option);
                } else if (cat.parentName == null) {
                    let option = `<option th:value="${cat.name}" style="background: #b3cccc; color: #fff;">${cat.name}</option>`;
                    categorySelect.append(option);
                } else {
                    let option = `<option th:value="${cat.name}">${cat.name}</option>`;
                    categorySelect.append(option);
                }
            });
            if (category.data.parentName == null) {
                let option = `<option  th:value=null selected></option>`;
                categorySelect.append(option);
            }
        });
    });
}

async function showDeleteCategoryModal(catName) {
    $('#deleteCategoryModal').modal('show');
    let categoryResponse = await categoryService.findCategoryByName(catName);
    let categoryJson = categoryResponse.json();
    categoryJson.then(category => {
        $('#categoryDeleteID').val(category.data.id);
        $('#categoryDeleteName').val(category.data.name);
        $('#categoryParentName').val(category.data.parentName);
    });
}

const http = {
    fetch: async function(url, options = {}) {
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

const categoryService = {
    findAllCategories: async () => {
        return await http.fetch('/api/category', {
            method: 'GET'
        });
    },
    findCategoryByName: async (id) => {
        return await http.fetch('/api/category/' + id);
    },
    updateCategory: async (data) => {
        return await http.fetch('/api/category/', {
            body: JSON.stringify(data),
            method: 'PUT'
        });
    },
    deleteCategory: async (id) => {
        return await http.fetch('/api/category/' + id, {
            method: 'DELETE'
        });
    },
    createCategory: async (data) => {
        return await http.fetch('/api/category', {
            body: JSON.stringify(data),
            method: 'POST'
        });
    },
}