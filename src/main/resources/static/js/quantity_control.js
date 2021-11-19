$(document).ready(function () {
    $(".minusButton").on("click", function (evt) {
        evt.preventDefault();
        itemId = $(this).attr("iid");
        qtyInpit = $("#quantity" + itemId);

        newQty = parseInt(qtyInpit.val()) - 1;
        if (newQty > 0) qtyInpit.val(newQty);

    });

    $(".plusButton").on("click", function (evt) {
        evt.preventDefault();
        itemId = $(this).attr("iid");
        qtyInput = $("#quantity" + itemId);

        newQty = parseInt(qtyInput.val()) + 1;
        if (newQty < 20) qtyInput.val(newQty);

    });
});