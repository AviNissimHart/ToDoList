const params = new URLSearchParams(window.location.search);

for(let param of params ){
    console.log("here i am", param)
    let id = param[1];
    console.log(id);
    getSingleRecord(id);
}


function getSingleRecord(id){
fetch('http://localhost:8901/task/read/'+id)
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(taskData) {
        console.log(taskData);

        document.getElementById("taskID").value=taskData.id;
        document.getElementById("taskName").value=taskData.taskName;
        document.getElementById("taskType").value=taskData.type;
      });
    }
  )
.catch(function(err) {
    console.log('Fetch Error :-s', err);
});
}
document.querySelector("form.taskForm").addEventListener("mouseover", function(stop) {
  console.log("You're hovering");
})

document.querySelector("form.taskForm").addEventListener("submit", function(stop) {
    stop.preventDefault();

 
    let formElements = document.querySelector("form.taskForm").elements;
    console.log(formElements);
    let id = formElements["taskID"].value;
    let name = formElements["taskName"].value;
    let type = formElements["taskType"].value;

    
    console.log(id);
    console.log(name);
    console.log(type);

    
    updateTask(id,name,type)

})



function updateTask(id,name,type){
    
    let updateID = parseInt(id)
    
    console.log(typeof(id))
    console.log(typeof(name))
    console.log(typeof(type))

    
    let dataToPost = {
        "id": id,
        "taskName": name,
        "type": type
    }

    
    fetch("http://localhost:8901/task/update/" +id,  {
        method: 'put',
        headers: {
          "Content-type": "application/json"
        },
        body: JSON.stringify(dataToPost)
      })
      .then(JSON)
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
}