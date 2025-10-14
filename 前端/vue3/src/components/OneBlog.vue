<template>
    <div id="OneBlog" @click="clickBlock(article.id)">
        <div class="title">
            <span>标题：</span>
            <span v-html="highlight(article.title)"></span>
        </div>
        <div class="summary">
            <span>摘要：</span>
            <span v-html="highlight(article.summary)"></span>
        </div>
        <div class="author-and-time">
            <div class="author">
                <span>作者：</span>
                <span v-html="highlight(article.authorName)"></span>
            </div>
            <div class="other">
                {{ otherMsgComputed }}
            </div>
        </div>
        <div class="content nowrap" v-show="ifMarchContent && aliasMarchText.trim()">
            <span>匹配内容：</span>
            <span v-html="highlightSnippet(article.content)"></span>
        </div>
    </div>
</template>

<script setup lang="ts" name="OneBlog">
    import { ref, onMounted, computed, watch, toRefs } from 'vue';
    import { useRouter } from 'vue-router'

    const props =
        defineProps<{
            article: any
            marchText: string
            sortName: string
            sortFlag: string
            favoriteUserId?: string
            myBlogUserId?: string
        }>()

    const { article, marchText: aliasMarchText, sortName, sortFlag, favoriteUserId, myBlogUserId } = toRefs(props)

    // console.log("OneBlog中接受的marchText(aliasMarchText)值为：",aliasMarchText.value)

    aliasMarchText.value;
    let addMarchTextToUrl = ref(false)
    if ((favoriteUserId.value || myBlogUserId.value)) {
        addMarchTextToUrl.value = true;
    }
    // console.log(typeof marchText, marchText)

    const emit = defineEmits(['setSortFlag', 'setSortName'])

    const router = useRouter();
    function clickBlock(id: string) {
        const query = {
            id,
            favoriteUserId: favoriteUserId?.value,
            myBlogUserId: myBlogUserId?.value,
        }
        if (addMarchTextToUrl.value && aliasMarchText.value) {
            //@ts-ignore
            query.marchText = aliasMarchText?.value
        }
        router.push({
            path: "/detail",
            query
        })
    }

    // 以下完成筛选与排序
    let ifMarchContent = ref(false)

    const otherMsgComputed = computed(() => {
        if (sortName.value == "Like") {
            return "点赞：" + article.value.likeCount
        } else if (sortName.value == "Favorite") {
            return "收藏：" + article.value.favoriteCount
        } else if (sortName.value == "Comment") {
            return "评论：" + article.value.commentCount
        } else if (sortName.value == "Date") {
            return "时间：" + article.value.gmtModified.replace(/T/g, ' ').replace(/.[\d]{3}Z/, ' ')
        }
    })

    function highlight(text: string) {
        if (!aliasMarchText.value) return text; // 没有检索词就直接返回原文
        const keyword = aliasMarchText.value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&'); // 转义正则特殊字符
        const regex = new RegExp(keyword, 'gi');
        return text.replace(regex, match => `<span class="highlight">${match}</span>`);
    }

    function highlightSnippet(text: string) {
        if (!aliasMarchText.value.trim()) return text;

        const keyword = aliasMarchText.value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
        const regex = new RegExp(keyword, 'i'); // 找第一个匹配
        const match = text.match(regex);

        if (!match) {
            // 没匹配就取前 30 个字符
            ifMarchContent.value = false;
            return text.length > 30 ? text.slice(0, 30) + "..." : text;
        }
        ifMarchContent.value = true;

        const index = match.index!;
        const matchText = match[0];

        // 只截取匹配位置前 15 个字 + 匹配词 + 后 15 个字
        const start = Math.max(0, index - 25);
        const end = Math.min(text.length, index + matchText.length + 25);
        let snippet = text.slice(start, end);

        // 高亮关键词
        snippet = snippet.replace(new RegExp(keyword, 'gi'), m => `<span class="highlight">${m}</span>`);

        // 加省略号
        return (start > 0 ? "..." : "") + snippet + (end < text.length ? "..." : "");
    }
</script>

<style lang="scss" scoped>
#OneBlog {
    background-color: #eae9e9;
    box-shadow: 0 0 10px;
    border-radius: 10px;
    margin: 10px 0;
    padding: 20px;
    cursor: pointer;

    :deep(.highlight) {
        background-color: rgb(252, 150, 119);
        font-weight: bold;
    }

    :deep(.nowrap) {
        white-space: nowrap;
        /* 强制文本在一行内显示，不换行 */
        text-overflow: ellipsis;
        /* 当文本溢出容器宽度时，显示省略号 */
        overflow: hidden;
        /* 隐藏溢出的文本 */
    }
}
</style>