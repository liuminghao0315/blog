export default function removeTimesTAndSecond(time: any) {
    return (time as string) ? (time as string).replace(/T/g, ' ').replace(/.[\d]{3}Z/, ' ').slice(0, -3) : (time as string)
}
