<template>
  <div class="page_message" id="message_index">
    <div class="warp">
      <div class="container">
        <div class="row">
          <div class="col">
            <div class="card_form_message flex_cc">
              <!-- 留言回复 -->
              <div class="form_message">
                <b-form>
                  <b-form-group id="input-group-2" label="标题:" label-for="input-2" :state="validation_title" invalid-feedback="不能为空" valid-feedback="校验通过">
                    <b-form-input id="input-2" v-model="form.title" type="text" placeholder="请输入标题" trim></b-form-input>
                  </b-form-group>
                  <b-form-group id="input-group-1" label="用户名:" label-for="input-1">
                    <b-form-input id="input-1" v-model="form.nickname" type="text" placeholder="用户名" trim :disabled="disabledObj.nickname_disabled"></b-form-input>
                  </b-form-group>

                  <b-form-group id="input-group-2" label="留言内容:" label-for="input-2" :state="validation_content" invalid-feedback="最多12个字符" valid-feedback="">
                    <b-form-textarea id="textarea" v-model="form.content" placeholder="请输入你的留言..." rows="3" max-rows="6"></b-form-textarea>
                  </b-form-group>
                  <div class="btns_bottom">
                    <div class="btn_item" @click="submit()"><span>提交留言</span></div>
                  </div>
                </b-form>
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
      form: {
        title:"",
        nickname: "",
        content: "",
        user_id: 0,
        avatar:""
      },
      disabledObj: {
        nickname_disabled: false,
      },
    };
  },
  created() {
    setTimeout(() => {
      this.get_user()
    }, 500)
  },
  methods: {
    get_user(){
      let user = this.$store.state.user;
      if (user && user.user_id){
        this.form.nickname = user.nickname;
        this.form.user_id = user.user_id;
        this.form.avatar = user.avatar;
        this.disabledObj.nickname_disabled = true;
      }
    },
    /**
     * 提交留言
     */
    async submit() {
      var condition = this.validation_title && this.form.content;
      if (!condition) {
        this.$toast("输入不合法");
        return;
      }

      var sensitiveWords = await this.filterSensitiveWords(this.form.content)
      if(sensitiveWords.length > 0){
        this.$toast('当前消息中包含敏感词: ' + sensitiveWords.join(','))
        return
      }

      this.$post("~/api/message/add?", this.form, (res) => {
        if (res.result) {
          this.$router.push("/message/list");
        } else if (res.error) {
          this.$toast(res.error.message);
        }
      });
    },
  },
  computed: {
    /**
     * 验证用户名
     */
    validation_title() {
      var length = this.form.title.length;
      return length > 0;
    },
    /**
     * 验证留言内容
     */
    validation_content() {
      var length = this.form.content.length;
      if (!length) {
        return true;
      }
      return length > 1 && length < 580;
    },
  },
};
</script>

<style scoped>
.form_message {
  margin: 5px;
  padding-top: 10px;
}

.textarea {
  border: 1px solid rgb(229, 229, 229);
  background-color: rgb(255, 255, 255);
  width: calc(100% - 1rem);
  border-radius: 0.25rem;
  padding: 0.5rem;
  font-size: 0.875rem;
}

.btns_bottom .btn_item {
  cursor: pointer;
  padding: 5px;
  margin: 5px;
  width: 80px;
  height: 30px;
  border: 1px solid var(--color_default_b);
  border-radius: 2px;
  line-height: 18px;
}
.btns_bottom .btn_item:hover {
  background: var(--color_primary);
  border: 1px solid var(--color_primary);
  font-weight: bold;
}
</style>
