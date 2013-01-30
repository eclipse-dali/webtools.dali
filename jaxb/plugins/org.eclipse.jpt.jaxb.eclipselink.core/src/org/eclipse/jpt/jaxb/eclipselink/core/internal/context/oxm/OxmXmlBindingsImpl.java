/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SuperIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmFile;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmTypeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlSchema;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmXmlBindingsImpl
		extends AbstractJaxbContextNode
		implements OxmXmlBindings {
	
	protected EXmlBindings eXmlBindings;
	
	protected XmlAccessType specifiedAccessType;
	
	protected XmlAccessOrder specifiedAccessOrder;
	
	protected boolean xmlMappingMetadataComplete;
	
	protected String specifiedPackageName;
	protected String impliedPackageName;
	
	protected final OxmXmlSchema xmlSchema;
	
	protected final ContextListContainer<OxmJavaType, EJavaType> javaTypeContainer;
	
	
	public OxmXmlBindingsImpl(OxmFile parent, EXmlBindings eXmlBindings) {
		super(parent);
		this.eXmlBindings = eXmlBindings;
		this.specifiedAccessType = buildSpecifiedAccessType();
		this.specifiedAccessOrder = buildSpecifiedAccessOrder();
		this.xmlMappingMetadataComplete = buildXmlMappingMetadataComplete();
		this.specifiedPackageName = buildSpecifiedPackageName();
		// impliedPackageName not built until update, as it depends on sub-nodes
		this.xmlSchema = buildXmlSchema();
		this.javaTypeContainer = buildJavaTypeContainer();
	}
	
	
	public EXmlBindings getEXmlBindings() {
		return this.eXmlBindings;
	}
	
	public OxmFile getOxmFile() {
		return (OxmFile) super.getParent();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedAccessType_(buildSpecifiedAccessType());
		setSpecifiedAccessOrder_(buildSpecifiedAccessOrder());
		setXmlMappingMetadataComplete_(buildXmlMappingMetadataComplete());
		setSpecifiedPackageName_(buildSpecifiedPackageName());
		this.xmlSchema.synchronizeWithResourceModel();
		this.javaTypeContainer.synchronizeWithResourceModel();
	}
	
	@Override
	public void update() {
		super.update();
		setImpliedPackageName_(buildImpliedPackageName());
		this.xmlSchema.update();
		this.javaTypeContainer.update();
	}
	
	
	// ***** xml access type *****
	
	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : getDefaultAccessType();
	}
	
	public XmlAccessType getDefaultAccessType() {
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType newAccessType) {
		this.eXmlBindings.setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(newAccessType));
		setSpecifiedAccessType_(newAccessType);
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType newAccessType) {
		XmlAccessType oldAccessType = this.specifiedAccessType;
		this.specifiedAccessType = newAccessType;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, oldAccessType, newAccessType);
	}
	
	protected XmlAccessType buildSpecifiedAccessType() {
		return ELXmlAccessType.fromOxmResourceModel(this.eXmlBindings.getXmlAccessorType());
	}
	
	
	// ***** xml access order *****
	
	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : getDefaultAccessOrder();
	}
	
	public XmlAccessOrder getDefaultAccessOrder() {
		return XmlAccessOrder.UNDEFINED;
	}
	
	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder newAccessOrder) {
		this.eXmlBindings.setXmlAccessorOrder(ELXmlAccessOrder.toOxmResourceModel(newAccessOrder));
		setSpecifiedAccessOrder_(newAccessOrder);
	}
	
	protected void setSpecifiedAccessOrder_(XmlAccessOrder newAccessOrder) {
		XmlAccessOrder oldAccessOrder = this.specifiedAccessOrder;
		this.specifiedAccessOrder = newAccessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, oldAccessOrder, newAccessOrder);
	}
	
	protected XmlAccessOrder buildSpecifiedAccessOrder() {
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
	
	public String getSpecifiedPackageName() {
		return this.specifiedPackageName;
	}
	
	public void setSpecifiedPackageName(String packageName) {
		this.eXmlBindings.setPackageName(packageName);
		setSpecifiedPackageName_(packageName);
	}
	
	protected void setSpecifiedPackageName_(String packageName) {
		String oldPackageName = this.specifiedPackageName;
		this.specifiedPackageName = packageName;
		firePropertyChanged(SPECIFIED_PACKAGE_NAME_PROPERTY, oldPackageName, packageName);
	}
	
	protected String buildSpecifiedPackageName() {
		return this.eXmlBindings.getPackageName();
	}
	
	public String getImpliedPackageName() {
		return this.impliedPackageName;
	}
	
	protected void setImpliedPackageName_(String packageName) {
		String oldPackageName = this.impliedPackageName;
		this.impliedPackageName = packageName;
		firePropertyChanged(IMPLIED_PACKAGE_NAME_PROPERTY, oldPackageName, packageName);
	}
	
	protected String buildImpliedPackageName() {
		for (OxmJavaType javaType : getJavaTypes()) {
			String specifiedName = javaType.getSpecifiedName();
			String packageName = specifiedName == null ? null : TypeDeclarationTools.packageName(specifiedName);
			if (! StringTools.isBlank(packageName)) {
				return packageName;
			}
		}
		return StringTools.EMPTY_STRING;
	}
	
	public String getPackageName() {
		return (this.specifiedPackageName != null) ? this.specifiedPackageName : this.impliedPackageName;
	}
	
	/**
	 * append package if the name is not qualified
	 */
	public String getQualifiedName(String className) {
		if (StringTools.isBlank(className)) {
			return StringTools.EMPTY_STRING;
		}
		
		if (className.indexOf('.') >= 0) {
			return className;
		}
		
		// account for arrays
		String componentClassName = TypeDeclarationTools.elementTypeName(className);
		
		if (ClassNameTools.isPrimitive(componentClassName)) {
			return className;
		}
		
		if (TypeDeclarationTools.isJavaLangClass(className)) {
			return StringTools.concatenate("java.lang.", className);
		}
		
		if (StringTools.isBlank(this.specifiedPackageName)) {
			return className;
		}
		
		return StringTools.concatenate(this.specifiedPackageName, ".", className);
	}
	
	
	// ***** xml schema *****
	
	public OxmXmlSchema getXmlSchema() {
		return this.xmlSchema;
	}
	
	protected OxmXmlSchema buildXmlSchema() {
		return new OxmXmlSchemaImpl(this);
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
			if (ObjectTools.equals(javaType.getTypeName().getFullyQualifiedName(), qualifiedName)) {
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
		return IterableTools.cloneLive(this.eXmlBindings.getJavaTypes());
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
			return contextElement.getETypeMapping();
		}
//		@Override
//		protected void disposeElement(OxmJavaType element) {
//			element.dispose();
//		}
	}
	
	
	// ***** misc *****
	
	public Iterable<OxmTypeMapping> getTypeMappings() {
		// TODO xml-enums
		return new SuperIterableWrapper<OxmTypeMapping>(getJavaTypes());
	}
	
	public OxmTypeMapping getTypeMapping(String typeName) {
		// TODO xml-enums
		return getJavaType(typeName);
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = this.eXmlBindings.getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		for (OxmJavaType javaType : getJavaTypes()) {
			javaType.validate(messages, reporter);
		}
	}
}
