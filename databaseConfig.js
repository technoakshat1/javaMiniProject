const express = require("express");
const mongoose = require("mongoose");

const hotFlavours=require("./hotFlavours");
const array=require("./cake");


const app = express();

mongoose.connect("mongodb://localhost:27017/BakeryDb", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
  useFindAndModify: false,
});

mongoose.set("useCreateIndex", true);


const flavoursSchema = new mongoose.Schema({
  flavor: String,
  image: String,
  description: String,
  variants: Array,
});

const hotFlavoursSchema = new mongoose.Schema({
  flavor: String,
  image: String,
  description: String,
  variants: Array,
});

const HotFlavours = new mongoose.model("HotFlavour", hotFlavoursSchema);

//code to add dummy hot flavours
HotFlavours.insertMany(hotFlavours,(err)=>{
  console.log("Hot flavours configured successfully!");
});

const Flavours = new mongoose.model("flavour", flavoursSchema);
//code to add dummy flavors
Flavours.insertMany(array,(err)=>{
    console.log("Flavors successfuly configured!");
    console.log("Now stop this server and you can start server.js file using node server.js command!");
});

//server startup
app.listen(3000, () => {
  console.log("server started on port 3000");
});



