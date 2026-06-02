import { PageResult, QueryPageBean, Result } from "@/http/type"
import { request } from "@/http/axios"
import { Assessment, AssessmentQuestion, AssessmentAnswer } from "./type"

/**
 * 健康评估api接口，发送请求给后端接口
 */
export const assessmentApi = {
  /**
   * 分页查询评估
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<Assessment>>> => {
    return request({
      url: "/assessments/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增评估
   * @param params 评估信息
   * @returns 操作结果
   */
  add: (params: Assessment): Promise<Result<null>> => {
    return request({
      url: "/assessments",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新评估
   * @param params 评估信息
   * @returns 操作结果
   */
  update: (params: Assessment): Promise<Result<null>> => {
    return request({
      url: "/assessments",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除评估
   * @param id 评估ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/assessments/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询评估
   * @param id 评估ID
   * @returns 评估信息
   */
  getById: (id: number): Promise<Result<Assessment>> => {
    return request({
      url: `/assessments/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有评估
   * @returns 评估列表
   */
  findAll: (): Promise<Result<Assessment[]>> => {
    return request({
      url: `/assessments/all`,
      method: "GET"
    })
  }
}

/**
 * 评估问题api接口，发送请求给后端接口
 */
export const assessmentQuestionApi = {
  /**
   * 分页查询评估问题
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<AssessmentQuestion>>> => {
    return request({
      url: "/assessmentQuestions/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增评估问题
   * @param params 评估问题信息
   * @returns 操作结果
   */
  add: (params: AssessmentQuestion): Promise<Result<null>> => {
    return request({
      url: "/assessmentQuestions",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新评估问题
   * @param params 评估问题信息
   * @returns 操作结果
   */
  update: (params: AssessmentQuestion): Promise<Result<null>> => {
    return request({
      url: "/assessmentQuestions",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除评估问题
   * @param id 评估问题ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/assessmentQuestions/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询评估问题
   * @param id 评估问题ID
   * @returns 评估问题信息
   */
  getById: (id: number): Promise<Result<AssessmentQuestion>> => {
    return request({
      url: `/assessmentQuestions/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有评估问题
   * @returns 评估问题列表
   */
  findAll: (): Promise<Result<AssessmentQuestion[]>> => {
    return request({
      url: `/assessmentQuestions/all`,
      method: "GET"
    })
  }
}

/**
 * 评估答案api接口，发送请求给后端接口
 */
export const assessmentAnswerApi = {
  /**
   * 分页查询评估答案
   * @param params 查询参数
   * @returns 分页结果
   */
  pageQuery: (params: QueryPageBean): Promise<Result<PageResult<AssessmentAnswer>>> => {
    return request({
      url: "/assessmentAnswers/page",
      method: "POST",
      data: params
    })
  },

  /**
   * 新增评估答案
   * @param params 评估答案信息
   * @returns 操作结果
   */
  add: (params: AssessmentAnswer): Promise<Result<null>> => {
    return request({
      url: "/assessmentAnswers",
      method: "POST",
      data: params
    })
  },

  /**
   * 更新评估答案
   * @param params 评估答案信息
   * @returns 操作结果
   */
  update: (params: AssessmentAnswer): Promise<Result<null>> => {
    return request({
      url: "/assessmentAnswers",
      method: "PUT",
      data: params
    })
  },

  /**
   * 删除评估答案
   * @param id 评估答案ID
   * @returns 操作结果
   */
  delete: (id: number): Promise<Result<null>> => {
    return request({
      url: `/assessmentAnswers/${id}`,
      method: "DELETE"
    })
  },

  /**
   * 根据ID查询评估答案
   * @param id 评估答案ID
   * @returns 评估答案信息
   */
  getById: (id: number): Promise<Result<AssessmentAnswer>> => {
    return request({
      url: `/assessmentAnswers/${id}`,
      method: "GET"
    })
  },

  /**
   * 查询所有评估答案
   * @returns 评估答案列表
   */
  findAll: (): Promise<Result<AssessmentAnswer[]>> => {
    return request({
      url: `/assessmentAnswers/all`,
      method: "GET"
    })
  }
}
