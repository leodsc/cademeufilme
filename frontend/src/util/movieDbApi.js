export const BASE_URL = "https://api.themoviedb.org/3";
export const IMG_URL = "https://image.tmdb.org/t/p/w500";
export const headers = {
  method: "GET",
  mode: "cors",
};
export const API_KEY = "api_key=" + process.env.REACT_APP_MOVIE_DB_API_KEY;
