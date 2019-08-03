$(function () {
    $("#comment").click(function () {
        var id = $("#questionId").val();
        var content = $("#content").val();
        var type = 1;
        comment_on(id,content,type);
    });

    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
        return fmt;
    }

    $(".comment_btn").click(function () {
        var data = $(this).attr("data");
        var comment = $("#comment-"+data);
        if(comment.is(".in")){
            comment.removeClass("in");
            $(this).css("color", "");
        } else if(comment.children().length > 0){
            comment.addClass("in");
            $(this).css("color", "#666");
        } else {
            $.getJSON("/comment/"+data, function (result) {
                comment.addClass("in");
                var resultData = result.data;
                $.each(resultData, function (key, value) {
                    console.log(value);
                    var time = new Date(value.comment.gmtCreate).format("MM-dd hh:mm");
                    var html = "<div class=\"two_lv_comment\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<div class=\"media\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"media-left\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"#\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img class=\"media-object img-rounded comment_icon\" src=\""+ value.user.avatarUrl +"\">\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</a>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"media-body\" >\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<h5 text=\"" + "\" style=\"margin-top: 0px; color: #999\"> </h5>"+ value.user.name + "\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"\" style=\"margin-bottom: 10px\" text=\"" + "\">" + value.comment.content +"\n" +
                        "\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t<div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-thumbs-up icon\"></span>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"glyphicon glyphicon-comment icon comment_btn\" style=\"margin-left: 8px\" data=\"" + value.comment.id + "\"></span>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"pull-right\" style=\"color: #999\" text=\"'回复于 ' + ${#dates.format(commentDTO.comment.gmtCreate, 'MM-dd HH:mm')}\">回复于 "+ time +"</span>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t\t\t\t\t\t\t<hr>\n" +
                        "\t\t\t\t\t\t\t\t\t\t</div>"
                    comment.append(html);
                })
                var html = "<div>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" class=\"form-control\" placeholder=\"评论一下\" name=\"\" id=\"content-"+ data +"\" style='margin-top: 5px'>\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t<button class=\"btn btn-success pull-right tw_comment_btn\" type=\"submit\" style=\"margin: 8px 0px\" data=\""+ data +"\">回复</button>\n" +
                    "\t\t\t\t\t\t\t\t\t\t</div>";
                comment.append(html)
                $(this).css("color", "#666");
            })
        }
    });







    function comment_on(id, content, type) {
        if (!content){
            alert("你要说的话，空空的");
            return;
        }
        $.ajax({
            url: "/comment",
            contentType : 'application/json;charset=utf-8',
            type: "POST",
            data: JSON.stringify({
                "parentId": id,
                "content": content,
                "type": type
            }),
            success : function (data) {
                if (data.code == 2000){
                    $("#comment_frame").hide();
                    window.location.reload();
                } else if (data.code == 2002){
                    var flag =  confirm(data.message);
                    if (flag){
                        localStorage.setItem("close", "true");
                        window.open("https://github.com/login/oauth/authorize?client_id=13a5ddea610fa0692e1b&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                    }
                } else {
                    alert(data.message)
                }
            },
            dataType: "json"
        })
    }
    $(".tw_section ").on('click','.tw_comment_btn',function(){
        var data = $(this).attr("data");
        console.log("你");
        var content = $("#content-" + data).val();
        comment_on(data,content,2)
    });
})