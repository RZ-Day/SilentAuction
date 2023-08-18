<template>
  <div :class="{'sent': this.$store.state.user.id == this.fromId,  'received': this.$store.state.user.id != this.fromId}">
      {{ content }}
  </div>
</template>

<script>
export default {
    name: "message",
    props: {
        content: String,
        fromId: Number
    }
}
</script>

<style scoped>
    .sent {
        display: flex;
        background-color: rgb(0, 0, 0);
        width: max-content;
        border-radius: 20px 20px 20px 3px;
        color: white;
        min-height: 60px;;
        padding-left: 25px;
        padding-right: 25px;
        margin-left: 10px;
        margin-top: 5px;
        margin-bottom: 5px;
        align-items: center;
        cursor: pointer;
    }

    .received {
        display: flex;
        background-color: rgb(255, 255, 255);
        width: max-content;
        border-radius: 20px 20px 20px 3px;
        border-style: solid;
        border-width: 2px;
        color: black;
        min-height: 60px;;
        padding-left: 25px;
        padding-right: 25px;
        margin-left: 10px;
        margin-top: 5px;
        margin-bottom: 5px;
        align-items: center;
        cursor: pointer;
    }
</style>