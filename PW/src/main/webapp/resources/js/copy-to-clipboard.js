$(document).ready(function() {

	var copyTextButtons = document.getElementsByClassName("copy-text-button");/*this should be the class of the button*/

	var myFunction = function() {
		var copyTextArea = document.getElementById("text-for-button-"+this.id);/*this should be the id of the text container*/
		copyTextArea.select();
		document.execCommand('copy');
	};

	for (var i = 0; i < copyTextButtons.length; i++) {
		copyTextButtons[i].addEventListener('click', myFunction, false);
	}
	
});