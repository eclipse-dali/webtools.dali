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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneListIterable;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlAccessOrder;
import org.eclipse.jpt.jaxb.eclipselink.core.context.ELXmlAccessType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

public class OxmXmlBindingsImpl
		extends AbstractJaxbContextNode
		implements OxmXmlBindings {
	
	protected EXmlBindings eXmlBindings;
	
	protected ELXmlAccessType specifiedAccessType;
	
	protected ELXmlAccessOrder specifiedAccessOrder;
	
	protected boolean xmlMappingMetadataComplete;
	
	protected String packageName;
	
	protected final ContextListContainer<OxmJavaType, EJavaType> javaTypeContainer;
	
	
	public OxmXmlBindingsImpl(OxmFile parent, EXmlBindings eXmlBindings) {
		super(parent);
		this.eXmlBindings = eXmlBindings;
		this.specifiedAccessType = buildSpecifiedAccessType();
		this.specifiedAccessOrder = buildSpecifiedAccessOrder();
		this.xmlMappingMetadataComplete = buildXmlMappingMetadataComplete();
		this.packageName = buildPackageName();
		this.javaTypeContainer = buildJavaTypeContainer();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedAccessType_(buildSpecifiedAccessType());
		setSpecifiedAccessOrder_(buildSpecifiedAccessOrder());
		setXmlMappingMetadataComplete_(buildXmlMappingMetadataComplete());
		setPackageName_(buildPackageName());
		this.javaTypeContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		this.javaTypeContainer.update();
	}
	
	
	// ***** xml access type *****
	
	public ELXmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : getDefaultAccessType();
	}
	
	public ELXmlAccessType getDefaultAccessType() {
		return ELXmlAccessType.PUBLIC_MEMBER;
	}
	
	public ELXmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(ELXmlAccessType newAccessType) {
		this.eXmlBindings.setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(newAccessType));
		setSpecifiedAccessType_(newAccessType);
	}
	
	protected void setSpecifiedAccessType_(ELXmlAccessType newAccessType) {
		ELXmlAccessType oldAccessType = this.specifiedAccessType;
		this.specifiedAccessType = newAccessType;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, oldAccessType, newAccessType);
	}
	
	protected ELXmlAccessType buildSpecifiedAccessType() {
		return ELXmlAccessType.fromOxmResourceModel(this.eXmlBindings.getXmlAccessorType());
	}
	
	
	// ***** xml access order *****
	
	public ELXmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : getDefaultAccessOrder();
	}
	
	public ELXmlAccessOrder getDefaultAccessOrder() {
		return ELXmlAccessOrder.UNDEFINED;
	}
	
	public ELXmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(ELXmlAccessOrder newAccessOrder) {
		this.eXmlBindings.setXmlAccessorOrder(ELXmlAccessOrder.toOxmResourceModel(newAccessOrder));
		setSpecifiedAccessOrder_(newAccessOrder);
	}
	
	protected void setSpecifiedAccessOrder_(ELXmlAccessOrder newAccessOrder) {
		ELXmlAccessOrder oldAccessOrder = this.specifiedAccessOrder;
		this.specifiedAccessOrder = newAccessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, oldAccessOrder, newAccessOrder);
	}
	
	protected ELXmlAccessOrder buildSpecifiedAccessOrder() {
		return ELXmlAccessOrder.fromOxmResourceModel(this.eXmlBindings.getXmlAccessorOrder());
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
	
	/**
	 * append package if the name is not qualified
	 */
	public String getQualifiedName(String className) {
		if (className == null) {
			return null;
		}
		if (className.indexOf('.') >= 0) {
			return className;
		}
		return StringTools.concatenate(this.packageName, ".", className);
	}
	
	
	// ***** java types *****
	
	public ListIterable<OxmJavaType> getJavaTypes() {
		return this.javaTypeContainer.getContextElements();
	}
	
	public int getJavaTypesSize() {
		return this.javaTypeContainer.getContextElementsSize();
	}
	
	public OxmJavaType getJavaType(int index) {
		return this.javaTypeContainer.getContextElement(index);
	}
	
	public OxmJavaType getJavaType(String qualifiedName) {
		for (OxmJavaType javaType : getJavaTypes()) {
			if (Tools.valuesAreEqual(javaType.getQualifiedName(), qualifiedName)) {
				return javaType;
			}
		}
		return null;
	}
	
	public OxmJavaType addJavaType(int index) {
		EJavaType eJavaType = OxmFactory.eINSTANCE.createEJavaType();
		OxmJavaType javaType = this.javaTypeContainer.addContextElement(index, eJavaType);
		this.eXmlBindings.getJavaTypes().add(index, eJavaType);
		return javaType;
	}
	
	public void removeJavaType(int index) {
		removeJavaType_(index);
		this.eXmlBindings.getJavaTypes().remove(index);
	}
	
	protected void removeJavaType_(int index) {
		this.javaTypeContainer.removeContextElement(index);
	}
	
	protected ListIterable<EJavaType> getEJavaTypes() {
		return new LiveCloneListIterable<EJavaType>(this.eXmlBindings.getJavaTypes());
	}
	
	protected OxmJavaType buildJavaType(EJavaType eJavaType) {
		return new OxmJavaTypeImpl(this, eJavaType);
	}
	
	protected ContextListContainer<OxmJavaType, EJavaType> buildJavaTypeContainer() {
		JavaTypeContainer container = new JavaTypeContainer();
		container.initialize();
		return container;
	}
	
	protected class JavaTypeContainer
			extends ContextListContainer<OxmJavaType, EJavaType> {
		@Override
		protected String getContextElementsPropertyName() {
			return JAVA_TYPES_LIST;
		}
		@Override
		protected OxmJavaType buildContextElement(EJavaType resourceElement) {
			return OxmXmlBindingsImpl.this.buildJavaType(resourceElement);
		}
		@Override
		protected ListIterable<EJavaType> getResourceElements() {
			return OxmXmlBindingsImpl.this.getEJavaTypes();
		}
		@Override
		protected EJavaType getResourceElement(OxmJavaType contextElement) {
			return contextElement.getEJavaType();
		}
//		@Override
//		protected void disposeElement(OxmJavaType element) {
//			element.dispose();
//		}
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eXmlBindings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
}
