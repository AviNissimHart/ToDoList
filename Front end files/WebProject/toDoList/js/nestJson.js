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
        console.log(taskData + "hellooooo");
        for(let taskRecord of taskData){
            console.log("here is my taskRecord", taskRecord.tasks);
            for(let singleRecord in taskRecord.tasks){
                console.log("single record here", taskRecord[singleRecord])
                for (let a of singleRecord){
                    console.log(singleRecord[a]);
                }
            }
        }

      });
    }
  )
.catch(function(err) {
    console.log('Fetch Error :-s', err);
});

fetch('http://localhost:8901/tlist/read')
  .then(
    function(response) {
      if (response.status !== 200) {
        console.log('Looks like there was a problem. Status Code: ' +
          response.status);
        return;
      }

      // Examine the text in the response
      response.json().then(function(tlistData) {
        console.log(tlistData);
        for(let tlistRecord of tlistData){
            console.log("here is my tlistRecord", tlistRecord.tlists);
            for(let singleRecord in tlistRecord.tlists){
                console.log("single record here", tlistRecord[singleRecord])
                for (let a of singleRecord){
                    console.log(singleRecord[a]);
                }
            }
        }

      });
    }
  )
.catch(function(err) {
    console.log('Fetch Error :-s', err);
});
