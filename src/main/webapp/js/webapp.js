//--- common code -----------------------------------------------------------
function formatDate(date) {
	var d = new Date(date);
	var hours = d.getHours();
	if (hours < 10) {
    	hours = "0" + hours;
    }
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

//--- page.jsp code --------------------------------------------------------
$("#addRecordBtn").click(function() {
    $("#addRecordBtn").hide("fast", function() {
        //workaround of bootstrap has display: none !important on hidden class
		$("#addFormContainer").removeClass("hidden").hide().slideDown();
	});
});

function closeAddRecordForm() {
	$("#addFormContainer").hide("fast", function() {
		$("#addRecordBtn").show();
	});
	$("#recAddForm").trigger('reset');
}

$("#cancelAddRec").click(function() {
    closeAddRecordForm();
});

function addRecord() {
    var article = new Object();
    article.title = $('#inputTitle').val();
    article.description = $('#inputDescription').val();
    article.content = $('#inputContent').val();

    $.ajax({
        url: "addrecord",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(article),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
			closeAddRecordForm();
            if (data.success) {
                var record = '<div class="container">' +
				    '<h3><a href="record?id=' + data.id + '">' + data.title + '</a></h3>' +
                    '<p>' + data.description + '</p>' +
                    '<p class="text-right">posted at ' + formatDate(data.date) + '</p>' +
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

$("#recAddForm").on("submit", function( event ) {
    // Prevent the form's default submission.
    event.preventDefault();
    addRecord();
});


// --- record.jsp code ---------------------------------------------------------
$("#addCommentBtn").click(function() {
    $("#addCommentBtn").hide("fast", function() {
        //workaround of bootstrap has display: none !important on hidden class
		$("#addCommentContainer").removeClass("hidden").hide().slideDown();
	});
});

function closeAddCommentForm() {
	$("#addCommentContainer").hide("fast", function() {
		$("#addCommentBtn").show();
	});
	$("#commentAddForm").trigger('reset');
}

$("#cancelAddComment").click(function() {
    closeAddCommentForm();
});

function addComment() {
    var comment = new Object();
    comment.content = $('#inputContent').val();
    comment.recordId = $('#recordId').val();

    $.ajax({
        url: "addcomment",
        type: "POST",
        dataType: "json",
        data: JSON.stringify(comment),
        contentType: 'application/json',
        mimeType: 'application/json',

        success: function (data) {
			closeAddCommentForm();
            if (data.success) {
                if ($("#no-comments").length) {
                    $("#no-comments").remove();
                }
                var comment = '<div class="comment">' +
				    '<p><a href="user?login=' + data.username + '"><strong>' + data.username +
				    '</strong></a>, <small>' + formatDate(data.date) + '</small></p>' +
                    '<div class="content">' + data.content + '</div>' +
                    '</div>';
                $("#comments").append(comment);
            } else {
                alert("Can't save comment! " + data.message);
            }
            },
            error:function(data, status, er) {
                alert("Can't save comment! " + data.responseText);
            }
        });
}

$("#commentAddForm").on("submit", function( event ) {
    event.preventDefault();
    addComment();
});