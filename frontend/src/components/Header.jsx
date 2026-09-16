import styled from "styled-components";

const HeaderStyled = styled.header`
    text-align: center;
    font-weight: 700;
    font-family: monospace;
    font-size: 30px;
    height: 80px;
    border-bottom: 1px solid black;
    align-content: center;
    `

function Header() {
    return (
        <HeaderStyled id="header">Baza danych odczynników</HeaderStyled>
    )
}

export default Header