<template>
  <div class="container">
      <h2>Profile Settings</h2>
      <div class="profile-card">
          <div class="item">
              <div class="label">
                  Username
              </div>
              <div class="user-info">
                  <div>{{this.$store.state.user.username}}</div>
                  <button>Edit</button>
              </div>
          </div>

          <div class="item">
              <div class="label">
                  Email
              </div>
              <div class="user-info">
                  <div>JDoe@test.com</div>
                  <button>Edit</button>
              </div>
          </div>

          <div class="item">
              <div class="label">
                  Phone
              </div>
              <div class="user-info">
                  <div>555-555-5555</div>
                  <button>Edit</button>
              </div>
          </div>

          <div class="item">
              <div class="label">
                  Shipping Address
              </div>
              <div class="user-info">
                  <div>111 Test Ave</div>
                  <button>Edit</button>
              </div>
          </div>
          <div class="spacer" />
      </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
    .container{
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 91vh;
        font-family: Ariel, sans-serif;
    }

    .profile-card {
        display: flex;
        flex-direction: column;
        border-style: solid;
        border-width: 1px;
        border-radius: 20px;
        height: 60%;
        width: 50%;
        padding: 50px;
    }
    
    .user-info {
        display: flex;
        justify-content: space-between;
        border-top-style: solid;
        border-bottom-style: solid;
        border-color: rgb(189, 189, 189);
        border-width: 1px;
        padding: 10px;
    }

    .item {
        margin-bottom: 20px;
    }

    .label {
        font-size: 12px;
        color: rgb(182, 182, 182);
    }

    button {
        color: white;
        background-color: #253b6e;
        border-style: none;
        cursor: pointer;
    }
</style>