export default function(jsObject:any){
    Object.keys(jsObject).forEach(key=>{
        jsObject[key] = false;
    })
}