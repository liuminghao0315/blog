type Blog = {
    id: string,
    title: string,
    authorId: string,
    content: string,
    summary: string,
    gmtCreated: string,
    gmtModified: string
}

type BlogWithUserName = {
    id: string,
    title: string,
    authorName: string,
    content: string,
    summary: string,
    gmtCreated: string,
    gmtModified: string,
    likeCount: number,
    favoriteCount: number,
    commentCount: number,
}

type User = {
    id?: string,
    name?: string,
    authority?: string,
    gmtCreated?: string,
    gmtModified?: string,
}

type UserWithAvatarUrl = {
    id?: string,
    name?: string,
    authority?: string,
    gmtCreated?: string,
    gmtModified?: string,
    avatarUrl?: string,
}

type RegisterOrLoginForm = {
    userName: string,
    password: string
}

type UserProfile = {
    id?: string,
    userId?: string,
    avatarUrl?: string,
    gender?: string,
    signature?: string,
    birthday?: string,
    gmtCreated?: string,
    gmtModified?: string,
}

type UserProfileWithUserName = {
    id?: string,
    userId?: string,
    userName?: string,
    avatarUrl?: string,
    gender?: string,
    signature?: string,
    birthday?: string,
    gmtCreated?: string,
    gmtModified?: string,
}

type StructuredComments = {
    id?: string,
    articleId?: string,
    authorId?: string,
    parentId?: string,
    rootId?: string,
    content?: string,
    children?: [] | null,
    commentLikesDTO?: CommentLikesDTO,
    gmtCreated?: string,
    gmtModified?: string,
    userName?: string,
    parentName?: string,
    avatarUrl?: string,
    second?: boolean,
}

type CommentDO = {
    id?: string,
    articleId?: string,
    authorId?: string,
    parentId?: string,
    rootId?: string,
    content?: string,
    gmtCreated?: string,
    gmtModified?: string,
}

type ArticleLikesDTO = {
    likesNum: number,
    myIncrement: number,
}

type ArticleLikes = {
    id?: string,
    articleId?: string,
    userId?: string,
    incrementNum?: number,
}

type CommentLikesDTO = {
    likesNum?: number,
    myIncrement?: number,
}

type CommentLikes = {
    id?: string,
    commentId?: string,
    userId?: string,
    incrementNum?: number,
}

type Favorite = {
    id: string,
    userId: string,
    articleId: string,
}

type FavoriteDTO = {
    favoriteCount: number,
    ifUserFavorite: boolean,
}

type Paging = {
    pageNum: number,
    pageSize: number,
    totalPage: number,
    totalCount: number,
    data:any[],
}

export { type Blog, type User, type BlogWithUserName, type RegisterOrLoginForm, type UserProfile, type UserProfileWithUserName, type StructuredComments, type UserWithAvatarUrl, type CommentDO, type ArticleLikesDTO, type ArticleLikes, type CommentLikesDTO, type CommentLikes, type Favorite, type FavoriteDTO, type Paging };