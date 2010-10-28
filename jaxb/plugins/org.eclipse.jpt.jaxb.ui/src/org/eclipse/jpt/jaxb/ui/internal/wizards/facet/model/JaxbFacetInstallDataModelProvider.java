/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.facet.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Set;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetInstallConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.wst.common.componentcore.datamodel.FacetInstallDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelPropertyDescriptor;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectEvent;
import org.eclipse.wst.common.project.facet.core.events.IFacetedProjectListener;

public class JaxbFacetInstallDataModelProvider
		extends FacetInstallDataModelProvider 
		implements JaxbFacetInstallDataModelProperties {
	
	protected static final DataModelPropertyDescriptor[] EMPTY_DMPD_ARRAY = new DataModelPropertyDescriptor[0];
	
	
	protected static final Comparator<DataModelPropertyDescriptor> DMPD_COMPARATOR =
			new Comparator<DataModelPropertyDescriptor>() {
				public int compare(DataModelPropertyDescriptor dmpd1, DataModelPropertyDescriptor dmpd2) {
					return dmpd1.getPropertyDescription().compareTo(dmpd2.getPropertyDescription());
				}
			};
	
	protected static IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}
	
	protected static IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptJaxbUiPlugin.PLUGIN_ID, message);
	}
	
	
	private JaxbFacetInstallConfig config;
	
	private PropertyChangeListener configListener;
	
	private IPropertyChangeListener libraryInstallDelegateListener;
	
	
	public JaxbFacetInstallDataModelProvider() {
		this(new JaxbFacetInstallConfig());
	}
	
	public JaxbFacetInstallDataModelProvider(JaxbFacetInstallConfig config) {
		super();
		this.config = config;
		this.configListener = buildConfigListener();
		this.config.addPropertyChangeListener(this.configListener);
		this.libraryInstallDelegateListener = buildLibraryInstallDelegateListener();
	}
	
	
	protected PropertyChangeListener buildConfigListener() {
		return new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(JaxbFacetInstallConfig.FACETED_PROJECT_WORKING_COPY_PROPERTY)) {
					
				}
				else if (evt.getPropertyName().equals(JaxbFacetInstallConfig.LIBRARY_INSTALL_DELEGATE_PROPERTY)) {
					LibraryInstallDelegate oldLid = (LibraryInstallDelegate) evt.getOldValue();
					if (oldLid != null) {
						oldLid.removeListener(JaxbFacetInstallDataModelProvider.this.libraryInstallDelegateListener);
					}
					LibraryInstallDelegate newLid = (LibraryInstallDelegate) evt.getNewValue();
					if (newLid != null) {
						newLid.addListener(JaxbFacetInstallDataModelProvider.this.libraryInstallDelegateListener);
					}
					setLibraryInstallDelegate(newLid);
				}
			}
		};
	}
	
	protected IPropertyChangeListener buildLibraryInstallDelegateListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					JaxbFacetInstallDataModelProvider.this.getDataModel().notifyPropertyChange(
							LIBRARY_INSTALL_DELEGATE, IDataModel.VALUE_CHG);
				}
			};
	}
	
	@Override
	public Set getPropertyNames() {
		Set names = super.getPropertyNames();
		names.add(JAXB_FACET_INSTALL_CONFIG);
		names.add(PLATFORM);
		names.add(LIBRARY_INSTALL_DELEGATE);
		return names;
	}
	
	@Override
	public void init() {
		super.init();
		getDataModel().setProperty(JAXB_FACET_INSTALL_CONFIG, this.config);
		if (this.config.getPlatform() != null) {
			getDataModel().setProperty(PLATFORM, this.config.getPlatform());
		}
		else {
			this.config.setPlatform(getDefaultPlatform());
		}
	}
	
	@Override
	public Object getDefaultProperty(String propertyName) {
		if (propertyName.equals(FACET_ID)) {
			return JaxbFacet.ID;
		}
		else if (propertyName.equals(PLATFORM)) {
			return getDefaultPlatform();
		}
		else if (propertyName.equals(LIBRARY_INSTALL_DELEGATE)) {
			// means that library install delegate has not been initialized
			LibraryInstallDelegate lid = this.config.getLibraryInstallDelegate();
			setLibraryInstallDelegate(lid);
			return lid;
		}
		
		return super.getDefaultProperty(propertyName);
	}
	
	protected JaxbPlatformDescription getDefaultPlatform() {
		return JptJaxbCorePlugin.getDefaultPlatform(getProjectFacetVersion());
	}
	
	@Override
	public boolean propertySet(String propertyName, Object propertyValue) {
		boolean ok = super.propertySet(propertyName, propertyValue);
		
		if (propertyName.equals(FACET_VERSION)) {
			this.model.notifyPropertyChange(PLATFORM, IDataModel.DEFAULT_CHG);
		}
		else if (propertyName.equals(FACETED_PROJECT_WORKING_COPY)) {
			getFacetedProjectWorkingCopy().addListener(
				new IFacetedProjectListener() {
					public void handleEvent(IFacetedProjectEvent event) {
						LibraryInstallDelegate lid = getLibraryInstallDelegate();
						if (lid != null) {
							// may be null while model is being built up
							// ... or in tests
							lid.refresh();
						}
					}
				},
				IFacetedProjectEvent.Type.PRIMARY_RUNTIME_CHANGED);
		}
		else if (propertyName.equals(JAXB_FACET_INSTALL_CONFIG)) {
			return false;
		}
		else if (propertyName.equals(PLATFORM)) {
			this.config.setPlatform((JaxbPlatformDescription) propertyValue);
		}
		else if (propertyName.equals(LIBRARY_INSTALL_DELEGATE)) {
			this.config.setLibraryInstallDelegate((LibraryInstallDelegate) propertyValue);
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
		Iterable<JaxbPlatformDescription> validPlatformDescriptions = buildValidPlatformDescriptions();
		Iterable<DataModelPropertyDescriptor> validPlatformDescriptors =
				new TransformationIterable<JaxbPlatformDescription, DataModelPropertyDescriptor>(validPlatformDescriptions) {
					@Override
					protected DataModelPropertyDescriptor transform(JaxbPlatformDescription description) {
						return buildPlatformDescriptor(description);
					}
				};
		return ArrayTools.sort(ArrayTools.array(validPlatformDescriptors, EMPTY_DMPD_ARRAY), DMPD_COMPARATOR);
	}
	
	protected Iterable<JaxbPlatformDescription> buildValidPlatformDescriptions() {
		return new FilteringIterable<JaxbPlatformDescription>(
				JptJaxbCorePlugin.getJaxbPlatformManager().getJaxbPlatforms()) {
			@Override
			protected boolean accept(JaxbPlatformDescription o) {
				return o.supportsJaxbFacetVersion(getProjectFacetVersion());
			}
		};
	}
	
	@Override
	public DataModelPropertyDescriptor getPropertyDescriptor(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return buildPlatformDescriptor(getPlatform());
		}
		
		return super.getPropertyDescriptor(propertyName);
	}
	
	protected DataModelPropertyDescriptor buildPlatformDescriptor(JaxbPlatformDescription desc) {
		return new DataModelPropertyDescriptor(desc, desc.getLabel());
	}
	
	@Override
	public IStatus validate(String propertyName) {
		if (propertyName.equals(PLATFORM)) {
			return validatePlatform();
		}
		if (propertyName.equals(LIBRARY_INSTALL_DELEGATE)) {
		    return getLibraryInstallDelegate().validate();
		}
		
		return super.validate(propertyName);
	}
	
	protected IStatus validatePlatform() {
		return (getPlatform() == null) ? 
				buildErrorStatus(JptJaxbUiMessages.JaxbFacetDataModel_validatePlatformNotSpecified) 
				: OK_STATUS;
	}
	
	protected IFacetedProjectWorkingCopy getFacetedProjectWorkingCopy() {
		return (IFacetedProjectWorkingCopy) this.config.getFacetedProjectWorkingCopy();
	}
	
	protected IProjectFacetVersion getProjectFacetVersion() {
		return (IProjectFacetVersion) this.config.getProjectFacetVersion();
	}
	
	protected JaxbPlatformDescription getPlatform() {
		return (JaxbPlatformDescription) getProperty(PLATFORM);
	}
	
	protected LibraryInstallDelegate getLibraryInstallDelegate() {
		return (LibraryInstallDelegate) getProperty(LIBRARY_INSTALL_DELEGATE);
	}
	
	protected void setLibraryInstallDelegate(LibraryInstallDelegate lid) {
		getDataModel().setProperty(LIBRARY_INSTALL_DELEGATE, lid);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.config.removePropertyChangeListener(this.configListener);
	}
}
