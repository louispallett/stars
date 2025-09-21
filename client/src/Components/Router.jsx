import { createBrowserRouter, RouterProvider } from "react-router-dom";

import App from "./App";
import Star from "./Star";


export default function Router() {
    const router = createBrowserRouter([
        {
            path: "/",
            element: <App />,
            children: [
                {
                    path: "star/:id",
                    element: <Star />
                }
            ]
        }
    ]);

    return <RouterProvider router={router} />;
}