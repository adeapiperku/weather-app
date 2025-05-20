import React, { useState } from "react";
import { TextField, Button, Stack } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

const Search = ({ onSearch }) => {
  const [cityInput, setCityInput] = useState("");

  const handleSearchChange = (e) => {
    setCityInput(e.target.value);
  };

  const handleSearchClick = () => {
    if (cityInput.trim()) {
      onSearch(cityInput);
    }
  };

  return (
    <Stack direction={{ xs: "column", sm: "row" }} spacing={2} alignItems="center">
      <TextField
        label="Enter City"
        value={cityInput}
        onChange={handleSearchChange}
        variant="outlined"
        fullWidth
      />
      <Button
        variant="contained"
        color="primary"
        onClick={handleSearchClick}
        size="large"
        endIcon={<SearchIcon />}
      >
        Search
      </Button>
    </Stack>
  );
};

export default Search;
