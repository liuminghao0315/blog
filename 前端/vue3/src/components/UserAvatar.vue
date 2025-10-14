<template>
    <div id="UserAvatar">
        <div class="div-img" ref="divAvatar">
            <img :src="url" ref="imgAvatar" @load="imgLoad" @error="imgError">
        </div>
    </div>
</template>

<script setup lang="ts" name="UserAvatar">
    import { ref, onMounted, onUpdated, watch } from 'vue'

    const { url, pxNum } = defineProps(['url', 'pxNum'])

    const divAvatar = ref();
    const imgAvatar = ref();

    onMounted(() => {
        divAvatar.value.style.width = pxNum + "px"
        divAvatar.value.style.height = pxNum + "px"
        imgAvatar.value.style.width = pxNum + "px"
        imgAvatar.value.style.height = pxNum + "px"

        if (url) {
            imgAvatar.value.style.opacity = 1
        } else {
            imgAvatar.value.style.opacity = 0
        }
    })

    function imgLoad() {
        imgAvatar.value.style.opacity = 1
    }

    function imgError() {
        imgAvatar.value.style.opacity = 0
    }

    // watch(() => url, () => {
    //     if (url) {
    //         imgAvatar.value.style.opacity = 1
    //     } else {
    //         imgAvatar.value.style.opacity = 0
    //     }
    // })
</script>

<style lang="scss" scoped>
div.div-img {
    background: url("https://img.ixintu.com/download/jpg/20200901/3e9ce3813b7199ea9588eeb920f41208_512_512.jpg!ys") center center/cover no-repeat;
    border-radius: 50%;
}

img {
    display: inline-block;
    border-radius: 50%;
}
</style>