$(document).ready(function() {
    var wrapper         = $("#wrapper-ul"); //Fields wrapper
    var add_button      = $("#add_button"); //Add button ID
    var remove_button   = $(".remove_field_button"); //Add button ID
    var td_to_remove    = $(".td_to_remove");
    
    $(add_button).click(function(e){ //on add input button click
    	e.preventDefault();
        $(wrapper).append('<li>'+
				            		'<select name="stationList[]" >'+
				            			'<c:forEach var="item" items="${stationList}">'+
						  					'<option value="${item.name}"> <c:out value="${item.name}" /> </option>'+
						  				'</c:forEach></select>'+
						  				
					            		//'<input type="button" value="Удалить станцию" class="remove_field_button">'+
			            		'</li>'); 
        
    });
    $(remove_button).on("click", function(e) {
    	e.preventDefault();
        $('tr').remove();
  });

   
});