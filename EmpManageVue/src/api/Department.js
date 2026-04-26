// src/api/Department.js
import request from '@/utils/request'


export const getAllDepartmentsService = () => request.get('/department/getAll')




export const updateDepartmentService = (data) => request.put('/department', data)
export const deleteDepartmentService = (params) => request.delete('/department', { params })
export const getDepartmentDetailService = (params) => request.get('/department', { params })