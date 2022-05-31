import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Main } from "./pages/main";
import { Movie } from "./pages/movie";
import { createGlobalStyle } from "styled-components";
import { Search } from "./components/header/search";

const GlobalStyle = createGlobalStyle`
  * {
    margin: 0;
    padding: 0;
    font-family: sans-serif;
    box-sizing: border-box;
    background-color: transparent;
    outline: none;
    border: none;
    outline:  none;
    list-style-type: none;
    list-style-position: inside;
    text-decoration: none;
  }

  body {
    background: #212121;
  }
`;

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/" element={<Main />} />
        <Route path="/movie/:id" element={<Movie />} />
      </Routes>
      <GlobalStyle />
    </Router>
  );
}

export default App;
