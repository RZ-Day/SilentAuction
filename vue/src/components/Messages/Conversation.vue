<template>
  <div :class="inheritClass">
      <div class="container">
        <div v-for="m in messageList" :key="m.id">
            <message v-if="m.conversationId === $store.state.activeConversationID" :fromId="m.fromId" :content="m.messageBody"/>
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
        justify-content: flex-start;
        background-color: rgb(255, 255, 255);

    }

    .message-container {
        min-height: 50px;
    }
</style>