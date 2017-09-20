$(function(){

    $('#search').click(function(){
        window.location.href = 'searchs?article=' + $('#inputSearch').val();
        window.event.returnValue = false;
    });

    $("#inputSearch").keydown(function(event) {
        if (event.keyCode == 13) {
            window.location.href = 'searchs?article=' + $('#inputSearch').val();
            window.event.returnValue = false;
        }
    });

    $('#loadMoerBySearch').click(function(){
        loadMoerBySearch();
    });

    $('#loadMoerSearchUsers').click(function(){
        loadMoerAuthors();
    })

    $('#searchAuthors').on('show.bs.tab',function(e){
        searchAuthors();
    })

    //write
    $('#writingBtn').click(function(){
        userIsLoggedIn();
    });

    $('#writingWindow').click(function(){
        userIsLoggedIn();
    });
})

function searchAuthors(){
    var searchHeader = decodeURI(location.search);
    $.get('searchUsers',{searchHeader: searchHeader},function(result){
        $('.list-authors').empty();
        nextAuthorsPage = 2;
        if(result.authors.length > 0){
            $('#loadMoerSearchUsers').attr('disabled',false);
            $('#loadMoerSearchUsers').html('加载更多');
            $.each(result.authors, function(index,obj){
                $('.list-authors').append(
                        "<li class='list-group-item'>"+
                        "<a href='#'><img class='list-group-user-img' src='"+obj.photo+"'></a>"+
                        "<h5><a href='#'>"+obj.name+"</a></h5>"+
                    "</li>");
            })
        } else {
            $('.list-authors').append("<h5>噢~没有找到你想要的...</h5>");
            $('#loadMoerSearchUsers').hide();
        }
    })
}

var nextAuthorsPage = 2;
function loadMoerAuthors(){
    var searchHeader = decodeURI(location.search);
    $.get('loadMoerSearchResult',{nextPage: nextAuthorsPage,searchHeader: searchHeader,isSearchAuthors: true},function(result){
        if(result.authors.length > 0){
            $.each(result.authors, function(index,obj){
                $('.list-authors').append(
                        "<li class='list-group-item'>"+
                        "<a href='#'><img class='list-group-user-img' src='"+obj.photo+"'></a>"+
                        "<h5><a href='#'>"+obj.name+"</a></h5>"+
                    "</li>");
            })
            nextAuthorsPage ++;
            $('#loadMoerSearchUsers').blur();
        } else {
            $('#loadMoerSearchUsers').html('没有更多的用户啦');
            $('#loadMoerSearchUsers').attr('disabled','disabled');
        }
    })
}


var nextSearchPage = 2;
function loadMoerBySearch(){
    var searchHeader = decodeURI(location.search);
    $.get('loadMoerSearchResult',{nextPage: nextSearchPage,searchHeader: searchHeader},function(result){
        if(result.nextPageResult.length > 0){
            $.each(result.nextPageResult, function(index,obj){
                $('.posts').append(
                        "<article class='post'>"+
                        "<div class='post-head'><h3><a href='post/"+obj.id+"'>"+obj.title+"</a></h3></div>"+
                        "<div class='post-context'><h5>"+obj.description+"</h5></div>"+
                        "<footer class='post-footer'><div class='interacts'><span> <img src='images/post/like-icon.png' alt='like'> <span>5</span></span> <span> <img src='images/post/comment-icon.png'alt='comment'> <span>20</span></span></div><div class='sign'><time>"+timeStamp2String(obj.createDate)+"</time><p class='author'>"+obj.createUser.name+"</p></div></footer></article>"
                );
            })
            nextSearchPage ++;
            $('#loadMoerBySearch').blur();
        } else {
            $('#loadMoerBySearch').html('没有更多的帖子啦');
            $('#loadMoerBySearch').attr('disabled','disabled');
        }
    })
}

function timeStamp2String(time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    //var hour = datetime.getHours()< 10 ? "0" + datetime.getHours() : datetime.getHours();
    //var minute = datetime.getMinutes()< 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    //var second = datetime.getSeconds()< 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    //return year + "-" + month + "-" + date+" "+hour+":"+minute+":"+second;
    return year + "-" +month + "-" + date;
}

function userIsLoggedIn(){
    $.get('userIsLoggedIn',function(result){
        if (result){
            window.location.href='post/editor';
            window.event.returnValue = false;
        } else {
            $('#editorModal').modal('open');
        }
    })
}
