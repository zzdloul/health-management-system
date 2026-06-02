// 统一响应结果
export interface Result<T> {
  code: number
  message: string
  data: T
}

// 分页查询参数
export interface QueryPageBean {
  pageNum?: number
  pageSize?: number
  queryString?: string
}

// 分页响应数据
export interface PageResult<T> {
  rows: T[]
  total: number
}
// 通用请求参数
export interface RequestParams<T> {
  data: T; // 检查组 | 套餐 的 基础信息
  ids: number[]; // 关联的检查项 | 检查组 的id数组
}