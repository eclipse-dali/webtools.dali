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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.IJpaProject.Config;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmResource;
import org.eclipse.jpt.core.internal.content.orm.resource.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.content.persistence.Persistence;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.content.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.content.persistence.resource.PersistenceResource;
import org.eclipse.jpt.core.internal.facet.IJpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;
import org.eclipse.wst.common.project.facet.core.events.IProjectFacetActionEvent;

/**
 * "Internal" global stuff.
 * Provide access via a singleton.
 * Hold and manage the JPA model (which holds all the JPA projects)
 * and the various global listeners.
 * All the methods that handle the events from the listeners are 'synchronized',
 * effectively single-threading them and forcing the events to be queued up
 * and handled one at a time.
 * 
 * Various things that cause us to add or remove a JPA project:
 * - Project created via the "New Project" wizard - facet POST_INSTALL
 * - Project created programmatically - facet POST_INSTALL
 * - Project closed - resource PRE_CLOSE
 * - Project deleted - resource PRE_DELETE
 * - Project opened - facet PROJECT_MODIFIED
 * - Pre-existing project imported - facet PROJECT_MODIFIED
 * - Project facet uninstalled via "Properties" dialog - facet PRE_UNINSTALL
 */
public class JpaModelManager {

	/**
	 * The JPA model - null until the plug-in is started.
	 */
	private JpaModel jpaModel;

	/**
	 * Listen for changes to projects and files.
	 */
	private final IResourceChangeListener resourceChangeListener;

	/**
	 * Listen for the JPA facet being added or removed from a project.
	 */
	private final IFacetedProjectListener facetedProjectListener;

	/**
	 * Listen for Java changes and forward them to the JPA model,
	 * which will forward them to the JPA projects.
	 */
	private final IElementChangedListener javaElementChangeListener;

	/**
	 * Listen for changes to the "Default JPA Lib" preference
	 * so we can update the corresponding classpath variable.
	 */
	private final IPropertyChangeListener preferencesListener;


	// ********** singleton **********

	private static JpaModelManager INSTANCE;  // lazily-final

	/**
	 * Return the singleton JPA model manager.
	 */
	public static final synchronized JpaModelManager instance() {
		if (INSTANCE == null) {
			INSTANCE = new JpaModelManager();
		}
		return INSTANCE;
	}


	// ********** constructor **********

	/**
	 * Private - ensure single instance.
	 */
	private JpaModelManager() {
		super();
		this.resourceChangeListener = new ResourceChangeListener();
		this.facetedProjectListener = new FacetedProjectListener();
		this.javaElementChangeListener = new JavaElementChangeListener();
		this.preferencesListener = new PreferencesListener();
	}


	// ********** life-cycle controlled by the plug-in **********

	/**
	 * internal - called by JptCorePlugin
	 */
	public synchronized void start() throws Exception {
		try {
			this.jpaModel = this.buildJpaModel();
			ResourcesPlugin.getWorkspace().addResourceChangeListener(this.resourceChangeListener);
			FacetedProjectFramework.addListener(this.facetedProjectListener, IFacetedProjectEvent.Type.values());
			JavaCore.addElementChangedListener(this.javaElementChangeListener);
			JptCorePlugin.instance().getPluginPreferences().addPropertyChangeListener(this.preferencesListener);
		} catch (RuntimeException ex) {
			this.log(ex);
			this.stop();
		}
	}

	private JpaModel buildJpaModel() throws CoreException {
		ResourceProxyVisitor visitor = new ResourceProxyVisitor(this.buildJpaProjectConfigBuilder());
		ResourcesPlugin.getWorkspace().getRoot().accept(visitor, IResource.NONE);
		return new JpaModel(visitor.jpaProjectConfigs());
	}

	/**
	 * internal - called by JptCorePlugin
	 */
	public synchronized void stop() throws Exception {
		JptCorePlugin.instance().getPluginPreferences().removePropertyChangeListener(this.preferencesListener);
		JavaCore.removeElementChangedListener(this.javaElementChangeListener);
		FacetedProjectFramework.removeListener(this.facetedProjectListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this.resourceChangeListener);
		this.jpaModel.dispose();
		this.jpaModel = null;
	}


	// ********** API **********

	/**
	 * Return the workspace-wide JPA model.
	 */
	public IJpaModel jpaModel() {
		return this.jpaModel;
	}

	/** 
	 * Return the JPA project corresponding to the specified Eclipse project,
	 * or null if unable to associate the specified project with a
	 * JPA project.
	 */
	public IJpaProject jpaProject(IProject project) throws CoreException {
		return this.jpaModel.jpaProject(project);
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	public synchronized IJpaFile jpaFile(IFile file) throws CoreException {
		IJpaProject jpaProject = this.jpaProject(file.getProject());
		return (jpaProject == null) ? null : jpaProject.jpaFile(file);
	}

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt.
	 */
	public synchronized void rebuildJpaProject(IProject project) {
		this.removeJpaProject(project);
		this.addJpaProject(project);
	}

	/**
	 * Log the specified status.
	 */
	public void log(IStatus status) {
		JptCorePlugin.log(status);
    }

	/**
	 * Log the specified message.
	 */
	public void log(String msg) {
		JptCorePlugin.log(msg);
    }

	/**
	 * Log the specified exception or error.
	 */
	public void log(Throwable throwable) {
		JptCorePlugin.log(throwable);
	}


	// ********** JPA project configs **********

	private ResourceProxyVisitor.JpaProjectConfigBuilder buildJpaProjectConfigBuilder() {
		return new ResourceProxyVisitor.JpaProjectConfigBuilder() {
			public Iterable<Config> buildJpaProjectConfigs(Iterable<IProject> jpaFacetedProjects) {
				return JpaModelManager.this.buildJpaProjectConfigs(jpaFacetedProjects);
			}
		};
	}

	/* private */ Iterable<IJpaProject.Config> buildJpaProjectConfigs(Iterable<IProject> jpaFacetedProjects) {
		ArrayList<IJpaProject.Config> configs = new ArrayList<IJpaProject.Config>();
		for (IProject project : jpaFacetedProjects) {
			configs.add(buildJpaProjectConfig(project));
		}
		return configs;
	}

	private IJpaProject.Config buildJpaProjectConfig(IProject project) {
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.jpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.connectionProfileName(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		return config;
	}


	// ********** resource proxy visitor **********

	/**
	 * Visit the workspace resource tree, collecting open Eclipse projects
	 * that have a JPA facet.
	 */
	private static class ResourceProxyVisitor implements IResourceProxyVisitor {
		private final JpaProjectConfigBuilder builder;
		private final ArrayList<IProject> jpaFacetedProjects = new ArrayList<IProject>();

		ResourceProxyVisitor(JpaProjectConfigBuilder builder) {
			super();
			this.builder = builder;
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
					this.jpaFacetedProjects.add(project);
				}
			}
		}

		Iterable<IJpaProject.Config> jpaProjectConfigs() {
			return this.builder.buildJpaProjectConfigs(this.jpaFacetedProjects);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

		// ********** member interface **********
		interface JpaProjectConfigBuilder {
			Iterable<IJpaProject.Config> buildJpaProjectConfigs(Iterable<IProject> jpaFacetedProjects);
		}

	}


	// ********** internal **********

	private void addJpaProject(IProject project) {
		this.jpaModel.addJpaProject(this.buildJpaProjectConfig(project));
	}

	/**
	 * Remove the JPA project corresponding to the specified Eclipse project.
	 * Do nothing if there is no corresponding JPA project.
	 */
	private void removeJpaProject(IProject project) {
		if (this.jpaModel.removeJpaProject(project)) {
//			try {
//				// TODO remove classpath items?
//				// TODO remove persistence.xml?
//				// TODO remove orm.xml?
//				// TODO copy to dispose()? where were the markers added in the first place???
//				ResourcesPlugin.getWorkspace().deleteMarkers(project.findMarkers(JptCorePlugin.VALIDATION_MARKER_ID, true, IResource.DEPTH_INFINITE));
//			} catch (CoreException ex) {
//				this.log(ex);  // not much we can do
//			}
		}
	}


	// ********** resource changed **********

	/**
	 * Check for:
	 *   - project close/delete
	 *   - file add/remove
	 */
	/* private */ synchronized void resourceChanged(IResourceChangeEvent event) {
		if (! (event.getSource() instanceof IWorkspace)) {
			return;  // this probably shouldn't happen...
		}
		switch (event.getType()){
			case IResourceChangeEvent.PRE_DELETE :  // project-only event
			case IResourceChangeEvent.PRE_CLOSE :  // project-only event
				this.projectPreDeleteOrClose(event);
				break;
			case IResourceChangeEvent.POST_CHANGE :
				this.resourcePostChange(event);
				break;
			case IResourceChangeEvent.PRE_BUILD :
			case IResourceChangeEvent.POST_BUILD :
			default :
				break;
		}
	}

	/**
	 * A project is being deleted or closed. Remove its corresponding
	 * JPA project if appropriate.
	 */
	private void projectPreDeleteOrClose(IResourceChangeEvent event) {
		IResource resource = event.getResource();
		if (resource.getType() != IResource.PROJECT) {
			return;  // this probably shouldn't happen...
		}
		IProject project = (IProject) resource;
		if (JptCorePlugin.projectHasJpaFacet(project)) {
			this.removeJpaProject(project);
		}
	}

	/**
	 * A resource has changed somehow.
	 * Check for files being added or removed.
	 * (The JPA project only handles added and removed files here, ignoring
	 * changed files.)
	 */
	private void resourcePostChange(IResourceChangeEvent event) {
		this.synchronizeFiles(event.getDelta());
	}

	private void synchronizeFiles(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.synchronizeFiles(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.synchronizeFiles((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void synchronizeFiles(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.synchronizeFiles(deltas[i]);  // recurse
		}
	}

	private void synchronizeFiles(IProject project, IResourceDelta delta) {
		if (JptCorePlugin.projectHasJpaFacet(project)) {
			this.synchronizeJpaFiles(project, delta);
		}
	}

	/**
	 * Checked exceptions bite.
	 */
	private void synchronizeJpaFiles(IProject project, IResourceDelta delta) {
		try {
			this.jpaModel.synchronizeJpaFiles(project, delta);
		} catch (CoreException ex) {
			this.log(ex);  // problem traversing the project's resources - not much we can do
		}
	}


	// ********** faceted project changed **********

	/**
	 * Check for:
	 *   - install of JPA facet
	 *   - un-install of JPA facet
	 *   - sudden appearance or disappearance of JPA facet
	 */
	/* private */ synchronized void facetedProjectChanged(IFacetedProjectEvent event) {
		switch (event.getType()) {
			case POST_INSTALL :
				this.facetedProjectPostInstall((IProjectFacetActionEvent) event);
				break;
			case PRE_UNINSTALL :
				this.facetedProjectPreUninstall((IProjectFacetActionEvent) event);
				break;
			case PROJECT_MODIFIED :
				this.facetedProjectModified(event.getProject().getProject());
				break;
			default :
				break;
		}
	}

	private void facetedProjectPostInstall(IProjectFacetActionEvent event) {
		if (JptCorePlugin.projectHasJpaFacet(event.getProject().getProject())) {
			this.jpaFacetedProjectPostInstall(event);
		}
	}

	private void jpaFacetedProjectPostInstall(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		IDataModel dataModel = (IDataModel) event.getActionConfig();

		this.createPersistenceXml(project);

		if (dataModel.getBooleanProperty(IJpaFacetDataModelProperties.CREATE_ORM_XML)) {
			this.createOrmXml(project);
		}

		this.addJpaProject(project);
	}

	private void createPersistenceXml(IProject project) {
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(project, JptCorePlugin.persistenceXmlDeploymentURI(project));
		PersistenceResource resource = pae.getPersistenceResource();

		// 202811 - do not add content if it is already present
		if (resource.getPersistence() == null) {
			Persistence persistence = PersistenceFactory.eINSTANCE.createPersistence();
			persistence.setVersion("1.0");
			PersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createPersistenceUnit();
			pUnit.setName(project.getName());
			persistence.getPersistenceUnits().add(pUnit);
			resource.getContents().add(persistence);
			pae.save(null);
		}
		
		pae.dispose();
	}

	private void createOrmXml(IProject project) {
		OrmArtifactEdit oae =
				OrmArtifactEdit.getArtifactEditForWrite(project, JptCorePlugin.ormXmlDeploymentURI(project));
		OrmResource resource = oae.getOrmResource();

		// 202811 - do not add content if it is already present
		if (resource.getEntityMappings() == null) {
			EntityMappingsInternal entityMappings = OrmFactory.eINSTANCE.createEntityMappingsInternal();
			entityMappings.setVersion("1.0");
			resource.getContents().add(entityMappings);
			oae.save(null);
		}
		
		oae.dispose();
	}

	private void facetedProjectPreUninstall(IProjectFacetActionEvent event) {
		if (JptCorePlugin.projectHasJpaFacet(event.getProject().getProject())) {
			this.jpaFacetedProjectPreUninstall(event);
		}
	}

	private void jpaFacetedProjectPreUninstall(IProjectFacetActionEvent event) {
		this.removeJpaProject(event.getProject().getProject());
	}

	/**
	 * This event is triggered when various, unknown, things happen to a
	 * faceted project, e.g.
	 *   - a closed project is opened
	 *   - a pre-existing project is imported
	 *   - one of a project's metadata files is modified directly
	 * Add a JPA project if the Eclipse project has a JPA facet but the JPA
	 * model does not have a corresponding JPA project. Remove the JPA
	 * project if the Eclipse project does not have a JPA facet but the JPA
	 * model does have a corresponding JPA project.
	 */
	private void facetedProjectModified(IProject project) {
		boolean jpaFacet = JptCorePlugin.projectHasJpaFacet(project);
		boolean jpaProject = this.jpaModel.containsJpaProject(project);

		if (jpaFacet) {
			if ( ! jpaProject) {  // facet added
				this.addJpaProject(project);
			}
		} else {
			if (jpaProject) {  // facet removed
				this.removeJpaProject(project);
			}
		}
	}


	// ********** Java element changed **********

	/**
	 * Forward the event to the JPA model.
	 */
	/* private */ synchronized void javaElementChanged(ElementChangedEvent event) {
		this.jpaModel.javaElementChanged(event);
	}


	// ********** preference changed **********

	/**
	 * When the "Default JPA Lib" preference changes,
	 * update the appropriate JDT Core classpath variable.
	 */
	/* private */ synchronized void preferenceChanged(PropertyChangeEvent event) {
		if (event.getProperty() == JpaPreferenceConstants.PREF_DEFAULT_JPA_LIB) {
			try {
				JavaCore.setClasspathVariable("DEFAULT_JPA_LIB", new Path((String) event.getNewValue()), null);
			} catch (JavaModelException ex) {
				this.log(ex);  // not sure what would cause this...
			}
		}
	}


	// ********** resource change listener **********

	/**
	 * Forward the Resource change event back to the JPA model manager.
	 */
	private class ResourceChangeListener implements IResourceChangeListener {
		ResourceChangeListener() {
			super();
		}
		public void resourceChanged(IResourceChangeEvent event) {
			JpaModelManager.this.resourceChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** faceted project listener **********

	/**
	 * Forward the Faceted project change event back to the JPA model manager.
	 */
	private class FacetedProjectListener implements IFacetedProjectListener {
		FacetedProjectListener() {
			super();
		}
		public void handleEvent(IFacetedProjectEvent event) {
			JpaModelManager.this.facetedProjectChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** Java element change listener **********

	/**
	 * Forward the Java element change event back to the JPA model manager.
	 */
	private class JavaElementChangeListener implements IElementChangedListener {
		JavaElementChangeListener() {
			super();
		}
		public void elementChanged(ElementChangedEvent event) {
			JpaModelManager.this.javaElementChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** preferences listener **********

	/**
	 * Forward the Preferences change event back to the JPA model manager.
	 */
	private class PreferencesListener implements IPropertyChangeListener {
		PreferencesListener() {
			super();
		}
		public void propertyChange(PropertyChangeEvent event) {
			JpaModelManager.this.preferenceChanged(event);
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

}
