userName = "RandomUser"
const signInButton = document.getElementById("signInBtn");
signInButton.addEventListener("click", function sendMessage(){

    userName = document.getElementById("nameInput").value;

    if(userName=="")
    {
        alert("Please enter your Name!");
        return;
    }

    console.log(userName);
    document.querySelector("#mainChat").style.removeProperty("display");
    let divToHide = document.getElementsByClassName("signup-container");

    if(divToHide){
        divToHide[0].style.display = 'none'
    }

    createWebSocketConn();

});

var inputField = document.getElementById("nameInput");
inputField.addEventListener("keypress", function sendMessage(event){

    if (event.key === "Enter") {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("signInBtn").click();
      }

});

const currentHostname = window.location.host;
let ws = null;
function createWebSocketConn(){

    try {
        ws = new WebSocket('ws://' + currentHostname + '/register/'+ userName);
        
      }
      catch(err) {
        alert("Failed to reach server, please reload the page!");
      }
    
      if(ws!=null)
      {
        document.getElementById("chatArea").textContent = "Connected to server";
        ws.onmessage = message => {

            let existingMessages = document.getElementById("chatArea").value;
            let newMessage = "";
            console.log("Existing" + existingMessages);
            console.log("Incoming" + message.data);
            newMessage = existingMessages + "\n"+ message.data;
            console.log("New" + newMessage);
            document.getElementById("chatArea").textContent = newMessage;
            let textarea = document.getElementById('chatArea');
            textarea.scrollTop = textarea.scrollHeight;
        }
      }
}





const button = document.getElementById("SubmitMsg");
button.addEventListener("click", function sendMessage(){

        sendMessageToServer();
});

var inputField = document.getElementById("msgInput");
inputField.addEventListener("keypress", function sendMessage(event){

    if (event.key === "Enter") {
        // Cancel the default action, if needed
        event.preventDefault();
        // Trigger the button element with a click
        document.getElementById("SubmitMsg").click();
      }

});


function sendMessageToServer(){
    let inputMessage = document.getElementById("msgInput").value;
    if(!inputMessage)
    {
        return;
    }
    ws.send(inputMessage);
    document.getElementById("msgInput").value = "";
}