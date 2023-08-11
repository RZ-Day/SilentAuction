<template>
  <div :class="inheritClass">
      <form class="message-form">
          <button @click="submit">Send</button>
          <input type="text" v-model="message.messageBody"/>
      </form>
  </div>
</template>

<script>
import messageService from "@/services/MessageService.js";

export default {
    name: "write-message",
    props: {
        inheritClass: String
    },
    data() {
        return {
            message: {
                fromId: 1,
                toId: 3,
                itemId: 4,
                timeSent: "8/10/2023",
                messageBody: ""
            }
        }
    },
    methods: {
        submit() {
            messageService.addMessage(this.message);
        }
    }
}
</script>

<style scoped>
    .message-form {
        display: flex;
        justify-content: space-evenly;
    }
</style>