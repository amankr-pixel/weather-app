function Forecast({ forecast }) {

  if (!forecast || forecast.length === 0) {
    return null;
  }

  return (
    <div className="forecast">

      <h3>📅 5-Day Forecast</h3>

      <div className="forecast-container">

        {forecast.map((item) => (
          <div
            className="forecast-card"
            key={item.date}
          >

            <p className="forecast-date">
              {new Date(item.date).toLocaleDateString(
                "en-US",
                {
                  weekday: "short",
                  month: "short",
                  day: "numeric"
                }
              )}
            </p>

            <img
              src={`https://openweathermap.org/img/wn/${item.icon}@2x.png`}
              alt={item.description}
            />

            <h2>
              {Math.round(item.temperature)}°C
            </h2>

            <p>
              {item.description}
            </p>

          </div>
        ))}

      </div>

    </div>
  );
}

export default Forecast;