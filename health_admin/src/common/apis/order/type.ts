// 订单类型定义

// 订单基础信息
export interface Order {
  id?: number | null
  memberId?: number
  orderDate?: string
  orderTime?: string
  type?: string
  status?: string
  setmealId?: number
  telephone?: string
  visitDate?: string
  memberName?: string
  orderNumber?: string
}

// 预约设置信息
export interface OrderSetting {
  id?: number | null
  orderDate?: string
  number?: number
  reservations?: number
}
