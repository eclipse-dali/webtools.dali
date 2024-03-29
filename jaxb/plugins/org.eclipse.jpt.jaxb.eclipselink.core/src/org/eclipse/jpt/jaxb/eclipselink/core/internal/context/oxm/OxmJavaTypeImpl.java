/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.TypeDeclarationTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.TypeKind;
import org.eclipse.jpt.jaxb.core.context.TypeName;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
import org.eclipse.jpt.jaxb.core.internal.context.ContextContainerTools;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaAttributesContainer;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaJaxbClass;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlBindings;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.validation.JptJaxbEclipseLinkCoreValidationMessages;
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
	protected XmlAccessType accessType;
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	// java-attributes
	protected final Vector<OxmJavaAttribute> specifiedAttributes;
	protected final SpecifiedAttributeContainerAdapter specifiedAttributeContainerAdapter;
	
	protected final static String DEFAULT_ATTRIBUTES_COLLECTION = "defaultAttributes"; //$NON-NLS-1$
	protected final JaxbAttributesContainer defaultAttributesContainer;
	
	
	public OxmJavaTypeImpl(OxmXmlBindings parent, EJavaType eJavaType) {
		super(parent, eJavaType);
		
		this.specifiedAttributes = new Vector<OxmJavaAttribute>();
		this.specifiedAttributeContainerAdapter = new SpecifiedAttributeContainerAdapter();
		this.defaultAttributesContainer = 
				new DefaultAttributesContainer(this, new DefaultAttributesContainerContext());
		
		initSpecifiedName();
		initSuperTypeName();
		initSpecifiedAccessOrder();
		initDefaultAccessOrder();
		initAccessType();
		initAttributes();
	}
	
	
	@Override
	public EJavaType getETypeMapping() {
		return (EJavaType) super.getETypeMapping();
	}
	
	public TypeKind getTypeKind() {
		return TypeKind.CLASS;
	}
	
	@Override
	protected JavaClass buildJavaType(JavaResourceAbstractType resourceType) {
		if (resourceType.getAstNodeType() == AstNodeType.TYPE) {
			return new GenericJavaJaxbClass(this, (JavaResourceType) resourceType);
		}
		return null;
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
		syncAccessType();
		syncAttributes();
	}
	
	@Override
	public void update() {
		super.update();
		updateSuperTypeName();
		updateSuperclass();
		updateDefaultAccessOrder();
		updateAccessType();
		updateAttributes();
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
		if (this.superTypeName != null) {
			setSuperclass_(getContextRoot().getClassMapping(this.superTypeName));
		}
		else {
			setSuperclass_(null);
		}
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
	 * - if not, use superclass setting if superclass exists
	 * - if not, check if specified on xml bindings
	 * - if not, return UNDEFINED
	 */
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder access;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessOrder();
				if (access != null) {
					return access;
				}
			}
		}
		
		if (this.superclass != null) {
			access = getSuperclass().getAccessOrder();
			if (access != null) {
				return access;
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
	
	
	// ***** xml-accessor-type *****
	
	public XmlAccessType getAccessType() {
		return this.accessType;
	}
	
	protected void setAccessType_(XmlAccessType accessType) {
		XmlAccessType old = this.accessType;
		this.accessType = accessType;
		firePropertyChanged(ACCESS_TYPE_PROPERTY, old, accessType);
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
		setSpecifiedAccessType_(access);
		getETypeMapping().setXmlAccessorType(ELXmlAccessType.toOxmResourceModel(access));
	}
	
	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}
	
	protected void initAccessType() {
		this.specifiedAccessType = buildSpecifiedAccessType();
	}
	
	protected void syncAccessType() {
		setSpecifiedAccessType_(buildSpecifiedAccessType());
	}
	
	protected void updateAccessType() {
		setDefaultAccessType_(buildDefaultAccessType());
		
		XmlAccessType actual = (this.specifiedAccessType != null) ?
				this.specifiedAccessType
				: this.defaultAccessType;
		setAccessType_(actual);
	}
	
	/**
	 * Default access order rules are TBD.  For now we
	 * - check if specified on java class
	 * - if not, use superclass setting if superclass exists
	 * - if not, check if specified on xml bindings
	 * - if not, return UNDEFINED
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType access;
		
		if (! getXmlBindings().isXmlMappingMetadataComplete()) {
			JaxbClassMapping javaMapping = getJavaClassMapping();
			if (javaMapping != null) {
				access = javaMapping.getSpecifiedAccessType();
				if (access != null) {
					return access;
				}
			}
		}
		
		if (this.superclass != null) {
			access = getSuperclass().getAccessType();
			if (access != null) {
				return access;
			}
		}
		
		access = getXmlBindings().getSpecifiedAccessType();
		if (access != null) {
			return access;
		}
		
		return XmlAccessType.PUBLIC_MEMBER;
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
		// TODO
		return EmptyListIterable.instance();
	}
	
	public int getPropOrderSize() {
		// TODO
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
		// TODO 
		return EmptyIterable.instance();
	}
	
	public int getAttributesSize() {
		// TODO 
		return 0;
	}
	
	public Iterable<JaxbPersistentAttribute> getIncludedAttributes() {
		// TODO
		return EmptyIterable.instance();
	}
	
	public int getIncludedAttributesSize() {
		// TODO
		return 0;
	}
	
	public Iterable<JaxbPersistentAttribute> getAllLocallyDefinedAttributes() {
		// TODO
		return EmptyIterable.instance();
	}
	
	public Iterable<JaxbPersistentAttribute> getInheritedAttributes() {
		// TODO 
		return EmptyIterable.instance();
	}
	
	public JaxbAttributesContainer buildIncludedAttributesContainer(
			JaxbClassMapping parent, JaxbAttributesContainer.Context context) {
		return new OxmAttributesContainer(parent);
	}
	
	
	// ***** attributes *****
	
	protected Iterable<JaxbAttributeMapping> getDefaultAttributes() {
		return this.defaultAttributesContainer.getAttributes();
	}
	
	
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
	
	protected void initAttributes() {
		for (EJavaAttribute eJavaAttribute : getETypeMapping().getJavaAttributes()) {
			this.specifiedAttributes.add(buildSpecifiedAttribute(eJavaAttribute));
		}
	}
	
	protected void syncAttributes() {
		ContextContainerTools.synchronizeWithResourceModel(this.specifiedAttributeContainerAdapter);
	}
	
	protected void updateAttributes() {
		ContextContainerTools.update(this.specifiedAttributeContainerAdapter);
	}
	
	protected OxmJavaAttribute buildSpecifiedAttribute(EJavaAttribute resourceAttribute) {
		return new OxmJavaAttributeImpl(this, resourceAttribute);
	}
	
	
	// ***** default attributes *****
	
	
	
	
	// ***** misc *****
	
	public void attributeMappingChanged(OxmJavaAttribute attribute, OxmAttributeMapping oldMapping, OxmAttributeMapping newMapping) {
		// TODO Auto-generated method stub
		
	}
	
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
					this.buildValidationMessage(
							getNameTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.OXM_JAVA_TYPE__NAME_NOT_SPECIFIED
						));
			return;
		}
		
		// package name must be uniform across oxm file
		String packageName = TypeDeclarationTools.packageName(this.specifiedName);
		if (! StringTools.isBlank(packageName) && ! ObjectTools.equals(packageName, getXmlBindings().getPackageName())) {
			messages.add(
					this.buildValidationMessage(
							getNameTextRange(),
							JptJaxbEclipseLinkCoreValidationMessages.OXM_JAVA_TYPE__PACKAGE_NAME_NOT_UNIFORM
						));
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
	
	
	protected class DefaultAttributesContainer
			extends GenericJavaAttributesContainer {
		
		protected DefaultAttributesContainer(OxmJavaTypeImpl parent, Context context) {
			super(parent, context, null);
		}
		
		
		@Override
		public boolean isFor(JavaResourceType javaResourceType) {
			if (this.javaResourceType == null) {
				return false;
			}
			return super.isFor(javaResourceType);
		}
		
		@Override
		protected Iterable<JavaResourceField> getResourceFields() {
			if (this.javaResourceType == null) {
				return EmptyIterable.instance();
			}
			return super.getResourceFields();
		}
		
		@Override
		protected Iterable<JavaResourceMethod> getResourceMethods() {
			if (this.javaResourceType == null) {
				return EmptyIterable.instance();
			}
			return super.getResourceMethods();
		}
		
		protected void setJavaResourceType(JavaResourceType javaResourceType) {
			this.javaResourceType = javaResourceType;
		}
	}
	
	
	protected class DefaultAttributesContainerContext
			implements JaxbAttributesContainer.Context {
		
		public XmlAccessType getAccessType() {
			return OxmJavaTypeImpl.this.getAccessType();
		}
		
		public void attributeAdded(JaxbPersistentAttribute attribute) {
			OxmJavaTypeImpl.this.fireItemAdded(DEFAULT_ATTRIBUTES_COLLECTION, attribute);
		}
		
		public void attributeRemoved(JaxbPersistentAttribute attribute) {
			OxmJavaTypeImpl.this.fireItemRemoved(DEFAULT_ATTRIBUTES_COLLECTION, attribute);
		}
	}
}
