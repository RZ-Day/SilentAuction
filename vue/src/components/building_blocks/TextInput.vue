<template>
    <div class="input-box">
        <input :type="inputType" @input="$emit('input',$event.target.value)" :required="isRequired" />
        <span>{{ title }}</span>
    </div>
</template>

<script>
    export default {
        name:"text-input",
        props: {
            title: String,
            inputType: String,
            isRequired: Boolean
        }
    }
</script>

<style scoped>

    .input-box {
        display: flex;
        align-items: center;
        position: relative;
        margin-top: 10px;
        margin-bottom: 10px;
    }

    .input-box input {
        width: 100%;
        padding: 10px;
        border-radius: 5px;
        border-style: solid;
        border-width: 1px;
        border-color: grey;
    }

    .input-box span {
        position: absolute;
        left: 0;
        margin-left: 10px;
        padding: 0 5px 0 5px;
        font-size: 1em;
        pointer-events: none;
        color: grey;
        text-transform: uppercase;
        font-family: Ariel, sans-serif;
        transition: 0.5s;
    }

    .input-box input:focus {
        border-style: none;
    }

    .input-box input:valid ~ span,
    .input-box input:focus ~ span {
        color: rgb(238, 113, 41);
        border-width: 1px;
        border-right-style: solid;
        border-left-style: solid;
        border-left-color: rgb(238, 113, 41);
        border-right-color: rgb(238, 113, 41);
        transform: translateX(10px) translateY(-17px);
        font-size: 0.70em;
        background-color: white;
    }

    .input-box input:focus,
    .input-box input:valid {
        outline: none;
        border-style: solid;
        border-width: 1px;
        border-color: rgb(238, 113, 41);
    }
</style>