export interface Task {
  id?: number; // Optional because new tasks don’t have an ID until saved
  title: string;
  description: string;
  completed: boolean;
  createdAt?: string; // Date as a string from the backend
}
