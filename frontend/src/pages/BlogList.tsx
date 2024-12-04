import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PlusCircle, LogOut } from 'lucide-react';
import { auth ,blogs} from '../utils/api';
import { Blog } from '../types';
import { useAuthStore } from '../store/authStore';
import { toast } from 'react-hot-toast';

export default function BlogList() {
  const [blogPosts, setBlogPosts] = useState<Blog[]>([]);
  const navigate = useNavigate();
  const user = useAuthStore((state) => state.user);
  const logout = useAuthStore((state) => state.logout);
console.log(user , "user");
  useEffect(() => {
    const fetchBlogs = async () => {
      try {
        const data = await blogs.getAll();
        setBlogPosts(data);
      } catch (error) {
        console.error('Failed to fetch blogs:', error);
      }
    };
    fetchBlogs();
  }, []);
console.log(blogPosts , "blogPosts");

  const handleDelete = async (blogId: string) => {
    try {
      await blogs.delete(blogId);
      setBlogPosts(blogPosts.filter((p) => p.id !== blogId));
      toast.success('Blog post deleted successfully');
    } catch (error: any) {
      if (error.response?.status === 403) {
        toast.error("You don't have permission to delete this blog post");
      } else {
        toast.error('Failed to delete blog post');
      }
      console.error('Failed to delete blog:', error);
    }
  };

  return (
    <div className="max-w-4xl mx-auto py-8 px-4">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">Blog Posts</h1>
        <div className="flex items-center gap-4">
          <button
            onClick={() => navigate('/admin/blogs/new')}
            className="flex items-center gap-2 bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700"
          >
            <PlusCircle className="w-5 h-5" />
            New Post
          </button>
          <button
            onClick={async () => {
              try {
                await auth.logout();
                logout();
                navigate('/login');
              } catch (error) {
                console.error('Failed to logout:', error);
                toast.error('Logout failed. Please try again.');
              }
            }}
            className="flex items-center gap-2 bg-gray-600 text-white px-4 py-2 rounded-md hover:bg-gray-700"
          >
            <LogOut className="w-5 h-5" />
            
            Logout
          </button>
        </div>
      </div>
      <div className="space-y-6">
        {blogPosts.map((blog, index) => (
          <div key={blog.id} className="bg-white p-6 rounded-lg shadow-md">
            <span className="text-lg font-semibold text-gray-500 mb-4 block">#{index + 1}</span>
            
            <div className="border border-gray-200 rounded-md p-4 mb-4">
              <div className="flex-1">
                <div className="text-sm text-gray-500 mb-1">Title:</div>
                <h2 className="text-2xl font-bold">{blog.title}</h2>
              </div>
            </div>

            <div className="border border-gray-200 rounded-md p-4 mb-4">
              <div className="text-sm text-gray-500 mb-1">Content:</div>
              <p className="text-gray-600">{blog.content}</p>
            </div>

            <div className="flex justify-between items-center text-sm text-gray-500">
              <span>By {blog.author.username}</span>
              <span>{new Date(blog.createdAt).toLocaleDateString()}</span>
            </div>

            {/* {user?.role === 'admin' && ( */}
              <div className="mt-4 flex gap-2">
                <button
                  onClick={() => navigate(`/admin/blogs/edit/${blog.id}`)}
                  className="border border-blue-600 text-blue-600 px-4 py-1 rounded hover:bg-blue-50"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(blog.id)}
                  className="border border-red-600 text-red-600 px-4 py-1 rounded hover:bg-red-50"
                >
                  Delete
                </button>
              </div>
            {/* )} */}
          </div>
        ))}
      </div>
    </div>
  );
}