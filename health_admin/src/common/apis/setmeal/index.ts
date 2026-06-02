import { PageResult, QueryPageBean, RequestParams, Result } from "@/http/type"
import { request } from "@/http/axios"
import { Setmeal } from "./type"

/**
 * 套餐api接口，发送请求给后端接口
 */
export const setmealApi = {
  /**
   * 分页查询套餐
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<Setmeal>>> => {
    return request({
      url: "/setmeals/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增或更新套餐
   * @param params 套餐信息和关联的检查组ID列表
   * @returns 操作结果
   */
  addorupdate: (params: RequestParams<Setmeal>): Promise<Result<null>> => {
    return request({
      url: "/setmeals",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除套餐
   * @param id 套餐ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/setmeals/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询套餐
   * @param id 套餐ID
   * @returns 套餐信息
   */
  getById: (id: number): Promise<Result<Setmeal>> => {
    return request({
      url: `/setmeals/${id}`,
      method: "GET"
    })
  },

  /**
   * 根据ID查询套餐关联的检查组ID列表
   * @param id 套餐ID
   * @returns 检查组ID列表
   */
  findCheckGroupIdsBySetmealId: (id: number): Promise<Result<number[]>> => {
    return request({
      url: `/setmeals/checkGroupIds/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有套餐
   * @returns 套餐列表
   */
  findAll: (): Promise<Result<Setmeal[]>> => {
    return request({
      url: `/setmeals/all`,
      method: "GET"
    })
  },

  /**
   * 上传套餐图片
   * @param file 图片文件
   * @returns 上传结果和图片URL
   */
  upload: (file: File): Promise<Result<string>> => {
    const formData = new FormData()
    formData.append("file", file)

    return request({
      url: `/setmeals/upload`,
      method: "POST",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data"
      }
    })
  }
}