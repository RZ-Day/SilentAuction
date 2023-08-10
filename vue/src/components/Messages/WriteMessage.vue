<template>
  <div :class="inheritClass">
      <form class="message-form">
          <input type="submit" value="Send" />
          <input type="text" />
      </form>
  </div>
</template>

<script>
export default {
    name: "write-message",
    props: {
        inheritClass: String
    }
}
</script>

<style scoped>
    .message-form {
        display: flex;
        justify-content: space-evenly;
    }
</style>