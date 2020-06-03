import {LoginData} from '@/models/rules/LoginData'

function token() {
  const value = document.cookie.match("(^|;) ?" + "jwt_auth_token" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function cookieId() {
  const value = document.cookie.match("(^|;) ?" + "user_id" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function cookieName() {
  const value = document.cookie.match("(^|;) ?" + "user_name" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}
function cookieLocation() {
  const value = document.cookie.match("(^|;) ?" + "user_location" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function setCookie(data: LoginData) {
  const date = new Date();
  date.setTime(date.getTime() + (60 * 60 * 1000));
  // Set it
  document.cookie = "jwt_auth_token=" + data.token + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "user_id=" + data.id + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "user_name=" + data.name + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "user_location=" + data.location + "; expires=" + date.toUTCString() + "; path=/";
}

function getCookie() {
  const value = token();
  if (value != null) {
    const date = new Date();
    date.setTime(date.getTime() + (60 * 60 * 1000));

    document.cookie = "jwt_auth_token=" + value + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_id=" + cookieId() + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_name=" + cookieName() + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "user_location=" + cookieLocation() + "; expires=" + date.toUTCString() + "; path=/";
    return true;
  } else {
  return false;
  }
}

const cookie = {
  token: () => token(),
  cookieId: () => cookieId(),
  cookieName: () => cookieName(),
  cookieLocation: () => cookieLocation(),
  getCookie: () => getCookie(),
  setCookie: (data: LoginData) => setCookie(data)
}

export default cookie