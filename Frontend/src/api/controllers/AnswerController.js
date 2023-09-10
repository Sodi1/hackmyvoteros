import { DenisHttpClient } from "@/api/clients";

class AnswerController {
  static API_ENDPOINT = "answer";


  static async getAnswers(id, q_id = 0) {
    let str = ""
    if (q_id) {
      str = "&question_ids=" + [q_id]
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "" + `?file_id=${id}` + str)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { AnswerController };
