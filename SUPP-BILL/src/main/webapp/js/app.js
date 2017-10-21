$(document).ready(function(){
	
	//this run as the first method when loading the page
	
});

function openClaim(user) {
	
	doAjax({
		requestType: "GET",
		url: "openClaim", 
		data: "user="+user,
		success: openClaimCallback
	});
}

function openClaimCallback(data, element)
{
	
	var error = false;
	var response = $('<div/>').html(data);
	
	if(response.find('.header') .length != 0)
	{
		error = true;
	}
	
	data = response.html();
	
	$('#popDivContainer').find('.modal-body').html(data);
	$('#popDivContainer').removeClass("hidePopUp");
}