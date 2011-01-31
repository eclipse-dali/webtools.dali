/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.properties.JptProjectPropertiesPage;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
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
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import com.ibm.icu.text.Collator;

/**
 * Way more complicated UI than you would think....
 */
public class JaxbProjectPropertiesPage
		extends JptProjectPropertiesPage {
	
	public static final String PROP_ID = "org.eclipse.jpt.jaxb.ui.jaxbProjectPropertiesPage"; //$NON-NLS-1$
	
	private PropertyValueModel<JaxbProject> jaxbProjectModel;
	
	private BufferedWritablePropertyValueModel<JaxbPlatformDescription> platformModel;
	private PropertyChangeListener platformListener;
	
	
	@SuppressWarnings("unchecked")
	/* private */ static final Comparator<String> STRING_COMPARATOR = Collator.getInstance();
	
	
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
	
	private BufferedWritablePropertyValueModel<JaxbPlatformDescription> buildPlatformModel() {
		return new BufferedWritablePropertyValueModel<JaxbPlatformDescription>(
				new PlatformModel(this.jaxbProjectModel), this.trigger);
	}
	
	private PropertyChangeListener buildPlatformListener(){
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				JaxbProjectPropertiesPage.this.platformChanged((JaxbPlatformDescription) event.getNewValue());
			}
		};
	}
	
	void platformChanged(JaxbPlatformDescription newPlatform) {
		if ( ! this.getControl().isDisposed()) {
			// handle null, in the case the jpa facet is changed via the facets page,
			// the library install delegate is temporarily null
			adjustLibraryProviders();
		}
	}
	
	
	// ********** LibraryFacetPropertyPage implementation **********
	
	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		return this.getFacetedProject().getInstalledVersion(JaxbFacet.FACET);
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
				jaxbConfig.setJaxbPlatform(this.platformModel.getValue());
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
				JptJaxbUiMessages.JaxbFacetWizardPage_jaxbImplementationLabel);
		
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
		group.setText(JptJaxbUiMessages.JaxbFacetWizardPage_platformLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Combo platformDropDown = this.buildDropDown(group);
		SWTTools.bind(
				buildPlatformChoicesModel(),
				this.platformModel,
				platformDropDown,
				JAXB_PLATFORM_LABEL_CONVERTER);
		
		buildFacetsPageLink(group, JptJaxbUiMessages.JaxbFacetWizardPage_facetsPageLink);
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
	private ListValueModel<JaxbPlatformDescription> buildPlatformChoicesModel() {
		return new SortedListValueModelAdapter<JaxbPlatformDescription>(
				new SetCollectionValueModel<JaxbPlatformDescription>(
						new CompositeCollectionValueModel<CollectionValueModel<JaxbPlatformDescription>, JaxbPlatformDescription>(
								new PropertyCollectionValueModelAdapter<JaxbPlatformDescription>(this.platformModel),
								buildRegistryPlatformsModel())),
				JAXB_PLATFORM_COMPARATOR);
	}
	
	private CollectionValueModel<JaxbPlatformDescription> buildRegistryPlatformsModel() {
		Iterable<JaxbPlatformDescription> enabledPlatforms = 
			new FilteringIterable<JaxbPlatformDescription>(
					JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatforms()) {
				@Override
				protected boolean accept(JaxbPlatformDescription o) {
					return o.supportsJaxbFacetVersion(getProjectFacetVersion());
				}
			};
		return new StaticCollectionValueModel<JaxbPlatformDescription>(enabledPlatforms);
	}
	
	private static final Comparator<JaxbPlatformDescription> JAXB_PLATFORM_COMPARATOR =
			new Comparator<JaxbPlatformDescription>() {
				public int compare(JaxbPlatformDescription desc1, JaxbPlatformDescription desc2) {
					return STRING_COMPARATOR.compare(desc1.getLabel(), desc2.getLabel());
				}
			};
	
	private static final StringConverter<JaxbPlatformDescription> JAXB_PLATFORM_LABEL_CONVERTER =
			new StringConverter<JaxbPlatformDescription>() {
				public String convertToString(JaxbPlatformDescription desc) {
					return desc.getLabel();
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
		JptJaxbCorePlugin.getProjectManager().rebuildJaxbProject(getProject());
	}
	
	@Override
	protected BufferedWritablePropertyValueModel<?>[] buildBufferedModels() {
		return new BufferedWritablePropertyValueModel[] {
			this.platformModel
		};
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
		super.performValidation(statuses);
	}
	
	
	// ********** UI model adapters **********
	
	/**
	 * Treat the JAXB platform as an "aspect" of the JAXB project.
	 * The platform ID is stored in the project preferences.
	 * The platform ID does not change for a JAXB project - if the user wants a
	 * different platform, we build an entirely new JAXB project.
	 */
	static class PlatformModel
			extends AspectPropertyValueModelAdapter<JaxbProject, JaxbPlatformDescription> {
		
		PlatformModel(PropertyValueModel<JaxbProject> jaxbProjectModel) {
			super(jaxbProjectModel);
		}
		
		@Override
		protected JaxbPlatformDescription buildValue_() {
			return this.subject.getPlatform().getDescription();
		}
		
		@Override
		public void setValue_(JaxbPlatformDescription newPlatform) {
			JptJaxbCorePlugin.setJaxbPlatform(this.subject.getProject(), newPlatform);
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
