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
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer.Context;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbClass;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessageBuilder;
import org.eclipse.jpt.jaxb.eclipselink.core.internal.validation.ELJaxbValidationMessages;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OxmJavaTypeImpl
		extends AbstractOxmTypeMapping
		implements OxmJavaType {
	
	protected String specifiedName;
	
	// super-type
	protected String superTypeName;
	protected String defaultSuperTypeName;
	protected String specifiedSuperTypeName;
	
	// superclass
	protected JaxbClassMapping superclass;
	
	// accessor-order
	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;
	
	// accessor-type
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	// java-attributes
	protected final Vector<OxmJavaAttribute> specifiedAttributes;
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter;
	
	
	public OxmJavaTypeImpl(OxmXmlBindings parent, EJavaType eJavaType) {
		super(parent, eJavaType);
		
		this.specifiedAttributes = new Vector<OxmJavaAttribute>();
		this.specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();
		
		initSpecifiedName();
		initSuperTypeName();
		initSpecifiedAccessOrder();
		initDefaultAccessOrder();
		initSpecifiedAccessType();
		initDefaultAccessType();
		initSpecifiedAttributes();
	}
	
	
	@Override
	public EJavaType getETypeMapping() {
		return (EJavaType) super.getETypeMapping();
	}
	
	public TypeKind getTypeKind() {
		return TypeKind.CLASS;
	}
	
	@Override
	protected JavaClass buildJavaType(JavaResourceType resourceType) {
		return new GenericJavaJaxbClass(this, resourceType);
	}
	
	@Override
	public JavaClass getJavaType() {
		return (JavaClass) super.getJavaType();
	}
	
	protected JaxbClassMapping getJavaClassMapping() {
		JavaClass javaClass = getJavaType();
		return (javaClass == null) ? null : javaClass.getMapping();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		setSpecifiedName_(buildSpecifiedName());
		syncSuperTypeName();
		syncSpecifiedAccessOrder();
		syncSpecifiedAccessType();
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedAttributeContainerAdapter);
	}
	
	@Override
	public void update() {
		super.update();
		updateSuperTypeName();
		updateSuperclass();
		updateDefaultAccessOrder();
		updateDefaultAccessType();
		ContextContainerTools.update(this.specifiedAttributeContainerAdapter);
	}
	
	
	// ***** name *****
	
	public String getSpecifiedName() {
		return this.specifiedName;
	}
	
	public void setSpecifiedName(String newName) {
		setSpecifiedName_(newName);
		getETypeMapping().setName(newName);
	}
	
	protected void setSpecifiedName_(String newName) {
		String oldName = this.specifiedName;
		this.specifiedName = newName;
		firePropertyChanged(SPECIFIED_NAME_PROPERTY, oldName, newName);
	}
	
	protected void initSpecifiedName() {
		this.specifiedName = buildSpecifiedName();
	}
	
	protected String buildSpecifiedName() {
		return getETypeMapping().getName();
	}
	
	
	// ***** super-type name *****
	
	public String getSuperTypeName() {
		return this.superTypeName;
	}
	
	protected void setSuperTypeName_(String superTypeName) {
		String old = this.superTypeName;
		this.superTypeName = superTypeName;
		firePropertyChanged(SUPER_TYPE_NAME_PROPERTY, old, superTypeName);
	}
	
	public String getDefaultSuperTypeName() {
		return this.defaultSuperTypeName;
	}
	
	protected void setDefaultSuperTypeName_(String superTypeName) {
		String old = this.defaultSuperTypeName;
		this.defaultSuperTypeName = superTypeName;
		firePropertyChanged(DEFAULT_SUPER_TYPE_NAME_PROPERTY, old, superTypeName);
	}
	
	public String getSpecifiedSuperTypeName() {
		return this.specifiedSuperTypeName;
	}
	
	public void setSpecifiedSuperTypeName(String superTypeName) {
		setSpecifiedSuperTypeName_(superTypeName);
		getETypeMapping().setSuperType(superTypeName);
	}
	
	protected void setSpecifiedSuperTypeName_(String superTypeName) {
		String old = this.specifiedSuperTypeName;
		this.specifiedSuperTypeName = superTypeName;
		firePropertyChanged(SPECIFIED_SUPER_TYPE_NAME_PROPERTY, old, superTypeName);
	}
	
	protected void initSuperTypeName() {
		this.specifiedSuperTypeName = getETypeMapping().getSuperType();
	}
	
	protected void syncSuperTypeName() {
		setSpecifiedSuperTypeName_(getETypeMapping().getSuperType());
	}
	
	protected void updateSuperTypeName() {
		String defaultTypeName = null;
		JavaType javaType = getJavaType();
		if (javaType != null) {
			JavaResourceAbstractType resourceType = javaType.getJavaResourceType();
			if (resourceType.getAstNodeType() == JavaResourceAbstractType.AstNodeType.TYPE) {
				defaultTypeName = ((JavaResourceType) resourceType).getSuperclassQualifiedName();
			}
		}
		
		setDefaultSuperTypeName_(defaultTypeName);
		
		String typeName = (this.specifiedSuperTypeName != null) ? 
				this.specifiedSuperTypeName
				: this.defaultSuperTypeName;
		
		setSuperTypeName_(typeName);
	}
	
	
	// ***** superclass *****
	
	public JaxbClassMapping getSuperclass() {
		return this.superclass;
	}
	
	protected void setSuperclass_(JaxbClassMapping superclass) {
		JaxbClassMapping old = this.superclass;
		this.superclass = superclass;
		firePropertyChanged(SUPERCLASS_PROPERTY, old, superclass);
	}
	
	protected void updateSuperclass() {
		setSuperclass_(getContextRoot().getClassMapping(getSuperTypeName()));
	}
	
	
	// ***** type name *****
	
	@Override
	protected void updateTypeName() {
		String fqName = this.typeName.getFullyQualifiedName();
		String newFqName = getXmlBindings().getQualifiedName(this.specifiedName);
		if (! ObjectTools.equals(fqName, newFqName)) {
			setTypeName_(buildTypeName());
		}
	}
	
	@Override
	protected TypeName buildTypeName() {
		return new OxmTypeName(getXmlBindings().getQualifiedName(this.specifiedName));
	}
	
	
	// ***** XmlAccessorOrder *****
	
	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.defaultAccessOrder;
	}
	
	public XmlAccessOrder getDefaultAccessOrder() {
		return this.defaultAccessOrder;
	}
	
	protected void setDefaultAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.defaultAccessOrder;
		this.defaultAccessOrder = accessOrder;
		firePropertyChanged(DEFAULT_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}
	
	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}
	
	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		getETypeMapping().setXmlAccessorOrder(ELXmlAccessOrder.toOxmResourceModel(accessOrder));
		setSpecifiedAccessOrder_(accessOrder);
	}
	
	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}
	
	protected void initDefaultAccessOrder() {
		this.defaultAccessOrder = buildDefaultAccessOrder();
	}
	
	protected void updateDefaultAccessOrder() {
		setDefaultAccessOrder_(buildDefaultAccessOrder());
	}
	
	/**
	 * Default access order rules are TBD.  For now we
	 * - check if specified on java class
	 * - if not, check if specified on xml bindings
	 * - if not, return UNDEFINED
	 */
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder access;
		
		// TODO
//		access = getSuperclassAccessOrder();
//		if (access != null) {
//			return access;
//		}
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessOrder();
				if (access != null) {
					return access;
				}
			}
		}
		access = getXmlBindings().getSpecifiedAccessOrder();
		if (access != null) {
			return access;
		}
		
		return XmlAccessOrder.UNDEFINED;
	}
	
	protected void initSpecifiedAccessOrder() {
		this.specifiedAccessOrder = buildSpecifiedAccessOrder();
	}
	
	protected void syncSpecifiedAccessOrder() {
		setSpecifiedAccessOrder_(buildSpecifiedAccessOrder());
	}
	
	protected XmlAccessOrder buildSpecifiedAccessOrder() {
		return ELXmlAccessOrder.fromOxmResourceModel(getETypeMapping().getXmlAccessorOrder());
	}
	
	
	// ***** XmlAccessorType *****
	
	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.defaultAccessType;
	}
	
	public XmlAccessType getDefaultAccessType() {
		return this.defaultAccessType;
	}
	
	protected void setDefaultAccessType_(XmlAccessType access) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = access;
		firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}
	
	public void setSpecifiedAccessType(XmlAccessType access) {
		getETypeMapping().setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(access));
		setSpecifiedAccessType_(access);
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	protected void initDefaultAccessType() {
		this.defaultAccessType = buildDefaultAccessType();
	}
	
	protected void updateDefaultAccessType() {
		setDefaultAccessType_(buildDefaultAccessType());
	}
	
	/**
	 * Default access order rules are TBD.  For now we
	 * - check if specified on java class
	 * - if not, check if specified on xml bindings
	 * - if not, return PUBLIC_MEMBER
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType access;
		
		// TODO
//		access = getSuperclassAccessType();
//		if (access != null) {
//			return access;
//		}
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessType();
				if (access != null) {
					return access;
				}
			}
		}
		
		access = getXmlBindings().getSpecifiedAccessType();
		if (access != null) {
			return access;
		}
		
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	protected void initSpecifiedAccessType() {
		this.specifiedAccessType = buildSpecifiedAccessType();
	}
	
	protected void syncSpecifiedAccessType() {
		setSpecifiedAccessType_(buildSpecifiedAccessType());
	}
	
	protected XmlAccessType buildSpecifiedAccessType() {
		return ELXmlAccessType.fromOxmResourceModel(getETypeMapping().getXmlAccessorType());
	}
	
	
	// ***** factory class *****
	
	public String getFactoryClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getSpecifiedFactoryClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setSpecifiedFactoryClass(String factoryClass) {
		// TODO Auto-generated method stub
		
	}
	
	
	// ***** factory method *****
	
	public String getFactoryMethod() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setFactoryMethod(String factoryMethod) {
		// TODO Auto-generated method stub
		
	}
	
	
	// ***** prop order *****
	
	public ListIterable<String> getPropOrder() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getPropOrderSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String getProp(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addProp(int index, String prop) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeProp(int index) {
		// TODO Auto-generated method stub
		
	}
	
	public void removeProp(String prop) {
		// TODO Auto-generated method stub
		
	}
	
	public void moveProp(int targetIndex, int sourceIndex) {
		// TODO Auto-generated method stub
		
	}
	
	
	// ***** attributes *****
	
	public Iterable<JaxbPersistentAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getAttributesSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Iterable<JaxbPersistentAttribute> getIncludedAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getIncludedAttributesSize() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public Iterable<JaxbPersistentAttribute> getAllLocallyDefinedAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterable<JaxbPersistentAttribute> getInheritedAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JaxbAttributesContainer buildIncludedAttributesContainer(JaxbClassMapping parent, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// ***** specified java attributes *****
	
	public ListIterable<OxmJavaAttribute> getSpecifiedAttributes() {
		return IterableTools.cloneLive(this.specifiedAttributes);
	}
	
	public int getSpecifiedAttributesSize() {
		return this.specifiedAttributes.size();
	}
	
	protected void addSpecifiedAttribute_(int index, EJavaAttribute resourceAttribute) {
		OxmJavaAttribute attribute = buildSpecifiedAttribute(resourceAttribute);
		addItemToList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void removeSpecifiedAttribute_(OxmJavaAttribute attribute) {
		this.removeItemFromList(attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void moveSpecifiedAttribute_(int index, OxmJavaAttribute attribute) {
		moveItemInList(index, attribute, this.specifiedAttributes, SPECIFIED_ATTRIBUTES_LIST);
	}
	
	protected void initSpecifiedAttributes() {
		for (EJavaAttribute eJavaAttribute : getETypeMapping().getJavaAttributes()) {
			this.specifiedAttributes.add(buildSpecifiedAttribute(eJavaAttribute));
		}
	}
	
	protected OxmJavaAttribute buildSpecifiedAttribute(EJavaAttribute resourceAttribute) {
		// If this gets weighty or duplicated, we can move it
		String elementName = resourceAttribute.getElementName();
		if (ObjectTools.equals(Oxm.XML_ELEMENT, elementName)) {
			return new OxmXmlElementImpl(this, (EXmlElement) resourceAttribute);
		}
		
		// ?
		return null;
	}
	
	
	// ***** misc *****
	
	public JaxbAttributeMapping getXmlIdMapping() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// ***** validation *****
	
	@Override
	public TextRange getValidationTextRange() {
		TextRange textRange = getETypeMapping().getValidationTextRange();
		return (textRange != null) ? textRange : this.getParent().getValidationTextRange();
	}
	
	protected TextRange getNameTextRange() {
		return getETypeMapping().getNameTextRange();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateName(messages, reporter);
		
	}
	
	protected void validateName(List<IMessage> messages, IReporter reporter) {
		// type name must be specified
		if (StringTools.isBlank(this.specifiedName)) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__NAME_NOT_SPECIFIED,
							this,
							getNameTextRange()));
			return;
		}
		
		// package name must be uniform across oxm file
		String packageName = TypeDeclarationTools.packageName(this.specifiedName);
		if (! StringTools.isBlank(packageName) && ! ObjectTools.equals(packageName, getXmlBindings().getPackageName())) {
			messages.add(
					ELJaxbValidationMessageBuilder.buildMessage(
							IMessage.HIGH_SEVERITY,
							ELJaxbValidationMessages.OXM_JAVA_TYPE__PACKAGE_NAME_NOT_UNIFORM,
							this,
							getNameTextRange()));
		}
	}
	
	
	protected static class OxmTypeName
			implements TypeName {
		
		// never null
		protected String fullyQualifiedName;
		
		protected OxmTypeName(String fullyQualifiedName) {
			assert (fullyQualifiedName != null);
			this.fullyQualifiedName = fullyQualifiedName;
		}
		
		public String getPackageName() {
			return ClassNameTools.packageName(this.fullyQualifiedName);
		}
		
		public String getSimpleName() {
			return ClassNameTools.simpleName(this.fullyQualifiedName);
		}
		
		public String getTypeQualifiedName() {
			String packageName = this.getPackageName();
			return (StringTools.isBlank(packageName)) ? this.fullyQualifiedName : this.fullyQualifiedName.substring(packageName.length() + 1);
		}
		
		public String getFullyQualifiedName() {
			return this.fullyQualifiedName;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return ObjectTools.equals(this.fullyQualifiedName, ((OxmTypeName) obj).fullyQualifiedName);
		}
		
		@Override
		public int hashCode() {
			return ObjectTools.hashCode(this.fullyQualifiedName);
		}
	}
	
	
	/**
	 * specified attribute container adapter
	 */
	protected class SpecifiedAttributeContainerAdapter
			implements ContextContainerTools.Adapter<OxmJavaAttribute, EJavaAttribute> {
		
		public Iterable<OxmJavaAttribute> getContextElements() {
			return OxmJavaTypeImpl.this.getSpecifiedAttributes();
		}
		
		public Iterable<EJavaAttribute> getResourceElements() {
			return OxmJavaTypeImpl.this.getETypeMapping().getJavaAttributes();
		}
		
		public EJavaAttribute getResourceElement(OxmJavaAttribute contextElement) {
			return contextElement.getEJavaAttribute();
		}
		
		public void addContextElement(int index, EJavaAttribute resourceElement) {
			OxmJavaTypeImpl.this.addSpecifiedAttribute_(index, resourceElement);
		}
		
		public void removeContextElement(OxmJavaAttribute element) {
			OxmJavaTypeImpl.this.removeSpecifiedAttribute_(element);
		}
		
		public void moveContextElement(int index, OxmJavaAttribute element) {
			OxmJavaTypeImpl.this.moveSpecifiedAttribute_(index, element);
		}
	}
}
