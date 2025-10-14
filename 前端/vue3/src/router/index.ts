import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/pages/Home.vue'
import Detail from '@/pages/Detail.vue'
import Write from '@/pages/Write.vue'
import Register from '@/pages/Register.vue'
import Login from '@/pages/Login.vue'
import ChangeArticle from '@/pages/ChangeArticle.vue'
import UserSpace from '@/pages/UserSpace.vue'
import EditPersonData from '@/pages/EditPersonData.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/home',
            component: Home,
        },
        {
            path: "/detail",
            component: Detail,
            props(route: any) {
                return route.query;
            }
        },
        {
            path: "/write",
            component: Write,
        },
        {
            path: "/change",
            component: ChangeArticle,
            props(route: any) {
                return route.query;
            }
        },
        {
            path: "/register",
            component: Register,
        },
        {
            path: "/login",
            component: Login,
        },
        {
            path: "/space/:name",
            component: UserSpace,
            props(route: any) {
                return route.params;
            }
        },
        {
            path: "/space/:name/edit",
            component: EditPersonData,
            props(route: any) {
                return route.params;
            }
        },
        {
            path: '/',
            redirect: '/home'
        }
    ]
})

export default router