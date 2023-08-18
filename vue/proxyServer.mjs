import express from "express";
import fetch from "node-fetch";

const app = express();


app.use(express.json());

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "http://localhost:8080");
  res.header("Access-Control-Allow-Methods", "GET, PUT");
  res.header("Access-Control-Allow-Headers", "Content-Type");
  next();
});

app.put("/api/proxy/:filename", async (req, res) => {
  const apiUrl = `https://wy8fdrlenh.execute-api.us-east-2.amazonaws.com/dev/auction-image-storage/${req.params.filename}`;
  
  const requestOptions = {
    method: "PUT",
    host: req.hostname,
    headers: {
      "Content-Type": req.header("Content-Type"),
      "Content-Length": req.header("Content-Length")
    },
    body: req,
    redirect: 'follow',
  };

  try {
    
    const response = await fetch(apiUrl, requestOptions);
    const data = await response.text();

    res.set("Content-Type", response.headers.get("Content-Type"));
    res.send(data);
  } catch (error) {
    console.error("Error fetching image:", error);
    res.status(500).send("Error fetching image");
  }
});



app.get("/api/proxy/get/:filename", async (req, res) => {
  const apiUrl = `https://wy8fdrlenh.execute-api.us-east-2.amazonaws.com/dev/auction-image-storage?filename=${req.params.filename}`;

    const requestOptions = {
    method: 'GET',
    redirect: 'follow'
  };

  try {
    const response = await fetch(apiUrl, requestOptions);
    
    const arrayBufferData = await response.arrayBuffer();
    const imageData = Buffer.from(arrayBufferData); // Convert ArrayBuffer to Buffer

    res.set("Content-Type", response.headers.get("Content-Type"));
    res.set("Content-Length", response.headers.get("Content-Length"));
    res.send(imageData);
  } catch (error) {
    console.error("Error fetching image:", error);
    res.status(500).send("Error fetching image");
  }
});


// app.get("/api/proxy/get/:filename", async (req, res) => {
//   const apiUrl = `https://wy8fdrlenh.execute-api.us-east-2.amazonaws.com/dev/auction-image-storage?filename=${req.params.filename}`;     //`https://wy8fdrlenh.execute-api.us-east-2.amazonaws.com/dev/auction-image-storage?${req.params.filename}+${req.params.numOfImages} `;

//   const requestOptions = {
//     method: 'GET',
//     redirect: 'follow'
//   };
//   try {

//     const response = await fetch(apiUrl, requestOptions);
//     const imageData = await response.blob(); 
//     //console.log("IMAGE DATA" + imageData);

    
//     res.set("Content-Type", response.headers.get("Content-Type"));
//     res.set("Content-Length", response.headers.get("Content-Length"));
//     res.send(imageData);
//   } catch (error) {
//     console.error("Error fetching image:", error);
//     res.status(500).send("Error fetching image");
//   }

// });


const port = 8081;
app.listen(port, () => {
  console.log(`Proxy server listening on port ${port}`);
});
