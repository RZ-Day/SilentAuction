<template>
  <div :class="inheritClass">
      <div v-for="m in messageList" :key="m.id">
          <message :content="m.message_body"/>
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
    }
}
</script>

<style>

</style>