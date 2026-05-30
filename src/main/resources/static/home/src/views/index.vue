<template>
	<div class="page_root" id="root_demo">
		<div class="warp banner">
		    <div class="container swiper_box">
		        <div class="row">
		            <div class="col-12">
		                <div class="sw_no">
		                    <!-- 轮播图组件 -->
		                    <div class="swiper_img">
		                        <swiper_img :list="list_slide"/>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>

		<div class="warp article_s">
		    <!-- 容器 -->
		    <div class="container">
		        <div class="row">
		            <div class="col-12">
		                <!-- 标题栏组件 -->
		                <div class="article_recommend">
		                    <div class="title">
		                        <span> 音乐资讯 </span>
		                    </div>
							          <div class="text_t"><span></span></div>
		                    <div class="more_box">
		                        <router-link to="/article/list" class="art_more more">
		                            <span class="mor">更多</span>
		                        </router-link>
		                    </div>
		                </div>
						        <div class="list_article_box">
						            <div class="Left_box">
								        <span class="i"></span>
								        <span class="i"></span>
								        <span class="i"></span>
                        </div>
							          <!-- 音乐资讯列表组件 -->
							          <list_article v-if="show_list_article" :list="list_article" />
							          <div class="overflow-auto table_article" >
								            <table id="list_article" role="table" aria-busy="false" :aria-colcount="fields_article.length" class="table b-table table-striped table-hover">
									              <thead>
                                <!--循环 fields_article 数组（应该是表格字段的定义），为每个字段动态生成一个 <th> 元素-->
									                  <tr><th v-for="(o,i) in fields_article" :key="i">{{o.label}}</th></tr>
									              </thead>
									              <tbody>
									                  <tr v-for="(o, i) in list_table_article" :key="i">
										                    <td v-for="(oj,n) in fields_article" @click="to_details('article',o)">
											                      <img v-if="oj.type && oj.type == '图片' " :src="$fullUrl(o[oj.key])" alt="" v-default-img="'../../public/img/default.png'">
											                      <span v-else-if="oj.key === 'create_time'">{{ o[oj.key] | formatDate }}</span>
											                      <!--对于其他字段，直接显示 o[oj.key] 的值。-->
                                            <span v-else>{{ o[oj.key] }}</span>
										                    </td>
									                  </tr>
									              </tbody>
								            </table>
							          </div>
							          <div class="right_box">
								            <span class="i"></span>
								            <span class="i"></span>
								            <span class="i"></span>
                        </div>
						        </div>
		            </div>
		        </div>
		    </div>
		</div>

		<!-- 推荐音乐乐库模块(开始) -->
		<div class="ins warp model1" v-if="$check_action('/music_library/list', 'get')">
		    <div class="container">
		        <div class="row">
		            <div class="col-12">
		                <!-- 标题栏组件 -->
		                <div class="diy_recommend">
		                    <div class="title">
		                        <span> 音乐乐库推荐 </span>
		                    </div>
							          <div class="text_t"><span></span></div>
		                    <div class="more_box">
		                        <router-link to="/music_library/list" class="ins_more more">
		                            <span calss="mor">更多</span>
		                        </router-link>
		                    </div>
		                </div>
		                <!-- 音乐乐库推荐列表组件 -->
                    <div class="list_music_library_box" ins_box>
							          <div class="Left_box">
								            <span class="i"></span>
								            <span class="i"></span>
								            <span class="i"></span>
                        </div>
							          <list_music_library :list="list_music_library" />
							          <div class="right_box">
								            <span class="i"></span>
								            <span class="i"></span>
								            <span class="i"></span>
                        </div>
						        </div>
		            </div>
		        </div>
		    </div>
		</div>
	</div>
</template>

<script>
	import mixin from "@/mixins/page.js";
  import list_music_library from "@/components/diy/list_music_library.vue";
  import bar_title from "@/components/diy/bar_title.vue";
	import list_article from "@/components/diy/list_article.vue";
	import swiper_img from "@/components/diy/swiper_img.vue";

	export default {
		mixins: [mixin],
		components: {
      list_music_library,
      bar_title,
			list_article,
			swiper_img,
		},
		data() {
			return {
				isSmall: false,
				sendValue: "",
				showChat: false,
				isAdmin: false,
				token:"",
				// 资讯模型数组
				list_article: [],
				vm_arr_article: [
					"article_id",
					"img",
					"title",
					"praise_len",
					"hits",
					"type",
					"create_time",
				],
				fields_article: [
					{key: "img", label: "图片", type: "图片"},
					{key: "title", label: "标题", type: "文本"},
					{key: "praise_len", label: "点赞数", type: "文本"},
					{key: "hits", label: "点击量", type: "文本"},
					{key: "type", label: "分类", type: "文本"},
					{key: "create_time", label: "发布时间", type: "文本"},
				],
				show_list_article: true,
        list_music_library: [],
        list_slide: [],
				list_menu: [],
			};
		},
		created(){
			this.get_token();
		},
		methods: {
      //从当前页面的 URL 获取所有查询参数并返回指定token的值
			getHashParams() {
				let key = "token";
				// 获取所有参数
				let query = window.location.search.substring(1);  //获取当前页面 URL 中的查询字符串
				let hash = window.location.hash.substring(1);
				// 如果锚点后面有参数，把锚点后面的参数加入到search参数中
				if(hash.indexOf("?") > -1){
					query += "&" + hash.split("?")[1];
				}
				let key_values = query.split("&");
				let params = {};
				// 遍历参数并存入params对象
				key_values.map(function (key_val){
					let key_val_arr = key_val.split("=");
					params[key_val_arr[0]] = key_val_arr[1];
				});
				// 如果找到了key对应的参数，返回对应值
				if(typeof params[key]!="undefined"){
					return params[key];
				}
				// 如果没找到，返回空字符串
				return "";
			},
			get_token(){
				let token = this.getHashParams();
				console.log(token)
				if (token){
          //将 token 存储到某个本地存储
					$.db.set("token",token,120);
          //更新当前页面的 URL，移除查询参数中的 token 部分
					let new_href = window.location.href.replaceAll("?token="+encodeURIComponent(token));
					location.href = new_href
				}
			},
      // 获取音乐乐库列表
			get_music_library() {
				let url = "~/api/music_library/get_hits_list?";
        let param = {
          "user_id": this.$store.state.user.user_id,
          "page": 1,
					"size": 12
				}
        this.$get(url, param, (json) => {
					if (json.result) {
            this.list_music_library = json.result.list;
					}
				})
			},
			// 获取轮播图
			get_slides() {
			    this.$get("~/api/slides/get_list?", {}, (json) => {
			        if (json.result) {
                this.list_slide = json.result.list;
			        }
			    });
			},
			// 获取导航栏
			get_menu() {
				var user_group = this.user_group;
			    this.$get(
			        "~/api/auth/get_list?",
			        {
                page: "",
                user_group,
                get: 1,
                position: "top"
			        },
			        (json) => {
                if (json.result) {
                  this.list_menu = json.result.list;
                }
			        }
			    );
			},
			// 获取资讯
			get_article() {
			    this.$get(
			        "~/api/article/get_list?",
			        {
			            page: 1,
			            size: 6,
			        },
			        (json) => {
			            if (json.result) {
			                var list_article = json.result.list;
			                this.list_article = list_article;
			            }
			        }
			    );
			},
			to_details(key,o,id) {
				if(!id){
					id = key + "_id";
				}
			    this.$router.push('/' + key + '/details?' + id + '=' + o[id]);
			}
		},
		mounted() {   //生命周期钩子,在组件挂载时，调用多个方法来初始化数据
      this.get_music_library();
      this.get_menu();
			this.get_slides();
			this.get_article();
		},
		computed: {
      list_table_song_classification() {
        var list = this.list_music_library;
        var list_table = [];
        for (let i = 0; i < list.length; i++) {
          list_table[i] = {};
          this.vm_arr_music_library.map((o) => {
            // 第二个中括号是对象的属性
            list_table[i][o] = list[i][o] || "";
          });
        }
        return list_table;
      },

      list_table_article() {
        var list = this.list_article;
        var list_table = [];
        for (let i = 0; i < list.length; i++) {
          list_table[i] = {};
          this.vm_arr_article.map((o) => {
            // 第二个中括号是对象的属性
            list_table[i][o] = list[i][o];
          });
        }
        return list_table;
      },
		}
	};
</script>

<style scoped>
  .page_root{
	  position: relative;
  }

  .support_module .item.item-center span{
    font-size: 12px;
    padding: 2px 4px;
    color: #fff;
    background-color: #dadada;
    border-radius: 3px;
    -moz-user-select:none; /*火狐*/
    -webkit-user-select:none; /*webkit浏览器*/
    -ms-user-select:none; /*IE10*/
    -khtml-user-select:none; /*早期浏览器*/
    user-select:none;
  }

  .support_module .avatar img{
    width: 42px;
    height: 42px;
    border-radius: 50%;
  }

  .support_module textarea{
    flex: 1;
    padding: 10px;
    font-size: 14px;
    border: none;
    overflow-y: auto;
    overflow-x: hidden;
    outline:none;
    resize:none;
    width: 102%;
    height: 100%;
    min-height: 100%;
    max-height: 100%;
  }

  .support_module .button-area button{
    width: 80px;
    height: 40px;
    line-height: 40px;
    border: none;
    outline: none;
    border-radius: 4px;
    float: right;
    cursor: pointer;
    background: #9eea6a;
    color: #333;
    margin: 0px;
  }

  /* 设置滚动条的样式 */
  ::-webkit-scrollbar {
    width:10px;
  }
  /* 滚动槽 */
  ::-webkit-scrollbar-track {
    -webkit-box-shadow:inset 0 0 6px rgba(0,0,0,0.3);
    border-radius:8px;
  }
  /* 滚动条滑块 */
  ::-webkit-scrollbar-thumb {
    border-radius:10px;
    background:rgba(0,0,0,0);
    -webkit-box-shadow:inset 0 0 6px rgba(0,0,0,0.5);
  }

	.sw_no {
	    margin: 0.5rem;
	    display: flex;
	    flex-wrap: wrap;
	}

	.swiper_img {
	    width: 60%;
	    height: 18.75rem;
	}

  .article_recommend > * {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}

	.article_recommend {
	    display: block;
	    margin: 1.5rem 0.5rem;
	    height: 4rem;
	    border-radius: 0.5rem;
	    background-color: var(--color_primary);
	}

	.diy_recommend > * {
	    display: flex;
	    justify-content: center;
	    align-items: center;
	}

	.diy_recommend {
	    display: block;
	    margin: 1.5rem 0.5rem;
	    height: 4rem;
	    border-radius: 0.5rem;
	    background-color: var(--color_primary);
	}

	.title {
	    float: left;
	    height: 4rem;
	    color: white;
	    font-size: 1.5rem;
	    font-weight: bold;
	    padding-left: 1rem;
	}

	.more_box {
	    float: right;
	    height: 4rem;
	    color: white;
	    font-size: 1.5rem;
	    padding-right: 1rem;
	}

	.more_box .more {
	    border-radius: 0.5rem;
	    border: 0.125rem solid white;
	    display: flex;
	    align-items: center;
	    font-size: 1.2rem;
	    justify-content: center;
	    height: 3rem;
	    width: 3.5rem;
	}

	.overflow-auto .table th,
	.overflow-auto .table td {
	    text-align: center;
	    text-wrap: normal;
	    font-size: 1rem;
	    vertical-align: middle;
	}

	@media (max-width: 996px) {
	    .swiper_img {
	        width: 100%;
	        height: 12.5rem;
	    }

	    .card_notice {
	        width: 100%;
	        padding-left: 0;
	    }
	}
</style>
