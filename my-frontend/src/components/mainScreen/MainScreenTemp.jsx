import React from 'react';
import testImage from './test.png'; // Adjust path as needed
import QueryCardView from './queryCardView';
import BigMovie from './BigMovie';
import CategoriesAndMoviesDisplay from './categoriesAndMoviesDisplay';

const MainScreenTemp = ({ isLoading, error, data, searchQuery }) => {
  return (
    <div className="mainContainer">
      {/* Display loading state */}
      {isLoading && <p>Loading...</p>}

      {/* Display error message */}
      {error && <p>{error}</p>}

      {/* Render movie categories or list of movies based on searchQuery */}
      {!isLoading && !error && Array.isArray(data) && data.length > 0 ? (
        searchQuery === '' ? (
          // Render categories when no search query
                <div>
            <BigMovie/>
            <CategoriesAndMoviesDisplay data={data}/>
          </div>
        ) : (
          <QueryCardView isLoading={isLoading} error={error} data={data} searchQuery={searchQuery} />
        )
      ) : (
        <p>No categories or movies to display.</p> // If data is empty or not an array
      )}
    </div>
  );
};

export default MainScreenTemp;
