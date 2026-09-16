import styled from 'styled-components'
import FormTextInput from '../recyclableComponents/FormTextInput.jsx'
import ExecuteButton from '../recyclableComponents/ExecuteButton.jsx'
import PostStatusModal from '../modals/PostStatusModal.jsx'
import Selector from "../recyclableComponents/Selector.jsx";
import {useEffect, useState} from "react";

function AddCabinet() {
    const initialState = {
        cabinetName: "",
        labOptions: [],
        chosenLab: null
    }

    const [formState, setFormState] = useState(initialState);

    function setNewFormState(propertyName, propertyValue) {
        setFormState(prevState => ({
            ...prevState,
            [propertyName]: propertyValue
        }));

    }

    useEffect(() => {
        async function getOptions() {
            const labsFetch = await fetch("http://localhost:8080/lab/all");
            const labsJson = await labsFetch.json();
            console.log(labsJson)
            setNewFormState("labOptions", labsJson);
        }
        getOptions();
    }, []);


    return (
        <>
            <FormTextInput
                headerText="Podaj nazwę szafki"
                placeholder="Nazwa szafki..."
                inputValue={formState.cabinetName}
                setStateFunction={(event) => setNewFormState("cabinetName", event.target.value)}
            /> <br/>
            <h2>Wybierz laboratorium</h2>
            <Selector options={formState.labOptions} stringName="laboratoryName" identifierName="uuid" >
            </Selector>


        </>
    )
}
export default AddCabinet;