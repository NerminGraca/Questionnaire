window.fbAsyncInit = function() {
  FB.init({
    appId      : '1999458266970525',
    cookie     : true,
    xfbml      : true,
    version    : 'v2.12'
  });
    
  FB.AppEvents.logPageView();
  
  FB.getLoginStatus(function(response) {
	    statusChangeCallback(response);
  });
    
};

(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = 'https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.12&appId=1999458266970525';
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk')
);

function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

function statusChangeCallback(response) {
  if (response.status === 'connected') {
    let userName = '';
    FB.api('/me', function(apiresponse) {
  	  console.log(apiresponse.name)
    	  userName = apiresponse.name;
  	  window.location.href = "/login-fb?fbId=" + response.authResponse.userID + "&fbUsername=" + userName;
    });
  }
}
