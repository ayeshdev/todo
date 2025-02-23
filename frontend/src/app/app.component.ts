import {
  TuiRoot,
  TuiButton,
  TuiNotification,
} from '@taiga-ui/core';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskController } from './controller/task.controller';
import { NgFor, NgIf } from '@angular/common';
import { TuiInputModule, TuiTextareaModule } from '@taiga-ui/legacy';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
    TuiRoot,
    TuiInputModule,
    TuiButton,
    TuiNotification,
    TuiTextareaModule,
    ReactiveFormsModule,
    NgFor,
    NgIf,
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

  title = 'todo';
  todoForm: FormGroup;
  taskLimit = 5; // Set initial task limit to 5

  constructor(private fb: FormBuilder, public taskController: TaskController) {
    this.todoForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.taskController.loadTasks(); // Load tasks initially
  }

  addTask() {
    if (this.todoForm.invalid) return;

    const { title, description } = this.todoForm.value;
    this.taskController.addTask(title, description);
    this.todoForm.reset(); // Clear form after adding task
  }

  // Load more tasks when button is clicked
  loadMore() {
    this.taskLimit += 2; // Increase task limit (show 7 tasks, then 9, etc.)
  }
}
