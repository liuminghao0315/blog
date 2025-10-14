export default function removeTimesT(time: any) {
    return (time as string) ? (time as string).replace(/T/g, ' ').replace(/.[\d]{3}Z/, ' ') : (time as string)
}
