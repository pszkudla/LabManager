import styled from 'styled-components'


const MenuContainer = styled.div`
    width: 15%;
    text-align: center;
`

const ButtonOption = styled.div`
    font-family: monospace;
    margin-left: 20px;
    margin-right: 20px;
    font-size: 12px;
    cursor: pointer;
    &:hover {
        font-weight: 700;
    }
`

function LeftMenu({setContentFunction}) {
    return (
        <MenuContainer>
            <h2>Lokalizacja</h2>
            <ButtonOption onClick={() => setContentFunction("addLab")}>Dodaj laboratorium</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("addCabinet")}>Dodaj szafę</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("addShelf")}>Dodaj półkę</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("addZone")}>Dodaj strefę</ButtonOption>

            <h2>Substancje</h2>
            <ButtonOption onClick={() => setContentFunction("addSubstance")}>Dodaj substancję</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("findSubstance")}>Znajdź substancję</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("editLab")}>Edytuj substancję</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("addSubsAltName")}>Dodaj alternatywną nazwę substancji</ButtonOption>
            <ButtonOption onClick={() => setContentFunction("addSds")}>Dodaj kartę charakterystyki</ButtonOption>
        </MenuContainer>
    )
}
export default LeftMenu;