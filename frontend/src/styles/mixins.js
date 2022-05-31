import { css } from "styled-components";

export const flex = (direction, justifyContent, alignItems, gap) => css`
  display: flex;
  flex-direction: ${direction};
  justify-content: ${justifyContent};
  align-items: ${alignItems};
  gap: ${gap};
`;
