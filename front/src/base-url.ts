import axios from "axios";
// import cookie from "@/cookie"

export default axios.create({
  baseURL: "https://localhost:8888/api",
  // headers: {
  //   "jwt_auth_token": cookie.token()
  // }
})
