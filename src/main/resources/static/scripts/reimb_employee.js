import { parseJwt } from "./funcs.js";

const url = "http://localhost:8080";
let user = parseJwt(document.cookie);
let statusSelect = document.getElementById("status");
let typeSelect = document.getElementById("type");
let createBtn = document.getElementById("create-btn");
let logoutBtn = document.getElementById("logout-btn");

document.getElementById("reimbursement-header").innerText =
  "Welcome, " + user.sub + "!";

window.onload = getReimbursements("/employee/reimbursements");

function makeRow(rowdata) {
  let row = document.createElement("tr");

  let columns = [];

  for (let i = 0; i < 5; ++i) {
    columns[i] = document.createElement("td");
  }

  columns[0].innerText = rowdata.id;
  columns[0].setAttribute("class", "id");
  columns[1].innerText = `$${rowdata.amount}`;
  columns[2].innerText = rowdata.description;
  columns[3].innerText = rowdata.type.type;
  columns[4].innerText = rowdata.status.name;

  for (let col of columns) {
    row.appendChild(col);
  }

  return row;
}

async function getReimbursements(path) {
  await fetch(`${url}${path}`, {
    method: "GET",
    headers: {
      "Authorization": "Bearer " + document.cookie,
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      data.map((reimb) => {
        let table = document.getElementById("table-body");
        table.appendChild(makeRow(reimb));
      });
    });
}

statusSelect.onchange = getByStatus;
typeSelect.onchange = getByExpenseType;

function getByStatus() {
  let table = document.getElementById("table-body");
  let path = "";

  table.innerHTML = "";

  switch (statusSelect.value) {
    case "All":
      path = "/employee/reimbursements";
      break;
    case "Pending":
      path = "/employee/reimbursements/pending";
      break;
    case "Approved":
      path = "/employee/reimbursements/approved";
      break;
    case "Denied":
      path = "/employee/reimbursements/denied";
      break;
    default:
      path = "/employee/reimbursements";
  }

  console.log(path)
  typeSelect.value = "--";
  getReimbursements(path);
}

function getByExpenseType() {
  let table = document.getElementById("table-body");
  let path = "";

  table.innerHTML = "";

  switch (typeSelect.value) {
    case "Travel":
      path = "/employee/reimbursements/travel";
      break;
    case "Lodging":
      path = "/employee/reimbursements/lodging";
      break;
    case "Food":
      path = "/employee/reimbursements/food";
      break;
    case "Other":
      path = "/employee/reimbursements/other";
      break;
    default:
      path = "/employee/reimbursements";
  }

  console.log(path);  
  statusSelect.value = "All";
  getReimbursements(path);
}

createBtn.onclick = () => {
  if (user.Role === "Finance Manager") {
    window.location.href = "m_create.html";
  } else {
    window.location.href = "create.html";
  }
};

logoutBtn.onclick = () => {
  window.location.href = "index.html";
}