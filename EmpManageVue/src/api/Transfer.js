
import request from '@/utils/request2'

export const searchAllService = () => request.get('/transfer/getAll')
export const search1Service = (params) => request.get('/transfer/search1',{ params })
export const search2Service = (params) => request.get('/transfer/search2',{ params })
export const deleteService =(params)=> request.delete('/transfer/delete',{params})
//调动员工
export const addService=(data)=> {

  const formData = new FormData();

  formData.append('empId', data.empId);
  formData.append('transferInDpt', data.transferInDpt.toString());
  formData.append('empJob', data.empJob);
  formData.append('empTitle', data.empTitle);
  formData.append('transferReason', data.transferReason);

  return request.post('/transfer', formData);
}