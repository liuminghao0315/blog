export default function isEmpty(value: any) {
    return value === null || value === undefined || value.trim() === '';
}

// export default isEmpty;