import { parseJwt } from "./funcs.js";

const url = "http://localhost:8080";

let loginButton = document.getElementById("login-btn");
let passField = document.getElementById("password");

const login = async () => {
  let loginDTO = {
    username: document.getElementById("username").value,
    password: document.getElementById("password").value
  };

  await fetch(`${url}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(loginDTO)
  })

    .then((response) => {
      return response.json();
    })
    .then((data) => {
      document.cookie = data.token;

      if (parseJwt(data.token).Role === "Finance Manager") {
        window.location.href = "manager.html";
      } else {
        window.location.href = "employee.html"
      }
    })
    .catch((error) => {
      document.getElementById("error").innerText = "Login Failed! Try again...";
    })
    .finally();
}

loginButton.onclick = () => login();
passField.onkeydown = (key) => {
  if (key.which == 13) {
    login();
  }
}