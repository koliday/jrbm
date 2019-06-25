'use strict';
var KTDatatablesBasicBasic = function() {

	var initTable1 = function() {
		var table = $('#kt_table_1');
        var lang = {
            'sProcessing':'处理中...',
            'sLengthMenu': '每页 _MENU_ 项',
            'sZeroRecords': '没有匹配结果',
            'sInfo': '当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。',
            'sInfoEmpty': '当前显示第 0 至 0 项，共 0 项',
            'sInfoFiltered': '(由 _MAX_ 项结果过滤)',
            'sInfoPostFix': '',
            'sSearch': '搜索:',
            'sUrl': '',
            'sEmptyTable': '表中数据为空',
            'sLoadingRecords': '载入中...',
            'sInfoThousands': ',',
            'oPaginate': {
                'sFirst': '首页',
                'sPrevious': '上页',
                'sNext': '下页',
                'sLast': '末页',
                'sJump': '跳转'
            },
            'oAria': {
                'sSortAscending': ': 以升序排列此列',
                'sSortDescending': ': 以降序排列此列'
            }
        };

		// begin first table
		table.DataTable({
			responsive: true,
			lengthMenu: [5, 10, 25, 50],
            paging:true,
            ordering:false,
			pageLength: 10,
			processing:true,
			//是否由服务端进行分页和过滤
            serverSide:true,
            ajax: function (data, callback) {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8888/freemarketpage',
                    // cache: false,  //禁用缓存
                    data: {
                        draw:data.draw,
                        start:data.start,
                        length:data.length
                    },
                    dataType:'json',
                    success: function (result) {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = result.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.recordsTotal;//返回数据全部记录
                        returnData.recordsFiltered = result.recordsFiltered;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.data;//返回的数据列表
                        callback(returnData);
                    },
                    error:function(jqXHR,textStatus,errorThrown){
                        alert("自由市场加载错误！");
                    }
                });
            },
            //columns数量必须一致
            columns: [
                { 'data': 'basicplayer.bpid' },
                { 'data': 'fpid' },
                { 'data': 'basicplayer.chname' },
                { 'data': 'basicplayer.bpid' },
                { 'data': 'basicplayer.offensive' },
                { 'data': 'basicplayer.defensive' },
                { 'data': 'basicplayer.position' },
                { 'data': 'freetime' },
                { 'data': 'source' },
                { 'data': 'source' }
            ],
			//提示信息
			language: lang,
			// Order settings
			order: [],
            columnDefs: [
                {
                    targets: -1,
                    title: '操作',
                    orderable: false,
                    render: function(data, type, full, meta) {
                        return `
                        <span class="dropdown">
                            <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" aria-expanded="true" title="详细">
                              <i class="la la-ellipsis-h"></i>
                            </a>
                        </span>
                        <a href="#" class="btn btn-sm btn-clean btn-icon btn-icon-md" title="签约">
                          <i class="la la-edit"></i>
                        </a>`;
                    },
                }
                ]
		});
	};

	return {

		//main function to initiate the module
		init: function() {
			initTable1();
		}

	};

}();

jQuery(document).ready(function() {

	KTDatatablesBasicBasic.init();




});