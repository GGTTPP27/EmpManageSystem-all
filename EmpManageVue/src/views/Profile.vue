<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, FormInstance } from 'element-plus'
import { useTokenStore } from '@/stores/token.js'
import { useUserStore } from '@/stores/user.js' // 导入独立user仓库
import { getUserInfoService, updatePasswordService } from '@/api/Profile.js'

// 获取仓库实例
const tokenStore = useTokenStore();
const userStore = useUserStore();
// 直接获取单个属性
const { userId, username, createTime, updateTime, setUserInfo } = userStore;

// 弹窗与表单
const passwordVisible = ref(false);
const passwordForm = reactive({ oldPwd: '', newPwd: '', renewPwd: '' });
const passwordFormRef = ref<FormInstance>();

// 密码校验规则
const passwordRules = reactive({
  oldPwd: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  newPwd: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 16, message: '密码长度6-16位', trigger: 'blur' }
  ],
  renewPwd: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string) => {
        if (value !== passwordForm.newPwd) return Promise.reject('两次输入的密码不一致');
        return Promise.resolve();
      },
      trigger: 'blur'
    }
  ]
});

// 加载用户信息
onMounted(async () => {
  

  if (!tokenStore.token) {
    ElMessage.warning('请先登录');
    return;
  }

  try {
    const res = await getUserInfoService();
    if (res.data.code === 0) {
      setUserInfo(res.data.data); // 依然使用setUserInfo赋值
    } else {
      ElMessage.error(res.data.msg || '获取用户信息失败');
    }
  } catch {
    ElMessage.error('网络异常，获取用户信息失败');
  }
});

// 修改密码
const submitPassword = async () => {
  if (!passwordFormRef.value) return;
  try {
    await passwordFormRef.value.validate();
  } catch {
    return ElMessage.error('表单填写有误，请检查');
  }

  ElMessageBox.confirm('确定修改密码吗？修改后需重新登录', '提示', { type: 'warning' })
    .then(async () => {
      try {
        const res = await updatePasswordService({
          oldPwd: passwordForm.oldPwd,
          newPwd: passwordForm.newPwd,
          renewPwd: passwordForm.renewPwd
        });
        if (res.data.code === 0) {
          ElMessage.success(res.data.msg || '密码修改成功，请重新登录');
          
          tokenStore.removeToken()
          userStore.removeUserInfo()
          location.href = '/login'

          passwordVisible.value = false;
          Object.assign(passwordForm, { oldPwd: '', newPwd: '', renewPwd: '' });
        } else {
          ElMessage.error(res.data.msg || '密码修改失败');
        }
      } catch {
        ElMessage.error('网络异常，修改密码失败');
      }
    });
};



</script>

<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <div class="card-header">
          <span class="title">管理员信息</span>
        </div>
      </template>

      <el-descriptions :column="2" border class="info-table">
        <el-descriptions-item label="用户ID" label-class-name="label-bg">
          {{ userId || '暂无数据' }}
        </el-descriptions-item>
        <el-descriptions-item label="用户名">
          <el-tag type="success" size="small">{{ username || '未登录' }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ createTime || '暂无数据' }}
        </el-descriptions-item>
        <el-descriptions-item label="最后更新时间">
          {{ updateTime || '暂无数据' }}
        </el-descriptions-item>
      </el-descriptions>

      <div class="action-buttons">
        <el-button type="danger" @click="passwordVisible = true">修改密码</el-button>
      </div>
    </el-card>

    <!-- 密码弹窗完全不变 -->
    <el-dialog v-model="passwordVisible" title="修改密码" width="500px" destroy-on-close>
      <el-form :model="passwordForm" :rules="passwordRules" label-width="120px" ref="passwordFormRef">
        <el-form-item label="原密码" prop="oldPwd">
          <el-input v-model="passwordForm.oldPwd" type="password" show-password placeholder="原密码" autocomplete="off" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd">
          <el-input v-model="passwordForm.newPwd" type="password" show-password placeholder="6-16位非空字符" maxlength="16" show-word-limit autocomplete="off" />
        </el-form-item>
        <el-form-item label="确认新密码" prop="renewPwd">
          <el-input v-model="passwordForm.renewPwd" type="password" show-password placeholder="再次输入新密码" autocomplete="off" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPassword">确定修改</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.profile-container {
  padding: 20px;
  max-width: 900px;
  margin: 0 auto;
}

.profile-card {
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
}

.title {
  color: #303133;
}

.info-table {
  margin: 20px 0;
}

.label-bg {
  background-color: #f5f7fa !important;
  font-weight: bold;
}

.action-buttons {
  text-align: center;
  margin-top: 30px;
}

.action-buttons .el-button + .el-button {
  margin-left: 20px;
}
</style>