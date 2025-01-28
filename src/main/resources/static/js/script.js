console.log("Script loaded")

let currentTheme = getTheme();
// initial--->
changeTheme(currentTheme)

// TODO:
function changeTheme() {

    //set to web page
    document.querySelector('html').classList.add(currentTheme);

    //set the listner to change button
    const changethemebutton = document.querySelector('#theme_change_button');


    changethemebutton.addEventListener("click", (event) => {

        let oldTheme = currentTheme;
        console.log("clicked")


        if (currentTheme == "dark") {
            //theme light
            currentTheme = "light";

        } else {
            //theme dark
            currentTheme = "dark";
        }
        // update to local storage
        setTheme(currentTheme);

        //remove the current theme
        document.querySelector('html').classList.remove(oldTheme);

        //set the current theme
        document.querySelector('html').classList.add(currentTheme)

        //change button text on clicked
        changethemebutton.querySelector("span").textContent = currentTheme == "light" ? "Dark" : "Light";

    });
}


// set theme to localstorage 
function setTheme(theme) {
    localStorage.setItem("theme", theme)
}


// get theme from loacalstorage
function getTheme() {
    let theme = localStorage.getItem("theme")

    //turnary operator
    return theme ? theme : "light"
}