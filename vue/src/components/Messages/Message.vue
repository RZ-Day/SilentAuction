<template>
  <div>
      {{ content }}
  </div>
</template>

<script>
export default {
    name: "message",
    props: {
        content: String
    }
}
</script>

<style>
    .sent {
        background-color: rgb(172, 173, 231);
    }
    .received {
        background-color: rgb(201, 201, 201);
    }
</style>