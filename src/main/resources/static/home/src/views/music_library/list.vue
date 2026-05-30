<template>
	<div class="diy_list page_music_library" id="music_library_list">
		<div class="warp">
			<div class="container diy_list_container">
				<div class="diy_list_title">
					<div class="col">
						<span class="title">音乐乐库列表</span>
					</div>
				</div>
				<div class="leis_box">
				<div class="iudis_box">
				<div class="row diy_list_search">
					<div class="col">
						<!-- 搜索栏 -->
						<div class="view Search">
							<span class="diy_list_search_title">关键字搜索：</span>
              <b-form-input size="sm" class="mr-sm-2" placeholder="歌曲名称搜索" v-model="query['song_name']" />
              <b-form-input size="sm" class="mr-sm-2" placeholder="歌手姓名搜索" v-model="query['singers_name']" />
              <b-form-input size="sm" class="mr-sm-2" placeholder="歌曲分类搜索" v-model="query['song_classification']" />
              <b-button size="sm" @click="search()" >
								<b-icon icon="search"/>
							</b-button>
						</div>
					</div>
				</div>
				<div class="diy_list_select_box">
					<span class="diy_list_select_title">下拉搜索：</span>
						<div class="diy_list_dropdown_box">
						<div class="col">
							<!-- 筛选 -->
							<div class="view sift">
                <b-dropdown text="歌曲分类" variant="outline-dark" left>
									<b-dropdown-item @click="filter_set('全部','song_classification')">全部</b-dropdown-item>
										<b-dropdown-item v-for="(o, i) in list_song_classification" :key="i" @click="filter_set(o['song_classification'],'song_classification')" >
												{{ o['song_classification'] }}
										</b-dropdown-item>
								</b-dropdown>
                <!-- 排序 -->
								<b-dropdown text="排序" variant="outline-dark" left>
										<b-dropdown-item v-for="(o, i) in list_sort" :key="i" @click="set_sort(o)" >
												{{ o.name }}
										</b-dropdown-item>
								</b-dropdown>
							</div>
						</div>
					</div>
				</div>
        </div>
				<div class="row diy_list_box">
					<div class="col">
						<list_music_library :list="list" />
					</div>
				</div>
	      </div>
	      <!-- 大盒子结尾 -->
				<div class="row diy_list_page_box">
					<div class="col overflow-auto flex_cc">
						<!-- 分页器 -->
            <b-pagination v-model="query.page" :total-rows="count" :per-page="query.size" @change="goToPage"/>
					</div>
				</div>
				<music_player :music-list="player_list"></music_player>
			</div>
		</div>
	</div>
</template>

<script>
	import list_music_library from "@/components/diy/list_music_library.vue";
	import diy_pager from "@/components/diy/diy_pager";
	import mixin from "@/mixins/page.js";
	import music_player from "@/components/diy/music_player.vue";

	export default {
		mixins: [mixin],
		components: {
			diy_pager,
			list_music_library,
			music_player,
		},
		data() {
			return {
				url_get_list: "~/api/music_library/get_list?like=0",

				// 查询条件
				query: {
					keyword: "",
					page: 1,
					size: 12,
          song_name: "",
          singers_name: "",
          song_classification: "",
        },
				player_list: [],
				// 排序内容
				list_sort: [{
						name: "创建时间从高到低",
						value: "create_time desc",
					},
					{
						name: "创建时间从低到高",
						value: "create_time asc",
					},
					{
						name: "更新时间从高到低",
						value: "update_time desc",
					},
					{
						name: "更新时间从低到高",
						value: "update_time asc",
					},
        ],
        // 歌曲分类列表
				"list_song_classification": [""],
			}
		},
		methods: {
			/**
			 * 筛选选择
			 */
			filter_set(o,key) {
			    if (o == "全部") {
			        this.query[key] = "";
			    } else {
			        this.query[key] = o;
			    }
			    this.search();
			},

			/**
			 * 排序
			 */
			set_sort(o) {
			    this.query.orderby = o.value;
			    this.search();
			},
			/**
       * 获取列表后
       * @param {Object} json
       * @param {Object} func
       */
			get_list_after(json ,func){
        let list = json.result.list;
        for (let i = 0 ; i < list.length; i++){
          let obj = {};
          obj.music_name = list[i].song_name
          obj.singer = list[i].singers_name
          obj.audio_frequency = list[i].song_file
          obj.cover = list[i].cover_photo
          this.player_list.push(obj)
        }
        if (func) {
          func(json);
        }
      },
      /**
			 * 获取歌曲分类列表
			 */
			async get_list_song_classification() {
				var json = await this.$get("~/api/song_classification/get_list?");
				if (json.result) {
					this.list_song_classification = json.result.list;
				} else if (json.error) {
					console.log(json.error);
				}
			},
      /**
       * 重置
			 */
			reset() {
        this.query.song_name = ""
        this.query.singers_name = ""
        this.query.song_classification = ""
				this.search();
			},

			// 返回条数
			toSize(i){
				this.query.size = i;
				this.first();
			},

			// 返回页数
			toPage(i){
				this.query.page = i;
				this.first();
			},

      goToPage(v){
        this.query.page = v;
        this.goToNew(v)
      },
		},
		computed: {},
		created() {
			this.get_list_song_classification();
    }
	}
</script>

<style>
</style>
