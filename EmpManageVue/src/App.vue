<template>
  <router-view />
</template>

<style>
/* 全局重置 - 必须加！ */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html,
body,
#app {
  height: 100%;
  width: 100%;
  overflow: hidden;   /* 禁止 body 本身滚动 */
}
</style>