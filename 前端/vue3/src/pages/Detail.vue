<template>
    <div id="Detail">
        <div class="main-part">
            <div class="top">
                <h2>{{ article.title }}</h2>
                <span @click="clickAuthorName" class="authorName">{{ article.authorName }}</span>
                <br>
                <span>{{ article.gmtModified.replace(/T/g, ' ').replace(/.[\d]{3}Z/, ' ') }}</span>
                <template v-if="article.authorName == userData.name">
                    <button class="changeBtn" @click="changeArticle">修改你的文章</button>
                    <button class="deleteBtn" @click="deleteArticle">删除你的文章</button>
                </template>
                <template v-if="article.authorName !== userData.name && userData.authority == 'ADMINISTRATOR'">
                    <button class="deleteBtn" @click="deleteArticle">删除这篇文章</button>
                </template>
            </div>
            <hr>
            <div class="medium">
                <div>{{ article.summary }}</div>
                <hr>
                <div>
                    <MdPreview :modelValue="article.content" />
                </div>
            </div>
            <div class="likesSection" v-if="favoriteDTO">
                <div class="left">
                    <div class="favorite" :class="{ monochrome: ifUserFavorite !== true }" @click="clickFavoriteBtn">
                    </div>
                    <div class="favoritedNumber">{{ favoriteDTO.favoriteCount }}</div>

                </div>
                <div class="right" v-if="articleLikesDTO">
                    <div class="like" :class="{ monochrome: myIncrement !== 1 }" @click="clickLikeBtn"></div>
                    <div class="likedNumber">{{ likesNum }}</div>
                    <div class="dislike" :class="{ monochrome: myIncrement !== -1 }" @click="clickDislikeBtn"></div>
                    <!-- <div class="dislike monochrome"></div> -->
                </div>
            </div>
            <div class="selectLastOrNext">
                <div class="last" v-if="lastAndNext.last">
                    <span>上一篇：</span>
                    <RouterLink :to="articleUrlComputed('last')">{{ lastAndNext.last.title }}</RouterLink>
                    <span>Written By
                        <span class="authorName" @click="clickLastAuthorName">{{ lastAndNext.last.authorName }}</span>
                    </span>
                </div>
                <div class="next" v-if="lastAndNext.next">
                    <span>下一篇：</span>
                    <RouterLink :to="articleUrlComputed('next')">{{ lastAndNext.next.title }}</RouterLink>
                    <span>Written By
                        <span class="authorName" @click="clickNextAuthorName">{{ lastAndNext.next.authorName }}</span>
                    </span>
                </div>
            </div>
            <div class="commentsPart">
                <CommentsPart :id="id"></CommentsPart>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Detail">
    import { ref, onMounted, onBeforeMount, toRefs, onUpdated, watch, computed, inject } from 'vue'
    import { type BlogWithUserName, type StructuredComments, type CommentDO, type ArticleLikesDTO, type ArticleLikes, type FavoriteDTO } from "@/types"
    import axios from 'axios'
    import { storeToRefs } from 'pinia'
    import { MdPreview } from 'md-editor-v3'
    import { useUserDataStore } from "@/store/userData"
    import { useRoute, useRouter, RouterLink } from 'vue-router'
    import CommentsPart from '@/components/CommentsPart.vue'
    import { nanoid } from 'nanoid'
    import useFavoriteApi from '@/hooks/useFavoriteApi'

    const baseUrl = inject('baseUrl')

    const commentInput = ref("")
    const router = useRouter();
    const route = useRoute()
    const { userData, homeSortJS, userSpaceSortJS } = storeToRefs(useUserDataStore())

    let { id } = defineProps<{ id: string }>()
    // @ts-ignore
    let article = ref<BlogWithUserName>({
        id: '',
        title: '',
        authorName: '',
        content: '',
        summary: '',
        gmtCreated: '',
        gmtModified: '',
    })

    let articleLikesDTO = ref<ArticleLikesDTO>({
        likesNum: 0,
        myIncrement: 0,
    });

    let likesNum = ref();

    let myIncrement = ref();

    let lastAndNext = ref({
        last: {
            title: '',
            authorName: '',
            id: '',
        },
        next: {
            title: '',
            authorName: '',
            id: '',
        },
    })

    let ifUseuserSpaceSortJS = ref<boolean>(route.query.myBlogUserId != undefined || route.query.favoriteUserId != undefined)
    // console.log(ifUseuserSpaceSortJS.value)

    const articleUrlComputed = computed(() => (option: string) => {
        let baseStr;
        if (option == 'last') {
            baseStr = `detail?id=${lastAndNext.value.last.id}`;
        } else {
            baseStr = `detail?id=${lastAndNext.value.next.id}`;
        }
        if (route.query.myBlogUserId) {
            baseStr += `&myBlogUserId=${route.query.myBlogUserId}`
        } else if (route.query.favoriteUserId) {
            baseStr += `&favoriteUserId=${route.query.favoriteUserId}`
        } else {
            return baseStr
        }
        if (route.query.marchText) {
            baseStr += "&marchText=" + route.query.marchText
        }
        return baseStr;
    })

    let importantMarchText = ref(route.query.marchText)
    let useImportantMarchText = ref(false)

    if (importantMarchText.value != undefined) {
        useImportantMarchText.value = true
    }

    async function todo() {
        article.value = (await axios.get(baseUrl+"/bwun/findbyid?id=" + id)).data;
        articleLikesDTO.value = (await axios.get(`${baseUrl}/db/articleLikes/getDTO?articleId=${id}&userId=${userData.value.id}`)).data
        let refs1 = toRefs(articleLikesDTO.value);
        likesNum = refs1.likesNum;
        myIncrement = refs1.myIncrement;
        const params = {
            sortFlag: ifUseuserSpaceSortJS.value ? userSpaceSortJS.value.sortFlag : homeSortJS.value.sortFlag,
            marchText: ifUseuserSpaceSortJS.value ? importantMarchText.value : homeSortJS.value.marchText,
            blogId: id,
            myBlogUserId: route.query.myBlogUserId,
            favoriteUserId: route.query.favoriteUserId
        }
        lastAndNext.value = ifUseuserSpaceSortJS.value
            ? (await axios.get(`${baseUrl}/bwun/findLastAndNextRelyOn${userSpaceSortJS.value.sortName}ById`, { params })).data
            : (await axios.get(`${baseUrl}/bwun/findLastAndNextRelyOn${homeSortJS.value.sortName}ById`, { params })).data
        favoriteDTO.value = await getDTOFn(userData.value.id as string, id);
        const refs2 = toRefs(favoriteDTO.value)
        favoriteCount = refs2.favoriteCount;
        ifUserFavorite = refs2.ifUserFavorite;
    }

    onMounted(async () => {
        await todo();
    })


    watch(() => route.query.id, async () => {
        await todo();
    })


    function changeArticle() {
        router.push({
            path: "/change",
            query: {
                id
            }
        })
    }

    function clickAuthorName() {
        router.push("/space/" + article.value.authorName)
    }

    function clickLastAuthorName() {
        router.push("/space/" + lastAndNext.value.last.authorName)
    }

    function clickNextAuthorName() {
        router.push("/space/" + lastAndNext.value.next.authorName)
    }

    async function deleteArticle() {
        const option: boolean = confirm("确认删除？不可恢复！");
        if (!option) {
            return;
        }
        await axios.get(baseUrl+"/deleteBlog/blogsImages?blogId=" + article.value.id)

        await axios.get(baseUrl+"/db/commentLikes/deleteWhenDeleteArticle?articleId=" + article.value.id)
        await axios.get(baseUrl+"/db/comment/deleteByArticleId?id=" + article.value.id)
        await axios.get(baseUrl+"/db/blog/delete?id=" + id);
        await deleteWhenDeleteArticle();
        alert("删除成功");
        router.push("/home")
    }

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
                likesNum.value += 2;
                // alert("点赞成功")
            } else {
                // alert("点赞失败")
            }
        } else if (myIncrement.value === 0) {
            responseMsg_clickLikeOrDislikeBtn = await add(1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 1;
                likesNum.value += 1;
                // alert("点赞成功")
            } else {
                // alert("点赞失败")
            }
        } else {
            responseMsg_clickLikeOrDislikeBtn = await deleteWhenCancel();
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = 0;
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
                likesNum.value += 1;
                // alert("取消倒赞成功");
            } else {
                // alert("取消倒赞失败");
            }
        } else if (myIncrement.value === 0) {
            responseMsg_clickLikeOrDislikeBtn = await add(-1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = -1;
                likesNum.value -= 1;
                // alert("倒赞成功");
            } else {
                // alert("倒赞失败")
            }
        } else {
            responseMsg_clickLikeOrDislikeBtn = await update(-1);
            if (responseMsg_clickLikeOrDislikeBtn > 0) {
                myIncrement.value = -1;
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
        const articleLikes: ArticleLikes = {
            id: nanoid(),
            articleId: id,
            userId: userData.value.id,
            incrementNum,
        };
        return (await axios.post(baseUrl+"/db/articleLikes/add", articleLikes)).data;
    }

    async function deleteWhenCancel() {
        return (await axios.get(baseUrl+"/db/articleLikes/deleteWhenCancel", {
            params: {
                articleId: id,
                userId: userData.value.id,
            }
        })).data;
    }

    async function deleteWhenDeleteArticle() {
        return (await axios.get(baseUrl+"/db/articleLikes/deleteWhenDeleteArticle", {
            params: {
                articleId: id,
            }
        })).data;
    }

    async function update(incrementNum: number) {
        return (await axios.get(baseUrl+"/db/articleLikes/update", {
            params: {
                articleId: id,
                userId: userData.value.id,
                incrementNum,
            }
        })).data
    }

    // 以下是收藏功能
    const { add: addFn, delete_: deleteFn, deleteByArticleId: deleteByArticleIdFn, getDTO: getDTOFn } = useFavoriteApi()

    let favoriteDTO = ref<FavoriteDTO>({
        favoriteCount: 0,
        ifUserFavorite: false,
    });

    let favoriteCount = ref<number>(0)

    let ifUserFavorite = ref<boolean>(false)

    // onMounted(async () => {
    //     favoriteDTO.value = await getDTOFn(userData.value.id as string, id);
    //     const refs = toRefs(favoriteDTO.value)
    //     favoriteCount = refs.favoriteCount;
    //     ifUserFavorite = refs.ifUserFavorite;
    //     // console.log(favoriteDTO.value)
    // })

    let responseMsg_clickFavoriteBtn: number = 0;

    async function clickFavoriteBtn() {
        if (responseMsg_clickFavoriteBtn === 1) {
            return;
        }
        responseMsg_clickFavoriteBtn = 1;
        if (favoriteDTO.value.ifUserFavorite === false) {
            responseMsg_clickFavoriteBtn = await addFn(userData.value.id as string, id);
            if (responseMsg_clickFavoriteBtn > 0) {
                // favoriteCount.value += 1;
                favoriteCount.value += 1;
                ifUserFavorite.value = true;
                // alert("收藏成功")
            } else {
                // alert("收藏失败")
            }
        } else if (favoriteDTO.value.ifUserFavorite === true) {
            responseMsg_clickFavoriteBtn = await deleteFn(userData.value.id as string, id);
            if (responseMsg_clickFavoriteBtn > 0) {
                // favoriteCount.value -= 1;
                favoriteCount.value -= 1;
                ifUserFavorite.value = false;
                // alert("取消收藏成功")
            } else {
                // alert("取消收藏失败")
            }
        }
        responseMsg_clickFavoriteBtn = 0
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

    .top {
        position: relative;
        z-index: 1;

        .authorName {
            cursor: pointer;
        }

        .changeBtn {
            position: absolute;
            right: 0;
            margin-top: -30px
        }

        .deleteBtn {
            position: absolute;
            right: 0;
        }
    }

    .medium {
        position: relative;
        z-index: 1;
        margin-bottom: 80px;
    }

    .likesSection {
        height: 50px;
        display: flex;
        justify-content: space-between;

        .left {
            height: 50px;
            width: 100px;
            display: flex;
            margin-left: 20px;
            justify-content: space-between;
            align-items: center;

            .favorite {
                height: 33px;
                width: 33px;
                background: url("../assets/favorite.png") center center/cover no-repeat;
                cursor: pointer;
            }

            .favoritedNumber {
                text-align: center;
                height: 30px;
                width: 30px;
                line-height: 30px;
                font-size: 20px;
            }


            .monochrome {
                filter: saturate(10%);
            }
        }

        .right {
            height: 50px;
            width: 200px;
            display: flex;
            justify-content: space-around;
            align-items: center;

            .like {
                height: 30px;
                width: 30px;
                background: url("../assets/like.png") center center/cover no-repeat;
                cursor: pointer;
            }

            .likedNumber {
                text-align: center;
                height: 30px;
                width: 30px;
                line-height: 30px;
                font-size: 20px;
            }

            .dislike {
                height: 30px;
                width: 30px;
                background: url("../assets/dislike.png") center center/cover no-repeat;
                cursor: pointer;
            }

            .monochrome {
                filter: saturate(10%);
            }
        }

        // background-color: rgb(199, 234, 160);
    }

    .selectLastOrNext {
        margin: 10px 0;

        .last,
        .next {
            color: #3e3b3b;
            margin-top: 5px;

            &:first-child {
                margin-top: 0;
            }

            span {
                margin-left: 20px;

                &:first-child {
                    margin-left: 5px;
                }

                span.authorName {
                    margin-left: 10px;
                    color: #232222;
                    font-weight: 550;
                    cursor: pointer;
                }
            }
        }
    }

    .commentsPart {
        // display: none;
        z-index: 0;
    }
}
</style>