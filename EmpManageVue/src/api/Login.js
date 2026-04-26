import request from '@/utils/request2'

export const registerService = (Data) =>{
    const params = new URLSearchParams()

    for(let key in Data){
        params.append(key,Data[key]);
    }
    return request.post('/user/register',params);
}
export const loginService = (data) =>{
    const params = new URLSearchParams()

    for(let key in data){
        params.append(key,data[key]);
    }
    return request.post('/user/login',params);
}