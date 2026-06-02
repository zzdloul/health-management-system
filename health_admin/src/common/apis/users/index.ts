import type * as Users from "./type"
import { request } from "@/http/axios"

/** 获取当前登录用户详情 */
export function getCurrentUserApi() {
  // return request<Users.CurrentUserResponseData>({
  //   url: "users/me",
  //   method: "get"
  // })

  
  // 暂时返回模拟的数据
return Promise.resolve({
  code: 2000,
  data: {
    username: "admin",
    roles: ["admin"]
  },
  message:"success"
})
}
