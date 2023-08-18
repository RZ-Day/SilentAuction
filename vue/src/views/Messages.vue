<template>
  <div class="container">
      <!-- <div class="side-bar"></div> -->
        <conversation-list inheritClass="side-bar" :convos="this.conversationList"/>
        <conversation inheritClass="current-convo" :messageList="currentMessages" />
        <write-message inheritClass="write-message" :toId="toId"/>
  </div>
</template>

<script>
import WriteMessage from "../components/Messages/WriteMessage.vue";
import Conversation from "../components/Messages/Conversation.vue";
import ConversationList from "../components/Messages/ConversationList.vue"
import messageService from "../services/MessageService.js";

export default {
    name: 'messages',
    data() {
        return {
            conversationList: []
        }
    },
    created() {
        console.log("About to hit first service method");
        //Find all messages for active conversation
        messageService.getMessagesByUserId(this.$store.state.user.id).then((response) => {

            if (response.status == 200) {
                this.$store.commit("SET_STORE_MESSAGES", response.data);
            } else {
                console.log("Unable to retrieve message data");
            }
        }).catch((error) => {
            console.log("An error occurred: ", error);
        });

        //Find all active conversations relating to user
        messageService.getConversationsByUserId(this.$store.state.user.id).then((response) => {
            if(response.status == 200) {
                this.conversationList = response.data;
            } else {
                console.log("Unable to retrieve message data");
            }
        }).catch((error) => {
            console.log("Error occurred: ", error)
        });
    },
    components: {
        ConversationList,
        WriteMessage,
        Conversation
    },
    computed: {
        //This is horrible. Oh well
        toId() {
            let currentToId = 0;
            
            if (this.conversationList.length > 0 && this.$store.state.activeConversationID != 0) {
                const convo = this.conversationList.find(obj => obj.conversationId === this.$store.state.activeConversationID);

                if (convo.sellerId == this.$store.state.user.id) {
                    currentToId = convo.buyerId;
                } else {
                    currentToId = convo.sellerId;
                }
            } else {
                console.log("No conversations found");
            }

            return currentToId;
        },
        currentMessages() {
            return this.$store.state.storeMessages;
        }
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
        font-family: Ariel, sans-serif;
    }
    .side-bar {
        grid-area: side-bar;
        width: 100%;
        height: 100%;
        background-color: rgb(209, 209, 209);
        overflow-y: scroll;
    }
    .current-convo {
        grid-area: convo;
        width: 100%;
        height: 100%;
        overflow-y: scroll;
        background-color: rgb(255, 255, 255);
        border-left-style: solid;
        border-left-width: 1px;
        border-color: rgb(223, 223, 223);
    }
    .write-message {
        grid-area: input;
        display: flex;
        align-items: center;
        background-color: rgb(0, 0, 0);
        padding-left: 10px;
    }
</style>