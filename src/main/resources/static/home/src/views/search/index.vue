<template>
  <div class="page_search search_index">
	<div class="warp">
	  <div class="container">
		<div class="row">
		  <div class="col-12">
			<div class="card_result_search">
			  <div class="title">搜索结果</div>

				<!-- 资讯搜索结果 -->
			  <list_result_search :list="result_article" title="音乐资讯" source_table="article"></list_result_search>

				<!-- 论坛搜索结果 -->
			  <list_result_search :list="result_forum" title="社交论坛" source_table="forum"></list_result_search>

        <list_result_search
				v-if="$check_action('/music_library/list', 'get')"
				:list="result_music_library_song_name"
				title="音乐乐库歌曲名称"
				source_table="music_library"
			  ></list_result_search>

        <list_result_search
				v-if="$check_action('/music_library/list', 'get')"
				:list="result_music_library_singers_name"
				title="音乐乐库歌手姓名"
				source_table="music_library"
			  ></list_result_search>
      </div>
		  </div>
		</div>
	  </div>
	</div>
  </div>
</template>

<script>
import mixin from "../../mixins/page.js";
import list_result_search from "../../components/diy/list_result_search.vue";

export default {
  mixins: [mixin],
  data() {
	return {
	  "query": {
		word: "",
	  },
	  "result_article": [],
	  "result_forum": [],
    "result_ordinary_users_user_name":[],
    "result_music_library_song_name":[],
    "result_music_library_singers_name":[],
    "result_music_library_song_classification":[],
    "result_song_classification_song_classification":[],
  };
  },
  methods: {
	/**
	 * 获取资讯
	 */
	get_article() {
	  this.$get("~/api/article/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_article = json.result.list;
		}
	  });
	},
	/**
	 * 获取社交论坛
	 */
	get_forum() {
	  this.$get("~/api/forum/get_list?like=0", { page: 1, size: 10, title: this.query.word }, (json) => {
		if (json.result) {
		  this.result_forum = json.result.list;
		}
	  });
	},

  /**
	 * 获取song_name
	 */
	get_music_library_song_name(){
		let url = "~/api/music_library/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "song_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_music_library_song_name = json.result.list;
			result_music_library_song_name.map(o => o.title = o['song_name'])
	  			this.result_music_library_song_name = result_music_library_song_name
		 	}
		});
	},
  /**
	 * 获取singers_name
	 */
	get_music_library_singers_name(){
		let url = "~/api/music_library/get_list?like=0";
				this.$get(url, { page: 1, size: 10, "singers_name": this.query.word }, (json) => {
		  if (json.result) {
			var result_music_library_singers_name = json.result.list;
			result_music_library_singers_name.map(o => o.title = o['singers_name'])
	  			this.result_music_library_singers_name = result_music_library_singers_name
		 	}
		});
	},
  },
  components: { list_result_search },
	created(){
    this.query.word = this.$route.query.word || "";
  },
  mounted() {
	  this.get_article();
	  this.get_forum();
    this.get_music_library_song_name();
    this.get_music_library_singers_name();
  },
  watch: {
	$route() {
	  $.push(this.query, this.$route.query);
	  this.get_article();
	  this.get_forum();
    this.get_music_library_song_name();
    this.get_music_library_singers_name();
    },
  },
};
</script>

<style scoped>
.card_search {
  text-align: center;
}
.card_result_search>.title {
  text-align: center;
  padding: 10px 0;
}
</style>
