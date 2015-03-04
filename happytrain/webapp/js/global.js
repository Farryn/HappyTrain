
$(function() {
	$('.datetimepicker_mask').datetimepicker({
		dayOfWeekStart : 1,
		lang:'en',
		defaultDate:'01.01.2015',
		defaultTime:'00:00',
		format:'d.m.Y H:i',
		mask:'39.19.9999 29:59'
	});
});
