// src/api/Employee.js
import request from '@/utils/request'
//import { useTokenStore } from '@/stores/token.js'

export const searchAllService = () => request.get('/employee/getAll')
export const searchByConditionService = (params) => request.get('/employee/Search', { params })


export const addEmployeeService = (data) => request.post('/employee', data)
export const updateEmployeeService = (data) => request.put('/employee', data) 
export const deleteEmployeeService = (params) => request.delete('/employee', { params }) 
