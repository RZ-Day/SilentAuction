<template>
  <div class="body">
      {{ content }}
  </div>
</template>

<script>
export default {
    name: "message",
    props: {
        content: String
    }
}
</script>

<style scoped>
    .body {
        display: flex;
        background-color: rgb(172, 173, 231);
        min-height: 60px;;
        padding-left: 25px;
        padding-right: 25px;
        align-items: center;
    }
</style>