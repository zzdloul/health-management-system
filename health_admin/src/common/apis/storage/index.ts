import { PageResult, QueryPageBean, Result } from "@/http/type"
import { request } from "@/http/axios"
import { FileInfo } from "./type"

/**
 * 文件存储api接口，发送请求给后端接口
 */
export const storageApi = {
  /**
   * 上传文件
   * @param file 文件对象
   * @returns 上传结果和文件URL
   */
  upload: (file: File): Promise<Result<{ url: string; filename: string }>> => {
    const formData = new FormData()
    formData.append("file", file)

    return request({
      url: `/storage/upload`,
      method: "POST",
      data: formData,
      headers: {
        "Content-Type": "multipart/form-data"
      }
    })
  },

  /**
   * 下载文件
   * @param id 文件ID
   * @returns 文件流
   */
  download: (id: number): Promise<Blob> => {
    return request({
      url: `/storage/download/${id}`,
      method: "GET",
      responseType: "blob"
    })
  },

  /**
   * 分页查询文件列表
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<FileInfo>>> => {
    return request({
      url: `/storage/list`,
      method: "GET",
      params: params
    })
  },

  /**
   * 查询所有文件
   * @returns 文件列表
   */
  findAll: (): Promise<Result<FileInfo[]>> => {
    return request({
      url: `/storage/list`,
      method: "GET"
    })
  }
}
