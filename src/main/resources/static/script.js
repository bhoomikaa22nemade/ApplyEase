// ===========================
// ApplyEase - Landing Page
// ===========================

// Smooth Scroll

document.querySelectorAll('a[href^="#"]').forEach(anchor => {

    anchor.addEventListener("click", function(e){

        e.preventDefault();

        document.querySelector(this.getAttribute("href"))
            .scrollIntoView({
                behavior:"smooth"
            });

    });

});
const texts = [

    "🚀 Register to start your job search journey.",

    "🔐 Login to access your personalized dashboard.",

    "📄 Upload your resume and check your ATS score.",

    "💼 Search jobs that match your skills.",

    "🤖 Automate your applications with ApplyEase."

];

let textIndex = 0;
let letterIndex = 0;

const typingElement = document.getElementById("typingText");

function type() {

    if (letterIndex < texts[textIndex].length) {

        typingElement.textContent += texts[textIndex].charAt(letterIndex);

        letterIndex++;

        setTimeout(type, 60);

    } else {

        setTimeout(erase, 1800);

    }

}

function erase() {

    if (letterIndex > 0) {

        typingElement.textContent = texts[textIndex].substring(0, letterIndex - 1);

        letterIndex--;

        setTimeout(erase, 30);

    } else {

        textIndex++;

        if (textIndex >= texts.length) {

            textIndex = 0;

        }

        setTimeout(type, 500);

    }

}

document.addEventListener("DOMContentLoaded", type);
// ===========================
// Login
// ===========================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function (e) {

        e.preventDefault();

        const email =
            document.getElementById("loginEmail").value.trim();

        const password =
            document.getElementById("loginPassword").value.trim();

        if (email === "" || password === "") {

            alert("Please enter Email and Password.");

            return;

        }

        try {

            const response = await fetch(
                "http://localhost:8080/api/auth/login",
                {

                    method: "POST",

                    headers: {
                        "Content-Type": "application/json"
                    },

                    body: JSON.stringify({

                        email: email,

                        password: password

                    })

                }

            );

            const data = await response.json();

            console.log("Login Response:", data);

            if (response.ok) {

                localStorage.removeItem("username");
                localStorage.removeItem("email");

                localStorage.setItem("username", data.username);
                localStorage.setItem("email", data.email);

                alert("Login Successful!");

                window.location.href = "dashboard.html";

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

}

// Open Login Section After Register
// ===============================

document.addEventListener("DOMContentLoaded", () => {

    if (sessionStorage.getItem("scrollToLogin") === "true") {

        sessionStorage.removeItem("scrollToLogin");

        const loginSection = document.getElementById("login");

        if (loginSection) {

            loginSection.scrollIntoView({

                behavior: "smooth"

            });

            setTimeout(() => {

                alert("✅ Account created successfully!\nPlease log in to continue.");

            }, 600);

        }

    }

});
