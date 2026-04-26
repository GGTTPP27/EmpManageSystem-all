<script lang="ts" setup>

import { searchAllService,search1Service,search2Service,deleteService, addService } from '@/api/Transfer'
// 导入图标
import { Delete, Edit, Plus, Search } from '@element-plus/icons-vue'
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage,ElMessageBox } from 'element-plus'

// 调动信息展示类型
interface TransferInfo {
  transferId: number
  empId: number
  transferDate:String
  transferOutDpt: number
  transferInDpt: number
  transferReason: string
}

const originalData = ref<TransferInfo[]>([])
const tableData = ref<TransferInfo[]>([])
const loading = ref(false)



// 搜索表单
const searchForm = reactive({
  empId: '',
  transferOutDpt: '',
  transferInDpt: '',
})

// 调动表单
const transferForm = reactive({
  empId: '',
  transferInDpt: 0,
  empJob:'',
  empTitle:'',
  transferReason: ''
})

// 弹窗控制
const dialogVisible = ref(false)

// 部门名称映射
const dptMap = {
  1: "1(人事部门)",
  2: "2(后勤部门)",
  3: "3(管理部门)",
  4: "4(法务部门)",
  5: "5(技术部门)",
  6: "6(市场部门)",
  7: "7(财务部门)",
  8: "8(监控部门)",
};

const getDptName = (dptId: number | undefined) => {
  if (!dptId) return "未知部门";
  return dptMap[dptId] || "未知部门";
};
///////////////////////////////////////////////////////// 分页核心逻辑
const page = reactive({
  pageNum: 1,
  pageSize: 5
})

const filteredData = computed(() => {
  // 保留原搜索逻辑
  let list = Array.isArray(originalData.value) ? [...originalData.value] : [];
  return list
})

const total = computed(() => filteredData.value.length)

const applyFilterAndPaging = () => {
  const list = filteredData.value
  const start = (page.pageNum - 1) * page.pageSize
  const end = start + page.pageSize
  tableData.value = list.slice(start, end)
}

const handlePageChange = (val: number) => {
  page.pageNum = val
  applyFilterAndPaging()
}

const handleSizeChange = (val: number) => {
  page.pageSize = val
  page.pageNum = 1
  applyFilterAndPaging()
}
/////////////////////////////////////////////////////////////////////////////////

const onDelete=async (transferId:number)=>{
  await ElMessageBox.confirm(
      `确定要删除编号为 ${transferId} 的调动记录吗？`,
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
    );


  await deleteService({
    transferId: transferId
  })
  ElMessage.success('调度信息 删除成功！');
  ShowAll(); // 刷新表格数据
}
// 搜索方法
const onSearch = async () => {
  // 增加异常捕获
  try {
    const res = await search1Service({
      empId: searchForm.empId 
    });

    originalData.value = Array.isArray(res.data) ? res.data : [];
    page.pageNum = 1 // 重置页码
    applyFilterAndPaging();
    
    if (originalData.value.length === 0) {
      ElMessage.info('未查询到该员工的调动记录');
    }
  } catch (error) {
    ElMessage.error('搜索失败：' + (error as Error).message);
    console.error('搜索1异常：', error);
  }
}

// 搜索方法2
const onSearch2 = async () => {

  try {
    
    const params = {
      transferOutDpt: searchForm.transferOutDpt ? Number(searchForm.transferOutDpt) : '',
      transferInDpt: searchForm.transferInDpt ? Number(searchForm.transferInDpt) : ''
    };
    const res = await search2Service(params);
    
    originalData.value = Array.isArray(res.data) ? res.data : [];
    page.pageNum = 1 // 重置页码
    applyFilterAndPaging();
    
    if (originalData.value.length === 0) {
      ElMessage.info('未查询到符合条件的调动记录');
    }
  } catch (error) {
    ElMessage.error('搜索失败：' + (error as Error).message);
    console.error('搜索2异常：', error);
  }
}

// 打开新增弹窗
const openDialog = () => {
  dialogVisible.value = true
}

// 提交调动信息
const submitTransfer = async() => {
  
  if (transferForm.empId === '') {
    ElMessage.warning('请填写员工编号！');
    return; // 未选择则终止提交
  }

  if (transferForm.transferInDpt === 0) {
    ElMessage.warning('请选择员工调入部门！');
    return; // 未选择则终止提交
  }
  if(transferForm.empJob.trim()===''){
    ElMessage.warning('请输入员工的新岗位！');
    return; // 未选择则终止提交
  }
  if(transferForm.empTitle.trim()===''){
    ElMessage.warning('请输入员工的新职务！');
    return; // 未选择则终止提交
  }



  if (transferForm.transferReason.trim()==='') {
    ElMessage.warning('请填写调动理由');
    return; // 未选择则终止提交
  }
  console.log(transferForm)

  try {
    const rest: any = await addService({
      empId: transferForm.empId,
      transferInDpt: transferForm.transferInDpt,
      empJob: transferForm.empJob,
      empTitle: transferForm.empTitle,
      transferReason: transferForm.transferReason
    })

    //console.log(rest)
    
    if (rest && rest.code === 0) {
      ElMessage.success('调动信息添加成功！');
      dialogVisible.value = false;
      ShowAll();
      Object.assign(transferForm, { empId: '', transferInDpt: 0, empJob: '', empTitle: '', transferReason: '' })
    } else {
      const errMsg = rest ? (rest.msg || '添加失败') : '接口返回空数据';
      ElMessage.error(errMsg);
    }
  } catch (error) {
    console.error('请求异常：', error);
    ElMessage.error('添加失败：' + (error as Error).message);
  }



  dialogVisible.value = false
  ShowAll();
  Object.assign(transferForm, { empId: '', transferInDpt: 0, empJob:'', empTitle:'', transferReason: '' })// 重置表单
}

// 显示全部数据
const ShowAll = async () => {

  const res = await searchAllService()
  originalData.value = Array.isArray(res.data) ? res.data : [];

  page.pageNum = 1 //重置页码
  applyFilterAndPaging()
}

onMounted(() => {
  ShowAll();
})
</script>

<template>
  <!-- 调动管理卡片 -->
  <el-card class="card">
    <template #header>
      <div class="card-header">
        <span>人事调动管理</span>
        <el-button type="primary" :icon="Plus" class="float-right" @click="openDialog">进行人员调动</el-button>
      </div>
    </template>

    <!-- 搜索表单1 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="员工编号">
        <el-input v-model="searchForm.empId" placeholder="输入员工编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="onSearch">搜索</el-button>
      </el-form-item>
    </el-form>

    <!-- 搜索表单2 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="调出部门编号">
        <el-input v-model="searchForm.transferOutDpt" placeholder="输入调出部门" clearable />
      </el-form-item>
      <el-form-item label="调入部门编号">
        <el-input v-model="searchForm.transferInDpt"  placeholder="输入调入部门" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" :icon="Search" @click="onSearch2">搜索</el-button>
      </el-form-item>
    </el-form>
      <el-form-item>
        <el-button type="primary" @click="ShowAll">显示全部</el-button>
      </el-form-item>
    <!-- 调动信息表格 -->
     <div class="table-title">调动记录</div>
    <el-table :data="tableData" style="width: 100%" border v-loading="loading">
      <el-table-column prop="transferId" label="序号" width="80" />
      <el-table-column prop="empId" label="员工编号" width="120" />
      <el-table-column prop="transferDate" label="调动日期" width="150" />
    
      <el-table-column prop="transferOutDpt" label="调出部门" width="120" >
        <template #default="scope">
          <span>{{ getDptName(scope.row.transferOutDpt) }}</span>
        </template>
      </el-table-column>
    
      <el-table-column prop="transferInDpt" label="调入部门" width="120" >
        <template #default="scope">
          <span>{{ getDptName(scope.row.transferInDpt) }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="transferReason" label="调动原因" min-width="200" />
      <el-table-column label="操作" width="100">
        <template #default="scope">
          <el-button @click="onDelete(scope.row.transferId)" type="danger" :icon="Delete" circle size="small" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="demo-pagination-block">
      <el-pagination 
        v-model:current-page="page.pageNum" 
        v-model:page-size="page.pageSize" 
        :total="total"
        :page-sizes="[5, 10, 20, 30, 50]" 
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="handlePageChange" 
        @size-change="handleSizeChange" 
      />
    </div>
  </el-card>

  <!-- 调动信息录入弹窗 -->
  <el-dialog title="进行人员调动" v-model="dialogVisible" width="500px">
    <el-form :model="transferForm" label-width="100px" class="transfer-form">
      <el-form-item label="员工编号" required>
        <el-input v-model="transferForm.empId" placeholder="输入员工编号" />
      </el-form-item>
      <el-form-item label="调入部门" required>
        <el-radio-group v-model="transferForm.transferInDpt">
          <el-radio label="1">1-人事部门</el-radio>
          <el-radio label="2">2-后勤部门</el-radio>
          <el-radio label="3">3-管理部门</el-radio>
          <el-radio label="4">3-法务部门</el-radio>
          <el-radio label="5">5-技术部门</el-radio>
          <el-radio label="6">6-市场部门</el-radio>
          <el-radio label="7">7-财务部门</el-radio>
          <el-radio label="8">8-监控部门</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="新的岗位" required>
        <el-input v-model="transferForm.empJob" placeholder="输入新的岗位" />
      </el-form-item>
      <el-form-item label="新的职务" required>
        <el-input v-model="transferForm.empTitle" placeholder="输入新的职务" />
      </el-form-item>

      <el-form-item label="调动原因" required>
        <el-input v-model="transferForm.transferReason" placeholder="输入调动原因" />
      </el-form-item>

    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitTransfer">提交</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.card-header {
  font-weight: bolder;
  font-size: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card {
  width: 90%;
  margin: 20px auto;
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 20px;
  background-color: rgb(255, 255, 255);
  backdrop-filter: blur(8px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  box-sizing: border-box;
}

.demo-form-inline {
  margin-bottom: 20px;
}

.transfer-form {
  margin-top: 10px;
}

/* 分页样式 */
.demo-pagination-block {
  margin-top: 30px;
  text-align: center;
}
/* 表格标题样式 */
.table-title {
  font-size: 16px;
  font-weight: 600;
  margin: 20px 0 10px 0;
}
</style>