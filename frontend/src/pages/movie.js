import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "../scss/index.scss";
import { API_KEY, BASE_URL, headers, IMG_URL } from "../util/movieDbApi";

export function Movie() {
  const [movie, setMovie] = useState(null);
  const params = useParams();

  useEffect(() => {
    async function fetchMovie() {
      const result = await fetch(
        `${BASE_URL}/movie/${params.id}?${API_KEY}&language=pt-BR&`,
        headers
      );
      const json = await result.json();
      setMovie(json);
    }
    fetchMovie();
  }, []);

  return (
    <section>
      {movie !== null && (
        <article>
          <h2>{movie.title}</h2>
          <img
            className="movie__image"
            width="100%"
            height="300"
            src={IMG_URL + movie.poster_path}
          />
        </article>
      )}
    </section>
  );
}
