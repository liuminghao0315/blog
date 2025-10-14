import {customRef} from 'vue'
export default function(){
    let text:string = "";
    let trimText = customRef((track,trigger)=>{
        return{
            get(){
                track();
                return text.trim();
            },
            set(value){
                text = value.trim();
                trigger();
            }
        }
    })
    return {trimText}
}