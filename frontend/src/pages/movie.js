import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { API_KEY, BASE_URL, headers, IMG_URL } from "../util/movieDbApi";
import styled from "styled-components";
import { flex } from "../styles/mixins";

const PageWrapper = styled.main`
  padding: 1rem 2rem;
`;

const MovieWrapper = styled.section`
  h1 {
    color: white;
    text-align: center;
  }
`;

const Poster = styled.img`
  width: 250px;
  height: 350px;
`;

const Streaming = styled.img`
  width: 100px;
  height: 100px;
`;

const InfoWrapper = styled.aside`
  ${flex("row", "center", "center", "1rem")};
`;

const Info = styled.div`
  border: 1px solid white;
  height: 75px;
  width: 75px;
`;

const Synopsis = styled.p`
  color: white;
  text-align: justify;
  font-size: 1.1rem;
`;

export function Movie() {
  const [movie, setMovie] = useState(null);
  const params = useParams();
  const [streaming, setStreaming] = useState([]);
  const streamingsIcons = {
    netflix:
      "https://w0.peakpx.com/wallpaper/656/1013/HD-wallpaper-netflix-logo-netflix-logo-black-red-design.jpg",
    hbo: "https://hbomax-images.warnermediacdn.com/2020-05/square%20social%20logo%20400%20x%20400_0.png",
  };

  useEffect(() => {
    async function fetchMovie() {
      const result = await fetch(
        `${BASE_URL}/movie/${params.id}?${API_KEY}&language=pt-BR&`,
        headers
      );
      const json = await result.json();
      setMovie(json);
      const queryTitle = json.title.replaceAll(" ", "+").toLowerCase();
      console.log(queryTitle);
      const streamings = await fetch(
        `http://localhost:8080/movies?title=${queryTitle}`,
        {
          method: "GET",
          mode: "cors",
        }
      );
      const moviesStreaming = await streamings.json();
      setStreaming(moviesStreaming);
    }
    fetchMovie();
  }, []);

  return (
    <PageWrapper>
      {movie !== null && (
        <MovieWrapper>
          <h1>{movie.title}</h1>
          <InfoWrapper>
            <Poster src={IMG_URL + movie.poster_path} />
            <div>
              {Array(4)
                .fill(0)
                .map(() => {
                  return <Info></Info>;
                })}
            </div>
          </InfoWrapper>
          <section>
            {streaming.length != 0 ? (
              streaming.map((s) => {
                return <Streaming src={streamingsIcons[s.streaming]} />;
              })
            ) : (
              <h3>Não tem em nenhum streaming do banco de dados</h3>
            )}
          </section>
          <Synopsis>{movie.overview}</Synopsis>
        </MovieWrapper>
      )}
    </PageWrapper>
  );
}
