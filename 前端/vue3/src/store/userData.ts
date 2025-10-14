import { defineStore } from "pinia";
import { type UserWithAvatarUrl } from '@/types'

export const useUserDataStore = defineStore('userData', {
    state() {
        return {
            userData: <UserWithAvatarUrl>{},
            homeSortJS: {
                sortFlag: "up",
                sortName: "Date",
                marchText: "",
            },
            userSpaceSortJS: {
                sortFlag: "up",
                sortName: "Date",
                marchText: "",
            },
            homePagingJS: {
                pageNum: 1,
                pageSize: 2,
            },
            userSpacePagingJS: {
                favorite: {
                    pageNum: 1,
                    pageSize: 2,
                },
                myblogs: {
                    pageNum: 1,
                    pageSize: 2,
                }
            },
        }
    },
    persist: true
})