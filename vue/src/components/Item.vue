<template>
  <div class="item-page">
    <div class="item-details">
      <h2>{{ item.itemName }}</h2>
      <p>Description: {{ item.description }}</p>

    </div>

    <div class="item-image">
      <img src="@/Assets/itemTemp.png" alt="Auction Icon" class="item-icon" />
    </div>
    
  </div>
</template>

<script>

export default {
  name: "ItemPage",
  props: {
    item: Object,
  },
};
</script>

<style scoped>
.item-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}


.item-details,
.item-image {
  margin: 20px; 
}

.item-details {
  text-align: center;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.item-details h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #192a3d;
}

.item-image {
  margin-left: 20px;
}

.item-icon {
  width: 400px;
  height: auto;
}

</style>
