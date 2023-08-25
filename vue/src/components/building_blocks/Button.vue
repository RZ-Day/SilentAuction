<template>
    <button :type="buttonType" class="standard-button">
        {{ buttonLabel }}
    </button>
</template>

<script>
    export default {
        name: 'standard-button',
        props: {
            buttonType: String,
            buttonLabel: String
        }
    }
</script>

<style scoped>
    .standard-button {
        padding: 10px;
        border-radius: 5px;
        background-color: rgb(238, 113, 41);
        color: white;
        border-style: none;
    }

    .standard-button:hover {
        background-color: rgb(207, 96, 32);
    }

    .standard-button:active {
        background-color: rgb(136, 59, 15);
    }
</style>