import { parseJwt } from "./funcs.js";

const url = "http://localhost:8080";

let regButton = document.getElementById("reg-btn");
let passField = document.getElementById("password");

const register = async () => {
  let registerDTO;
  let password = document.getElementById("password").value;
  let passwordCheck = document.getElementById("password-check").value;

  if (
    password != "" &&
    passwordCheck != "" &&
    password === passwordCheck &&
    password.length >= 8
  ) {
    registerDTO = {
      firstName: document.getElementById("firstname").value,
      lastName: document.getElementById("lastname").value,
      username: document.getElementById("username").value,
      password: password,
    };

    await fetch(`${url}/auth/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(registerDTO),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);

        if (data.message !== "" && data.message !== undefined) {
          document.getElementById("error").innerText = data.message;
        }
      })
      .catch((error) => console.log(error))
      .finally(() => {
        window.location.href = "/index.html";
      });
  } else {
    if (password.length < 8) {
      document.getElementById("error").innerText = "Passwords need to 8 characters or longer...";
    } else {
      document.getElementById("error").innerText =
        "Your passwords did not match!";
    }
  }
};

regButton.onclick = () => register();
passField.onkeydown = (key) => {
  if (key.which == 13) {
    register();
  }
};
