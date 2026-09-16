import styled from "styled-components";

const StyledSelector = styled.select`
    width: 60%;
`

function Selector({options=[], stringName, identifierName, identifierSetter}) {
    const optElements = options.map(option => {
        return <option
            value={option[identifierName]}
            key={option[identifierName]}
        >{option[stringName] }</option>
    })

    return (
        <StyledSelector onChange={identifierSetter}>
            <option value="">Wybierz...</option>
            {optElements}
        </StyledSelector>
    )
}
export default Selector;