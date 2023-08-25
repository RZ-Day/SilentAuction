<template>
    <div class="card">
        <slot>

        </slot>
    </div>
</template>

<script>
    export default {
        name: 'card'
    }
</script>

<style scoped>
    .card {
        padding: 50px;
        margin: 20px;
        height: 100%;
        width: 100%;
        border-top-style: solid;
        border-bottom-style: solid;
        border-width: 1px;
        border-color: grey;
    }
</style>