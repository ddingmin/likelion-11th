const introtitle = document.getElementById('introtitle');
const hobbytitle = document.getElementById('hobbytitle');
const foodtitle = document.getElementById('foodtitle');
const etctitle = document.getElementById('etctitle');

const introitems = document.getElementById('introitems');
const hobbyitems = document.getElementById('hobbyitems');
const fooditems = document.getElementById('fooditems');
const etcitems = document.getElementById('etcitems');



introtitle.addEventListener('click', function(){
    if (introitems.style.display === "none") {
        introitems.style.display = "block";
    } else {
        introitems.style.display = "none";
    }
});
hobbytitle.addEventListener('click', function(){
    if (hobbyitems.style.display === "none") {
        hobbyitems.style.display = "block";
    } else {
        hobbyitems.style.display = "none";
    }
});
foodtitle.addEventListener('click', function(){
    if (fooditems.style.display === "none") {
        fooditems.style.display = "block";
    } else {
        fooditems.style.display = "none";
    }
});
etctitle.addEventListener('click', function(){
    if (etcitems.style.display === "none") {
        etcitems.style.display = "block";
    } else {
        etcitems.style.display = "none";
    }
});


