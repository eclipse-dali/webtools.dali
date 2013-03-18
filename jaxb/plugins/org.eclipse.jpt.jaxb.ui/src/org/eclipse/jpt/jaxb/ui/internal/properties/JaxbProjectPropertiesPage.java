/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.properties;

import static org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi.createInstallLibraryPanel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jpt.common.core.internal.utility.ICUStringCollator;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.properties.JptProjectPropertiesPage;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.BufferedModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jaxb.core.JaxbPreferences;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JaxbProjectManager;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jpt.jaxb.ui.JaxbWorkbench;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Way more complicated UI than you would think....
 */
public class JaxbProjectPropertiesPage
		extends JptProjectPropertiesPage {
	
	public static final String PROP_ID = "org.eclipse.jpt.jaxb.ui.jaxbProjectPropertiesPage"; //$NON-NLS-1$
	
	private PropertyValueModel<JaxbProject> jaxbProjectModel;
	
	private BufferedModifiablePropertyValueModel<JaxbPlatformConfig> platformModel;
	private PropertyChangeListener platformListener;
	
	/* private */ static final Comparator<String> STRING_COMPARATOR = new ICUStringCollator();
	
	// ************ construction ************
	
	public JaxbProjectPropertiesPage() {
		super();
	}
	
	
	@Override
	protected void buildModels() {
		this.jaxbProjectModel = new JaxbProjectModel(this.projectModel);
		
		this.platformModel = this.buildPlatformModel();
		this.platformListener = this.buildPlatformListener();
	}
	
	
	// ***** platform ID model
	
	private BufferedModifiablePropertyValueModel<JaxbPlatformConfig> buildPlatformModel() {
		return new BufferedModifiablePropertyValueModel<JaxbPlatformConfig>(
				new PlatformModel(this.jaxbProjectModel), this.trigger);
	}
	
	private PropertyChangeListener buildPlatformListener(){
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				JaxbProjectPropertiesPage.this.platformChanged((JaxbPlatformConfig) event.getNewValue());
			}
		};
	}
	
	void platformChanged(JaxbPlatformConfig newPlatform) {
		if ( ! this.getControl().isDisposed()) {
			// handle null, in the case the jpa facet is changed via the facets page,
			// the library install delegate is temporarily null
			adjustLibraryProviders();
		}
	}
	
	
	// ********** LibraryFacetPropertyPage implementation **********
	
	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		return this.getFacetedProject().getInstalledVersion(JaxbProject.FACET);
	}
	
	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(IFacetedProject project, IProjectFacetVersion fv) {
		LibraryInstallDelegate lid = new LibraryInstallDelegate(project, fv);
		lid.addListener(buildLibraryProviderListener());
		return lid;
	}
	
	@Override
	protected void adjustLibraryProviders() {
		LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
		if (lid != null) {
			List<JaxbLibraryProviderInstallOperationConfig> jaxbConfigs 
					= new ArrayList<JaxbLibraryProviderInstallOperationConfig>();
			// add the currently selected one first
			JaxbLibraryProviderInstallOperationConfig currentJaxbConfig = null;
			LibraryProviderOperationConfig config = lid.getLibraryProviderOperationConfig();
			if (config instanceof JaxbLibraryProviderInstallOperationConfig) {
				currentJaxbConfig = (JaxbLibraryProviderInstallOperationConfig) config;
				jaxbConfigs.add(currentJaxbConfig);
			}
			for (ILibraryProvider lp : lid.getLibraryProviders()) {
				config = lid.getLibraryProviderOperationConfig(lp);
				if (config instanceof JaxbLibraryProviderInstallOperationConfig
						&& ! config.equals(currentJaxbConfig)) {
					jaxbConfigs.add((JaxbLibraryProviderInstallOperationConfig) config);
				}
			}
			for (JaxbLibraryProviderInstallOperationConfig jaxbConfig : jaxbConfigs) {
				jaxbConfig.setJaxbPlatformConfig(this.platformModel.getValue());
			}
		}
	}
	
	
	// ********** page **********
	
	@Override
	protected void createWidgets(Composite parent) {	
		buildPlatformGroup(parent);
		
		Control libraryProviderComposite = createInstallLibraryPanel(
				parent, 
				getLibraryInstallDelegate(), 
				JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_JAXB_IMPLEMENTATION_LABEL);
		
 		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
 		
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JaxbHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
	}
	
	@Override
	protected void engageListeners() {
		super.engageListeners();
		this.platformModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.platformListener);
	}
	
	@Override
	public void disengageListeners() {
		this.platformModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.platformListener);
		super.disengageListeners();
	}
	
	
	// ********** platform group **********
	
	private void buildPlatformGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_PLATFORM_LABEL);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Combo platformDropDown = this.buildDropDown(group);
		SWTTools.bind(
				buildPlatformChoicesModel(),
				this.platformModel,
				platformDropDown,
				JaxbPlatformConfig.LABEL_TRANSFORMER);
		
		buildFacetsPageLink(group, JptJaxbUiMessages.JAXB_FACET_WIZARD_PAGE_FACETS_PAGE_LINK);
	}
	
	/**
	 * Add the project's JAXB platform if it is not on the list of valid
	 * platforms.
	 * <p>
	 * This is probably only useful if the project is corrupted
	 * and has a platform that exists in the registry but is not on the
	 * list of valid platforms for the project's JAXB facet version.
	 * Because, if the project's JAXB platform is completely invalid, there
	 * would be no JAXB project!
	 */
	@SuppressWarnings("unchecked")
	private ListValueModel<JaxbPlatformConfig> buildPlatformChoicesModel() {
		return new SortedListValueModelAdapter<JaxbPlatformConfig>(
				new SetCollectionValueModel<JaxbPlatformConfig>(
						CompositeCollectionValueModel.forModels(
								new PropertyCollectionValueModelAdapter<JaxbPlatformConfig>(this.platformModel),
								buildRegistryPlatformsModel())),
				JAXB_PLATFORM_CONFIG_COMPARATOR);
	}
	
	private CollectionValueModel<JaxbPlatformConfig> buildRegistryPlatformsModel() {
		Iterable<JaxbPlatformConfig> enabledPlatforms = 
			IterableTools.filter(
					this.getJaxbPlatformConfigs(),
					new JaxbPlatformConfig.SupportsJaxbFacetVersion(getProjectFacetVersion()));
		return new StaticCollectionValueModel<JaxbPlatformConfig>(enabledPlatforms);
	}

	private Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs() {
		JaxbPlatformManager jaxbPlatformManager = this.getJaxbPlatformManager();
		return (jaxbPlatformManager != null) ? jaxbPlatformManager.getJaxbPlatformConfigs() : IterableTools.<JaxbPlatformConfig>emptyIterable();
	}
	
	private static final Comparator<JaxbPlatformConfig> JAXB_PLATFORM_CONFIG_COMPARATOR =
			new Comparator<JaxbPlatformConfig>() {
				public int compare(JaxbPlatformConfig desc1, JaxbPlatformConfig desc2) {
					return STRING_COMPARATOR.compare(desc1.getLabel(), desc2.getLabel());
				}
			};
	
	
	// ********** OK/Revert/Apply behavior **********
	
	@Override
	protected boolean projectRebuildRequired() {
		return this.platformModel.isBuffering();
	}
	
	@Override
	protected void rebuildProject() {
		// if the JAXB platform is changed, we need to completely rebuild the JAXB project
		JaxbProjectManager jaxbProjectManager = this.getJaxbProjectManager();
		if (jaxbProjectManager != null) {
			jaxbProjectManager.rebuildJaxbProject(getProject());
		}
	}
	
	@Override
	protected BufferedModifiablePropertyValueModel<?>[] buildBufferedModels() {
		return new BufferedModifiablePropertyValueModel[] {
			this.platformModel
		};
	}
	
	private JaxbPlatformManager getJaxbPlatformManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbPlatformManager();
	}

	private JaxbProjectManager getJaxbProjectManager() {
		JaxbWorkspace jaxbWorkspace = this.getJaxbWorkspace();
		return (jaxbWorkspace == null) ? null : jaxbWorkspace.getJaxbProjectManager();
	}

	private JaxbWorkspace getJaxbWorkspace() {
		JaxbWorkbench jaxbWorkbench = this.getJaxbWorkbench();
		return (jaxbWorkbench == null) ? null : jaxbWorkbench.getJaxbWorkspace();
	}

	private JaxbWorkbench getJaxbWorkbench() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JaxbWorkbench.class);
	}

	
	// ********** validation **********
	
	@Override
	protected Model[] buildValidationModels() {
		return new Model[] {
			platformModel
		};
	}
	
	@Override
	protected void performValidation(Map<Integer, ArrayList<IStatus>> statuses) {
		/* platform */
		// user is unable to unset the platform, so no validation necessary
		
		/* library provider */
		// default validation is OK
	}
	
	
	// ********** UI model adapters **********
	
	/**
	 * Treat the JAXB platform as an "aspect" of the JAXB project.
	 * The platform ID is stored in the project preferences.
	 * The platform ID does not change for a JAXB project - if the user wants a
	 * different platform, we build an entirely new JAXB project.
	 */
	static class PlatformModel
			extends AspectPropertyValueModelAdapter<JaxbProject, JaxbPlatformConfig> {
		
		PlatformModel(PropertyValueModel<JaxbProject> jaxbProjectModel) {
			super(jaxbProjectModel);
		}
		
		@Override
		protected JaxbPlatformConfig buildValue_() {
			return this.subject.getPlatform().getConfig();
		}
		
		@Override
		public void setValue_(JaxbPlatformConfig newPlatform) {
			JaxbPreferences.setJaxbPlatformID(this.subject.getProject(), newPlatform.getId());
		}
		
		@Override
		protected void engageSubject_() {
			// the platform ID does not change
		}
		
		@Override
		protected void disengageSubject_() {
			// the platform ID does not change
		}
	}
}
