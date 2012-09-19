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

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.internal.utility.SimpleTextRange;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbContextRoot;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;

public class OxmFile 
		extends AbstractJaxbContextNode {
	
	// never null
	protected JptXmlResource oxmResource;
	
	/**
	 * The resource type will only change if the XML file's version changes
	 * (since, if the content type changes, we get garbage-collected).
	 */
	protected JptResourceType resourceType;
	
	/**
	 * The root element of the oxm file.
	 */
	protected OxmXmlBindings xmlBindings;

	
	
	public OxmFile(ELJaxbContextRoot parent, JptXmlResource oxmResource) {
		super(parent);
		this.oxmResource = oxmResource;
		this.resourceType = oxmResource.getResourceType();
		this.xmlBindings = buildXmlBindings();
	}
	
	
	@Override
	public ELJaxbContextRoot getContextRoot() {
		return (ELJaxbContextRoot) super.getContextRoot();
	}
	
	public JptXmlResource getOxmResource() {
		return this.oxmResource;
	}
	
	public String getPackageName() {
		return (this.xmlBindings == null) ? null : this.xmlBindings.getPackageName();
	}
	
	
	// ***** xml bindings *****
	
	protected OxmXmlBindings buildXmlBindings() {
		// if less than 2.3, then there is no context model support
		if (this.resourceType.isKindOf(Oxm.RESOURCE_TYPE_2_3)) {
			return new OxmXmlBindings(this, (EXmlBindings) this.oxmResource.getRootObject());
		}
		return null;
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		return new SimpleTextRange(0, 0, 0); // simple beginning of document
	}
}
