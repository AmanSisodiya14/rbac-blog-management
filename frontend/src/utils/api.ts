import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Replace with your actual API URL
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export const auth = {
  login: async (username: string, password: string) => {
    const response = await api.post('/auth/login', { username, password });
    return response.data;
  },
  register: async (username: string, password: string, role:string) => {
    const response = await api.post('/auth/register', { username, password ,role});
    return response.data;
  },
  logout: async () => {
    const response = await api.post('/auth/logout');
    return response.data;
  },

};

export const blogs = {
  getAll: async () => {
    const response = await api.get('/blog');
    return response.data;
  },
  get: async (id: string) => {
    const response = await api.get(`/blog/${id}`);
    return response.data;
  },
  create: async (data: { title: string; content: string }) => {
    const response = await api.post('/blog', data);
    return response.data;
  },
  update: async (id: string, data: { title: string; content: string }) => {
    const response = await api.put(`/blog/${id}`, data);
    return response.data;
  },
  delete: async (id: string) => {
    await api.delete(`/blog/${id}`);
  },

};

export default api;