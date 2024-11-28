import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const IncidentList = () => {
    const [incidents, setIncidents] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchIncidents();
    }, []);

    const fetchIncidents = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/incidents');
            setIncidents(response.data);
            setLoading(false);
        } catch (err) {
            setError('Error fetching incidents');
            setLoading(false);
        }
    };

    const handleDelete = async (id) => {
        if (window.confirm('Are you sure you want to delete this incident?')) {
            try {
                await axios.delete(`http://localhost:8080/api/incidents/${id}`);
                setIncidents(incidents.filter(incident => incident.id !== id));
            } catch (err) {
                setError('Error deleting incident');
            }
        }
    };

    if (loading) return <div>Loading...</div>;
    if (error) return <div className="text-red-500">{error}</div>;

    return (
        <div className="container mx-auto px-4">
            <div className="flex justify-between items-center mb-6">
                <h2 className="text-2xl font-bold">Incident List</h2>
                <Link
                    to="/create"
                    className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
                >
                    Create New Incident
                </Link>
            </div>

            <div className="overflow-x-auto">
                <table className="min-w-full bg-white border border-gray-300">
                    <thead>
                        <tr className="bg-gray-100">
                            <th className="px-6 py-3 border-b text-left">Title</th>
                            <th className="px-6 py-3 border-b text-left">Description</th>
                            <th className="px-6 py-3 border-b text-left">Status</th>
                            <th className="px-6 py-3 border-b text-left">Priority</th>
                            <th className="px-6 py-3 border-b text-left">Created At</th>
                            <th className="px-6 py-3 border-b text-left">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {incidents.length === 0 ? (
                            <tr>
                                <td colSpan="6" className="px-6 py-4 text-center">
                                    No incidents found
                                </td>
                            </tr>
                        ) : (
                            incidents.map(incident => (
                                <tr key={incident.id} className="hover:bg-gray-50">
                                    <td className="px-6 py-4 border-b">
                                        {incident.title}
                                    </td>
                                    <td className="px-6 py-4 border-b">
                                        {incident.description}
                                    </td>
                                    <td className="px-6 py-4 border-b">
                                        <span className={`px-2 py-1 rounded ${  
                                            incident.status === 'OPEN' ? 'bg-green-100 text-green-800' :  
                                            incident.status === 'IN_PROGRESS' ? 'bg-yellow-100 text-yellow-800' :  
                                            'bg-red-100 text-red-800'  
                                        }`}>
                                            {incident.status}
                                        </span>
                                    </td>
                                    <td className="px-6 py-4 border-b">
                                        {incident.priority}
                                    </td>
                                    <td className="px-6 py-4 border-b">
                                        {new Date(incident.createdAt).toLocaleString()}
                                    </td>
                                    <td className="px-6 py-4 border-b">
                                        <div className="flex space-x-2">
                                            <Link
                                                to={`/edit/${incident.id}`}
                                                className="text-blue-500 hover:text-blue-700"
                                            >
                                                Edit
                                            </Link>
                                            <button
                                                onClick={() => handleDelete(incident.id)}
                                                className="text-red-500 hover:text-red-700"
                                            >
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default IncidentList;