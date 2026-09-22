function WeatherCard({ weather, onAddFavorite }) {

  const getWeatherIcon = (description) => {

    const weatherDescription = description.toLowerCase();

    if (weatherDescription.includes("clear")) {
      return "☀️";
    }

    if (weatherDescription.includes("cloud")) {
      return "☁️";
    }

    if (weatherDescription.includes("rain")) {
      return "🌧️";
    }

    if (weatherDescription.includes("thunderstorm")) {
      return "⛈️";
    }

    if (weatherDescription.includes("snow")) {
      return "❄️";
    }

    if (
      weatherDescription.includes("mist") ||
      weatherDescription.includes("fog") ||
      weatherDescription.includes("haze")
    ) {
      return "🌫️";
    }

    return "🌤️";
  };

  const icon = getWeatherIcon(weather.description);

  return (
    <div className="weather-card">

      <h2>{weather.city}</h2>

      <div className="weather-icon">
        {icon}
      </div>

      <div className="temperature">
        {weather.temperature}°C
      </div>

      <p className="description">
        {weather.description}
      </p>

      <div className="weather-details">

        <div className="detail">
          <span>🤔</span>
          <p>Feels Like</p>
          <strong>{weather.feelsLike}°C</strong>
        </div>

        <button
          className="favorite-button"
          onClick={() => onAddFavorite(weather.city)}
        >
          ❤️ Add to Favorites
        </button>

        <div className="detail">
          <span>💧</span>
          <p>Humidity</p>
          <strong>{weather.humidity}%</strong>
        </div>

        <div className="detail">
          <span>💨</span>
          <p>Wind Speed</p>
          <strong>{weather.windSpeed} m/s</strong>
        </div>

      </div>

    </div>
  );
}

export default WeatherCard;