import { ref, inject } from 'vue'
import { type Favorite,type FavoriteDTO } from '@/types'
import axios from 'axios'
import {nanoid} from 'nanoid'
export default function(){
    const baseUrl = inject('baseUrl')

    async function findMyBlogs(authorId:string){
        return (await axios.get(baseUrl+"/bwun/findByAuthorId",{
            params:{
                authorId,
            }
        })).data
    }
    return {findMyBlogs}
}