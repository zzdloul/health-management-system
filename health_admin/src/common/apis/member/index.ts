import { PageResult, QueryPageBean, Result } from "@/http/type"
import { request } from "@/http/axios"
import { Member } from "./type"

/**
 * 会员api接口，发送请求给后端接口
 */
export const memberApi = {
  /**
   * 分页查询会员
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<Member>>> => {
    return request({
      url: "/members/pageQuery",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增会员
   * @param params 会员信息
   * @returns 操作结果
   */
  add: (params: Member): Promise<Result<null>> => {
    return request({
      url: "/members",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新会员
   * @param params 会员信息
   * @returns 操作结果
   */
  update: (params: Member): Promise<Result<null>> => {
    return request({
      url: "/members",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除会员
   * @param id 会员ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/members/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询会员
   * @param id 会员ID
   * @returns 会员信息
   */
  getById: (id: number): Promise<Result<Member>> => {
    return request({
      url: `/members/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有会员
   * @returns 会员列表
   */
  findAll: (): Promise<Result<Member[]>> => {
    return request({
      url: `/members/all`,
      method: "GET"
    })
  }
}
