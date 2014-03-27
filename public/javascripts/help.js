/**
 * This generates a <div> with the name "dialog_help" and replaces the placeholder in main.scala.html. The text is located under
 * public/help/help.txt. The dialog interface is provided by jQuery.
 */
function genHelp() {
    var el = $("#dialog_help");
    var loc = window.location;
    var url = "http://" + getUrl(loc) + "assets/help/help.txt";
    $.ajax({
        type: "get",
        url: url,
        success: function(data){
            var div = $("<div>").html(data).css({width:"780px",height:"525px","overflow-y":"scroll",fontSize:"14px"});
            var d = el.dialog({
                width: 800,
                height: 600,
                resizable: false,
                draggable: false,
                modal: true
            });
            el.html(div);
        }
    });
}