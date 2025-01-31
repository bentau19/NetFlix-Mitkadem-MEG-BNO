// Open the popup
function openPopup() {
    const selected = document.getElementById('movieCategorySelect').value;
    const categoryForm = document.getElementById('categoryForm');
    const movieForm = document.getElementById('movieForm');
    
    if (selected === 'category') {
        categoryForm.style.display = 'block';
        movieForm.style.display = 'none';
    } else {
        categoryForm.style.display = 'none';
        movieForm.style.display = 'block';
    }
    
    document.getElementById('popup').style.display = 'flex';
}

// Close the popup
function closePopup() {
    document.getElementById('popup').style.display = 'none';
    // Clear the forms
    document.getElementById('categoryName').value = '';
    document.getElementById('promoted').checked = false;
    document.getElementById('movieTitle').value = '';
    document.getElementById('movieLogline').value = '';
    document.getElementById('movieImage').value = '';
    document.getElementById('movieCategories').value = '';
}

// Create a new category
async function createCategory() {
    const name = document.getElementById('categoryName').value;
    const isPromoted = document.getElementById('promoted').checked;
    
    if (!name) {
        alert('Please enter a category name');
        return;
    }

    try {
        const response = await fetch('/api/categories', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ 
                name: name, 
                promoted: isPromoted 
            })
        });

        const data = await response.json();
        console.log('Response data:', data); // Debug log

        if (!response.ok) {
            throw new Error(data.message || 'Failed to create category');
        }

        if (data && data.message) {
            alert('Category created successfully!');
            closePopup();
            location.reload();
        } else {
            throw new Error('Invalid response format');
        }
    } catch (error) {
        console.error('Error creating category:', error);
        alert('Failed to create category: ' + error.message);
    }
}

// Create a new movie
async function createMovie() {
    const title = document.getElementById('movieTitle').value;
    const logline = document.getElementById('movieLogline').value;
    const image = document.getElementById('movieImage').value;
    const categories = document.getElementById('movieCategories').value
        .split(',')
        .map(id => id.trim())
        .filter(id => id);

    if (!title) {
        alert('Please enter a movie title');
        return;
    }

    try {
        const response = await fetch('/api/movies', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: title,
                logline: logline,
                image: image,
                categories: categories
            })
        });

        const data = await response.json();
        console.log('Response data:', data); // Debug log

        if (!response.ok) {
            throw new Error(data.message || 'Failed to create movie');
        }

        if (data && data.message) {
            alert('Movie created successfully!');
            closePopup();
            location.reload();
        } else {
            throw new Error('Invalid response format');
        }
    } catch (error) {
        console.error('Error creating movie:', error);
        alert('Failed to create movie: ' + error.message);
    }
}

// Show categories or movies based on selection
async function listShow() {
    const selected = document.getElementById('movieCategorySelect').value;
    const categoryList = document.getElementById('categoryList');
    const movieList = document.getElementById('movieList');
    
    if (selected === 'category') {
        categoryList.style.display = 'block';
        movieList.style.display = 'none';
        loadCategories();  // Fetch and display categories when 'category' is selected
    } else {
        categoryList.style.display = 'none';
        movieList.style.display = 'block';
        loadMovies();
        // Optionally, you can add logic to load movies here
    }
}

async function loadCategories() {
    try {
        const response = await fetch('/api/categories');
        const data = await response.json();
        
        if (!Array.isArray(data.message)) {
            throw new Error('Invalid response format');
        }

        const categoryList = document.getElementById('categoryList');
        categoryList.innerHTML = '';  // Clear previous categories

        data.message.forEach(category => {
            const categoryItem = document.createElement('li');
            categoryItem.innerHTML = `<strong>${category.name} (ID: ${category._id})</strong>`;
            categoryList.appendChild(categoryItem);
        });
    } catch (error) {
        console.error('Error loading categories:', error);
        alert('Failed to load categories');
    }
}
async function loadMovies() {
    try {
    
        const response = await fetch('/api/movies/search/ ');
        const data = await response.json();
        
        if (!Array.isArray(data.message)) {
            throw new Error('Invalid response format');
        }

        const movieList = document.getElementById('movieList');
        movieList.innerHTML = '';

        data.message.forEach(movie => {
            const movieItem = document.createElement('li');
            movieItem.innerHTML = `<strong>${movie.title} (ID: ${movie._id})</strong>`;
            movieList.appendChild(movieItem);
        });
    } catch (error) {
        console.error('Error loading movies:', error);
        alert('Failed to load movies');
    }
}
