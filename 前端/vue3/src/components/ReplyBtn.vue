<template>
    <div id="ReplyBtn">
        <div class="reply" :style="{ color: replyBtnColor }">回复</div>
    </div>
</template>

<script setup lang="ts" name="ReplyBtn">
    import { ref } from 'vue'
    let replyBtnColor = ref('')
</script>

<style lang="scss" scoped>
    .reply {
        margin-left: 5px;
        cursor: pointer;
        user-select: none;
    }
</style>