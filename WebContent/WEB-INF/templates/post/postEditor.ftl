<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type"
    content="multipart/form-data; charset=utf-8" />
<title>Jerry Home Edtior</title>
<!-- Bootstrap -->
<link href="../bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="../amazeui/css/amazeui.min.css" rel="stylesheet" />
<link href="../editormd/css/editormd.css" rel="stylesheet" />
<link href="../bootstrap/fileinput/css/fileinput.min.css"
    rel="stylesheet" />
<link href="../css/editor.css" rel="stylesheet" />
<link href="../css/navbar.css" rel="stylesheet" />
</head>
<body>
    <#include "../common/header.ftl">
    <div class="main">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="editor-label">
                        <h1>发布文章</h1>
                    </div>
                    <form id="postForm" accept-charset="UTF-8" method="post"
                        enctype="multipart/form-data" class="post-form">
                        <a href="#postInfo" data-toggle="collapse" data-parent="#postForm" class="a-collapse"> <i
                            class="fa fa-angle-double-down i-toggle" aria-hidden="true"></i><span>收起</span></a>
                        <div id="postInfo" class="post-info collapse in">
                            <div class="input-group">
                                <div>
                                    <h4>标题</h4>
                                </div>
                                <input type="text" name="title" class="form-control form-input"
                                    placeholder="文章标题" required>
                            </div>
                            <div class="input-group">
                                <div>
                                    <h4>描述</h4>
                                </div>
                                <input type="text" name="description"
                                    class="form-control form-input" placeholder="文章描述" required>
                            </div>
                            <div class="input-group">
                                <div>
                                    <h4>主题</h4>
                                </div>
                                <input type="text" class="form-control form-input"
                                    name="subject" placeholder="文章主題，例如美食，科技，编程.." required>
                            </div>
                            <div class="input-group">
                                <div>
                                    <h4>标签</h4>
                                </div>
                                <input type="text" class="form-control  form-input" name="tags"
                                    placeholder="文章标签，例如美食，科技，编程.." required>
                            </div>
                            <div class="input-group post-image-group">
                                <div>
                                    <h4>主图片</h4>
                                </div>
                                <input id="postImage" name="postImage" type="file"
                                    class="file-loading image-upload-button">
                            </div>
                            <div class="content-label">
                                <h4>文章</h4>
                            </div>
                        </div>
                        <div class="editormd" id="postEditor">
                            <textarea class="editormd-markdown-textarea"
                                name="editormd-markdown-doc"></textarea>
                            <!--   第二个隐藏文本域，用来构造生成的HTML代码，方便表单POST提交，这里的name可以任意取，后台接受时以这个name键为准 -->
                            <textarea class="editormd-html-textarea"
                                name="editormd-html-code"></textarea>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="bottom-float-commit">
        <div class="container">
            <div class="row">
                <div class="col-md-11">
                    <input type="button" id="commitPost" name="commitPost" value="提交"
                        class="btn-post-commit pull-right" />
                </div>
            </div>
        </div>
    </div>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <script src="../js/jquery.form.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../bootstrap/fileinput/js/fileinput.min.js"></script>
    <script src="../bootstrap/js/bootstrap.min.js"></script>
    <script src="../editormd/js/editormd.min.js"></script>
    <script src="../bootstrap/fileinput/js/zh.js"></script>
    <script src="../amazeui/js/amazeui.min.js"></script>
    <script src="../js/editor.js"></script>
    <script src="../js/search.js"></script>
</body>
</html>
