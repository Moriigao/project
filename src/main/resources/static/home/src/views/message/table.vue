<template>
	<div class="page_user message_table" id="user_address">
		<div class="warp">
			<div class="container">
				<div class="row justify-content-between">
					<div class="col-12 col-md-3">
						<div class="card_menu">
							<!-- 左侧边栏 -->
							<list_admin_menu_user></list_admin_menu_user>
						</div>
					</div>
					<div class="col-12 col-md-9">
						<div class="card_addres message pl-2">
							<!-- 留言反馈 -->
							<div><span>{{$page_title("/message/table") || 留言反馈}}</span></div>
							<table_message v-if="$check_action('/message/table','get')"></table_message>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>


<script>
	import list_admin_menu_user from "@/components/diy/list_admin_menu_user.vue";
	import table_message from "../../components/diy/table_message.vue";
	export default {
		data() {
			return {
			};
		},
		mounted() {
		},
		methods: {
		},

		components: {
			list_admin_menu_user,
			table_message,
		},
	};
</script>

<style scoped>
	.container {
		min-height: 800px;
	}
</style>
