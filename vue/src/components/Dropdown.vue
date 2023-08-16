<template>
  <div class="select-dropdown">
      <a class="title" @click="toggle" href="#">
          {{ title }}
      </a>
      <div v-if="this.selected" class="sub-menu">
        <div v-for="item in items" class="sub-item" v-bind:key="item.label">
            <router-link :to="item.link" class="sub-link"> {{ item.label }} </router-link>
        </div>
      </div>
  </div>
</template>

<script>
export default {
    name: 'dropdown',
    data() {
      return {
        selected: false
      }
    },
    methods: {
      toggle() {
        this.selected = !this.selected;
      }
    },
    props: ['title', 'items']
}
</script>

<style scoped>
  .title {
    display: flex;
    cursor: pointer;
    align-items: center;
    height: 100%;
    color: black;
    background-color:rgb(255, 255, 255);
    padding-left: 10px;
    padding-right: 10px;
    text-align: center;
    text-decoration: none;
  }

  .title:hover,
  .sub-link:hover {
    transition: .2s ease;
    color: #253b6e;
  }

  .sub-menu {
    display: flex;
    background-color: white;
    border-bottom: solid;
    border-left: solid;
    border-right: solid;
    border-bottom-width: 1px;
    border-left-width: 1px;
    border-right-width: 1px;
    border-color: rgb(223, 223, 223);
    flex-direction: column;
    align-items: center;
  }

  .sub-item {
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    padding-top: 10px;
    padding-bottom: 10px;
    width: 100%;
    height: 100%;
  }

  .sub-link {
    display: flex;
    align-items: center;
    justify-content: center;
    text-decoration: none;
    width: 100%;
    height: 100%;
    color: black;
  }
</style>