import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import IncidentList from './components/IncidentList';
import IncidentForm from './components/IncidentForm';

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-gray-100">
        <Navbar />
        <main className="container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<IncidentList />} />
            <Route path="/create" element={<IncidentForm />} />
            <Route path="/edit/:id" element={<IncidentForm />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;