import { PageResult, QueryPageBean, RequestParams, Result } from "@/http/type"
import { request } from "@/http/axios"
import { CheckGroup } from "./type"

/**
 * 检查组api接口，发送请求给后端接口
 */
export const checkgroupApi = {
  /**
   * 分页查询检查组
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<CheckGroup>>> => {
    return request({
      url: "/checkGroups/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增或更新检查组
   * @param params 检查组信息和关联的检查项ID列表
   * @returns 操作结果
   */
  addorupdate: (params: RequestParams<CheckGroup>): Promise<Result<null>> => {
    return request({
      url: "/checkGroups",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除检查组
   * @param id 检查组ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/checkGroups/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询检查组
   * @param id 检查组ID
   * @returns 检查组信息
   */
  getById: (id: number): Promise<Result<CheckGroup>> => {
    return request({
      url: `/checkGroups/${id}`,
      method: "GET"
    })
  },

  /**
   * 根据ID查询检查组关联的检查项ID列表
   * @param id 检查组ID
   * @returns 检查项ID列表
   */
  findCheckItemIdsByCheckGroupId: (id: number): Promise<Result<number[]>> => {
    return request({
      url: `/checkGroups/checkItemIds/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有检查组
   * @returns 检查组列表
   */
  findAll: (): Promise<Result<CheckGroup[]>> => {
    return request({
      url: `/checkGroups/all`,
      method: "GET"
    })
  }
}