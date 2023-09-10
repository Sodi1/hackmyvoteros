import axios from "axios";

const MyVoteHttpClient = () => {
  const baseUrl = "https://myvote-analytics.kovalev.team/api/";

  return axios.create({
    baseURL: baseUrl,
  });
};

export { MyVoteHttpClient };
