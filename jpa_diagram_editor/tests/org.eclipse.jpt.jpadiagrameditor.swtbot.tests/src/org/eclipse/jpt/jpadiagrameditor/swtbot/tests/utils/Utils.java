/*******************************************************************************
 * Copyright (c) 2012, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.swtbot.tests.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Semaphore;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.jobs.Job;


/**
 * Utility for testing purposes
 * @author i057508
 *
 */
public class Utils {

	/**
	 * User space system variable key.
	 */
	private static Semaphore writeToConsolePermission = new Semaphore(1);
	static SimpleDateFormat sdf = new SimpleDateFormat(">> [HH:mm:ss.S] ");
	
	public final static String NEW_LINE = (System.getProperty("line.separator") != null  ? System.getProperty("line.separator") : "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	// used to decide whether to print Job Manager's state
	private static boolean DEBUG = false;

	// seconds
	private static final String DUMPER_JOB_NAME = "Dumper 1";
			
	private static final int SIDE_JOBS_COMPLETE_TIMEOUT = 30 * 1000; // 30


	/** A constant for java validation error markers*/
	
	/**
	 * the plug-in ID of this plug-in
	 */
	public static final String COMMON_TEST_PLUGIN_ID = "com.sap.core.tools.eclipse.server.test.common";

	/**
	 * Issues a message into the system console into a friendly format. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message no new line will be added.
	 * @param message the message to be issued
	 */
	public static void printFormatted(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print(sdf.format(new Date()) + message);
		writeToConsolePermission.release();
	}

	/**
	 * Issues a message into the system console. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message no new line will be added.
	 * @param message the message to be issued
	 */
	public static void print(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print(message);
		writeToConsolePermission.release();
	}

	/**
	 * Issues a message into the system console into a friendly format. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message a new line will be added.
	 * @param message the message to be issued
	 */
	public static void printlnFormatted(String message) {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(sdf.format(new Date()) + message);
		writeToConsolePermission.release();
	}

	/**
	 * Utility for printing into the system console
	 * @param message a message to be printed
	 * @param t an exception to be printed
	 */
	public static void printlnFormatted(String message, Throwable t) {
		StringBuilder buf = new StringBuilder();
		buf.append(message);

		if (t != null  && t.getStackTrace() != null) {
			buf.append(NEW_LINE);
			buf.append("Exception:").append(t.getMessage());
			buf.append(NEW_LINE);
			buf.append("Stacktrace:");
			buf.append(NEW_LINE);
			buf.append(traceToString(t.getStackTrace()));
		}

		printlnFormatted(buf.toString());
	}

	/**
	 * Issues a message into the system console. The format includes current time.
	 * The order is preserved, so if another request is issued in meantime, it will be processed consequently.
	 * At the end of the message a new line will be added.
	 * @param message the message to be issued
	 */
	public static void println(String message)  {
		try {
			writeToConsolePermission.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(message);
		writeToConsolePermission.release();
	}

	/**
	 * Indicates a start of a test into the system console into a use friendly format.
	 * @param testName the name of the test.
	 */
	public static void sayTestStarted(String testName) {

		Utils.println("\n----------------------------------------------------------------------------");
		Utils.printlnFormatted("Test [" +  testName + "] started.");
		Utils.println("----------------------------------------------------------------------------");
	}

	/**
	 * Indicates an end of a test into the system console into a use friendly format.
	 * @param testName the name of the test.
	 */
	public static void sayTestFinished(String testName) {

		Utils.println("\n----------------------------------------------------------------------------");
		Utils.printlnFormatted("Test [" +  testName + "] finished.");
		Utils.println("----------------------------------------------------------------------------");
	}

	/**
	 * Converts stacktrace into a string
	 * @param trace the stacktrace
	 * @return the converted stacktrace
	 */
	public static String traceToString(StackTraceElement[] trace) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < trace.length; i++) {
			builder.append(trace[i]).append("\n");
		}
		return builder.toString();
	}
	
	
	/**
	 * Wait all build and refresh jobs to complete by joining them.
	 */
	public static void joinBuildAndRerfreshJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		Job[] jobsArray;
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_REFRESH);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
		jobs.addAll(Arrays.asList(jobsArray));
		jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_REFRESH);
		jobs.addAll(Arrays.asList(jobsArray));
		Utils.printlnFormatted("Waiting for " + jobs.size() + " Jobs to finish...");
		for (int i = 0; i < jobs.size(); i++) {
			Job job = jobs.get(i);
			int jobState = job.getState();
			if ((jobState == Job.RUNNING) || (jobState == Job.WAITING)) {
				try {
					job.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Wait all build and refresh jobs to complete or until 30 seconds timeout
	 * pass.
	 */
	public static void waitBuildAndRerfreshJobs() {
		Utils.printlnFormatted("Entered into joinBuildAndRerfreshJobs.");
		ArrayList<Job> jobs = new ArrayList<Job>();
		Job[] jobsArray;
		int timeout = SIDE_JOBS_COMPLETE_TIMEOUT;

		while (timeout > 0) {
			jobs.clear();
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_BUILD);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_AUTO_REFRESH);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_BUILD);
			jobs.addAll(Arrays.asList(jobsArray));
			jobsArray = Job.getJobManager().find(ResourcesPlugin.FAMILY_MANUAL_REFRESH);
			jobs.addAll(Arrays.asList(jobsArray));
			if (jobs.size() == 0) {
				return;
			}
			Utils.printlnFormatted("Waiting for " + jobs.size() + " Jobs to finish...");
			for (Job job : jobsArray) {
				Utils.printlnFormatted("Waiting for job: " + job.getName() + ", which is " + jobStateToString(job));
			}
			timeout -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
	}
	
	private static String jobStateToString(Job job) {
		int state = job.getState();
		switch (state) {
			case Job.NONE:
				return "NONE";
			case Job.RUNNING:
				return "RUNNING";
			case Job.SLEEPING:
				return "SLEEPING";
			case Job.WAITING:
				return "SLEEPING";
		}
		return "UNKNOWN";
	}

	
	
	/**
	 * Waits a certain time for any non system job to complete and if there is
	 * still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @return flag
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs() throws InterruptedException {
		Utils.printlnFormatted("Entered into waitNonSystemJobs.");
		return waitNonSystemJobs(SIDE_JOBS_COMPLETE_TIMEOUT, true);
	}

	/**
	 * Waits a certain time for any non system job to complete and if there is still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @param timeout
	 *            time after which the jobs will be canceled.
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs(int timeout) throws InterruptedException {
		return waitNonSystemJobs(timeout, true);
	}

	/**
	 * Waits a certain time for any non system job to complete and if there is
	 * still jobs after the certain periord,<br/>
	 * removes all of them so the test will have a clean environment
	 * 
	 * @param timeout
	 *            time after which the jobs will be canceled.
	 * @param cancelWaitForBrowser
	 *            will cancel rightaway the wait for browser jobs
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean waitNonSystemJobs(int timeout, boolean cancelWaitForBrowser) throws InterruptedException {
		Job[] find = null;
		int count = 0;

		while (timeout > 0) {
			count = 0;
			find = Job.getJobManager().find(null);
			for (Job job : find) {
				if (!job.isSystem()) {
					count++;
					if (!DUMPER_JOB_NAME.equals(job.getName())) {
						Utils.printlnFormatted("Waiting for job: " + job.getName() + ", which is " + jobStateToString(job));
					}
				}
			}
			if (DEBUG && (count == 1)) {
				return true;
			} else if (!DEBUG && (count == 0)) {
				return true;
			}
			timeout -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

		}
		Utils.println("Cancelling any running job...");

		find = Job.getJobManager().find(null);
		for (Job job : find) {
			if (!job.isSystem() && !DUMPER_JOB_NAME.equals(job.getName())) {
				printTrace(job);
				cancelJob(job);
			}
		}

		find = Job.getJobManager().find(null);
		count = 0;
		for (Job job : find) {
			if (!job.isSystem()) {
				count++;
			}
		}
		if (count == 0) {
			Utils.printlnFormatted("All jobs removed");
			return true;
		}

		Utils.printlnFormatted("There are still unremoved job. This may cause the test to block.");
		return false;
	}
	
	/**
	 * Cancels all running system jobs.
	 * 
	 * @return true if no jobs are running at the end.
	 * @throws InterruptedException
	 */
	public static boolean cancelAllNonSystemJobs() throws InterruptedException {
		waitNonSystemJobs(0);
		return waitNonSystemJobs();
	}

	private static void printTrace(Job job) {
		Thread thread = job.getThread();
		if (thread == null) {
			Utils.println("Null Thread ....");
			return;
		}
		StackTraceElement[] stackTrace = thread.getStackTrace();
		if (stackTrace != null) {
			for (StackTraceElement traceElement : stackTrace) {
				if (traceElement != null) {
					Utils.println("\tat " + traceElement.toString());
				}
			}
		}
	}

	/**
	 * Will call cancel job and produce a log
	 * 
	 * @param job
	 */
	private static void cancelJob(Job job) {
		Utils.printlnFormatted("* [" + job.getName() + "]...");
		job.cancel();
		Utils.printlnFormatted("* [" + job.getName() + "]...cancelled.");
	}
}

