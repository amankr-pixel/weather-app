import axios from 'axios';

const WEATHER_API_URL = "http://localhost:8080/api/weather";
const HISTORY_API_URL = "http://localhost:8080/api/history";
const FAVORITES_API_URL = "http://localhost:8080/api/favorites";
const FORECAST_API_URL = "http://localhost:8080/api/forecast";
const AUTH_API_URL = "http://localhost:8080/api/auth";

export const getWeatherByCity = async (city) => {
  const response = await axios.get(
    WEATHER_API_URL,
    {
      params: { city },
      headers: getAuthHeaders()
    }
  );

  return response.data;
};

export const getSearchHistory = async () => {
  const response = await axios.get(
    HISTORY_API_URL,
    {
      headers: getAuthHeaders()
    }
  );

  return response.data;
};

export const addFavorite = async (city) => {
    const response = await axios.post(FAVORITES_API_URL, {
        city
    },{headers: getAuthHeaders()});
    return response.data;
};

export const getFavorites = async () => {
    const response = await axios.get(FAVORITES_API_URL,
        {headers: getAuthHeaders()
    });

    return response.data;
};

export const deleteFavorite = async (id) => {
    await axios.delete(`${FAVORITES_API_URL}/${id}`, {
        headers: getAuthHeaders()});
};

export const clearSearchHistory = async () => {
  await axios.delete(
    HISTORY_API_URL,
    {
      headers: getAuthHeaders()
    }
  );
};

export const getForecastByCity = async (city) => {
    const response = await axios.get(FORECAST_API_URL, {
        params : {
            city : city
        }
    });
    return response.data;
};

export const registerUser = async (userData) => {
  const response = await axios.post(
    `${AUTH_API_URL}/register`,
    userData
  );

  return response.data;
};

export const loginUser = async (loginData) => {
  const response = await axios.post(
    `${AUTH_API_URL}/login`,
    loginData
  );

  return response.data;
};
const getAuthHeaders = () => {
  const token = localStorage.getItem("token");

  return {
    Authorization: `Bearer ${token}`
  };
};
