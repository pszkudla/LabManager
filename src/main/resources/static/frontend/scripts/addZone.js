const labSelector = document.getElementById("labSelector");
const cabinetSelector = document.getElementById("cabinetSelector");
const shelfSelector = document.getElementById("shelfSelector");

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

async function updateShelfOptions() {
    shelfSelector.innerHTML = "";
    const cabinetUuid = cabinetSelector.value;

    const shelfvesOptionsFetch = await fetch(`http://localhost:8080/shelf/allShelvesByCabinetUuid/${cabinetUuid}`);
    const shelvesJson = await shelfvesOptionsFetch.json();

    for (let shelf of shelvesJson) {
        let shelfElement = document.createElement("option");
        shelfElement.innerText = shelf.shelfString;
        shelfElement.setAttribute("value", shelf.uuid);
        shelfSelector.append(shelfElement);
    }
}

async function addZone(event) {
    event.preventDefault();
    const shelfUuid = shelfSelector.value;
    const zoneName = document.getElementById("zoneNameInput").value;
    const map = {"shelfUuid": shelfUuid, "zoneName": zoneName};

    console.log(map);
    if (!shelfUuid || !zoneName) {
        alert("Strefa powinna mieć określoną nazwę i półkę.")
    } else {
        const postReq = await fetch(`http://localhost:8080/zone/`,
            {
                method: "POST",
                body: JSON.stringify(map),
                headers: {"Content-Type": "application/json"}
            });
        const response = await postReq.text();
        console.log(response);
    }
}