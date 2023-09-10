<template>
  <div class="layout">
    <div class="header-wrapper elevation-2">
      <div>
        <v-btn v-click-outside="() => {
          leftMenuShown = false;
        }
          " icon="mdi-menu" variant="flat" @click="leftMenuShown = !leftMenuShown" />

        <Transition name="slide-x-transition">
          <div v-show="leftMenuShown" class="header-left-menu  elevation-2">
            <NavMenuMain />
          </div>
        </Transition>
        <v-btn icon="mdi-subdirectory-arrow-left" @click="router.go(-1)" variant="text"></v-btn>
      </div>
      <div class="rosatom-logo" @click="router.push({ name: 'Home' })">
        <img :src="rosatomLogo" />
      </div>
      <div v-click-outside="() => {
        menuShown = false;
      }
        " class="search-avatar-group" v-ripple @click="menuShown = !menuShown">
        <AvatarComponent size="50px"></AvatarComponent>
        <v-icon icon="mdi-chevron-down" size="small" color="grey-lighten-1"></v-icon>
        <Transition name="slide-x-reverse-transition" @click.stop>
          <div v-show="menuShown" class="avatar-menu">
            <div class="avatar-menu-item">
              <v-icon icon="mdi-cog-outline"></v-icon>
              <span>Настройки</span>
            </div>
            <div class="avatar-menu-item" @click="logout()">
              <v-icon icon="mdi-logout"></v-icon>
              <span>Выйти</span>
            </div>
          </div>
        </Transition>
      </div>
    </div>
    <div style="width:100%;height:100%">
      <RouterView></RouterView>
    </div>
  </div>
</template>
<script setup lang="ts">
import { AvatarComponent } from "@/components";
import { ref } from "vue";
import { NavMenuMain } from "@/components";
import { useUserStore } from "@/stores";
import rosatomLogo from "assets/rosatom-logo.png"
import { router } from "@/app/providers";

const userStore = useUserStore();
const shadow = ref("none");

const menuShown = ref(false);
const leftMenuShown = ref(false);

function logout() {
  userStore.user = {};
  router.push({ name: "Login" })
}
</script>
<style lang="scss" scoped>
.layout {
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: $bg-primary;
  font-family: "Montserrat";
  align-items: center;
  min-height: 100vh;
}



.header-wrapper {
  @apply shadow-sm;
  height: 78px;
  padding: 0 0 0 8px;
  transition: all 0.5s ease;
  width: 100%;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: v-bind(shadow);

  &> :last-child {
    display: flex;
    align-items: center;
  }

  .rosatom-logo {
    padding: 14px;
    height: 100%;
    cursor: pointer;

    img {
      height: 100%;
    }
  }

  .header-left-menu {
    @apply shadow;
    z-index: 3;
    position: absolute;
    width: max-content;
    left: 30px;
    top: 108px;
    background-color: white;
    overflow: hidden;
    padding: 12px;
    border-radius: 10px;
  }



}

.search-avatar-group {
  display: flex;
  align-items: center;
  box-sizing: content-box;
  position: relative;
  padding: 14px 12px 14px 12px;
  cursor: pointer;

  &:hover {
    background-color: rgba(0, 0, 0, 0.045);
  }

  &> :first-child {
    margin-right: 2px;
  }

  .avatar-menu {
    @apply shadow;
    border-radius: 20px;
    padding: 10px;
    z-index: 5;
    cursor: auto;
    position: absolute;
    top: calc(100% + 8px);
    left: -88px;
    background-color: white;

    .avatar-menu-item {
      cursor: pointer;
      padding: 8px 16px 8px 14px;

      &> :first-child {
        margin-right: 6px;
      }

      &:hover {
        background-color: rgba(0, 0, 0, 0.085);
      }
    }

    .avatar-menu-item-lang {
      cursor: pointer;
      padding: 4px 16px 4px 14px;

      &:nth-child(2) {
        border-bottom: 1px solid $text-lightest;
      }

      &> :first-child {
        margin-right: 6px;
      }

      &:hover {
        background-color: rgba(0, 0, 0, 0.045);
      }
    }

    .avatar-menu-item-selected {
      background-color: rgba(91, 74, 154, 0.15);

      &:hover {
        background-color: rgba(91, 74, 154, 0.25) !important;
      }
    }
  }
}
</style>
