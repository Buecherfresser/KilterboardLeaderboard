async function getClimbs(url, kilterUser) {
    try {
        console.log(kilterUser.id);
        const response = await fetch(url + `/kilter/climbs/${kilterUser.id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Accept": "*/*"
            },
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error("There was an error!", error);
        return undefined; // Return undefined in case of error
    }
}

async function updateProfile(kilterUser) {
    const url = "https://jonasdrechsel.com";
    const climbsTable = document.getElementById("climbsTable");
    let climbsData = await getClimbs(url, kilterUser);
    if (!climbsData) {
        climbsData.innerText = "There was an error fetching the leaderboard.";
        console.error("ClimbsData is undefined.");
    } else {
        climbsData = climbsData.sort((a, b) => {
            if (Number(a.pp) === Number(b.pp)) {
                if (Number(a.difficulty) === Number(b.difficulty)) {
                    // maybe here date?
                    return a.name.localeCompare(b.name);
                }
                return Number(b.difficulty) - Number(a.difficulty);
            }
            return Number(b.pp) - Number(a.pp);
        });
        const oldTBody = document.getElementById("climbsTable-body");
        const newTBody = document.createElement('tbody');
        newTBody.id = "climbsTable-body";
        for (let i = 0; i < climbsData.length; i++) {
            const climb = climbsData[i];
            const row = newTBody.insertRow(-1);
            row.className = "climbsTable-row";

            const climbsTableCellRank = row.insertCell(0);
            const climbsTableCellName = row.insertCell(1);
            const climbsTableCellDifficulty = row.insertCell(2);
            const climbsTableCellPP = row.insertCell(3);
            const climbsTableCellAngle = row.insertCell(4);
            const climbsTableCellTries = row.insertCell(5);
            const climbsTableCellFlash = row.insertCell(6);


            const climbsTableDivDifficulty = document.createElement("div");
            climbsTableDivDifficulty.className = "climbsTableCell-difficultyDiv cellDiv cell1Div"
            climbsTableDivDifficulty.innerText = climb.difficulty;
            climbsTableCellDifficulty.appendChild(climbsTableDivDifficulty);

            const climbsTableDivPp = document.createElement("div");
            climbsTableDivPp.className = "climbsTableCell-ppDiv cellDiv cell2Div"
            climbsTableDivPp.innerText = climb.pp;
            climbsTableCellPP.appendChild(climbsTableDivPp);

            const climbsTableDivAngle = document.createElement("div");
            climbsTableDivAngle.className = "climbsTableCell-angleDiv cellDiv cell3Div"
            climbsTableDivAngle.innerText = climb.angle;
            climbsTableCellAngle.appendChild(climbsTableDivAngle);

            const climbsTableDivTries = document.createElement("div");
            climbsTableDivTries.className = "climbsTableCell-triesDiv cellDiv cell4Div"
            climbsTableDivTries.innerText = climb.bid_count;
            climbsTableCellTries.appendChild(climbsTableDivTries);

            const climbsTableDivFlash = document.createElement("div");
            // if flash
            if (Number(climb.bid_count) === 1) {
                let img = document.createElement('img');
                img.className = "climbsTableCellDivImg-check"
                img.src = "../ressources/icons/lightning.png";
                img.alt = "yes";
                climbsTableDivFlash.appendChild(img);
            }
            climbsTableDivFlash.className = "climbsTableCell-flashesDiv cellDiv cell5Div"
            climbsTableCellFlash.appendChild(climbsTableDivFlash);

            climbsTableCellRank.innerText = (i + 1).toString();

            climbsTableCellName.className = "nameCell";
            climbsTableCellName.textContent = climb.name;
        }
        climbsTable.replaceChild(newTBody, oldTBody);
    }
}

// const kilterUser = JSON.parse("{\"id\":39718,\"username\":\"AlexanderMegos\",\"name\":null,\"pp\":789,\"ascents\":1192,\"flashes\":722,\"verified\":true,\"_type\":\"user\",\"avatar_image\":null,\"is_verified\":true,\"created_at\":\"2020-09-08 10:16:05.169902\"}");
// await updateProfile(kilterUser);