import { customRef } from 'vue'
export default function (initialValue:string,delay: number) {
    let timer:number;
    let timeout = customRef((track, trigger) => {
        return {
            get() {
                track();
                return initialValue;
            },
            set(value) {
                clearTimeout(timer)
                if(value==""){
                    initialValue = "";
                    trigger();
                    return;
                }
                timer = setTimeout(() => {
                    initialValue = value;
                    trigger();
                },delay);
            }
        }
    })
    return { timeout }
}