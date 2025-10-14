import { ref } from 'vue'
import { type Favorite,type FavoriteDTO } from '@/types'
import axios from 'axios'
import {nanoid} from 'nanoid'
export default function(){
    async function findMyBlogs(authorId:string){
        return (await axios.get("http://localhost:8080/bwun/findByAuthorId",{
            params:{
                authorId,
            }
        })).data
    }
    return {findMyBlogs}
}