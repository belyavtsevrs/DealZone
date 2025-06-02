function toggleEditForm() {
    const form = document.getElementById('profileEditForm');
    const table = document.querySelector('.product-table');
    if (form.style.display === 'none' || !form.style.display) {
        form.style.display = 'table';
        table.style.display = 'none';
    } else {
        form.style.display = 'none';
        table.style.display = 'table';
    }
}
function toggleProductEditForm() {
    const view = document.getElementById("productView");
    const form = document.getElementById("productViewEditForm");

    if (form.style.display === "none") {
        view.style.display = "none";
        form.style.display = "block";
    } else {
        view.style.display = "block";
        form.style.display = "none";
    }
}
document.addEventListener("DOMContentLoaded", function () {
    const rows = document.querySelectorAll(".clickable-row");
    rows.forEach(row => {
        row.addEventListener("click", () => {
            const uuid = row.getAttribute("data-uuid");
            if (uuid) {
                window.location.href = `/products/product-info/${uuid}`;
            }
        });
    });
});

function validateForm() {
    const title = document.getElementById('title').value.trim();
    const description = document.getElementById('description').value.trim();
    const price = document.getElementById('price').value.trim();

    if (!title) {
        alert("Please enter a title ");
        return false;
    }

    if (title.length < 5) {
        alert("Title must be at least 5 characters long ");
        return false;
    }

    if (!description) {
        alert("Please enter a description.");
        return false;
    }

    if (!price || isNaN(price) || Number(price) <= 0) {
        alert("Please enter a valid price more than 0 ");
        return false;
    }


}
