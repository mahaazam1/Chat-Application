var socketIO = require('socket.io'),
    http = require('http'),
    port = process.env.PORT || 4000,
    ip = process.env.IP || '192.168.0.104', //My IP address. I try to "127.0.0.1" but it the same => don't run
    server = http.createServer().listen(port, ip, function() {
        console.log("IP = " , ip);
        console.log("start socket successfully");
});
var arrUsers =[];
var arrGroup =[];
io = socketIO.listen(server);
//io.set('match origin protocol', true);
io.set('origins', '*:*');
var run = function(socket){
socket.broadcast.emit("message", "hello");
    
    
        socket.on("SignUp", function(id,value) {
        arrUsers.push(value);
        console.log(arrUsers+"Signup");
        console.log("id signup"+id);   
        io.emit("SignUp",id,value);
    });
    
        socket.on("SignIn", function(id,bool,value) {
            for(let i=0; i<arrUsers.length; i++){
                if(arrUsers[i]['username']==value['user'] && arrUsers[i]['pass1']==value['pass']){
                    io.emit("SignIn",id,true,arrUsers[i]);
                    console.log(value);  
                    break;
                }else if(i==arrUsers.length-1){
                    io.emit("SignIn",id,false);
                }
            }
    });
    
        socket.on("allUser", function(value) {
        io.emit("allUser",arrUsers);
    });
    
        socket.on("message", function(id,value) {
        console.log(value);
        console.log("username");
        console.log(value["message"]);
        io.emit("message",id,value);
        
    });
        socket.on("addGroup", function(value) {
        arrGroup.push(value);
        io.emit("allGroup",arrGroup);
        console.log("group",value);
    
    });
    
        socket.on("allGroup", function(value) {
        io.emit("allGroup",arrGroup);
    
    });
    
        socket.on("messageGroup", function(id,value) {
        io.emit("messageGroup",id,value);
    });
    
        socket.on("updateOnline", function(value) {
         for(let i=0; i<arrUsers.length; i++){
                if(arrUsers[i]['id']==value['id']){
                    arrUsers[i]['status']=value['status'];
                    console.log("arrUsers[i]['status']"+arrUsers[i]['status']);
                    io.emit("allUser",arrUsers);
                    break;
                }
            }
    });
    
}

io.sockets.on('connection', run);
