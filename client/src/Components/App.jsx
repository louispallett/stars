import { Outlet } from "react-router-dom";

const apiUrl = import.meta.env.VITE_API_URL; // Use this to define your API call

export default function App() {
    return (
        <div>
            <h1>Stars</h1>
            <Outlet />
        </div>
    )
}