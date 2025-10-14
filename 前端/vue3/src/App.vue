<template>
  <div id="title-part">
    <div class="logout" @click="clickLogoutBtn" v-if="userData?.authority">登出</div>
    <div class="login" @click="clickLoginBtn"
      v-if="!userData?.authority && !['/login', '/register'].includes(route.path)">去登录</div>
    <RouterLink to="/home">返回首页</RouterLink> 博 客 网 站 <RouterLink to="/write">去写作</RouterLink>
    <div class="userInfo" v-if="userData?.authority" @click="clickUserInfo">
      <div class="userName">{{ useUserDataStore().userData?.name }}</div>
      <div class="userAuthority">权限级别：{{ useUserDataStore().userData?.authority }}</div>
    </div>
  </div>
  <div id="formal-part">
    <RouterView></RouterView>
  </div>
</template>

<script setup lang="ts" name="App">
  import { ref, watch, onUpdated } from 'vue';
  import { RouterLink } from 'vue-router'
  import { type UserWithAvatarUrl } from "@/types"
  import { useUserDataStore } from "@/store/userData"
  import { useRouter, useRoute } from 'vue-router'
  import { useRefreshCountStore } from '@/store/refreshCount'

  const refreshCountStore = useRefreshCountStore()
  const router = useRouter();
  const route = useRoute()

  import {storeToRefs} from 'pinia'
  let {userData} = storeToRefs(useUserDataStore())
  function clickLogoutBtn() {
    const option: boolean = confirm("确认退出登录？");
    if (!option) {
      return;
    }
    useUserDataStore().userData = {
      id: "",
      name: "",
      authority: "",
      gmtCreated: "",
      gmtModified: "",
      avatarUrl:"",
    }
    userData.value = {
      id: "",
      name: "",
      authority: "",
      gmtCreated: "",
      gmtModified: "",
      avatarUrl:"",
    }
    localStorage.removeItem("token");
  }
  function clickLoginBtn() {
    router.push("/login")
  }
  function clickUserInfo() {
    refreshCountStore.otherSpace_to_mySpace_count = 0;
    router.push("/space/" + userData.value?.name);
  }
</script>

<style lang="scss" scoped>
#title-part {
  height: 50px;
  position: fixed;
  background-color: skyblue;
  text-align: center;
  font-size: 30px;
  color: rgb(119, 64, 44);
  width: 100%;
  font-weight: 800;
  z-index: 999;
}

#formal-part {
  padding-top: 50px;
}

.logout {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 0;
  top: 10px;
  color: rgb(74, 56, 15);
  font-size: 18px;
  cursor: pointer;
}

.login {
  display: flex;
  flex-direction: column;
  position: absolute;
  left: 0;
  top: 10px;
  color: rgb(66, 76, 40);
  font-size: 18px;
  cursor: pointer;
}

.userInfo {
  display: flex;
  flex-direction: column;
  position: absolute;
  right: 0;
  top: 0;
  color: rgb(255, 0, 93);
  font-size: 18px;
  cursor: pointer;
}
</style>
