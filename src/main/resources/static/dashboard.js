// ==========================================
// ApplyEase Dashboard st
// ==========================================

const state = {
    resumeFile: null,
    atsScore: 0,
    jobs: [],
    selectedJobs: new Set(),
    applied: 0,
    appliedResults: [],
    running: false,
    controller: null
};

// ==========================================
// Helpers
// ==========================================

function setStatus(id, text, cls) {
    const el = document.getElementById(id);
    if (!el) return;
    el.textContent = text;
    el.className = "status-pill " + cls;
}

function showError(id, msg) {
    const el = document.getElementById(id);
    if (!el) return;
    el.hidden = !msg;
    el.textContent = msg;
}

function showSpinner(id, visible) {
    const el = document.getElementById(id);
    if (!el) return;
    el.hidden = !visible;
}

function fillList(id, items, emptyText) {
    const el = document.getElementById(id);
    if (!el) return;

    el.innerHTML = "";

    if (items && items.length > 0) {
        items.forEach(item => {
            const li = document.createElement("li");
            li.textContent = item;
            el.appendChild(li);
        });
    } else {
        const li = document.createElement("li");
        li.textContent = emptyText;
        el.appendChild(li);
    }
}

// ==========================================
// Start
// ==========================================

document.addEventListener("DOMContentLoaded", () => {
    loadApplications();

    // ==========================================
    // Welcome message (dashboard only)
    // ==========================================

    const username = localStorage.getItem("username");
    const loggedInUserEl = document.getElementById("loggedInUser");

    if (username && loggedInUserEl) {
        loggedInUserEl.textContent = "Welcome, " + username;
    }

    // ==========================================
    // Logout (dashboard only)
    // ==========================================

    const logoutBtn = document.getElementById("logoutBtn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            if (confirm("Logout from ApplyEase?")) {
                localStorage.removeItem("username");
                localStorage.removeItem("email");
                window.location.href = "index.html";
            }
        });
    }

    // ==========================================
    // Login (login page only)
    // ==========================================

    const loginForm = document.getElementById("loginForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async function (e) {
            e.preventDefault();

            const email = document.getElementById("email").value.trim();
            const password = document.getElementById("password").value.trim();

            const submitBtn = loginForm.querySelector("button[type='submit']");

            try {
                if (submitBtn) {
                    submitBtn.disabled = true;
                }

                const response = await fetch("http://localhost:8080/api/auth/login", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({ email, password })
                });

                const data = await response.json();

                if (response.ok) {
                    localStorage.setItem("username", data.username);
                    localStorage.setItem("email", data.email);

                    alert("Login Successful");
                    window.location.href = "dashboard.html";
                } else {
                    alert(data.message || "Invalid Email or Password");
                }
            }
            catch (error) {
                console.error(error);
                alert("Unable to connect to server.");
            }
            finally {
                if (submitBtn) {
                    submitBtn.disabled = false;
                }
            }
        });
    }

    // ==========================================
    // Resume Upload (dashboard only)
    // ==========================================

    const resumeInput = document.getElementById("resumeFile");
    const uploadBtn = document.getElementById("uploadBtn");
    const fileName = document.getElementById("resumeFileHelp");

    if (resumeInput && uploadBtn) {

        resumeInput.addEventListener("change", () => {
            if (fileName) {
                fileName.textContent = resumeInput.files.length > 0
                    ? resumeInput.files[0].name
                    : "No file selected";
            }
        });

        uploadBtn.addEventListener("click", async () => {
            showError("resumeUploadError", "");

            if (resumeInput.files.length === 0) {
                showError("resumeUploadError", "Please select your resume.");
                return;
            }

            const formData = new FormData();
            formData.append("resume", resumeInput.files[0]);

            uploadBtn.disabled = true;
            uploadBtn.textContent = "Uploading...";
            showSpinner("uploadSpinner", true);

            try {
                const response = await fetch("http://localhost:8080/api/resume/upload", {
                    method: "POST",
                    body: formData
                });

                if (!response.ok) {
                    throw new Error("Resume upload failed.");
                }

                const data = await response.json();
                state.resumeFile = resumeInput.files[0];

                setStatus("resumeStatus", "Uploaded", "status-success");
                const step1 = document.getElementById("stepCard1");
                if (step1) step1.classList.add("step-card-active");

                alert(data.message);
            }
            catch (error) {
                console.error(error);
                showError("resumeUploadError", error.message);
            }
            finally {
                uploadBtn.disabled = false;
                uploadBtn.textContent = "Upload Resume";
                showSpinner("uploadSpinner", false);
            }
        });
    }

    // ==========================================
    // ATS Resume Checker (dashboard only)
    // ==========================================

    const atsBtn = document.getElementById("atsBtn");

    if (atsBtn) {
        atsBtn.addEventListener("click", async () => {
            showError("atsError", "");

            if (!state.resumeFile) {
                showError("atsError", "Please upload your resume first.");
                return;
            }

            const formData = new FormData();
            formData.append("resume", state.resumeFile);

            atsBtn.disabled = true;
            atsBtn.textContent = "Checking...";
            showSpinner("atsSpinner", true);

            try {
                const response = await fetch("http://localhost:8080/api/resume/check", {
                    method: "POST",
                    body: formData
                });

                const data = await response.json().catch(() => ({}));

                if (!response.ok) {
                    throw new Error(data.message || "ATS Check Failed");
                }

                state.atsScore = data.score;

                const scoreEl = document.getElementById("atsScore");
                if (scoreEl) scoreEl.textContent = data.score + "%";

                fillList("atsStrengths", data.strengths, "No notable strengths detected.");
                fillList("atsWeaknesses", data.weaknesses, "No major weaknesses detected.");

                const list = document.getElementById("atsSuggestions");
                if (list) {
                    list.innerHTML = "";

                    if (data.suggestions && data.suggestions.length > 0) {
                        data.suggestions.forEach(item => {
                            const li = document.createElement("li");
                            li.textContent = item;
                            list.appendChild(li);
                        });
                    } else {
                        const li = document.createElement("li");
                        li.textContent = "Resume looks good.";
                        list.appendChild(li);
                    }
                }

                setStatus("atsStatus", "Completed", "status-success");
                const step2 = document.getElementById("stepCard2");
                if (step2) step2.classList.add("step-card-active");
            }
            catch (error) {
                console.error(error);
                showError("atsError", error.message);
            }
            finally {
                atsBtn.disabled = false;
                atsBtn.textContent = "Check ATS Score";
                showSpinner("atsSpinner", false);
            }
        });
    }

    // ==========================================
    // Search Jobs (dashboard only)
    // ==========================================

    const searchBtn = document.getElementById("searchJobsBtn");
    const resultsList = document.getElementById("jobResultsList");
    const emptyState = document.getElementById("jobResultsEmpty");

    if (searchBtn && resultsList && emptyState) {

        searchBtn.addEventListener("click", async () => {

            showError("jobSearchError", "");

            const keyword = document.getElementById("jobKeywords").value;
            const location = document.getElementById("jobLocation").value.trim();

            if (!keyword) {
                showError("jobSearchError", "Please select a Job Role.");
                return;
            }

            if (!location) {
                showError("jobSearchError", "Please enter Location.");
                return;
            }

            searchBtn.disabled = true;
            searchBtn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i> Searching...';

            resultsList.innerHTML = "";
            emptyState.hidden = true;

            try {

                const response = await fetch("http://localhost:8080/api/jobs/search", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        keyword: keyword,
                        location: location,
                        experience: document.getElementById("filterExperience").value,
                        jobType: document.getElementById("filterJobType").value,
                        workMode: document.getElementById("filterWorkMode").value,
                        postedDate: document.getElementById("filterPostedDate").value,
                        salaryMin: document.getElementById("filterSalaryMin").value,
                        salaryMax: document.getElementById("filterSalaryMax").value,
                        portal: "TechFetch"
                    })
                });

                if (!response.ok) {
                    throw new Error("Unable to Search Jobs");
                }

                const data = await response.json();

                state.jobs = data.jobs || [];
                state.selectedJobs.clear();

                const jobsFoundEl = document.getElementById("jobsFound");

                if (state.jobs.length === 0) {
                    emptyState.hidden = false;
                    if (jobsFoundEl) jobsFoundEl.textContent = "0";
                    setStatus("jobStatus", "No Jobs Found", "status-pending");
                    return;
                }

                if (jobsFoundEl) jobsFoundEl.textContent = state.jobs.length;

                setStatus("jobStatus", state.jobs.length + " Jobs Found", "status-success");

                const step3 = document.getElementById("stepCard3");
                if (step3) step3.classList.add("step-card-active");

                state.jobs.forEach(job => {
                    const li = document.createElement("li");
                    li.className = "job-result-item";

                    li.innerHTML = `
<div class="job-result-info">
<h4>${job.title}</h4>
<p><strong>🏢 Company:</strong> ${job.company}</p>
<p><strong>📍 Location:</strong> ${job.location}</p>
<p><strong>💼 Experience:</strong> ${job.experience}</p>
<p><strong>💰 Salary:</strong> ${job.salary}</p>
<p><strong>🕒 Job Type:</strong> ${job.jobType}</p>
<p><strong>🛠 Skills:</strong> ${job.skills}</p>
<p><strong>📝 Description:</strong> ${job.description}</p>
<p><strong>📅 Posted:</strong> ${job.postedDate}</p>
</div>
<label class="job-select-label">
<input type="checkbox" class="job-select-checkbox" data-id="${job.id}">
Select
</label>
`;

                    resultsList.appendChild(li);
                });

                resultsList.querySelectorAll(".job-select-checkbox").forEach(box => {
                    box.addEventListener("change", function () {
                        const id = this.dataset.id;

                        if (this.checked) {
                            state.selectedJobs.add(id);
                        } else {
                            state.selectedJobs.delete(id);
                        }
                    });
                });
            }
            catch (error) {
                console.error(error);
                showError("jobSearchError", error.message);
            }
            finally {
                searchBtn.disabled = false;
                searchBtn.innerHTML = '<i class="fa-solid fa-magnifying-glass"></i> Search Jobs';
            }
        });
    }

    // ==========================================
    // Automation (dashboard only)
    // ==========================================

    const startBtn = document.getElementById("startAutomationBtn");
    const stopBtn = document.getElementById("stopAutomationBtn");
    const automationLog = document.getElementById("automationLog");

    if (startBtn && stopBtn) {

        startBtn.addEventListener("click", async () => {
            showError("automationError", "");

            if (state.selectedJobs.size === 0) {
                showError("automationError", "Please select at least one job.");
                return;
            }

            startBtn.disabled = true;
            stopBtn.disabled = false;
            state.running = true;
            state.controller = new AbortController();

            setStatus("automationStatus", "Running", "status-active");

            try {
                const response = await fetch("http://localhost:8080/api/jobs/apply", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({
                        keyword: document.getElementById("jobKeywords").value,
                        location: document.getElementById("jobLocation").value.trim(),
                        experience: document.getElementById("filterExperience").value,
                        portal: "TechFetch",
                        jobIds: [...state.selectedJobs]
                    }),
                    signal: state.controller.signal
                });

                if (!response.ok) {
                    throw new Error("Automation Failed");
                }

                const data = await response.json();
                state.applied = data.applied;
                const appliedJobs = state.jobs.filter(job =>
    state.selectedJobs.has(String(job.id))
);

localStorage.setItem(
    "appliedJobs",
    JSON.stringify(appliedJobs)
);
console.log(appliedJobs);

loadApplications();
                state.appliedResults = data.results || [];

                const jobsAppliedEl = document.getElementById("jobsApplied");
                const finalStatusEl = document.getElementById("finalStatus");

                if (jobsAppliedEl) jobsAppliedEl.textContent = data.applied;
                if (finalStatusEl) finalStatusEl.textContent = "Completed";

                setStatus("automationStatus", "Completed", "status-success");
                const step4 = document.getElementById("stepCard4");
                if (step4) step4.classList.add("step-card-active");

                if (automationLog) {
                    automationLog.innerHTML = "";
                    state.appliedResults.forEach(job => {
                        const li = document.createElement("li");
                        li.textContent = "✔ Applied : " + job.title;
                        automationLog.appendChild(li);
                    });
                }

                alert("Automation Completed Successfully!");
            }
            catch (error) {
                if (error.name === "AbortError") {
                    console.log("Automation stopped by user.");
                    setStatus("automationStatus", "Stopped", "status-pending");
                } else {
                    console.error(error);
                    showError("automationError", error.message);
                    setStatus("automationStatus", "Failed", "status-pending");
                }
            }
            finally {
                startBtn.disabled = false;
                stopBtn.disabled = true;
                state.running = false;
                state.controller = null;
            }
        });

        // ==========================================
        // Stop Automation
        // ==========================================

        stopBtn.addEventListener("click", () => {
            if (state.controller) {
                state.controller.abort();
            }
            state.running = false;
            stopBtn.disabled = true;
            startBtn.disabled = false;
            setStatus("automationStatus", "Stopped", "status-pending");
        });
    }

    // ==========================================
    // Download Report (PDF) (dashboard only)
    // ==========================================

    const downloadReportBtn = document.getElementById("downloadReportBtn");

    if (downloadReportBtn) {
        downloadReportBtn.addEventListener("click", () => {

            if (!window.jspdf) {
                alert("PDF library failed to load. Please refresh and try again.");
                return;
            }

            const { jsPDF } = window.jspdf;
            const doc = new jsPDF();

            let y = 20;

            doc.setFont("helvetica", "bold");
            doc.setFontSize(20);
            doc.text("ApplyEase Application Report", 20, y);

            y += 15;

            doc.setFontSize(12);
            doc.setFont("helvetica", "normal");

            doc.text("Portal : TechFetch", 20, y);
            y += 10;

            doc.text(`ATS Score : ${state.atsScore || "--"}%`, 20, y);
            y += 10;

            doc.text(`Jobs Found : ${state.jobs.length}`, 20, y);
            y += 10;

            doc.text(`Jobs Applied : ${state.applied}`, 20, y);
            y += 20;

            doc.setFont("helvetica", "bold");
            doc.text("Applied Jobs", 20, y);

            y += 10;

            doc.setFont("helvetica", "normal");

            // Prefer the confirmed results returned by the automation API;
            // fall back to the selected jobs if automation hasn't run yet.
            const appliedJobs = state.appliedResults.length > 0
                ? state.appliedResults
                : state.jobs.filter(job => [...state.selectedJobs].includes(String(job.id)));

            if (appliedJobs.length === 0) {
                doc.text("No jobs applied yet.", 20, y);
            } else {
                appliedJobs.forEach((job, index) => {
                    if (y > 270) {
                        doc.addPage();
                        y = 20;
                    }

                    doc.text(`${index + 1}. ${job.title}`, 20, y);
                    y += 8;

                    doc.text(`Company : ${job.company}`, 30, y);
                    y += 8;

                    doc.text(`Location : ${job.location}`, 30, y);
                    y += 12;
                });
            }

            doc.save("ApplyEase_Report.pdf");
        });
    }
    function loadApplications() {

    const body = document.getElementById("myApplicationsBody");

    if (!body) return;

    body.innerHTML = "";

    const jobs = JSON.parse(localStorage.getItem("appliedJobs")) || [];

    if (jobs.length === 0) {

        body.innerHTML = `
        <tr>
            <td colspan="5" style="text-align:center;">
                No applications yet.
            </td>
        </tr>
        `;

        return;
    }

    jobs.forEach(job => {

        body.innerHTML += `
        <tr>

            <td>${job.company}</td>

            <td>${job.title}</td>

            <td>${job.location}</td>

            <td>

                <span class="status-success">

                    Applied

                </span>

            </td>

            <td>${new Date().toLocaleDateString()}</td>

        </tr>
        `;

    });

}
});