/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;

public class OxmXmlBindings
		extends AbstractJaxbContextNode {
	
	protected EXmlBindings eXmlBindings;
	
	public final static String PACKAGE_NAME_PROPERTY = "packageName"; //$NON-NLS-1$
	protected String packageName;
	
	
	public OxmXmlBindings(OxmFile parent, EXmlBindings eXmlBindings) {
		super(parent);
		this.eXmlBindings = eXmlBindings;
		this.packageName = buildPackageName();
	}
	
	
	// ***** package name *****
	
	public String getPackageName() {
		return this.packageName;
	}
	
	public void setPackageName(String packageName) {
		this.eXmlBindings.setPackageName(packageName);
		setPackageName_(packageName);
	}
	
	protected void setPackageName_(String packageName) {
		String oldPackageName = this.packageName;
		this.packageName = packageName;
		firePropertyChanged(PACKAGE_NAME_PROPERTY, oldPackageName, packageName);
	}
	
	protected String buildPackageName() {
		return this.eXmlBindings.getPackageName();
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eXmlBindings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
}
