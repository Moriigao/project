<template>
	<el-main class="bg edit_wrap comtable_e">
		<el-form ref="form" :model="form" status-icon label-width="120px">
		<el-row class="row_ce">
      <el-col v-if="$check_field('get','song_name') || $check_field('add','song_name') || $check_field('set','song_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌曲名称" prop="song_name">
          <el-input id="song_name" v-model="form['song_name']" placeholder="请输入歌曲名称"
							  v-if="(form['music_library_id'] && $check_field('set','song_name')) || (!form['music_library_id'] && $check_field('add','song_name'))" :disabled="disabledObj['song_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','song_name')">{{form['song_name']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','singers_name') || $check_field('add','singers_name') || $check_field('set','singers_name')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌手姓名" prop="singers_name">
          <el-input id="singers_name" v-model="form['singers_name']" placeholder="请输入歌手姓名"
							  v-if="(form['music_library_id'] && $check_field('set','singers_name')) || (!form['music_library_id'] && $check_field('add','singers_name'))" :disabled="disabledObj['singers_name_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','singers_name')">{{form['singers_name']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','song_classification') || $check_field('add','song_classification') || $check_field('set','song_classification')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌曲分类" prop="song_classification">
          <el-select id="song_classification" v-model="form['song_classification']"	v-if="(form['music_library_id'] && $check_field('set','song_classification')) || (!form['music_library_id'] && $check_field('add','song_classification'))">
						<el-option v-for="o in list_song_classification" :key="o['song_classification']" :label="o['song_classification']" :value="o['song_classification']"></el-option>
					</el-select>
          <div v-else-if="$check_field('get','song_classification')">{{form['song_classification']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','album_title') || $check_field('add','album_title') || $check_field('set','album_title')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="专辑名称" prop="album_title">
          <el-input id="album_title" v-model="form['album_title']" placeholder="请输入专辑名称"
							  v-if="(form['music_library_id'] && $check_field('set','album_title')) || (!form['music_library_id'] && $check_field('add','album_title'))" :disabled="disabledObj['album_title_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','album_title')">{{form['album_title']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','song_file') || $check_field('add','song_file') || $check_field('set','song_file')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌曲文件" prop="song_file">
          <el-upload v-if="(form['music_library_id'] && $check_field('set','song_file')) || (!form['music_library_id'] && $check_field('add','song_file'))" class="upload-demo" drag
						action="" style="max-width: 300px;width: 100%;" :http-request="upload_song_file" :limit="1" accept="audio/ogg,audio/mp3,audio/wav">
						<i class="el-icon-upload"></i>
						<div class="el-upload__text">将音频拖到此处，或<em>点击上传</em></div>
					</el-upload>
					<div v-else-if="$check_field('get','song_file')">
						<el-button type="primary" @click="download(form['song_file'])">下载<i class="el-icon-download el-icon--right"></i></el-button>
					</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','release_date') || $check_field('add','release_date') || $check_field('set','release_date')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="发布日期" prop="release_date">
          <el-date-picker :disabled="disabledObj['release_date_isDisabled']" v-if="(form['music_library_id'] && $check_field('set','release_date')) || (!form['music_library_id'] && $check_field('add','release_date'))" id="release_date"
						v-model="form['release_date']" type="date" placeholder="选择日期" value-format="yyyy-MM-dd">
					</el-date-picker>
					<div v-else-if="$check_field('get','release_date')">{{form['release_date']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','cover_photo') || $check_field('add','cover_photo') || $check_field('set','cover_photo')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="封面图片" prop="cover_photo">
          <el-upload :disabled="disabledObj['cover_photo_isDisabled']" class="avatar-uploader" drag
						accept="image/gif, image/jpeg, image/png, image/jpg" action="" :http-request="upload_cover_photo"
						:show-file-list="false" v-if="(form['music_library_id'] && $check_field('set','cover_photo')) || (!form['music_library_id'] && $check_field('add','cover_photo'))">
            <img id="cover_photo" v-if="form['cover_photo']" :src="$fullUrl(form['cover_photo'])" class="avatar">
						<i v-else class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
					<el-image v-else-if="$check_field('get','cover_photo')" style="width: 100px; height: 100px"
						:src="$fullUrl(form['cover_photo'])" :preview-src-list="[$fullUrl(form['cover_photo'])]">
						<div slot="error" class="image-slot">
							<img src="../../../public/img/error.png" style="width: 90px; height: 90px" />
						</div>
					</el-image>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','song_lyrics') || $check_field('add','song_lyrics') || $check_field('set','song_lyrics')" :xs="24" :sm="12" :lg="8" class="el_form_item_warp">
        <el-form-item label="歌曲歌词" prop="song_lyrics">
          <el-input type="textarea" id="song_lyrics" v-model="form['song_lyrics']" placeholder="请输入歌曲歌词"
						v-if="(form['music_library_id'] && $check_field('set','song_lyrics')) || (!form['music_library_id'] && $check_field('add','song_lyrics'))" :disabled="disabledObj['song_lyrics_isDisabled']"></el-input>
					<div v-else-if="$check_field('get','song_lyrics')">{{form['song_lyrics']}}</div>
        </el-form-item>
			</el-col>

      <el-col v-if="$check_field('get','song_introduction') || $check_field('add','song_introduction') || $check_field('set','song_introduction')" :xs="24" :sm="24" :lg="24" class="el_form_editor_warp">
        <el-form-item label="歌曲介绍" prop="song_introduction">
          <quill-editor v-model.number="form['song_introduction']"
						v-if="(form['music_library_id'] && $check_field('set','song_introduction')) || (!form['music_library_id'] && $check_field('add','song_introduction')) ">
					</quill-editor>
					<div v-else-if="$check_field('get','song_introduction')" v-html="form['song_introduction']"></div>
				</el-form-item>
			</el-col>
		</el-row>

			<el-col :xs="24" :sm="12" :lg="8" class="el_form_btn_warp">
				<el-form-item v-if="$check_action('/music_library/view','set') || $check_action('/music_library/view','add') || $check_option('/music_library/table','examine')">
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
				field: "music_library_id",
				url_add: "~/api/music_library/add?",
				url_set: "~/api/music_library/set?",
				url_get_obj: "~/api/music_library/get_obj?",
				url_upload: "~/api/music_library/upload?",

				query: {
					"music_library_id": 0,
				},

				form: {
          "song_name":  '', // 歌曲名称
          "singers_name":  '', // 歌手姓名
          "song_classification":  '', // 歌曲分类
          "album_title":  '', // 专辑名称
          "song_file":  '', // 歌曲文件
          "release_date":  '', // 发布日期
          "cover_photo":  '', // 封面图片
          "song_lyrics":  '', // 歌曲歌词
          "song_introduction":  '', // 歌曲介绍
          "music_library_id": 0, // ID
        },
				disabledObj:{
          "song_name_isDisabled": false,
          "singers_name_isDisabled": false,
          "song_classification_isDisabled": false,
          "album_title_isDisabled": false,
          "song_file_isDisabled": false,
          "release_date_isDisabled": false,
          "cover_photo_isDisabled": false,
          "song_lyrics_isDisabled": false,
          "song_introduction_isDisabled": false,
        },
        // 歌曲分类选项列表
				list_song_classification: [""],
			}
		},
		methods: {
      /**
			 * 获取歌曲分类列表
			 */
			async get_list_song_classification() {
				var json = await this.$get("~/api/song_classification/get_list?");
				if(json.result && json.result.list){
					this.list_song_classification = json.result.list;
				}
				else if(json.error){
					console.error(json.error);
				}
			},
			/**
			 * 上传歌曲文件
			 * @param {Object} param 音频参数
			 */
			upload_song_file(param){
						this.uploadFile(param.file, "song_file");
      },
			/**
			 * 上传封面图片
			 * @param {Object} param 图片参数
			 */
			upload_cover_photo(param){
        this.uploadFile(param.file, "cover_photo");
      },
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
        if (this.form["release_date"] && this.form["release_date"].indexOf("-")===-1){
          this.form["release_date"] = this.$toTime(parseInt(this.form["release_date"]),"yyyy-MM-dd")
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
        if(this.form["release_date"]=="0000-00-00"){
				  this.form["release_date"] = null;
				}
				if(parseInt(this.form["release_date"]) > 9999){
					this.form["release_date"] = this.$toTime(parseInt(this.form["release_date"]),"yyyy-MM-dd")
				}
			},
			/**
			 * 提交前验证事件
			 * @param {Object} 请求参数
			 * @return {String} 验证成功返回null, 失败返回错误提示
			 */
      submit_check(param) {
        if (!param.song_file){
					return "歌曲文件不能为空";
				}
        if (!param.song_name){
          return "歌曲名称不能为空";
        }
        if (!param.song_classification){
          return "歌曲分类不能为空";
        }
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
      this.get_list_song_classification();
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
