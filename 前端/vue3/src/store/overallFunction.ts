import { defineStore } from "pinia";

export const useOverallFunctionStore = defineStore('overallFunction', {
    state(){
        return{
            stopWatch_Home : ()=>{}
        }
    },
    persist: true
})