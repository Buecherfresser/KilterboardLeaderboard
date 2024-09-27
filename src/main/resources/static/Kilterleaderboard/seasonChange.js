import {updateLeaderboard} from "./leaderboard.js";

let savedSeason = 0;

async function changeSeason() {
    if (savedSeason === 0) {
        document.getElementById("season-button").innerText = "All Time";
        document.getElementById("season-header").innerText = "Season 1";
        document.getElementById("season-header-sub").innerText = "September 26th 2024 - October 26th 2024";
        savedSeason = 1;
    } else {
        document.getElementById("season-button").innerText = "Season 1";
        document.getElementById("season-header").innerText = "All Time";
        document.getElementById("season-header-sub").innerHTML = "<br>";

        savedSeason = 0;
    }
    await updateLeaderboard(savedSeason);
}

document.getElementById("season-button").addEventListener("click", changeSeason);
