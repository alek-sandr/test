$("#addRecordBtn").click(function() {
    $("#addFormContainer").removeClass("hidden");
    $("#addRecordBtn").addClass("hidden");
});

$("#cancelAddRec").click(function() {
    $("#addFormContainer").addClass("hidden");
	$("#recAddForm").trigger('reset');
	$("#addRecordBtn").removeClass("hidden");
});

function addRecord() {
    var article = new Object();
    article.title = $('#inputTitle').val();
    article.content = $('#inputContent').val();

    $.ajax({
        url: "addrecord",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(article),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
			$("#addFormContainer").addClass("hidden");
			$("#recAddForm").trigger('reset');
		    $("#addRecordBtn").removeClass("hidden");
            if (data.success) {
                var record = '<div class="container">' +
				    '<h3>' + data.record.title + '</h3>' +
                    '<p>' + data.record.content + '</p>' +
                    '<p class="text-right">posted at ' + formatDate(data.record.date) + '</p>' +
                    '</div>';
                $("#addFormContainer").after(record);
            } else {
                alert("Can't save record! " + data.message);
            }
            },
            error:function(data, status, er) {
                alert("Can't save record! " + data.responseText);
            }
        });
}

function formatDate(date) {
	var d = new Date(date);
	var hours = d.getHours();
	var minutes = d.getMinutes();
	if (minutes < 10) {
	    minutes = "0" + minutes;
	}
	var day = d.getDate();
	if (day < 10) {
		day = "0" + day;
	}
	var month = d.getMonth() + 1;
	if (month < 10) {
		month = "0" + month;
	}
	var year = d.getFullYear();
	return hours + ":" + minutes + " | " + day + "." + month + "." + year;
}

$("#recAddForm").on("submit", function( event ) {
    // Prevent the form's default submission.
    event.preventDefault();
    // Make an AJAX request to submit the form data
    addRecord();
});