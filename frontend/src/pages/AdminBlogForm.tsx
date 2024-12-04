import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { blogs } from '../utils/api';
import toast from 'react-hot-toast';

export default function AdminBlogForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [blog, setBlog] = useState({ title: '', content: '' });

  useEffect(() => {
    if (id) {
      const fetchBlog = async () => {
        try {
          const existingBlog = await blogs.get(id);
          console.log(existingBlog , "existingBlog");
          if (existingBlog) {
            
            setBlog({ title: existingBlog.title, content: existingBlog.content });
          }
        } catch (error:any) {
          if (error.response?.status === 403) {
            toast.error("You don't have permission to fetch this blog post");
          } else {
            toast.error('Failed to fetch blog post');
          }
          console.error('Failed to fetch blog:', error);
          navigate('/');
        }
      };
      fetchBlog();
    }
  }, [id]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      if (id) {
        await blogs.update(id, blog);
        toast.success('Blog updated successfully!');
      } else {
        await blogs.create(blog);
        toast.success('Blog created successfully!');
      }
      navigate('/');
    } catch (error: any) {
      if (error.response?.status === 403) {
        toast.error("You don't have permission to save blog post");
      } else {
        toast.error('Failed to save blog post');
      }
      // toast.error('Failed to save blog');
      navigate('/');
    }
  };

  return (
    <div className="max-w-4xl mx-auto py-8 px-4">
      <h1 className="text-3xl font-bold mb-8">{id ? 'Edit' : 'Create'} Blog Post</h1>
      <form onSubmit={handleSubmit} className="space-y-6">
        <div>
          <label className="block text-sm font-medium text-gray-700">Title</label>
          <input
            type="text"
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200"
            value={blog.title}
            onChange={(e) => setBlog({ ...blog, title: e.target.value })}
            required
          />
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700">Content</label>
          <textarea
            className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200"
            rows={10}
            value={blog.content}
            onChange={(e) => setBlog({ ...blog, content: e.target.value })}
            required
          />
        </div>
        <div className="flex gap-4">
          <button
            type="submit"
            className="bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
          >
            {id ? 'Update' : 'Create'} Post
          </button>
          <button
            type="button"
            onClick={() => navigate('/')}
            className="bg-gray-200 text-gray-700 py-2 px-4 rounded-md hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500 focus:ring-offset-2"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  );
}