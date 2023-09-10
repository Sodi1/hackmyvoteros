import { DenisHttpClient } from "@/api/clients";
import { MyVoteHttpClient } from "@/api/clients";

class QuestionController {
  static API_ENDPOINT = "question";


  static async getQuestions(file_id) {
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + `?file_id=${file_id}`)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getQuestionsById(id) {
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + `/${id}`)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getFilteredQuestions(file_id, query) {
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + `/find?file_id=${file_id}&query=${query}`)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getSearchedQuestions(file_id,question_id, val) {
    const resp = await this.getQuestionsById(question_id);
    const response = await MyVoteHttpClient()
      .get(this.API_ENDPOINT + "/search" + `?query=` + resp.name + `&significance=` + val + "&file_id=" + file_id)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { QuestionController };
