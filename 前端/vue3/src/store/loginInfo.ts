import { defineStore } from "pinia";

export const useLoginInfoStore = defineStore('loginInfo', {
    state(){
        return{
            userName:<string>"",
            password:<string>"",
            autoFillTime:<number>0,
        }
    },
    persist: true
})