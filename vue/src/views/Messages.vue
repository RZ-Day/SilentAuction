<template>
  <div class="container">
      <div class="side-bar"></div>
      <conversation inheritClass="current-convo" :messageList="this.messageList"/>
      <write-message inheritClass="write-message" />
  </div>
</template>

<script>
import WriteMessage from "../components/Messages/WriteMessage.vue";
import Conversation from "../components/Messages/Conversation.vue";
import messageService from "../services/MessageService.js";

export default {
    name: 'messages',
    data() {
        return {
            messageList: []
        }
    },
    created() {
        messageService.getMessagesByUserId(5).then((response) => {
            if (response == 200) {
                this.messageList = response.data;
            } else {
                console.log("Unable to retrieve message data");
            }
        })
    },
    components: {
        WriteMessage,
        Conversation
    }
}
</script>

<style scoped>
    .container {
        display: grid;
        grid-template-columns: 1fr 3fr;
        grid-template-rows: 9fr 1fr;
        grid-template-areas:
            "side-bar convo"
            "side-bar input";

        height: 91vh; /*TODO: find a better way to scale the page according to available viewport space*/
        overflow: auto;
        background-color: black;
    }
    .side-bar {
        grid-area: side-bar;
        width: 100%;
        height: 100%;
        background-color: red;
    }
    .current-convo {
        grid-area: convo;
        width: 100%;
        height: 100%;
        background-color: rgb(65, 65, 97);
    }
    .write-message {
        grid-area: input;
        display: flex;
        align-items: center;
        background-color: chartreuse;
    }
</style>