lastFilesData = null;
function getFiles(filesData) {
    $.get("/files", getFiles);
    if (JSON.stringify(lastFilesData) !== JSON.stringify(filesData)) {
        $("#fileList").empty();
        for (var i in filesData) {
            var elem = $("<a>");
            elem.attr("href", "files/" + filesData[i].filename);
            elem.text(filesData[i].comment);
            $("#fileList").append(elem);
            var elem2 = $("<form>");
            elem2.attr("id", i)
            elem2.attr("action", "/delete");
            elem2.attr("method", "post");
            var input = $("<input>");
            input.attr("type", "hidden");
            input.attr("name", "id");
            input.attr("value", filesData[i].id)
            var button = $("<button>");
            button.attr("type", "submit");
            button.text("Delete");
            $("#fileList").append(elem2);
            $("#" + i).append(input);
            $("#" + i).append(button);

            lastFilesData = filesData;
        }
    }
}
function loadPage() {
    $.get("/files", getFiles);
}
loadPage();
setInterval(loadPage, 1000)