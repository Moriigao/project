<template>
    <!--定义HTML结构-->
    <div id="app">
        <page_header/>
        <div class="space"/>
        <!--当前页面的退出和新页面的进入是顺序执行的-->
        <transition name="fade-transform" mode="out-in">
            <router-view :key="$route.fullPath"/>
        </transition>
        <page_footer/>
    </div>
</template>

<script>
export default {
  watch: {
    $route(to, from) {
      // 获取当前的 URL hash 值并去掉前面的 # 符号
      let currentPath = window.location.hash.slice(1)
      if (this.$route.path !== currentPath) {
        this.$router.push(currentPath)
      }
    }
  }
}
</script>

<style lang='less'>
    //为活动页项的链接设置背景色和边框色。
    .page-item.active .page-link {
        background-color: var(--color_primary) !important;
        border-color: var(--color_primary) !important;
    }
    //定义了带有边框的主按钮样式。
    .btn-outline-primary {
        color: var(--color_primary) !important;
        border-color: var(--color_primary) !important;
    }

    //放大镜
    .mouse-cover-canvas {
        position: absolute;
        left: calc(50% - 6rem) !important;
        top: 8rem !important;
    }

    body {
        background-color: #F4F7F9 !important;
        font-family: STXihei !important;
    }

    [class="col-"] {
        padding: 0;
    }

    .space {
        // height: 5rem;
    }
    //设置页头固定位置和 z-index，使其总是显示在页面顶部
    .page_header {
        position: fixed;
        top: 0;
        z-index: 1000;
        width: 100%;
    }

    //使链接默认没有下划线，且有平滑的过渡效果，鼠标悬停时颜色变为灰色
    a {
        color: inherit !important;
        text-decoration: none;
        -webkit-transition: all 0.3s ease 0s;
        -o-transition: all 0.3s ease 0s;
        transition: all 0.3s ease 0s;
    }

    a:hover {
        text-decoration: none !important;
        /*color: var(--color_primary) !important;*/
        color: #909399 !important;
    }

    //设置通知提示框的位置和样式
    .toast {
        opacity: 1 !important;
        min-width: 200px;
    }

    .b-toaster-top-right {
        position: absolute;
        top: 10rem;
        left: 50%;
        transform: translate(-50%);
    }

    figure,
    p,
    h1,
    h2,
    h3,
    h4,
    h5,
    h6,
    ul,
    li {
        margin: 0;
        padding: 0;
    }

    //设置页面过渡效果
    .fade-transform-leave-active,
    .fade-transform-enter-active {
        transition: all 0.5s;
    }

    .fade-transform-enter {
        opacity: 0;
        transform: translateX(-30px);
    }

    .fade-transform-leave-to {
        opacity: 0;
        transform: translateX(30px);
    }
</style>

