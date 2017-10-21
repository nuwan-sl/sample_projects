/**
 * Copyright (c) 2014. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by Madawa Jeevananada on Aug 8, 2014 2:41:31 PM
 */

/**
 * Wrapper for JQuery ajax request. All the parameters will be provided within a JSON array
 * 
 *  requestType - Request method
 *  url - relative url for the request
 *  dataType - html or json. Default HTML
 *  data - parameters as a JSON array
 *  timeout - timeout for the ajax request
 *  startProgress - start waiting animation
 *  endProgress - stop waiting animation
 *  success - success callback
 *  
 * @param opt
 */

$(document).ready(function(){
intLogOut();
closePopup();
});

//setting up the value inside the inline script in selectedPkg.html 
var messageBundle = [];

function doAjax(opt)
{
	_reqType = opt.requestType ? opt.requestType : "POST";
//	_url = $("#webBase").val() + opt.url +"?ts=" + new Date().getTime();
	_url = $("#webBase").val()+opt.url +"?ts=" + new Date().getTime();
	_data = opt.data ? opt.data : {};
	_dataType = opt.dataType ? opt.dataType : "html";
	_timeout = opt.timeout ? opt.timeout : 1800000;
	_extraData = opt.extraData;
	if(opt.startProgress)
	{
		opt.startProgress();
	}
	
	$.ajax({
		type: _reqType,
		url:_url ,
		data:_data,
		dataType : _dataType,
		timeout : _timeout,
		success: function(data)
		{
			if(opt.endProgress)
			{
				opt.endProgress();
			}
			if(opt.success){
				opt.success(data, _extraData);
			};
		},
		error: function(err, status, errTh)
		{

			if(opt.endProgress)
			{
				opt.endProgress();
			}
			// Redirect to Error page
			// window.location = "error/genericError";
		}
	
	});
}

function replaceExistingContent(content, element)
{
	element.html(content);	
}

//trim function
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
};

function formatCurrency(price, hideDpIfWholeNum) 
{
    var priceFormat = $("#priceFormat").val();
	var pfArray = priceFormat.split(".");
	var decimalPoints = pfArray.length > 1 ? pfArray[1].length : 0;
	var separator = ",";
	
	return getCurrencySymbol() + parseFloat(price).toFixed( decimalPoints ).replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1" + separator);
}

function handlePageOverFlow(isHidden){
	if(isHidden){
		//$('body').css('overflow','hidden');
		
		$('body').css({
			   'position' : 'fixed',
			   	'left' : '482px',
			   	'margin-left' : '-482px',
			   	'width':'100%'
			});
	}
	else
		$('body').attr('style', '');
}

function getCurrencySymbol(){
	
	var currencySymbol = '';
    if( $('#currencySymbol').length >0 ){
    	currencySymbol = $('#currencySymbol').val();
    }
    else{
    	currencySymbol = '&pound; ';
    }
    
    return currencySymbol;
}

function intLogOut() {
	
	$(".logOut").unbind("click");
	$(".logOut").bind("click" , function(event)
	{
		doAjax({
			requestType: "GET",
			url: "ppoLogOut", 
			data: "",
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				logOutCallBack(data);
			}
		});
	});
	
}


function logOutCallBack(data) 
{
	var SUCCESS = 'SUCCESS';
	var ERROR = 'ERROR';
	
	var brandCode = $("#brandCode").val();
	if( data && data == SUCCESS)
	{
		window.location.href = $("#webBase").val() +"loadBooking" +"?brand="+brandCode+"&fromIndex=true";
	}
	else if (data && data == ERROR)
	{
		window.location.href = $("#webBase").val() +"loadBooking" +"?brand="+brandCode+"&fromIndex=true";
	}
	else
	{
		window.location.href = $("#webBase").val() +"loadBooking" +"?brand="+brandCode+"&fromIndex=true";
	}
}

function closePopup(){
	
	$( ".clsPoup" ).bind( "click", function() {
		$('#popDivContainer').addClass("hidePopUp");
		});
}


