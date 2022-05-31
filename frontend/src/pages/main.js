import { useEffect, useState } from "react";
import { Search } from "../components/header/search";
import { Link } from "react-router-dom";
import { IMG_URL } from "../util/movieDbApi";
import styled from "styled-components";
import { flex } from "../styles/mixins";

const PageWrapper = styled.main`
  width: 100%;
`;

const MovieNotFound = styled.h2`
  color: red;
`;

const MoviesWrapper = styled.section`
  width: 100%;
  ${flex("column", "center", "center", 0)};
`;

const Movie = styled.article`
  color: #212121;
  width: 100%;
  max-width: 340px;
  background-color: white;
  padding: 2rem 1rem;
  margin-top: 2rem;

  h1 {
    font-size: 1.5rem;
    text-align: center;
  }

  img {
    max-width: 100%;
    border-radius: 5px;
  }
`;

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
    <PageWrapper>
      <Search
        changeState={{ setMovies, setQuery, setMovieNotFound }}
        states={{ query }}
      />
      <MoviesWrapper>
        {movieNotFound && (
          <MovieNotFound>
            Nenhum filme com o nome <span>{query}</span> foi encontrado
          </MovieNotFound>
        )}
        {movies.map((movie) => {
          return (
            <Link to={"movie/" + movie.id}>
              <Movie>
                <h1>{movie.title}</h1>
                <img src={IMG_URL + movie.poster_path} alt="movie poster" />
                <p>{movie.overview.slice(0, 150)}...</p>
              </Movie>
            </Link>
          );
        })}
        {movies.length > 10 && <button>Mostrar mais</button>}
      </MoviesWrapper>
    </PageWrapper>
  );
}
