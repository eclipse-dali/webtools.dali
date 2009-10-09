/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaModel;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.JpaProject.Config;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.OrmFileCreationDataModelProvider;
import org.eclipse.jpt.core.internal.operations.PersistenceFileCreationDataModelProperties;
import org.eclipse.jpt.core.internal.operations.PersistenceFileCreationDataModelProvider;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

/**
 * The JPA model is synchronized so all changes to the list of JPA projects
 * are thread-safe.
 * 
 * The JPA model holds on to a list of JPA project configs and only instantiates
 * their associated JPA projects when necessary. Other than performance,
 * this should be transparent to clients.
 */
public class GenericJpaModel
	extends AbstractModel
	implements JpaModel
{

	/** maintain a list of all the current JPA projects */
	private final ArrayList<JpaProjectHolder> jpaProjectHolders = new ArrayList<JpaProjectHolder>();


	// ********** constructor **********

	/**
	 * Construct a JPA model and populate it with JPA projects for all the
	 * current Eclipse projects with JPA facets.
	 * The JPA model can only be instantiated by the JPA model manager.
	 */
	GenericJpaModel() throws CoreException {
		super();
		ResourcesPlugin.getWorkspace().getRoot().accept(new ResourceProxyVisitor(), IResource.NONE);
	}


	// ********** IJpaModel implementation **********

	/**
	 * This will trigger the instantiation of the JPA project associated with the
	 * specified Eclipse project.
	 */
	public synchronized JpaProject getJpaProject(IProject project) throws CoreException {
		return this.getJpaProjectHolder(project).jpaProject();
	}

	/**
	 * We can answer this question without instantiating the
	 * associated JPA project.
	 */
	public synchronized boolean containsJpaProject(IProject project) {
		return this.getJpaProjectHolder(project).holdsJpaProjectFor(project);
	}

	/**
	 * This will trigger the instantiation of all the JPA projects.
	 */
	public synchronized Iterator<JpaProject> jpaProjects() throws CoreException {
		// force the CoreException to occur here (instead of later, in Iterator#next())
		ArrayList<JpaProject> jpaProjects = new ArrayList<JpaProject>(this.jpaProjectHolders.size());
		for (JpaProjectHolder holder : this.jpaProjectHolders) {
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

	/**
	 * This will trigger the instantiation of the JPA project associated with the
	 * specified file.
	 */
	public synchronized JpaFile getJpaFile(IFile file) throws CoreException {
		JpaProject jpaProject = this.getJpaProject(file.getProject());
		return (jpaProject == null) ? null : jpaProject.getJpaFile(file);
	}


	// ********** internal methods **********

	/**
	 * never return null
	 */
	private JpaProjectHolder getJpaProjectHolder(IProject project) {
		for (JpaProjectHolder holder : this.jpaProjectHolders) {
			if (holder.holdsJpaProjectFor(project)) {
				return holder;
			}
		}
		return NullJpaProjectHolder.instance();
	}

	private JpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.getJpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.getConnectionProfileName(project));
		config.setUserOverrideDefaultCatalog(JptCorePlugin.getUserOverrideDefaultCatalog(project));
		config.setUserOverrideDefaultSchema(JptCorePlugin.getUserOverrideDefaultSchema(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		config.setMetamodelSourceFolderName(JptCorePlugin.getMetamodelSourceFolderName(project));
		return config;
	}

	/* private */ void addJpaProject(IProject project) {
		this.addJpaProject(this.buildJpaProjectConfig(project));
	}

	/**
	 * Add a JPA project to the JPA model for the specified Eclipse project.
	 * JPA projects can only be added by the JPA model manager.
	 * The JPA project will only be instantiated later, on demand.
	 */
	private void addJpaProject(JpaProject.Config config) {
		dumpStackTrace();  // figure out exactly when JPA projects are added
		this.jpaProjectHolders.add(this.getJpaProjectHolder(config.getProject()).buildJpaProjectHolder(this, config));
	}

	/**
	 * Remove the JPA project corresponding to the specified Eclipse project
	 * from the JPA model. Return whether the removal actually happened.
	 * JPA projects can only be removed by the JPA model manager.
	 */
	private void removeJpaProject(IProject project) {
		dumpStackTrace();  // figure out exactly when JPA projects are removed
		this.getJpaProjectHolder(project).remove();
	}


	// ********** Resource events **********

	/**
	 * A project is being deleted. Remove its corresponding
	 * JPA project if appropriate.
	 */
	synchronized void projectPreDelete(IProject project) {
		this.removeJpaProject(project);
	}

	/**
	 * Forward the specified resource delta to all our JPA projects;
	 * they will each determine whether the event is significant.
	 */
	synchronized void projectChanged(IResourceDelta delta)  throws CoreException {
		for (JpaProjectHolder holder : this.jpaProjectHolders) {
			holder.projectChanged(delta);
		}
	}


	// ********** Resource and/or Facet events **********

	/**
	 * Check whether the JPA facet has been added or removed.
	 */
	synchronized void checkForTransition(IProject project) {
		boolean jpaFacet = JptCorePlugin.projectHasJpaFacet(project);
		boolean jpaProject = this.containsJpaProject(project);

		if (jpaFacet) {
			if ( ! jpaProject) {  // JPA facet added
				this.addJpaProject(project);
			}
		} else {
			if (jpaProject) {  // JPA facet removed
				this.removeJpaProject(project);
			}
		}
	}


	// ********** Facet events **********

	synchronized void jpaFacetedProjectPostInstall(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		IDataModel dataModel = (IDataModel) event.getActionConfig();

		boolean buildOrmXml = dataModel.getBooleanProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML);
		this.createProjectXml(project, buildOrmXml);

		// assume(?) this is the first event to indicate we need to add the JPA project to the JPA model
		this.addJpaProject(project);
	}

	private void createProjectXml(IProject project, boolean buildOrmXml) {
		this.createPersistenceXml(project);

		if (buildOrmXml) {
			this.createOrmXml(project);
		}
	}

	private void createPersistenceXml(IProject project) {
		IDataModel config =
			DataModelFactory.createDataModel(new PersistenceFileCreationDataModelProvider());
		config.setProperty(PersistenceFileCreationDataModelProperties.PROJECT_NAME, project.getName());
		// default values for all other properties should suffice
		try {
			config.getDefaultOperation().execute(null, null);
		}
		catch (ExecutionException e) {
			JptCorePlugin.log(e);
		}
	}

	private void createOrmXml(IProject project) {
		IDataModel config =
			DataModelFactory.createDataModel(new OrmFileCreationDataModelProvider());
		config.setProperty(OrmFileCreationDataModelProperties.PROJECT_NAME, project.getName());
		// default values for all other properties should suffice
		try {
			config.getDefaultOperation().execute(null, null);
		}
		catch (ExecutionException e) {
			JptCorePlugin.log(e);
		}
	}

	// TODO remove classpath items? persistence.xml? orm.xml?
	synchronized void jpaFacetedProjectPreUninstall(IProjectFacetActionEvent event) {
		// assume(?) this is the first event to indicate we need to remove the JPA project to the JPA model
		this.removeJpaProject(event.getProject().getProject());
	}


	// ********** Java events **********

	/**
	 * Forward the Java element changed event to all the JPA projects
	 * because the event could affect multiple projects.
	 */
	synchronized void javaElementChanged(ElementChangedEvent event) {
		for (JpaProjectHolder jpaProjectHolder : this.jpaProjectHolders) {
			jpaProjectHolder.javaElementChanged(event);
		}
	}


	// ********** miscellaneous **********

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt
	 * (e.g. when the user changes a project's JPA platform).
	 */
	synchronized void rebuildJpaProject(IProject project) {
		this.removeJpaProject(project);
		this.addJpaProject(project);
	}

	/**
	 * Dispose the JPA model by disposing and removing all its JPA projects.
	 * The JPA model can only be disposed by the JPA model manager.
	 */
	synchronized void dispose() {
		// clone the list to prevent concurrent modification exceptions
		JpaProjectHolder[] holders = this.jpaProjectHolders.toArray(new JpaProjectHolder[this.jpaProjectHolders.size()]);
		for (JpaProjectHolder holder : holders) {
			holder.remove();
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append("JPA projects size: " + this.jpaProjectsSize()); //$NON-NLS-1$
	}


	// ********** holder callbacks **********

	/**
	 * called by the JPA project holder when the JPA project is actually
	 * instantiated
	 */
	/* private */ void jpaProjectBuilt(JpaProject jpaProject) {
		this.fireItemAdded(JPA_PROJECTS_COLLECTION, jpaProject);
	}

	/**
	 * called by the JPA project holder if the JPA project has been
	 * instantiated and we need to remove it
	 */
	/* private */ void jpaProjectRemoved(JpaProject jpaProject) {
		this.fireItemRemoved(JPA_PROJECTS_COLLECTION, jpaProject);
	}

	/**
	 * called by the JPA project holder
	 */
	/* private */ void removeJpaProjectHolder(JpaProjectHolder jpaProjectHolder) {
		this.jpaProjectHolders.remove(jpaProjectHolder);
	}


	// ********** JPA project holders **********

	private interface JpaProjectHolder {

		boolean holdsJpaProjectFor(IProject project);

		JpaProject jpaProject() throws CoreException;

		void projectChanged(IResourceDelta delta) throws CoreException;

		void javaElementChanged(ElementChangedEvent event);

		JpaProjectHolder buildJpaProjectHolder(GenericJpaModel jpaModel, JpaProject.Config config);

		void remove();

	}

	private static class NullJpaProjectHolder implements JpaProjectHolder {
		private static final JpaProjectHolder INSTANCE = new NullJpaProjectHolder();

		static JpaProjectHolder instance() {
			return INSTANCE;
		}

		// ensure single instance
		private NullJpaProjectHolder() {
			super();
		}

		public boolean holdsJpaProjectFor(IProject project) {
			return false;
		}

		public JpaProject jpaProject() throws CoreException {
			return null;
		}

		public void projectChanged(IResourceDelta delta) throws CoreException {
			// do nothing
		}

		public void javaElementChanged(ElementChangedEvent event) {
			// do nothing
		}

		public JpaProjectHolder buildJpaProjectHolder(GenericJpaModel jpaModel, Config config) {
			return new DefaultJpaProjectHolder(jpaModel, config);
		}

		public void remove() {
			// do nothing
		}

		@Override
		public String toString() {
			return ClassTools.shortClassNameForObject(this);
		}
	}

	/**
	 * Pair a JPA project config with its lazily-initialized JPA project.
	 */
	private static class DefaultJpaProjectHolder implements JpaProjectHolder {
		private final GenericJpaModel jpaModel;
		private final JpaProject.Config config;
		private JpaProject jpaProject;

		DefaultJpaProjectHolder(GenericJpaModel jpaModel, JpaProject.Config config) {
			super();
			this.jpaModel = jpaModel;
			this.config = config;
		}

		public boolean holdsJpaProjectFor(IProject project) {
			return this.config.getProject().equals(project);
		}

		public JpaProject jpaProject() throws CoreException {
			if (this.jpaProject == null) {
				this.jpaProject = this.buildJpaProject();
				// notify listeners of the JPA model
				this.jpaModel.jpaProjectBuilt(this.jpaProject);
			}
			return this.jpaProject;
		}

		private JpaProject buildJpaProject() throws CoreException {
			JpaPlatform jpaPlatform = this.config.getJpaPlatform();
			if (jpaPlatform == null) {
				return null;
			}
			JpaProject result = jpaPlatform.getJpaFactory().buildJpaProject(this.config);
			result.setUpdater(new AsynchronousJpaProjectUpdater(result));
			return result;
		}

		public void projectChanged(IResourceDelta delta) throws CoreException {
			if (this.jpaProject != null) {
				this.jpaProject.projectChanged(delta);
			}
		}

		public void javaElementChanged(ElementChangedEvent event) {
			if (this.jpaProject != null) {
				this.jpaProject.javaElementChanged(event);
			}
		}

		public JpaProjectHolder buildJpaProjectHolder(GenericJpaModel jm, Config c) {
			throw new IllegalArgumentException(c.getProject().getName());
		}

		public void remove() {
			this.jpaModel.removeJpaProjectHolder(this);
			if (this.jpaProject != null) {
				this.jpaModel.jpaProjectRemoved(this.jpaProject);
				this.jpaProject.dispose();
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.config.getProject().getName());
		}

	}


	// ********** resource proxy visitor **********

	/**
	 * Visit the workspace resource tree, adding a JPA project to the
	 * JPA model for each open Eclipse project that has a JPA facet.
	 */
	private class ResourceProxyVisitor implements IResourceProxyVisitor {

		ResourceProxyVisitor() {
			super();
		}

		public boolean visit(IResourceProxy resourceProxy) throws CoreException {
			switch (resourceProxy.getType()) {
				case IResource.ROOT :
					return true;  // all projects are in the "root"
				case IResource.PROJECT :
					this.checkProject(resourceProxy);
					return false;  // no nested projects
				default :
					return false;
			}
		}

		private void checkProject(IResourceProxy resourceProxy) {
			if (resourceProxy.isAccessible()) {  // the project exists and is open
				IProject project = (IProject) resourceProxy.requestResource();
				if (JptCorePlugin.projectHasJpaFacet(project)) {
					GenericJpaModel.this.addJpaProject(project);
				}
			}
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** DEBUG **********

	// @see JpaModelTests#testDEBUG()
	private static final boolean DEBUG = false;

	private static void dumpStackTrace() {
		if (DEBUG) {
			// lock System.out so the stack elements are printed out contiguously
			synchronized (System.out) {
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

}
