<script lang="ts" setup>
import type { CheckGroup } from "@/common/apis/checkgroup/type"
import type { CheckItem } from "@/common/apis/checkitem/type"
import type { QueryPageBean, RequestParams } from "@/http/type"
import type { FormRules } from "element-plus"
import { usePagination } from "@@/composables/usePagination"
import { CirclePlus, Delete, Download, Refresh, RefreshRight, Search } from "@element-plus/icons-vue"
import { cloneDeep } from "lodash-es"
import { ref, reactive, watch } from "vue"
import { ElForm, ElMessage } from "element-plus"
import { checkitemApi } from "@/common/apis/checkitem"
import { checkgroupApi } from "@@/apis/checkgroup"
import { Setmeal } from "@/common/apis/setmeal/type"
import { setmealApi } from "@/common/apis/setmeal"


defineOptions({
    // 命名当前组件
    name: "Setmeal"
})

// 加载状态
const loading = ref<boolean>(false)
// 图片的URL前缀
const BASE_URL = "https://itzzd.oss-cn-hangzhou.aliyuncs.com/"
// 分页数据
const { paginationData, handleCurrentChange, handleSizeChange } = usePagination()

// 新增表单的：初始数据
const DEFAULT_FORM_DATA: Setmeal = {
    id: null,
    code: "",
    name: "",
    helpCode: "",
    sex: "",
    age: "",
    price: 0,
    remark: "",
    attention: "",
    img: ""
}

// 对话框状态
const dialogVisible = ref<boolean>(false)

// 表单引用
const formRef = ref<InstanceType<typeof ElForm>>()
// 表单数据
const formData = ref<Setmeal>(cloneDeep(DEFAULT_FORM_DATA))
// 表单校验规则
const formRules: FormRules<Setmeal> = {
    code: [{ required: true, trigger: "blur", message: "请输入检查套餐编码" }],
    name: [{ required: true, trigger: "blur", message: "请输入检查套餐名称" }],
    helpCode: [{ required: true, trigger: "blur", message: "请输入助记码" }],
    price: [{ required: true, trigger: "blur", message: "请输入套餐价格" }]

}

// 检查组数据
const allCheckGroups = ref<CheckGroup[]>([])

// 已选择的检查组 id 列表
const selectedCheckGroupIds = ref<number[]>([])

// 获取所有检查组
// 作用：从后端获取所有检查组数据，用于检查组选择对话框
function getAllCheckGroups() {
    checkgroupApi.findAll().then(response => {
        if (response.code == 2000) {
            allCheckGroups.value = response.data!
        }
    })
}

// 打开检查组选择对话框：加载已选择的检查组
function openCheckGroupDialog(row: CheckGroup) {
    formData.value = cloneDeep(row)  // 回显当前检查套餐数据

    // 查询所有关联的检查组数组
    setmealApi.findCheckGroupIdsBySetmealId(row.id!).then(response => {
        if (response.code === 2000) {
            selectedCheckGroupIds.value = response.data!  // 更新已选择的检查组id
        }
    })

}

// #region 新增或者修改方法
function handleCreateOrUpdate() {
    formRef.value?.validate((valid) => {
        if (!valid) {
            ElMessage.error("表单校验不通过")
            return
        }
        loading.value = true

        // 请求参数：当前检查组的基本数据  和  新的关联的 checkGroupIds 关系
        let params: RequestParams<Setmeal> = {
            data: formData.value,
            ids: selectedCheckGroupIds.value  // 选中检查组 id 数组
        }
        // 更新
        setmealApi.addorupdate(params).then(response => {
            if (response.code === 2000) {
                ElMessage.success(response.message)
                dialogVisible.value = false   // 关闭弹窗
                getSetmealsData()   // 刷新数据
            }
        })


    })
}
// 重置表单
function resetForm() {
    formRef.value?.clearValidate()
    formData.value = cloneDeep(DEFAULT_FORM_DATA)
    selectedCheckGroupIds.value = []
}
// #endregion

// #region 删
function handleDelete(row: Setmeal) {
    ElMessageBox.confirm(`正在删除检查套餐：${row.name}，确认删除？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
    })
        .then(() => {
            setmealApi.delete(row.id!).then(response => {
                if (response.code === 2000) {
                    ElMessage.success(response.message)
                    getSetmealsData()
                }
            })
        })
}
// #endregion

// #region修改
function handleUpdate(row: Setmeal) {
    dialogVisible.value = true
    formData.value = cloneDeep(row)   // 回显数据

    console.log("************************" + JSON.stringify(formData.value));


    // 查询所有关联的检查组数组
    setmealApi.findCheckGroupIdsBySetmealId(row.id!).then(response => {
        if (response.code === 2000) {
            selectedCheckGroupIds.value = response.data!  // 更新已选择的检查组id
        }
    })
}
// #endregion

// #region 分页查询的相关变量和方法
// 表格数据
const setmealsData = ref<Setmeal[]>([])
// 搜索表单引用
const searchFormRef = ref<InstanceType<typeof ElForm>>()
// 搜索数据
const searchData = reactive({
    queryString: ""  // 搜索的关键字
})

// 获取检查套餐数据：使用模拟静态数据
function getSetmealsData() {
    loading.value = true

    // 请求分页查询的参数
    const params: QueryPageBean = {
        pageNum: paginationData.currentPage,
        pageSize: paginationData.pageSize,
        queryString: searchData.queryString
    }

    // 请求查询分页数据
    setmealApi.pageQuery(params).then(response => {
        if (response.code === 2000) {
            paginationData.total = response.data.total // 总记录数
            setmealsData.value = response.data.rows  // 当前页记录
        } else {
            ElMessage.error(response.message || "获取分页数据失败")
            setmealsData.value = []
        }
    })
        .finally(() => {
            loading.value = false
        })
}
// #endregion

// #region 查
function handleSearch() {
    paginationData.currentPage === 1 ? getSetmealsData() : (paginationData.currentPage = 1)
}
// 重置搜索
function resetSearch() {
    searchFormRef.value?.resetFields()
    handleSearch()
}
// 图片上传
// on-change钩子调用的函数
function handleImageUpload(uploadFile: any) {

    const file = uploadFile.row
    if (!file) return false

    loading.value = true

    //发送API 请求
    setmealApi.upload(file).then(response => {
        if (response.code === 2000) {
            formData.value.img = response.data
            ElMessage.success(response.message)
        } else {
            ElMessage.error(response.message)
        }
    }).finally(() => {
        loading.value = false
    })
}

// 监听分页参数的变化
watch([() => paginationData.currentPage, () => paginationData.pageSize], getSetmealsData, { immediate: true })

// 初始化数据
getAllCheckGroups()

//钩子 
</script>

<template>
    <div class="app-container">
        <el-alert title="数据来源" type="success" description="由后端API提供真实数据，支持完整的CRUD操作" show-icon />

        <!-- 搜索栏  -->
        <el-card v-loading="loading" shadow="never" class="search-wrapper">
            <el-form ref="searchFormRef" :inline="true" :model="searchData">
                <el-form-item label="搜索">
                    <el-input v-model="searchData.queryString" placeholder="请输入检查套餐名称|编码|说明" style="width: 300px" />
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
            <!-- 新增和删除按钮 -->
            <div class="toolbar-wrapper">
                <div>
                    <el-button type="primary" :icon="CirclePlus" @click="dialogVisible = true">
                        新增检查套餐
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
                        <el-button type="primary" :icon="RefreshRight" circle @click="getSetmealsData" />
                    </el-tooltip>
                </div>
            </div>

            <!-- 表格数据 -->
            <div class="table-wrapper">
                <el-table :data="setmealsData">
                    
                    <el-table-column type="selection" width="50" align="center" />
                    <!-- 展示套餐图片 -->
          <el-table-column width="250" align="center"  label="套餐图片">
              <template #default="scope">
                <div>
                  <el-image
                    style="width: 100px; height: 100px"
                    :src="BASE_URL + scope.row.img"
                    fit="fill"
                  />
                </div>
              </template>
          </el-table-column>
                    <el-table-column prop="code" label="检查套餐编码" align="center" />
                    <el-table-column prop="name" label="检查套餐名称" align="center" />
                    <el-table-column prop="helpCode" label="助记码" align="center" />
                    <el-table-column prop="sex" label="适用性别" align="center" />
                    <el-table-column prop="age" label="年龄" align="center" />
                    <el-table-column prop="price" label="价格" align="center" />
                    <el-table-column prop="remark" label="备注"  show-overflow-tooltip />
                    <el-table-column prop="attention" label="说明"  show-overflow-tooltip />
                    <el-table-column fixed="right" label="操作"  align="center">
                        <template #default="scope">
                            <div style="display: flex; gap: 10px; justify-content: center;">
                                <el-button type="primary" text bg size="small" @click="handleUpdate(scope.row)">
                                    修改
                                </el-button>
                                <el-button type="danger" text bg size="small" @click="handleDelete(scope.row)">
                                    删除
                                </el-button>
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </div>

            <!-- 分页插件 -->
            <div class="pager-wrapper">
                <el-pagination background :layout="paginationData.layout" :page-sizes="paginationData.pageSizes"
                    :total="paginationData.total" :page-size="paginationData.pageSize"
                    :current-page="paginationData.currentPage" @size-change="handleSizeChange"
                    @current-change="handleCurrentChange" />
            

                </div>
                
        </el-card>
          



        <!-- 新增/修改模态窗口 -->
        <el-dialog v-model="dialogVisible" :title="formData.id === null ? '新增检查套餐' : '修改检查套餐'" width="50%"
            @closed="resetForm">
            <el-form ref="formRef" :model="formData" :rules="formRules" label-width="100px" label-position="left">
                <el-form-item prop="code" label="检查套餐编码">
                    <el-input v-model="formData.code" placeholder="请输入" />
                </el-form-item>
                <el-form-item prop="name" label="检查套餐名称">
                    <el-input v-model="formData.name" placeholder="请输入" />
                </el-form-item>
                <el-form-item prop="helpCode" label="助记码">
                    <el-input v-model="formData.helpCode" placeholder="请输入" />

                    <el-form-item prop="sex" label="适用性别">
                        <el-select v-model="formData.sex" placeholder="请选择">
                            <el-option label="不限" value="0" />
                            <el-option label="男" value="1" />
                            <el-option label="女" value="2" />
                        </el-select>
                    </el-form-item>
                </el-form-item>
                <el-form-item prop="age" label="年龄">
                    <el-input v-model="formData.age" placeholder="请输入" />
                </el-form-item>
                <el-form-item prop="price" label="价格">
                    <el-input v-model="formData.price" placeholder="请输入" />
                </el-form-item>
<!-- ================= -->
 <!--套餐图片  -->
        <el-form-item label="套餐图片">

          <el-upload class="avatar-uploader" action="#" :auto-upload="false":show-file-list="false" :on-change="handleImageUpload">
            <img v-if="formData.img" :src="BASE_URL + formData.img" class="avatar" />
            <el-icon v-else class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>

        </el-form-item>

                <el-form-item label="注意事项">
                    <el-input v-model="formData.attention" type="textarea" placeholder="请输入" />
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="formData.remark" type="textarea" placeholder="请输入" />
                </el-form-item>

                <!-- 关联检查组  -->
                <el-form-item label="选择检查组">
                    <el-select v-model="selectedCheckGroupIds" multiple placeholder="请选择检查组" style="width: 100%">
                        <el-option v-for="item in allCheckGroups" :key="item.id!" :label="item.name"
                            :value="item.id!" />
                    </el-select>
                </el-form-item>


            </el-form>
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
