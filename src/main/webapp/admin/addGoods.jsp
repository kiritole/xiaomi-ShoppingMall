<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="../css/bootstrap.min.css" />
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/DatePicker.js"></script>
<script type="application/javascript">

	$(function () {
		//事件绑定, 当浏览器加载页面的body标签后执行
		$("body").ready(function () {
			$.get(
					"${pageContext.request.contextPath}/type?method=findAll",
					function(res){
						var data = res.data
						//遍历返回的商品分类集合
						var htmlStr = "<option value=\"0\">---请选择---</option>";
						for(var i = 0; i < data.length; i++) {
							htmlStr += "<option value='"+data[i].id+"'>"+data[i].name+"</option>";
						}
						$("#typeid").html(htmlStr);
					}, "json"
			);
		});
	});
</script>
<title>商品添加页面</title>
</head>
<body>
	<div class="row" style="margin-left: 20px;">
		<form action="${pageContext.request.contextPath }/admin?method=addGoods" method="post" enctype="multipart/form-data">
			<div>
				<h3>新增商品</h3>
			</div>
			<hr />
			<div class="row">
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>名称:</label>
						<input type="text" name="name" class="form-control" />
					</div>
					
					<div class="form-group form-inline">
						<label>分类:</label>
						<select name="tid" class="form-control" id="typeid">


						</select>
					</div>
					<div class="form-group form-inline">
						<label>时间:</label>
						<input type="text" name="pubdate" readonly="readonly" class="form-control" onclick="setday(this)" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="form-group form-inline">
						<label>价格:</label>
						<input type="text" name="price" class="form-control" />
					</div>
					<div class="form-group form-inline">
						<label>评分:</label>
						<input type="text" name="star" class="form-control" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-10">
					<div class="form-group form-inline">
						<label>商品图片</label>
						<input type="file" name="picture"/>
					</div>
					<div class="form-group ">
						<label>商品简介</label>
						<textarea  name="intro" class="form-control" rows="5"></textarea>
					</div>
					<div class="form-group form-inline">
						<input type="submit" value="添加" class="btn btn-primary" />
						<input type="reset" value="重置" class="btn btn-default" />
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>