export default function correctionDate(originStr: string) {
    if(originStr.length<=8){
        return originStr;
    }
    const formatSplit = originStr[4];
    const originStrArr = originStr.split(formatSplit);
    if (originStrArr[1].length == 1 && /^\d+$/.test(originStrArr[1])) {
        originStrArr[1] = "0" + originStrArr[1]
    }
    if (originStrArr[2].length == 1 && /^\d+$/.test(originStrArr[2])) {
        originStrArr[2] = "0" + originStrArr[2]
    }
    return originStrArr.join(formatSplit);
}