// 健康评估类型定义

// 评估基础信息
export interface Assessment {
  id?: number | null
  memberId?: number
  assessmentDate?: string
  totalScore?: number
  result?: string
  advice?: string
  updateTime?: string
}

// 评估问题信息
export interface AssessmentQuestion {
  id?: number | null
  question?: string
  type?: string
  options?: string
  score?: number
}

// 评估答案信息
export interface AssessmentAnswer {
  id?: number | null
  assessmentId?: number
  questionId?: number
  answer?: string
  score?: number
}
