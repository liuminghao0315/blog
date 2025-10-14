<template>
    <div id="ChangeArticle">
        <div class="main-part">
            <div class="top">
                标题：<input type="text" v-model="title">
            </div>
            <hr>
            <div class="top-medium">
                摘要：<input type="text" v-model="summary">
            </div>
            <hr>
            <div class="medium">
                <div class="left">
                    <div class="text">写作区</div>
                    <div class="main">
                        <textarea v-model="content"></textarea>
                    </div>
                </div>
                <div class="right">
                    <div class="text">预览区</div>
                    <div class="main">
                        <div class="wordWrap">
                            <MdPreview :modelValue="content" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="bottom">
                <button @click="submit">提交</button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Write">
    import { MdPreview } from 'md-editor-v3'
    import { ref, onMounted } from 'vue'
    import axios from 'axios'
    import isEmpty from '@/utils/isEmpty'
    import { useUserDataStore } from "@/store/userData"
    import { useRouter } from 'vue-router'

    const router = useRouter()
    const { id } = defineProps(['id'])
    let title = ref<string>();
    let summary = ref<string>();
    let content = ref<string>();
    let authorId: string;

    onMounted(async () => {
        const originArticle = (await axios.get("http://localhost:8080/db/blog/findbyid?id=" + id)).data;
        console.log(originArticle)
        authorId = originArticle.authorId;
        title.value = originArticle.title;
        summary.value = originArticle.summary;
        content.value = originArticle.content;
    })

    let submitLock = ref<number>(0);

    async function submit() {
        if (submitLock.value === 1) {
            return;
        }
        submitLock.value = 1;
        if (isEmpty(title.value)) {
            alert("请输入 标题")
            return;
        }
        if (isEmpty(summary.value)) {
            alert("请输入 摘要")
            return;
        }
        if (isEmpty(content.value)) {
            alert("请输入 内容")
            return;
        }
        try {
            const responseMsg = (await axios.post("http://localhost:8080/db/blog/update", {
                id,
                title: title.value,
                authorId: authorId,
                content: content.value,
                summary: summary.value
            })).data
            if (responseMsg > 0) {
                alert("发送成功")
            } else {
                alert("发送失败")
            }
        } catch {
            alert("发送失败")
            submitLock.value = 0
            return;
        }
        submitLock.value = 0
        router.push("/detail?id=" + id)
    }
</script>

<style lang="scss" scoped>
.wordWrap {
    word-break: break-all;
    word-wrap: break-word;
    white-space: normal;
}

.main-part {
    width: 95vw;
    margin: 0 auto;
    background-color: aliceblue;
    display: flex;
    flex-direction: column;
    padding: 10px;

    .top {
        input {
            width: 80vw
        }
    }

    .top-medium {

        input {
            width: 80vw
        }
    }

    .medium {
        display: flex;

        .left {
            flex: 1;
            align-items: stretch;

            textarea {
                width: 45vw;
                min-height: 60vh;
                font-size: 17.5px;
            }
        }

        .right {
            flex: 1;

            .main {
                div {
                    width: 45vw;
                    min-height: 60vh;
                    background-color: #f9f5f5;
                    border: 1px #b3e2a1 solid;
                }
            }
        }
    }
}
</style>