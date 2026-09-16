import styled from 'styled-components'
import FormTextInput from '../recyclableComponents/FormTextInput.jsx'
import ExecuteButton from '../recyclableComponents/ExecuteButton.jsx'
import PostStatusModal from '../modals/PostStatusModal.jsx'
import {useState} from "react";

function AddLaborary() {
    const initialState = {
        "labName": "",
        "roomNumber": ""
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

    const addLab = async() => {
        const data = {"laboratoryName": formState.labName, "roomNumber": formState.roomNumber}
        const response = await fetch("http://localhost:8080/lab/", {
            method: "POST", headers: {
                "Content-Type": "application/json"
            }, body: JSON.stringify(data)
        });

        if (response.ok) {
            setPostState({
                "status": response.status,
                "message": "Pomyślnie dodano laboratorium."
            });
            setFormState(initialState);
        } else {
            setPostState({
                "status": response.status,
                "message": "Nie udało się dodać laboratorium."
            })
        }
    }

    return (
        <>
            <FormTextInput headerText="Nazwa laboratorium" setStateFunction={(event) => setNewFormState("labName", event.target.value)} inputValue={formState.labName}/>
            <FormTextInput headerText="Numer pokoju" setStateFunction={(event) => setNewFormState("roomNumber", event.target.value)} inputValue={formState.roomNumber}/>

            <br/>
            <ExecuteButton text="Dodaj laboratorium" clickFunction={() =>addLab()}/>


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
export default AddLaborary;