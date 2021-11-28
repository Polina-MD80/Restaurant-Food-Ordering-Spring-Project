$(document).ready(function() {
    $(".linkMinus").on("click", function(evt) {
        evt.preventDefault();
        itemId = $(this).attr("iid");
        quantityInput = $("#quantity" + itemId);
        newQuantity = parseInt(quantityInput.val()) - 1;

        if (newQuantity > 0) {
            quantityInput.val(newQuantity);
            $("#qty" + itemId).val(newQuantity);
        } else {
            showWarningModal('Minimum quantity is 1');
        }
    });

    $(".linkPlus").on("click", function(evt) {
        evt.preventDefault();
        itemId = $(this).attr("iid");
        quantityInput = $("#quantity" + itemId);
        newQuantity = parseInt(quantityInput.val()) + 1;

        if (newQuantity <= 10) {
            quantityInput.val(newQuantity);
            $("#qty" + itemId).val(newQuantity);
        } else {
            showWarningModal('Maximum quantity is 10');
        }
    });
});