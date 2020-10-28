require("dotenv").config();
const express = require("express");
const jwt = require("jsonwebtoken");
const passport = require("passport");
const bodyParser = require("body-parser");
const passportLocalMongoose = require("passport-local-mongoose");
const mongoose = require("mongoose");
const app = express();

mongoose.connect("mongodb://localhost:27017/BakeryDb", {
  useNewUrlParser: true,
  useUnifiedTopology: true,
  useFindAndModify: false,
});

mongoose.set("useCreateIndex", true);

app.use(bodyParser.urlencoded({ extended: true }));

app.use(passport.initialize());

const userSchema = new mongoose.Schema();

userSchema.plugin(passportLocalMongoose);

const User = new mongoose.model("user", userSchema);

passport.use(User.createStrategy());

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

const Flavours = new mongoose.model("flavour", flavoursSchema);

//server startup
app.listen(3000, () => {
  console.log("server started on port 3000");
});

//login route for authentication token

app.get("/login", (req, res) => {
  //console.log(req.headers.authorization);

  if (isAuthenticated(req.headers.authorization)) {
    res.json({ isAuthenticated: true });
  } else {
    res.sendStatus(403);
  }
});

app.post("/login", (req, res) => {
  const user = new User({
    username: req.body.username,
  });

  user.authenticate(req.body.password, (err, userModel, passwordErr) => {
    //console.log(userModel);
    if (!err) {
      if (!passwordErr) {
        jwt.sign(
          { username: userModel.username },
          process.env.JWT_SECRET,
          { expiresIn: "2h" },
          (err, token) => {
            if (!err) {
              res.json({ token });
            } else {
              res.sendStatus(500);
              console.log(err);
            }
          }
        );
      } else {
        res.status(403).send(passwordErr.message);
        console.log(passwordErr);
      }
    } else {
      res.sendStatus(500);
      console.log(err);
    }
  });
});

//sign up route

app.post("/signUp", (req, res) => {
  User.register(
    { username: req.body.username },
    req.body.password,
    (err, user) => {
      if (!err) {
        jwt.sign(
          { username: user.username },
          process.env.JWT_SECRET,
          { expiresIn: "2h" },
          (err, token) => {
            if (!err) {
              res.json({ token });
            } else {
              res.sendStatus(500);
              console.log(err);
            }
          }
        );
      } else {
        res.sendStatus(403);
        console.log(err);
      }
    }
  );
});

//protected routes/endpoints of rest api

app.get("/hotFlavours", (req, res) => {
  if (isAuthenticated(req.headers.authorization)) {
    HotFlavours.find(
      {},
      ["flavor", "image", "description", "variants", "-_id"],
      (err, hotFlavours) => {
        if (!err) {
          res.json(hotFlavours);
        } else {
          res.sendStatus(500);
        }
      }
    );
  } else {
    res.sendStatus(403);
  }
});

app.get("/flavours", (req, res) => {
  if (isAuthenticated(req.headers.authorization)) {
    Flavours.find(
      {},
      ["flavor", "image", "description", "variants", "-_id"],
      (err, flavours) => {
        if (!err) {
          if (flavours) {
            res.json(flavours);
          } else {
            res.sendStatus(500);
            console.log("flavours returned " + flavours);
          }
        } else {
          res.sendStatus(500);
          console.log(err);
        }
      }
    );
  } else {
    res.sendStatus(403);
  }
});

//password confirmation route

app.post("/login/confirmPassword", (req, res) => {
  let token;
  let authorization = req.headers.authorization;
  if (authorization) {
    token = authorization.split(" ")[1];
  } else {
    res.sendStatus(403);
  }

  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (!err) {
      if (decoded.username) {
        let user = new User({
          username: decoded.username,
        });

        user.authenticate(req.body.password, (err, userModel, passwordErr) => {
          if (!err) {
            if (!passwordErr) {
              res.json({ message: true });
            } else {
              res.sendStatus(403);
            }
          } else {
            res.sendStatus(403);
          }
        });
      } else {
        res.sendStatus(403);
      }
    } else {
      res.sendStatus(500);
    }
  });
});

//password change route
app.post("/login/changePassword", (req, res) => {
  let token;
  if (req.headers.authorization) {
    token = req.headers.authorization.split(" ")[1];
  } else {
    return;
  }
  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (!err) {
      if (decoded) {
        User.findOne({ username: decoded.username }, (err, user) => {
          if (!err) {
            if (user) {
              user.setPassword(req.body.newPassword, (err) => {
                if (!err) {
                  user.save();
                  res.sendStatus(200);
                } else {
                  console.log(err);
                  res.sendStatus(500);
                }
              });
            } else {
              console.log(user);
              res.status(404).send("user not found!");
            }
          } else {
            console.log(err);
            res.sendStatus(500);
          }
        });
      } else {
        console.log(decoded);
        res.sendStatus(500);
      }
    } else {
      console.log(err);
      res.sendStatus(500);
    }
  });
});

//authentication function

function isAuthenticated(authorization) {
  let token;
  if (authorization) {
    token = authorization.split(" ")[1];
  } else {
    return false;
  }
  let returnValue = false;
  jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
    if (!err) {
      if (decoded.username) {
        returnValue = true;
      }
    }
  });
  return returnValue;
}
