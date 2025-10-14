import { customRef } from 'vue'
export default function (initialValue: string) {
    let positiveInteger = customRef((track, trigger) => {
        return {
            get() {
                track();
                return initialValue;
            },
            set(value) {
                if (/^[1-9]\d*$/.test(value) || value==="") {
                    initialValue = value;
                    trigger();
                }
                    trigger();
            }
        }
    })
    return { positiveInteger }
}