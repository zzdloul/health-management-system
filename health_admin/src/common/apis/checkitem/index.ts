import { PageResult, QueryPageBean, Result } from "@/http/type"
import { request } from "@/http/axios"
import { CheckItem } from "./type"

/**
 * 检查项api接口，发送请求给后端接口
 */
export const checkitemApi = {
  /**
   * 分页查询检查项
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<CheckItem>>> => {
    return request({
      url: "/checkItems/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增检查项
   * @param params 检查项信息
   * @returns 操作结果
   */
  add: (params: CheckItem): Promise<Result<null>> => {
    return request({
      url: "/checkItems",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新检查项
   * @param params 检查项信息
   * @returns 操作结果
   */
  update: (params: CheckItem): Promise<Result<null>> => {
    return request({
      url: "/checkItems",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除检查项
   * @param id 检查项ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/checkItems/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 查询所有检查项
   * @returns 检查项列表
   */
  findAll: (): Promise<Result<CheckItem[]>> => {
    return request({
      url: `/checkItems/all`,
      method: "GET"
    })
  }
}