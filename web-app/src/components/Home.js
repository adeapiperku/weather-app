import React, { useContext, useState } from "react";
import { Container, Typography, Box, Paper, Button, Alert } from "@mui/material";
import WeatherForecastList from "./WeatherForecastList";
import { fetchCities, fetchWeatherData } from "../api/OpenWeatherService";
import Search from "./Search";
import useUser from "../hooks/useUser";
import { FavoritesContext } from "../context/FavoritesContext";
import { FavoritesService } from "../service/FavoritesService";

export default function Home() {
  const [forecasts, setForecasts] = useState([]);
  const [error, setError] = useState(null);
  const { user } = useUser();
  const { favorites, toggleFavorite } = useContext(FavoritesContext);
  const favoritesService = new FavoritesService();

  const handleSearch = async (cityInput) => {
    try {
      const citiesData = await fetchCities(cityInput);

      if (citiesData.data.length === 0) {
        alert("No city found!");
        return;
      }

      const firstCity = citiesData.data[0];
      const { latitude, longitude, name, id: cityId } = firstCity;

      const [currentWeather, forecastWeather] = await fetchWeatherData(latitude, longitude);

      const forecastsGroupedByDay = forecastWeather.list.reduce((acc, entry) => {
        const date = entry.dt_txt.split(" ")[0];
        if (!acc[date]) acc[date] = [];
        acc[date].push(entry);
        return acc;
      }, {});

      const processedForecasts = Object.entries(forecastsGroupedByDay).map(([date, entries], index) => {
        const midEntry = entries[Math.floor(entries.length / 2)];
        return {
          id: cityId, 
          cityName: name,
          timestamp: date,
          temperature: midEntry.main.temp,
          condition: midEntry.weather[0].main,
          description: midEntry.weather[0].description,
        };
      });

      setForecasts(processedForecasts);
      setError(null);
    } catch (error) {
      console.error("Error fetching weather:", error);
      setError("Failed to fetch weather data. Please try again later.");
    }
  };

  const handleAddFavorite = async () => {
    try {
      const city = forecasts[0];
      const customerId = user?.user?.id;

      if (!customerId || !city?.id) {
        alert("Missing customer or city information.");
        return;
      }

      const isAlreadyFavorite = favorites.some((fav) => fav.cityName === city.cityName);

      if (isAlreadyFavorite) {
      
        await favoritesService.removeFavorite(customerId, city);
      } else {
    
        await favoritesService.addFavorite(customerId, city);
      }

      toggleFavorite(city);
    } catch (error) {
      console.error("Error updating favorites:", error);
      alert("Failed to update favorites.");
    }
  };

  return (
    <Container maxWidth="md">
      <Paper elevation={4} sx={{ padding: 4, marginTop: 6, borderRadius: 3 }}>
        <Typography variant="h4" align="center" gutterBottom>
          🌤️ Weather Forecast
        </Typography>
        <Typography variant="subtitle1" align="center" gutterBottom>
          Enter a city name to view weather conditions for the upcoming days.
        </Typography>

        <Box mt={4} mb={4}>
          <Search onSearch={handleSearch} />
        </Box>

        {error && <Alert severity="error">{error}</Alert>}

        {forecasts.length > 0 && (
          <>
            <WeatherForecastList forecasts={forecasts} />
            <Box mt={2} display="flex" justifyContent="center">
              <Button
                variant="contained"
                color="primary"
                onClick={handleAddFavorite}
              >
                {favorites.some((fav) => fav.cityName === forecasts[0].cityName)
                  ? "Remove from Favorites"
                  : "Add to Favorites"}
              </Button>
            </Box>
          </>
        )}
      </Paper>
    </Container>
  );
}
