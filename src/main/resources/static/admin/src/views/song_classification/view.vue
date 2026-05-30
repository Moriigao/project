<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px">
		<el-row class="row_ce">
      <el-col v-if="$check_field('get','song_classification') || $check_field('add','song_classification') || $check_field('set','song_classification')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌曲分类" prop="song_classification">
          <el-input id="song_classification" v-model="form['song_classification']" placeholder="请输入歌曲分类"
							  v-if="(form['song_classification_id'] && $check_field('set','song_classification')) || (!form['song_classification_id'] && $check_field('add','song_classification'))" :disabled="disabledObj['song_classification_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','song_classification')">{{form['song_classification']}}</div>
        </el-form-item>
			</el-col>
		</el-row>

      <el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/song_classification/view','set') || $check_action('/song_classification/view','add') || $check_option('/song_classification/table','examine')">
					<el-button type="primary" @click="submit()">提交</el-button>
					<el-button @click="cancel()">取消</el-button>
				</el-form-item>
				<el-form-item v-else>
					<el-button @click="cancel()">返回</el-button>
				</el-form-item>
			</el-col>

		</el-form>
	</el-main>
</template>

<script>
	import mixin from "@/mixins/page.js";
	export default {
		mixins: [mixin],
		data() {
			return {
				field: "song_classification_id",
				url_add: "~/api/song_classification/add?",
				url_set: "~/api/song_classification/set?",
				url_get_obj: "~/api/song_classification/get_obj?",
				url_upload: "~/api/song_classification/upload?",

				query: {
					"song_classification_id": 0,
				},

				form: {
          "song_classification":  '', // 歌曲分类
          "song_classification_id": 0, // ID
        },
				disabledObj:{
          "song_classification_isDisabled": false,
        },
			}
		},
		methods: {
			/**
			 * 获取对象之前
			 * @param {Object} param
			 */
			get_obj_before(param) {
				var form = "";

				if(this.form && form){
					Object.keys(this.form).forEach(key => {
						Object.keys(form).forEach(dbKey => {
							if(key === dbKey){
								this.disabledObj[key+'_isDisabled'] = true;
							}
							if(dbKey === "source_table"){
								this.form['source_table'] = form[dbKey];
							}
							if(dbKey === "source_id"){
								this.form['source_id'] = form[dbKey];
							}
							if(dbKey === "source_user_id"){
								this.form['source_user_id'] = form[dbKey];
							}
						})
					})
				}
        $.db.del("form");

				return param;
			},

			/**
			 * 获取对象之后
			 * @param {Object} json
			 * @param {Object} func
			 */
			get_obj_after(json, func){
			},

			/**
			 * 提交前验证事件
			 * @param {Object} 请求参数
			 * @return {String} 验证成功返回null, 失败返回错误提示
			 */
      submit_check(param) {
        return null;
			},
			/**
			 * 上传文件
			 * @param {Object} param
			 */
			uploadimg(param) {
				this.uploadFile(param.file, "avatar");
			},

		},
		created() {
    },
	}
</script>

<style>
	.avatar-uploader .el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}

	.avatar-uploader .el-upload:hover {
		border-color: #409EFF;
	}

	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 178px;
		height: 178px;
		line-height: 178px;
		text-align: center;
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}

</style>
