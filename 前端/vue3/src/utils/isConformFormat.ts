export default function isConformFormat(formatSplit: string, originStr: string) {
    if(originStr.length<=8){
        return false;
    }
    const originStrArr = originStr.split(formatSplit);
    // console.log(originStrArr)
    if (originStrArr.length != 3) {
        return false;
    }
    if (originStrArr[0].length != 4) {
        return false;
    }
    // if (originStrArr[1].length == 1) {
    //     originStrArr[1] = "0" + originStrArr[1]
    // }
    if (originStrArr[1].length != 2) {
        return false;
    }
    // if (originStrArr[2].length == 1) {
    //     originStrArr[2] = "0" + originStrArr[2]
    // }
    if (originStrArr[2].length != 2) {
        return false;
    }
    return true;
}
