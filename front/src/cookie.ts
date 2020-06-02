import {CookieRules} from '@/models/rules/CookieRules'

function token() {
  const value = document.cookie.match("(^|;) ?" + "jwt_auth_token" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function cookieUser() {
  const value = document.cookie.match("(^|;) ?" + "jwt_auth_token" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function cookieId() {
  const value = document.cookie.match("(^|;) ?" + "jwt_auth_token" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function cookieName() {
  const value = document.cookie.match("(^|;) ?" + "jwt_auth_token" + "=([^;]*)(;|$)");
  const val = value ? value[2] : null;
  return val;
}

function setCookie(data: CookieRules) {
  const date = new Date();
  date.setTime(date.getTime() + (60 * 60 * 1000));
  // Set it
  document.cookie = "jwt_auth_token=" + data.token + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "login_user=" + data.login + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "login_id=" + data.email + "; expires=" + date.toUTCString() + "; path=/";
  document.cookie = "login_name=" + data.name + "; expires=" + date.toUTCString() + "; path=/";
}

function getCookie() {
  const value = token();
  if (value != null) {
    const date = new Date();
    date.setTime(date.getTime() + (60 * 60 * 1000));

    document.cookie = "jwt_auth_token=" + value + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "login_user=" + cookieUser() + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "login_id=" + cookieId() + "; expires=" + date.toUTCString() + "; path=/";
    document.cookie = "login_name=" + cookieName() + "; expires=" + date.toUTCString() + "; path=/";
    return true;
  } else {
  return false;
  }
}

const cookie = {
  token: () => token(),
  cookieUser: () => cookieUser(),
  cookieId: () => cookieId(),
  cookieName: () => cookieName(),
  getCookie: () => getCookie(),
  setCookie: (data: CookieRules) => setCookie(data)
}

export default cookie