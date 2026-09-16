import styled from "styled-components";

const StyledSelector = styled.select`
    width: 60%;
`

function Selector({options=[], stringName, identifierName}) {
    const optElements = options.map(option => {
        return <option value={option[identifierName]} key={option[identifierName]}>{option[stringName]}</option>
    })

    return (
        <StyledSelector>
            {optElements}
        </StyledSelector>
    )
}
export default Selector;