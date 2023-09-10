import { MyVoteHttpClient } from "@/api/clients";
import { AnswerController } from "./AnswerController";

class EmotionController {
  static API_ENDPOINT = "emotions/";

  static async getEmotionStats(strings) {
    const postData = {
      texts: strings
    }
    const response = await MyVoteHttpClient()
      .post(this.API_ENDPOINT, postData)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getSpellCheck(str) {
    const response = await MyVoteHttpClient()
      .post("spellcheck/", { texts: [str] })
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getClusters(strs) {
    const response = await MyVoteHttpClient()
      .post("clustering/", { texts: strs })
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { EmotionController };
