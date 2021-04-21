function customAlert(type, title, message) {
    $("#customAlertLabel").html('<span class="badge bg-' + type + '">' + title + '</span>');
    $("#customAlertMessage").html(message);
    $('#customAlert').modal('show');
}