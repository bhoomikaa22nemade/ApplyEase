// ===============================
// ApplyEase Register
// ===============================

document.addEventListener("DOMContentLoaded", () => {

    const registerForm = document.getElementById("registerForm");

    registerForm.addEventListener("submit", async function (event)  {

        event.preventDefault();

        const name = document.getElementById("name").value.trim();
        const email = document.getElementById("email").value.trim();
        const phone = document.getElementById("phone").value.trim();
        const experience = document.getElementById("experience").value;
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirmPassword").value;

        if (
            name === "" ||
            email === "" ||
            phone === "" ||
            experience === "Experience"
        ) {
            alert("Please fill all fields.");
            return;
        }

        if (password.length < 6) {
            alert("Password must contain at least 6 characters.");
            return;
        }

        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return;
        }

       try {

    const response = await fetch(
        "http://localhost:8080/api/auth/register",
        {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({

                name: name,

                email: email,

                phone: phone,

                experience: experience,

                password: password

            })

        }

    );

    const data = await response.json();

    if (response.ok) {

       sessionStorage.setItem("scrollToLogin", "true");
sessionStorage.setItem("registeredEmail", email);

window.location.href = "index.html";

    }

    else {

        alert(data.message);

    }

}

catch (error) {

    console.error(error);

    alert("Unable to connect to server.");

}
    });
});