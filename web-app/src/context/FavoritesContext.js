import React, { createContext, useState, useEffect } from 'react';


export const FavoritesContext = createContext();

export const FavoritesProvider = ({ children }) => {
  const [favorites, setFavorites] = useState([]);

  useEffect(() => {
    const savedFavorites = JSON.parse(localStorage.getItem('favorites')) || [];
    setFavorites(savedFavorites);
  }, []);

  const toggleFavorite = (city) => {
    let updatedFavorites;
    const isFavorite = favorites.some(fav => fav.id === city.id);

    if (isFavorite) {
      updatedFavorites = favorites.filter(fav => fav.id !== city.id);
    } else {
      updatedFavorites = [...favorites, city];
    }

    setFavorites(updatedFavorites);
    localStorage.setItem('favorites', JSON.stringify(updatedFavorites)); 
  };

  return (
    <FavoritesContext.Provider value={{ favorites, toggleFavorite }}>
      {children}
    </FavoritesContext.Provider>
  );
};
