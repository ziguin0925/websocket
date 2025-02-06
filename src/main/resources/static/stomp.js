const stompClient = new StompJs.Client({
  brokerURL: 'ws://localhost:8080/stomp/chats'
});

stompClient.onConnect = (frame) => {
  setConnected(true);
  console.log('Connected: ' + frame);

};

stompClient.onWebSocketError = (error) => {
  console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
  console.error('Broker reported error: ' + frame.headers['message']);
  console.error('Additional details: ' + frame.body);
};

// 채팅방 페이지에 들어왔을 때.
function setConnected(connected) {
  $("#connect").prop("disabled", connected);
  $("#disconnect").prop("disabled", !connected);
  $("#create").prop("disabled", connected);
}

function connect() {
  stompClient.activate();
}

function disconnect() {
  stompClient.deactivate();
  setConnected(false);
  console.log("Disconnected");
}

function sendMessage() {
  let chatroomId = $("#chatroom-id").val();

  stompClient.publish({
    destination: "/pub/chats/" + chatroomId,
    body: JSON.stringify(
        {'message': $("#message").val()})
  });
  $("#message").val("")
}

function showMessage(chatMessage) {
  $("#messages").append(
      "<tr><td>" + chatMessage.sender + " : " + chatMessage.message
      + "</td></tr>");
}

// 채팅방 생성 요청
function createChatroom(){
  $.ajax({ // ajax요청
    type: 'POST',
    dataType: "json",
    url: "/chats?title=" + $("#chatroom-title").val,

    success: function (data) { // 성공시
      console.log("data : ", data);
      showChatrooms();
      enterChatrooms(data.id, true);
    },
    error: function(request, status, error) { // 에러 발생시
      console.log('request: ' + request);
      console.log('error: ', error);
    }
      })
}

// 화면에 표시될 채팅방 목록.
function showChatrooms() {
  $.ajax(
      {
        type:'GET',
        dataType: 'json',
        url: '/chats',
        success: function (data) {
          console.log("data : ", data);
          renderChatrooms(data);
        },
        error: function (request, status, error) {
          console.log('request: ' + request);
          console.log('error: ', error);
        }
      }
  )
}

function renderChatrooms(chatrooms){
  $("#chatroom-list").html(""); // chatroom-list라는 id의 태그 부분을 초기화 한다.

  for(let i=0; i<chatrooms.length; i++){ // chatroom-list 태그에 아래의 html을 더해준다.
    $("#chatroom-list").append(
        "<tr onclick='joinChatroom(" + chatrooms[i].id + ")'>"
        + chatrooms[i].id + "</td><td>" + chatrooms[i].title + "</td><td>"// 채팅방 id와 채팅방 title을 표시.
        + chatrooms[i].memberCount + "</td><td>" + chatrooms[i].createAt
        + "</td></tr>"
    )
  }
}
// 채팅방에 대해서 subscribe와 publish를 해준다.
let subcsription;

function enterChatrooms(chatroomId, newMember){
  $("#chatroom-id").val(chatroomId); // 사용자가 수정할 수 없는 텍스트 박스의 chatroom id를 넣는다.
  $("#conversation").show();
  $("#send").prop("disabled", false);
  $("#leave").prop("disabled", false);

  // 채팅방에서 다른 채팅방으로 넘어가는 경우.
  if(subcsription != undefined){
     subscription.unsubscribe();
  }


  // 특정 채팅방에 대해서 구독
  subcsription = stompClient.subscribe('/sub/chats/' + chatroomId, (chatMessage) => {
    showMessage(JSON.parse(chatMessage.body));
  });

  // 해당 방에 처음 들어오는 유저라면
  if(newMember) {
    stompClient.publish({
      destination: "/pub/chats/" + chatroomId,
      body: JSON.stringify(
          {'message': "님이 방에 들어왔습니다."}
      )
    })
  }
}


function joinChatroom(chatroomId){
  $.ajax({
    type: 'POST',
    dataType: 'json',
    url: "/chats/"+chatroomId,
    success: function(data){
      console.log('data : ', data);
      enterChatrooms(chatroomId, data);
    },
    error: function(request, status, error) {
      console.log('request: ' + request);
      console.log('error: ', error);
    }
  })
}

// 해당 채팅방을 나가는 경우.
function leaveChatroom(){
  let chatroomId = $("chatroom-id").val(); // 현재 채팅방의 id
  $.ajax({
    type:'DELETE',
    dataType: 'json',
    url: '/chats/'+chatroomId,

    success: function(data){
      console.log('data : ', data);
      showChatrooms();
      exitChatroom(chatroomId);
    },

    error: function(request, status, error) {
      console.log('request: ' + request);
      console.log('error: ', error);
    }
  })
}

function exitChatroom(chatroomId){
  $("#chatroom-id").val(""); // 현재 들어가 있는 방의 채팅방 id 값을 지워준다.
  $("#conversation").hide(); // 메시지가 표시되고 있는 부분을 닫아줌.
  $("#send").prop("disabled", true);
  $("#leave").prop("disabled", true);
}

$(function () {
  $("form").on('submit', (e) => e.preventDefault());
  $("#connect").click(() => connect());
  $("#disconnect").click(() => disconnect());
  $("#send").click(() => sendMessage());
  $("#create").click(()  => createChatroom());
  $("#leave").click(()  => leaveChatroom());
});