<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>MySecret</title>

  <link rel="stylesheet" href="./stylesheets/main.css" type="text/css">

</head>
<body>
    <div class ="sidebar">
        <div class ="logo">
            <a href="/index.ftl">MySecret</a>
        </div>
        
        <div class = "user" style="text-align: center;">         
            <img src="1.jpg" width="100" height="100" class="profile" alt="userImage">
            <span><br>Namkiu</span>
        </div>
        <div class = "menu">
            <a href="personInfo.html">Person Info</a> 
            <a href="blog.html">Blog</a>  
            <a href="schedule.html">Schedule</a>
            <a href="messageBoard.html">Message Board</a>
        </div>
    </div>
    
    <div class="container">
        <div class="inner">
            <div class="inner">
            <p>Message Board<p>
                <fieldset>
                <legend>Leave a new message<input type="button" id="more_btn" value="Add a new"/></legend> 
                <br>
                <textarea cols="80" rows="6"></textarea>
                <br>
                <br>
                </fieldset>

       <ul id="wrap">
	<li>
	<div class="boxCont" style="height:215px;"></div>
	</li>
	<li>
	<div class="boxCont" style="height:286px;"></div>
	</li>
	<li>
	<div class="boxCont" style="height:240px;"></div>
	</li>
        <li>
	<div class="boxCont" style="height:180px;"></div>
	</li>

</ul>             
        </div>
            </div>
    </div>
    
<script src="./js/main.js" type="text/javascript"></script> 
</body>
</html>