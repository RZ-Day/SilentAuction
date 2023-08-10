<template>
  <div class="container">
      <div class="side-bar"></div>
      <conversation inheritClass="current-convo" :messageList="this.messageList"/>
      <write-message inheritClass="write-message" />
  </div>
</template>

<script>
import WriteMessage from "../components/Messages/WriteMessage.vue";
import Conversation from "../components/Messages/Conversation.vue"

export default {
    name: 'messages',
    data() {
        return {
            messageList: [
                {   
                    id: 1,
                    to_id: 1,
                    from_id: 2,
                    time_sent: "8/10/23 3:00",
                    message_body: "Epstein didn't do it."
                },
                {
                    id: 2,
                    to_id: 2,
                    from_id: 1,
                    time_sent: "8/10/23 3:01",
                    message_body: "OK." 
                }
            ]
        }
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