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
	}
	
	public JaxbPlatformDescription getPlatform() {
		return this.platform;
	}
	
	public void setPlatform(JaxbPlatformDescription platform) {
		JaxbPlatformDescription oldPlatform = this.platform;
		this.platform = platform;
		this.changeSupport.firePropertyChange(PLATFORM_PROPERTY, oldPlatform, platform);
	}
	
	public LibraryInstallDelegate getLibraryInstallDelegate() {
		return this.libraryInstallDelegate;
	}
	
	public void setLibraryInstallDelegate(LibraryInstallDelegate libraryInstallDelegate) {
		LibraryInstallDelegate oldLibraryInstallDelegate = this.libraryInstallDelegate;
		this.libraryInstallDelegate = libraryInstallDelegate;
		this.changeSupport.firePropertyChange(
					LIBRARY_INSTALL_DELEGATE_PROPERTY, oldLibraryInstallDelegate, libraryInstallDelegate);
	}
}
