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
            var elem2 = $("<br>");
            $("#fileList").append(elem2);
            lastFilesData = filesData;
        }
    }
}
function loadPage() {
    $.get("/files", getFiles);
}
loadPage();
setInterval(loadPage, 1000)