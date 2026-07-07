package com.applyease.backend.service;

import com.applyease.backend.dto.JobDTO;
import com.applyease.backend.dto.JobRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {

    public List<JobDTO> searchJobs(JobRequest request) {

        List<JobDTO> jobs = new ArrayList<>();

        String role = request.getKeyword();

        String location = request.getLocation();

        if (role == null) {

            role = "";

        }
        // ================================
// JAVA ROLES
// ================================

if (

        role.equalsIgnoreCase("Java Developer") ||

        role.equalsIgnoreCase("Java Full Stack Developer") ||

        role.equalsIgnoreCase("Spring Boot Developer") ||

        role.equalsIgnoreCase("Backend Developer") ||

        role.equalsIgnoreCase("Software Engineer") ||

        role.equalsIgnoreCase("Java Software Engineer") ||

        role.equalsIgnoreCase("J2EE Developer") ||

        role.equalsIgnoreCase("Microservices Developer") ||

        role.equalsIgnoreCase("REST API Developer")

) {

    jobs.add(new JobDTO(

            1L,

            "Java Full Stack Developer",

            "Infosys",

            location,

            "0 - 2 Years",

            "₹4 - ₹6 LPA",

            "Full Time",

            "Java, Spring Boot, MySQL, REST API, Hibernate, Git",

            "Join Infosys to develop scalable enterprise applications using Java and Spring Boot. Build REST APIs, integrate MySQL databases, participate in Agile sprints, perform code reviews, debug production issues and collaborate with frontend developers to deliver high-quality software.",

            "2 days ago"

    ));

    jobs.add(new JobDTO(

            2L,

            "Spring Boot Developer",

            "TCS",

            location,

            "1 - 3 Years",

            "₹5 - ₹8 LPA",

            "Full Time",

            "Spring Boot, Java, Hibernate, Maven, REST API",

            "Design and develop backend microservices using Spring Boot. Build secure REST APIs, optimize SQL queries, write unit tests, integrate third-party APIs and maintain high-performance enterprise applications.",

            "Today"

    ));

    jobs.add(new JobDTO(

            3L,

            "Backend Java Developer",

            "Capgemini",

            location,

            "2 - 4 Years",

            "₹6 - ₹9 LPA",

            "Full Time",

            "Java, REST API, MySQL, Docker, Git",

            "Develop backend modules, implement business logic, optimize database performance, fix production bugs, perform code reviews and improve application scalability using Java technologies.",

            "1 day ago"

    ));

    jobs.add(new JobDTO(

            4L,

            "Software Engineer",

            "Accenture",

            location,

            "Fresher",

            "₹4 - ₹5 LPA",

            "Full Time",

            "Java, SQL, Git, OOP",

            "Work with experienced software engineers to develop enterprise applications. Write clean code, test software modules, participate in development meetings and contribute to software delivery.",

            "3 days ago"

    ));

    jobs.add(new JobDTO(

            5L,

            "Java Developer",

            "Cognizant",

            location,

            "0 - 1 Years",

            "₹3.5 - ₹5 LPA",

            "Full Time",

            "Java, Collections, Multithreading, JDBC",

            "Develop Java applications, integrate REST APIs, troubleshoot technical issues, maintain existing systems, optimize application performance and support production deployments.",

            "Today"

    ));

}
// ================================
// REACT / FRONTEND ROLES
// ================================

else if (

        role.equalsIgnoreCase("React Developer") ||

        role.equalsIgnoreCase("Frontend Developer") ||

        role.equalsIgnoreCase("Angular Developer") ||

        role.equalsIgnoreCase("Web Developer")

) {

    jobs.add(new JobDTO(

            6L,

            "React Developer",

            "IBM",

            location,

            "1 - 3 Years",

            "₹6 - ₹9 LPA",

            "Full Time",

            "ReactJS, JavaScript, HTML5, CSS3, Bootstrap, REST APIs",

            "Develop modern web applications using React.js. Build reusable UI components, integrate REST APIs, optimize performance, debug frontend issues and work closely with UI/UX designers to deliver responsive applications.",

            "Today"

    ));

    jobs.add(new JobDTO(

            7L,

            "Frontend Developer",

            "Deloitte",

            location,

            "0 - 2 Years",

            "₹5 - ₹7 LPA",

            "Full Time",

            "HTML, CSS, JavaScript, ReactJS, Bootstrap",

            "Develop responsive websites and dashboards. Implement pixel-perfect UI designs, optimize applications for speed, ensure cross-browser compatibility and collaborate with backend developers.",

            "2 days ago"

    ));

    jobs.add(new JobDTO(

            8L,

            "React JS Engineer",

            "Wipro",

            location,

            "2 - 4 Years",

            "₹7 - ₹10 LPA",

            "Full Time",

            "ReactJS, Redux, JavaScript, REST API",

            "Develop enterprise React applications using Redux. Create reusable components, manage application state, consume REST APIs and improve application performance.",

            "Yesterday"

    ));

    jobs.add(new JobDTO(

            9L,

            "Angular Developer",

            "HCLTech",

            location,

            "1 - 3 Years",

            "₹6 - ₹8 LPA",

            "Full Time",

            "Angular, TypeScript, HTML, CSS",

            "Build scalable Angular applications, integrate APIs, write clean TypeScript code, fix frontend bugs and participate in Agile development.",

            "3 days ago"

    ));

    jobs.add(new JobDTO(

            10L,

            "Web Developer",

            "LTIMindtree",

            location,

            "Fresher",

            "₹4 - ₹6 LPA",

            "Full Time",

            "HTML, CSS, JavaScript, React",

            "Develop responsive websites, optimize user experience, collaborate with designers, test frontend modules and maintain modern web applications.",

            "Today"

    ));

}
// ================================
// PYTHON / NODE.JS ROLES
// ================================

else if (

        role.equalsIgnoreCase("Python Developer") ||

        role.equalsIgnoreCase("Django Developer") ||

        role.equalsIgnoreCase("Node.js Developer") ||

        role.equalsIgnoreCase("MERN Stack Developer") ||

        role.equalsIgnoreCase("MEAN Stack Developer")

) {

    jobs.add(new JobDTO(

            11L,

            "Python Developer",

            "Google",

            location,

            "1 - 3 Years",

            "₹12 - ₹18 LPA",

            "Full Time",

            "Python, Django, Flask, REST API, PostgreSQL",

            "Develop scalable backend applications using Python. Build REST APIs, optimize database performance, write reusable code, implement security best practices and collaborate with frontend teams.",

            "Today"

    ));

    jobs.add(new JobDTO(

            12L,

            "Backend Python Engineer",

            "Amazon",

            location,

            "2 - 4 Years",

            "₹15 - ₹22 LPA",

            "Full Time",

            "Python, FastAPI, PostgreSQL, Docker",

            "Design cloud-ready backend systems using Python and FastAPI. Build APIs, integrate cloud services, optimize performance and maintain production systems.",

            "Yesterday"

    ));

    jobs.add(new JobDTO(

            13L,

            "Django Developer",

            "Microsoft",

            location,

            "1 - 3 Years",

            "₹10 - ₹15 LPA",

            "Full Time",

            "Python, Django, REST Framework, MySQL",

            "Develop enterprise web applications using Django. Create REST APIs, integrate databases, implement authentication and maintain secure applications.",

            "2 days ago"

    ));

    jobs.add(new JobDTO(

            14L,

            "Node.js Developer",

            "Oracle",

            location,

            "1 - 3 Years",

            "₹8 - ₹12 LPA",

            "Full Time",

            "Node.js, Express.js, MongoDB, REST API",

            "Develop scalable backend APIs using Node.js and Express. Integrate MongoDB, optimize server performance and deploy production-ready applications.",

            "Today"

    ));

    jobs.add(new JobDTO(

            15L,

            "MERN Stack Developer",

            "Zoho",

            location,

            "0 - 2 Years",

            "₹7 - ₹10 LPA",

            "Full Time",

            "MongoDB, Express.js, React, Node.js",

            "Build full-stack web applications using the MERN stack. Develop responsive UIs, backend APIs and integrate databases while following modern development practices.",

            "3 days ago"

    ));

}
// ================================
// DATA / ANALYTICS ROLES
// ================================

else if (

        role.equalsIgnoreCase("Data Analyst") ||

        role.equalsIgnoreCase("Data Engineer") ||

        role.equalsIgnoreCase("Business Analyst")

) {

    jobs.add(new JobDTO(

            16L,

            "Data Analyst",

            "Deloitte",

            location,

            "0 - 2 Years",

            "₹6 - ₹8 LPA",

            "Full Time",

            "Excel, SQL, Power BI, Python",

            "Analyze business data, prepare dashboards, generate reports, perform data cleaning, identify trends, support business decisions and collaborate with stakeholders using Power BI and SQL.",

            "Today"

    ));

    jobs.add(new JobDTO(

            17L,

            "Business Analyst",

            "EY",

            location,

            "1 - 3 Years",

            "₹7 - ₹10 LPA",

            "Full Time",

            "SQL, Excel, Power BI, Communication",

            "Gather business requirements, prepare functional documents, communicate with clients, analyze business processes and support software implementation projects.",

            "Yesterday"

    ));

    jobs.add(new JobDTO(

            18L,

            "Data Engineer",

            "IBM",

            location,

            "2 - 4 Years",

            "₹10 - ₹15 LPA",

            "Full Time",

            "Python, Spark, SQL, Hadoop, ETL",

            "Design and build ETL pipelines, manage data warehouses, optimize large-scale datasets and support analytics teams with reliable data infrastructure.",

            "2 days ago"

    ));

    jobs.add(new JobDTO(

            19L,

            "BI Developer",

            "PwC",

            location,

            "1 - 3 Years",

            "₹8 - ₹11 LPA",

            "Full Time",

            "Power BI, SQL, DAX, Excel",

            "Create interactive dashboards, develop business intelligence reports, perform data visualization and deliver insights for management reporting.",

            "Today"

    ));

    jobs.add(new JobDTO(

            20L,

            "Junior Data Analyst",

            "KPMG",

            location,

            "Fresher",

            "₹5 - ₹7 LPA",

            "Full Time",

            "Excel, SQL, Tableau",

            "Assist senior analysts in preparing reports, collecting business data, creating visual dashboards and supporting client analytics projects.",

            "3 days ago"

    ));

}
// ================================
// DEFAULT JOBS
// ================================

else {

    jobs.add(new JobDTO(

            21L,

            "Software Engineer",

            "Tech Solutions",

            location,

            "Fresher",

            "₹4 - ₹6 LPA",

            "Full Time",

            "Java, SQL, Git, Problem Solving",

            "Join our software engineering team to build enterprise applications. Write clean code, fix bugs, participate in testing, collaborate with team members and contribute to software development projects.",

            "Today"

    ));

    jobs.add(new JobDTO(

            22L,

            "Graduate Engineer Trainee",

            "Global Technologies",

            location,

            "Fresher",

            "₹3.5 - ₹5 LPA",

            "Full Time",

            "Programming, OOP, SQL",

            "Excellent opportunity for fresh graduates to work on live projects, receive mentorship, participate in training programs and build a successful software engineering career.",

            "Yesterday"

    ));

    jobs.add(new JobDTO(

            23L,

            "Associate Software Developer",

            "Persistent Systems",

            location,

            "0 - 2 Years",

            "₹5 - ₹7 LPA",

            "Full Time",

            "Java, Spring Boot, MySQL",

            "Develop enterprise applications, work on REST APIs, participate in Agile ceremonies, perform debugging and collaborate with QA and frontend teams.",

            "3 days ago"

    ));

}

return jobs;

    }

}