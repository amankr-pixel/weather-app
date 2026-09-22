import { useState, useEffect } from "react";
import { getWeatherByCity,
    getSearchHistory,
     addFavorite,
     getFavorites,
     deleteFavorite,
      clearSearchHistory,
       getForecastByCity} from "./services/weatherService";
import WeatherCard from "./components/WeatherCard";
import Forecast from "./components/Forecast";
import Auth from "./components/Auth";

function App() {
  const [city, setCity] = useState("");
  const [weather, setWeather] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);
  const [history, setHistory] = useState([]);
  const [favorites, setFavorites] = useState([]);
  const [forecast, setForecast] = useState([]);

  const [isLoggedIn, setIsLoggedIn] = useState(
    !!localStorage.getItem("token")
  );

  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");

    setIsLoggedIn(false);
  };

  useEffect(() => {

    if (!isLoggedIn) {
      return;
    }

    loadHistory();
    loadFavorites();

  }, [isLoggedIn]);

  const loadHistory = async () => {
      try{
          const data = await getSearchHistory();
          setHistory(data);
      }catch(error){
          console.error("Failed to load history:", error);
      }
  };

  const loadFavorites = async () => {
        try{
            const data = await getFavorites();
            setFavorites(data);
        }catch(error){
            console.error("Failed to load favorites:", error);
        }
  };

  const handleSearch = async (cityName = city) => {

    if (cityName.trim() === "") {
      setError("Please enter a city name!");
      return;
    }

    try {
      setError("");
      setLoading(true);

      const weatherData = await getWeatherByCity(cityName);

      setWeather(weatherData);

      const forecastData = await getForecastByCity(cityName);

      setForecast(forecastData);

      await loadHistory();

    } catch (error) {

      console.error("Failed to fetch weather:", error);

      setError("City not found or something went wrong!");

      setWeather(null);
      setForecast([]);

    } finally {

      setLoading(false);

    }
  };

  const handleAddFavorite = async (cityName) => {
        try{
            await addFavorite(cityName);

            await loadFavorites();

            alert('${cityName} added to favorites ❤️');

        }catch(error){

            if(error.response){
                alert(error.response.data);
            }else{
                alert("Failed to add favorite");
            }
            console.error("Failed to add favorite:", error);
        }
  };

  const handleDeleteFavorite = async (id) => {
        try{
            await deleteFavorite(id);

            await loadFavorites();
        }catch(error) {
            console.error("Failed to delete favorite:", error);
        }
  };

const handleClearHistory = async () => {
    try{
        await clearSearchHistory();

        setHistory([]);

    }catch(error){
        console.error("Failed to clear history:", error);

        alert("Failed to clear search history");
    }
}
if (!isLoggedIn) {
      return <Auth onLogin={handleLogin} />;
    }
  return (



    <div className="app">

      <div className="weather-container">

        <div className="user-bar">
          <span>
            👋 Welcome, {localStorage.getItem("username")}
          </span>

          <button onClick={handleLogout}>
            Logout
          </button>
        </div>

        <h1>🌤️ Weather App</h1>
        <p className="subtitle">
          Search for the current weather in any city
        </p>

        <div className="search-container">
          <input
            type="text"
            placeholder="Enter city name"
            value={city}
            onChange={(e) => setCity(e.target.value)}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                handleSearch();
              }
            }}
          />

          <button onClick={handleSearch} disabled={loading}>
              {loading ? "Searching..." : "Search"}
          </button>
        </div>

        {error && <p className="error">{error}</p>}

        {loading && <p className="loading">Loading weather...</p>}

        {weather && <WeatherCard weather={weather}
                        onAddFavorite={handleAddFavorite}/>}

        {forecast.length > 0 && (
          <Forecast forecast={forecast} />
        )}

        {favorites.length > 0 && (
          <div className="favorites">

            <h3>❤️ Favorite Cities</h3>

            {favorites.map((item) => (
              <div key={item.id} className="favorite-item">

                <span
                  className="favorite-city"
                  onClick={() => {
                    setCity(item.city);
                    handleSearch(item.city);
                  }}
                >
                  {item.city}
                </span>

                <button
                  className="delete-button"
                  onClick={() => handleDeleteFavorite(item.id)}
                >
                  ❌
                </button>

              </div>
            ))}

          </div>
        )}


        {history.length > 0 && (
            <div className="history">
                <div className="history-header">
                      <h3>Recent Searches</h3>

                      <button
                        className="clear-history-button"
                        onClick={handleClearHistory}
                      >
                        🗑️ Clear
                      </button>
                    </div>


                {history.map((item) => (
                    <div key={item.id} className="history-item"
                        onClick={() => {
                            setCity(item.city);
                            handleSearch(item.city);
                        }}>
                        {item.city}
                    </div>
                ))}
            </div>
        )}
      </div>
    </div>
  );
}

export default App;