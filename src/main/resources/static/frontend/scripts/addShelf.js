const labSelector = document.getElementById("labSelector");
const cabinetSelector = document.getElementById("cabinetSelector");

addEventListener("DOMContentLoaded", async function addLabOptions() {
    const labsFetch = await fetch("http://localhost:8080/lab/all");
    const labsJson = await labsFetch.json();

    for (let lab of labsJson) {
        let optionElement = document.createElement("option");
        optionElement.innerText = lab.laboratoryString;
        optionElement.setAttribute("value", lab.uuid);
        labSelector.appendChild(optionElement);
    }
})

async function updateCabinetOptions() {
    cabinetSelector.innerHTML = "";

    const labUuid = labSelector.value;
    const cabinetsFetch = await fetch(`http://localhost:8080/cabinet/byLabUuid/${labUuid}`)
    const cabinetsJson = await cabinetsFetch.json();

    for (let cabinet of cabinetsJson) {
        let cabinetElement = document.createElement("option");
        cabinetElement.innerText = cabinet.cabinetString;
        cabinetElement.setAttribute("value", cabinet.uuid);
        cabinetSelector.append(cabinetElement);
    }


}

async function addShelf(event) {


    event.preventDefault();
    const shelfName = document.getElementById("shefNameInput").value;
    const cabinetUuid = cabinetSelector.value;

    if (!cabinetUuid || !shelfName) {
        alert("Półka powinna mieć określoną nazwę i szafę.")
    } else {
        const map = JSON.stringify({"shelfName": shelfName, "cabinetUuid": cabinetUuid})

        const postRequest = await fetch("http://localhost:8080/shelf/",
            {method: "POST",
                body: map,
                headers: {"Content-Type": "application/json"}
            }
            );
        const responseText = await postRequest.text();
        console.log(responseText);
        document.getElementById("shefNameInput").value = "";
    }


}