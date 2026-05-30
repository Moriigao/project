import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue';
import login from '../views/login.vue';
import forgot from '../views/forgot.vue';
Vue.use(VueRouter)

const routes = [
    // 主页
    {
        path: '/',
        name: 'index',
        component: index,
        meta: {
            index: 0,
            title: '首页'
        }
    },

    // 登录
    {
        path: '/login',
        name: 'login',
        component: login,
        meta: {
            index: 0,
            title: '登录'
        }
    },
    // 忘记密码
    {
        path: '/forgot',
        name: "forgot",
        component: forgot,
        meta: {
            index: 0,
            title: '忘记密码'
        }
    },

    // 修改密码
    {
        path: '/user/password',
        name: "password",
        component: () => import("../views/user/password.vue"),
        meta: {
            index: 0,
            title: '修改密码'
        }
    },
    // 轮播图管理
    {
        path: '/slides/table',
        name: 'slides_table',
        component: () => import('../views/slides/table.vue'),
        meta: {
            index: 0,
            title: '轮播图列表'
        }
    },
    //轮播图详情
    {
        path: '/slides/view',
        name: 'slides_view',
        component: () => import('../views/slides/view.vue'),
        meta: {
            index: 0,
            title: '轮播图详情'
        }
    },
    // 音乐资讯
    {
        path: '/article/table',
        name: 'article_table',
        component: () => import('../views/article/table.vue'),
        meta: {
            index: 0,
            title: '音乐资讯列表'
        }
    },
    //资讯详情
    {
        path: '/article/view',
        name: 'article_view',
        component: () => import('../views/article/view.vue'),
        meta: {
            index: 0,
            title: '音乐资讯详情'
        }
    },

    // 资讯分类路由
    {
        path: '/article_type/table',
        name: 'article_type_table',
        component: () => import('../views/article_type/table.vue'),
        meta: {
            index: 0,
            title: '音乐资讯分类列表'
        }
    },
    {
        path: '/article_type/view',
        name: 'article_type_view',
        component: () => import('../views/article_type/view.vue'),
        meta: {
            index: 0,
            title: '音乐资讯分类详情'
        }
    },
    // 敏感词路由
    {
        path: '/sensitive_vocabulary/table',
        name: 'sensitive_vocabulary_table',
        component: () => import('../views/sensitive_vocabulary/table.vue'),
        meta: {
            index: 0,
            title: '敏感词列表'
        }
    },
    {
        path: '/sensitive_vocabulary/view',
        name: 'sensitive_vocabulary_view',
        component: () => import('../views/sensitive_vocabulary/view.vue'),
        meta: {
            index: 0,
            title: '敏感词详情'
        }
    },

    // 留言板路由
    {
        path: '/message/table',
        name: 'message_table',
        component: () => import('../views/message/table.vue'),
        meta: {
            index: 0,
            title: '留言反馈列表'
        }
    },
    {
        path: '/message/view',
        name: 'message_view',
        component: () => import('../views/message/view.vue'),
        meta: {
            index: 0,
            title: '留言反馈详情'
        }
    },

    // 论坛路由
    {
        path: '/forum/table',
        name: 'forum_table',
        component: () => import('../views/forum/table.vue'),
        meta: {
            index: 0,
            title: '论坛列表'
        }
    },
    {
        path: '/forum/view',
        name: 'forum_view',
        component: () => import('../views/forum/view.vue'),
        meta: {
            index: 0,
            title: '论坛详情'
        }
    },

    // 论坛分类路由
    {
        path: '/forum_type/table',
        name: 'forum_type_table',
        component: () => import('../views/forum_type/table.vue'),
        meta: {
            index: 0,
            title: '论坛分类列表'
        }
    },
    {
        path: '/forum_type/view',
        name: 'forum_type_view',
        component: () => import('../views/forum_type/view.vue'),
        meta: {
            index: 0,
            title: '论坛分类详情'
        }
    },

    // 公告路由
    {
        path: '/notice/table',
        name: 'notice_table',
        component: () => import('../views/notice/table.vue'),
        meta: {
            index: 0,
            title: '通知公告列表'
        }
    },
    {
        path: '/notice/view',
        name: 'notice_view',
        component: () => import('../views/notice/view.vue'),
        meta: {
            index: 0,
            title: '通知公告详情'
        }
    },

    // 评论路由
    {
        path: '/comment/table',
        name: 'comment_table',
        component: () => import('../views/comment/table.vue'),
        meta: {
            index: 0,
            title: '评论列表'
        }
    },
    {
        path: '/comment/view',
        name: 'comment_view',
        component: () => import('../views/comment/view.vue'),
        meta: {
            index: 0,
            title: '评论详情'
        }
    },
    // 普通用户路由
    {
        path: '/ordinary_users/table',
        name: 'ordinary_users_table',
        component: () => import('../views/ordinary_users/table.vue'),
        meta: {
            index: 0,
            title: '普通用户列表'
        }
    },
    {
        path: '/ordinary_users/view',
        name: 'ordinary_users_view',
        component: () => import('../views/ordinary_users/view.vue'),
        meta: {
            index: 0,
            title: '普通用户详情'
        }
    },
    // 音乐乐库路由
    {
        path: '/music_library/table',
        name: 'music_library_table',
        component: () => import('../views/music_library/table.vue'),
        meta: {
            index: 0,
            title: '音乐乐库列表'
        }
    },
    {
        path: '/music_library/view',
        name: 'music_library_view',
        component: () => import('../views/music_library/view.vue'),
        meta: {
            index: 0,
            title: '音乐乐库详情'
        }
    },
    // 歌曲分类路由
    {
        path: '/song_classification/table',
        name: 'song_classification_table',
        component: () => import('../views/song_classification/table.vue'),
        meta: {
            index: 0,
            title: '歌曲分类列表'
        }
    },
    {
        path: '/song_classification/view',
        name: 'song_classification_view',
        component: () => import('../views/song_classification/view.vue'),
        meta: {
            index: 0,
            title: '歌曲分类详情'
        }
    },
    // 用户路由
    {
        path: '/user/table',
        name: 'user_table',
        component: () => import('../views/user/table.vue'),
        meta: {
            index: 0,
            title: '用户列表'
        }
    },
    {
        path: '/user/view',
        name: 'user_view',
        component: () => import('../views/user/view.vue'),
        meta: {
            index: 0,
            title: '用户详情'
        }
    },
    {
        path: '/user/info',
        name: 'user_info',
        component: () => import('../views/user/info.vue'),
        meta: {
            index: 0,
            title: '个人信息'
        }
    }
]

const router = new VueRouter({
    mode: 'hash',
    base: process.env.BASE_URL,
    routes
})

router.beforeEach((to, from, next) => {
    let token = to.query.token;
    if (token) {
        $.db.set("token", token, 120);
    }
    next();
})

router.afterEach((to, from, next) => {
    let title = "个性化音乐系统-admin";
    document.title = title;
})

export default router
