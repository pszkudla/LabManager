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
        labUuid: ""
    }

    const [formState, setFormState] = useState(initialState);

    function setNewFormState(propertyName, propertyValue) {
        setFormState(prevState => ({
            ...prevState,
            [propertyName]: propertyValue
        }));
        console.log(formState)
    }

    useEffect(() => {
        async function getLabOptions() {
            const labsFetch = await fetch("http://localhost:8080/lab/all");
            const labsJson = await labsFetch.json();
            console.log(labsJson)
            setNewFormState("labOptions", labsJson);
        }
        getLabOptions();
    }, []);


    const addCabinet = async() => {
        let map = {"labUuid": formState.labUuid, "cabinetName": formState.cabinetName};
        const cabinetPost = await fetch(`http://localhost:8080/cabinet/`, {
            "method": "POST", "body": JSON.stringify(map), headers: {"Content-Type": "application/json"}
        });
        const postJson = await cabinetPost.json();
        console.log(postJson);
    }

    return (
        <>
            <FormTextInput
                headerText="Podaj nazwę szafki"
                placeholder="Nazwa szafki..."
                inputValue={formState.cabinetName}
                setStateFunction={(event) => setNewFormState("cabinetName", event.target.value)}
            /> <br/>
            <h2>Wybierz laboratorium</h2>
            <Selector options={formState.labOptions}
                      stringName="laboratoryName"
                      identifierName="uuid"
                      identifierSetter={(event) =>
                          setNewFormState("labUuid", event.target.value)
                      }>

            </Selector>
            <br/>

            <ExecuteButton clickFunction={addCabinet} text="Dodaj szafkę."/>
        </>
    )
}
export default AddCabinet;