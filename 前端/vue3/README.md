在script中 import { MdPreview } from 'md-editor-v3';
在template中 <MdPreview :modelValue="article.content" />

父改子 用事件触发（子绑定、父触发）
子改父 用provide/inject拿到的 父亲的响应式数据