import { defineStore } from "pinia";

export const useRegisterInfoStore = defineStore('registerInfo', {
    state(){
        return{
            userName:<string>"",
            password:<string>"",
            autoFillTime:<number>0,
        }
    },
    persist: true
})