<template>
    <div id="ImagePreview">
        <div :style="{ backgroundImage: urlText }" class="img"></div>
        <div class="cancel" @click="cancelImg"></div>
    </div>
</template>

<script lang="ts" setup name="ImagePreview">
    const BASE_PATH = "D:\\360MoveData\\Users\\23793\\Desktop\\博客网站\\七代版本-加入分页系统\\后端\\blog_project\\src\\main\\resources\\images\\"

    const { url,id } = defineProps(['url','id'])
    const emit = defineEmits(['cancelImg'])
    const urlText = `url(${url})`
    // console.log(JSON.stringify(url))
    function cancelImg(){
        emit('cancelImg',id)
    }
</script>

<style lang="scss" scoped>
#ImagePreview {
    position: relative;

    div.img {
        background-position: center center;
        background-size: cover;
        background-repeat: no-repeat;

        width: 150px;
        height: 150px;
        border: 1px solid green;
        margin: 0 3px;
    }

    div.cancel {
        position: absolute;
        background: url("../assets/取消按钮.png") center center/cover no-repeat;
        left:-5px;
        top:-5px;
        height:20px;
        width:20px;
        border-radius: 50%;
        cursor: pointer;
    }
}
</style>