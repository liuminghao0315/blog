<template>
    <div id="NonHeadComments">
        <div class="recursion" v-for="child in options" :key="child.id">
            <OneComment :oneCommentObj="child" />
            <NonHeadComments v-if="child?.children" :options="<StructuredComments[]>child.children" />
        </div>
    </div>
</template>

<script setup lang='ts' name="NonHeadComments">
    import { type StructuredComments, type CommentDO } from '@/types'
    import OneComment from './OneComment.vue'
    const { options } = defineProps<{ options: StructuredComments[] }>()
</script>