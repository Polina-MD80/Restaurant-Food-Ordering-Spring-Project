

$(document).ready(function () {
    $(".linkMinus").on("click", function (evt) {
        evt.preventDefault();
        decreaseQuantity($(this));
    });

    $(".linkPlus").on("click", function (evt) {
        evt.preventDefault();
        increaseQuantity($(this));
    });

    $(".linkRemove").on("click", function (evt) {
        evt.preventDefault();
        removeProduct($(this));
    });
});

function decreaseQuantity(link) {
    itemId = link.attr("iid");
    quantityInput = $("#quantity" + itemId);
    newQuantity = parseInt(quantityInput.val()) - 1;

    if (newQuantity > 0) {
        quantityInput.val(newQuantity);
        updateQuantity(itemId, newQuantity);
    } else {
        showWarningModal('Minimum quantity is 1');
    }
}

function increaseQuantity(link) {
    itemId = link.attr("iid");
    quantityInput = $("#quantity" + itemId);
    newQuantity = parseInt(quantityInput.val()) + 1;

    if (newQuantity <= 10) {
        quantityInput.val(newQuantity);
        updateQuantity(itemId, newQuantity);
    } else {
        showWarningModal('Maximum quantity is 10');
    }
}

function updateQuantity(itemId, quantity) {
    url = contextPath + "cart/update/" + itemId + "/" + quantity;


    $.ajax({
        type: "POST",
        url: url,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(csrfHeaderName, csrfValue);
        }

    }).done(function (updatedSubtotal) {
        updateSubtotal(updatedSubtotal, itemId);
        updateTotal();
        removeSuccessHtml(success)


    }).fail(function () {
        showErrorModal("Error while updating product quantity.");
    });
}

function updateSubtotal(updatedSubtotal, itemId) {
    $("#subtotal" + itemId).text(updatedSubtotal);
}


function updateTotal() {
    total = 0.0;


    $(".subtotal").each(function (index, element) {
        total += parseFloat(element.innerHTML);
    });

    $("#total").text($.number(total, 2));
}


function removeProduct(link) {
	url = link.attr("href");

	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(response) {
		rowNumber = link.attr("rowNumber");
        removeProductHTML(rowNumber);
        success = link.attr("success")
        removeSuccessHtml(success)
		updateTotal();
		updateCountNumbers();

		showModalDialog("Cart", response);

	}).fail(function() {
		showErrorModal("Error while removing item.");
	});
}

function removeProductHTML(rowNumber) {
	$("#row" + rowNumber).remove();
	$("#blankLine" + rowNumber).remove();
}
function removeSuccessHtml(success){
    $("#success").remove();
}


function updateCountNumbers() {
	$(".divCount").each(function(index, element) {
		element.innerHTML = "" + (index + 1);
	});
}
