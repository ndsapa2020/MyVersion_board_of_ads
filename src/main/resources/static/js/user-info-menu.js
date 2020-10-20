let stringToColor = function stringToColor(str) {
    let hash = 0;
    let color = '#';
    let i;
    let value;
    let strLength;

    if (!str) {
        return color + '333333';
    }

    strLength = str.length;

    for (i = 0; i < strLength; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }

    for (i = 0; i < 3; i++) {
        value = (hash >> (i * 8)) & 0xFF;
        color += ('00' + value.toString(16)).substr(-2);
    }

    return color;
};

let name = document.getElementById('name').innerText;
let letter = name.substr(0, 1);
let backgroundColor = stringToColor(name);
let elementAvatar = document.getElementById('avatar');
let elementName = document.getElementById('name');


elementName.innerHTML = name;
if (elementAvatar.innerHTML.length === 0) {
    elementAvatar.innerHTML = letter;
    elementAvatar.style.backgroundColor = backgroundColor;
}
