import styled from "styled-components";
import {useEffect, useState} from "react";
import FormTextInput from "../recyclableComponents/FormTextInput.jsx";
import Selector from "../recyclableComponents/Selector.jsx";
import ExecuteButton from "../recyclableComponents/ExecuteButton.jsx";
import PostStatusModal from "../modals/PostStatusModal.jsx";

function AddShelf() {

    const initialState = {
        shelfName: "",
        labOptions: [],
        labUuid: "",
        cabinetOptions: [],
        cabinetUuid: ""
    }

    const [formState, setFormState] = useState(initialState);
    const [postState, setPostState] = useState(null);


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

    useEffect(() => {
        async function getCabinetOptions() {
            if (formState.labUuid.length > 0) {
                const cabinetsFetch = await fetch(`http://localhost:8080/cabinet/byLabUuid/${formState.labUuid}`)
                const cabinetsJson = await cabinetsFetch.json();
                console.log(cabinetsJson);
                setNewFormState("cabinetOptions", cabinetsJson);
            }
        }
        getCabinetOptions();
    }, [formState.labUuid])


    const addShelf = async() => {
        const shelfName = formState.shelfName;
        const cabinetUuid = formState.cabinetUuid;
        if (!shelfName || !cabinetUuid) {
            alert("Półka powinna mieć określoną nazwę i szafkę.")
        } else {
            const map = JSON.stringify({"shelfName": shelfName, "cabinetUuid": cabinetUuid})
            const response = await fetch("http://localhost:8080/shelf/",
                {method: "POST", body: map, headers: {"Content-Type": "application/json"}});
            if (response.ok) {
                setPostState({
                    "status": response.status,
                    "message": "Pomyślnie dodano półkę."
                });
                setFormState(initialState);
            } else {
                setPostState({
                    "status": response.status,
                    "message": "Nie udało się dodać półki."
                })
            }
        }
    }


    return (
        <>
            <FormTextInput headerText="Podaj nazwę szafki" placeholder="Nazwa szafki..." inputValue={formState.shelfName} setStateFunction={(event) => setNewFormState("shelfName", event.target.value)} />
            <h2>Wybierz laboratorium</h2>
            <Selector
                options={formState.labOptions}
                stringName="laboratoryName"
                identifierName="uuid"
                identifierSetter={(event) => setNewFormState("labUuid", event.target.value)}>

            </Selector>
            <h2>Wybierz szafkę</h2>
            <Selector
                options={formState.cabinetOptions}
                stringName="cabinetString"
                identifierName="uuid"
                identifierSetter={(event) => setNewFormState("cabinetUuid", event.target.value)}
            />

            <br/>

            <ExecuteButton text="Dodaj półkę" clickFunction={() => addShelf()}/>


            {postState && (
                <PostStatusModal
                    status={postState.status}
                    message={postState.message}
                    lifeTime={5000}
                    onClose={() => setPostState(null)}
                />
            )}
        </>
    )
}

export default AddShelf;