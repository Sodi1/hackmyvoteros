import { DenisHttpClient } from "@/api/clients";

class AnalyticController {
  static API_ENDPOINT = "analytics/";


  static async getTop(id, q_id, str) {
    let str2 = ""
    if (q_id) {
      str2 += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "question/top-positive" + `?file_id=${id}&emotional=${str}&limit=7` + str2)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getEmotionalDestribution(id, q_id = 0, str) {
    let str2 = ""
    if (q_id) {
      str2 += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + `answer/emotional-distribution?file_id=${id}&limit=100&number_order=${str}` + str2)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getPie(id, q_id = 0) {
    let str = ""
    if (q_id) {
      str += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "question/count" + "?file_id=" + Number(id) + str)
      .catch((err) => {
        return err;
      });
    const dat = []
    response.data.forEach((v) => {
      dat.push({ value: v.count, name: v.emotional })
    })
    return dat;
  }
  static async stat(id, q_id = 0) {
    let str = ""
    if (q_id) {
      str += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "censored/stat" + "?file_id=" + id + str)
      .catch((err) => {
        return err;
      });
    return response;
  }
  static async incorrectAnswerTop(id, q_id = 0, percent = 1, limit = 5) {
    let str = ""
    if (q_id) {
      str += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "answer/incorrect-answer-top" + "?file_id=" + id + str + `&percent=${percent}&limit=${limit}`)
      .catch((err) => {
        return err;
      });
    return response;
  }
  static async incorrectAnswer(id, q_id = 0) {
    let str = ""
    if (q_id) {
      str += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "answer/incorrect-answer" + "?file_id=" + id + str)
      .catch((err) => {
        return err;
      });
    return response;
  }
  static async anwerTop(id, q_id = 0) {
    let str = ""
    if (q_id) {
      str += "&question_id=" + q_id
    }
    const response = await DenisHttpClient()
      .get(this.API_ENDPOINT + "answer/top" + "?file_id=" + id + "&limit=5" + str)
      .catch((err) => {
        return err;
      });
    return response;
  }
}

export { AnalyticController };
