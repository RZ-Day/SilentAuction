<template>
    <button v-bind="$attrs">
        <slot></slot>
    </button>
</template>


<script>
</script>

<style scoped>
button{
    width: 100%;
    height: 100%;
    background-image: linear-gradient(var(--l-prime), var(--d-prime));
}
</style>
