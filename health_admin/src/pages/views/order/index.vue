<script lang="ts" setup>

import type { FormRules } from "element-plus"

import { usePagination } from "@@/composables/usePagination"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search } from "@element-plus/icons-vue"
import { cloneDeep } from "lodash-es"
import { Order } from "@/common/apis/order/type"
import { orderApi } from "@/common/apis/order"
import { QueryPageBean } from "@/http/type"

defineOptions({
  // 命名当前组件
  name: "Order"
})

const loading = ref<boolean>(false)

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

// #region 表单初始值
const DEFAULT_FORM_DATA: Order = {
  id: null,
  memberId: 0,
  orderDate: null,
  orderTime: "",
  type: "",
  status: "",
  setmealId: 0,
  telephone: "",
  visitDate: null,
  memberName: "",
  orderNumber: ""
}
//新增/修改模态窗口的显隐状态控制
const dialogVisible = ref<boolean>(false)

const formRef = useTemplateRef("formRef")

//新增或者编辑的表单数据
const formData = ref<Order>(cloneDeep(DEFAULT_FORM_DATA))

const formRules: FormRules<Order> = {
  memberId: [{ required: true, trigger: "blur", message: "请输入会员ID" }],
  orderDate: [{ required: true, trigger: "change", message: "请选择预约日期" }],
  orderTime: [{ required: true, trigger: "blur", message: "请输入预约时间" }],
  setmealId: [{ required: true, trigger: "blur", message: "请输入套餐ID" }],
  telephone: [{ required: true, trigger: "blur", message: "请输入联系电话" }],
  visitDate: [{ required: true, trigger: "change", message: "请选择到诊日期" }],
  memberName: [{ required: true, trigger: "blur", message: "请输入会员姓名" }]
}

//新增或者是编辑的处理方法
function handleCreateOrUpdate() {
  formRef.value?.validate((valid) => {
    if (!valid) {
      ElMessage.error("表单校验不通过")
      return
    }
    loading.value = true
    const api = formData.value.id === null ? orderApi.add : orderApi.update
    //传递表单数据，发送请求到后端接口
    api(formData.value).then(() => {
      ElMessage.success("操作成功")
      //关闭模态床
      dialogVisible.value = false
    getOrderData()   //重新获取分页数据，刷新页面****************
    }).finally(() => {
      loading.value = false
    })
  })
}
// 点击编辑按钮时，显示模态窗口，将当前行的数据回显到表单中
// 表示的是当前行数据
function handleUpdate(row: Order) {
  dialogVisible.value = true
  formData.value = cloneDeep(row)

  
  }


//重置表单
function resetForm() {
  formRef.value?.clearValidate()
  //将默认数据重新赋值给表单，（清空表单）
  formData.value = cloneDeep(DEFAULT_FORM_DATA)
}
// #endregion

//  删除按钮
function handleDelete(row: Order) {
  ElMessageBox.confirm(`正在删除订单：${row.orderNumber}，确认删除？`, "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {

    //row.id!排除空值   order的id有可能为null
    orderApi.delete(row.id!).then(() => {
      ElMessage.success("删除成功")
      getOrderData()//重新获取页面数据
    }).catch(() => {
      // 错误处理已在 request 拦截器中统一处理
    })
  })
}
// #endregion


// #endregion

// 查询
//当前页面的订单数据
const orderData = ref<Order[]>([])

//搜索的表单
const searchFormRef = useTemplateRef("searchFormRef")

//搜索的表单的数据：根据这个字段，模糊查询数据库的orderNumber、memberName和telephone
const searchData = reactive({
  querystring: ""
})

const getOrderData = () => {
  loading.value = true
//请求参数
  let params: QueryPageBean = {
    pageNum: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    queryString: searchData.querystring

  }

  //发送后端请求：查询分页数据
  orderApi.pageQuery(params).then(Response=>{
    if(Response.code==2000){
      paginationData.total=Response.data.total//后端响应回来的中记录数
      orderData.value=Response.data.rows
    }else{
      ElMessage.error(Response.message || "获取分页数据失败")
      orderData.value=[]
    }
}).catch((err) => {
  console.error("API·ERROR:",err)
  orderData.value = []
}).finally(() => {
  loading.value = false
})
}



function handleSearch() {
  paginationData.currentPage === 1 ? getOrderData() : (paginationData.currentPage = 1)
}

function resetSearch() {
  searchFormRef.value?.resetFields()
  handleSearch()
}
// #endregion

// 监听分页参数的变化
watch([() => paginationData.currentPage, () => paginationData.pageSize], getOrderData, { immediate: true })
</script>

<template>
  <div class="app-container">
    <el-alert title="数据来源" type="success" description="由 Apifox 提供在线 Mock，数据不具备真实性，仅供简单的 CRUD 操作演示" show-icon />
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="搜索">
          <el-input v-model="searchData.querystring" placeholder="请输入订单信息" />
        </el-form-item>
       
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleSearch">
            查询
          </el-button>
          <el-button :icon="Refresh" @click="resetSearch">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card v-loading="loading" shadow="never">
      <div class="toolbar-wrapper">
        <div>
          <!-- 点击显示模态框 -->
          <el-button type="primary" :icon="CirclePlus" @click="dialogVisible = true">
            新增订单
          </el-button>
          <el-button type="danger" :icon="Delete">
            批量删除
          </el-button>
        </div>
        <div>
          <el-tooltip content="下载">
            <el-button type="primary" :icon="Download" circle />
          </el-tooltip>
          <el-tooltip content="刷新当前页">
            <el-button type="primary" :icon="RefreshRight" circle @click="getOrderData" />
          </el-tooltip>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table :data="orderData">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="orderNumber" label="订单号" align="center" />
          <el-table-column prop="memberName" label="会员姓名" align="center" />
          <el-table-column prop="telephone" label="联系电话" align="center" />
          <el-table-column prop="orderDate" label="预约日期" align="center" />
          <el-table-column prop="orderTime" label="预约时间" align="center" />
          <el-table-column prop="visitDate" label="到诊日期" align="center" />
          <el-table-column prop="type" label="订单类型" align="center" />
          <el-table-column prop="status" label="订单状态" align="center" />

          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="handleUpdate(scope.row)">
                修改
              </el-button>
              <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">
                删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="pager-wrapper">
        <el-pagination background :layout="paginationData.layout" :page-sizes="paginationData.pageSizes"
          :total="paginationData.total" :page-size="paginationData.pageSize" :current-page="paginationData.currentPage"
          @size-change="handleSizeChange" @current-change="handleCurrentChange" />
      </div>
    </el-card>
    <!-- 新增/修改 模态窗口 -->
    <el-dialog v-model="dialogVisible" :title="formData.id === undefined ? '新增订单' : '修改订单'" width="30%"
      @closed="resetForm">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
  <el-form-item prop="memberId" label="会员ID">
    <el-input v-model.number="formData.memberId" placeholder="请输入" type="number" />
  </el-form-item>

  <el-form-item prop="memberName" label="会员姓名">
    <el-input v-model="formData.memberName" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="telephone" label="联系电话">
    <el-input v-model="formData.telephone" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="orderDate" label="预约日期">
    <el-date-picker v-model="formData.orderDate" type="date" placeholder="选择日期" style="width: 100%" />
  </el-form-item>

  <el-form-item prop="orderTime" label="预约时间">
    <el-input v-model="formData.orderTime" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="visitDate" label="到诊日期">
    <el-date-picker v-model="formData.visitDate" type="date" placeholder="选择日期" style="width: 100%" />
  </el-form-item>

  <el-form-item prop="setmealId" label="套餐ID">
    <el-input v-model.number="formData.setmealId" placeholder="请输入" type="number" />
  </el-form-item>

  <el-form-item prop="type" label="订单类型">
    <el-input v-model="formData.type" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="status" label="订单状态">
    <el-input v-model="formData.status" placeholder="请输入" />
  </el-form-item>
</el-form>
<!-- 确认取消按钮 -->
      <template #footer>
        <el-button @click="dialogVisible = false">
          取消
        </el-button>
        <el-button type="primary" :loading="loading" @click="handleCreateOrUpdate">
          确认
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped>
.el-alert {
  margin-bottom: 20px;
}

.search-wrapper {
  margin-bottom: 20px;

  :deep(.el-card__body) {
    padding-bottom: 2px;
  }
}

.toolbar-wrapper {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.table-wrapper {
  margin-bottom: 20px;
}

.pager-wrapper {
  display: flex;
  justify-content: flex-end;
}
</style>