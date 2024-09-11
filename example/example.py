import threading
import random
import time
import math
from queue import Queue

# Function to generate random numbers and return their square roots recursively
def recursive_sqrt(numbers, index=0):
    if index >= len(numbers):
        return []
    # Square root calculation with error handling for negative numbers
    try:
        sqrt_value = math.sqrt(numbers[index])
    except ValueError:
        sqrt_value = float('nan')  # If the number is negative, return NaN
    return [sqrt_value] + recursive_sqrt(numbers, index + 1)

# Worker class to perform multithreading tasks
class TaskWorker(threading.Thread):
    def __init__(self, task_queue, results):
        threading.Thread.__init__(self)
        self.task_queue = task_queue
        self.results = results

    def run(self):
        while not self.task_queue.empty():
            task = self.task_queue.get()
            result = task.execute()  # Execute the task
            self.results.append(result)
            self.task_queue.task_done()

# Task class to represent individual tasks
class Task:
    def __init__(self, data):
        self.data = data

    def execute(self):
        # Simulate a complex task by performing recursive calculations
        print(f"Processing data: {self.data}")
        time.sleep(random.uniform(0.5, 2))  # Simulate processing time
        sqrt_result = recursive_sqrt(self.data)
        return sqrt_result

# Main manager class to create tasks and manage workers
class TaskManager:
    def __init__(self, num_workers):
        self.num_workers = num_workers
        self.task_queue = Queue()
        self.results = []

    def add_task(self, task):
        self.task_queue.put(task)

    def run(self):
        workers = []
        # Create workers (threads)
        for i in range(self.num_workers):
            worker = TaskWorker(self.task_queue, self.results)
            workers.append(worker)
            worker.start()

        # Wait for all tasks to complete
        self.task_queue.join()

        # Ensure all threads have finished
        for worker in workers:
            worker.join()

# Function to generate random data for tasks
def generate_random_data(size, data_length):
    return [[random.randint(-100, 100) for _ in range(data_length)] for _ in range(size)]

# Main execution
if __name__ == "__main__":
    num_workers = 4
    task_manager = TaskManager(num_workers)

    # Generate random data and create tasks
    random_data = generate_random_data(10, 20)
    for data in random_data:
        task = Task(data)
        task_manager.add_task(task)

    # Run the task manager
    start_time = time.time()
    task_manager.run()
    end_time = time.time()

    # Print the results
    for i, result in enumerate(task_manager.results):
        print(f"Result {i + 1}: {result}")

    print(f"Completed in {end_time - start_time:.2f} seconds.")
