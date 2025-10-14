<template>
    <div class="comments">
        <div class="sendComment">
            <div class="div-img">
                <UserAvatar :url="userData.avatarUrl" :pxNum="80" />
            </div>
            <div class="commentInput">
                <textarea v-model="commentInput" placeholder=" 留下您的评论"></textarea>
            </div>
            <button @click="sendComment">发送评论</button>
        </div>
        <div class="otherComments">
            <div class="aGroupOfCommentsDiv" v-for="aGroupOfComments in structuredComments" :key="aGroupOfComments.id">
                <AGroupOfComments :headComment="aGroupOfComments" :id="id" />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="CommentsPart">
    import { ref, onMounted, onBeforeMount, type StyleValue, onUnmounted, provide, watch } from 'vue'
    import { type StructuredComments, type CommentDO } from "@/types"
    import axios from 'axios'
    import { MdPreview } from 'md-editor-v3'
    import { useUserDataStore } from "@/store/userData"
    import { useRouter,useRoute } from 'vue-router'
    import NonHeadComments from './NonHeadComments.vue'
    import UserAvatar from './UserAvatar.vue'
    import { nanoid } from 'nanoid'
    import AGroupOfComments from './AGroupOfComments.vue'
    import isEmpty from '@/utils/isEmpty'
    import emitter from '@/utils/emitter'

    const commentInput = ref("")
    const router = useRouter();
    const route = useRoute()
    const userData = useUserDataStore().userData;
    const { id } = defineProps<{ id: string }>()

    let structuredComments = ref<StructuredComments[]>([])
    let comments = ref()
    const userAvatar = ref()

    onBeforeMount(async () => {
        await getComments();
        // console.log(JSON.stringify(structuredComments.value))
    })

    watch(() => route.query.id, async () => {
        await getComments();
    })

    async function sendComment() {
        if (isEmpty(commentInput.value.trim())) {
            alert('评论内容不能为空');
            commentInput.value = "";
            return;
        }
        const requestBody: CommentDO = {
            id: nanoid(),
            articleId: id,
            authorId: userData.id,
            content: commentInput.value
        }
        const responseMsg = (await axios.post("http://localhost:8080/db/commnet/add", requestBody)).data;
        if (responseMsg == 1) {
            alert("发送成功");
            await getComments();
            commentInput.value = ""
        } else {
            alert("发送失败");
        }
    }

    async function getComments() {
        const comments = (await axios.get("http://localhost:8080/findStructuredCommentWithUserNameAndAvatarUrl", {
            params: {
                articleId: id,
                userId: userData.id,
            }
        })).data
        structuredComments.value.splice(0, structuredComments.value.length);
        structuredComments.value.push(...comments)
    }

    emitter.on('update-a-group-of-comments', (value: any) => {
        // structuredComments.value = value;
        structuredComments.value.splice(0, structuredComments.value.length);
        structuredComments.value.push(...JSON.parse(value))
        // window.location.reload()
        console.log("新的值：" + structuredComments.value)
    })

    provide('structuredComments-ref', structuredComments)

    onUnmounted(() => {
        emitter.all.clear();
    })

</script>

<style lang="scss" scoped>
.comments {
    margin-top: 20px;
    padding-bottom: 30px;

    .sendComment {
        display: flex;
        align-items: center;
        justify-content: space-between;
        position: relative;
        z-index: 1;
        background-color: #f0f8ff;

        // top:80px;

        .div-img {
            margin-bottom: 20px;
        }

        .commentInput {
            textarea {
                margin-left: 10px;
                width: 55vw;
                height: 50px;
                background-color: #f1f1f1;
                border-radius: 5%;
            }
        }
    }

    .otherComments {
        position: relative;
        z-index: 0;
        // bottom: 95px;
    }
}
</style>