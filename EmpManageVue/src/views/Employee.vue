<script lang="ts" setup>
// 导入图标
import { Delete,Check, Edit, Plus,Download } from '@element-plus/icons-vue'
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox} from 'element-plus'
//导入接口函数
import {
  searchAllService,
  searchByConditionService,
  addEmployeeService,
  deleteEmployeeService,
 // backupEmployeeService
} from '@/api/Employee'

// 员工信息类型
interface Employee {
  empId: number
  empName: string
  empStatus: number // 1-在职，2-兼职，3-试用，4-离职，5-返聘，6-退休
  empOnboard: string
  empJob: string
  empTitle: string
  dptId: number
  dptName: string  // 部门名称
}
const employeeForm = reactive<Employee>({
  empId: 0,
  empName: '',
  empStatus: 0,// 默认未选择
  empOnboard: '',
  empJob: '',
  empTitle: '',
  dptId: 0,
  dptName: ''
});


// 状态映射（用于显示）
const statusMap = {
  1: '在职',
  2: '兼职',
  3: '试用',
  4: '离职',
  5: '返聘',
  6: '退休'
}

// 表格行样式
const tableRowClassName = ({ rowIndex }: { rowIndex: number }) => {
  if (rowIndex % 2 === 1) return 'warning-row'
  return ''
}

// 原始表格数据
const originalData = ref<Employee[]>([])

const tableData = ref<Employee[]>([])
const loading = ref(false)

// 弹窗状态
const dialogVisible = ref(false);

const openDialog = () => {
  dialogVisible.value = true;
};

// 搜索表单
const formInline = reactive({
  empId: '',
  empName: '',
  dptId: ''
})


////////////////////////////////////////////////////////////////////
// 分页核心逻辑
const page = reactive({
  pageNum: 1,
  pageSize: 5
})

const filteredData = computed(() => {
  let list = Array.isArray(originalData.value) ? [...originalData.value] : [];
  
  return list;
});

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
/////////////////////////////////////////////////////////////////////////
// 搜索方法
const onSubmit = async () => {

  const { empId, empName, dptId } = formInline || {};
  const cleanEmpId = empId ? String(empId).trim() : '';
  const cleanEmpName = empName ? String(empName).trim() : '';
  const cleanDptId = dptId ? String(dptId).trim() : '';

  if (!cleanEmpId && !cleanEmpName && !cleanDptId) {
    ElMessage.warning('请至少输入一个搜索条件（员工ID/员工姓名/部门ID）');
    return; // 终止后续执行
  }


  
  const res = await searchByConditionService({
    empId: formInline.empId,
    empName: formInline.empName,
    dptId: formInline.dptId
  })
  originalData.value = Array.isArray(res) ? res : [];

  page.pageNum = 1 // 搜索后重置页码
  applyFilterAndPaging()
}

const ShowAll = async () => {
  const res = await searchAllService()
  originalData.value = Array.isArray(res) ? res : [];

  page.pageNum = 1 // 搜索后重置页码
  applyFilterAndPaging()
}
// 提交新增员工
const submitEmployee = async () => {

  if (employeeForm.empName.trim() === '') {
    ElMessage.warning('请填写员工姓名！');
    return; // 未选择则终止提交
  }
  if (employeeForm.empStatus === 0) {
    ElMessage.warning('请选择员工状态！');
    return; // 未选择则终止提交
  }
  if (employeeForm.empJob.trim() === '') {
    ElMessage.warning('请填写员工岗位！');
    return; // 未选择则终止提交
  }
  if (employeeForm.empTitle.trim() === '') {
    ElMessage.warning('请填写员工职务！');
    return; // 未选择则终止提交
  }
  if (employeeForm.dptId === 0) {
    ElMessage.warning('请选择部门名称！');
    return; // 未选择则终止提交
  }

  await addEmployeeService({
    empName: employeeForm.empName,
    empStatus: employeeForm.empStatus,
    empJob: employeeForm.empJob,
    empTitle: employeeForm.empTitle,
    dptId: employeeForm.dptId
  });
  ElMessage.success('员工'+ employeeForm.empName +'添加成功！');
  // 关闭弹窗
  dialogVisible.value = false;
  // 清空表单
  Object.assign(employeeForm, {
    empId: 0,
    empName: '',
    empStatus: 0,
    empOnboard: '',
    empJob: '',
    empTitle: '',
    dptId: 0,
    dptName: ''
  });
  ShowAll(); // 刷新表格数据
};
// 删除员工
const deleteEmployee = async (empId: number) => {


  await ElMessageBox.confirm(
      `确定要删除编号为 ${empId} 的员工吗？`,
      '删除确认',
      {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }
    );

  await deleteEmployeeService({
    empId: empId
  });
  ElMessage.success('员工删除成功！');
  ShowAll(); // 刷新表格数据
}
onMounted(async () => {


  const res = await searchAllService()
  originalData.value = Array.isArray(res) ? res : [];

  applyFilterAndPaging()
})

//备份
const backupEmployeeData = () => {
  try {
    // 调用后端备份接口
    const downloadUrl = 'http://localhost:8081/employee/backup/sql';
    const link = document.createElement('a');
    link.href = downloadUrl;
    link.target = '_blank';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    
    ElMessage.success('员工信息备份成功！SQL文件已开始下载');
  } catch (error) {
    ElMessage.error(`备份失败：${(error as Error).message || '服务端异常'}`);
  }
};
</script>
<!--////////////////////////////////////////////////////////////////////////////////////////-->
<template>
  <!-- 员工信息卡片 -->
  <el-card class="card">
    <template #header>
      <div class="card-header">
        <span>员工基本信息管理</span>
        <el-button @click="openDialog" type="primary" :icon="Plus" class="float-right">新增员工</el-button>
        <el-button 
          @click="backupEmployeeData" 
          type="success" 
          :icon="Download" 
          class="float-right" 
          style="margin-right: 10px">
          备份员工信息
        </el-button>
      </div>
    </template>

    <!-- 搜索表单 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="员工编号">
        <el-input v-model="formInline.empId" placeholder="输入员工编号" clearable />
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="formInline.empName" placeholder="输入员工姓名" clearable />
      </el-form-item>
      <el-form-item label="部门编号">
        <el-input v-model="formInline.dptId" placeholder="输入部门编号" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">搜索</el-button>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="ShowAll">显示全部</el-button>
      </el-form-item>
    </el-form>

    <!-- 员工表格 -->
    <el-table :data="tableData" style="width: 100%" :row-class-name="tableRowClassName" border v-loading="loading">
      <el-table-column prop="empId" label="员工编号" width="120" />
      <el-table-column prop="empName" label="员工姓名" width="120" />
      <el-table-column label="员工状态" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.empStatus === 1 ? 'success' : scope.row.empStatus === 6 ? 'info' : 'warning'">
            {{ statusMap[scope.row.empStatus] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="empOnboard" label="到岗日期" width="150" />
      <el-table-column prop="empJob" label="工作岗位" width="150" />
      <el-table-column prop="empTitle" label="职务" width="120" />
      <el-table-column prop="dptName" label="部门名称" width="120" />
      <el-table-column prop="dptId" label="部门编号" width="120" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <!-- <el-button type="primary" :icon="Edit" circle size="small" />-->
          <el-button @click="deleteEmployee(scope.row.empId)" type="danger" :icon="Delete" circle size="small" />
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增员工弹窗 -->
  <el-dialog title="新增员工信息" v-model="dialogVisible" width="450px">
    <el-form :model="employeeForm" label-width="100px">
      <el-form-item label="员工姓名" required>
        <el-input v-model="employeeForm.empName" />
      </el-form-item>

      <el-form-item label="工作岗位" required>
        <el-input v-model="employeeForm.empJob" />
      </el-form-item>

      <el-form-item label="职务" required>
        <el-input v-model="employeeForm.empTitle" />
      </el-form-item>
      
      <el-form-item label="员工状态" required>
        <el-radio-group v-model="employeeForm.empStatus">
          <el-radio label="1">在职</el-radio>
          <el-radio label="2">兼职</el-radio>
          <el-radio label="3">试用</el-radio>
          <el-radio label="4">离职</el-radio>
          <el-radio label="5">返聘</el-radio>
          <el-radio label="6">退休</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="部门编号" required>
        <el-radio-group v-model="employeeForm.dptId">
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
    </el-form>
    <template #footer>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" :icon="Check" @click="submitEmployee">确认处理</el-button>
    </template>
  </el-dialog>



    <!-- 分页 -->
    <div class="demo-pagination-block">
      <el-pagination v-model:current-page="page.pageNum" v-model:page-size="page.pageSize" :total="total"
        :page-sizes="[5, 10, 20, 30, 50]" layout="total, sizes, prev, pager, next, jumper"
        @current-change="handlePageChange" @size-change="handleSizeChange" />
    </div>
  </el-card>
</template>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

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
</style>