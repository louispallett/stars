import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function Star() {
    const [data, setData] = useState(null);
    const {id} = useParams();

    useEffect(() => {
        const getData = async() => {
            axios.get(`/api/star/get/${id}`, {})
            .then((response) => {
                console.log(data);
                setData(response.data);
            }).catch((err) => {
                console.log(err.message);
            }).finally(() => {});
        }
        getData();
    }, []);

    return (
        <>
            { data ? (
                <div className="table-wrapper">
                    <table>
                        <thead>
                            <th scope="col" colSpan="2">Key Information</th>
                        </thead>
                        <thead>
                            <th scope="col">Key</th>
                            <th scope="col">Value</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Name</th>
                                <td>{data.name}</td>
                            </tr>
                            <tr>
                                <th scope="row">Type</th>
                                <td>{data.starType}</td>
                            </tr>
                            <tr>
                                <th scope="row">Type</th>
                                <td>{data.spectralType}</td>
                            </tr>
                            <tr>
                                <th scope="row">Date Created</th>
                                <td>{data.dateCreated}</td>
                            </tr>
                        </tbody>
                    </table>
                    <table>
                        <thead>
                            <th scope="col" colSpan="2">Luminosity</th>
                        </thead>
                        <thead>
                            <th scope="col">Key</th>
                            <th scope="col">Value</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Relative (L☉)</th>
                                <td>{parseFloat(data.relativeLuminosity.toFixed(4))}</td>
                            </tr>
                            <tr>
                                <th scope="row">Absolute</th>
                                <td>{parseFloat(data.absoluteLuminosity.toFixed(4))} <i>W</i></td>
                            </tr>
                            <tr>
                                <th scope="row">Surface Temperature</th>
                                <td>{parseFloat(data.surfaceTemperature.toFixed(4))} <i>°K</i></td>
                            </tr>
                        </tbody>
                    </table>
                    <table>
                        <thead>
                            <th scope="col" colSpan="2">Dimensions</th>
                        </thead>
                        <thead>
                            <th scope="col">Key</th>
                            <th scope="col">Value</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Solar Radius (r☉)</th>
                                <td>{parseFloat(data.solarRadius.toFixed(4))}</td>
                            </tr>
                            <tr>
                                <th scope="row">Diameter</th>
                                <td>{parseFloat(data.diameter.toFixed(4)) / 1000} <i>km</i></td>
                            </tr>
                            <tr>
                                <th scope="row">Circumference</th>
                                <td>{parseFloat((data.circumference / 1000).toFixed(4))} <i>km</i></td>
                            </tr>
                            <tr>
                                <th scope="row">Surface Area</th>
                                <td>{parseFloat((data.surfaceArea / 1000).toFixed(4))} <i>km²</i></td>
                            </tr>
                        </tbody>
                    </table>
                    <table>
                        <thead>
                            <th scope="col" colSpan="2">Mass</th>
                        </thead>
                        <thead>
                            <th scope="col">Key</th>
                            <th scope="col">Value</th>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">Solar Mass (M☉)</th>
                                <td>{parseFloat(data.solarMass.toFixed(4))}</td>
                            </tr>
                            <tr>
                                <th scope="row">Absolute Mass</th>
                                <td>{parseFloat(data.massKg.toFixed(4))} <i>kg</i></td>
                            </tr>
                            <tr>
                                <th scope="row">Mean Density</th>
                                <td>{parseFloat(data.meanDensity.toFixed(4))} <i>kg/m³</i></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            ) : (
                <p>Awaiting data</p>
            )}
        </>
    )
}