<template>
  <div class="list_menu diy_menu">

    <router-link to="/user_center/index" class="menu_item" :class="{ selected: this.$route.path=== '/user_center/index'}">
      <span class="left_span">个人首页</span>
      <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
    </router-link>

    <router-link to="/message/table" class="menu_item" :class="{ selected: this.$route.path=== '/message/table'}" v-if="$check_action('/message/table','get')">
        <span class="left_span">{{$page_title("/message/table") || 留言反馈}}</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
    </router-link>

    <router-link to="/user/collect" class="menu_item" :class="{ selected: this.$route.path=== '/collect/list'}" v-if="$check_action('/collect/list','get')">
        <span class="left_span">收藏</span>
        <span class="right_span"><b-icon icon="house-fill"></b-icon></span>
    </router-link>

  </div>
</template>

<script>
import mixin from "@/mixins/page.js";
export default {
  mixins: [mixin],
  data() {
    return {
    };
  },
  mounted() {
  },
  methods: {
  },

  components: {
  },
};
</script>

<style scoped>
.list_menu {
  flex: 0 0 25%;
  border-bottom: 1px solid #eee;
}
.menu_item {
  display: flex;
  justify-content: space-between;
  padding: 15px 20px;
  border: 1px solid #eee;
  border-bottom: none;
  background-color: #fff;
}
.menu_item.selected {
  background-color: #000;
}
.menu_item.selected span {
  color: #fff ;
}
.content {
  flex: 0 0 74%;
}
</style>
