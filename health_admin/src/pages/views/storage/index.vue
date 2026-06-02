<script lang="ts" setup>

import type { FormRules } from "element-plus"

import { usePagination } from "@@/composables/usePagination"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search, Upload } from "@element-plus/icons-vue"
import { cloneDeep } from "lodash-es"
import { StorageFile } from "@/common/apis/storage/type"
import { storageApi } from "@/common/apis/storage"
import { QueryPageBean } from "@/http/type"

defineOptions({
  // 命名当前组件
  name: "Storage"
})

const loading = ref<boolean>(false)

const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

// #region 文件上传
const uploadLoading = ref<boolean>(false)

const handleFileUpload = (file: File) => {
  uploadLoading.value = true
  storageApi.upload(file).then((response) => {
    if (response.code === 2000) {
      ElMessage.success("文件上传成功")
      getStorageData() // 重新获取文件列表
    } else {
      ElMessage.error(response.message || "文件上传失败")
    }
  }).finally(() => {
    uploadLoading.value = false
  })
  return false // 阻止自动上传
}

const handleDownload = (file: StorageFile) => {
  storageApi.download(file.id!).then((response) => {
    // 创建下载链接
    const url = window.URL.createObjectURL(new Blob([response]))
    const link = document.createElement('a')
    link.href = url
    link.setAttribute('download', file.filename!)
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
  }).catch(() => {
    ElMessage.error("文件下载失败")
  })
}
// #endregion

//  删除按钮
function handleDelete(file: StorageFile) {
  ElMessage.info("文件存储功能尚未实现")
}
// #endregion

// 查询
//当前页面的文件数据
const storageData = ref<StorageFile[]>([])

//搜索的表单
const searchFormRef = useTemplateRef("searchFormRef")

//搜索的表单的数据：根据这个字段，模糊查询数据库的filename
const searchData = reactive({
  querystring: ""
})

const getStorageData = () => {
  loading.value = true
//请求参数
  let params: QueryPageBean = {
    pageNum: paginationData.currentPage,
    pageSize: paginationData.pageSize,
    queryString: searchData.querystring

  }

  //发送后端请求：查询分页数据
  storageApi.pageQuery(params).then(Response=>{
    if(Response.code==2000){
      paginationData.total=Response.data.total//后端响应回来的中记录数
      storageData.value=Response.data.rows
    }else{
      ElMessage.error(Response.message || "获取分页数据失败")
      storageData.value=[]
    }
}).catch((err) => {
  console.error("API·ERROR:",err)
  storageData.value = []
}).finally(() => {
  loading.value = false
})
}



function handleSearch() {
  paginationData.currentPage === 1 ? getStorageData() : (paginationData.currentPage = 1)
}

function resetSearch() {
  searchFormRef.value?.resetFields()
  handleSearch()
}
// #endregion

// 监听分页参数的变化
watch([() => paginationData.currentPage, () => paginationData.pageSize], getStorageData, { immediate: true })
</script>

<template>
  <div class="app-container">
    <el-alert title="数据来源" type="success" description="由 Apifox 提供在线 Mock，数据不具备真实性，仅供简单的 CRUD 操作演示" show-icon />
    <el-card v-loading="loading" shadow="never" class="search-wrapper">
      <el-form ref="searchFormRef" :inline="true" :model="searchData">
        <el-form-item prop="username" label="搜索">
          <el-input v-model="searchData.querystring" placeholder="请输入文件名" />
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
          <!-- 点击显示上传 -->
          <el-upload
            class="upload-demo"
            :show-file-list="false"
            :on-change="(file) => handleFileUpload(file.raw!)"
            :disabled="uploadLoading"
          >
            <el-button type="primary" :icon="Upload" :loading="uploadLoading">
              上传文件
            </el-button>
          </el-upload>
          <el-button type="danger" :icon="Delete">
            批量删除
          </el-button>
        </div>
        <div>
          <el-tooltip content="下载">
            <el-button type="primary" :icon="Download" circle />
          </el-tooltip>
          <el-tooltip content="刷新当前页">
            <el-button type="primary" :icon="RefreshRight" circle @click="getStorageData" />
          </el-tooltip>
        </div>
      </div>
      <div class="table-wrapper">
        <el-table :data="storageData">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="filename" label="文件名" align="center" />
          <el-table-column prop="url" label="文件URL" align="center" />
          <el-table-column prop="size" label="文件大小" align="center" />
          <el-table-column prop="type" label="文件类型" align="center" />
          <el-table-column prop="createTime" label="上传时间" align="center" />

          <el-table-column fixed="right" label="操作" width="150" align="center">
            <template #default="scope">
              <el-button type="primary" text bg size="small" @click="handleDownload(scope.row)">
                下载
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