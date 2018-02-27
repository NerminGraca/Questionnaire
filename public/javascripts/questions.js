$('#addQuestionButton').on('click', function() {
	let selectedValue = $('select[name=selector]').val();
	let index = $(this).attr('index');
	
	jsRoutes.controllers.Questionnaires.appendQuestion(index, selectedValue).ajax({
		success: function(data) {
			$('#questionsContent').append(data);
			$('#addQuestionButton').attr('index', parseInt(index) + 1);
			fixQuestionIndexes();
			enableDisableSubmitButton();
		},
		error: function() {}
	});
});

function addNewChoiceInput(id, counter, index) {
	$('#' + id).append(
			"<div id=\"choice-" + index + "-" + (parseInt(counter) + 1) + "\">" +
				"<label>Choice <span class=\"choice-label-" + index + "\">" + (parseInt(counter) + 1) + "</span></label>" +
				"<div class=\"input-group\">" +
					"<input type=\"text\" class=\"form-control picker has-error\" name=\"questions[" + index +"].offeredAnswers[" + counter +"].answer\">" +
					"<span class=\"input-group-btn\">" +
						"<a class=\"btn btn-danger\" onclick=\"removeChoice('choice-" + index + "-" + (parseInt(counter) + 1) + "')\">X</a>" +
					"</span>" +
				"</div>" +
			"</div>"
	);
	$('#' + id + '-button').empty();
	$('#' + id + '-button').append(
			"<a class=\"btn btn-success btn-sm\" onclick=\"addNewChoiceInput('" + id + "', " + (parseInt(counter) + 1) + ", " + index + ")\">" +
					"Add new choice <span class=\"glyphicon glyphicon-plus\"></span></a>"
	);
	enableDisableSubmitButton();
}

$('body').on('keyup', '.picker', function() {
	enableDisableSubmitButton();
});

function enableDisableSubmitButton() {

	const $inputPickers = $('input.picker').filter(function() {return $(this).val() == "";});
	const $textareaPickers = $('textarea.picker').filter(function() {return $(this).val() == "";});
	
	$('input.picker').each(function() {
		if ($(this).val() != "") {
			$(this).parent().parent().removeClass('has-error');
			$(this).parent().removeClass('has-error');
			$(this).removeClass('has-error');
		} else {
			$(this).parent().parent().addClass('has-error');
			$(this).parent().addClass('has-error');
			$(this).addClass('has-error');
		}
	});
	
	$('textarea.picker').each(function() {
		if ($(this).val() != "") {
			$(this).parent().removeClass('has-error');
			$(this).removeClass('has-error');
		} else {
			$(this).parent().addClass('has-error');
			$(this).addClass('has-error');
		}
	});
	
	$("#submitQuestionnaireButton").prop("disabled", $inputPickers.length > 0 || $textareaPickers.length > 0);
}

$('body').on('keyup', '.selector', function() {
	enableDisableSaveButton();
});

$('.selector-parent').on('change', function() {
	enableDisableSaveButton();
}).change();

function enableDisableSaveButton() {
	
	const $textareaSelectors = $('textarea.selector').filter(function() {return $(this).val() == "";});
	let $checkerSelectors = 0;
	
	$('textarea.selector').each(function() {
		if ($(this).val() != "") {
			$(this).parent().removeClass('has-error');
			$(this).removeClass('has-error');
		} else {
			$(this).parent().addClass('has-error');
			$(this).addClass('has-error');
		}
	});
	
	$('.selector-parent').each(function(i, element) {
		let isChecked = false;
		$(element).children().each(function(i, child) {
			if ($(":first-child", child).is(':checked')) {
				isChecked = true;
			}
		});
		if (!isChecked) {
			$checkerSelectors += 1;
			$(element).parent().addClass('has-error');
		} else {
			$(element).parent().removeClass('has-error');
		}
	});
	
	$("#saveQuestionnaireButton").prop("disabled", $textareaSelectors.length > 0 || $checkerSelectors > 0);
	
}

function removeQuestion(id) {
	if (confirm('Are you sure?')) {
		$('#' + id).remove();
		fixQuestionIndexes();
	}
}

function removeChoice(id) {
	if (confirm('Are you sure?')) {
		$('#' + id).remove();
		fixChoiceIndexLabels('.choice-label-' + id.split('-')[1]);
	}
}

function fixQuestionIndexes() {
	$('.question-index-label').each(function(i, element) {
		$(element).text(i + 1);
	});
}

function fixChoiceIndexLabels(className) {
	$(className).each(function(i, element) {
		$(element).text(i + 1);
	});
}
