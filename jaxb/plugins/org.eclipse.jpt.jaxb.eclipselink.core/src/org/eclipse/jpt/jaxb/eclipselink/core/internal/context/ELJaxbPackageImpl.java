/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context;

import org.eclipse.jpt.jaxb.core.internal.context.GenericPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbPackage;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;

public class ELJaxbPackageImpl
		extends GenericPackage
		implements ELJaxbPackage {
	
	protected OxmFile oxmFile;
	
	
	public ELJaxbPackageImpl(ELJaxbContextRoot parent, String name) {
		super(parent, name);
	}
	
	
	@Override
	public ELJaxbContextRoot getContextRoot() {
		return (ELJaxbContextRoot) super.getContextRoot();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void update() {
		super.update();
		setOxmFile(getContextRoot().getOxmFile(getName()));
	}
	
	
	// ***** oxm file *****
	
	public OxmFile getOxmFile() {
		return this.oxmFile;
	}
	
	protected void setOxmFile(OxmFile oxmFile) {
		if (valuesAreDifferent(this.oxmFile, oxmFile)) {
			if (this.oxmFile != null) {
				this.oxmFile.setPackage(null);
			}
			setOxmFile_(oxmFile);
			if (oxmFile != null) {
				oxmFile.setPackage(this);
			}
		}
	}
	
	protected void setOxmFile_(OxmFile oxmFile) {
		OxmFile old = this.oxmFile;
		this.oxmFile = oxmFile;
		firePropertyChanged(OXM_FILE_PROPERTY, old, oxmFile);
	}
}
