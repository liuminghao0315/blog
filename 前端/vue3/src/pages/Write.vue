<template>
    <div id="Write">
        <div class="main-part">
            <main>
                <div class="top">
                    标题：<input type="text" v-model="title">
                </div>
                <br>
                <div class="top-medium">
                    摘要：<input type="text" v-model="summary">
                </div>
                <br>
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
            </main>
            <aside>
                <div class="arrow">
                </div>
                <!-- <div class="vertical-bar"> -->

                <!-- </div> -->
                <div class="right-aside">
                    <div class="uploadImg">
                        点我上传
                        <br>
                        或
                        <br>
                        拖拽于此
                        <input type="file" id="fileInput" multiple accept="image/*" @change="uploadFiles" />
                    </div>
                    <div class="imgs-list">
                        <div v-for="(oneLine, index) in imgsList" :key="oneLine.id" class="oneLine">
                            <div class="index">{{ index + 1 }}.</div>
                            <ImagePreview :url="oneLine.fileName" :id="oneLine.id" @cancelImg="cancelImg($event)">
                            </ImagePreview>
                            <div class="btn" @click="copyMarkdown(oneLine.fileName)">复制Markdown</div>
                        </div>
                    </div>
                </div>
            </aside>
        </div>
    </div>
</template>

<script setup lang="ts" name="Write">
    import { MdPreview } from 'md-editor-v3'
    import { ref, inject, onUnmounted, onBeforeUnmount } from 'vue'
    import axios from 'axios'
    import { nanoid } from 'nanoid'
    import isEmpty from '@/utils/isEmpty'
    import { useUserDataStore } from "@/store/userData"
    import { useRouter } from 'vue-router'
    import { type BlogsImages } from '@/types'
    import ImagePreview from "@/components/ImagePreview.vue"

    const baseUrl = inject('baseUrl')

    const router = useRouter()
    let title = ref<string>();
    let summary = ref<string>();
    let content = ref<string>();
    const id = nanoid();

    let submitLock = ref<number>(0);

    let isSaveBlogWhenExit = false;

    const BASE_PATH = "D:\\360MoveData\\Users\\23793\\Desktop\\博客网站\\七代版本-加入分页系统\\后端\\blog_project\\src\\main\\resources\\images\\"

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
            const responseMsg = (await axios.post(baseUrl + "/db/blog/add", {
                id,
                title: title.value,
                authorName: useUserDataStore().userData.name,
                content: content.value,
                summary: summary.value
            })).data
            await axios.post(baseUrl + "/db/articleLikes/add", {
                id: nanoid(),
                articleId: id,
                userId: "system",
                incrementNum: 0,
            })
            if (responseMsg > 0) {
                isSaveBlogWhenExit = true;
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

    //以下是图片上传功能
    let imgsList = ref<BlogsImages[]>([
        {
            authorId
                :
                "Hsl5HBIb2n6YcDld9luNr",
            blogId
                :
                "5RzBYeXdtuEDeND5IBonN",
            fileName
                :
                "https://fastly.picsum.photos/id/674/300/200.jpg?hmac=yH5rkbzvgHDnf-1vkfPNZkummbCL2ClEPJq1bw5uCYo",
            gmtCreated
                :
                "2025-10-18T14:17:52.719203",
            gmtModified
                :
                "2025-10-18T14:17:52.719203",
            id
                :
                "10EedC_K9pf14wshTrv6f",
            status
                :
                "SIDEBAR_ONLY"
        },
        {
            authorId
                :
                "Hsl5HBIb2n6YcDld9luNr",
            blogId
                :
                "5RzBYeXdtuEDeND5IBonN",
            fileName
                :
                "https://fastly.picsum.photos/id/607/300/200.jpg?hmac=0JXKdW0INIcSZwTBLUgUvENj_6JgRGLXs2MSJoo-ucI",
            gmtCreated
                :
                "2025-10-18T14:17:52.719203",
            gmtModified
                :
                "2025-10-18T14:17:52.719203",
            id
                :
                "20EedC_s9pf14wshTwv6f",
            status
                :
                "SIDEBAR_ONLY"
        }
    ])

    imgsList = ref<BlogsImages[]>([])

    async function uploadFiles(event: any) {
        const files = event.target.files;
        for (const file of files) {
            const formData = new FormData();
            formData.append("file", file);
            const responseMsg = (await axios.post(`${baseUrl}/upload/blogsImages?authorId=${useUserDataStore().userData.id}&blogId=${id}&id=${nanoid()}`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                },
            })).data
            // console.log(responseMsg)
            imgsList.value?.push(responseMsg.data)
            // console.log(imgsList.value)
        }
    }

    function cancelImg(id: string) {
        // console.log("ok",id)
        imgsList.value = imgsList.value.filter((theImg) => {
            if (theImg.id != id) {
                // console.log("true!")
                return true;
            } else {
                axios.get(`${baseUrl}/deleteOne/blogsImages?fileName=${theImg.fileName}`);
                // console.log("false!")
                return false;
            }
        })
        // console.log("imgsList.value = " + JSON.stringify(imgsList.value))
    }

    onBeforeUnmount(async () => {
        console.log("最外层")
        if (isSaveBlogWhenExit) {
            console.log("内层——1")
            let fileNames_save: string | string[] = [];
            let fileNames_delete: string | string[] = [];
            for (let file of imgsList.value) {
                if (content.value?.indexOf(`](${file.fileName})`) != -1) {
                    fileNames_save.push(file.fileName);
                } else {
                    fileNames_delete.push(file.fileName)
                }
            }
            console.log("fileNames_save = " + fileNames_save)
            console.log("fileNames_delete = " + fileNames_delete)
            if (fileNames_save.length > 0 && fileNames_delete.length > 0) {
                await Promise.all([
                    axios.get(`${baseUrl}/updateSeveral/blogsImages?fileNames=${fileNames_save.join(",")}`),
                    axios.get(`${baseUrl}/deleteSeveral/blogsImages?fileNames=${fileNames_delete.join(",")}`)
                ])
            } else if (fileNames_save.length > 0) {
                await axios.get(`${baseUrl}/updateSeveral/blogsImages?fileNames=${fileNames_save.join(",")}`);
            } else if (fileNames_delete.length > 0) {
                await axios.get(`${baseUrl}/deleteSeveral/blogsImages?fileNames=${fileNames_delete.join(",")}`)
            }
        } else {
            console.log("内层——2")
            let fileNames: string | string[] = []
            for (let file of imgsList.value) {
                fileNames.push(file.fileName);
            }
            fileNames = fileNames.join(",")
            console.log("fileNames = " + fileNames)
            await axios.get(`${baseUrl}/deleteSeveral/blogsImages?fileNames=${fileNames}`)
        }
    })

    async function copyMarkdown(path: string) {
        await navigator.clipboard.writeText(`![](${path})`);
    }
</script>

<style lang="scss" scoped>
.wordWrap {
    word-break: break-all;
    word-wrap: break-word;
    white-space: normal;
    // width: 100%;

    @for $i from 0 through 100 {
        #md-editor-v-#{$i} {
            width: 100%;
        }
    }
}

.main-part {
    width: 95vw;
    margin: 0 auto;
    background-color: aliceblue;
    display: flex;

    main {
        flex: 1;
        display: flex;
        flex-direction: column;
        padding: 10px;

        .top {
            input {
                width: 90%
            }
        }

        .top-medium {

            input {
                width: 90%
            }
        }

        .medium {
            display: flex;

            .left {
                flex: 1;

                textarea {
                    width: 90%;
                    min-height: 60vh;
                }
            }

            .right {
                flex: 1;

                .main {
                    div {
                        width: 90%;
                        min-height: 60vh;
                        background-color: #f9f5f5;
                        border: 1px #b3e2a1 solid;
                    }
                }
            }
        }
    }

    aside {
        margin-left: -50px;
        display: flex;
        height: auto;

        .arrow {
            align-self: center;
            width: 15px;
            height: 60px;
            background: url("../assets/上一页.png") center center/ 100% no-repeat;

            &:hover {
                background-image: url("../assets/上一页(mouseOn).png");
            }

            // background-color: orange;

        }

        .vertical-bar {
            align-self: center;
            width: 10px;
            border-bottom-left-radius: 100%;
            border-top-left-radius: 100%;
            height: 500px;
            border: 2px solid rgba(145, 141, 141, 0.737);
        }

        .right-aside {
            background-color: rgb(229, 249, 208);
            width: 200px;
            display: flex;
            flex-direction: column;
            align-items: center;

            .uploadImg {
                height: 80px;
                width: 80px;
                // background-color: red;
                text-align: center;
                font-weight: 500;
                border: 1px solid black;
                position: relative;
                cursor: pointer;
                z-index: 0;

                input {
                    height: 80px;
                    width: 80px;
                    opacity: 0;
                    position: absolute;
                    top: 0;
                    left: 0;
                    z-index: 1;
                    cursor: pointer;
                    // background-color: red;
                }
            }

            .imgs-list {
                margin-top: 15px;

                .oneLine {
                    margin-bottom: 10px;
                    display: flex;

                    .index {
                        line-height: 150px;
                        font-weight: 600;
                    }

                    .btn {
                        writing-mode: vertical-rl;
                        text-align: center;
                        font-size: 12px;
                        border: 1px solid black;
                        user-select: none;
                        cursor: pointer;
                    }
                }
            }
        }
    }
}
</style>