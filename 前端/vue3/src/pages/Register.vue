<template>
    <div id="Register">
        <div class="main-part">
            <div class="text">
                <h3>欢迎注册</h3>
            </div>
            <div class="register-form">
                <div>
                    <span>用户名：</span>
                    <input type="text" v-model="formJs.userName" placeholder="请输入用户名">
                </div>
                <div>
                    <span>密码：</span>
                    <input type="text" v-model="formJs.password" placeholder="请输入密码">
                </div>
                <RouterLink to="/login">已有账号？前往登录</RouterLink>
                <br>
                <button @click="clickRegisterBtn">注册</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Register">
    import { ref, onMounted } from 'vue'
    import axios from 'axios'
    import { type RegisterOrLoginForm } from '@/types'
    import isEmpty from '@/utils/isEmpty'
    import { useRegisterInfoStore } from '@/store/registerInfo'
    import { useLoginInfoStore } from '@/store/loginInfo'

    const loginInfoStore = useLoginInfoStore();
    const registerInfoStore = useRegisterInfoStore()
    let formJs = ref<RegisterOrLoginForm>({
        userName: '',
        password: '',
    })

    onMounted(() => {
        if (loginInfoStore.autoFillTime == 0) {
            formJs.value.userName = loginInfoStore.userName;
            formJs.value.password = loginInfoStore.password;
            loginInfoStore.autoFillTime++
        }
    })

    async function clickRegisterBtn() {
        if (isEmpty(formJs.value.userName)) {
            alert("请输入 用户名")
            return;
        } else if (isEmpty(formJs.value.password)) {
            alert("请输入 密码")
            return;
        }
        registerInfoStore.userName = formJs.value.userName;
        registerInfoStore.password = formJs.value.password;
        registerInfoStore.autoFillTime = 0
        try {
            let responseMsg = (await axios.post("http://localhost:8080/db/user/add", {
                userName: formJs.value.userName,
                password: formJs.value.password
            })).data
            if (responseMsg == "11") {
                loginInfoStore.autoFillTime = 0
                alert('注册成功')
            } else if (responseMsg == "0") {
                alert('注册失败：用户名已存在')
            }
        } catch (e) {
            console.log('error-register')
        }
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