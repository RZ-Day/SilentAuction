<template>
  <div>
      <h2>
          {{ item.itemName }} 
      </h2>
      <p>
         Description :  {{item.description}}
      </p>
      <p>
          Current Price : ${{item.currentPrice}} 
      </p>

      <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="header-icon" />
  </div>
</template>

<script>
export default {
name: "ItemPage",
props: {
    item: Object
}
}
</script>

<style>

</style>