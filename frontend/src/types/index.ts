export interface User {
  id: string;
  email: string;
  role: 'admin' | 'user';
}

export interface Blog {
  id: string;
  title: string;
  content: string;
  author: Author;
  createdAt: string  ; // Allow null for createdAt if it's not always present
}

export interface Author {
  id: number;
  username: string;
  password: string;
  role: Role;
}

export interface Role {
  id: number;
  name: string;
}


export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterCredentials extends LoginCredentials {
  confirmPassword: string;
}