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
}