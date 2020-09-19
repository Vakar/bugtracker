import React, { useContext } from "react";
import { AppContext } from "../context/AppContext";
import Login from "./Login";
import WellCome from "./WellCome";

export default function Home() {
  const { context } = useContext(AppContext);
  return context.user.id === 0 ? <Login /> : <WellCome />;
}
