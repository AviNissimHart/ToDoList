fetch('http://localhost:8901/task/read')
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

        let table = document.querySelector("table"); // table is declared but value never read
        let data = Object.keys(taskData[0]);

        createTableHead(table,data);
        createTableBody(table,taskData);
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });




  function createTableHead(table, data){
      let tableHead=table.createTHead();
      let row = tableHead.insertRow();
      for(let keys of data) {
          let th = document.createElement("th");
          let text = document.createTextNode(keys);
          th.appendChild(text);
          row.appendChild(th);
      }
      let th2 = document.createElement("th")
      let text2 = document.createTextNode("View")
      th2.appendChild(text2)
      row.appendChild(th2)
      let th3 = document.createElement("th")
      let text3 = document.createTextNode("Delete")
      th3.appendChild(text3)
      row.appendChild(th3)
  }

  function createTableBody(table, taskData){
      for(let taskRecord of taskData){
          let row = table.insertRow();
          for(values in taskRecord){
              console.log(taskRecord[values]);
              let cell = row.insertCell();
              let text = document.createTextNode(taskRecord[values]);
              cell.appendChild(text);
          }
          let newCell = row.insertCell();
          let myViewButton = document.createElement("a");
          let myButtonValue = document.createTextNode("View");
          myViewButton.className = "btn btn-danger";
          myViewButton.href = `oneTask.html?id=` + taskRecord.id;
          myViewButton.appendChild(myButtonValue);
          newCell.appendChild(myViewButton);
          let newCellDel = row.insertCell();
          let myDelButton = document.createElement("button");
          let myButtonValue1 = document.createTextNode("Delete");
          myDelButton.className = "btn btn-danger";
          myDelButton.onclick =function(){
            delRec(taskRecord.id);return false;
          };
          myDelButton.appendChild(myButtonValue1);
          newCellDel.appendChild(myDelButton);
         
      }
  }

  function printString(string){
      console.log(string)
  }

  document.querySelector("form.taskForm").addEventListener("submit", function(stop) {
    stop.preventDefault();

    
    let formElements = document.querySelector("form.taskForm").elements;
    console.log(formElements);
    // let id = formElements["taskID"].value;
    let name = formElements["taskName"].value;
    let type = formElements["taskType"].value;
    let taskList = formElements["taskList"].value;

    
    // console.log(id);
    console.log("This is my value " + name);
    console.log("This is my value " + type);
    console.log("This is my value " + taskList);

    
    createTask(name,type,taskList)

})
  function createTask(name,type,taskList) {
    

    console.log(typeof(name))
    console.log(typeof(type))
    console.log(typeof(taskList))

   
    let dataToPost = {
        "taskName": name,
        "type": type,
        "tlist": {
          "id": taskList
        }
    }

    
    fetch("http://localhost:8901/task/create",  {
        method: 'post',
        headers: {
          "Content-type": "application/json"
        },
        body: JSON.stringify(dataToPost)
      })
      .then(JSON)
      .then(function (data) {
        console.log('Request succeeded with JSON response', data);
        location.reload()
      })
      .catch(function (error) {
        console.log('Request failed', error);
      });
    }

    document.querySelector("form.tlistForm").addEventListener("submit", function(stop) {
      stop.preventDefault();
  
    
      let formElements = document.querySelector("form.tlistForm").elements;
      console.log(formElements);
      // let id = formElements["taskID"].value;
      let name = formElements["tlistName"].value;
  
    
      console.log("This is my value " + name);
  
    
      createTList(name)
  
  })
    function createTList(name) {
      
  
      console.log(typeof(name))
      
      let dataToPost = {
          "category": name
      }
  
      
      fetch("http://localhost:8901/tlist/create",  {
          method: 'post',
          headers: {
            "Content-type": "application/json"
          },
          body: JSON.stringify(dataToPost)
        })
        .then(JSON)
        .then(function (data) {
          console.log('Request succeeded with JSON response', data);
          location.reload()
        })
        .catch(function (error) {
          console.log('Request failed', error);
        });
      }
  


  function delRec(id){
    console.log(id);
    

 fetch("http://localhost:8901/task/delete/" +id,  {
  method: 'delete',
  headers: {
    "Content-type": "application/json"
  },
})

.then(function (data) {
  console.log('Request succeeded with JSON response', data);
 
  location.reload()
})
.catch(function (error) {
  console.log('Request failed', error);
});
}

function delList(id){
  console.log(id);
  


fetch("http://localhost:8901/tlist/delete/" +id,  {
method: 'delete',
headers: {
  "Content-type": "application/json"
},
})

.then(function (data) {
console.log('Request succeeded with JSON response', data);

location.reload()
})
.catch(function (error) {
console.log('Request failed', error);
});
}

fetch('http://localhost:8901/tlist/read')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      
      response.json().then(function(tlistData) {
        console.log(tlistData);

        let table1 = document.querySelector("table.table1"); // table is declared but value never read
        let data1 = Object.keys(tlistData[0]); // find how to make array show here of tasks
        console.log(table1);

        createTableHead1(table1,data1);
        createTableBody1(table1,tlistData); // find how to make pretty
        // find how to fix list delete
        // adjust update for tasks to change lists
        // make list update
      });
    }
  )
  .catch(function(err) {
    console.log('Fetch Error :-S', err);
  });
  function createTableHead1(table1, data1){
    let tableHead1=table1.createTHead();
    let row = tableHead1.insertRow();
    for(let keys of data1) {
        let th = document.createElement("th");
        let text = document.createTextNode(keys);
        th.appendChild(text);
        row.appendChild(th);
    }
    let th2 = document.createElement("th")
    let text2 = document.createTextNode("View")
    th2.appendChild(text2)
    row.appendChild(th2)
    let th3 = document.createElement("th")
    let text3 = document.createTextNode("Delete")
    th3.appendChild(text3)
    row.appendChild(th3)
}

function createTableBody1(table1, tlistData){
    for(let tlistRecord of tlistData){
        let row = table1.insertRow();
        for(values in tlistRecord){
            console.log(tlistRecord[values]);
            let cell = row.insertCell();
            let text = document.createTextNode(tlistRecord[values]);
            cell.appendChild(text);
        }
        let newCell = row.insertCell();
        let myViewButton = document.createElement("a");
        let myButtonValue = document.createTextNode("View");
        //myViewButton.className = "btn btn-danger";
        //myViewButton.href = `oneTask.html?id=` + taskRecord.id;
        //myViewButton.appendChild(myButtonValue);
        newCell.appendChild(myViewButton);
        let newCellDel = row.insertCell();
        let myDelButton = document.createElement("button");
        let myButtonValue1 = document.createTextNode("Delete");
        myDelButton.className = "btn btn-danger";
        myDelButton.onclick =function(){
          delList(tlistRecord.id);return false;
        };
        myDelButton.appendChild(myButtonValue1);
        newCellDel.appendChild(myDelButton);
       
    }
}


