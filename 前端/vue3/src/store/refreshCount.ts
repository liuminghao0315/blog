import { defineStore } from "pinia";

export const useRefreshCountStore = defineStore('refreshCount', {
    state(){
        return{
            login_to_home_count:<number>0,
            otherSpace_to_mySpace_count:<number>-1,
        }
    },
    persist: true
})