<template>
  <div class="page_forum forum_list" id="forum_index">
    <div class="warp" v-if="$check_action('/forum/list','get')">
      <div class="container">
        <div class="left_box">
            <span></span>
            <span></span>
            <span></span>
          </div>
        <div class="row">
          <div class="col-12 col-md-9">
            <div class="card_forum_list">
              <!-- 搜索栏 -->
              <div class="search_box">
                <b-form-input size="sm" class="mr-sm-2" placeholder="局部搜索" v-model.trim="search_val" @keyup.enter="submit_search"></b-form-input>
                <select v-model="query.type" style="width: 200px">
                  <option value="">全部</option>
                  <option v-for="(o,i) in list_forum_type" :value="o">{{o}}</option>
                </select>
                <b-button size="sm" @click="submit_search"><b-icon icon="search"></b-icon></b-button>
                <router-link  style="margin-left: 10px" v-if="$check_action('/forum/list','add')" class="add_forum" to="/forum/edit">
                  <span>发布内容</span>
                </router-link>
              </div>
              <!-- 论坛列表 -->
              <list_forum :list="list"></list_forum>
            </div>
          </div>
        </div>

        <div class="row">
          <!-- 分页器 -->
          <div class="col overflow-auto flex_cc">
            <b-pagination v-model="query.page" :total-rows="count" :per-page="query.size" @change="goToPage"/>
          </div>
        </div>
        <div class="right_box">
            <span></span>
            <span></span>
            <span></span>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
  import mixin from "@/mixins/page.js";
  import list_forum from "@/components/diy/list_forum.vue";

  export default {
    mixins: [mixin],
    components: {
      list_forum,
    },
    data() {
      return {
        url_get_list: "~/api/forum/get_list?like=0",
        list: [],
        query: {
          page: 1,
          size: 8,
          title:"",
          type:"",
		      orderby: "istop desc"
        },
        list_hot: [],
        list_forum_type: [],
        title: "",
        sort_list: [
          {
            name: "热度从高到低",
            value: "`hits` desc",
          },
          {
            name: "热度从低到高",
            value: "`hits` asc",
          },
          {
            name: "创建时间从高到低",
            value: "`create_time` desc",
          },
          {
            name: "创建时间从低到高",
            value: "`create_time` asc",
          },
        ],
        search_val:""
      };
    },
    methods: {
      /**
       *添加论坛类型过滤
       */
      get_forum_type() {
        this.$get(
            "~/api/forum_type/get_list",
            {
            },
            (res) => {
              if (res.result) {
                res.result.list.map((o) => this.list_forum_type.push(o.name));
              }bar_title
            }
        );
      },
      /**
       *  搜索
       * */
      submit_search() {
        this.query.title = this.search_val;
        this.$get(this.url_get_list, this.query, (json) => {
          if (json.result && json.result.list) {
            this.list = json.result.list
            this.count = json.result.count
          }
        })
      },
      goToPage(v){
        this.query.page = v;
        this.goToNew(v)
      }
    },
    mounted() {
      this.get_forum_type();
    },
  };
</script>

<style scoped>
  .flex_cc {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .container {
    min-height: 800px;
  }
  .forum_list h4 {
    font-size: 20px;
  }
  .list_hot {
    flex: 0 0 30%;
    overflow: hidden;
  }
  .list_hot h4 {
    font-size: 20px;
  }
  .filter_block {
    display: flex;
    flex-direction: row;
    justify-content: start;
    margin-bottom: 10px;
  }
  .bar_filter {
    display: flex;
    align-items: center;
  }
  li {
    list-style: none;
  }
  .bar_sort {
    margin-left: 5px;
  }
  .search_box{
    display: flex;
    margin-bottom: 1rem;
  }
  .add_forum{
    display: block;
    background-color: dodgerblue;
    color: white !important;
    width: 100px;
    text-align: center;
    line-height: 30px;
    border-radius: 5px;
  }
</style>
