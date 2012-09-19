package org.javatroopers.concurrency.forknjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DirectorySearch extends RecursiveTask<File> {
	private static final long serialVersionUID = 1L;
	private File directory;

	public DirectorySearch(File my_directory) {
		directory = my_directory;
	}

	@Override
	protected File compute() {
		if (!directory.isDirectory() && !directory.canRead()) {
			return null;
		}

		System.out.println("[" + Thread.currentThread().getId() + "] Visiting: " + directory);
		List<DirectorySearch> forkedTasks = new ArrayList<DirectorySearch>();
		File ret = null;
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				DirectorySearch subtask = new DirectorySearch(file);
				forkedTasks.add(subtask);
				subtask.fork();
			} else if (file.isFile() && (ret == null || file.length() > ret.length())) {
				ret = file;
			}
		}

		for (DirectorySearch subtask : forkedTasks) {
			File file = subtask.join();
			if (ret == null || (file != null && file.length() > ret.length())) {
				ret = file;
			}
		}

		return ret;
	}

	public static void main(String[] args) {
		ForkJoinPool pool = new ForkJoinPool();
		File directory = new File(args[0]);
		DirectorySearch task = new DirectorySearch(directory);
		File result = pool.invoke(task);
		System.out.println("Largest file found: " + result);
	}
}
