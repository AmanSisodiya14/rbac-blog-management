import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import Login from './pages/Login';
import Register from './pages/Register';
import BlogList from './pages/BlogList';
import AdminBlogForm from './pages/AdminBlogForm';
import { AuthGuard } from './components/AuthGuard';

function App() {
  return (
    <BrowserRouter>
      <Toaster position="top-right" />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/"
          element={
            <AuthGuard>
              <BlogList />
            </AuthGuard>
          }
        />
        <Route
          path="/admin/blogs/new"
          element={
            <AuthGuard >
              <AdminBlogForm />
            </AuthGuard>
          }
        />
        <Route
          path="/admin/blogs/edit/:id"
          element={
            <AuthGuard >
              <AdminBlogForm />
           </AuthGuard>
          }
        />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;