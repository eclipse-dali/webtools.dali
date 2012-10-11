/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.jaxb.core.internal.plugin.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.libprov.JaxbLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformManager;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;


public abstract class JaxbFacetDataModelProvider
		extends FacetInstallDataModelProvider 
		implements JaxbFacetDataModelProperties {
	
	protected static final DataModelPropertyDescriptor[] EMPTY_DMPD_ARRAY = new DataModelPropertyDescriptor[0];
	
	
	protected static final Comparator<DataModelPropertyDescriptor> DMPD_COMPARATOR =
			new Comparator<DataModelPropertyDescriptor>() {
				public int compare(DataModelPropertyDescriptor dmpd1, DataModelPropertyDescriptor dmpd2) {
					return dmpd1.getPropertyDescription().compareTo(dmpd2.getPropertyDescription());
				}
			};
	
	
	
	// listens to primary runtime changing
	private IFacetedProjectListener fprojListener;
	
	private LibraryInstallDelegate defaultLibraryInstallDelegate;
	
	
	protected JaxbFacetDataModelProvider() {
		super();
		this.fprojListener = buildFprojListener();
	}
	
	
	protected IFacetedProjectListener buildFprojListener() {
		return new IFacetedProjectListener() {
			public void handleEvent(IFacetedProjectEvent event) {
				getLibraryInstallDelegate().refresh();
			}
		};
	}
	
	@Override
	public Set getPropertyNames() {
		Set names = super.getPropertyNames();
		names.add(PLATFORM);
		names.add(LIBRARY_INSTALL_DELEGATE);
		return names;
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FACET_ID)) {
			return JaxbFacet.ID;
		}
		else if (propertyName.equals(PLATFORM)) {
			return getDefaultPlatformConfig();
		}
		else if (propertyName.equals(LIBRARY_INSTALL_DELEGATE)) {
			return getDefaultLibraryInstallDelegate();
		}
		
		return super.getDefaultProperty(propertyName);
	}
	
	protected JaxbPlatformConfig getDefaultPlatformConfig() {
		return this.getJaxbPlatformManager().getDefaultJaxbPlatformConfig(getProjectFacetVersion());
	}
	
	protected LibraryInstallDelegate getDefaultLibraryInstallDelegate() {
		// delegate itself changes only when facet version changes
		if (this.defaultLibraryInstallDelegate == null) {
			this.defaultLibraryInstallDelegate = buildDefaultLibraryInstallDelegate();
		}
		else if (! this.defaultLibraryInstallDelegate.getProjectFacetVersion().equals(getProjectFacetVersion())) {
			this.defaultLibraryInstallDelegate.dispose();
			this.defaultLibraryInstallDelegate = buildDefaultLibraryInstallDelegate();
		}
		return defaultLibraryInstallDelegate;
	}
	
	protected LibraryInstallDelegate buildDefaultLibraryInstallDelegate() {
		IFacetedProjectWorkingCopy fpjwc = getFacetedProjectWorkingCopy();
		IProjectFacetVersion pfv = getProjectFacetVersion();
		if (fpjwc == null || pfv == null) {
			return null;
		}
		LibraryInstallDelegate lid = new LibraryInstallDelegate(fpjwc, pfv);
		lid.addListener(buildLibraryInstallDelegateListener());
		return lid;
	}
	
	protected IPropertyChangeListener buildLibraryInstallDelegateListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					if (LibraryInstallDelegate.PROP_AVAILABLE_PROVIDERS.equals(property)) {
						adjustLibraryInstallDelegate();
					}
					getDataModel().notifyPropertyChange(LIBRARY_INSTALL_DELEGATE, IDataModel.VALUE_CHG);
				}
			};
	}
	
	protected void adjustLibraryInstallDelegate() {
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
				jaxbConfig.setJaxbPlatformConfig(getPlatformConfig());
			}
		}
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		
		if (propertyName.equals(FACETED_PROJECT_WORKING_COPY)) {
			// should only be done once
			IFacetedProjectWorkingCopy fproj = (IFacetedProjectWorkingCopy) propertyValue;
			fproj.addListener(this.fprojListener, IFacetedProjectEvent.Type.PRIMARY_RUNTIME_CHANGED);
		}
		else if (propertyName.equals(FACET_VERSION)) {
			adjustLibraryInstallDelegate();
			this.model.notifyPropertyChange(PLATFORM, IDataModel.DEFAULT_CHG);
			this.model.notifyPropertyChange(LIBRARY_INSTALL_DELEGATE, IDataModel.DEFAULT_CHG);
		}
		else if (propertyName.equals(PLATFORM)) {
			adjustLibraryInstallDelegate();
		}
		
		return ok;
	}
	
	@Override
	public DataModelPropertyDescriptor[] getValidPropertyDescriptors(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return this.buildValidPlatformDescriptors();
		}
		
		return super.getValidPropertyDescriptors(propertyName);
	}
	
	protected DataModelPropertyDescriptor[] buildValidPlatformDescriptors() {
		Iterable<JaxbPlatformConfig> validPlatformConfigs = buildValidPlatformConfigs();
		Iterable<DataModelPropertyDescriptor> validPlatformDescriptors =
				new TransformationIterable<JaxbPlatformConfig, DataModelPropertyDescriptor>(validPlatformConfigs) {
					@Override
					protected DataModelPropertyDescriptor transform(JaxbPlatformConfig config) {
						return buildPlatformDescriptor(config);
					}
				};
		return ArrayTools.sort(ArrayTools.array(validPlatformDescriptors, EMPTY_DMPD_ARRAY), DMPD_COMPARATOR);
	}
	
	protected Iterable<JaxbPlatformConfig> buildValidPlatformConfigs() {
		return new FilteringIterable<JaxbPlatformConfig>(this.getJaxbPlatformManager().getJaxbPlatformConfigs()) {
			@Override
			protected boolean accept(JaxbPlatformConfig o) {
				return o.supportsJaxbFacetVersion(getProjectFacetVersion());
			}
		};
	}
	
	protected JaxbPlatformManager getJaxbPlatformManager() {
		return this.getJaxbWorkspace().getJaxbPlatformManager();
	}

	protected JaxbWorkspace getJaxbWorkspace() {
		return (JaxbWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JaxbWorkspace.class);
	}

	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return buildPlatformDescriptor(getPlatformConfig());
		}
		
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor buildPlatformDescriptor(JaxbPlatformConfig config) {
		return new DataModelPropertyDescriptor(config, config.getLabel());
	}
	
	// ********** validation **********
	
	protected static IStatus OK_STATUS = Status.OK_STATUS;
	
	protected static IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}
	
	protected static IStatus buildStatus(int severity, String message) {
		return JptJaxbCorePlugin.instance().buildStatus(severity, message);
	}
	
	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return this.validatePlatform();
		}
		else if (propertyName.equals(LIBRARY_INSTALL_DELEGATE)) {
			return getLibraryInstallDelegate().validate();
		}
		
		return super.validate(propertyName);
	}
	
	protected IStatus validatePlatform() {
		return (getPlatformConfig() == null) ? 
				buildErrorStatus(JptJaxbCoreMessages.JaxbFacetConfig_validatePlatformNotSpecified) 
				: OK_STATUS;
	}
	
	protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) getProperty(FACETED_PROJECT_WORKING_COPY);
	}
	
	protected IProjectFacetVersion getProjectFacetVersion() {
		return (IProjectFacetVersion) getProperty(FACET_VERSION);
	}
	
	protected JaxbPlatformConfig getPlatformConfig() {
		return (JaxbPlatformConfig) getProperty(PLATFORM);
	}
	
	protected LibraryInstallDelegate getLibraryInstallDelegate() {
		return (LibraryInstallDelegate) getProperty(LIBRARY_INSTALL_DELEGATE);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		getFacetedProjectWorkingCopy().removeListener(this.fprojListener);
		this.defaultLibraryInstallDelegate.dispose();
	}
}
