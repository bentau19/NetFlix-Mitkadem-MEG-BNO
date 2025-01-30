import React from 'react';
import GuestMovies from './GuestMovies';

const GuestScreenTemp = ({ isLoading, error, data }) => {
  return (
    <div className="guestScreenContainer">
      {/* Display loading state */}
      {isLoading && <p>Loading...</p>}

      {/* Display error message */}
      {error && <p className="errorMessage">{error}</p>}

      {/* Render movies if data is available */}
      {!isLoading && !error && Array.isArray(data) && data.length > 0 ? (
        <GuestMovies movies={data} />
      ) : (
        <p>No movies available.</p>
      )}
    </div>
  );
};

export default GuestScreenTemp;
