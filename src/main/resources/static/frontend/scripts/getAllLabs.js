async function getAllLabsData() {
    const labsFetch = await fetch("http://localhost:8080/lab/all");
    const labsJson = await labsFetch.json();
    return labsJson;
}

async function showAllLabs() {
    const container = document.getElementById("mainContent");
    const labsJson = await getAllLabsData();
    console.log(labsJson)
    for (let lab of labsJson) {
        const labDiv = document.createElement("div");
        console.log(lab);
        labDiv.innerText = lab.laboratoryString;
        container.appendChild(labDiv);
    }
}

addEventListener("DOMContentLoaded", showAllLabs());