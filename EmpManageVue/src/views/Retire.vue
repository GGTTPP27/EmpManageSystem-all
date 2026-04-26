<script lang="ts" setup>

// 导入
import { Check, Search, UserFilled } from '@element-plus/icons-vue'
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { searchAllService, searchByConditionService } from '@/api/Employee'
import { updateRetiredService } from '@/api/Retired'
// 展示表格数据
interface RetireInfo {
  empId: number
  empName: string
  empStatus: number
  handleTime: string
  dptId: number
}

// 处理表单类型
interface RetireForm {
  empId: number
  empName: string
  handleTime: string
  empStatus: number // 4-离职，6-退休
}

// 状态映射
const statusMap = {
  1: '在职',
  2: '兼职',
  3: '试用',
  4: '离职',    ///
  5: '返聘',
  6: '退休'   /////
}

// 表格数据
const originalData = ref<RetireInfo[]>([])
const tableData1 = ref<RetireInfo[]>([]) // 未退休/离职员工
const tableData2 = ref<RetireInfo[]>([]) // 退休/离职员工
const loading = ref(false)

// 搜索表单
const searchForm = reactive({
  empId: '',
  empName: '',
  dptId: '',
})

// 处理表单
const retireForm = reactive<RetireForm>({
  empId: 0,
  empName: '',
  handleTime: '',
  empStatus: 0 // 默认未选择
})

// 弹窗控制
const dialogVisible = ref(false)

// 时间格式化函数
const formatTime = (row: RetireInfo) => {
  const timeStr = row.handleTime; // 从行数据中取 handleTime
  if (!timeStr) return '未处理'; // 空值友好提示
  return timeStr.replace('T', ' '); // 替换 T 为空格
};

///////////////////////////////////////////////////////////////////////
// 分页核心逻辑 - 未退休/离职员工
const page1 = reactive({
  pageNum: 1,
  pageSize: 5
})

// 分页核心逻辑 - 退休/离职员工
const page2 = reactive({
  pageNum: 1,
  pageSize: 5
})

// 未退休/离职员工过滤
const filteredData1 = computed(() => {
  let list = Array.isArray(originalData.value) ? [...originalData.value] : [];
  // 筛选状态为1,2,3,5的员工
  list = list.filter(item => [1,2,3,5].includes(item.empStatus))
  return list
})

// 退休/离职员工过滤
const filteredData2 = computed(() => {
  let list = Array.isArray(originalData.value) ? [...originalData.value] : [];
  // 筛选状态为4,6的员工
  list = list.filter(item => [4,6].includes(item.empStatus))
  return list
})

const total1 = computed(() => filteredData1.value.length)
const total2 = computed(() => filteredData2.value.length)

// 未退休/离职员工分页
const applyFilterAndPaging1 = () => {
  const list = filteredData1.value
  const start = (page1.pageNum - 1) * page1.pageSize
  const end = start + page1.pageSize
  tableData1.value = list.slice(start, end)
}

// 退休/离职员工分页
const applyFilterAndPaging2 = () => {
  const list = filteredData2.value
  const start = (page2.pageNum - 1) * page2.pageSize
  const end = start + page2.pageSize
  tableData2.value = list.slice(start, end)
}

// 未退休/离职员工分页切换
const handlePageChange1 = (val: number) => {
  page1.pageNum = val
  applyFilterAndPaging1()
}

// 退休/离职员工分页切换
const handlePageChange2 = (val: number) => {
  page2.pageNum = val
  applyFilterAndPaging2()
}

// 未退休/离职员工页大小切换
const handleSizeChange1 = (val: number) => {
  page1.pageSize = val
  page1.pageNum = 1
  applyFilterAndPaging1()
}

// 退休/离职员工页大小切换
const handleSizeChange2 = (val: number) => {
  page2.pageSize = val
  page2.pageNum = 1
  applyFilterAndPaging2()
}
////////////////////////////////////////////////////////////////////////
// 搜索方法
const onSubmit = async () => {
  const { empId, empName, dptId } = searchForm || {};
  const cleanEmpId = empId ? String(empId).trim() : '';
  const cleanEmpName = empName ? String(empName).trim() : '';
  const cleanDptId = dptId ? String(dptId).trim() : '';

  if (!cleanEmpId && !cleanEmpName && !cleanDptId) {
    ElMessage.warning('请至少输入一个搜索条件（员工ID/员工姓名/部门ID）');
    return;
  }
  
  const res = await searchByConditionService({
    empId: cleanEmpId,
    empName: cleanEmpName,
    dptId: cleanDptId
  })
  originalData.value = Array.isArray(res) ? res : [];

  page1.pageNum = 1 // 搜索后重置页码
  page2.pageNum = 1 // 搜索后重置页码
  applyFilterAndPaging1()
  applyFilterAndPaging2()
}

// 打开处理弹窗
const openDialog = (empId: number, empName: string) => {
  retireForm.empId = empId
  retireForm.empName = empName
  dialogVisible.value = true
}

// 提交离退休处理
const submitRetire = async () => {
  if (retireForm.empStatus === 0 || retireForm.empStatus === null) {
    ElMessage.warning('请选择处理类型！');
    return; // 未选择则终止提交
  }

  await updateRetiredService({
    empId: retireForm.empId,
    empStatus: retireForm.empStatus,
    handleTime: retireForm.handleTime
  })

  dialogVisible.value = false;//关闭处理表单弹窗
  


  ShowAll(); 

  const empName = retireForm.empName;
  const status = retireForm.empStatus;

  const successHtml = `
      成功处理 员工<span style="color: #000; font-weight: 600;">${empName}</span> 
      为 <span style="color: #1677ff; font-weight: 600;">${statusMap[status]}</span> 状态
    `;
  ElMessage.success({
    message: successHtml,
    dangerouslyUseHTMLString: true,
    duration: 3500 
  });
  
  /*重置表单*/
  retireForm.empStatus = 0;
}
//仅点击取消时，依然把表单信息清空
const OnCancel = () => {
  dialogVisible.value = false;
  retireForm.empStatus = 0;
};


// 显示全部数据
const ShowAll = async () => {
  const res = await searchAllService()
  originalData.value = Array.isArray(res) ? res : [];

  page1.pageNum = 1 // 搜索后重置页码
  page2.pageNum = 1 // 搜索后重置页码
  applyFilterAndPaging1()
  applyFilterAndPaging2()
}

onMounted(async () => {
  const res = await searchAllService();
  originalData.value = Array.isArray(res) ? res : [];
  applyFilterAndPaging1()
  applyFilterAndPaging2()
})
</script>

<template>
  <!-- 离退休处理卡片 -->
  <el-card class="card">
    <template #header>
      <div class="card-header">
        <span>离退休处理管理</span>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="员工编号">
        <el-input v-model="searchForm.empId" placeholder="输入员工编号" clearable/>
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="searchForm.empName" placeholder="输入员工姓名" clearable />
      </el-form-item>
      <el-form-item label="部门编号">
        <el-input v-model="searchForm.dptId" placeholder="输入部门编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="ShowAll">显示全部</el-button>
      </el-form-item>
    </el-form>

    <!-- 未退休/离职员工表格 -->
    <div class="table-title">在职员工</div>
    <el-table :data="tableData1" style="width: 100%" border v-loading="loading">
      <el-table-column prop="empId" label="员工编号" width="120" />
      <el-table-column prop="empName" label="员工姓名" width="120" />
      <el-table-column label="当前状态" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.empStatus === 4 ? 'warning' : scope.row.empStatus === 6 ? 'info' : 'success'">
            {{ statusMap[scope.row.empStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="dptId" label="部门编号" width="120" />
      <el-table-column prop="dptName" label="部门名称" width="120" />
      <el-table-column prop="handleTime" label="处理时间" width="150" :formatter="formatTime" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button type="primary" :icon="UserFilled" circle size="small"
            @click="openDialog(scope.row.empId, scope.row.empName)" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 未退休/离职员工分页 -->
    <div class="demo-pagination-block">
      <el-pagination v-model:current-page="page1.pageNum" v-model:page-size="page1.pageSize" :total="total1"
        :page-sizes="[5, 10, 20, 30, 50]" layout="total, sizes, prev, pager, next, jumper"
        @current-change="handlePageChange1" @size-change="handleSizeChange1" />
    </div>

    <!-- 分割线 -->
    <div style="height: 20px;"></div>

    <!-- 退休/离职员工表格 -->
    <div class="table-title">退休/离职员工</div>
    <el-table :data="tableData2" style="width: 100%" border v-loading="loading">
      <el-table-column prop="empId" label="员工编号" width="120" />
      <el-table-column prop="empName" label="员工姓名" width="120" />
      <el-table-column label="当前状态" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.empStatus === 4 ? 'warning' : scope.row.empStatus === 6 ? 'info' : 'success'">
            {{ statusMap[scope.row.empStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="dptId" label="部门编号" width="120" />
      <el-table-column prop="dptName" label="部门名称" width="120" />
      <el-table-column prop="handleTime" label="处理时间" width="150" :formatter="formatTime" />
      <el-table-column label="操作" width="120">
        <template #default="scope">
          <el-button type="primary" :icon="UserFilled" circle size="small"
            @click="openDialog(scope.row.empId, scope.row.empName)" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 退休/离职员工分页 -->
    <div class="demo-pagination-block">
      <el-pagination v-model:current-page="page2.pageNum" v-model:page-size="page2.pageSize" :total="total2"
        :page-sizes="[5, 10, 20, 30, 50]" layout="total, sizes, prev, pager, next, jumper"
        @current-change="handlePageChange2" @size-change="handleSizeChange2" />
    </div>
  </el-card>

  <!-- 离退休处理弹窗 -->
  <el-dialog title="离退休处理" v-model="dialogVisible" width="450px">
    <el-form :model="retireForm" label-width="100px">
      <el-form-item label="员工编号" required>
        <el-input v-model="retireForm.empId" disabled />
      </el-form-item>
      <el-form-item label="员工姓名" required>
        <el-input v-model="retireForm.empName" disabled />
      </el-form-item>
      <el-form-item label="处理类型" required>
        <el-radio-group v-model="retireForm.empStatus">
          <el-radio label="1">在职</el-radio>
          <el-radio label="2">兼职</el-radio>
          <el-radio label="3">试用</el-radio>
          <el-radio label="4">离职</el-radio>
          <el-radio label="5">返聘</el-radio>
          <el-radio label="6">退休</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="default" @click="OnCancel">取消</el-button>
      <el-button type="primary" :icon="Check" @click="submitRetire">确认处理</el-button>
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