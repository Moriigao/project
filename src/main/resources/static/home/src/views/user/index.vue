<template>
  <div class="page_user user_list" id="user_index">
    <div class="warp">
      <div class="container">
        <div class="row justify-content-between">
          <div class="col-12 col-md-9 user_bg">
            <div class="card_default pl-2"  >
              <!--判断 user.user_id 是否存在-->
              <div class="user_block" v-if="user.user_id">
                <div class="img_block">
                  <img style="width: 6rem; height: 6rem" :src="user.avatar ? $fullUrl(user.avatar) : '../../../public/img/default.png'"/>
                </div>
                <div class="nickname">
                  <h4>{{ user.nickname }}</h4>
                </div>
                <div class="user_group">
                  <span>{{ user.user_group }}</span>
                </div>
                <div class="log_out">
                  <b-button class="Personal_data" @click="$router.push('/user/info')">个人资料</b-button>
                  <b-button variant="info" @click="sign_out()">退出登录</b-button>
                </div>
              </div>
              <!--当 user.user_id 不存在时，显示一个登录按钮-->
              <div v-else class="sign_in">
                <b-button class="btn_block" variant="info" @click="$router.push('/account/login')">登录</b-button>
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
export default {
  mixins: [mixin],
  data() {
    return {
    };
  },
  methods: {
    sign_out(){
        this.$store.commit('sign_out');
        location.href = "/";
        location.reload();
    },
  },
  components: {},
};
</script>

<style scoped>
.container {
  min-height: 800px;
}
.user_block {
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  padding: 1rem;
  margin-bottom: 1rem;
}
.user_block > * {
  margin-top: 5px;
}
.img_block img {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 8px rgba(0, 0, 0, 0.04);
}
.nickname {
  color: var(--color_black);
}
.user_group {
  color: var(--color_subtitle);
}
.log_out {
  margin-top: 20px;
  height: 30px;
}
.log_out >*{
  line-height: 20px;
}
.sign_in{
  display: flex;
  flex-direction: column;
  justify-content: start;
  align-items: center;
  padding: 5rem;
  margin-top: 100px;
}
.btn_block{
  width: 90px;
  height: 60px;
}
</style>
