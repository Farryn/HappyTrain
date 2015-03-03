$(document).ready(function() {
    var max_fields      = 10; //maximum input boxes allowed
    var wrapper         = $(".input_fields_wrap"); //Fields wrapper
    var add_button      = $(".add_field_button"); //Add button ID
    var remove_button   = $(".remove_field_button"); //Add button ID
    var td_to_remove    = $(".td_to_remove");
    
    var x = 1; //initlal text box count
    $(add_button).click(function(e){ //on add input button click
        e.preventDefault();
        if(x < max_fields){ //max input box allowed
            x++; //text box increment
            $(wrapper).append('<tr class="td_to_remove">'+
				            		'<td ><select name="stationList[]" >'+
				            			'<c:forEach var="item" items="${stationList}">'+
						  					'<option value="${item.name}" ><c:out value="${item.name}" /></option>'+
						  				'</c:forEach></select></td>'+
				            		'<td><input type="button" value="Удалить станцию" class="remove_field_button"></td>'+
			            		'</tr>'); 
        }
    });
    $(remove_button).on("click", function(e) {
    	e.preventDefault();
        $('tr').remove();
  });

   
});