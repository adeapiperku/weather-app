import React, { forwardRef } from "react";
import { NavLink, useLocation } from "react-router-dom";
import { ListItem } from "@material-ui/core";
import Divider from "@material-ui/core/Divider";

export default function AppMenuItemComponent({
  onClick,
  link,
  divider,
  children,
}) {
  const location = useLocation();

  if (divider) {
    return <Divider />;
  }
  
  if (!link) {
    return (
      <ListItem
        button
        selected={location.pathname === link}
        children={children}
        onClick={onClick}
      />
    );
  }


  return (
    <ListItem
      button
      selected={location.pathname === link}
      children={children}
      component={forwardRef((props, ref) => (
        <NavLink {...props} ref={ref} />
      ))}
      to={link}
      onClick={onClick}
    />
  );
}
