<template>
    <div id="AGroupOfComments">
        <div class="headComment">
            <div class="content-part">
                <div class="left">
                    <UserAvatar :url="headComment?.avatarUrl" pxNum="80" />
                </div>
                <div class="right">
                    <div class="userNameDiv">
                        <span @click="clickUserName">{{ headComment?.userName }}</span>
                    </div>
                    <div>{{ headComment?.content }}</div>
                    <div class="medium">
                        <div class="left">
                            <div class="time">{{ removeTimesTAndSecond(headComment?.gmtCreated) }}</div>
                            <div class="like" :class="{ monochrome: myIncrement !== 1 }" @click="clickLikeBtn"></div>
                            <div class="likedNumber" v-if="commentLikesDTO">{{ commentLikesDTO.likesNum }}</div>
                            <div class="dislike" :class="{ monochrome: myIncrement !== -1 }" @click="clickDislikeBtn">
                            </div>
                            <div class="reply" @click="clickReplyBtn()" :style="{ color: replyBtnColor }">回复</div>
                        </div>
                        <div class="right">
                            <div class="otherBtn" @click="clickOtherBtn">⋮</div>
                            <div class="otherOptionsDiv" v-if="ifRenderOtherOptionDiv_Head">
                                <div v-if="userData.name == headComment.userName" @click="deleteComment">删除评论</div>
                                <div v-else>举报评论</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="replyComments">
            <NonHeadComments v-if="headComment.children" :options="<StructuredComments[]>headComment.children" />
        </div>
        <div class="sendReply" v-if="ifRenderReplyInput_Head || ifRenderReplyInput_NonHead">
            <UserAvatar :url="userData.avatarUrl" :pxNum="65" />
            <textarea v-model="replyCommentDTO.content" :placeholder="`回复 @${replyToWho}: `" ref="textarea"></textarea>
            <button @click="sendComment">发布</button>
        </div>
    </div>
</template>

<script setup lang='ts' name="AGroupOfComments">
    import { onMounted, ref, watch, provide, onUnmounted, inject, toRefs } from 'vue'
    import { type StructuredComments, type CommentDO, type CommentLikesDTO, type CommentLikes } from '@/types'
    import removeTimesTAndSecond from '@/utils/removeTimesTAndSecond'
    import { useUserDataStore } from '@/store/userData'
    import UserAvatar from '@/components/UserAvatar.vue'
    import NonHeadComments from './NonHeadComments.vue'
    import { useRouter } from 'vue-router'
    import { storeToRefs } from 'pinia'
    import { nanoid } from 'nanoid'
    import axios from 'axios'
    import emitter from '@/utils/emitter'
    import isEmpty from '@/utils/isEmpty'

    let structuredComments = inject("structuredComments-ref", ref())

    emitter.on('click-head-comment-reply-btn', () => {
        ifRenderOtherOptionDiv_Head.value = false;
        ifRenderReplyInput_NonHead.value = false;
        ifRenderReplyInput_Head.value = false;
        emitter.emit('click-non-head-comment-reply-btn')
    })

    emitter.on('click-non-head-comment-reply-btn-want-to-close', () => {
        ifRenderOtherOptionDiv_Head.value = false;
    })

    emitter.on('click-head-comment-reply-btn-without-emitter-emit', () => {
        ifRenderOtherOptionDiv_Head.value = false;
        ifRenderReplyInput_Head.value = false;
        ifRenderReplyInput_NonHead.value = false;
        // emitter.emit('click-non-head-comment-reply-btn')
    })

    let { headComment, id } = defineProps<{ headComment: StructuredComments, id: string }>()

    const { userData } = storeToRefs(useUserDataStore());

    const router = useRouter()

    let textarea = ref()

    let replyBtnColor = ref("#8b8a8a")
    let ifRenderReplyInput_Head = ref<boolean>(false)
    let ifRenderReplyInput_NonHead = ref<boolean>(false)
    let replyToWho = ref('')
    let replyCommentDTO = ref<CommentDO>({
        articleId: id,
        authorId: userData.value.id,
        parentId: headComment.id,
        rootId: headComment.id,
        content: "",
    })

    function changeReplyCommentDTO(newReplyCommentDTO: CommentDO) {
        replyCommentDTO.value = newReplyCommentDTO;
    }

    provide('change-reply-commentdto', changeReplyCommentDTO);

    provide('change-reply-to-who', replyToWho);

    provide('if-render-reply-input-head', ifRenderReplyInput_Head);

    provide('if-render-reply-input-non-head', ifRenderReplyInput_NonHead);

    provide('a-group-of-comments:textarea-ref', textarea)

    function clickReplyBtn() {
        replyToWho.value = headComment?.userName as string;

        if (ifRenderReplyInput_Head.value == true) {
            // 点的是蓝色的
            // console.log("点的是蓝色的")
            emitter.emit('click-head-comment-reply-btn')
            ifRenderReplyInput_Head.value = false;
        } else {
            // 点的是灰色的
            // console.log("点的是灰色的")
            emitter.emit('click-head-comment-reply-btn')
            ifRenderReplyInput_Head.value = true;
            ifRenderReplyInput_NonHead.value = false;
        }
    }

    let stopWatch: any;

    watch(ifRenderReplyInput_Head, (value) => {
        if (value) {
            replyBtnColor.value = "blue"
            stopWatch = watch(textarea, () => {
                if (textarea.value) {
                    textarea.value.focus();
                    if (stopWatch) {
                        stopWatch()
                    }
                }
            }, { immediate: true })
        } else {
            replyBtnColor.value = "#8b8a8a"
        }
    })

    async function sendComment() {
        if (isEmpty(replyCommentDTO.value.content?.trim())) {
            alert('评论内容不能为空');
            replyCommentDTO.value.content = "";
            return;
        }
        replyCommentDTO.value.id = nanoid();
        const responseMsg = (await axios.post('http://localhost:8080/db/commnet/add', replyCommentDTO.value)).data;
        if (responseMsg == 1) {
            alert("发布成功")
            replyCommentDTO.value.content = "";
            const newContents = (await axios.get("http://localhost:8080/findStructuredCommentWithUserNameAndAvatarUrl?articleId=" + id)).data
            // console.log(newContents)
            structuredComments.value.splice(0, structuredComments.value.length);
            structuredComments.value.push(...newContents)
            // emitter.emit('update-a-group-of-comments', JSON.stringify(newContents))
            // console.log(newContents)
            emitter.emit('click-non-head-comment-reply-btn')

            ifRenderReplyInput_Head.value = false;
        } else {
            alert("发布失败")
        }
    }

    function clickUserName() {
        router.push("/space/" + headComment?.userName)
    }

    let ifRenderOtherOptionDiv_Head = ref(false)

    function clickOtherBtn() {
        // emitter.emit('click-non-head-comment-reply-btn')
        // emitter.emit('click-head-comment-reply-btn')
        if (ifRenderOtherOptionDiv_Head.value == true) {
            //点击的是 展开的，目的是关上
            // console.log("想要关上")
            ifRenderOtherOptionDiv_Head.value = false;
        } else {
            //点击的是 关上的，目的是展开、关闭 其他评论的展开
            emitter.emit('click-head-comment-other-btn')
            ifRenderOtherOptionDiv_Head.value = true;
        }
        ifRenderReplyInput_NonHead.value = false;
        ifRenderReplyInput_Head.value = false;
    }

    provide('if-render-other-option-div-head', ifRenderOtherOptionDiv_Head)

    emitter.on('click-head-comment-other-btn', () => {
        ifRenderOtherOptionDiv_Head.value = false;
        emitter.emit('click-non-head-comment-other-btn')
    })

    async function deleteComment() {
        emitter.emit('click-non-head-comment-reply-btn')
        await deleteWhenDeleteRootComment();
        const responseMsg = (await axios.get(`http://localhost:8080/db/comment/deleteByRootId?id=${headComment.id}`)).data
        // console.log(responseMsg)
        if (responseMsg > 0) {
            alert("删除成功");
            const newContents = (await axios.get("http://localhost:8080/findStructuredCommentWithUserNameAndAvatarUrl?articleId=" + id)).data
            structuredComments.value.splice(0, structuredComments.value.length);
            structuredComments.value.push(...newContents)
            // emitter.emit('update-a-group-of-comments', JSON.stringify(newContents))
            // console.log(newContents)
        } else {
            alert("删除失败")
        }
    }

    onUnmounted(() => {
        emitter.all.clear();
    })

    const commentLikesDTO = ref(headComment.commentLikesDTO);
    const refs = toRefs(commentLikesDTO.value || { likesNum: 0, myIncrement: 0 });
    let likesNum = refs.likesNum || ref(0);
    let myIncrement = refs.myIncrement || ref(0);

    // console.log(likesNum.value)
    // console.log(myIncrement.value)

    let responseMsg_clickLikeOrDislikeBtn: number = 0;

    async function clickLikeBtn() {
        if (responseMsg_clickLikeOrDislikeBtn === 1) {
            return;
        }
        responseMsg_clickLikeOrDislikeBtn = 1;
        if (myIncrement.value === -1) {
            responseMsg_clickLikeOrDislikeBtn = await update(1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 1;
                // @ts-ignore
                likesNum.value += 2;
                // alert("点赞成功")
            } else {
                // alert("点赞失败")
            }
        } else if (myIncrement.value === 0) {
            responseMsg_clickLikeOrDislikeBtn = await add(1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 1;
                // @ts-ignore
                likesNum.value += 1;
                // alert("点赞成功")
            } else {
                // alert("点赞失败")
            }
        } else {
            responseMsg_clickLikeOrDislikeBtn = await deleteWhenCancel();
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 0;
                // @ts-ignore
                likesNum.value -= 1;
                // alert("取消点赞成功")
            } else {
                // alert("取消点赞失败")
            }
        }
        responseMsg_clickLikeOrDislikeBtn = 0
    }

    async function clickDislikeBtn() {
        if (responseMsg_clickLikeOrDislikeBtn === 1) {
            return;
        }
        responseMsg_clickLikeOrDislikeBtn = 1;
        if (myIncrement.value === -1) {
            responseMsg_clickLikeOrDislikeBtn = await deleteWhenCancel();
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 0;
                // @ts-ignore
                likesNum.value += 1;
                // alert("取消倒赞成功");
            } else {
                // alert("取消倒赞失败");
            }
        } else if (myIncrement.value === 0) {
            responseMsg_clickLikeOrDislikeBtn = await add(-1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = -1;
                // @ts-ignore
                likesNum.value -= 1;
                // alert("倒赞成功");
            } else {
                // alert("倒赞失败")
            }
        } else {
            responseMsg_clickLikeOrDislikeBtn = await update(-1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = -1;
                // @ts-ignore
                likesNum.value -= 2;
                // alert("倒赞成功")
            } else {
                // alert("倒赞失败")
            }
        }
        responseMsg_clickLikeOrDislikeBtn = 0
    }

    //以下是后端对应接口(API)
    async function add(incrementNum: number) {
        const commentLikes: CommentLikes = {
            id: nanoid(),
            commentId: headComment.id,
            userId: userData.value.id,
            incrementNum,
        };
        return (await axios.post("http://localhost:8080/db/commentLikes/add", commentLikes)).data;
    }

    async function deleteWhenCancel() {
        return (await axios.get("http://localhost:8080/db/commentLikes/deleteWhenCancel", {
            params: {
                commentId: headComment.id,
                userId: userData.value.id,
            }
        })).data;
    }

    async function deleteWhenDeleteRootComment() {
        return (await axios.get("http://localhost:8080/db/commentLikes/deleteWhenDeleteRootComment", {
            params: {
                commentId: headComment.id,
            }
        })).data;
    }

    async function update(incrementNum: number) {
        return (await axios.get("http://localhost:8080/db/commentLikes/update", {
            params: {
                commentId: headComment.id,
                userId: userData.value.id,
                incrementNum,
            }
        })).data
    }
</script>

<style lang="scss" scoped>
#AGroupOfComments {

    .headComment {

        .content-part {
            margin-top: 3px;
            padding-top: 10px;
            border-top: 2px solid #ccc;
            display: flex;

            .right {
                margin-left: 10px;

                .userNameDiv {

                    span {
                        font-weight: 700;
                        font-size: 20px;
                        cursor: pointer;
                    }
                }

                .replyName {
                    color: #575b68
                }

                .medium {
                    display: flex;
                    justify-content: space-between;
                    width: 55vw;

                    .left {
                        display: flex;
                        align-items: center;
                        justify-content: space-between;
                        width:230px;
                        color: #8b8a8a;
                        font-size: 13px;
                        margin-top: 3px;

                        .like {
                            height: 14px;
                            width: 14px;
                            background: url("../assets/like.png") center center/cover no-repeat;
                            cursor: pointer;
                        }

                        .likedNumber {
                            text-align: center;
                            height: 14px;
                            width: 14px;
                            line-height: 14px;
                            font-size: 13px;
                            margin-right:5px;
                            cursor: default;
                        }

                        .dislike {
                            height: 14px;
                            width: 14px;
                            background: url("../assets/dislike.png") center center/cover no-repeat;
                            cursor: pointer;
                        }

                        .monochrome {
                            filter: saturate(10%);
                        }

                        .reply {
                            margin-left: 5px;
                            cursor: pointer;
                            user-select: none;
                        }
                    }

                    .right {
                        position: relative;

                        .otherBtn {
                            cursor: pointer;
                            width: 20px;
                            height: 20px;
                            // margin-top: -20px;
                            line-height: 20px;
                            text-align: center;
                            user-select: none;
                        }

                        .otherOptionsDiv {
                            background-color: #fff;
                            position: absolute;
                            display: flex;
                            flex-direction: column;
                            width: 75px;
                            right: 20px;
                            top: 5px;
                            align-items: center;
                            box-sizing: content-box;
                            padding: 10px;
                            border-radius: 20px;
                            user-select: none;

                            div {
                                font-size: 17px;
                                border-top: 1px solid rgb(152, 147, 147);
                                cursor: pointer;

                                &:first-child {
                                    border: none;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    .sendReply {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-top: 15px;

        textarea {
            margin-left: 10px;
            width: 55vw;
            height: 50px;
            background-color: #f1f1f1;
            border-radius: 5%;
            margin: 0 10px;
        }
    }
}
</style>