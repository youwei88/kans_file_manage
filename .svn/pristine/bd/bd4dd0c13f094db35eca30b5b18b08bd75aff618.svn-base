/**
 * 分页控件JS
 * 
 *准备工作：
 *	(1)引入public.css样式文件；
 *	(2)引入page.js脚本文件；
 *	(3)以及Jquery相关资源文件
 * 
 * 在要进行分页的页面作如下设置
 * 	<1> 将查询form表单中的submit按钮的ID指定为submitBtn
 *  <2> form表单中添加两个隐藏域hidden分别存储页面号和页面尺寸
 *  	如：
 *   <input type="hidden" id="pageNum" name="pageNum" value="${page.pageNum}" />
 *   <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
 *  <3>添加分页层,即DIV层
 *  	 <div id="pageMod" class="page" _pages="${page.pages}" _pageNum="${page.pageNum}" _pageSize="10", _total="${page.total}" _ajaxParam="false">
 *			<div id="pagnation"  align="center" class="page"> </div>
 *		</div>
 *
 * 工作流程：
 * 		(A)同步分页：设置参数_ajaxParam="false"，每次点击查询按钮或者分页链接进行分页查询，都是进行的form表单同步提交，会刷新整个页面；
 * 由此为带来查询条件丢失的问题，解决方法是在后台存储，查询完成后进行返回，还原页面现场;
 * 		(B)异步分页：设置参数_ajaxParam="true"，同时需要自己提供回调函数ajaxPageSubmit(),并处理结果
 */

// 因为是JS渲染分页控件，加上标识变量避免重复渲染
var isCreationFlag = false;
	
$(function(){
	
	// 分页JS 控件
	var _Nav = $('#pagnation');
	
	// 获取参数值：当前页面号、总页数、总记录数
	var pageNum = $("#pageMod").attr("_pageNum");
	var pages = $("#pageMod").attr("_pages");
	var total = $("#pageMod").attr("_total");
	
	if (!isCreationFlag) {
		//1.创建页面链接
		createLinks(pageNum, pages, total);


		//2.处理当前页、上一页和下一页的状态(当前页选中，上一页和下一页显示或隐藏)
		processLinkState(pageNum);


		//3.为页面链接注册点击事件
		registSplitPageEve();
    
		//4.置标识变量为：已创建  
		isCreationFlag = true;
		
	}
    /********************************* 功能函数定义 *************************************/
    
    
  /***
   * 【功能】：创建链接
   * @param pageNum 当前页面号
   * @param pages 总页数
   * @param total 总记录数 
   */
    function createLinks(pageNum, pages, total){
    	
    	var pageTotal = parseInt(pages);
    	pageNum = parseInt(pageNum);
    	if (pageTotal > 1) {
    		_Nav.append('<a href="javascript:;" id="prev">上一页</a>');
		}
    	
    	if (pageTotal <= 9) {  // 少于包括9个分页链接时，进行全显示
    		for(var i=0;i< pageTotal;i++){
    			_Nav.append('<a href="javascript:void(0);" class="numlink">'+(i+1)+'</a>');
    		}
		}else{ // 分三种情形展示分页链接
			if (pageNum <= 5) {  // （1）省略号：L无，R有
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 1 +'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 2 +'</a>');
				for(var i=3;i < 8;i++){
	    			_Nav.append('<a href="javascript:void(0);" class="numlink">'+(i)+'</a>');
	    		}
				_Nav.append('...');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal - 1)+'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal )+'</a>');
			}
			else if (pageNum < pageTotal -4) { // （2）省略号：L有，R有
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 1 +'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 2 +'</a>');
				_Nav.append('...');
				for(var i=-2;i < 3;i++){
	    			_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageNum + i)+'</a>');
	    		}
				_Nav.append('...');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal - 1)+'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal )+'</a>');
			}
			else{  // （3）省略号：L有，R无
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 1 +'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+ 2 +'</a>');
				_Nav.append('...');
				for(var i=6;i > 1;i--){
	    			_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal - i)+'</a>');
	    		}
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal - 1)+'</a>');
				_Nav.append('<a href="javascript:void(0);" class="numlink">'+(pageTotal )+'</a>');
			}
		}
    	
        if (pageTotal > 1) {
        	_Nav.append('<a href="javascript:;" id="next">下一页</a>');
        }
        _Nav.append('<a href="javascript:;" class="co_gray">共<font class="co_or">'+ total + '</font>个结果</a>');
    }  
	
    /**
     * 【功能】：处理当前页、上一页和下一页的显示状态
     *  @param pageNum 当前页面号
     *  
     */
    function processLinkState(pageNum){
        var _Next = $('#next');
        var _Prev = $('#prev');
        var currPageNum = parseInt(pageNum);
    	
        //高亮显示当前页
        $('.numlink').each(function(){
        	if (currPageNum == parseInt($(this).text()) ) {
        		$(this).addClass('current');
    		}
        });
        
        var pages = parseInt( $("#pageMod").attr("_pages") );
        if(currPageNum == 1){
            _Prev.hide();
            _Next.show();
        }
        else if(currPageNum == pages){
            _Prev.show();
            _Next.hide();
        }
        else {
            _Prev.show();
            _Next.show();
        } 
    }
	
    /**
     * 【功能】：注册分页查询事件：下一页、上一页、页面号点击事件
     * 
     */
    function registSplitPageEve(){
    	// 判断是否需要异步提交
        var ajaxParam = $("#pageMod").attr("_ajaxParam");
        var isAjax = false;
        if (ajaxParam == 'true' || ajaxParam == true ) {
        	isAjax = true;
    	}
    	
        var _Next = $('#next');
        var _Prev = $('#prev');
        var _Link = $('.numlink');
        
    	_Next.click(function(){
    		//把将要显示的页面设置到form表单控件里
    		var currPageNum = parseInt( $('.numlink.current').text() );
    		var nextPageNum = currPageNum + 1;
    		$("#pageNum").val(nextPageNum);

    		// 提交：form的同步提交；自定义的异步提交
    		pageSubmit(isAjax, nextPageNum);

    	});
    	_Prev.click(function(){
    		var currPageNum = parseInt( $('.numlink.current').text() );
    		var prevPageNum = currPageNum - 1;
    		$("#pageNum").val(prevPageNum);
    		pageSubmit(isAjax, prevPageNum);

    	});
    	_Link.click(function(){
    		var which = $(this).text();
    		$("#pageNum").val(which);

    		pageSubmit(isAjax, which);

    	});
    }
    
    /**
     * 【功能】：进行分页提交
     * @param isAjax 是否异步
     * @param pageNum 页面号参数
     */
    function pageSubmit(isAjax, pageNum){
    	$("#pageNum").val(pageNum);
    	//进行form表单同步提交，刷新整个页面，重新渲染分页js
    	if (!isAjax) {
    		$("#submitBtn").click();
    	}else{  // 异步提交
    		
    		// 分页JS 控件
    		$('#pagnation').empty(); // 清空上次的内容
    		var _Nav = $('#pagnation');
    		
    		//1.创建页面链接
    		createLinks(pageNum, pages, total);
    	    
    	    //2.处理当前页、上一页和下一页的状态(当前页选中，上一页和下一页显示或隐藏)
    	    processLinkState(pageNum);
    	    
    	    //3.为页面链接注册点击事件
    	    registSplitPageEve();
    		
    		
    		//回调函数，开发者自己定义
    		var srcUrl = $("#srcUrl").val();
    		ajaxPageSubmit(srcUrl);
    	}
    }
	
	
})