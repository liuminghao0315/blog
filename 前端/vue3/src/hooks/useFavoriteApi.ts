import { ref, inject } from 'vue'
import { type Favorite,type FavoriteDTO } from '@/types'
import axios from 'axios'
import {nanoid} from 'nanoid'
export default function(){
    const baseUrl = inject('baseUrl')

    async function add(userId:string,articleId:string){
        const favorite : Favorite = {
            id:nanoid(),
            userId,
            articleId,
        }
        return (await axios.post(baseUrl+"/db/favorite/add",favorite)).data
    }

    async function delete_(userId:string,articleId:string){
        return (await axios.get(baseUrl+"/db/favorite/delete",{
            params:{
                userId,
                articleId,
            }
        })).data
    }

    async function deleteByArticleId(articleId:string){
        return (await axios.get(baseUrl+"/db/favorite/deleteByArticleId",{
            params:{
                articleId,
            }
        })).data
    }

    async function findByUserId(userId:string){
        return (await axios.get(baseUrl+"/db/favorite/findByUserId",{
            params:{
                userId,
            }
        })).data
    }

    async function getDTO(userId:string,articleId:string){
        return (await axios.get(baseUrl+"/db/favorite/getDTO",{
            params:{
                userId,
                articleId,
            }
        })).data
    }

    return {add,delete_,deleteByArticleId,findByUserId,getDTO}
}