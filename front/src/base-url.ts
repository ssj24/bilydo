import axios from "axios";
import cookie from "@/cookie"

export default axios.create({
  baseURL: "https://k02b2071.p.ssafy.io/api",
  headers: {
    "X-AUTH-TOKEN": cookie.token()
  }
})
