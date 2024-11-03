// Toggle password visibility for login and signup forms
function togglePasswordVisibility(id) {
    const passwordField = document.getElementById(id);
    const icon = document.getElementById('togglePasswordIcon' + (id === 'password' ? '1' : '2'));

    if (passwordField.type === "password") {
        passwordField.type = "text";
        icon.classList.remove("fa-eye");
        icon.classList.add("fa-eye-slash");
    } else {
        passwordField.type = "password";
        icon.classList.remove("fa-eye-slash");
        icon.classList.add("fa-eye");
    }
}

// Sign-up form validation
function validateSignupForm() {
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    if (password !== confirmPassword) {
        alert("Passwords do not match!");
        return false;
    }

    const role = document.getElementById('role').value;
    alert(`Signup successful as ${role.charAt(0).toUpperCase() + role.slice(1)}! Redirecting to login...`);
    window.location.href = "/login"; // Change this to your login page URL
    return true;
}

// Add event listener for the signup form
document.getElementById('signup-form')?.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form from submitting to the server directly
    validateSignupForm();
});

// Function to initialize scroll reveal animations
function initScrollReveal() {
    ScrollReveal().reveal('.form-container', {
        origin: 'top',
        distance: '30px',
        duration: 1000,
        delay: 200,
        easing: 'ease-in-out'
    });
}

// Function to initialize Swiper carousel (if needed)
function initSwiper() {
    const swiper = new Swiper('.swiper-container', {
        slidesPerView: 1,
        spaceBetween: 10,
        loop: true,
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        breakpoints: {
            640: {
                slidesPerView: 2,
                spaceBetween: 20,
            },
            768: {
                slidesPerView: 3,
                spaceBetween: 30,
            },
            1024: {
                slidesPerView: 4,
                spaceBetween: 40,
            },
        },
    });
}

// Initialize all necessary scripts when the document is ready
document.addEventListener('DOMContentLoaded', function () {
    initScrollReveal();
    initSwiper();
});

let leftSlides = document.querySelectorAll('#left-sidebar .ad-slide');
let rightSlides = document.querySelectorAll('#right-sidebar .ad-slide');
let currentIndex = 0;

function showSlides(slides) {
    slides.forEach((slide, index) => {
        slide.classList.remove('active');
        if (index === currentIndex) {
            slide.classList.add('active');
            slide.style.animation = "slideY 0.5s ease-in-out";
        }
    });
}

function rotateAds() {
    currentIndex = (currentIndex + 1) % leftSlides.length;
    showSlides(leftSlides);
    showSlides(rightSlides);
}

setInterval(rotateAds, 3000);

// ScrollReveal initialization
function initScrollReveal() {
    ScrollReveal().reveal('#left-sidebar .ad-slide', {
        origin: 'left',
        distance: '50px',
        duration: 1000,
        delay: 200,
        easing: 'ease-in-out'
    });

    ScrollReveal().reveal('#right-sidebar .ad-slide', {
        origin: 'right',
        distance: '50px',
        duration: 1000,
        delay: 200,
        easing: 'ease-in-out'
    });
}

// Initialize ScrollReveal and other scripts on page load
document.addEventListener('DOMContentLoaded', function () {
    initScrollReveal();
});


