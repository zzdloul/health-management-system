import type * as Auth from "./type"
import { request } from "@/http/axios"

/** 获取登录验证码 */
export function getCaptchaApi() {
  // return request<Auth.CaptchaResponseData>({
  //   url: "auth/captcha",
  //   method: "get"
  // })
  return Promise.resolve({
  code: 2000,
  data: "data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyMDAiIGhlaWdodD0iNTAiPgo8cmVjdCB3aWR0aD0iMjAwIiBoZWlnaHQ9IjUwIiBmaWxsPSIjZjVmNWY1Ii8+Cjx0ZXh0IHg9IjEwMCIgeT0iMzAiIGZvbnQtZmFtaWx5PSJBcmlhbCIgZm9udC1zaXplPSIxNCIgZG9taW5hbnQtYmFzZWxpbmU9Im1pZGRsZSI+Q09MT1JEPC90ZXh0Pgo8L3N2Zz4=",
  message:"success"
})
}

/** 登录并返回 Token */
export function loginApi(data: Auth.LoginRequestData) {
  // return request<Auth.LoginResponseData>({
  //   url: "auth/login",
  //   method: "post",
  //   data
  // })
  return Promise.resolve({
  code: 2000,
  data: {token:"token-admin"},
  message:"success"
})
}
