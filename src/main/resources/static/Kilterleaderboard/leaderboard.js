async function getLeaderboard(url) {
    try {
        const response = await fetch(url + "/kilter/leaderboard", {
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

export async function updateLeaderboard(season) {
    const url = "https://jonasdrechsel.com";
    const leaderboard = document.getElementById("leaderboard");
    const leaderBoardData = await getLeaderboard(url);
    if (!leaderBoardData) {
        leaderboard.innerText = "There was an error fetching the leaderboard.";
        console.error("LeaderBoardData is undefined.");
    } else {
        const oldTBody = document.getElementById("rankedUsers");
        const newTBody = document.createElement('tbody');
        newTBody.id = "rankedUsers";
        for (let i = 0; i < leaderBoardData.length; i++) {
            const user = leaderBoardData[i];
            let row = newTBody.insertRow(-1);
            row.className = "row";

            let cellRank = row.insertCell(0);
            let cellName = row.insertCell(1);
            let cellPP = row.insertCell(2);
            let cellPPUnweighted = row.insertCell(3);
            let cellAscents = row.insertCell(4);
            let cellFlashes = row.insertCell(5);
            let cellHighestDifficulty = row.insertCell(6);
            // let cellUpdate = row.insertCell(6);

            cellRank.className = "cellRank cell cell0";
            cellName.className = "cellName cell cell1";
            cellPP.className = "cellPP cell cell2";
            cellPPUnweighted.className = "cellPPWeighted cell cell3";
            cellAscents.className = "cellAscents cell cell4";
            cellFlashes.className = "cellFlashes cell cell5";
            cellHighestDifficulty.className = "cellHighestDifficulty cell cell6";
            // cellUpdate.className = "cellUpdate cell cell7";

            let divPP = document.createElement("div");
            divPP.className = "ppCellDiv cellDiv cell1Div"
            if (season === 1) {
                divPP.innerText = user.ppWeightedSeason1;
            } else {
                divPP.innerText = user.ppWeighted;
            }
            cellPP.appendChild(divPP);

            let divPPUnweighted = document.createElement("div");
            divPPUnweighted.className = "ppUnweightedCellDiv cellDiv cell2Div"
            if (season === 1) {
                divPPUnweighted.innerText = user.ppUnweightedSeason1;
            } else {
                divPPUnweighted.innerText = user.ppUnweighted;
            }
            cellPPUnweighted.appendChild(divPPUnweighted);

            let divAscents = document.createElement("div");
            divAscents.className = "ascentCellDiv cellDiv cell3Div"
            divAscents.innerText = user.ascents;
            cellAscents.appendChild(divAscents);

            let divFlashes = document.createElement("div");
            divFlashes.className = "flashCellDiv cellDiv cell4Div"
            divFlashes.innerText = user.flashes;
            cellFlashes.appendChild(divFlashes);

            let divHighestDifficulty = document.createElement("div");
            divHighestDifficulty.className = "highestDifficultyCellDiv cellDiv cell5Div"
            divHighestDifficulty.innerText = user.highestDifficulty;
            cellHighestDifficulty.appendChild(divHighestDifficulty);

            // let divUpdate = document.createElement("div");
            // divUpdate.className = "updateCellDiv cellDiv"
            // divUpdate.innerText = "3 hours ago";
            // cellUpdate.appendChild(divUpdate);

            cellRank.innerText = (i + 1).toString();
            cellName.className = "nameCellDiv";
            const anchor = document.createElement('a');
            anchor.className = "name-link-leaderboard"
            anchor.href = `user/${user.username}`;
            anchor.textContent = user.username;
            cellName.appendChild(anchor);
        }
        leaderboard.replaceChild(newTBody, oldTBody);
    }
}


await updateLeaderboard(0);
// Buttons
let deg = 180;
document.getElementById("refresh").onclick = async () => {
    // document.getElementById("refresh").style.transition = "all .3s ease"
    document.getElementById("refresh").style.transform = "rotate(" + deg + "deg)";
    deg = deg + 180;
    await updateLeaderboard();
    // document.getElementById("refresh").style.transition = "all .3s ease"
};

