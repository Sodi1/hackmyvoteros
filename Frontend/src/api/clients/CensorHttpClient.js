import axios from "axios";

const CensorHttpClient = () => {
  const baseUrl = "https://myvote-profanity.kovalev.team/";

  return axios.create({
    baseURL: baseUrl,
  });
};

export { CensorHttpClient };
