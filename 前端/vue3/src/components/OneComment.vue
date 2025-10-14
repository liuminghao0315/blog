<template>
    <div id="OneCommentExceptHead">
        <!-- 同一级别下处于后面的元素会覆盖前面的元素（实体(child)已经变成最后的元素了，原因是：这里用的v-for每次取对象child，都会覆重新赋值给script的变量child，那么这个v-for结束后，也就只剩下一个child了，解决方法：用动态数组分别存储各个child实体？？或者把child封装成组件，每个组件之间相互独立保存各自child对象实体，不会被覆盖） -->
        <div class="content-part">
            <div class="left">
                <UserAvatar :url="oneCommentObj.avatarUrl" pxNum="60" />
            </div>
            <div class="right">
                <div class="userNameDiv">
                    <span @click="clickUserName">{{ oneCommentObj.userName }}</span>
                </div>
                <div>
                    <span v-if="replyText">{{ preReply }}</span>
                    <span v-if="replyText" class="replyName">{{ oneCommentObj.parentName }}</span>
                    <span v-if="replyText">{{ underReply }}</span>
                    {{ oneCommentObj.content }}
                </div>
                <div class="medium">
                    <div class="left">
                        <div class="time">{{ removeTimesTAndSecond(oneCommentObj.gmtCreated) }}</div>
                        <div class="like" :class="{ monochrome: myIncrement !== 1 }" @click="clickLikeBtn"></div>
                        <div class="likedNumber" v-if="commentLikesDTO">{{ commentLikesDTO.likesNum }}</div>
                        <div class="dislike" :class="{ monochrome: myIncrement !== -1 }" @click="clickDislikeBtn">
                        </div>
                        <div class="reply" @click="clickReplyBtn()" :style="{ color: replyBtnColor }">回复</div>
                    </div>
                    <div class="right">
                        <div class="like"></div>
                        <div class="likedNumber"></div>
                        <div class="dislike"></div>
                        <div class="otherBtn" @click="clickOtherBtn">⋮</div>
                        <div class="otherOptionsDiv" v-if="ifRenderOtherOptionDiv_NonHead">
                            <div v-if="userData.name == oneCommentObj.userName" @click="deleteComment">删除评论</div>
                            <div v-else>举报评论</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="OneCommentExceptHead">
    import { onMounted, ref, computed, inject, watch, onUnmounted, toRefs } from 'vue'
    import axios from 'axios'
    import { type StructuredComments, type CommentDO, type CommentLikesDTO, type CommentLikes } from '@/types'
    import removeTimesTAndSecond from '@/utils/removeTimesTAndSecond'
    import { useUserDataStore } from '@/store/userData'
    import UserAvatar from '@/components/UserAvatar.vue'
    import { useRouter } from 'vue-router'
    import { nanoid } from 'nanoid'
    import { storeToRefs } from 'pinia'
    import emitter from '@/utils/emitter'

    let structuredComments = inject("structuredComments-ref", ref())

    emitter.on('click-non-head-comment-reply-btn', () => {
        emitter.emit('click-head-comment-reply-btn-without-emitter-emit')
        private_ifReplyBtnBlue.value = false;
        ifRenderOtherOptionDiv_Head.value = false;
        ifRenderOtherOptionDiv_NonHead.value = false;
        ifRenderReplyInput_Head.value = false;
        ifRenderReplyInput_NonHead.value = false;
    })

    const { oneCommentObj } = defineProps<{ oneCommentObj: StructuredComments }>()

    let changeReplyCommentDTO = inject('change-reply-commentdto', (obj: CommentDO) => { })

    let replyToWho = inject('change-reply-to-who', ref(''))

    let ifRenderReplyInput_Head = inject('if-render-reply-input-head', ref(false))

    let ifRenderReplyInput_NonHead = inject('if-render-reply-input-non-head', ref(false))

    let textarea = inject('a-group-of-comments:textarea-ref', ref<any>())

    let private_ifReplyBtnBlue = ref(false)

    const { userData } = storeToRefs(useUserDataStore())

    const router = useRouter()

    function clickUserName() {
        router.push("/space/" + oneCommentObj?.userName)
    }

    const replyText = computed(() => {
        if (oneCommentObj?.second || !oneCommentObj?.parentId) {
            return ""
        } else {
            return "true";
        }
    })

    const preReply = "回复 ";
    const underReply = ": "

    let replyBtnColor = ref("#8b8a8a")

    watch([ifRenderReplyInput_NonHead, private_ifReplyBtnBlue], (value) => {
        if (value[0] && value[1]) {
            replyBtnColor.value = "blue"
        } else {
            replyBtnColor.value = "#8b8a8a"
        }
    })

    let stopWatch: any;

    emitter.on('click-non-head-comment-reply-btn-want-to-close', () => {
        ifRenderOtherOptionDiv_NonHead.value = false;
    })

    function clickReplyBtn() {
        replyToWho.value = oneCommentObj.userName as string;

        //下面这一行代码应该是有点bug，如果不注释，那么non-head的评论的回复按钮处于蓝色时候，再点该蓝色按钮，输入框不消失
        // emitter.emit('click-non-head-comment-reply-btn')
        //临时用下面这个代码试试
        emitter.emit('click-non-head-comment-reply-btn-want-to-close')

        if (private_ifReplyBtnBlue.value == true) {
            // 点的是蓝色的
            private_ifReplyBtnBlue.value = false;
            ifRenderReplyInput_NonHead.value = false;
        } else {
            // 点的是灰色的
            private_ifReplyBtnBlue.value = true;
            ifRenderReplyInput_Head.value = false;
            ifRenderReplyInput_NonHead.value = true;

            stopWatch = watch(textarea, () => {
                if (textarea.value) {
                    textarea.value.focus();
                    if (stopWatch) {
                        stopWatch()
                    }
                }
            }, { immediate: true })
        }
        changeReplyCommentDTO({
            authorId: userData.value.id,
            rootId: oneCommentObj.rootId,
            parentId: oneCommentObj.id,
            articleId: oneCommentObj.articleId,
        })
    }

    let ifRenderOtherOptionDiv_NonHead = ref(false)

    function clickOtherBtn() {
        // emitter.emit('click-non-head-comment-reply-btn')
        // emitter.emit('click-head-comment-reply-btn')

        if (ifRenderOtherOptionDiv_NonHead.value == true) {
            //点击的是 展开的，目的是关上
            // console.log("想要关上")
            ifRenderOtherOptionDiv_Head.value = false;
            ifRenderOtherOptionDiv_NonHead.value = false;
        } else {
            //点击的是 关上的，目的是展开、关闭 其他评论的展开
            emitter.emit('click-head-comment-other-btn')
            ifRenderOtherOptionDiv_NonHead.value = true;
        }
        ifRenderReplyInput_NonHead.value = false;
        ifRenderReplyInput_Head.value = false;
    }

    let ifRenderOtherOptionDiv_Head = inject('if-render-other-option-div-head', ref())

    emitter.on('click-non-head-comment-other-btn', () => {
        ifRenderOtherOptionDiv_NonHead.value = false;
        ifRenderOtherOptionDiv_Head.value = false;
    })

    async function deleteComment() {
        // emitter.emit('click-non-head-comment-reply-btn')
        await deleteWhenDeleteNonRootComment();
        const responseMsg = (await axios.get(`http://localhost:8080/db/comment/deleteByParentId?id=${oneCommentObj.id}`)).data
        if (responseMsg > 0) {
            alert("删除成功");
            const newContents = (await axios.get("http://localhost:8080/findStructuredCommentWithUserNameAndAvatarUrl?articleId=" + oneCommentObj.articleId)).data
            structuredComments.value.splice(0, structuredComments.value.length);
            structuredComments.value.push(...newContents)
            // emitter.emit('update-a-group-of-comments', JSON.stringify(newContents))
        } else {
            alert("删除失败")
            window.location.reload()
        }
    }

    onUnmounted(() => {
        emitter.all.clear();
    })

    const commentLikesDTO = ref(oneCommentObj.commentLikesDTO);
    const refs = toRefs(commentLikesDTO.value || { likesNum: 0, myIncrement: 0 });
    let likesNum = refs.likesNum;
    let myIncrement = refs.myIncrement || ref(0);

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
            commentId: oneCommentObj.id,
            userId: userData.value.id,
            incrementNum,
        };
        return (await axios.post("http://localhost:8080/db/commentLikes/add", commentLikes)).data;
    }

    async function deleteWhenCancel() {
        return (await axios.get("http://localhost:8080/db/commentLikes/deleteWhenCancel", {
            params: {
                commentId: oneCommentObj.id,
                userId: userData.value.id,
            }
        })).data;
    }

    async function deleteWhenDeleteNonRootComment() {
        return (await axios.get("http://localhost:8080/db/commentLikes/deleteWhenDeleteNonRootComment", {
            params: {
                commentId: oneCommentObj.id,
            }
        })).data;
    }

    async function update(incrementNum: number) {
        return (await axios.get("http://localhost:8080/db/commentLikes/update", {
            params: {
                commentId: oneCommentObj.id,
                userId: userData.value.id,
                incrementNum,
            }
        })).data
    }
</script>

<style lang="scss" scoped>
.content-part {
    display: flex;
    margin-left: 60px;
    padding-top: 5px;

    .left {

        div.div-img {
            display: inline-block;
            height: 60px;
            width: 60px;
            border-radius: 50%;
            background: url("https://img.ixintu.com/download/jpg/20200901/3e9ce3813b7199ea9588eeb920f41208_512_512.jpg!ys") center center/cover no-repeat;

            img {
                border-radius: 50%;
                height: 60px;
                width: 60px;
            }
        }
    }

    .right {
        margin-left: 10px;

        .userNameDiv {

            span {
                cursor: pointer;
                font-weight: 600;
                font-size: 17px;
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
                width: 230px;
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
                    margin-right: 5px;
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
</style>