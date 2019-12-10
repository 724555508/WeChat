<%@ page language="java" contentType="text/html;charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"/>
    <title>邀请记录</title>
    <style>
        body{
            background-color: #e8e8e8;
        }
        .head{
            display: flex;
            justify-content: space-around;
            height: 100px;
            align-items: center;
        }
        .center{
            text-align: center;
        }
        .num{
            font-size: 20px;
            font-weight: bold;
        }

        .pre>div:first-child{
            width: 15%;
        }
        .pre>div:nth-child(2){
            width: 40%;
        }
        .pre>div:nth-child(3){
            width: 40%;
        }
    </style>
</head>
<body>
    <div>

        <div class="head">
            <div class="center">
                <div>粉丝数</div>
                <div class="num">${countPeople[0]}</div>
            </div>
            <div>
                <img src="http://thirdwx.qlogo.cn/mmopen/70Irk3xTp60McZoPiandNRicpJaaCdfXzoEoqdSJ6JP8ILYQJvJiatAZmrxwGFrwg9OwFX3mXtXmCAmk9aTAueiaFK3IgzjGmTAf/132"
                    style="border-radius: 100%;width: 80px;height: 80px;">
            </div>
            <div class="center">
                <div>累计邀请</div>
                <div class="num">${countPeople[1]}</div>
            </div>
        </div>

        <div class="" style="background-color: #ffffff;margin: 50px 3%;border-radius: 15px;padding: 20px 0 20px 0;">
            <div style="text-align: center;font-size: 20px;font-weight: bold;">邀请记录(0人)</div>
            <c:forEach items="${userList}" var="user" varStatus="status">
            	<div style="display: flex;justify-content: space-around;align-items: center;
    padding: 7px;" class="pre">
                	<div>
                    	<img src="${user.posterUrl}" alt=""
                    style="border-radius: 100%;width: 35px;height: 35px;">
                	</div>
                	<div>${user.username}</div>
                	<c:if test="${user.subscribe == 1}">
                		<div>${user.createTime}</div>
                	</c:if>
                	<c:if test="${user.subscribe == 0}">
                		<div>已取消关注</div>
                	</c:if>
            	</div>
            </c:forEach>
            
            <div style="display: flex;justify-content: space-around;align-items: center;
    padding: 7px;" class="pre">
                <div>
                    <img src="http://thirdwx.qlogo.cn/mmopen/70Irk3xTp60McZoPiandNRicpJaaCdfXzoEoqdSJ6JP8ILYQJvJiatAZmrxwGFrwg9OwFX3mXtXmCAmk9aTAueiaFK3IgzjGmTAf/132" alt=""
                         style="border-radius: 100%;width: 35px;height: 35px;">
                </div>
                <div>
                    爱萝莉真是太好了
                </div>
                <div>
                    2019-10-31 14:45:31
                </div>
            </div>
            <div style="display: flex;justify-content: space-around;align-items: center;
    padding: 7px;" class="pre">
                <div>
                    <img src="http://thirdwx.qlogo.cn/mmopen/70Irk3xTp60McZoPiandNRicpJaaCdfXzoEoqdSJ6JP8ILYQJvJiatAZmrxwGFrwg9OwFX3mXtXmCAmk9aTAueiaFK3IgzjGmTAf/132" alt=""
                         style="border-radius: 100%;width: 35px;height: 35px;">
                </div>
                <div>
                    爱萝莉真是太好了
                </div>
                <div>
                    已取消关注
                </div>
            </div>
        </div>
    </div>

</body>
<script src="https://video.vshidai.top/js-lib/jquery/jquery.min.js"></script>
<script src="https://video.vshidai.top/js-lib/vue/vue.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<script>



</script>
</html>