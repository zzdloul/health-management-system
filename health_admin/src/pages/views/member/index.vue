<script lang="ts" setup>

import type { FormRules } from "element-plus"

import { usePagination } from "@@/composables/usePagination"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search } from "@element-plus/icons-vue"
import { cloneDeep } from "lodash-es"
import { Member } from "@/common/apis/member/type"
import { memberApi } from "@/common/apis/member"
import { QueryPageBean } from "@/http/type"

defineOptions({
  // 命名当前组件
  name: "Member"
})

const loading = ref<boolean>(false)

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

// #region 表单初始值
const DEFAULT_FORM_DATA: Member = {
  id: null,
  fileNumber: "",
  name: "",
  gender: "",
  phoneNumber: "",
  idCard: "",
  birthday: undefined,
  address: ""
}
//新增/修改模态窗口的显隐状态控制
const dialogVisible = ref<boolean>(false)

const formRef = useTemplateRef("formRef")

//新增或者编辑的表单数据
const formData = ref<Member>(cloneDeep(DEFAULT_FORM_DATA))

const formRules: FormRules<Member> = {
  fileNumber: [{ required: false, trigger: "blur", message: "请输入文件编号" }],
  name: [{ required: false, trigger: "blur", message: "请输入会员姓名" }],
  phoneNumber: [{ required: false, trigger: "blur", message: "请输入手机号" }],
  idCard: [{ required: false, trigger: "blur", message: "请输入身份证号" }],
  birthday: [{ required: false, trigger: "change", message: "请选择出生日期" }]
}

//新增或者是编辑的处理方法
function handleCreateOrUpdate() {
  formRef.value?.validate((valid) => {
    if (!valid) {
      ElMessage.error("表单校验不通过")
      return
    }
    loading.value = true
    const api = formData.value.id === null || formData.value.id === undefined ? memberApi.add : memberApi.update
    //传递表单数据，发送请求到后端接口
    // 转换数据格式，将gender字段重命名为sex
    const requestData = {
      ...formData.value,
      sex: formData.value.gender,
      fileNumber: formData.value.fileNumber || ''
    }
    // 删除gender字段，因为后端期望的是sex字段
    delete requestData.gender
    
    api(requestData).then(() => {
      ElMessage.success("操作成功")
      //关闭模态床
      dialogVisible.value = false
      getMemberData()   //重新获取分页数据，刷新页面****************
    }).finally(() => {
      loading.value = false
    })
  })
}
// 点击编辑按钮时，显示模态窗口，将当前行的数据回显到表单中
// 表示的是当前行数据
function handleUpdate(row: Member) {
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
function handleDelete(row: Member) {
  ElMessageBox.confirm(`正在删除会员：${row.name}，确认删除？`, "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning"
  }).then(() => {

    //row.id!排除空值   member的id有可能为null
    memberApi.delete(row.id!).then(() => {
      ElMessage.success("删除成功")
      getMemberData()//重新获取页面数据
    }).catch((error) => {
      console.error("Delete error:", error)
      ElMessage.error("删除失败：" + (error.response?.data?.message || "操作失败"))
    })
  })
}
// #endregion


// #endregion

// 查询
//当前页面的会员数据
const memberData = ref<Member[]>([])

//搜索的表单
const searchFormRef = useTemplateRef("searchFormRef")

//搜索的表单的数据：根据这个字段，模糊查询数据库的name、phoneNumber和idCard
const searchData = reactive({
  querystring: ""
})

const getMemberData = () => {
  loading.value = true
//请求参数
  let params: QueryPageBean = {
    pageNum: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    queryString: searchData.querystring

  }

  //发送后端请求：查询分页数据
  console.log('Request params:', params)
  memberApi.pageQuery(params).then(Response=>{
    console.log('Response:', Response)
    if(Response.code==2000){
      paginationData.total=Response.data.total//后端响应回来的中记录数
      // 转换数据格式，将sex字段重命名为gender
      memberData.value=Response.data.rows.map(item => ({
        ...item,
        gender: item.sex,
        fileNumber: item.fileNumber || ''
      }))
      console.log('Member data:', memberData.value)
      console.log('Member data length:', memberData.value.length)
    }else{
      ElMessage.error(Response.message || "获取分页数据失败")
      memberData.value=[]
    }
}).catch((err) => {
  console.error("API·ERROR:",err)
  memberData.value = []
}).finally(() => {
  loading.value = false
})
}



function handleSearch() {
  paginationData.currentPage === 1 ? getMemberData() : (paginationData.currentPage = 1)
}

function resetSearch() {
  searchFormRef.value?.resetFields()
  handleSearch()
}
// #endregion

// 监听分页参数的变化
watch([() => paginationData.currentPage, () => paginationData.pageSize], getMemberData, { immediate: true })
</script>

<template>
  <div class="app-container">
    <el-alert title="数据来源" type="success" description="由 Apifox 提供在线 Mock，数据不具备真实性，仅供简单的 CRUD 操作演示" show-icon />
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="搜索">
          <el-input v-model="searchData.querystring" placeholder="请输入会员信息" />
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
            新增会员
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
            <el-button type="primary" :icon="RefreshRight" circle @click="getMemberData" />
          </el-tooltip>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table :data="memberData">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="fileNumber" label="文件编号" align="center" />
          <el-table-column prop="name" label="姓名" align="center" />
<!-- 性别 -->
          <el-table-column prop="gender" label="性别" align="center" >
<template #default="scope">
  <el-tag v-if="scope.row.gender == '1'" type="primary" effect="plain" disable-transitions>男</el-tag>
  <el-tag v-if="scope.row.gender == '2'" type="warning" effect="plain" disable-transitions>女</el-tag>
</template>
</el-table-column>


<el-table-column prop="phoneNumber" label="手机号" align="center" />
<el-table-column prop="idCard" label="身份证号" align="center" />
<el-table-column prop="birthday" label="出生日期" align="center" />
<el-table-column prop="address" label="地址" align="center" />
<el-table-column prop="regTime" label="注册时间" align="center" />

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
    <el-dialog v-model="dialogVisible" :title="formData.id === undefined ? '新增会员' : '修改会员'" width="30%"
      @closed="resetForm">
      <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
  <el-form-item prop="fileNumber" label="文件编号">
    <el-input v-model="formData.fileNumber" placeholder="请输入" />
  </el-form-item>

  <el-form-item prop="name" label="姓名">
    <el-input v-model="formData.name" placeholder="请输入" />
  </el-form-item>
  <!-- 性别 -->
<el-form-item prop="gender" label="性别">
  <el-select v-model="formData.gender" placeholder="请选择">
<el-option label="男" value="1" />
<el-option label="女" value="2" />
  </el-select>
</el-form-item>
<!-- 手机号 -->
<el-form-item prop="phoneNumber" label="手机号">
  <el-input v-model="formData.phoneNumber" placeholder="请输入" />
</el-form-item>
<!-- 身份证号 -->
<el-form-item prop="idCard" label="身份证号">
  <el-input v-model="formData.idCard" placeholder="请输入" />
</el-form-item>
<!-- 出生日期 -->
<el-form-item prop="birthday" label="出生日期">
  <el-date-picker v-model="formData.birthday" type="date" placeholder="选择日期" style="width: 100%" />
</el-form-item>
<!-- 地址 -->
<el-form-item prop="address" label="地址">
  <el-input v-model="formData.address" placeholder="请输入" type="textarea" />
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