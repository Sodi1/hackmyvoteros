import { createApp } from "vue";
import { router, store, vuetify } from "./providers";
import App from "./App.vue";

const app = createApp(App);

app.use(store).use(router).use(vuetify).mount("#app");
