<template>
    <div id="ImagePreview">
        <div :style="{ backgroundImage: urlText }" class="img"></div>
        <div class="cancel" @click="cancelImg" v-if="canCancel"></div>
        <div class="forbid" v-if="!canCancel" title="请先删除图片对应的markdown"></div>
        <!-- <button @click="console.log(canCancel)">点我查看是否可以删除</button> -->
    </div>
</template>

<script lang="ts" setup name="ImagePreview">
    import { inject,ref, watchEffect } from 'vue'

    const baseUrl = inject('baseUrl')
    const BASE_PATH = baseUrl + "/images/"

    const props = defineProps(['fileName', 'id', 'content'])
    const { fileName, id } = props;

    let canCancel = ref(true)

    watchEffect(()=>{
    canCancel.value = props.content===undefined || props.content.indexOf(fileName)===-1
    })
    // console.log(props.content)

    const emit = defineEmits(['cancelImg'])
    const urlText = `url(${fileName})`
    // console.log(JSON.stringify(fileName))
    function cancelImg() {
        emit('cancelImg', id)
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

    div.cancel,div.forbid {
        position: absolute;
        background: url("../assets/取消按钮.png") center center/cover no-repeat;
        left: -5px;
        top: -5px;
        height: 20px;
        width: 20px;
        border-radius: 50%;
        cursor: pointer;
    }

    div.forbid{
        background-image: url("../assets/禁止.png");
    }
}
</style>