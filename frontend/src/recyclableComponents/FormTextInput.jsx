import styled from "styled-components";

const StyledInput = styled.input`
    width: 60%;
    border-radius: 15px;
    padding:5px;
    border: 2px solid black;
`

function FormTextInput({headerText, inputValue, placeholder, setStateFunction}) {
    return (
        <>
            <h3>{headerText}</h3>
            <StyledInput type="text" value={inputValue} placeholder={placeholder} onChange={setStateFunction} />
        </>
    )
}
export default FormTextInput;