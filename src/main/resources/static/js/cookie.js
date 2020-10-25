window.onload = function getCookie() {
    let cookie_value = cookie("loginUser");
    if (cookie_value !== ""){
        get("#login-hello").style.display = "block";
        get("#login-ul").style.display = "block";
        get("#login-user").innerHTML = "【当前用户：" + cookie_value + "】";
        get("#login-before").style.display = "none";
        get("#login-after").style.display = "block";
    }
}