import axios from "axios";
import cookie from "@/cookie"

export default axios.create({
  baseURL: "http://13.125.209.188:8080/api",
  headers: {
    "X-AUTH-TOKEN": cookie.token()
  }
})
