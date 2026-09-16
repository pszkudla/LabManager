import styled from 'styled-components'

const StyledButton = styled.button`
    margin-top:20px;
    padding: 20px;
    border-radius: 15px;
    font-weight: 700;
    font-size: 30px;
    border: 2px solid black;
    background-color: transparent;
    &:hover {
        background-color: chartreuse;
    }
`

function ExecuteButton({clickFunction, text}) {
    return (
        <StyledButton onClick={clickFunction}>{text}</StyledButton>
    )
}

export default ExecuteButton;