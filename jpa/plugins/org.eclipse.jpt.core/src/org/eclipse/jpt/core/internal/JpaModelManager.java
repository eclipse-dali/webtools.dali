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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IOpenable;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jpt.core.internal.IJpaProject.Config;
import org.eclipse.jpt.core.internal.facet.IJpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.prefs.JpaPreferenceConstants;
import org.eclipse.jpt.core.internal.resource.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.orm.OrmArtifactEdit;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.utility.internal.BitTools;
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
 * and handled one at a time. Hopefully we don't cause any deadlocks.
 * 
 * Various things that cause us to add or remove a JPA project:
 * - Startup of the Dali plug-in will trigger all the JPA projects to be added
 * 
 * - Project created and facet installed
 *     facet POST_INSTALL
 * - Project facet uninstalled
 *     facet PRE_UNINSTALL
 * 
 * - Project opened
 *     facet PROJECT_MODIFIED
 * - Project closed
 *     facet PROJECT_MODIFIED
 * 
 * - Pre-existing project imported (created and opened)
 *     resource POST_CHANGE -> PROJECT -> CHANGED -> OPEN
 * - Project deleted
 *     resource PRE_DELETE
 * 
 * - Project facet installed by editing the facets settings file directly
 *     facet PROJECT_MODIFIED
 * - Project facet uninstalled by editing the facets settings file directly
 *     facet PROJECT_MODIFIED
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

	private static final JpaModelManager INSTANCE = new JpaModelManager();

	/**
	 * Return the singleton JPA model manager.
	 */
	public static final synchronized JpaModelManager instance() {
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
		debug("*** START JPA model manager ***");
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
		debug("*** STOP JPA model manager ***");
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

	/**
	 * Check whether the JPA facet has been added or removed.
	 */
	private void checkForTransition(IProject project) {
		boolean jpaFacet = JptCorePlugin.projectHasJpaFacet(project);
		boolean jpaProject = this.jpaModel.containsJpaProject(project);

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

	private void addJpaProject(IProject project) {
		this.jpaModel.addJpaProject(this.buildJpaProjectConfig(project));
	}

	/**
	 * Remove the JPA project corresponding to the specified Eclipse project.
	 * Do nothing if there is no corresponding JPA project.
	 */
	// TODO remove classpath items? persistence.xml? orm.xml?
	private void removeJpaProject(IProject project) {
		this.jpaModel.removeJpaProject(project);
	}


	// ********** resource changed **********

	/**
	 * Check for:
	 *   - project close/delete
	 *   - file add/remove
	 */
	//not synchronized, synchronizing once we know we are going to access JpaModel
	/* private */ void resourceChanged(IResourceChangeEvent event) {
		if (! (event.getSource() instanceof IWorkspace)) {
			return;  // this probably shouldn't happen...
		}
		switch (event.getType()){
			case IResourceChangeEvent.PRE_DELETE :  // project-only event
				this.resourcePreDelete(event);
				break;
			case IResourceChangeEvent.POST_CHANGE :
				this.resourcePostChange(event);
				break;
			case IResourceChangeEvent.PRE_CLOSE :  // project-only event
			case IResourceChangeEvent.PRE_BUILD :
			case IResourceChangeEvent.POST_BUILD :
			default :
				break;
		}
	}

	/**
	 * A project is being deleted. Remove its corresponding
	 * JPA project if appropriate.
	 */
	private void resourcePreDelete(IResourceChangeEvent event) {
		debug("Resource (Project) PRE_DELETE: " + event.getResource());
		this.removeJpaProject((IProject) event.getResource());
	}

	/**
	 * A resource has changed somehow.
	 * Check for files being added or removed.
	 * (The JPA project only handles added and removed files here, ignoring
	 * changed files.)
	 */
	private void resourcePostChange(IResourceChangeEvent event) {
		debug("Resource POST_CHANGE");
		this.synchronizeFiles(event.getDelta());
		this.checkForOpenedProjects(event.getDelta());
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

	/**
	 * Checked exceptions bite.
	 */
	private void synchronizeFiles(IProject project, IResourceDelta delta) {
		try {
			this.jpaModel.synchronizeFiles(project, delta);
		} catch (CoreException ex) {
			this.log(ex);  // problem traversing the project's resources - not much we can do
		}
	}

	/**
	 * Crawl the specified delta, looking for projects being opened.
	 * Projects being deleted are handled in IResourceChangeEvent.PRE_DELETE.
	 * Projects being closed are handled in IFacetedProjectEvent.Type.PROJECT_MODIFIED.
	 */
	private void checkForOpenedProjects(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.checkForOpenedProjects(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.checkForOpenedProject((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void checkForOpenedProjects(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.checkForOpenedProjects(deltas[i]);  // recurse
		}
	}

	private void checkForOpenedProject(IProject project, IResourceDelta delta) {
		switch (delta.getKind()) {
			case IResourceDelta.CHANGED : 
				this.checkForOpenedProject2(project, delta);
				break;
			case IResourceDelta.REMOVED :  // already handled with the PRE_DELETE event
			case IResourceDelta.ADDED :  // already handled with the facet POST_INSTALL event
			case IResourceDelta.ADDED_PHANTOM :  // ignore
			case IResourceDelta.REMOVED_PHANTOM :  // ignore
			default :
				break;
		}
	}

	/**
	 * We don't get any events from the Facets Framework when a pre-existing
	 * project is imported, so we need to check for the newly imported project here.
	 * 
	 * This event also occurs when a project is simply opened. Project opening
	 * also triggers a Facet PROJECT_MODIFIED event and that is where we add
	 * the JPA project, not here
	 */
	private synchronized void checkForOpenedProject2(IProject project, IResourceDelta delta) {
		if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.OPEN) && project.isOpen()) {
			debug("\tProject CHANGED - OPEN: " + project.getName());
			this.checkForTransition(project);
		}
	}


	// ********** faceted project changed **********

	/**
	 * Check for:
	 *   - install of JPA facet
	 *   - un-install of JPA facet
	 *   - any other appearance or disappearance of the JPA facet
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
		debug("Facet POST_INSTALL: " + event.getProjectFacet());
		if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
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

		// assume(?) this is the first event to indicate we need to add the JPA project to the JPA model
		this.addJpaProject(project);
	}

	private void createPersistenceXml(IProject project) {
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(project);
		PersistenceResource resource = pae.getResource(JptCorePlugin.persistenceXmlDeploymentURI(project));
		
		// 202811 - do not add content if it is already present
		if (resource.getPersistence() == null) {
			XmlPersistence persistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
			persistence.setVersion("1.0");
			XmlPersistenceUnit pUnit = PersistenceFactory.eINSTANCE.createXmlPersistenceUnit();
			pUnit.setName(project.getName());
			persistence.getPersistenceUnits().add(pUnit);
			resource.getContents().add(persistence);
			pae.save(null);
		}
		
		pae.dispose();
	}

	private void createOrmXml(IProject project) {
		OrmArtifactEdit oae =
				OrmArtifactEdit.getArtifactEditForWrite(project);
		OrmResource resource = oae.getResource(JptCorePlugin.ormXmlDeploymentURI(project));

		// 202811 - do not add content if it is already present
		if (resource.getEntityMappings() == null) {
			EntityMappings entityMappings = OrmFactory.eINSTANCE.createEntityMappings();
			entityMappings.setVersion("1.0");
			resource.getContents().add(entityMappings);
			oae.save(null);
		}
		
		oae.dispose();
	}

	private void facetedProjectPreUninstall(IProjectFacetActionEvent event) {
		debug("Facet PRE_UNINSTALL: " + event.getProjectFacet());
		if (event.getProjectFacet().getId().equals(JptCorePlugin.FACET_ID)) {
			this.jpaFacetedProjectPreUninstall(event);
		}
	}

	private void jpaFacetedProjectPreUninstall(IProjectFacetActionEvent event) {
		// assume(?) this is the first event to indicate we need to remove the JPA project to the JPA model
		this.removeJpaProject(event.getProject().getProject());
	}

	/**
	 * This event is triggered for any change to a faceted project.
	 * We use the event to watch for the following:
	 *   - an open project is closed
	 *   - a closed project is opened
	 *   - one of a project's (facet) metadata files is edited directly
	 */
	private void facetedProjectModified(IProject project) {
		debug("Facet PROJECT_MODIFIED: " + project.getName());
		this.checkForTransition(project);
	}


	// ********** Java element changed **********

	/**
	 * Forward the event to the JPA model.
	 */
	// not synchronized because of a conflict between facet install and 
	// java element change notifiation.  synchronize on JpaModel instead.
	/* private */ void javaElementChanged(ElementChangedEvent event) {
		if (isAddProjectNotOpenEvent(event)) {
			return;
		}
		this.jpaModel.javaElementChanged(event);
	}

	//209275 - This particular event only causes problems in a clean workspace the first time a JPA project
	//is created through the JPA wizard.  The second time a JPA project is created, this event occurs, but 
	//it occurs as the wizard is closing so it does not cause a deadlock.
	private boolean isAddProjectNotOpenEvent(ElementChangedEvent event) {
		IJavaElementDelta delta = event.getDelta();
		if (delta.getKind() == IJavaElementDelta.CHANGED) {
			if (delta.getElement().getElementType() == IJavaElement.JAVA_MODEL) {
				if (delta.getAffectedChildren().length == 1) {
					IJavaElementDelta childDelta = delta.getAffectedChildren()[0];
					if (childDelta.getKind() == IJavaElementDelta.ADDED) {
						if (childDelta.getElement().getElementType() == IJavaElement.JAVA_PROJECT) {
							if (childDelta.getAffectedChildren().length == 0) {
								if (!((IOpenable) childDelta.getElement()).isOpen()) {
									return true;
								}
							}
						}
					}
				}
			}
		}	
		return false;
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


	// ********** debug **********

	private static final boolean DEBUG = false;

	private static void debug(String message) {
		if (DEBUG) {
			System.out.println(Thread.currentThread().getName() + ": " + message);
		}
	}

}
