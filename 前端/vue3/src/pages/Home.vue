<template>
    <div id="Home">
        <div class="main-part">
            <div class="filterAndSort">
                <div class="filter">
                    <span>关键词检索：</span>
                    <input type="text" placeholder="可输入检索词" v-model="marchText">
                    <button @click="marchText = ''">清空</button>
                </div>
                <div class="sort">
                    <span>选择排序依据：</span>
                    <select v-model="sortName">
                        <option value="Like">点赞量</option>
                        <option value="Favorite">收藏量</option>
                        <option value="Comment">评论数</option>
                        <option value="Date">发布时间</option>
                    </select>
                    <div :class="sortFlag == 'up' ? 'shadow' : ''" @click="changeToSortUp">
                        <img src="../assets/升序.png">
                    </div>
                    <div :class="sortFlag == 'down' ? 'shadow' : ''" @click="changeToSortDown">
                        <img src="../assets/降序.png">
                    </div>
                </div>
            </div>
            <div class="block" v-for="article in articles" :key="article.id">
                <OneBlog :article="article" :march-text="marchText.trim()" :sort-flag="sortFlag" :sort-name="sortName"
                    @set-sort-flag="sortFlag = $event" @set-sort-name="sortName = $event" />
            </div>
            <div class="pagingSelect" v-if="articles[0]">
                <div class="lastArrow" @click="clickLastArrow" v-show="pageNum > 1"></div>
                <div class="last4" @click="clickPageOne" v-if="pageNum >= 5">1</div>
                <div class="last3" @click="clickPageTag(-3)" v-if="pageNum >= 4">{{ pageNum >= 6 ? '...' : pageNum - 3
                }}
                </div>
                <div class="last2" @click="clickPageTag(-2)" v-if="pageNum >= 3">{{ pageNum - 2 }}</div>
                <div class="last1" @click="clickPageTag(-1)" v-if="pageNum >= 2">{{ pageNum - 1 }}</div>
                <div class="thisPageNum">{{ pageNum }}</div>
                <div class="next1" @click="clickPageTag(1)" v-if="totalPage - pageNum >= 1">{{ pageNum + 1 }}</div>
                <div class="next2" @click="clickPageTag(2)" v-if="totalPage - pageNum >= 2">{{ pageNum + 2 }}</div>
                <div class="next3" @click="clickPageTag(3)" v-if="totalPage - pageNum >= 3 && pageNum <= 4">{{ pageNum +
                    3 }}</div>
                <div class="nextArrow" @click="clickNextArrow" v-show="pageNum < totalPage"></div>
                <div class="pagingSetting" @click="clickPagingSetting" ref="pagingSetting"
                    @mouseenter="handleMouseEnter" @mouseleave="handleMouseLeave">
                    <div class="setting" v-if="ifRenderSetting" @pointerdown.stop @mousedown.stop @click.stop
                        @wheel.stop>
                        <div class="option">跳转至第<input type="text" v-model="directNum">
                            <div><button class="topArrow" @click="changeDirectNum(1)"></button><button
                                    class="bottomArrow" @click="changeDirectNum(-1)"></button></div>
                            页<button class="ok" @click="directOK">确认</button>
                        </div>
                        <div class="option">每页展示<input type="text" v-model="displayNum">
                            <div><button class="topArrow" @click="changeDisplayNum(1)"></button><button
                                    class="bottomArrow" @click="changeDisplayNum(-1)"></button></div>
                            条<button class="ok" @click="displayOK">确认</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="non" v-if="!articles[0]">
                暂时没有~~~
            </div>
            <div class="nonElse" v-if="articles[0]">
                我们是有底线的~~~
            </div>
        </div>
    </div>
</template>

<script setup lang="ts" name="Home">
    import { type Blog, type User, type BlogWithUserName, type Paging } from '@/types'
    import { ref, onMounted, computed, watch, toRefs, inject } from 'vue';
    import axios from 'axios'
    import { useRefreshCountStore } from '@/store/refreshCount'
    import { useUserDataStore } from '@/store/userData';
    import { storeToRefs } from 'pinia';
    import useTimeOutRef from '@/hooks/useTimeOutRef'
    import OneBlog from '@/components/OneBlog.vue';
    import usePositiveIntegerRef from '@/hooks/usePositiveIntegerRef'

    const baseUrl = inject('baseUrl')

    let articles = ref<Array<BlogWithUserName>>([])
    const refreshCountStore = useRefreshCountStore();
    let { homeSortJS, homePagingJS } = storeToRefs(useUserDataStore())

    onMounted(async () => {
        if (refreshCountStore.login_to_home_count == 0) {
            refreshCountStore.login_to_home_count++
        }
        articles.value = await getBlogs(marchText.value)
    })


    //以下是分页对象、分页功能
    let paging = ref<Paging>({
        pageNum: homePagingJS.value.pageNum,
        pageSize: homePagingJS.value.pageSize,
        totalPage: 0,
        totalCount: 0,
        data: []
    });

    let { pageNum, pageSize, totalPage, totalCount, data } = toRefs(paging.value)

    async function clickLastArrow() {
        pageNum.value--;
        articles.value = await getBlogs(marchText.value);
    }

    async function clickNextArrow() {
        pageNum.value++;
        articles.value = await getBlogs(marchText.value);
    }

    async function clickPageTag(num: number) {
        pageNum.value += num;
        articles.value = await getBlogs(marchText.value);
    }

    async function clickPageOne() {
        pageNum.value = 1;
        articles.value = await getBlogs(marchText.value);
    }

    //现在下面是分页设置功能
    let ifRenderSetting = ref<boolean>(false);
    let pagingSetting = ref();

    // 进入时
    const handleMouseEnter = () => {
        if (pagingSetting.value && !ifRenderSetting.value) {
            pagingSetting.value.style.backgroundColor = '#f1f1f1' // 直接改样式
        }
    }

    // 离开时
    const handleMouseLeave = () => {
        if (pagingSetting.value && !ifRenderSetting.value) {
            pagingSetting.value.style.backgroundColor = ''
        }
    }

    function clickPagingSetting() {
        ifRenderSetting.value = !ifRenderSetting.value
    }

    let { positiveInteger: directNum } = usePositiveIntegerRef(pageNum.value + '');

    let { positiveInteger: displayNum } = usePositiveIntegerRef(pageSize.value + '')

    async function directOK() {
        if (directNum.value === '') {
            directNum.value = '1';
        }
        pageNum.value = Number(directNum.value);
        articles.value = await getBlogs(marchText.value);
        directNum.value = pageNum.value + ''
    }

    async function displayOK() {
        if (displayNum.value === '') {
            displayNum.value = '1';
        }
        pageSize.value = Number(displayNum.value)
        articles.value = await getBlogs(marchText.value);
        directNum.value = pageNum.value + ''
    }

    function changeDirectNum(num: number) {
        if (directNum.value === '') {
            directNum.value = '0';
        }
        if (num === -1 && directNum.value === '0') {
            return;
        }
        directNum.value = Number(directNum.value) + num + ''
    }

    function changeDisplayNum(num: number) {
        if (displayNum.value === '') {
            displayNum.value = '0';
        }
        if (num === -1 && displayNum.value === '0') {
            return;
        }
        displayNum.value = Number(displayNum.value) + num + ''
    }

    //以上是分页对象、分页功能


    // 以下完成筛选与排序
    let { timeout: marchText } = useTimeOutRef(homeSortJS.value.marchText, 700)
    async function getBlogs(marchText = "") {
        paging.value = (await axios.get(`${baseUrl}/bwun/findSortBy${sortName.value}?`, {
            params: {
                sortFlag: sortFlag.value,
                marchText,
                pageNum: pageNum.value,
                pageSize: pageSize.value
            }
        })).data.data;
        homePagingJS.value.pageNum = pageNum.value = paging.value.pageNum;
        homePagingJS.value.pageSize = pageSize.value = paging.value.pageSize;
        totalPage.value = paging.value.totalPage;
        totalCount.value = paging.value.totalCount;
        data.value = paging.value.data;
        directNum.value = pageNum.value + '';
        return paging.value.data;
    }

    let sortName = ref(homeSortJS.value.sortName)

    let sortFlag = ref(homeSortJS.value.sortFlag)

    watch([sortName, sortFlag], async (value) => {
        homeSortJS.value.sortName = value[0];
        homeSortJS.value.sortFlag = value[1];
        articles.value = await getBlogs(marchText.value);
    })

    watch(marchText, (value) => {
        homeSortJS.value.marchText = value;
    })

    async function changeToSortUp() {
        sortFlag.value = "up";
        homeSortJS.value.sortFlag = "up"
        articles.value = await getBlogs(marchText.value)
    }

    async function changeToSortDown() {
        sortFlag.value = "down";
        homeSortJS.value.sortFlag = "down"
        articles.value = await getBlogs(marchText.value)
    }

    watch(marchText, async (oldValue, newValue) => {
        if (oldValue.trim() == '' && newValue.trim() == '') {
            return;
        }
        articles.value = await getBlogs(marchText.value.trim());
    })
</script>

<style scoped lang="scss">
.main-part {
    width: 70vw;
    margin: 0 auto;
    background-color: aliceblue;
    display: flex;
    flex-direction: column;
    padding: 10px;

    .pagingSelect {
        display: flex;
        padding: 20px;
        align-items: center;
        gap: 15px;

        div:not(.setting) {
            // border: 2px solid #6a74df;
            width: 40px;
            height: 40px;
            line-height: 33px;
            text-align: center;
            font-size: 18px;
            font-weight: 700;
            color: #585757;
            cursor: pointer;
            user-select: none;

            &:not(.pagingSetting):hover {
                background-color: #f1f1f1;
            }
        }

        .lastArrow {
            background: url("../assets/上一页.png") center center/35% no-repeat;

            &:hover {
                background-image: url("../assets/上一页（mouseOn）.png");
            }
        }

        div.thisPageNum {
            position: relative;
            user-select: text;
            cursor: text;

            &::before {
                content: "";
                position: absolute;
                background-color: #174ae4;
                left: 0;
                right: 0;
                bottom: 0;
                top: 37px;
            }
        }

        .nextArrow {
            background: url("../assets/下一页.png") center center/35% no-repeat;

            &:hover {
                background-image: url("../assets/下一页（mouseOn）.png");
            }
        }

        .pagingSetting {
            background: url("../assets/分页菜单设置.png") center center/55% no-repeat;

            &:hover {
                background-image: url("../assets/分页菜单设置（mouseOn）.png")
            }
        }

        .setting {
            background-color: #fff;
            position: relative;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 10px;
            width: 250px;
            height: 100px;
            border-radius: 20px;
            left: 60px;
            top: 10px;
            user-select: none;

            div.option {
                &:hover {
                    background-color: #ffffff;
                }

                display: flex;
                align-items: center;
                width: 100%;
                font-size: 17px;
                border-top: 1px solid rgb(152, 147, 147);
                cursor: default;

                input {
                    width: 40px;
                    margin-left: 5px;
                }

                div {
                    display: flex;
                    flex-direction: column;
                    width: 20px;
                    margin-right: 4px;
                    cursor: pointer;


                    &:hover {
                        background-color: #ffffff;
                    }

                    button {
                        flex: 1;
                        width: 100%;
                        border: none;
                        cursor: pointer;
                    }

                    button.topArrow {
                        background: url("../assets/上箭头.png") center center/55% no-repeat;

                        &:hover {
                            background-color: #e5e5e5;
                        }
                    }

                    button.bottomArrow {
                        background: url("../assets/下箭头.png") center center/55% no-repeat;

                        &:hover {
                            background-color: #e5e5e5;
                        }
                    }
                }

                .ok {
                    margin-left: 16px;
                }

                &:first-child {
                    border: none;
                }
            }
        }

    }

    .non,
    .nonElse {
        text-align: center;
        font-size: 25px;
        font-weight: 600;
        color: #b3b1b1;
        text-align: center;
        padding-top: 30px;
    }

    .filterAndSort {
        display: flex;
        gap: 20px;
        justify-content: center;

        .filter {
            display: flex;
        }

        .sort {
            display: flex;
            align-items: center;

            div {
                margin-left: 10px;
                display: flex;
                height: 20px;
                cursor: pointer;

                img {
                    border-radius: 15%;
                }
            }

            .shadow {
                background-color: rgba(24, 27, 27, 0.25);
            }
        }
    }
}
</style>