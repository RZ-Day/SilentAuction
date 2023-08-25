<template>
    <div class="body">
        <h2>
            {{ formTitle }}
        </h2>

        <CardVertical>
            <form>
                <slot>
                    
                </slot>
                <Button buttonType="submit" :buttonLabel="submitButtonLabel" />
            </form>
        </CardVertical>
    </div>
</template>

<script>
    import Button from './Button.vue';
    import CardVertical from './CardVertical.vue';

    export default {
        name: 'standard-form',
        components: {
            Button,
            CardVertical
        },
        props: {
            submitButtonLabel: String
        }
    }
</script>

<style scoped>
    h2 {
        font-family: Ariel sans-serif;
    }
</style>