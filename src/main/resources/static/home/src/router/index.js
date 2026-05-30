import Vue from 'vue';
import VueRouter from 'vue-router';
import index from '../views/index.vue'
import login from '../views/account/login.vue';
Vue.use(VueRouter)

const routes = [
	// 主页
	{
		path: '/',
		name: 'index',
		component: index
	},
	// 登录
	{
		path: '/account/login',
		name: 'login',
		component: login
	},
	// 忘记密码
	{
		path: '/account/forgot',
		name: 'forgot',
		component: () => import('../views/account/forgot.vue')
	},
	// 注册账号
	{
		path: '/account/register',
		name: 'register',
		component: () => import('../views/account/register.vue')
	},
	// 音乐资讯
	{
		path: '/article/list',
		name: 'article_list',
		component: () => import('../views/article/list.vue')
	},
	//详细资讯
	{
		path: '/article/details',
		name: 'article_details',
		component: () => import('../views/article/details.vue')
	},
	// 收藏路由
	{
		path: '/user/collect',
		name: 'collect_list',
		component: () => import('../views/user/collect.vue')
	},
	// 论坛路由
	{
		path: '/forum/list',
		name: 'forum_list',
		component: () => import('../views/forum/list.vue')
	},
	//详细论坛
	{
		path: '/forum/details',
		name: 'forum_details',
		component: () => import('../views/forum/details.vue')
	},
	//发布论坛
	{
		path: '/forum/edit',
		name: 'forum_edit',
		component: () => import('../views/forum/edit.vue')
	},
	// 留言列表
	{
	 	path: '/message/list',
	 	name: 'message_list',
	 	component: () => import('../views/message/list.vue')
	},
	//发布留言
	{
		path: '/message/edit',
		name: 'message_edit',
		component: () => import('../views/message/edit.vue')
	},
	// 留言板路由
	{
		path: '/message/table',
		name: 'message_table',
		component: () => import('../views/message/table.vue')
	},
	//留言查看
	{
		path: '/message/view',
		name: 'message_view',
		component: () => import('../views/message/view.vue')
	},
	// 通知公告
	{
		path: '/notice/list',
		name: 'notice_list',
		component: () => import('../views/notice/list.vue')
	},
	//通知详情
	{
		path: '/notice/details',
		name: 'notice_details',
		component: () => import('../views/notice/details.vue')
	},
	// 普通用户详情路由
	{
		path: '/ordinary_users/view',
		name: '/ordinary_users_view',
		component: () => import('../views/ordinary_users/view.vue')
	},
	// 音乐乐库列表
	{
		path: '/music_library/list',
		name: '/music_library_list',
		component: () => import('../views/music_library/list.vue')
	},
	// 音乐乐库详情
	{
		path: '/music_library/details',
		name: '/music_library_details',
		component: () => import('../views/music_library/details.vue')
	},
	// 用户路由
	{
		path: '/user/index',
		name: 'user_index',
		component: () => import('../views/user/index.vue')
	},
	// 基本信息
	{
		path: '/user/info',
		name: 'user_info',
		component: () => import('../views/user/info.vue')
	},
	// 密码修改
	{
		path: '/user/password',
		name: 'user_password',
		component: () => import('../views/user/password.vue')
	},
	// 搜索
	{
		path: '/search',
		name: 'search',
		component: () => import('../views/search/index.vue')
	}
]

const router = new VueRouter({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes
})

router.afterEach((to, from, next) => {
	let title = "个性化音乐系统-home";
	document.title = title;
	document.logo = "个性化音乐系统"
})

export default router
