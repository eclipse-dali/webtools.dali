/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.facet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.wst.common.project.facet.core.ActionConfig;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class JaxbFacetInstallConfig
		extends ActionConfig {
	
	private PropertyChangeSupport changeSupport;
	
	public static final String FACETED_PROJECT_WORKING_COPY_PROPERTY = "facetedProjectWorkingCopy";
	
	public static final String PROJECT_FACET_VERSION_PROPERTY = "projectFacetVersion";
	
	public static final String PLATFORM_PROPERTY = "platform";
	private JaxbPlatformDescription platform;
	
	public static final String LIBRARY_INSTALL_DELEGATE_PROPERTY = "libraryInstallDelegate";
	private LibraryInstallDelegate libraryInstallDelegate;
	
	
	
	public JaxbFacetInstallConfig() {
		super();
		this.changeSupport = new PropertyChangeSupport(this);
	}
	
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(listener);
	}
	
	@Override
	public void setFacetedProjectWorkingCopy(IFacetedProjectWorkingCopy fpjwc) {
		IFacetedProjectWorkingCopy oldFpjwc = getFacetedProjectWorkingCopy();
		super.setFacetedProjectWorkingCopy(fpjwc);
		this.changeSupport.firePropertyChange(FACETED_PROJECT_WORKING_COPY_PROPERTY, oldFpjwc, fpjwc);
	}
	
	@Override
	public void setProjectFacetVersion(IProjectFacetVersion fv) {
		IProjectFacetVersion oldFv = getProjectFacetVersion();
		super.setProjectFacetVersion(fv);
		this.changeSupport.firePropertyChange(PROJECT_FACET_VERSION_PROPERTY, oldFv, fv);
		adjustLibraryProviders();
		if (getLibraryInstallDelegate().getProjectFacetVersion().equals(getProjectFacetVersion())) {
			getLibraryInstallDelegate().dispose();
			setLibraryInstallDelegate(buildLibraryInstallDelegate());
		}
	}
	
	public JaxbPlatformDescription getPlatform() {
		return this.platform;
	}
	
	public void setPlatform(JaxbPlatformDescription platform) {
		JaxbPlatformDescription oldPlatform = this.platform;
		this.platform = platform;
		this.changeSupport.firePropertyChange(PLATFORM_PROPERTY, oldPlatform, platform);
		adjustLibraryProviders();
	}
	
	public LibraryInstallDelegate getLibraryInstallDelegate() {
		if (this.libraryInstallDelegate == null) {
			this.libraryInstallDelegate = buildLibraryInstallDelegate();
		}
		return this.libraryInstallDelegate;
	}
	
	protected  LibraryInstallDelegate buildLibraryInstallDelegate() {
		IFacetedProjectWorkingCopy fpjwc = this.getFacetedProjectWorkingCopy();
		if (fpjwc == null) {
			return null;
		}
		IProjectFacetVersion pfv = this.getProjectFacetVersion();
		if (pfv == null) {
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
						adjustLibraryProviders();
					}
				}
			};
	}
	
	protected void adjustLibraryProviders() {
		LibraryInstallDelegate lid = getLibraryInstallDelegate();
		if (lid != null) {
//			List<JpaLibraryProviderInstallOperationConfig> jpaConfigs 
//					= new ArrayList<JpaLibraryProviderInstallOperationConfig>();
//			// add the currently selected one first
//			JpaLibraryProviderInstallOperationConfig currentJpaConfig = null;
//			LibraryProviderOperationConfig config = lid.getLibraryProviderOperationConfig();
//			if (config instanceof JpaLibraryProviderInstallOperationConfig) {
//				currentJpaConfig = (JpaLibraryProviderInstallOperationConfig) config;
//				jpaConfigs.add(currentJpaConfig);
//			}
//			for (ILibraryProvider lp : lid.getLibraryProviders()) {
//				config = lid.getLibraryProviderOperationConfig(lp);
//				if (config instanceof JpaLibraryProviderInstallOperationConfig
//						&& ! config.equals(currentJpaConfig)) {
//					jpaConfigs.add((JpaLibraryProviderInstallOperationConfig) config);
//				}
//			}
//			for (JpaLibraryProviderInstallOperationConfig jpaConfig : jpaConfigs) {
//				jpaConfig.setJpaPlatformId(getPlatformId());
//			}
		}
	}
	
	public void setLibraryInstallDelegate(LibraryInstallDelegate libraryInstallDelegate) {
		LibraryInstallDelegate oldLibraryInstallDelegate = this.libraryInstallDelegate;
		this.libraryInstallDelegate = libraryInstallDelegate;
		this.changeSupport.firePropertyChange(
					LIBRARY_INSTALL_DELEGATE_PROPERTY, oldLibraryInstallDelegate, libraryInstallDelegate);
	}
}
