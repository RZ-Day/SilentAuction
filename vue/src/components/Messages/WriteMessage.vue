<template>
  <div :class="inheritClass">
      <form class="message-form">
          <button class="submit-button" @click.prevent="submit">Send</button>
          <input class="message-input" type="text" v-model="message.messageBody"/>
      </form>
  </div>
</template>

<script>
import messageService from "@/services/MessageService.js";

export default {
    name: "write-message",
    props: {
        inheritClass: String,
        toId: Number
    },
    data() {
        return {
            message: {
                fromId: this.$store.state.user.id,
                toId: 0,
                conversationId: 0,
                messageBody: ""
            }
        }
    },
    methods: {
        submit() {
            
            const newMessage = {
                fromId: this.$store.state.user.id,
                toId: this.toId,
                conversationId: this.$store.state.activeConversationID,
                messageBody: this.message.messageBody.slice()
            };

            this.$store.commit("ADD_MESSAGE", newMessage);
            messageService.addMessage(newMessage);
            this.message.messageBody = "";
        }
    }
}
</script>

<style scoped>
    .message-form {
        display: flex;
        width: 100%;
    }

    .submit-button {
        background-color: rgb(255, 255, 255);
        height: 25px;
        border-radius: 5px;
        margin-right: 10px;
        border-style: solid;
        border-color: white;
    }

    .message-input {
        width: 50%;
        border-radius: 5px;
        border-style: solid;
        border-color: white;
    }
</style>