import axios from 'axios';
import {ElMessage} from "element-plus";//引入用到的组件

const defaultError = () => ElMessage.error('发生错误，请联系管理员。') //定义默认错误提示语
const defaultFailure = (message) => ElMessage.warning(message) //后端请求返回失败信息时将其打印
function getAuthToken() {
    const token = localStorage.getItem('authToken');
    if (token) {
        // 确保返回的token带有Bearer前缀
        return `Bearer ${token}`;
    }
    return '';
}
//post请求示例
function post(url, data, success, failure = defaultFailure, error = defaultError, useJson = false) {
    const contentType = useJson ? "application/json" : "application/x-www-form-urlencoded";
    const requestData = useJson ? JSON.stringify(data) : new URLSearchParams(data).toString();  // 使用 URLSearchParams 格式化表单数据

    axios.post(url, requestData, {
        headers: {
            "Authorization": getAuthToken(),
            "Content-Type": contentType // 动态设置 Content-Type
        },
        withCredentials: true
    })
        .then(({data}) => {
            if (data.success) {
                success(data.message, data.data, data.status);
            } else {
                failure(data.message, data.data, data.status);
            }
        })
        .catch(error);
}


function get(url, data = null, success, failure = defaultFailure, error = defaultError) {
    const config = {
        withCredentials: true,
        params: data,  // 将数据作为查询参数
        headers: {
            "Authorization": getAuthToken(),
        }
    };

    axios.get(url, config)
        .then(({data}) => {

            if (data.success)
                success(data.message,data.data, data.status)
            else
                failure(data.message,data.data, data.status)
        })
        .catch(error)
}

function InternalGet(url, success, failure = defaultFailure, error = defaultError) {
    axios.get(url, {
        withCredentials: true
    }).then((response) => {
        success(response.data);
    }).catch(error);
}


export {get, post, InternalGet} //导出get post InternalGet方法 供所有组件使用