import testImage from './test.png'; // Adjust the path if needed

const QueryCardView = ({ isLoading, error, data, searchQuery }) => {
  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error.message || 'Something went wrong'}</div>;
  }

  if (!data || data.length === 0) {
    return <div>No results found for "{searchQuery}".</div>;
  }

  return (
    <div className="moviesGrid">
      {data.map((movie,index) => (
        <div key={index} className="movieCard">
          <img
            src={movie.image || testImage} // Fallback to testImage if movie.image is not available
            alt={movie.title || 'Movie Title'}
            className="movieImage"
          />
          <div className="movieInfo">
            <h3>{movie.title || 'Untitled Movie'}</h3>
          </div>
        </div>
      ))}
    </div>
  );
};

export default QueryCardView;
