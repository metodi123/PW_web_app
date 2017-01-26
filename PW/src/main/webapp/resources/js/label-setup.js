function labelTextSetup(inputText, outputText, showElement) {
	document.getElementById(showElement).style.display = 'block';
	
	x=document.getElementsByClassName(outputText.className);
	for(var i = 0; i < x.length; i++) {
		x[i].innerHTML = inputText;
	}
}

function labelTextOnlySetup(inputText, outputText) {
	document.getElementById(outputText).innerHTML = inputText.value;
}

function labelColorOnlySetup(inputValue, outputValue) {
	document.getElementById(outputValue).style.backgroundColor = inputValue.value;
	document.getElementById(outputValue).style.borderColor = inputValue.value;
}

function showElement(showElement) {
	document.getElementById(showElement).style.display = 'block';
}

function selectLabel(radioButton) {
	document.getElementById(radioButton).click();
}

function selectLabelCheckbox(checkButton) {
	document.getElementById(checkButton).click();
}