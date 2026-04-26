// src/router/index.ts
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Layout.vue'

const Employee = () => import('@/views/Employee.vue')
const Transfer = () => import('@/views/Transfer.vue')

const Retire = () => import('@/views/Retire.vue')
const Department = () => import('@/views/Department.vue')
const Profile = () => import('@/views/Profile.vue')
const Login = () => import('@/views/Login.vue')

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: Layout,
    redirect: '/Login',
    children: [
      {
        path: '/employee',
        component: Employee,
        name: 'Employee',
        meta: { title: '员工信息管理' }
      },
      {
        path: '/transfer',
        component: Transfer,
        name: 'Transfer',
        meta: { title: '调度信息管理' }
      },
      {
        path: '/retire',
        component: Retire,
        name: 'Retire',
        meta: { title: '离职退休管理' }
      },
      {
        path: '/department',
        component: Department,
        name: 'Department',
        meta: { title: '部门信息管理' }
      },
      {
        path: '/profile',
        component: Profile,
        name: 'Profile',
        meta: { title: '管理员信息' }
      }
    ]
  },
  {
    path: '/login',
    component: Login,
    meta: { title: '登录' }
  },
  {
    path: '/:pathMatch(.*)*',
    component: () => import('@/views/Profile.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router