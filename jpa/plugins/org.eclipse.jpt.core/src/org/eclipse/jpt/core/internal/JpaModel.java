/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.internal.IJpaProject.Config;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;

/**
 * The JPA model is synchronized so all changes to the list of JPA projects
 * are thread-safe.
 * 
 * The JPA model holds on to a list of JPA project configs and only instantiates
 * their associated JPA projects when necessary. Other than performance,
 * this should be transparent to clients.
 */
public class JpaModel extends AbstractModel implements IJpaModel {

	/** maintain a list of all the current JPA projects */
	private ArrayList<IJpaProjectHolder> jpaProjectHolders = new ArrayList<IJpaProjectHolder>();


	// ********** constructor **********

	/**
	 * Construct a JPA model and populate it with JPA projects to be built
	 * from the specified set of JPA project configs.
	 * The JPA model can only be instantiated by the JPA model manager.
	 */
	JpaModel(Iterable<IJpaProject.Config> configs) {
		super();
		for (IJpaProject.Config config : configs) {
			this.addJpaProject(config);
		}
	}


	// ********** IJpaModel implementation **********

	/**
	 * This will trigger the instantiation of the JPA project associated with the
	 * specified Eclipse project.
	 */
	public synchronized IJpaProject jpaProject(IProject project) throws CoreException {
		return this.jpaProjectHolder(project).jpaProject();
	}

	/**
	 * We can answer this question without instantiating the
	 * associated JPA project.
	 */
	public synchronized boolean containsJpaProject(IProject project) {
		return this.jpaProjectHolder(project).holdsJpaProjectFor(project);
	}

	/**
	 * This will trigger the instantiation of all the JPA projects.
	 */
	public synchronized Iterator<IJpaProject> jpaProjects() throws CoreException {
		// force the CoreException to occur here (instead of later, in Iterator#next())
		ArrayList<IJpaProject> jpaProjects = new ArrayList<IJpaProject>(this.jpaProjectHolders.size());
		for (IJpaProjectHolder holder : this.jpaProjectHolders) {
			jpaProjects.add(holder.jpaProject());
		}
		return jpaProjects.iterator();
	}

	/**
	 * We can answer this question without instantiating any JPA projects.
	 */
	public synchronized int jpaProjectsSize() {
		return this.jpaProjectHolders.size();
	}


	// ********** internal methods **********

	/**
	 * never return null
	 */
	private IJpaProjectHolder jpaProjectHolder(IProject project) {
		for (IJpaProjectHolder holder : this.jpaProjectHolders) {
			if (holder.holdsJpaProjectFor(project)) {
				return holder;
			}
		}
		return NullJpaProjectHolder.instance();
	}

	/**
	 * Add a JPA project to the JPA model for the specified Eclipse project.
	 * JPA projects can only be added by the JPA model manager.
	 * The JPA project will only be instantiated later, on demand.
	 */
	synchronized void addJpaProject(IJpaProject.Config config) {
		dumpStackTrace();  // figure out exactly when JPA projects are built
		this.jpaProjectHolders.add(this.jpaProjectHolder(config.project()).buildJpaProjectHolder(this, config));
	}

	/**
	 * Remove the JPA project corresponding to the specified Eclipse project
	 * from the JPA model. Return whether the removal actually happened.
	 * JPA projects can only be removed by the JPA model manager.
	 */
	synchronized boolean removeJpaProject(IProject project) {
		dumpStackTrace();  // figure out exactly when JPA projects are removed
		return this.jpaProjectHolder(project).remove();
	}

	/**
	 * Dispose the JPA model by disposing and removing all its JPA projects.
	 * The JPA model can only be disposed by the JPA model manager.
	 */
	synchronized void dispose() {
		// clone the list to prevent concurrent modification exceptions
		@SuppressWarnings("unchecked")
		ArrayList<IJpaProjectHolder> holders = (ArrayList<IJpaProjectHolder>) this.jpaProjectHolders.clone();
		for (IJpaProjectHolder holder : holders) {
			holder.remove();
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("JPA projects size: " + this.jpaProjectsSize());
	}


	// ********** events **********

	/**
	 * Forward the specified resource delta to the JPA project corresponding
	 * to the specified Eclipse project.
	 */
	synchronized void synchronizeJpaFiles(IProject project, IResourceDelta delta) throws CoreException {
		this.jpaProjectHolder(project).synchronizeJpaFiles(delta);
	}

	/**
	 * Forward the Java element changed event to all the JPA projects
	 * because the event could affect multiple projects.
	 */
	synchronized void javaElementChanged(ElementChangedEvent event) {
		for (IJpaProjectHolder jpaProjectHolder : this.jpaProjectHolders) {
			jpaProjectHolder.javaElementChanged(event);
		}
	}


	// ********** holder callbacks **********

	/**
	 * called by the JPA project holder when the JPA project is actually
	 * instantiated
	 */
	/* private */ void jpaProjectBuilt(IJpaProject jpaProject) {
		this.fireItemAdded(JPA_PROJECTS_COLLECTION, jpaProject);
	}

	/**
	 * called by the JPA project holder if the JPA project has been
	 * instantiated and we need to remove it
	 */
	/* private */ void jpaProjectRemoved(IJpaProject jpaProject) {
		this.fireItemRemoved(JPA_PROJECTS_COLLECTION, jpaProject);
	}

	/**
	 * called by the JPA project holder
	 */
	/* private */ void removeJpaProjectHolder(IJpaProjectHolder jpaProjectHolder) {
		this.jpaProjectHolders.remove(jpaProjectHolder);
	}


	// ********** JPA project holder **********

	private interface IJpaProjectHolder {

		boolean holdsJpaProjectFor(IProject project);

		IJpaProject jpaProject() throws CoreException;

		void synchronizeJpaFiles(IResourceDelta delta) throws CoreException;

		void javaElementChanged(ElementChangedEvent event);

		IJpaProjectHolder buildJpaProjectHolder(JpaModel jpaModel, IJpaProject.Config config);

		boolean remove();

	}

	private static class NullJpaProjectHolder implements IJpaProjectHolder {
		private static final IJpaProjectHolder INSTANCE = new NullJpaProjectHolder();

		static IJpaProjectHolder instance() {
			return INSTANCE;
		}

		// ensure single instance
		private NullJpaProjectHolder() {
			super();
		}

		public boolean holdsJpaProjectFor(IProject project) {
			return false;
		}

		public IJpaProject jpaProject() throws CoreException {
			return null;
		}

		public void synchronizeJpaFiles(IResourceDelta delta) throws CoreException {
			// do nothing
		}

		public void javaElementChanged(ElementChangedEvent event) {
			// do nothing
		}

		public IJpaProjectHolder buildJpaProjectHolder(JpaModel jpaModel, Config config) {
			return new JpaProjectHolder(jpaModel, config);
		}

		public boolean remove() {
			return false;
		}

		@Override
		public String toString() {
			return ClassTools.shortClassNameForObject(this);
		}
	}

	/**
	 * Pair a JPA project config with its lazily-initialized JPA project.
	 */
	private static class JpaProjectHolder implements IJpaProjectHolder {
		private final JpaModel jpaModel;
		private final IJpaProject.Config config;
		private IJpaProject jpaProject;

		JpaProjectHolder(JpaModel jpaModel, IJpaProject.Config config) {
			super();
			this.jpaModel = jpaModel;
			this.config = config;
		}

		public boolean holdsJpaProjectFor(IProject project) {
			return this.config.project().equals(project);
		}

		public IJpaProject jpaProject() throws CoreException {
			if (this.jpaProject == null) {
				this.jpaProject = this.buildJpaProject();
				// notify listeners of the JPA model
				this.jpaModel.jpaProjectBuilt(this.jpaProject);
			}
			return this.jpaProject;
		}

		private IJpaProject buildJpaProject() throws CoreException {
			return this.config.jpaPlatform().getJpaFactory().createJpaProject(this.config);
		}

		public void synchronizeJpaFiles(IResourceDelta delta) throws CoreException {
			if (this.jpaProject != null) {
				this.jpaProject.synchronizeJpaFiles(delta);
			}
		}

		public void javaElementChanged(ElementChangedEvent event) {
			if (this.jpaProject != null) {
				this.jpaProject.javaElementChanged(event);
			}
		}

		public IJpaProjectHolder buildJpaProjectHolder(JpaModel jm, Config c) {
			throw new IllegalArgumentException(c.project().getName());
		}

		public boolean remove() {
			this.jpaModel.removeJpaProjectHolder(this);
			if (this.jpaProject != null) {
				this.jpaModel.jpaProjectRemoved(this.jpaProject);
				this.jpaProject.dispose();
			}
			return true;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.config.project().getName());
		}

	}


	// ********** debug **********

	private static final boolean DEBUG = false;

	private static void dumpStackTrace() {
		if (DEBUG) {
			// lock System.out so the stack elements are printed out contiguously
			synchronized (System.out) {
				StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
				// skip the first 3 elements - those are this method and 2 methods in Thread
				for (int i = 3; i < stackTrace.length; i++) {
					StackTraceElement element = stackTrace[i];
					if (element.getMethodName().equals("invoke0")) {
						break;  // skip all elements outside of the JUnit test
					}
					System.out.println("\t" + element);
				}
			}
		}
	}

}
