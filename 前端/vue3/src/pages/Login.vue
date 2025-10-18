<template>
    <div id="Login">
        <div class="main-part">
            <div class="text">
                <h3>欢迎登录</h3>
            </div>
            <div class="login-form">
                <div>
                    <span>用户名：</span>
                    <input type="text" v-model="formJs.userName" placeholder="请输入用户名">
                </div>
                <div>
                    <span>密码：</span>
                    <input type="text" v-model="formJs.password" placeholder="请输入密码">
                </div>
                <RouterLink to="/register">没有账号？前往注册</RouterLink>
                <br>
                <button @click="clickLoginBtn">登录</button>
            </div>
            <button @click="testToken" v-show="false">测试token！</button>
        </div>
    </div>
</template>

<script setup lang="ts" name="Login">
    import { ref, onMounted, watch, inject } from 'vue'
    import { useRoute } from 'vue-router'
    import axios from 'axios'
    import { type RegisterOrLoginForm } from '@/types'
    import isEmpty from '@/utils/isEmpty'
    import { RouterLink, useRouter } from 'vue-router'
    import { useUserDataStore } from "@/store/userData"
    import { useRefreshCountStore } from '@/store/refreshCount'
    import { useRegisterInfoStore } from '@/store/registerInfo'
    import { useLoginInfoStore } from '@/store/loginInfo'
    import { useOverallFunctionStore } from '@/store/overallFunction'

    const baseUrl = inject('baseUrl')

    const route = useRoute()
    const overallFunctionStore = useOverallFunctionStore()
    const refreshCountStore = useRefreshCountStore();
    const userDataStore = useUserDataStore();
    const registerInfoStore = useRegisterInfoStore();
    const loginInfoStore = useLoginInfoStore();

    const router = useRouter()

    let formJs = ref<RegisterOrLoginForm>({
        userName: '',
        password: '',
    })

    onMounted(() => {
        if (registerInfoStore.autoFillTime == 0) {
            formJs.value.userName = registerInfoStore.userName;
            formJs.value.password = registerInfoStore.password;
            registerInfoStore.autoFillTime++
        }
    })

    async function clickLoginBtn() {
        if (isEmpty(formJs.value.userName)) {
            alert("请输入 用户名")
            return;
        } else if (isEmpty(formJs.value.password)) {
            alert("请输入 密码")
            return;
        }
        try {
            let token = (await axios.post(baseUrl+"/login", {
                userName: formJs.value.userName,
                password: formJs.value.password
            })).data
            if (token == "00") {
                loginInfoStore.userName = formJs.value.userName;
                loginInfoStore.password = formJs.value.password;
                loginInfoStore.autoFillTime = 0
                alert('登录失败！用户名错误')
            } else if (token == "01") {
                alert('登录失败！密码错误')
            } else {
                loginInfoStore.userName = "";
                loginInfoStore.password = "";
                localStorage.setItem("token", token)
                console.log("返回数据（token）：", token)
                let userData = (await axios.get(baseUrl+"/db/user/findbytoken", {
                    headers: {
                        token
                    }
                })).data
                console.log("返回的java对象user：", JSON.stringify(userData))
                console.log("原pinia:", userDataStore.userData)
                userDataStore.userData.id = userData.id;
                userDataStore.userData.authority = userData.authority;
                userDataStore.userData.name = userData.name;
                userDataStore.userData.gmtCreated = userData.gmtCreated;
                userDataStore.userData.gmtModified = userData.gmtModified;
                userDataStore.userData.avatarUrl = (await axios.get(baseUrl+"/userprofile?name="+userData.name)).data.avatarUrl
                console.log("现在pinia:", userDataStore.userData)
                alert("登录成功！")
                refreshCountStore.login_to_home_count = 0
                router.push("/home")
            }
        } catch (e) {
            console.log('error-register')
        }
    }

    async function testToken() {
        let token = (await axios.get(baseUrl+"/loginToken", {
            headers: {
                token: localStorage.getItem('token') || ''
            }
        })).data;
        alert(token || "请求失败，token错误")
    }
</script>

<style lang="scss" scoped>
.main-part {
    width: 70vw;
    margin: 0 auto;
    background-color: aliceblue;
    display: flex;
    flex-direction: column;
    padding: 10px;
}
</style>