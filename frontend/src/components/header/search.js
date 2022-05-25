import search from "../../assets/search.png";
import { useState } from "react";
import { IMG_URL, API_KEY, BASE_URL, headers } from "../../util/movieDbApi";

export function Search(props) {
  console.log(props);
  const { setMovies, setQuery, setMovieNotFound } = props.changeState;
  const { query } = props.states;
  const [queryPages, setQueryPages] = useState();

  const searchMovie = async () => {
    const response = await fetch(
      `${BASE_URL}/search/movie?${API_KEY}&language=pt-BR&query=${query}`,
      headers
    );
    const result = await response.json();
    setMovies(result.results);
  };

  return (
    <div className="page__input-ctn">
      <input
        onChange={(e) => {
          setMovieNotFound(false);
          setQuery(e.target.value);
        }}
        onKeyDown={(e) => {
          if (e.key == "Enter") {
            searchMovie();
          }
        }}
        type="text"
        placeholder="Digite o filme que quer assistir"
      />
      <button onClick={searchMovie} className="search-btn">
        <img src={search} alt="" width="30" />
      </button>
    </div>
  );
}
