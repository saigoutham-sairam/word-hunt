# ForkCount - Word Counter (Fork/Join with Spring Boot)

## Project Goal
Build a RESTful API using Java Spring Boot that recursively scans a directory, counts the number of occurrences of a specific word in all `.txt` files, and performs the task in parallel using the **ForkJoin Framework**.

We use `RecursiveAction` with `ForkJoinPool` to break down the task into subtasks and process them concurrently — similar to multithreading but optimized for parallelism.

---

## How It Works
- Accepts a folder path and target word as inputs.
- Forks directory traversal into subtasks using ForkJoinPool.
- Searches `.txt` files and counts occurrences of the word.
- Aggregates results concurrently using an `AtomicInteger`.

---

## Sample API Request
**GET** `/wordcount?path=/Users/john/documents&word=java`

### Sample Response:
Total occurrences of 'java': 42

## Why ForkJoin Framework?
- Breaks big tasks (e.g., folders with subfolders) into **mini subtasks**.
- Each subtask runs in **parallel**, reusing threads efficiently.
- Works on **divide and conquer** model.
- Ideal for recursive problems like directory scanning, file parsing, etc.

### RecursiveAction vs. RecursiveTask
- **RecursiveAction**: No return value (used here).
- **RecursiveTask<V>**: Returns a result (`V` type).

---

## How is it Different from Classic Multithreading?
| Feature                  | ForkJoin Framework       | Traditional Multithreading     |
|--------------------------|--------------------------|---------------------------------|
| Thread Management        | Uses **worker-stealing** | You manage thread creation     |
| Task Model               | Divide-and-conquer       | Manual task division            |
| Optimized for Recursion  | ✅ Yes                    | ❌ Harder to manage             |
| Result Handling          | join(), fork() methods   | Future, Thread.join(), etc.     |
| Pool Type                | ForkJoinPool             | ExecutorService, ThreadPool     |

---

## Tools & Tech
- Java 17
- Spring Boot 3.x
- Spring Web
- ForkJoinPool (java.util.concurrent)
- Swagger UI (springdoc-openapi)

---

## Swagger UI
Once the app is running:
> http://localhost:8080/swagger-ui.html

Use the Swagger interface to test the `/wordcount` endpoint.

---

## Example Project Structure
- controller/WordCountController.java
- service/WordCountService.java
- task/WordCountTask.java
- ForkCountApplication.java

---

## Author
Developed by [Your Name], using Java's parallel programming power 💪.
