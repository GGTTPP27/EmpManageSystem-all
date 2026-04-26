import request from '@/utils/request'

export const updateRetiredService = (data) => request.put('/employee/retired', data) 