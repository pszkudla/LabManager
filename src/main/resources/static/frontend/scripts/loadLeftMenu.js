function getLeftMenuOptions() {
    const menu = [
        {"name": "Lokalizacja i opakowania", "options": [
            {"name": "Dodaj opakowanie", "link": "addContainer.html"},
            {"name": "Dodaj strefę", "link": "addZone.html"},
            {"name": "Dodaj półkę", "link": "addShelf.html"},
            {"name": "Dodaj szafę", "link": "addCabinet.html"},
            {"name": "Dodaj laboratorium", "link": "addLaboratory.html"},
            {"name": "Zobacz laboratoria", "link": "getLaboratories.html"}
        ]
    }, {"name": "Substancje", "options": [
                {"name": "Dodaj substancję", "link": "addSubstance.html"},
                {"name": "Znajdź substancję", "link": "findSubstance.html"},
                {"name": "Edytuj substancje", "link": "editSubstance.html"},
                {"name": "Dodaj alternatywną nazwę substancji", "link": "addSubsAltName.html"},
                {"name": "Dodaj kartę charakterystyki", "link": "addSds.html"},
            ]
    }
    ]
    return menu;
}

function createLeftMenu() {
    let leftMenuElement = document.getElementById("leftNav")
    const menu = getLeftMenuOptions();
    for (let category of menu) {
        let headerInnerText = category.name;
        let headerElement = document.createElement("h3")
        headerElement.innerText = headerInnerText;
        leftMenuElement.appendChild(headerElement)
        for (let option of category.options) {
            let optionElement = document.createElement("a");
            optionElement.classList.add("navLink");
            optionElement.innerText = option.name;
            optionElement.href = option.link;
            leftMenuElement.appendChild(optionElement);
        }
    }
}

addEventListener("DOMContentLoaded", createLeftMenu())
