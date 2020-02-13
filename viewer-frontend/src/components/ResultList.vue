<template>
  <table class="table table-striped">
    <thead>
      <tr>
        <th v-for="{heading, index} in tableHeadings" :key="index">{{heading}}</th>
      </tr>
    </thead>

    <tbody>
      <TableRow
        v-for="{ row, index } in rowData"
        v-bind:row="row"
        :key="index"
        :headings="tableHeadings"
      ></TableRow>
    </tbody>
  </table>
</template>

<script>
import TableRow from "./ResultListRow";
export default {
  data() {
    return {
      tableHeadings: [
        { name: "Weekly Rank", value: "rank" },
        { name: "Name", value: "name" },
        { name: "Team", value: "team" },
        { name: "Rec. Targets", value: "recTarget" },
        { name: "Receptions", value: "receptions" },
        { name: "Rec. Yards", value: "recYards" },
        { name: "Rec. TD", value: "recTD" },
        { name: "Rush Attempts", value: "rushAtt" },
        { name: "Rush Yards", value: "rushYards" },
        { name: "Rush TD", value: "rushTD" },
        { name: "Week's Points", value: "fantasyPts" },
        { name: "Total Points", value: "fantasyPtsPerGame" }
      ],
      rowData: [
        {
          rank: 1,
          name: "Brandin Cooks",
          team: "NO",
          recTarget: 9,
          receptions: 6,
          recYards: 143,
          recTD: 2,
          rushAtt: 1,
          rushYards: 11,
          rushTD: 0,
          fantasyPts: 27.4,
          fantasyPtsPerGame: 27.4
        },
        {
          rank: 2,
          name: "Antonio Brown",
          team: "PIT",
          recTarget: 11,
          receptions: 8,
          recYards: 126,
          recTD: 2,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 24.6,
          fantasyPtsPerGame: 24.6
        },
        {
          rank: 3,
          name: "A.J. Green",
          team: "CIN",
          recTarget: 13,
          receptions: 12,
          recYards: 180,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 24.0,
          fantasyPtsPerGame: 24.0
        },
        {
          rank: 4,
          name: "Willie Snead",
          team: "NO",
          recTarget: 9,
          receptions: 9,
          recYards: 172,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 23.2,
          fantasyPtsPerGame: 23.2
        },
        {
          rank: 5,
          name: "Larry Fitzgerald",
          team: "ARI",
          recTarget: 10,
          receptions: 8,
          recYards: 81,
          recTD: 2,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 20.1,
          fantasyPtsPerGame: 20.1
        },
        {
          rank: 6,
          name: "Jordan Matthews",
          team: "PHI",
          recTarget: 14,
          receptions: 7,
          recYards: 114,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 17.4,
          fantasyPtsPerGame: 17.4
        },
        {
          rank: 7,
          name: "Will Fuller",
          team: "HOU",
          recTarget: 11,
          receptions: 5,
          recYards: 107,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 16.7,
          fantasyPtsPerGame: 16.7
        },
        {
          rank: 8,
          name: "Mike Wallace",
          team: "BAL",
          recTarget: 6,
          receptions: 3,
          recYards: 91,
          recTD: 1,
          rushAtt: 1,
          rushYards: 11,
          rushTD: 0,
          fantasyPts: 16.2,
          fantasyPtsPerGame: 16.2
        },
        {
          rank: 9,
          name: "Mike Evans",
          team: "TB",
          recTarget: 7,
          receptions: 5,
          recYards: 99,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 15.9,
          fantasyPtsPerGame: 15.9
        },
        {
          rank: 10,
          name: "Doug Baldwin",
          team: "SEA",
          recTarget: 11,
          receptions: 9,
          recYards: 92,
          recTD: 1,
          rushAtt: 0,
          rushYards: 0,
          rushTD: 0,
          fantasyPts: 15.2,
          fantasyPtsPerGame: 15.2
        }
      ]
    };
  }
};
</script>

<style>
</style>
