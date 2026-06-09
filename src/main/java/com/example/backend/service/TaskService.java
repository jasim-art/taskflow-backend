package com.example.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.entity.Task;
import com.example.backend.entity.User;
import com.example.backend.repository.TaskRepository;
import com.example.backend.repository.UserRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> getTasksByUser(
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        return taskRepository
                .findByUser(user);
    }

    public Task createTask(
            Task task,
            String email) {

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        task.setUser(user);

        return taskRepository.save(task);
    }

    public Task updateTask(
            Long id,
            Task updatedTask) {

        Task task =
                taskRepository.findById(id)
                        .orElseThrow();

        task.setTitle(
                updatedTask.getTitle());

        task.setDescription(
                updatedTask.getDescription());

        task.setStatus(
                updatedTask.getStatus());

        task.setPriority(
                updatedTask.getPriority());

        task.setDueDate(
                updatedTask.getDueDate());

        return taskRepository.save(task);
    }

    public void deleteTask(
            Long id) {

        taskRepository.deleteById(id);
    }
}