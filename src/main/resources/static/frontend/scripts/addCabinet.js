let selectElement = document.getElementById("labSelector")

addEventListener("DOMContentLoaded", async function addLabOptions() {
    const labsFetch = await fetch("http://localhost:8080/lab/all");
    const labsJson = await labsFetch.json();

    for (let lab of labsJson) {
        let optionElement = document.createElement("option");
        optionElement.innerText = lab.laboratoryString;
        optionElement.setAttribute("value", lab.uuid);
        selectElement.appendChild(optionElement);
    }
})

async function addCabinet(event) {
    event.preventDefault();
    const cabinetName = document.getElementById("cabinetNameInput").value;
    const labUuid = document.getElementById("labSelector").value;
    let map = {"labUuid": labUuid, "cabinetName": cabinetName};
    const cabinetPost = await fetch(`http://localhost:8080/cabinet/`, {
        "method": "POST", "body": JSON.stringify(map),   headers: {
            "Content-Type": "application/json",
        }
    });
    const cabinetJson = await cabinetPost.json()
    console.log(cabinetJson);
}