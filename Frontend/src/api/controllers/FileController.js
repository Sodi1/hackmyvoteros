import { DenisHttpClient } from "@/api/clients";

class FileController {
  static API_ENDPOINT = "data/";

  static async postFile(file) {
    let formData = new FormData();
    formData.append("file", file);

    const response = await DenisHttpClient()
      .post(this.API_ENDPOINT + "upload", formData)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async getFiles() {
    const response = await DenisHttpClient()
      .get("file")
      .catch((err) => {
        return err;
      });
    return response.data;
  }
  static async deleteFile(id) {
    const response = await DenisHttpClient()
      .delete("file/" + id)
      .catch((err) => {
        return err;
      });
    return response.data;
  }
}

export { FileController };
