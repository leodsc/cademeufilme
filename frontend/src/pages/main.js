import { useEffect, useState } from "react";
import { Search } from "../components/header/search";
import { Link } from "react-router-dom";
import { IMG_URL } from "../util/movieDbApi";
import "../scss/index.scss";

export function Main() {
  const [movieNotFound, setMovieNotFound] = useState(false);
  const [movies, setMovies] = useState([]);
  const [query, setQuery] = useState("");

  useEffect(() => {
    movies.length == 0 && query != ""
      ? setMovieNotFound(true)
      : setMovieNotFound(false);
  }, [movies]);

  return (
    <main className="page">
      <Search
        changeState={{ setMovies, setQuery, setMovieNotFound }}
        states={{ query }}
      />
      <div className="page__movies-ctn">
        {movieNotFound && (
          <h2>
            Nenhum filme com o nome <span>{query}</span> foi encontrado
          </h2>
        )}
        {movies.map((movie) => {
          return (
            <Link to={"movie/" + movie.id}>
              <div className="movie">
                <h1 className="movie__title">{movie.title}</h1>
                <img
                  className="movie__image"
                  src={IMG_URL + movie.poster_path}
                  alt="movie poster"
                />
                <p className="movie__description">{movie.overview}</p>
              </div>
            </Link>
          );
        })}
        {movies.length > 10 && (
          <button className="show-more-btn">Mostrar mais</button>
        )}
      </div>
    </main>
  );
}
