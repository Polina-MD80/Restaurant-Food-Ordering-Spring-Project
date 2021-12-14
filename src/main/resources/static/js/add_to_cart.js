// CSRF tokens
// const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
// const csrfValue = document.head.querySelector('[name="_csrf"]').content;

// All add to cart forms
const addToCartButtons = document.querySelectorAll('.orderForm')

// intercept any of these forms
addToCartButtons.forEach(function(currentBtn){
    currentBtn.addEventListener('submit', handleAddToCart)
})

// add to cart
async function handleAddToCart(event) {
    // do not submit the form
    event.preventDefault();

    const form = event.currentTarget;
    // url
    const url = form.action;
    // form data
    const formData = new FormData(form);
    const plainFormData = Object.fromEntries(formData.entries());
    const formDataAsJSONString = JSON.stringify(plainFormData);

    // prepare the POST request
    const fetchOptions = {
        [csrfHeaderName] : csrfValue,
        method: "POST",
        headers: {
            [csrfHeaderName] : csrfValue,
            "Content-Type" : "application/json",
            "Accept" :"application/json"
        },
        body: formDataAsJSONString
    }

    // FETCH it!
    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        // handle the error here
        showErrorModal("ERROR")
    }

    // Show the happy news
    showModalDialog("Shopping Cart", "You added the item to your cart!");

}