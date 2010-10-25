/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;

/**
 * The JPA project manager maintains a list of all JPA projects in the workspace.
 * It keeps the list (and the state of the JPA projects themselves)
 * synchronized with the workspace by listening for various
 * changes:<ul>
 * <li>Resource
 * <li>Java
 * <li>Faceted Project
 * </ul>
 * We use an Eclipse {@link ILock lock} to synchronize access to the JPA
 * projects when dealing with these events. In an effort to reduce deadlocks,
 * the simple Resource and Java change events are dispatched to a background
 * thread, allowing us to handle the events outside of the workspace lock held
 * during resource and Java change notifications.
 * <p>
 * Events that trigger either the adding or removing of a JPA project (e.g.
 * {@link IResourceChangeEvent#POST_CHANGE}) are handled "synchronously"
 * by allowing the background thread to handle any outstanding events before
 * updating the list of JPA projects and returning execution to the event
 * source.
 * <p>
 * Various things that cause us to add or remove a JPA project:<ul>
 * <li>The {@link JptCorePlugin} will "lazily" instantiate and {@link #start() start}
 *     a JPA project manager as appropriate. This will trigger the manager
 *     to find and add all pre-existing JPA projects.
 * 
 * <li>Project created and facet installed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 * <li>Project facet uninstalled<p>
 *     {@link IFacetedProjectEvent.Type#PRE_UNINSTALL}
 * 
 * <li>Project opened<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#ADDED} facet settings file
 *     (<code>/.settings/org.eclipse.wst.common.project.facet.core.xml</code>)
 * <li>Project closed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file
 * 
 * <li>Pre-existing project imported from directory or archive (created and opened)<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#ADDED} facet settings file
 * <li>Project renamed<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file of old project
 *     -> {@link IResourceDelta#ADDED} facet settings file of new project
 * <li>Project deleted<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#REMOVED} facet settings file
 * 
 * <li>Project facet installed by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#CHANGED} facet settings file
 * <li>Project facet uninstalled by editing the facets settings file directly<p>
 *     {@link IResourceChangeEvent#POST_CHANGE}
 *     -> {@link IResource#FILE}
 *     -> {@link IResourceDelta#CHANGED} facet settings file
 * </ul>
 */
//TODO Still need to look at faceted project listener for facet uninstall
class GenericJaxbProjectManager
		extends AbstractModel
		implements JaxbProjectManager {
	
	/**
	 * Synchronize access to the JAXB projects.
	 */
	/* private */ final ILock lock = this.getJobManager().newLock();
	
	
	public IJobManager getJobManager() {
		return Job.getJobManager();
	}
	
	// ********** plug-in controlled life-cycle **********

	/**
	 * Internal: called by {@link JptJaxbCorePlugin}.
	 */
	void start() {
		try {
			this.lock.acquire();
			this.start_();
		} finally {
			this.lock.release();
		}
	}

	private void start_() {
		debug("*** JAXB project manager START ***"); //$NON-NLS-1$
		try {
//			this.buildJpaProjects();
//			this.eventHandler.start();
//			this.getWorkspace().addResourceChangeListener(this.resourceChangeListener, RESOURCE_CHANGE_EVENT_TYPES);
//			FacetedProjectFramework.addListener(this.facetedProjectListener, FACETED_PROJECT_EVENT_TYPES);
//			JavaCore.addElementChangedListener(this.javaElementChangeListener, JAVA_CHANGE_EVENT_TYPES);
		} catch (RuntimeException ex) {
			JptCorePlugin.log(ex);
			this.stop_();
		}
	}
	
	/**
	 * Internal: called by {@link JptCorePlugin Dali plug-in}.
	 */
	void stop() throws Exception {
		try {
			this.lock.acquire();
			this.stop_();
		} finally {
			this.lock.release();
		}
	}

	private void stop_() {
		debug("*** JPA project manager STOP ***"); //$NON-NLS-1$
//		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
//		FacetedProjectFramework.removeListener(this.facetedProjectListener);
//		this.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);
//		this.eventHandler.stop();
//		this.clearJpaProjects();
	}
	
	
	// ********** DEBUG **********

	// @see JaxbProjectManagerTests#testDEBUG()
	private static final boolean DEBUG = false;
	
	/**
	 * trigger #toString() call and string concatenation only if DEBUG is true
	 */
	/* private */ static void debug(String message, Object object) {
		if (DEBUG) {
			debug_(message + object);
		}
	}
	
	/* private */ static void debug(String message) {
		if (DEBUG) {
			debug_(message);
		}
	}
	
	private static void debug_(String message) {
		System.out.println(Thread.currentThread().getName() + ": " + message); //$NON-NLS-1$
	}
	
	/* private */ static void dumpStackTrace() {
		dumpStackTrace(null);
	}
	
	/* private */ static void dumpStackTrace(String message, Object object) {
		if (DEBUG) {
			dumpStackTrace_(message + object);
		}
	}
	
	/* private */ static void dumpStackTrace(String message) {
		if (DEBUG) {
			dumpStackTrace_(message);
		}
	}
	
	private static void dumpStackTrace_(String message) {
		// lock System.out so the stack elements are printed out contiguously
		synchronized (System.out) {
			if (message != null) {
				debug_(message);
			}
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			// skip the first 3 elements - those are this method and 2 methods in Thread
			for (int i = 3; i < stackTrace.length; i++) {
				StackTraceElement element = stackTrace[i];
				if (element.getMethodName().equals("invoke0")) { //$NON-NLS-1$
					break;  // skip all elements outside of the JUnit test
				}
				System.out.println("\t" + element); //$NON-NLS-1$
			}
		}
	}
}
