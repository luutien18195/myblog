function loadFile(e) {
    var reader = new FileReader();
    reader.onload = function () {
        var rs = document.getElementById("previewImg");
        rs.src = reader.result;
    };
    reader.readAsDataURL(e.target.files[0]);
}