<template>
  <div class="login-wrapper">
    <div class="auth-card">
      <div class="auth-card-body">
        <v-form ref="form" class="auth-card-form">
          <v-text-field v-model="login" required :rules="loginRules" label="Логин" color="secondary" />
          <v-text-field v-model="pass" :rules="passRules" required label="Пароль" color="secondary" />
        </v-form>
        <div class="auth-card-form-actions">
          <v-checkbox v-model="remeber" color="secondary" density="comfortable" hide-details label="Запомнить меня" />
          <div class="auth-card-form-forgot-link">Забыли пароль?</div>
        </div>
        <div class="auth-card-form-action-btns">
          <v-btn @click="loginGuest()" class="auth-card-btn" rounded variant="text" color="secondary">
            Войти как гость
          </v-btn>
          <v-btn @click="loginUser()" class="auth-card-btn" rounded variant="elevated" color="secondary">
            Войти
          </v-btn>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref } from "vue";
import { useUserStore } from "@/stores"
import { useRouter } from "vue-router";

const userStorage = useUserStore()
const router = useRouter()

const form = ref()


const login = ref("");
const loginRules = [(v) => (v.length ? true : "Поле обязательно")];

const pass = ref("");
const passRules = [(v) => (v.length ? true : "Поле обязательно")];

const remeber = ref(false);

async function loginUser() {
  const { valid } = await form.value.validate()

  if (valid) {
    await userStorage.login(login.value, pass.value)
    if (userStorage.user.accessToken && userStorage.user.refreshToken) {
      router.push('/')
    } else {
      error.value = true
    }
  }
}
async function loginGuest() {
  await userStorage.login('predictodemo', 'predictodemo')
  router.push('/')
}
</script>
<style scoped lang="scss">
.login-wrapper {
  height: 100dvh;
  width: 100dvw;
  background-size: contain;
  background-repeat: no-repeat;
  align-items: center;
  display: flex;
  justify-content: space-around;
  background-image: url('src/assets/rosatom-back.webp');
  background-size: cover;

  .auth-card {
    @apply shadow;
    border-radius: 20px;
    padding: 24px 30px 4px 30px;
    background-color: white;

    .auth-card-body {
      padding: 24px;
      min-width: 360px;
      text-align: end;

      .auth-card-form {
        &> :first-child {
          margin-bottom: 8px;
        }

        &> :nth-child(2) {
          margin-bottom: -8px;
        }
      }

      .auth-card-form-actions {
        display: flex;
        align-items: center;
        margin-bottom: 24px;

        .auth-card-form-forgot-link {
          cursor: pointer;
          color: rgb(118, 69, 223);
          font-size: 14px;
          text-decoration: underline;
        }
      }

      .auth-card-form-action-btns {
        display: flex;
        justify-content: space-between;
      }
    }
  }
}
</style>
