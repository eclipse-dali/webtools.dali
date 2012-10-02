/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbXmlAccessOrder;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELJaxbXmlAccessType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;

public class OxmXmlBindingsImpl
		extends AbstractJaxbContextNode
		implements OxmXmlBindings {
	
	protected EXmlBindings eXmlBindings;
	
	protected ELJaxbXmlAccessType specifiedAccessType;
	
	protected ELJaxbXmlAccessOrder specifiedAccessOrder;
	
	protected boolean xmlMappingMetadataComplete;
	
	protected String packageName;
	
	
	public OxmXmlBindingsImpl(OxmFile parent, EXmlBindings eXmlBindings) {
		super(parent);
		this.eXmlBindings = eXmlBindings;
		this.specifiedAccessType = buildSpecifiedAccessType();
		this.specifiedAccessOrder = buildSpecifiedAccessOrder();
		this.xmlMappingMetadataComplete = buildXmlMappingMetadataComplete();
		this.packageName = buildPackageName();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedAccessType_(buildSpecifiedAccessType());
		setSpecifiedAccessOrder_(buildSpecifiedAccessOrder());
		setXmlMappingMetadataComplete_(buildXmlMappingMetadataComplete());
		setPackageName_(buildPackageName());
	}
	
	
	// ***** xml access type *****
	
	public ELJaxbXmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : getDefaultAccessType();
	}
	
	public ELJaxbXmlAccessType getDefaultAccessType() {
		return ELJaxbXmlAccessType.PUBLIC_MEMBER;
	}
	
	public ELJaxbXmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(ELJaxbXmlAccessType newAccessType) {
		this.eXmlBindings.setXmlAccessorType(ELJaxbXmlAccessType.toOxmResourceModel(newAccessType));
		setSpecifiedAccessType_(newAccessType);
	}
	
	protected void setSpecifiedAccessType_(ELJaxbXmlAccessType newAccessType) {
		ELJaxbXmlAccessType oldAccessType = this.specifiedAccessType;
		this.specifiedAccessType = newAccessType;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, oldAccessType, newAccessType);
	}
	
	protected ELJaxbXmlAccessType buildSpecifiedAccessType() {
		return ELJaxbXmlAccessType.fromOxmResourceModel(this.eXmlBindings.getXmlAccessorType());
	}
	
	
	// ***** xml access order *****
	
	public ELJaxbXmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : getDefaultAccessOrder();
	}
	
	public ELJaxbXmlAccessOrder getDefaultAccessOrder() {
		return ELJaxbXmlAccessOrder.UNDEFINED;
	}
	
	public ELJaxbXmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(ELJaxbXmlAccessOrder newAccessOrder) {
		this.eXmlBindings.setXmlAccessorOrder(ELJaxbXmlAccessOrder.toOxmResourceModel(newAccessOrder));
		setSpecifiedAccessOrder_(newAccessOrder);
	}
	
	protected void setSpecifiedAccessOrder_(ELJaxbXmlAccessOrder newAccessOrder) {
		ELJaxbXmlAccessOrder oldAccessOrder = this.specifiedAccessOrder;
		this.specifiedAccessOrder = newAccessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, oldAccessOrder, newAccessOrder);
	}
	
	protected ELJaxbXmlAccessOrder buildSpecifiedAccessOrder() {
		return ELJaxbXmlAccessOrder.fromOxmResourceModel(this.eXmlBindings.getXmlAccessorOrder());
	}
	
	
	// ***** xml mapping metadata complete *****
	
	public boolean isXmlMappingMetadataComplete() {
		return this.xmlMappingMetadataComplete;
	}
	
	public void setXmlMappingMetadataComplete(boolean newValue) {
		this.eXmlBindings.setXmlMappingMetadataComplete((newValue) ? newValue : null); // set to null if false
		setXmlMappingMetadataComplete_(newValue);
	}
	
	protected void setXmlMappingMetadataComplete_(boolean newValue) {
		boolean oldValue = this.xmlMappingMetadataComplete;
		this.xmlMappingMetadataComplete = newValue;
		firePropertyChanged(XML_MAPPING_METADATA_COMPLETE_PROPERTY, oldValue, newValue);
	}
	
	protected boolean buildXmlMappingMetadataComplete() {
		Boolean eValue = this.eXmlBindings.getXmlMappingMetadataComplete();
		return (eValue == null) ? false : eValue.booleanValue(); // if xml value is null or false, use false
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
