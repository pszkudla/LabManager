import styled from "styled-components";
import {useState} from "react";
import FormTextInput from "../recyclableComponents/FormTextInput.jsx";

function AddShelf() {

    const initialState = {
        shelfName: "",
        labOptions: [],
        chosenLab: null
    }

    const [formState, setFormState] = useState(initialState);

    return (
        <>
            <FormTextInput headerText="Podaj nazwę szafki" placeholder="Nazwa szafki..." inputValue={formState.shelfName} setStateFunction={(event) => setFormState("cabinetName", event.target.value)} />
        </>
    )
}

export default AddShelf;