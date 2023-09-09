userName = "RandomUser"
const signInButton = document.getElementById("signInBtn");
let startUp=true;
let serverError = false;
signInButton.addEventListener("click", function sendMessage(){

    userName = document.getElementById("nameInput").value;

    if(userName=="")
    {
        alert("Please enter your Name!");
        return;
    }

    console.log(userName);
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

        //document.getElementById("chatArea").textContent = "Connected to server";
        ws.onmessage = message => {

            handleReplyFromServer(message);
        }

        ws.onclose = (event) => {
            raiseServerErrAlert();
        };

        ws.onerror = (event) => {
            raiseServerErrAlert();
        };
      }
}

function getCurrTime(){

    let currDate = new Date(); // for now
    let currHour = currDate.getHours(); // => 9
    let currMin = currDate.getMinutes(); // =>  30

    let currTime = currHour + ':' + currMin;

    return currTime;
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
    console.log("serverError:" + serverError);
    if(serverError==true)
    {
        console.log("Server Error")
        raiseServerErrAlert();
    }
    ws.send(inputMessage);
    document.getElementById("msgInput").value = "";
}

function handleReplyFromServer(serverReply)
{
    console.log("EXISITING AREA: " + document.getElementById("chatArea").value +"\n");
    let serverReplyJsonObj = JSON.parse(serverReply.data);
    let incomingMessage = serverReplyJsonObj.MESSAGE;
    let userName = serverReplyJsonObj.USERNAME;
    let userList = serverReplyJsonObj.UserList === undefined ? "" : serverReplyJsonObj.UserList;
    switch(serverReplyJsonObj.MESSAGE_TYPE) {
        case "SYS_MESSAGE":
            if(startUp)
            {
                document.querySelector("#mainChat").style.removeProperty("display");
                let divToHide = document.getElementsByClassName("signup-container");
                if(divToHide){
                    divToHide[0].style.display = 'none'
                }
                document.getElementById("chatArea").textContent = "Connected to server";
                startUp=false;
            }
            document.getElementById("userList").innerHTML = "Current Active Users: [" + userList +"]";
            appendMessageToTextArea(incomingMessage);
            break;
        case "CHAT_MESSAGE":
            appendMessageToTextArea(userName+": "+incomingMessage);
          break;
        case "ERROR_MESSAGE":
            alert(incomingMessage);
            window.location.reload();
        break;
        default:
          // code block
      }
}

function appendMessageToTextArea(incomingMessage){
    let existingMessages = document.getElementById("chatArea").value;

    let newMessage = "";
    console.log("Existing" + existingMessages);
    console.log("Incoming" + incomingMessage);
    newMessage = existingMessages + "\n" + incomingMessage;
    console.log("New" + newMessage);
    document.getElementById("chatArea").textContent = newMessage;
    let textarea = document.getElementById('chatArea');
    textarea.scrollTop = textarea.scrollHeight; 
}

function raiseServerErrAlert()
{
    serverError = true;
    alert("Server Error, please reload the page!");
}