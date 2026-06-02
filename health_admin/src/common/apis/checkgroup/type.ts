import { CheckItem } from "../checkitem/type"
// 检查组基础信息
export interface CheckGroup {
  id?: number |null
  code?: string
  name?: string
  helpCode?: string
  sex?: string
  remark?: string
  attention?: string
  checkItems?: CheckItem[]
}
