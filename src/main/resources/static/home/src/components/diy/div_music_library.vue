<template>
	<div class="diy_details_box diy_div_music_library">
		<div class="warp diy_warp">
			<div class="container">
					<div class="details_title">详情</div>
				<div class="row row_det">
          <!--根据用户是否登录和用户对该页面的访问权限显示点赞和收藏按钮-->
						<div v-if="this.$store.state.user.user_id" class="btns_add col-12 col-sm-6">
              <!-- 点赞按钮 -->
							<div v-if="$check_action('/music_library/details','set')">
								<div v-if="state_praise" class="praise_btn praise_change" @click="change_praise()">
									<b-icon icon="hand-thumbs-up" class="icon icon_change"></b-icon><span class="word_change">点赞</span>
								</div>
								<div v-else class="praise_btn" @click="change_praise()">
									<b-icon icon="hand-thumbs-up" class="icon"></b-icon>
									<span>点赞</span>
								</div>
							</div>
              <!-- 收藏按钮 -->
							<div v-if="$check_action('/music_library/details','set')">
								<div v-if="state_collect" class="collect_btn collect_change" @click="change_collect()">
									<b-icon icon="heart-fill" class="icon icon_change"></b-icon>
									<span class="word_change">收藏</span>
								</div>
								<div v-else class="collect_btn" @click="change_collect()">
									<b-icon icon="heart" class="icon"></b-icon>
									<span>收藏</span>
								</div>
							</div>
						</div>
						<div class="row_top_wrap">
						  <!-- 图片 -->
							<div class="row_1" v-if="imgList.length">
									<div class="diy_img" v-for="(item,index) in imgList" :key="item+index">
									<img :src="$fullUrl(obj[item.name])" />
								  </div>
							</div>
							<!-- 内容 -->
							<div class="row_2" :class="{flex_row2: !imgList.length}">
							<div class="row_cont" :class="{flex_row2_item: !imgList.length}" v-for="(item,index) in itemList" :key="item+index" v-show="$check_field('get',item.name)">
								<div class="view">
								<div class="diy_title">
									<span>{{item.title}}:</span>
								</div>

								<div class="diy_field diy_uid" :class="{flex_row2_item_text:imgList.length}" v-if="item.type == 'UID'">
									<span>
										{{ get_user_info(item.name,obj[item.name]) }}
									</span>
								</div>
								<div class="diy_field diy_music" :class="{flex_row2_item_text:imgList.length}" v-else-if="item.type === '音频'">
									<audio v-if="obj[item.name]" style="text-align: left" :src="$fullUrl(obj[item.name])" controls></audio>
								</div>
								<div class="diy_field diy_music" :class="{flex_row2_item_text:imgList.length}" v-else-if="item.type === '文件'">
									<a :href="$fullUrl(obj[item.name])" target="_blank" style="color: rgb(64, 158, 255);">点击下载</a>
								</div>
								<div class="diy_field diy_date" :class="{flex_row2_item_text:imgList.length}" v-else-if="item.type === '日期' || item.type === '日后'">
									<span>
										{{ $toTime(obj[item.name] ,"yyyy-MM-dd") }}
									</span>
								</div>
								<div class="diy_field diy_number" :class="{flex_row2_item_text:imgList.length}" v-else-if="item.type === '数字'">
									<span>
										{{ obj[item.name] }}
									</span>
								</div>
								<div class="diy_field diy_desc" :class="{flex_row2_item_text:imgList.length}" v-else-if="item.type === '多文本'">
									<div>
										{{ obj[item.name] }}
									</div>
								</div>

								<div class="diy_field diy_text" :class="{flex_row2_item_text:imgList.length}" v-else>
									<span>
										{{ obj[item.name] }}
									</span>
								</div>
							</div>
						  </div>
              <div class="view praise_wrap">
								<span class="diy_title">点赞数：</span>
                <span class="diy_field diy_text">
									{{ praiseLen }}
                </span>
              </div>
              <div class="view praise_wrap">
								<span class="diy_title">点击数：</span>
                <span class="diy_field diy_text">
									{{ hitsLen }}
								</span>
              </div>
            </div>
					</div>

			    <div class="details_btn_wrap"></div>
			    <!-- 富文本 -->
			    <div class="rich_text" v-for="(item,index) in richList" :key="item+index" v-show="$check_field('get',item.name)">
						  <div class="view">
						    <div class="diy_title">
							    <span v-text="item.title"></span>
						    </div>
							  <div class="diy_html" v-html="obj[item.name]" ></div>
						  </div>
			    </div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import mixinPage from "@/mixins/page.js";

	export default {
		mixins: [mixinPage],
		components: {

		},
		props: {
			obj: {
				type: Object,
				default: function() {
					return {};
				},
			}
		},
		data() {
			return {
				praiseLen: this.obj["praise_len"],
				hitsLen: this.obj["hits"],
				imgList: [
						{
							title: "封面图片",
							name: "cover_photo",
							type: "图片"
						},
				],
				itemList: [
						{
							title: "歌曲名称",
							name: "song_name",
							type: "文本"
						},
						{
							title: "歌手姓名",
							name: "singers_name",
							type: "文本"
						},
						{
							title: "歌曲分类",
							name: "song_classification",
							type: "文本"
						},
						{
							title: "专辑名称",
							name: "album_title",
							type: "文本"
						},
						{
							title: "歌曲文件",
							name: "song_file",
							type: "音频"
						},
						{
							title: "发布日期",
							name: "release_date",
							type: "日期"
						},
				],
				richList: [
						{
							title: "歌曲歌词",
							name: "song_lyrics",
							type: "多文本"
						},
						{
							title: "歌曲介绍",
							name: "song_introduction",
							type: "多文本"
						},
				],
        state_praise:false,
				state_collect:false,
			};
		},
		methods: {
      get_user_info(name,id){
				var obj = null;
        var ret = "";
				if(obj){
					ret = obj.nickname+"-"+obj.username;
				}
				return ret;
			},
			/**
			 * 改变点赞数
			 */
			change_praise(obj) {
				var user_id = this.user.user_id;

				var query = {
					source_table: "music_library",
					source_field: "music_library_id",
					source_id: this.obj["music_library_id"],
					user_id
				};

				var _this = this;

				// 点赞状态
				if (this.state_praise) {
					this.state_praise = false;
					this.$get('~/api/praise/del?', query, (res) => {
						if(res.result){
              _this.praiseLen = parseInt(_this.praiseLen)-1;
              var praise_len = _this.praiseLen;
							this.$post('~/api/music_library/set?music_library_id=' + _this.obj["music_library_id"], {
								praise_len
							}, (res) => {
								if(res.result){
									console.log("添加点赞数状态：" ,res.result);
								}
								else if(res.error){
									console.error(res.error);
								}
							});
							this.$message.success("取消点赞")
						}
						else if (res.error){
							this.$toast(res.error.message);
							console.error(res.error);
						}
					});
				} else {
					this.state_praise = true;
					this.$post('~/api/praise/add?', query, (res) => {
						if (res.result) {
              _this.praiseLen = parseInt(_this.praiseLen)+1;
              var praise_len = _this.praiseLen;
							this.$post('~/api/music_library/set?music_library_id=' + _this.obj["music_library_id"], {
								praise_len
							}, (res) => {
								if(res.result){
									console.log("添加点赞数状态：" ,res.result);
								}
								else if(res.error){
									console.error(res.error);
								}
							});
							// this.$toast("点赞成功");
							this.$message.success("点赞成功")
						}
						else if (res.error) {
							this.$toast(res.error.message);
							console.error(res.error);
						}
					});
				};
			},
      /**
       * 获取点赞
       */
      get_praise() {
        var user_id = this.$store.state.user.user_id;
        this.$get("~/api/praise/count?", {
          source_table: "music_library",
          source_field: "music_library_id",
          source_id: this.obj.music_library_id,
          user_id
        }, (res) => {
          if (res.result || res.result === 0) {
            console.log("sadsfasf"+JSON.stringify(res))
            this.state_praise = res.result ? true : false;
            console.log("点赞状态：" ,this.state_praise);
          }
          else if (res.error){
            this.$toast(res.error.message);
            console.error(res.error);
          }
        })
      },

      /**
			 * 改变收藏状态
			 */
			change_collect() {
				var user_id = this.user.user_id;

				var query = {
					source_table: "music_library",
					source_field: "music_library_id",
					source_id: this.obj["music_library_id"],
					user_id
				};

				// 收藏状态
				if (this.state_collect) {
					this.state_collect = false;
					this.$get('~/api/collect/del?', query, (res) => {
						if(res.result){
							// this.$toast("取消收藏");
							this.$message.success("取消收藏")
						}
						else if (res.error){
							this.$toast(res.error.message);
							console.error(res.error);
						}
					});
				} else {
					this.state_collect = true;
          query.title = this.obj.song_name
          query.img = this.obj.cover_photo
          this.$post('~/api/collect/add?', query, (res) => {
						if (res.result) {
							// this.$toast("收藏成功");
							this.$message.success("收藏成功")
						}
						else if (res.error) {
							this.$toast(res.error.message);
							console.error(res.error);
						}
					});
				};
			},
      check_collect() {
        var user_id = this.$store.state.user.user_id;
        var music_library_id = this.obj.music_library_id;
        this.$get('~/api/collect/count?', {
          user_id,
          source_table: "music_library",
          source_field: "music_library_id",
          source_id: music_library_id
        }, (res) => {
          this.state_collect = res.result
        });
      },

	  },
		created() {
      setTimeout(() => {
        this.get_praise();
        this.check_collect();
      }, 1000);
		},
		watch:{
			obj:{
				deep: true,
				handler(){
					this.praiseLen = this.obj["praise_len"]
					this.hitsLen = this.obj["hits"]
        }
			}
		},
	};
</script>

<style>
.collect_btn,
.praise_btn {
	margin-right: 0.5rem;
	display: flex;
	white-space: nowrap;
	padding: 0.125rem 0.5rem;
	border-radius: 0.25rem;
	color: var(--color_base);
	border: 1px solid var(--color_default);
	cursor: pointer;
}

.icon_change {
	color: var(--color_base);
}

.collect_btn:hover {
	border: 1px solid var(--color_base);
	color: var(color_base);
}

.praise_btn:hover {
	border: 1px solid var(color_base);
	color: var(--color_base);
}
.icon {
	font-size: 15px;
	margin: 3px;
}
.word_change {
	color: var(--color_base);
	font-weight: bold;
}
#app .diy_details_box .warp .container .row .btns_add{
	margin-left: 20px;
}
.btns_add {
	display: flex;
	justify-content: flex-start;
	align-items: center;
	flex-wrap: wrap;
	margin: 0 0.625rem 0.625rem 0;
	font-size: 0.875rem;
	-webkit-tap-highlight-color: rgba(0, 0, 0, 0);
	color: rgba(0, 0, 0, 0.3);
}
.btns_add > * {
	display: flex;
	align-items: center;
	height: 2rem;
}
</style>
