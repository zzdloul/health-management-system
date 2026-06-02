<script lang="ts" setup>

import type { FormRules } from "element-plus"

import { usePagination } from "@@/composables/usePagination"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search } from "@element-plus/icons-vue"
import { cloneDeep } from "lodash-es"
import { CheckItem } from "@/common/apis/checkitem/type"
import { checkitemApi } from "@/common/apis/checkitem"
import { QueryPageBean } from "@/http/type"

defineOptions({
  // 命名当前组件
  name: "CheckItem"
})

const loading = ref<boolean>(false)

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

// #region 表单初始值
const DEFAULT_FORM_DATA: CheckItem = {
  id: null,
  code: "",
  name: "",
  sex: "",
  age: "",
  price: 0,
  type: "",
  attention: "",
  remark: ""
}
//新增/修改模态窗口的显隐状态控制
const dialogVisible = ref<boolean>(false)

const formRef = useTemplateRef("formRef")

//新增或者编辑的表单数据
const formData = ref<CheckItem>(cloneDeep(DEFAULT_FORM_DATA))

const formRules: FormRules<CheckItem> = {
  code: [{ required: true, trigger: "blur", message: "请输入检查项编码" }],
  name: [{ required: true, trigger: "blur", message: "请输入检查项名称" }],
  price: [{ required: true, trigger: "blur", message: "请输入检查项价格" }],
  type: [{ required: true, trigger: "blur", message: "请输入检查项类型" }],
}

//新增或者是编辑的处理方法
function handleCreateOrUpdate() {
  formRef.value?.validate((valid) => {
    if (!valid) {
      ElMessage.error("表单校验不通过")
      return
    }
    loading.value = true
    const api = formData.value.id === null ? checkitemApi.add : checkitemApi.update
    //传递表单数据，发送请求到后端接口
    api(formData.value).then(() => {
      ElMessage.success("操作成功")
      //关闭模态床
      dialogVisible.value = false
    getCheckItemsData()   //重新获取分页数据，刷新页面****************
    }).finally(() => {
      loading.value = false
    })
  })
}
// 点击编辑按钮时，显示模态窗口，将当前行的数据回显到表单中
// 表示的是当前行数据
function handleUpdate(row: CheckItem) {
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
function handleDelete(row: CheckItem) {
  ElMessageBox.confirm(`正在删除检查项：${row.name}，确认删除？`, "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {

    //row.id!排除空值   checkItem的id有可能为null
    checkitemApi.delete(row.id!).then(() => {
      ElMessage.success("删除成功")
      getCheckItemsData()//重新获取页面数据
    }).catch(() => {
      // 错误处理已在 request 拦截器中统一处理
    })
  })
}
// #endregion


// #endregion

// 查询
//当前页面的检查项数据
const checkItemsData = ref<CheckItem[]>([])

//搜索的表单
const searchFormRef = useTemplateRef("searchFormRef")

//搜索的表单的数据：根据这个字段，模糊查询数据库的code、name和remark
const searchData = reactive({
  querystring: ""
})

const getCheckItemsData = () => {
  loading.value = true
//请求参数
  let params: QueryPageBean = {
    pageNum: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    queryString: searchData.querystring

  }

  //发送后端请求：查询分页数据
  checkitemApi.pageQuery(params).then(Response=>{
    if(Response.code==2000){
      paginationData.total=Response.data.total//后端响应回来的中记录数
      checkItemsData.value=Response.data.rows
    }else{
      ElMessage.error(Response.message || "获取分页数据失败")
      checkItemsData.value=[]
    }
}).catch((err) => {
  console.error("API·ERROR:",err)
  checkItemsData.value = []
}).finally(() => {
  loading.value = false
})
}



function handleSearch() {
  paginationData.currentPage === 1 ? getCheckItemsData() : (paginationData.currentPage = 1)
}

function resetSearch() {
  searchFormRef.value?.resetFields()
  handleSearch()
}
// #endregion

// 监听分页参数的变化
watch([() => paginationData.currentPage, () => paginationData.pageSize], getCheckItemsData, { immediate: true })
</script>

<template>
  <div class="app-container">
    <el-alert title="数据来源" type="success" description="由 Apifox 提供在线 Mock，数据不具备真实性，仅供简单的 CRUD 操作演示" show-icon />
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="搜索">
          <el-input v-model="searchData.querystring" placeholder="请输入检查项" />
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
            新增用户
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
            <el-button type="primary" :icon="RefreshRight" circle @click="getCheckItemsData" />
          </el-tooltip>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table :data="checkItemsData">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="code" label="编码" align="center" />
          <el-table-column prop="name" label="名称" align="center" />
<!-- 适用性别 -->
          <el-table-column prop="sex" label="性别" align="center" >
<template #default="scope">
  <el-tag v-if="scope.row.sex == 0" type="primary" effect="plain" disable-transitions>不限</el-tag>
  <el-tag v-if="scope.row.sex == 1" type="success" effect="plain" disable-transitions>男</el-tag>
  <el-tag v-if="scope.row.sex == 2" type="warning" effect="plain" disable-transitions>女</el-tag>
</template>
</el-table-column>


<el-table-column prop="age" label="适用年龄" align="center" />
<el-table-column prop="price" label="价格" align="center" />

<!-- 检查类型 -->
<el-table-column prop="type" label="检查类型" align="center">
  <template #default="scope">
    <el-tag v-if="scope.row.type == 1" type="primary" effect="plain" disable-transitions>常规检查</el-tag>
    <el-tag v-if="scope.row.type== 2" type="warning" effect="plain" disable-transitions>生化检查</el-tag>
    <el-tag v-if="scope.row.type== 3" type="info" effect="plain" disable-transitions>印象检查</el-tag>
  </template>
</el-table-column>
<el-table-column prop="remark" label="说明" align="center" />


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
    <el-dialog v-model="dialogVisible" :title="formData.id === undefined ? '新增检查项' : '修改检查项'" width="30%"
      @closed="resetForm">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
  <el-form-item prop="code" label="编码">
    <el-input v-model="formData.code" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="name" label="名称">
    <el-input v-model="formData.name" placeholder="请输入" />
  </el-form-item>
  <!-- 适用性别 -->
<el-form-item prop="sex" label="适用性别">
  <el-select v-model="formData.sex" placeholder="请选择">
<el-option label="不限" value="0" />
<el-option label="男" value="1" />
<el-option label="女" value="2" />
  </el-select>
</el-form-item>
<!-- 年龄 -->
<el-form-item prop="age" label="年龄">
  <el-input v-model="formData.age" placeholder="请输入" />
</el-form-item>
<!-- 价格 -->
<el-form-item prop="price" label="价格">
  <el-input v-model="formData.price" placeholder="请输入" />
</el-form-item>
<!-- 检查类型 -->
<el-form-item prop="type" label="检查类型">
  <el-select v-model="formData.type" placeholder="请选择检查类型" clearable>
    <el-option label="常规检查" :value="1" />
    <el-option label="生化检查" :value="2" />
    <el-option label="影像检查" :value="3" />
    <el-option label="其它" :value="0" />
  </el-select>
</el-form-item>
<!-- 注意事项 -->
<el-form-item prop="attention" label="注意事项">
  <el-input v-model="formData.attention" placeholder="请输入" type="textarea" />
</el-form-item>
<!-- 备注 -->
<el-form-item prop="remark" label="备注">
  <el-input v-model="formData.remark" placeholder="请输入" type="textarea"/>
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
