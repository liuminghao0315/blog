<template>
    <div id="UserSpace">
        <div class="main-part">
            <div class="userInfo">
                <div class="left">
                    <UserAvatar :url="userProfile.avatarUrl" pxNum="120" />
                </div>
                <div class="right">
                    <span class="name">{{ name }}</span>
                    <span class="gender">性别：
                        <span>{{ userProfile.gender }}</span>
                    </span>
                    <span class="birthday">生日：
                        <span v-if="userProfile.birthday">{{ userProfile.birthday }}</span>
                        <span v-else>暂无</span>
                    </span>
                    <span class="signatrue">个性签名：
                        <span v-if="userProfile.signature">{{ userProfile.signature }}</span>
                        <span v-else>暂无</span>
                    </span>
                </div>
                <template v-if="userDataStore.userData?.name == name">
                    <button class="EditBtn" @click="changeEditBtn">编辑资料</button>
                </template>
            </div>
            <div class="chooseShowWhat">
                <div class="options">
                    <div class="showMyBlogs" @click="chooseMyBlogs" :class="showMyBlogs ? 'activeBtn' : ''">
                        {{ showMyBlogs ? "隐藏" : "查看" }}{{ genderComputed }}的文章</div>
                    <div class="showFavoriteBlogs" @click="chooseFavoriteBlogs"
                        :class="showFavoriteBlogs ? 'activeBtn' : ''">
                        {{ showFavoriteBlogs ? "隐藏" : "查看" }}收藏文章</div>
                </div>
            </div>
            <div class="favoriteBlogs" v-if="userProfile?.id && showFavoriteBlogs">
                <FavoritePart :userProfile="userProfile" />
            </div>
            <div class="showMyBlogs" v-if="userProfile?.id && showMyBlogs">
                <MyBlogsPart :userProfile="userProfile" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="UserSpace">
    import { ref, onUpdated, onMounted, onBeforeMount, toRefs, computed, inject } from 'vue'
    import { type BlogWithUserName, type UserProfile } from '@/types'
    import { useRouter } from 'vue-router'
    import { useUserDataStore } from '@/store/userData'
    import { useRefreshCountStore } from '@/store/refreshCount'
    import UserAvatar from '@/components/UserAvatar.vue'
    import axios from 'axios'
    import useFavoriteApi from '@/hooks/useFavoriteApi'
    import setAllAttributeFalse from '@/utils/setAllAttributeFalse'
    import FavoritePart from '@/components/FavoritePart.vue'
    import MyBlogsPart from '@/components/MyBlogsPart.vue'

    const baseUrl = inject('baseUrl')

    let arr = 0
    const router = useRouter()
    const { name } = defineProps(['name'])
    const userDataStore = useUserDataStore();
    const refreshCountStore = useRefreshCountStore()

    const userAvatar = ref()

    let userProfile = ref<UserProfile>({
        id: "",
        userId: "",
        avatarUrl: "",
        gender: "",
        signature: "",
        birthday: "",
        gmtCreated: "",
        gmtModified: "",
    })

    async function toDO() {
        userProfile.value = (await axios.get(baseUrl+"/userprofile?name=" + name)).data;
        userProfile.value.gender = genderToString(userProfile.value.gender as string)
    }

    onBeforeMount(async () => {
        await toDO();
    })

    onUpdated(async () => {
        if (refreshCountStore.otherSpace_to_mySpace_count === 0) {
            refreshCountStore.otherSpace_to_mySpace_count++
            showMyBlogs.value = false;
            showFavoriteBlogs.value = false;
            // console.log("变")
            await toDO()
        }
    })

    function changeEditBtn() {
        router.push(`/space/${name}/edit`)
    }

    function genderToString(gender: string) {
        switch (gender) {
            case 'Male':
                return '男';
            case 'Female':
                return '女';
            case 'Other':
                return '其他';
            default:
                return '不想透露';
        }
    }

    // 以下是选择选项，进行展示
    let genderComputed = computed(() => {
        if (userProfile.value.userId == userDataStore.userData.id) {
            return "我"
        }
        switch (userProfile.value.gender) {
            case '男':
                return '他';
            case '女':
                return '她';
            case '其他':
                return 'Ta';
            default:
                return 'Ta';
        }
    })

    let showWhat = ref({
        showMyBlogs: false,
        showFavoriteBlogs: false,
    })

    let refs = toRefs(showWhat.value)

    let showMyBlogs = refs.showMyBlogs

    let showFavoriteBlogs = refs.showFavoriteBlogs

    function chooseMyBlogs() {
        setAllAttributeFalse(showMyBlogs.value);
        if (showMyBlogs.value === false) {
            showMyBlogs.value = true;
            showFavoriteBlogs.value = false;
        } else {
            showMyBlogs.value = false;
        }
        // console.log(userProfile.value.gender)
    }

    function chooseFavoriteBlogs() {
        // setAllAttributeFalse(showFavoriteBlogs.value);
        if (showFavoriteBlogs.value === false) {
            showFavoriteBlogs.value = true;
            showMyBlogs.value = false;
        } else {
            showFavoriteBlogs.value = false;
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

    .userInfo {
        display: flex;
        position: relative;

        .EditBtn {
            position: absolute;
            right: 10px;
            top: 10px;
        }

        .right {
            margin-left: 10px;
            margin-top: 10px;
            display: flex;
            flex-direction: column;

            .name {
                font-size: 17px;
                font-weight: 600;
            }

        }
    }

    .chooseShowWhat {
        margin-top: 10px;
        padding: 20px 0;

        .options {
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: space-evenly;

            .activeBtn {
                background-color: aquamarine;
            }

            .showMyBlogs,
            .showFavoriteBlogs {
                border: 1px solid #1f1b1b;
                padding: 5px;
                font-weight: 700;
                border-radius: 20px;
                user-select: none;
                cursor: pointer;
            }
        }
    }
}
</style>