<template>
  <div :class="inheritClass">
      <div
        v-for="convo in convos"
        :key="convo.conversationId"
        class="convo-item"
        :class="{'selected': convo.conversationId === $store.state.activeConversationID}" 
        @click="changeActiveConvo(convo.conversationId)" >
            {{convo.itemName}}
      </div>
  </div>
</template>

<script>
export default {
    name: "conversation-list",
    props: {
        inheritClass: String,
        convos: Array
    },
    methods: {
        changeActiveConvo(newId) {
            this.$store.commit("SET_ACTIVE_CONVERSATION", newId);
        }
    }
}
</script>

<style scoped>

    .convo-item {
        background-color: white;
        min-height: 100px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        border-top: solid;
        border-bottom: solid;
        border-width: 1px;
        border-color: rgb(223, 223, 223);
    }

    .selected {
        background-color: rgb(231, 231, 231);
        color: black;
        min-height: 100px;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        border-top: solid;
        border-bottom: solid;
        border-width: 1px;
        border-color: rgb(223, 223, 223);

    }

    .convo-item:hover {
        background-color: rgb(231, 231, 231);
        color: rgb(255, 255, 255);
        transition: .2s ease;
    }
</style>