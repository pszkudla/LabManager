async function addLaboratory() {
    const labName = document.getElementById("labNameInput").value;
    const labNumber = document.getElementById("roomNumberInput").value;
    const data = {"laboratoryName": labName, "roomNumber": labNumber}

    debugger;

    if (data.laboratoryName == null || data.roomNumber == null) {
        alert("Laboratorium musi mieć określoną nazwę oraz numer pokoju.")
    } else {
        const labPost = await fetch("http://localhost:8080/lab/", {
            method: "POST", headers: {
                "Content-Type": "application/json"
            }, body: JSON.stringify(data)
        });
        const fetchResult = await labPost.json();
        console.log(fetchResult);
        document.getElementById("labNameInput").value = "";
        document.getElementById("roomNumberInput").value = "";
    }
}