
import { DenisHttpClient } from "@/api/clients";

class PuntoController {
  static API_ENDPOINT = "lang-checker";

  static async getTranslation(txt) {
    const response = await DenisHttpClient()
      .post(this.API_ENDPOINT, { message: txt })
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { PuntoController };
