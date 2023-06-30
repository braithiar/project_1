import { parseJwt } from "./funcs.js";

const url = "http://localhost:8080";
let user = parseJwt(document.cookie);
let approveBtn = document.getElementById("approve-btn");
let denyBtn = document.getElementById("deny-btn");
let statusSelect = document.getElementById("status");
let typeSelect = document.getElementById("type");
let createBtn = document.getElementById("create-btn");
let logoutBtn = document.getElementById("logout-btn");

document.getElementById("reimbursement-header").innerText =
  "Welcome, " + user.sub + "!";

window.onload = getReimbursements("/reimbursements");
console.log(parseJwt(document.cookie))
function makeRow(rowdata) {
  let row = document.createElement("tr");

  let columns = [];

  for (let i = 0; i < 6; ++i) {
    columns[i] = document.createElement("td");
  }
  console.log(rowdata);
  columns[6] = document.createElement("td");
  columns[6].innerHTML = `<input type="checkbox" id="select${rowdata.id}">`;

  columns[0].innerText = rowdata.id;
  columns[0].setAttribute("class", "id");
  columns[1].innerText = `${rowdata.user.firstName} ${rowdata.user.lastName}`;
  columns[2].innerText = `$${rowdata.amount}`;
  columns[3].innerText = rowdata.description;
  columns[4].innerText = rowdata.type.type;
  columns[5].innerText = rowdata.status.name;

  for (let col of columns) {
    row.appendChild(col);
  }

  return row;
}

async function getReimbursements(path) {
  await fetch(`${url}${path}`, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + document.cookie,
      "Content-Type": "application/json",
    },
  })
    .then((response) => {
      console.log(`${url}${path}`)
      return response.json();
    })
    .then((data) => {
      console.log(data);
      data.map((reimb) => {
        let table = document.getElementById("table-body");
        table.appendChild(makeRow(reimb));
      });
    });
}

approveBtn.onclick = () => {
  let table = document.getElementById("reimbs");
  let ids = table.getElementsByClassName("id");
  let checkboxes = table.getElementsByTagName("input");
  let updateUrl = `${url}/reimbursements/status-update`;
  let updates = [];

  for (let i = 0; i < checkboxes.length; ++i) {
    if (checkboxes[i].checked) {
      updates.push({
        id: ids[i].textContent,
        status: {
          id: 2,
          name: "Approved",
        },
      });
    }
  }

  update(updateUrl, updates);

  location.reload();
};

denyBtn.onclick = () => {
  let table = document.getElementById("reimbs");
  let ids = table.getElementsByClassName("id");
  let checkboxes = table.getElementsByTagName("input");
  let updateUrl = `${url}/reimbursements/status-update`;
  let updates = [];

  for (let i = 0; i < checkboxes.length; ++i) {
    if (checkboxes[i].checked) {
      updates.push({
        id: ids[i].textContent,
        status: {
          id: 3,
          name: "Denied",
        },
      });
    }
  }

  update(updateUrl, updates);

  location.reload();
};

statusSelect.onchange = getByStatus;
typeSelect.onchange = getByExpenseType;

function getByStatus() {
  let table = document.getElementById("table-body");
  let path = "";

  table.innerHTML = "";

  switch (statusSelect.value) {
    case "All":
      path = "/reimbursements";
      break;
    case "Pending":
      path = "/reimbursements/pending";
      break;
    case "Approved":
      path = "/reimbursements/approved";
      break;
    case "Denied":
      path = "/reimbursements/denied";
      break;
    default:
      path = "/reimbursements";
  }

  typeSelect.value = "--";
  getReimbursements(path);
}

function getByExpenseType() {
  let table = document.getElementById("table-body");
  let path = "";

  table.innerHTML = "";

  switch (typeSelect.value) {
    case "Travel":
      path = "/reimbursements/travel";
      break;
    case "Lodging":
      path = "/reimbursements/lodging";
      break;
    case "Food":
      path = "/reimbursements/food";
      break;
    case "Other":
      path = "/reimbursements/other";
      break;
    default:
      path = "/reimbursements";
  }

  statusSelect.value = "All";
  getReimbursements(path);
}

let update = async (updateUrl, updates) => {
  Promise.all(
    updates.map((update) => {
      console.log(update);
      console.log(updateUrl);
      fetch(updateUrl, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + document.cookie,
        },
        body: JSON.stringify(update),
      }).then((res) => res.json());
    })
  ).catch((error) => alert(error));
};

createBtn.onclick = () => {
  window.location.href = "create.html";
};

logoutBtn.onclick = () => {
  window.location.href = "index.html";
};
