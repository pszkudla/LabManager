const labSelector = document.getElementById("labSelector");
const cabinetSelector = document.getElementById("cabinetSelector");
const shelfSelector = document.getElementById("shelfSelector");
const zoneSelector = document.getElementById("zoneSelector");
const substanceNameInput = document.getElementById("substanceNameInput");
const substanceOptionsElement = document.getElementById("substanceOptions");
const hiddenInput = document.getElementById("substanceUuidHiddenInput");

const capacityInputElement = document.getElementById("capacityInputElement");
const supplierInputElement = document.getElementById("supplierInputElement");
const purityInputElement = document.getElementById("purityInputElement");
const notesInputElement = document.getElementById("notesInputElement");


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
    zoneSelector.innerHTML = "";

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

async function updateZoneOptions() {
    zoneSelector.innerHTML = "";
    const shelfUuid = shelfSelector.value;

    const zoneOptionsFetch = await fetch(`http://localhost:8080/zone/allZonesByShelfUuid/${shelfUuid}`);
    const zonesJson = await zoneOptionsFetch.json();

    for (let zone of zonesJson) {
        let zoneElement = document.createElement("option");
        zoneElement.innerText = zone.zoneString;
        zoneElement.value = zone.uuid;
        zoneSelector.append(zoneElement);
    }
}

async function updateShelfOptions() {
    shelfSelector.innerHTML = "";
    const cabinetUuid = cabinetSelector.value;

    const shelvesOptionsFetch = await fetch(`http://localhost:8080/shelf/allShelvesByCabinetUuid/${cabinetUuid}`);
    const shelvesJson = await shelvesOptionsFetch.json();

    for (let shelf of shelvesJson) {
        let shelfElement = document.createElement("option");
        shelfElement.innerText = shelf.shelfString;
        shelfElement.setAttribute("value", shelf.uuid);
        shelfSelector.append(shelfElement);
    }
}

function chooseSubstanceInContainer(uuid, substanceName) {
        hiddenInput.value = uuid;
        substanceNameInput.value = "";
        substanceNameInput.placeholder = substanceName;
        substanceOptionsElement.innerHTML = "";
}

async function searchSubstancesFromSubstring() {

    substanceOptionsElement.innerHTML = "";
    const substanceNameSubstring = substanceNameInput.value;

    if (substanceNameSubstring.length > 2) {
        const substancesFetch = await fetch(`http://localhost:8080/substances/getByNamesNativeQuery/${substanceNameSubstring}`);
        const substancesJson = await substancesFetch.json();
        console.log(substancesJson);
        for (let substance of substancesJson) {
            const optionElement = document.createElement("div");
            optionElement.classList.add("substanceOption");
            optionElement.setAttribute("onclick", `chooseSubstanceInContainer("${substance.uuid}", "${substance.iupacName}")`);

            let substanceNameEl = document.createElement("div");
            substanceNameEl.classList.add("substanceNameEl");
            substanceNameEl.innerText = substance.iupacName;
            optionElement.append(substanceNameEl);

            let substanceCasEl = document.createElement("div");
            substanceCasEl.classList.add("substanceCasEl");
            substanceCasEl.innerText = substance.casNumber;
            optionElement.append(substanceCasEl);


            substanceOptionsElement.append(optionElement)
        }
    }
}

async function addContainerToDb() {
    const zoneUuid = zoneSelector.value;
    const substanceUuid = hiddenInput.value;
    const capacity = document.getElementById("capacityInputElement").value;
    const supplier = document.getElementById("supplierInputElement").value;
    const purity = document.getElementById("purityInputElement").value;
    const notes = document.getElementById("notesInputElement").value;

    const map = {
        "substanceUuid": substanceUuid,
        "capacity": capacity,
        "notes": notes,
        "zoneUuid": zoneUuid,
        "supplier": supplier,
        "purity": purity
    }


    const postFetch = await fetch ("http://localhost:8080/container/", {
        method: "POST",
        body: JSON.stringify(map),
        headers: {"Content-Type": "application/json"}
    });
    const postJson = await postFetch.json();
    console.log(postJson);

    capacityInputElement.value = "";
    supplierInputElement.value = "";
    notesInputElement.value = "";
    purityInputElement.value = "";

}