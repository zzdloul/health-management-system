import { PageResult, QueryPageBean, Result } from "@/http/type"
import { request } from "@/http/axios"
import { Order, OrderSetting } from "./type"

/**
 * 订单api接口，发送请求给后端接口
 */
export const orderApi = {
  /**
   * 分页查询订单
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<Order>>> => {
    return request({
      url: "/orders/pageQuery",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增订单
   * @param params 订单信息
   * @returns 操作结果
   */
  add: (params: Order): Promise<Result<null>> => {
    return request({
      url: "/orders",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新订单
   * @param params 订单信息
   * @returns 操作结果
   */
  update: (params: Order): Promise<Result<null>> => {
    return request({
      url: "/orders",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除订单
   * @param id 订单ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/orders/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询订单
   * @param id 订单ID
   * @returns 订单信息
   */
  getById: (id: number): Promise<Result<Order>> => {
    return request({
      url: `/orders/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有订单
   * @returns 订单列表
   */
  findAll: (): Promise<Result<Order[]>> => {
    return request({
      url: `/orders/all`,
      method: "GET"
    })
  }
}

/**
 * 预约设置api接口，发送请求给后端接口
 */
export const orderSettingApi = {
  /**
   * 分页查询预约设置
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<OrderSetting>>> => {
    return request({
      url: "/ordersettings",
      method: "GET",
      params: {
        pageNum: params.pageNum,
        pageSize: params.pageSize,
        queryString: params.queryString
      }
    })
  },

  /**
   * 新增或更新预约设置
   * @param params 预约设置信息
   * @returns 操作结果
   */
  addOrUpdate: (params: OrderSetting): Promise<Result<null>> => {
    return request({
      url: "/ordersettings",
      method: params.id ? "PUT" : "POST",
      data: params
    })
  },

  /**
   * 删除预约设置
   * @param id 预约设置ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/ordersettings/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询预约设置
   * @param id 预约设置ID
   * @returns 预约设置信息
   */
  getById: (id: number): Promise<Result<OrderSetting>> => {
    return request({
      url: `/ordersettings/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有预约设置
   * @returns 预约设置列表
   */
  findAll: (): Promise<Result<OrderSetting[]>> => {
    return request({
      url: `/ordersettings/all`,
      method: "GET"
    })
  }
}
