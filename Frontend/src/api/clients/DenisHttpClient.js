import axios from "axios";

const DenisHttpClient = () => {
  const baseUrl = "https://myvote-api.kovalev.team/api/v1/";

  return axios.create({
    baseURL: baseUrl,
  });
};

export { DenisHttpClient };
