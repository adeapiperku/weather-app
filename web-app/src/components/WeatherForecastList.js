import React, { useState } from "react";
import {
  Card,
  CardContent,
  Grid,
  Typography,
  Box,
  CircularProgress,
  Button,
  useTheme,
} from "@mui/material";
import { WbSunny, Cloud, CloudOff, AcUnit } from "@mui/icons-material";

const WeatherForecastList = ({ forecasts, isLoading, error }) => {
  const [isCelsius, setIsCelsius] = useState(true);
  const theme = useTheme();

  const toggleTemperatureUnit = () => {
    setIsCelsius(!isCelsius);
  };

  const formatTemperature = (temp) => {
    return isCelsius
      ? `${temp.toFixed(1)}°C`
      : `${((temp * 9) / 5 + 32).toFixed(1)}°F`;
  };
  

  const getWeatherIcon = (condition) => {
    switch (condition.toLowerCase()) {
      case "clear":
        return <WbSunny sx={{ fontSize: 40, color: "#fbc02d" }} />;
      case "clouds":
        return <Cloud sx={{ fontSize: 40, color: "#90a4ae" }} />;
      case "snow":
        return <AcUnit sx={{ fontSize: 40, color: "#81d4fa" }} />;
      case "rain":
      case "drizzle":
        return <CloudOff sx={{ fontSize: 40, color: "#4fc3f7" }} />;
      default:
        return <Cloud sx={{ fontSize: 40, color: "#90a4ae" }} />;
    }
  };

  if (isLoading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" height="80vh">
        <CircularProgress />
      </Box>
    );
  }

  if (error) {
    return <Typography color="error">Error: {error.message}</Typography>;
  }

  return (
    <Box sx={{ padding: 3 }}>
      <Box textAlign="center" mb={3}>
        <Button
          variant="contained"
          onClick={toggleTemperatureUnit}
          sx={{ borderRadius: "999px", px: 4, py: 1 }}
        >
          {isCelsius ? "Switch to Fahrenheit" : "Switch to Celsius"}
        </Button>
      </Box>
      <Grid container spacing={4}>
        {forecasts.map((forecast) => (
          <Grid item xs={12} sm={6} md={4} key={forecast.id}>
            <Card
              sx={{
                borderRadius: 4,
                boxShadow: 5,
                background: "linear-gradient(to right, #e0f7fa, #f1f8e9)",
                transition: "transform 0.3s ease",
                "&:hover": {
                  transform: "scale(1.03)",
                },
              }}
            >
              <CardContent>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={1}>
                  <Typography variant="h6" fontWeight="bold">
                    {forecast.cityName}
                  </Typography>
                  {getWeatherIcon(forecast.condition)}
                </Box>

                <Typography variant="body2" color="textSecondary">
                  {new Date(forecast.timestamp).toLocaleDateString()}
                </Typography>

                <Typography
                  variant="h4"
                  color="primary"
                  sx={{ mt: 2, fontWeight: 600 }}
                >
                  {formatTemperature(forecast.temperature)}
                </Typography>

                <Typography variant="subtitle1" color="textPrimary" sx={{ mt: 1 }}>
                  {forecast.condition}
                </Typography>

                <Typography variant="body2" color="textSecondary" sx={{ mt: 0.5 }}>
                  {forecast.description.charAt(0).toUpperCase() + forecast.description.slice(1)}
                </Typography>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default WeatherForecastList;
