<template>
    <div class="auth-page">
        <div class="auth-card">
            <!-- 紫色滑动区域 -->
            <div class="auth-left" :class="{ 'slide-right': !isLogin }">
                <div class="auth-text">
                    <h2 class="auth-greeting">
                        {{ isLogin ? 'Hello, User!' : 'Welcome Back!' }}
                    </h2>
                    <p class="auth-desc">
                        {{ isLogin
                            ? '填写你的账户信息以使用 EmployeeManageSystem'
                            : '填写你的个人信息以注册 EmployeeManageSystem'
                        }}
                    </p>
                    <el-button class="toggle-btn" @click="toggleMode">
                        {{ isLogin ? '去创建账户' : '已有账户，直接登录' }}
                    </el-button>
                </div>
            </div>

            <!-- 表单区域 -->
            <div class="auth-right" :class="{ 'shift-left': !isLogin }">
                <h1 class="auth-title">{{ isLogin ? '用户登录' : '注册账户' }}</h1>

                
                <p class="divider"> {{ isLogin ? ' use your account' : ' register a new account' }}</p>

                <el-form class="auth-form" :model="form" ref="formRef" :rules="Rules">
                    <!-- Username：登录和注册都显示，但内容独立 -->
                    <el-form-item prop ="username">
                        <el-input v-model="form.username" placeholder="Username" class="auth-input" clearable />
                    </el-form-item>

                    <!-- Password -->
                    <el-form-item prop ="password">
                        <el-input v-model="form.password" placeholder="Password" type="password" class="auth-input"
                            show-password clearable />
                    </el-form-item>

                    <!-- 注册页专属：确认密码 -->
                    <el-form-item v-if="!isLogin" prop ="passwordAgain">
                        <el-input v-model="form.passwordAgain" placeholder="Confirm Password" type="password"
                            class="auth-input" show-password clearable />
                    </el-form-item>


                    <!-- 提交按钮 -->
                    <el-form-item>
                        <el-button type="primary" class="submit-btn" @click="handleSubmit">
                            {{ isLogin ? '用户登录' : '注册账户' }}
                        </el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, reactive, watch ,nextTick} from 'vue'
import { registerService ,loginService} from '@/api/Login'
import { ElButton, ElForm, ElFormItem, ElInput ,ElMessage} from 'element-plus'
import { useTokenStore } from '@/stores/token.js'


const tokenStore = useTokenStore();
//console.log('Login.vue里的tokenStore实例：', tokenStore); 

const isLogin = ref(true)
// 自定义确认密码校验规则
const passwordAgainValidator = (rule, value, callback) => {
    if (!value&&!isLogin.value) {
        callback(new Error('请再次输入密码！'))
    }else if(value !== form.password) {
        callback(new Error('两次密码不一致！'))
    } else {
        callback()
    }
}
//校验规则
const Rules = reactive({ 
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }, 
        { min: 2, max: 10, message: '用户名长度在2到10个字符之间', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' }, 
        { min: 6, max: 16, message: '密码长度在6到16个字符之间', trigger: 'blur' }
    ],
    passwordAgain: [
        { message: '请再次输入密码', trigger: 'blur' },
        { validator: passwordAgainValidator, trigger: 'blur' }
    ]
})

// 表单数据
const form = reactive({
    username: '',
    password: '',
    passwordAgain: ''
})
const formRef = ref(null)

// 切换模式时自动清空表单
const toggleMode = () => {
    isLogin.value = !isLogin.value
    nextTick(() => {
        form.username = ''
        form.password = ''
        form.passwordAgain = ''
        formRef.value?.clearValidate()
    })
}

watch(isLogin, () => {
    form.username = ''
    form.password = ''
    form.passwordAgain = ''
}) 

const handleSubmit = async () => {
    // 1. 先执行表单内置校验，校验失败直接返回
    if (!formRef.value) return
    try {
        await formRef.value.validate()
    } catch (err) {
        ElMessage.error('表单填写有误，请检查！')
        return
    }

    //处理注册逻辑
    if (!isLogin.value) {
        try {
            const res = await registerService({
                username: form.username,
                password: form.password
            })
            if(res.code==0){
                ElMessage.success('注册成功，请登录！')
                isLogin.value = true
            }else{
                ElMessage.error(res.msg || '注册失败，请重试！')
                return // 注册失败不执行后续跳转
            }

        } catch (error) {
            // 捕获请求异常
            console.error('注册请求异常：', error)
            ElMessage.error('网络异常或服务器错误，请稍后再试！')
            return // 异常时不执行后续跳转
        }
    }
    // 当前是登录模式
    else { 
        try {
            const res = await loginService({
                username: form.username,
                password: form.password
            })
            //console.log('登录接口返回完整数据：', res); 
            //console.log('登录接口返回的token：', res.data?.token); 

            if(res.code==0){
                ElMessage.success( '登录成功！')

                tokenStore.setToken(res.data)//得到的token存入pinia
                //console.log('调用setToken后，token值：', tokenStore.token.value); 


                location.href = '/profile' // 简单粗暴的跳转
            }
            else{
                ElMessage.error(res.msg || '登录失败，请重试！')
                return // 登录失败不执行后续跳转
            }
            console.log(res)//Token
            
        } catch (error) {
            ElMessage.error('登录请求异常或业务失败：', error)
        }
    }
}
</script>

<style scoped>
.auth-page {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(245, 247, 250, 0.005);
    padding: 20px;
    margin: 0;
    box-sizing: border-box;
}

.auth-card {
    width: 100%;
    max-width: 900px;
    height: 580px;
    border-radius: 30px;
    box-shadow: 0 10px 50px rgba(0, 0, 0, 0.1);
    position: relative;
    overflow: hidden;
    background-color: rgba(255, 255, 255);
}

.auth-left {
    position: absolute;
    top: 0;
    left: 0;
    width: 45%;
    height: 100%;
    background: linear-gradient(135deg, #6a28d9, #8b5cf6);
    color: white;
    padding: 60px 30px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    text-align: center;
    border-radius: 0 120px 120px 0;
    transition: left 0.8s cubic-bezier(0.34, 1.56, 0.64, 1), border-radius 0.8s ease-in-out;
    z-index: 5;
}

.auth-left.slide-right {
    left: calc(100% - 45%);
    border-radius: 120px 0 0 120px;
}

.auth-text {
    width: 100%;
    padding: 0 20px;
}

.auth-greeting {
    font-size: 28px;
    margin-bottom: 160px;
    font-weight: 600;
}

.auth-desc {
    font-size: 14px;
    opacity: 0.9;
    margin-bottom: 32px;
    line-height: 1.5;
}

.toggle-btn {
    background-color: transparent;
    color: white;
    border: 2px solid white;
    padding: 10px 30px;
    border-radius: 30px;
    font-weight: 600;
    transition: all 0.3s ease;
}

.toggle-btn:hover {
    background-color: white;
    color: #6a28d9;
    transform: translateY(-2px);
}

.auth-right {
    position: absolute;
    top: 0;
    right: 0;
    width: 55%;
    height: 100%;
    padding: 60px 40px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    align-items: center;
    transition: right 0.8s cubic-bezier(0.34, 1.56, 0.64, 1);
    z-index: 10;
}

.auth-right.shift-left {
    right: calc(100% - 55%);
}

.auth-title {
    font-size: 32px;
    font-weight: 700;
    margin-bottom: 24px;
    color: #1e293b;
}

.social-buttons {
    display: flex;
    gap: 16px;
    margin-bottom: 20px;
}

.social-btn {
    width: 100px;
    height: 44px;
    border-radius: 8px;
    padding: 0;
    font-size: 16px;
    font-weight: 600;
    border: none;
}

.social-btn.wechat {
    background-color: #07c160;
    color: white;
}

.social-btn.qq {
    background-color: #1296db;
    color: white;
}

.divider {
    text-align: center;
    color: #94a3b8;
    margin: 24px 0;
    font-size: 13px;
    position: relative;
}

.divider::before,
.divider::after {
    content: '';
    position: absolute;
    top: 50%;
    width: 30%;
    height: 1px;
    background-color: #e2e8f0;
}

.divider::before {
    left: 10%;
}

.divider::after {
    right: 10%;
}

.auth-form {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.auth-input {
    height: 48px;
    border-radius: 8px;
    border: 1px solid #e2e8f0;
    padding: 0 15px;
    font-size: 15px;
    transition: all 0.2s ease;
    width: 100%;
    max-width: 300px;
}

.auth-input:focus {
    border-color: #6a28d9;
    box-shadow: 0 0 0 3px rgba(106, 40, 217, 0.1);
}

.forgot-link {
    text-align: right;
    margin-bottom: 8px;
    width: 100%;
    max-width: 300px;
}

.forgot-password {
    color: #6a28d9;
    font-size: 13px;
    text-decoration: none;
}

.forgot-password:hover {
    text-decoration: underline;
}

.submit-btn {
    width: 100%;
    max-width: 300px;
    height: 48px;
    border-radius: 8px;
    background-color: #6a28d9;
    border: none;
    font-size: 15px;
    font-weight: 600;
    transition: all 0.3s ease;
}

.submit-btn:hover {
    background-color: #5a18c9;
    transform: translateY(-2px);
    box-shadow: 0 5px 15px rgba(106, 40, 217, 0.3);
}

@media (max-width: 768px) {
    .auth-card {
        height: auto;
    }

    .auth-left {
        position: relative;
        width: 100%;
        height: 300px;
        border-radius: 30px 30px 0 0;
        left: 0 !important;
    }

    .auth-left.slide-right {
        border-radius: 0 0 30px 30px;
    }

    .auth-right {
        position: relative;
        width: 100%;
        padding: 40px 20px;
        right: 0 !important;
    }
}
</style>