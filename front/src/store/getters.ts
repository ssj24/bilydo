import cookie from "@/cookie";

export default {
  isLogin: () => {
    if (cookie.token() == null) {
      return false;
    }
    if (cookie.token()) {
      return true;
    } else {
      return false;
    }
  },
  userToken: () => {
    return cookie.token();
  },
  userName: () => {
    return cookie.cookieName();
  },
  userId: () => {
    return Number(cookie.cookieId());
  },
  userLocation: () => {
    return cookie.cookieLocation();
  },
}