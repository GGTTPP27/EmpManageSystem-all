<!-- src/views/Department.vue -->
<script lang="ts" setup>
// 部门信息类型定义
interface Department {
  dptId: number
  dptName: string
  empNum1: number
  empNum2: number
}

// 导入依赖
import { ref, onMounted } from 'vue'
import { getAllDepartmentsService } from '@/api/Department'
// 部门数据
const tableData = ref<Department[]>([])

const loading = ref(false)

onMounted(async () => {

  loading.value = true
  const res = await getAllDepartmentsService()
  tableData.value = Array.isArray(res) ? res : [];
  loading.value = false
})
</script>

<template>
  <!-- 部门信息卡片 -->
  <el-card class="card">
    <template #header>
      <div class="card-header">
        <span class="title">部门信息统计</span>

      </div>
    </template>

    <!-- 部门信息表格 -->
    <el-table :data="tableData" v-loading="loading" style="width: 100%" border>
      <el-table-column prop="dptId" label="部门编号" width="120" />
      <el-table-column prop="dptName" label="部门名称" width="150" />
      <el-table-column prop="empNum1" label="员工 在职 人数" width="120" />
      <el-table-column prop="empNum2" label="员工 离职/退休 人数" width="150" />
    </el-table>
  </el-card>
</template>

<style scoped>
.card {
  width: 90%;
  margin: 20px auto;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  padding: 20px;
  background: #fff;
}
.card-header {
  font-weight: bolder;
  font-size: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.card-header .title {
  font-weight: 700;
  font-size: 18px;
}
.demo-form-inline {
  margin-bottom: 20px;
}
</style>