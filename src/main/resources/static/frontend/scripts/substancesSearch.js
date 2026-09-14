let substancesContainer = document.getElementById("substanceTilesContainer");

function clearCasInput() {
    document.getElementById("searchSubstanceByCasInput").value = "";
}

function clearNameInput() {
    document.getElementById("searchSubstanceByNameInput").value = "";
}

async function onNameInputChange() {
    clearCasInput();
    substancesContainer.innerHTML = "";
}

async function onCasInputChange() {
    clearNameInput();
    substancesContainer.innerHTML = "";
}


function createSubstanceTile(substanceJson) {
    let substanceTile = document.createElement("div");
    substanceTile.classList.add("substanceTile");
    if (substanceJson.photoDir != null) {
        const photoDir = `http://localhost:8080/images/${substanceJson.photoDir}`
        const photoElement = document.createElement("img");
        photoElement.src = photoDir;
        photoElement.classList.add("substancePhoto");
        substanceTile.appendChild(photoElement);



    } else {
        const pseudoPhotoElement = document.createElement("div");
        pseudoPhotoElement.innerText = "?";
        pseudoPhotoElement.classList.add("substancePhoto");
        substanceTile.appendChild(pseudoPhotoElement);

    }

    const iupacNameElement = document.createElement("h4");
    iupacNameElement.innerText = substanceJson.iupacName;
    substanceTile.appendChild(iupacNameElement);

    const cas = substanceJson.casNumber;
    const casElement = document.createElement("h4");
    casElement.innerText = cas;
    substanceTile.appendChild(casElement);

    substanceTile.onclick = async () => {
        await createSubstanceModal(substanceJson.uuid);
    };

    return substanceTile;

}


async function showSubstance(uuid) {
    console.log(uuid)
}

async function searchByCasAfterClick() {
    console.log("Szukam po CAS.")

    let inputValue = document.getElementById("searchSubstanceByCasInput").value;
    if (inputValue.length > 3) {
        const substacesFetch = await fetch(`http://localhost:8080/substances/fromCas/${inputValue}`);
        const substancesJson = await substacesFetch.json();
        const tiles = substancesJson.map((substance) => {
            return createSubstanceTile(substance);
        });
        substancesContainer.append(...tiles);
    }
}

async function searchByNameAfterButtonClick() {
    console.log("Szukam po nazwie.")
    let inputValue = document.getElementById("searchSubstanceByNameInput").value;
    if (inputValue.length > 3) {
        const substacesFetch = await fetch(`http://localhost:8080/substances/fromSubstring/${inputValue}`);
        const substancesJson = await substacesFetch.json();
        if (substancesJson.length > 0) {
            const tiles = substancesJson.map((substance) => {
                return createSubstanceTile(substance);
            });
            substancesContainer.append(...tiles);


        } else {
            const shownMessage = `<h2>Nie znaleziono związków spełniających podane kryteria.</h2>`;
            substancesContainer.innerHTML += shownMessage;
        }

    } else {
        alert("Aby wyszukiwać po nazwie, trzeba podać co najmniej trzy znaki.")
    }
}

async function createSubstanceModal(substanceUuid) {
    const modal = document.createElement("div");
    modal.classList.add("modal");
    document.body.append(modal);

    const closeBelt = document.createElement("div");
    closeBelt.classList.add("closeBelt");
    modal.append(closeBelt);

    const closeCircle = document.createElement("div");
    closeCircle.innerText = "X";
    closeCircle.classList.add("closeCircle");
    closeBelt.append(closeCircle);
    closeCircle.onclick= () => closeModal(modal);

    const modalContentContainer = document.createElement("div");
    modalContentContainer.classList.add("modalContentContainer");
    modal.append(modalContentContainer)


    const containersFetch = await fetch(`http://localhost:8080/container/findContainersBySubstance/${substanceUuid}`);
    const containersJson = await containersFetch.json();
    console.log(containersJson.length)
    if (containersJson.length == 0) {
        modal.innerHTML += "<h2>Brak substancji w laboratorium</h2>"
    } else {
        const table = document.createElement("table");
        table.classList.add("tableStyle")
        modalContentContainer.append(table);
        const header = document.createElement("tr");
        table.append(header)
        const colNames = ["Pojemność", "Uwagi", "Położenie"]
        for (let name of colNames) {
            const cell = document.createElement("th");
            cell.innerText = name;
            header.append(cell);
        }
        for (let container of containersJson) {
            const row = document.createElement("tr");
            table.append(row);

            const capacityCell = document.createElement("td");
            capacityCell.innerText = container.capacity
            row.append(capacityCell);

            const notesCell = document.createElement("td");
            notesCell.innerText = container.containerNotes;
            row.append(notesCell);

            const locationCell = document.createElement("td");
            locationCell.innerText = container.zoneString;
            row.append(locationCell);
        }
    }

}

function closeModal(modal) {
    modal.remove();
}