import { CensorHttpClient } from "../clients/CensorHttpClient";

class CensorController {
  static API_ENDPOINT = "censor-words";

  static async censorStr(str) {
    const response = await CensorHttpClient()
      .post(this.API_ENDPOINT, { text: str })
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { CensorController };
