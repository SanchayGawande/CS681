package edu.umb.cs681.hw11;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Run implements Runnable {

    private AtomicBoolean done = new AtomicBoolean(false);

    public void setDone() {
        done.set(true);
    }

    @Override
    public void run() {
        while (!done.get()) {
            FileSystem fileSystem = testFixture.createFs();
            System.out.println("Thread #" + Thread.currentThread().getName() + " started.");
            Directory root = fileSystem.getRootDirectories().get(0);
            displayDirectoryDetails(root);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread #" + Thread.currentThread().getName() + " interrupted.");
                continue; // If interrupted, continue checking the done flag
            }
        }
        System.out.println("Thread #" + Thread.currentThread().getName() + " stopping as done flag is set.");
    }

    private void displayDirectoryDetails(Directory dir) {
        List<FSElement> children = dir.getChildren();
        System.out.println("Thread #" + Thread.currentThread().getName() + ": Details in " + dir.getName());
        for (FSElement child : children) {
            String type = child.isDirectory() ? "Directory" : (child.isFile() ? "File" : "Link");
            System.out.println("Thread #" + Thread.currentThread().getName() +" - " + child.getName() + " (Type: " + type + ", Size: " + child.getSize() + ")");
        }
        System.out.println("Thread #" + Thread.currentThread().getName() +"Total size of " + dir.getName() + ": " + dir.getTotalSize());
       return;
    }

    public static void main(String[] args) {
        int threads_count=12;
        LinkedList<Run> List_run = new LinkedList<Run>();
        IntStream.rangeClosed(0, threads_count)
                .mapToObj(i -> new Run())
                .forEach(List_run::add);

        LinkedList<Thread> threadLinkedList = IntStream.rangeClosed(0, threads_count)
                .mapToObj(i -> new Thread(List_run.get(i)))
                .collect(Collectors.toCollection(LinkedList::new));

        // Start threads
        threadLinkedList.forEach(Thread::start);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List_run.forEach(Run::setDone);
        threadLinkedList.forEach(Thread::interrupt);
        for (Thread t : threadLinkedList) {
            try {
                t.join();
            } catch (InterruptedException exception) {
                System.out.println("join failed: " + exception.getMessage());
            }
        }
    }
}
