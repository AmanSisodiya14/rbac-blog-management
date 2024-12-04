import { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';

interface AuthGuardProps {
  children: ReactNode;
  
}

export function AuthGuard({ children   }: AuthGuardProps) {
  const { user, token } = useAuthStore();

  if (!token || !user) {
    return <Navigate to="/login" replace />;
  }

  // if (requireAdmin && user.role !== 'admin') {
  //   return <Navigate to="/" replace />;
  // }

  return <>{children}</>;
}