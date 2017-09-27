<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>搜索结果</title>
<!-- Bootstrap -->
<link href="amazeui/css/amazeui.min.css" rel="stylesheet">
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="css/navbar.css" rel="stylesheet">
<link href="css/search.css" rel="stylesheet">
</head>
<body>
    <#include "../common/header.ftl">
    <div class="main search">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="col-md-8">
                        <ul id="searchTab" class="nav nav-tabs">
                            <li class="active"><a href="#article" data-toggle="tab">文章</a></li>
                            <li><a id="searchAuthors" href="#author" data-toggle="tab">作者</a></li>
                        </ul>
                        <div id="searchTabContent" class="tab-content">
                            <div class="tab-pane fade in active" id="article">
                                <#if searchResultNotEmpty>
                                <div class="posts">
                                    <#list searchResultPosts as post>
                                    <article class="post">
                                        <div class="post-user-card">
                                            <div class="sign">
                                                <h5>
                                                    <a class="username"> <img
                                                        src="${post.createUser.photo}"
                                                        class="img-circle user-avatar">
                                                        ${post.createUser.name}
                                                    </a> 发布了文章 -
                                                    <time> <#if (post.createDate)?string("yyyy") ==
                                                        .now?string("yyyy")> ${post.createDate?string("MM月dd日")}
                                                        <#else> ${post.createDate?string("yyyy年MM月dd日")} </#if> </time>
                                                </h5>
                                            </div>
                                        </div>
                                        <div class="post-head">
                                            <h3>
                                                <a href="post/${post.id?c}">${post.title}</a>
                                            </h3>
                                        </div>
                                        <div class="col-md-8">
                                            <div class="post-context">
                                                <#if post.description?length lt 110>
                                                <h5>${post.description}</h5>
                                                <#else>
                                                <h5>${post.description?substring(0,110)}...</h5>
                                                </#if>
                                            </div>
                                        </div>
                                        <div class="post-media col-md-4">
                                        <#if post.postImage??>
                                            <a href="post/${post.id?c}"><img class="post-image"
                                                src="${post.postImage}"></a>
                                        </#if>
                                        </div>
                                        <footer class="post-footer col-md-12">
                                            <div class="interacts">
                                                <span> <img src="images/post/like-icon.png"
                                                    alt="like"> <span>${post.postLikeCount}</span>
                                                </span> <span> <img src="images/post/comment-icon.png"
                                                    alt="comment"> <span>0</span>
                                                </span>
                                            </div>
                                        </footer>
                                    </article>
                                    </#list>
                                </div>
                                <a id="loadMoerBySearch" href="javascript:;"
                                    class="btn btn-default btn-lg btn-load-Moer col-md-12">加载更多</a>
                                <#else>
                                <h5>噢~没有找到你想要的...</h5>
                                </#if>
                            </div>
                            <div class="tab-pane fade" id="author">
                                <div class="authors">
                                    <ul class="list-group list-authors"></ul>
                                </div>
                                <a id="loadMoerSearchUsers" href="javascript:;"
                                    class="btn btn-default btn-lg btn-load-Moer col-md-12">加载更多</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../common/footer.ftl">
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <script src="amazeui/js/amazeui.min.js"></script>
    <script src="js/search.js"></script>
</body>
</html>
