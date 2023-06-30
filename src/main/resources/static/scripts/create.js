import { parseJwt } from "./funcs.js";

const url = "http://localhost:8080";
let user = parseJwt(document.cookie);
let newBtn = document.getElementById("new-btn");

document.getElementById("create-header").innerText =
  "Reimbursement for " + user.sub;

const create = async () => {
  let typeId = document.getElementById("type").value;
  let type;

  switch (typeId) {
    case "1":
      type = "Travel";
      break;
    case "2":
      type = "Lodging";
      break;
    case "3":
      type = "Food";
      break;
    case "4":
      type = "Other";
      break;
  }

  let reimbursement = {
    amount: document.getElementById("amount").value,
    description: document.getElementById("description").value,
    type: {
      id: typeId,
      type: type,
    },
  };

  await fetch(`${url}/employee/reimbursements/new`, {
    method: "POST",
    headers: {
      Authorization: "Bearer " + document.cookie,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(reimbursement),
  })
    .then((response) => {
      if (user.Role === "Finance Manager") {
        window.location.href = "manager.html";
      } else {
        window.location.href = "employee.html";
      }
      return response.json();
    })  
    .catch((error) => {
      document.getElementById("error").innerText = error;
    });
};

newBtn.onclick = () => create();
