import search from "../../assets/search.png";
import { useState } from "react";
import { IMG_URL, API_KEY, BASE_URL, headers } from "../../util/movieDbApi";
import styled from "styled-components";

const SearchWrapper = styled.div`
  input {
    position: relative;
    padding: 1rem;
    padding-left: 0.5rem;
    font-size: 1.25rem;
    width: 100vw;
    background-color: white;
  }
`;

const SearchButton = styled.button`
  padding: 0.5rem 1rem;
  position: absolute;
  top: 0;
  right: 0;
  height: 56px;
  width: 56px;
  background-color: #fff5b2;
  cursor: pointer;

  img {
    width: 25px;
    height: 25px;
    object-fit: center;
  }
`;

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
    <SearchWrapper>
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
      <SearchButton onClick={searchMovie}>
        <img src={search} alt="" width="30" />
      </SearchButton>
    </SearchWrapper>
  );
}
