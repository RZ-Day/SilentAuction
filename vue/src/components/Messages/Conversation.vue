<template>
  <div :class="inheritClass">
      <div class="container">
        <div v-for="m in messageList" :key="m.id">
            <message :content="m.message_body"/>
        </div>
      </div>
  </div>
</template>

<script>
import Message from "../Messages/Message.vue";

export default {
    name: "conversation",
    props: {
        messageList: Array,
        inheritClass: String
    },
    components: {
        Message
    },
}
</script>

<style scoped>
    .container {
        display: flex;
        width: 100%;
        height: 100%;
        flex-direction: column;
        justify-content: flex-end;
    }

    .message-container {
        min-height: 50px;
    }
</style>