var $j = jQuery.noConflict();


$j(document).ready(function($) {

	$("a.banner-popup-button").fancybox({
		//autoDimensions: false,
		padding: 0,
		margin: 0,
		scrolling: 'no',
		modal: true,
		overlayColor : '#ccc',
		overlayOpacity : 0.5
		//height: 231,
		//'transitionIn'	: 'none',
		//'transitionOut'	: 'none',
	});
	
	$("a.status-popup-button").fancybox({
		//autoDimensions: false,
		padding: 0,
		margin: 0,
		scrolling: 'no',
		modal: true,
		overlayColor : '#ccc',
		overlayOpacity : 0.5
		//height: 231,
		//'transitionIn'	: 'none',
		//'transitionOut'	: 'none',
	});
	
});
