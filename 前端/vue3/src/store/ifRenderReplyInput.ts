import { defineStore } from "pinia";

export const useIfRenderReplyInputStore = defineStore('ifRenderReplyInput', {
    state(){
        return{
            ifRenderReplyInput:<boolean>false,
        }
    },
    persist: true
})