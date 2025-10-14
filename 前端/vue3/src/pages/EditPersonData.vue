<template>
    <div id="EditPersonData">
        <div class="main-part">
            <div class="userInfo">
                <div class="left">
                    <UserAvatar :url="userProfile.avatarUrl" pxNum="120" />
                    <div>输入图片地址：</div>
                    <div class="bottom">
                        <textarea v-model="userProfile.avatarUrl"></textarea>
                        <!-- <button @click="getRandomImg">随机来一个</button> -->
                    </div>
                </div>
                <div class="right">
                    <div>
                        <span class="name">姓名：
                            <input type="text" v-model="userProfile.userName">
                        </span>
                    </div>
                    <div>
                        <span class="gender">性别：
                            <select v-model="userProfile.gender">
                                <option value="Blank">请选择</option>
                                <option value="Male">男</option>
                                <option value="Female">女</option>
                                <option value="Other">其他</option>
                            </select>
                        </span>
                    </div>
                    <div>
                        <span class="birthday">生日：
                            <input type="text" v-model="userProfile.birthday">
                        </span>
                    </div>
                    <div>
                        <span class="signatrue">
                            <span>签名：</span>
                            <textarea v-model="userProfile.signature"></textarea>
                        </span>
                    </div>
                </div>
            </div>
            <template v-if="userDataStore.userData?.name == name">
                <button class="EditBtn" @click="clickEditBtn">提交更改</button>
            </template>
        </div>
    </div>
</template>

<script setup lang="ts" name="EditPersonData">
    import { ref, onMounted, watch, onUnmounted } from 'vue'
    import { type UserProfileWithUserName } from '@/types'
    import { useUserDataStore } from '@/store/userData'
    import { useRouter } from 'vue-router'
    import isEmpty from '@/utils/isEmpty'
    import isConformFormat from '@/utils/isConformFormat'
    import correctionDate from '@/utils/correctionDate'
    import UserAvatar from '@/components/UserAvatar.vue'
    import moment from 'moment';
    import axios from 'axios'

    const userAvatar = ref()

    moment.locale('zh-cn');
    const router = useRouter()
    const { name } = defineProps(['name'])
    const userDataStore = useUserDataStore();

    const dateFormat1 = "YYYY-MM-DD";
    const dateFormat2 = "YYYY/MM/DD";
    let userProfile = ref<UserProfileWithUserName>({
        id: "",
        userName: "",
        userId: "",
        avatarUrl: "",
        gender: "",
        signature: "",
        birthday: "",
        gmtCreated: "",
        gmtModified: "",
    })

    let stopWatch: Function;

    onMounted(async () => {
        userProfile.value = (await axios.get("http://localhost:8080/userprofile?name=" + name)).data;
        userProfile.value.userName = name;

        // if (!userProfile.value.avatarUrl) {
        //     userAvatar.value.style.opacity = 0;
        // }

        // stopWatch = watch(() => userProfile.value.avatarUrl, () => {
        //     if (!userProfile.value.avatarUrl) {
        //         userAvatar.value.style.opacity = 0;
        //     } else {
        //         userAvatar.value.style.opacity = 1;
        //     }
        // })
    })

    // onUnmounted(() => {
    //     stopWatch()
    // })

    async function clickEditBtn() {
        // console.log(userProfile.value)
        if (isEmpty(userProfile.value.userName)) {
            alert("用户名为必填项")
            return;
        }
        if (!isEmpty(userProfile.value.birthday)) {
            userProfile.value.birthday = correctionDate(userProfile.value.birthday?.trim() as string);
            if (!isConformFormat("-", userProfile.value.birthday as string)
                && !isConformFormat("/", userProfile.value.birthday as string)
                && !isConformFormat(" ", userProfile.value.birthday as string)) {
                alert("生日格式必须是 yyyy-MM-dd 或 yyyy/MM/dd")
                return;
            }
            userProfile.value.birthday = userProfile.value.birthday.replace(/[/\s]/g, '-');
            // console.log(userProfile.value.birthday)
            if (!moment(userProfile.value.birthday, dateFormat1, true).isValid() && !moment(userProfile.value.birthday, dateFormat2, true).isValid()) {
                alert("生日内容不合法")
                return;
            }
        }

        const responseMsg = (await axios.post("http://localhost:8080/wpwun/update", {
            id: userProfile.value.id,
            userName: userProfile.value.userName,
            userId: userProfile.value.userId,
            avatarUrl: userProfile.value.avatarUrl,
            gender: userProfile.value.gender,
            signature: userProfile.value.signature,
            birthday: userProfile.value.birthday
        })).data;
        if (responseMsg == 1) {
            // genderToString();
            alert("修改成功");
            userDataStore.userData.name = userProfile.value.userName;
            userDataStore.userData.avatarUrl = userProfile.value.avatarUrl;
            router.push("/space/" + userProfile.value.userName)
        } else {
            alert("修改失败。" + responseMsg)
        }
    }

    async function getRandomImg(){
        userProfile.value.avatarUrl = "https://picsum.photos/200/200?random="+ Math.floor(Math.random() * 9999+1);
    }

    // function genderToString(gender:string) {
    //     switch (gender) {
    //         case 'Male':
    //             return '男';
    //         case 'Female':
    //             return '女';
    //         case 'Other':
    //             return '其他';
    //         default:
    //             return '不想透露';
    //     }
    // }

    // function stringToGender(str:string) {
    //     switch (str) {
    //         case '男':
    //             return 'Male';
    //         case '女':
    //             return 'Female';
    //         case '其他':
    //             return 'Other';
    //         default:
    //             return 'Blank';
    //     }
    // }
</script>

<style lang="scss" scoped>
.main-part {
    width: 70vw;
    margin: 0 auto;
    background-color: aliceblue;
    display: flex;
    flex-direction: column;
    padding: 10px;

    .userInfo {
        display: flex;
        position: relative;

        .EditBtn {
            position: absolute;
            right: 10px;
            top: 10px;
        }

        .left {
            .bottom{
                display: flex;
            }

            img {
                display: inline-block;
                width: 120px;
                height: 120px;
                border-radius: 50%;
            }
        }

        .right {
            margin-left: 10px;
            display: flex;
            flex-direction: column;

            .name,
            .gender,
            .birthday,
            .signatrue {
                display: flex;
                margin-bottom: 5px;
            }
        }
    }
}
</style>