window.onload = function() {
    let form = document.forms[0];
    form.bday.oninput = function() {
        let length = this.value.length;
        if (length === 2 || length === 5) {
            this.value = this.value + "/";
        }
    }
}