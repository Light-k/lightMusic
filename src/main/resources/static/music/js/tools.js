/**
 * 封装获取元素的方法
 * Param: 如 .header 或 #header 或者 header
 */
function get(selector) {
    if ((selector.indexOf(".") != -1) || (selector.indexOf("#") != -1)) {
        return selector.substring(0, 1) == "#" ? document.getElementById(selector.substring(1)) : document.getElementsByClassName(selector.substring(1));
    } else {
        return document.getElementsByTagName(selector);
    }
}

/**
 * 滚动字幕
 */
setInterval("active()", 1000);

function active() {
    let tag = document.getElementById('tip');
    let content = tag.innerText;
    let f = content.charAt(0);
    let l = content.substring(1, content.length);
    let new_content = l + f;
    tag.innerText = new_content;
}

/**
 * 获取cookie
 * @returns {string}
 */
function cookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i].trim();
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
