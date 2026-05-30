<template>
  <div class="y_music_player" ref="music_player" v-if="musicList.length > 0">
    <div class="play_control">
      <i class="el-icon-caret-left" @click="changeMusic('prev')"></i>
      <i class="el-icon-video-pause" v-if="playState" @click="pauseMusic"></i>
      <i class="el-icon-video-play" v-else @click="playMusic"></i>
      <i class="el-icon-caret-right" @click="changeMusic('next')"></i>
      <el-image class="cover_img ml-3" fit="cover" :src="$fullUrl(musicList[playIndex].cover)"></el-image>
    </div>
    <div class="play_progress">
      <div class="play_progress_slider_box">
        <div class="music_name">歌名：{{musicList[playIndex].music_name}} <span>{{musicList[playIndex].singer}}</span></div>
        <div class="play_mode">
          <el-tooltip content="列表循环" v-show="playMode === 'cycle'">
            <img src="../../../public/cycle.png" @click="changePlayMode('singleCycle')">
          </el-tooltip>
          <el-tooltip content="单曲循环" v-show="playMode === 'singleCycle'">
            <img src="../../../public/singleCycle.png" @click="changePlayMode('random')">
          </el-tooltip>
          <el-tooltip content="随机播放" v-show="playMode === 'random'">
            <img src="../../../public/random.png" @click="changePlayMode('cycle')">
          </el-tooltip>
        </div>
      </div>
      <div class="play_progress_slider_box">
        <el-slider
            class="play_progress_slider"
            @mousedown.native="sliderMousedown"
            @mouseup.native="sliderMouseup"
            @mouseleave.native="sliderMouseup"
            v-model="playProgress" :step="1"
            :show-tooltip="false"
            @change="seek"></el-slider>
        <div class="play-time">{{calcPlayDuration}} / {{calcTotalDuration}}</div>
      </div>
    </div>
    <div class="play-volume">
      <img src="../../../public/volume.png">
      <el-slider class="play_volume_slider" @input="setVolume" v-model="playVolume"></el-slider>
    </div>
    <audio :src="audioSrc" @ended="changeMusic('next')" type="" preload="auto" ref="audio" @canplay="getAudioInfo" @seeked="onSeeked">
    </audio>
  </div>
</template>

<script>


export default {
  name: "music_player",
  components: {},
  props: {
    musicList:{
      type:Array,
      default:[]
    }
  },
  data() {
    return {
      playState: false,
      playProgress: 0,
      playMode: 'cycle',
      playVolume: 80,
      playIndex: 0,
      playTime: 0,
      totalDuration:0,
      currentTime:0,
      noPlayAudioIndexList:[],
      audioSrc:"",
      getPlayProgressTimer: null
    }
  },
  watch:{
    musicList(){
      this.playIndex = 0
      this.getVideoData(this.$fullUrl(this.musicList[this.playIndex].audio_frequency))
      this.getPlayProgress()
    },
    playIndex(){
      this.getVideoData(this.$fullUrl(this.musicList[this.playIndex].audio_frequency))
    },
  },
  methods: {
    getAudioInfo(){
      this.totalDuration = this.$refs.audio.duration
      this.playVolume = this.$refs.audio.volume * 100
      if(this.playState){
        this.playMusic()
      }
    },
    playMusic(){
      clearInterval(this.getPlayProgressTimer)
      this.getPlayProgress()
      this.$refs.audio.play()
      this.playState = true
    },
    pauseMusic(){
      this.$refs.audio.pause()
      this.playState = false
    },
    setVolume(e){
      this.$refs.audio.volume = e/100
    },
    changeMusic(operate){
      switch (this.playMode){
        case 'cycle':{
          operate === 'prev' ? this.playIndex-- : this.playIndex++
          if(this.playIndex > this.musicList.length - 1){
            this.playIndex = 0
          }
          if(this.playIndex < 0){
            this.playIndex = this.musicList.length - 1
          }
          break
        }
        case 'singleCycle': {
          this.$refs.audio.currentTime = 0
          this.playMusic()
          break
        }
        case 'random':{
          if(this.noPlayAudioIndexList.length < 1){
            this.initRandomNoPlayIndexList()
          }
          this.playIndex = this.noPlayAudioIndexList[0]
          this.noPlayAudioIndexList.splice(0,1)
          console.log(this.noPlayAudioIndexList)
          break
        }
      }
    },
    changePlayMode(mode){
      this.playMode = mode
      if(mode === 'random'){
        this.initRandomNoPlayIndexList()
      }
    },
    transformSecondsToMinute(value){
      const minute = Math.floor(value/60);
      const rest_seconds = Math.floor(value%60);
      return `${minute<10?'0'+minute:minute}:${rest_seconds<10?'0'+rest_seconds:rest_seconds}`
    },
    initRandomNoPlayIndexList(){
      this.noPlayAudioIndexList = []
      for (let i = 0; i < this.musicList.length; i++) {
        if (i !== this.playIndex){
          this.noPlayAudioIndexList.push(i)
        }
      }
      for (let i = this.noPlayAudioIndexList.length - 1; i > 0; i--) {
        let randomPos = Math.floor(Math.random() * (i + 1));
        [this.noPlayAudioIndexList[i], this.noPlayAudioIndexList[randomPos]] = [this.noPlayAudioIndexList[randomPos], this.noPlayAudioIndexList[i]];

      }
    },
    async getVideoData (val) {
      await new Promise((resolve, reject) => {
        this.$axios({
          method: 'get',
          responseType: 'blob',
          url: val
        }).then((res) => {
          this.audioSrc = window.URL.createObjectURL(res.data)
        })
      })
    },
    getPlayProgress(){
      this.getPlayProgressTimer = setInterval(()=>{
        if (this.$refs.audio){
          this.currentTime = this.$refs.audio.currentTime
        }
        this.playProgress = (this.currentTime/this.totalDuration) * 100
      },200)
    },
    seek(e){
      this.$refs.audio.currentTime = this.totalDuration * (e/100)
    },
    onSeeked(){
      if(!this.getPlayProgressTimer){
        this.getPlayProgress()
      }
    },
    sliderMousedown(){
      clearInterval(this.getPlayProgressTimer)
      this.getPlayProgressTimer = null
    },
    sliderMouseup(){
      if(!this.getPlayProgressTimer){
        this.getPlayProgress()
      }
    }
  },
  computed:{
    calcTotalDuration(){
      return this.transformSecondsToMinute(this.totalDuration)
    },
    calcPlayDuration(){
      return this.transformSecondsToMinute(this.currentTime)
    }
  }
}
</script>

<style scoped>
.y_music_player {
  width: 100%;
  height: 60px;
  background: #ffffff;
  z-index: 9999;
  display: flex;
  flex-direction: row;
  justify-content: start;
  box-shadow: #eeeeee 0 0 5px;
  margin-bottom: 10px;
  padding: 0 20px;
}
.play_control{
  height: 100%;
  display: flex;
  flex-direction: row;
  align-items: center;
  font-size: 30px;
  cursor: pointer;
}
.cover_img{
  width: 40px;
  height: 40px;
}
.play_progress{
  display: flex;
  flex-direction: column;
  margin-left: 20px;
  padding: 10px 0;
  flex: 1;
}
.music_name{
  font-size: 14px;
}
.play_progress_slider_box{
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
}
.play_progress_slider{
  flex: 1;
}
.play-time{
  margin-left: 30px;
  font-size: 14px;
}
.play-volume{
  display: flex;
  flex-direction: row;
  margin-left: 20px;
  align-items: center;
}
.play-volume img{
  width: 24px;
  height: 24px;
}
.play_volume_slider{
  width: 80px;
  margin-left: 20px;
}
.play_mode{
  cursor: pointer;
}
.play_mode img{
  width: 18px;
  height: 18px;
}
</style>
<style>
  .y_music_player .el-slider__runway{
    margin: 0;
  }
</style>
