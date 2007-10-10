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
import org.eclipse.core.resources.IResourceDeltaVisitor;
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
 * and handled one at a time.
 */
public class JpaModelManager {

	/**
	 * The JPA model - null until the plug-in is started.
	 */
	private JpaModel jpaModel;

	/**
	 * Listen for changes to projects or files.
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
		return this.jpaModel().jpaProject(project);
	}

	/**
	 * Return the JPA file corresponding to the specified Eclipse file,
	 * or null if unable to associate the specified file with a JPA file.
	 */
	public IJpaFile jpaFile(IFile file) throws CoreException {
		IJpaProject jpaProject = this.jpaProject(file.getProject());
		return (jpaProject == null) ? null : jpaProject.jpaFile(file);
	}

	/**
	 * The JPA settings associated with the specified Eclipse project
	 * have changed in such a way as to require the associated
	 * JPA project to be completely rebuilt.
	 */
	public void rebuildJpaProject(IProject project) {
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

	Iterable<IJpaProject.Config> buildJpaProjectConfigs(Iterable<IProject> jpaFacetedProjects) {
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
	 * Visit the workspace resource tree, looking for open Eclipse projects
	 * with a JPA facet.
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
	private synchronized void removeJpaProject(IProject project) {
		if (this.jpaModel.removeJpaProject(project)) {
			try {
				// TODO remove classpath items?
				// TODO remove persistence.xml?
				// TODO copy to dispose()? where were the markers added in the first place???
				ResourcesPlugin.getWorkspace().deleteMarkers(project.findMarkers(JptCorePlugin.VALIDATION_MARKER_ID, true, IResource.DEPTH_INFINITE));
			} catch (CoreException ex) {
				this.log(ex);  // not much we can do
			}
		}
	}


	// ********** resource changed **********

	/**
	 * Check for project deletion/closing and file changes.
	 */
	synchronized void resourceChanged(IResourceChangeEvent event) {
		if (! (event.getSource() instanceof IWorkspace)) {
			return;  // this probably shouldn't happen...
		}
		switch (event.getType()){
			case IResourceChangeEvent.PRE_DELETE :
			case IResourceChangeEvent.PRE_CLOSE :
				this.projectPreDeleteOrClose(event);
				break;
			case IResourceChangeEvent.POST_CHANGE :
				this.resourcePostChange(event);
				break;
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
	 */
	private void resourcePostChange(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		// TODO 3 passes through the delta might be a bit much... but it keeps our code simpler...
		if (this.deltaIsRelevant(delta)) { // ignore SYNC and MARKER deltas
			this.checkForAddedOrOpenedProjects(delta);
			this.checkForAddedOrRemovedFiles(delta);
		}
	}

	/**
	 * Return whether the specified change delta contains anything relevant to
	 * the JPA model.
	 */
	private boolean deltaIsRelevant(IResourceDelta rootDelta) {
		if (rootDelta == null) {
			return false;
		}
		try {
			rootDelta.accept(RESOURCE_DELTA_VISITOR);
		} catch (RelevantDeltaFoundException ex) {
			return true;  // the visitor found a relevant delta and threw an exception
		} catch (CoreException ex) {
			this.log(ex);  // ignore delta if problems traversing - fall through and return false
		}
		return false;
	}

	/**
	 * Process the given delta and look for projects being added, opened, or closed.
	 * Note that projects being deleted are checked in deletingProject(IProject).
	 */
	private void checkForAddedOrOpenedProjects(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.checkForAddedOrOpenedProjects(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.checkForAddedOrOpenedProject((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void checkForAddedOrOpenedProjects(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.checkForAddedOrOpenedProjects(deltas[i]);  // recurse
		}
	}

	private void checkForAddedOrOpenedProject(IProject project, IResourceDelta delta) {
		if (JptCorePlugin.projectHasJpaFacet(project)) {
			this.checkForAddedOrOpenedJpaFacetedProject(project, delta);
		}
	}

	private void checkForAddedOrOpenedJpaFacetedProject(IProject project, IResourceDelta delta) {
		switch (delta.getKind()) {
			case IResourceDelta.REMOVED :
				break;  // already handled with the PRE_DELETE event
			case IResourceDelta.ADDED :  // adding and opening a project are treated alike
			case IResourceDelta.CHANGED : 
				if (BitTools.flagIsSet(delta.getFlags(), IResourceDelta.OPEN) && project.isOpen()) {
					this.addJpaProject(project);
				}
				break;
			case IResourceDelta.ADDED_PHANTOM :
			case IResourceDelta.REMOVED_PHANTOM :
			default :
				break;
		}
	}

	/**
	 * Check for files being added or removed.
	 * (The JPA project only handles added and removed files here, ignoring
	 * changed files.)
	 */
	private void checkForAddedOrRemovedFiles(IResourceDelta delta) {
		IResource resource = delta.getResource();
		switch (resource.getType()) {
			case IResource.ROOT :
				this.checkForAddedOrRemovedFiles(delta.getAffectedChildren());  // recurse
				break;
			case IResource.PROJECT :
				this.checkForAddedOrRemovedFiles((IProject) resource, delta);
				break;
			case IResource.FILE :
			case IResource.FOLDER :
			default :
				break;
		}
	}

	private void checkForAddedOrRemovedFiles(IResourceDelta[] deltas) {
		for (int i = 0; i < deltas.length; i++) {
			this.checkForAddedOrRemovedFiles(deltas[i]);  // recurse
		}
	}

	private void checkForAddedOrRemovedFiles(IProject project, IResourceDelta delta) {
		if (JptCorePlugin.projectHasJpaFacet(project)) {
			this.checkForAddedOrRemovedJpaFiles(project, delta);
		}
	}

	/**
	 * Checked exceptions bite.
	 */
	private void checkForAddedOrRemovedJpaFiles(IProject project, IResourceDelta delta) {
		try {
			this.jpaModel.checkForAddedOrRemovedJpaFiles(project, delta);
		} catch (CoreException ex) {
			this.log(ex);  // problem traversing the project's resources - not much we can do
		}
	}


	// ********** resource delta visitor **********

	/**
	 * The visitor is stateless, so we only need one.
	 */
	private static final IResourceDeltaVisitor RESOURCE_DELTA_VISITOR = new ResourceDeltaVisitor();

	/**
	 * Traverse the resource delta tree and throw a special exception if any
	 * relevant nodes are encountered.
	 */
	private static class ResourceDeltaVisitor implements IResourceDeltaVisitor {
		// the exception is only used to short-circuit the traversal, so we only need one
		private static final RelevantDeltaFoundException EX = new RelevantDeltaFoundException();

		ResourceDeltaVisitor() {
			super();
		}

		public boolean visit(IResourceDelta delta) {
			switch (delta.getKind()) {
				case IResourceDelta.ADDED :
				case IResourceDelta.REMOVED :
					throw EX;
				case IResourceDelta.CHANGED :
					if (this.changedDeltaIsRelevant(delta)) {
						throw EX;
					}
					break;
				case IResourceDelta.ADDED_PHANTOM :
				case IResourceDelta.REMOVED_PHANTOM :
				default :
					break;
			}
			return true;  // continue visit to any children
		}

		/**
		 * The specified "changed" delta is relevant if it is a leaf node
		 * (i.e. it has no children) and any flag, other than the SYNC or
		 * MARKERS flags, is set.
		 */
		private boolean changedDeltaIsRelevant(IResourceDelta delta) {
			return (delta.getAffectedChildren().length == 0)
					&& BitTools.anyFlagsAreSet(delta.getFlags(), RELEVANT_FLAGS);
		}

		private static final int IRRELEVANT_FLAGS = IResourceDelta.SYNC | IResourceDelta.MARKERS;
		private static final int RELEVANT_FLAGS = ~IRRELEVANT_FLAGS;

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}

	/**
	 * Internal exception to quickly escape from a delta traversal.
	 */
	private static class RelevantDeltaFoundException extends RuntimeException {
		RelevantDeltaFoundException() {
			super();
		}
		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}
	}


	// ********** faceted project changed **********

	synchronized void facetedProjectChanged(IFacetedProjectEvent event) {
		if (event.getType() == IFacetedProjectEvent.Type.POST_INSTALL) {
			this.postInstall((IProjectFacetActionEvent) event);
		} else if (event.getType() == IFacetedProjectEvent.Type.PRE_UNINSTALL) {
			this.preUninstall((IProjectFacetActionEvent) event);
		} else {
			this.checkJpaFacet(event.getProject().getProject());
		}
	}

	private void postInstall(IProjectFacetActionEvent event) {
		if (JptCorePlugin.projectHasJpaFacet(event.getProject().getProject())) {
			this.postInstallJpaFacetedProject(event);
		}
	}

	private void postInstallJpaFacetedProject(IProjectFacetActionEvent event) {
		IProject project = event.getProject().getProject();
		IDataModel dataModel = (IDataModel) event.getActionConfig();

		this.createPersistenceXml(project);

		if (dataModel.getBooleanProperty(IJpaFacetDataModelProperties.CREATE_ORM_XML)) {
			this.createOrmXml(project);
		}

		this.addJpaProject(project);
	}

	private void preUninstall(IProjectFacetActionEvent event) {
		if (JptCorePlugin.projectHasJpaFacet(event.getProject().getProject())) {
			this.preUninstallJpaFacetedProject(event);
		}
	}

	private void preUninstallJpaFacetedProject(IProjectFacetActionEvent event) {
		this.removeJpaProject(event.getProject().getProject());
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

	/**
	 * Add a JPA project if the Eclipse project has a JPA facet but the JPA
	 * model does not have a corresponding JPA project. Remove the JPA
	 * project if the Eclipse project does not have a JPA facet but the JPA
	 * model does have a corresponding JPA project.
	 */
	private void checkJpaFacet(IProject project) {
		boolean jpaFacet = JptCorePlugin.projectHasJpaFacet(project);
		boolean jpaProject = this.jpaModel.containsJpaProject(project);

		if (jpaFacet) {
			if ( ! jpaProject) {  // facet added?
				this.addJpaProject(project);
			}
		} else {  // facet removed?
			if (jpaProject) {
				this.removeJpaProject(project);
			}
		}
	}


	// ********** Java element changed **********

	/**
	 * Forward the event to the JPA model.
	 */
	synchronized void javaElementChanged(ElementChangedEvent event) {
		this.jpaModel.javaElementChanged(event);
	}


	// ********** preference changed **********

	/**
	 * When the "Default JPA Lib" preference changes,
	 * update the appropriate classpath variable.
	 * This method can probably get by without being synchronized.
	 */
	void preferenceChanged(PropertyChangeEvent event) {
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
