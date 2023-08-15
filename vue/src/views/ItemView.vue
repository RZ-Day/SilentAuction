<template>
  <div>
      <Item :item="item" />
      <button @click="startConversation">Message Vendor</button>
  </div>
</template>

<script>
import Item from "../components/Item.vue"; // Import the Item component
import auctionService from "@/services/AuctionsListService.js";
import messageService from "@/services/MessageService.js";

export default {
  components: {
    Item
  },
  data() {
    return {
      auction: null,
      item: null // Initialize item as null
    };
  },
  created() {
    const activeItemID = parseInt(this.$route.params.currentItemID);
    const activeAuctionID = parseInt(this.$route.params.currentAuctionID);
    this.$store.commit("SET_ACTIVE_ITEM", activeItemID);
    auctionService.getAuctions().then(response => {
      if (response.status === 200) {
        this.auction = response.data.find(
          auction => auction.auctionId === activeAuctionID
        );

        if (this.auction) {
          // Find the specific item within the auction's items array
          this.item = this.auction.items.find(
            item => item.itemId === activeItemID
          );
        }
      }
    });
  },
  methods: {
    startConversation() {
      //CREATE NEW CONVERSATION IN BACK-END
      console.log(this.item);

      messageService.startConversation({
        sellerId: this.item.userId,
        buyerId: this.$store.state.user.id,
        itemId: this.item.itemId,
        itemName: this.item.itemName
      }).then((response) => {
        if (response.status == 200) {
          //SET ACTIVE CONVERSATION ID TO NEWLY CREATED CONVERSATION, OR TO EXISTING CONVERSATION ID
          const newConvoId = response.data.conversationId;
          this.$store.commit("SET_ACTIVE_CONVERSATION", newConvoId);

          //REDIRECT TO ACTIVE CONVERSATION IN INBOX
          this.$router.push({name:"Messages", params:{currentUserId: this.$store.state.user.id}});
        }
      });
    }
  }
};
</script>


<style>

</style>