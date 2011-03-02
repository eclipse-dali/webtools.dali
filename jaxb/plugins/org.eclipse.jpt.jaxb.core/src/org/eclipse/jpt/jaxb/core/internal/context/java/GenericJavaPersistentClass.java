/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ChainIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbContainmentMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlAdaptable;
import org.eclipse.jpt.jaxb.core.context.XmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * To support inherited attributes in the Generic case I have made some assumptions
 * based on the JAXB RIs interpretation of the spec. Any superclass that is
 * marked as @XmlTransient will have a corresponding JaxbAttributesContainer for its JaxbClass.
 * If that XmlTransient class has no access type specified, the access type will be determined
 * by this JaxbPersistentClass's access type. If the XmlTransient class specified an access
 * type it will only be used in the case where this class does not specify an access type.
 * It will be this class's default access type. The spec states that you are not allowed
 * to use other annotations along with XmlTransient, but it appears the reference implementation
 * has made an exception for @XmlAccessorType and @XmlAccessorOrder. This is subject to change
 * pending a discussion with Blaise and the developers of the RI
 *
 */
public class GenericJavaPersistentClass
		extends AbstractJavaPersistentType
		implements JaxbPersistentClass {

	protected JaxbClass superClass;

	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;

	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;

	protected final JaxbAttributesContainer attributesContainer;

	protected final Map<JaxbClass, JaxbAttributesContainer> inheritedAttributesContainers = new HashMap<JaxbClass, JaxbAttributesContainer>();

	protected final XmlAdaptable xmlAdaptable;

	public GenericJavaPersistentClass(JaxbContextRoot parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.superClass = this.buildSuperClass();
		this.specifiedAccessType = this.getResourceAccessType();
		this.specifiedAccessOrder = this.getResourceAccessOrder();
		this.defaultAccessType = this.buildDefaultAccessType();
		this.defaultAccessOrder = this.buildDefaultAccessOrder();
		this.xmlAdaptable = this.buildXmlAdaptable();
		this.attributesContainer = new GenericJavaAttributesContainer(this, buildAttributesContainerOwner(), resourceType);
		this.initializeInheritedAttributes();
	}

	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccessType_(this.getResourceAccessType());
		this.setSpecifiedAccessOrder_(this.getResourceAccessOrder());
		this.xmlAdaptable.synchronizeWithResourceModel();
		this.attributesContainer.synchronizeWithResourceModel();
		this.syncInheritedAttributes();
	}

	@Override
	public void update() {
		super.update();
		super.update();
		this.setSuperClass(this.buildSuperClass());
		this.setDefaultAccessType(this.buildDefaultAccessType());
		this.setDefaultAccessOrder(this.buildDefaultAccessOrder());
		this.xmlAdaptable.update();
		this.attributesContainer.update();
		this.updateInheritedAttributes();
	}


	// ********** JaxbType impl **********

	public Kind getKind() {
		return Kind.PERSISTENT_CLASS;
	}
	
	@Override
	public Iterable<String> getDirectlyReferencedTypeNames() {
		return new CompositeIterable<String>(
				new SingleElementIterable(getJavaResourceType().getSuperclassQualifiedName()),
				new CompositeIterable<String>(
						new TransformationIterable<JaxbPersistentAttribute, Iterable<String>>(getAttributes()) {
							@Override
							protected Iterable<String> transform(JaxbPersistentAttribute o) {
								return o.getMapping().getDirectlyReferencedTypeNames();
							}
						}));
	}
	
	
	// ********** super class **********
	
	public JaxbClass getSuperClass() {
		return this.superClass;
	}

	protected void setSuperClass(JaxbClass superClass) {
		JaxbClass old = this.superClass;
		this.superClass = superClass;
		this.firePropertyChanged(SUPER_CLASS_PROPERTY, old, superClass);
	}

	protected JaxbClass buildSuperClass() {
		HashSet<JavaResourceType> visited = new HashSet<JavaResourceType>();
		visited.add(this.getJavaResourceType());
		JaxbClass spc = this.getSuperClass(this.getJavaResourceType().getSuperclassQualifiedName(), visited);
		if (spc == null) {
			return null;
		}
		if (CollectionTools.contains(spc.getInheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return spc;
	}
	
	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit. If it is not found we use
	 * resource persistent type and look for *its* super type.
	 * 
	 * The 'visited' collection is used to detect a cycle in the *resource* type
	 * inheritance hierarchy and prevent the resulting stack overflow.
	 * Any cycles in the *context* type inheritance hierarchy are handled in
	 * #buildSuperPersistentType().
	 */
	protected JaxbClass getSuperClass(String typeName, Collection<JavaResourceType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourceType resourceType = (JavaResourceType) this.getJaxbProject().getJavaResourceType(typeName, JavaResourceAbstractType.Kind.TYPE);
		if ((resourceType == null) || visited.contains(resourceType)) {
			return null;
		}
		visited.add(resourceType);
		JaxbClass spc = this.getClass(typeName);
		return (spc != null && resourceType.isMapped()) ? spc : this.getSuperClass(resourceType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected JaxbClass getClass(String fullyQualifiedTypeName) {
		return this.getParent().getClass(fullyQualifiedTypeName);
	}
	
	
	// ********** inheritance **********

	public Iterable<JaxbClass> getInheritanceHierarchy() {
		return this.getInheritanceHierarchyOf(this);
	}

	public Iterable<JaxbClass> getAncestors() {
		return this.getInheritanceHierarchyOf(this.superClass);
	}

	protected Iterable<JaxbClass> getInheritanceHierarchyOf(JaxbClass start) {
		// using a chain iterator to traverse up the inheritance tree
		return new ChainIterable<JaxbClass>(start) {
			@Override
			protected JaxbClass nextLink(JaxbClass jaxbClass) {
				return jaxbClass.getSuperClass();
			}
		};
	}

	// ********** access type **********

	public XmlAccessType getAccessType() {
		return (this.specifiedAccessType != null) ? this.specifiedAccessType : this.defaultAccessType;
	}

	public XmlAccessType getSpecifiedAccessType() {
		return this.specifiedAccessType;
	}

	public void setSpecifiedAccessType(XmlAccessType access) {
		this.getAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
		this.setSpecifiedAccessType_(access);
	}

	protected void setSpecifiedAccessType_(XmlAccessType access) {
		XmlAccessType old = this.specifiedAccessType;
		this.specifiedAccessType = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_TYPE_PROPERTY, old, access);
	}

	public XmlAccessType getDefaultAccessType() {
		return this.defaultAccessType;
	}

	protected void setDefaultAccessType(XmlAccessType access) {
		XmlAccessType old = this.defaultAccessType;
		this.defaultAccessType = access;
		this.firePropertyChanged(DEFAULT_ACCESS_TYPE_PROPERTY, old, access);
	}

	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(this.getAccessorTypeAnnotation().getValue());
	}

	protected XmlAccessorTypeAnnotation getAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorTypeAnnotation.ANNOTATION_NAME);
	}

	/**
	 * If there is a @XmlAccessorType on a class, then it is used.
	 * Otherwise, if a @XmlAccessorType exists on one of its super classes, then it is inherited.
	 * Otherwise, the @XmlAccessorType on a package is inherited. 	
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType superAccessType = this.getSuperClassAccessType();
		if (superAccessType != null) {
			return superAccessType;
		}
		XmlAccessType packageAccessType = getPackageAccessType();
		if (packageAccessType != null) {
			return packageAccessType;
		}
		return XmlAccessType.PUBLIC_MEMBER;
	}

	protected XmlAccessType getSuperClassAccessType() {
		JaxbClass superClass = this.getSuperClass();
		return superClass == null ? null : superClass.getSpecifiedAccessType();
	}

	protected XmlAccessType getPackageAccessType() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessType();
	}


	// ********** access order **********

	public XmlAccessOrder getAccessOrder() {
		return (this.specifiedAccessOrder != null) ? this.specifiedAccessOrder : this.defaultAccessOrder;
	}

	public XmlAccessOrder getSpecifiedAccessOrder() {
		return this.specifiedAccessOrder;
	}

	public void setSpecifiedAccessOrder(XmlAccessOrder accessOrder) {
		this.getAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
		this.setSpecifiedAccessOrder_(accessOrder);
	}

	protected void setSpecifiedAccessOrder_(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.specifiedAccessOrder;
		this.specifiedAccessOrder = accessOrder;
		this.firePropertyChanged(SPECIFIED_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	public XmlAccessOrder getDefaultAccessOrder() {
		return this.defaultAccessOrder;
	}

	protected void setDefaultAccessOrder(XmlAccessOrder accessOrder) {
		XmlAccessOrder old = this.defaultAccessOrder;
		this.defaultAccessOrder = accessOrder;
		this.firePropertyChanged(DEFAULT_ACCESS_ORDER_PROPERTY, old, accessOrder);
	}

	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(this.getAccessorOrderAnnotation().getValue());
	}

	protected XmlAccessorOrderAnnotation getAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) getJavaResourceType().getNonNullAnnotation(XmlAccessorOrderAnnotation.ANNOTATION_NAME);
	}

	/**
    * If there is a @XmlAccessorOrder on a class, then it is used.
    * Otherwise, if a @XmlAccessorOrder exists on one of its super classes, then it is inherited (by the virtue of Inherited)
    * Otherwise, the @XmlAccessorOrder on the package of the class is used, if it's there.
    * Otherwise XmlAccessOrder.UNDEFINED. 
  	*/
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder superAccessOrder = this.getSuperClassAccessOrder();
		if (superAccessOrder != null) {
			return superAccessOrder;
		}
		XmlAccessOrder packageAccessOrder = getPackageAccessOrder();
		if (packageAccessOrder != null) {
			return packageAccessOrder;
		}
		return XmlAccessOrder.UNDEFINED;
	}

	protected XmlAccessOrder getSuperClassAccessOrder() {
		JaxbClass superClass = this.getSuperClass();
		return superClass == null ? null : superClass.getSpecifiedAccessOrder();
	}

	protected XmlAccessOrder getPackageAccessOrder() {
		JaxbPackageInfo packageInfo = this.getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessOrder();
	}


	// ********** attributes **********

	public Iterable<JaxbPersistentAttribute> getAttributes() {
		return this.attributesContainer.getAttributes();
	}

	public int getAttributesSize() {
		return this.attributesContainer.getAttributesSize();
	}

	protected JaxbAttributesContainer.Owner buildAttributesContainerOwner() {
		return new JaxbAttributesContainer.Owner() {
			public XmlAccessType getAccessType() {
				return GenericJavaPersistentClass.this.getAccessType();
			}
			
			public void fireAttributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaPersistentClass.this.fireItemAdded(ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void fireAttributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaPersistentClass.this.fireItemRemoved(ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}


	// ********** inherited attributes **********

	public Iterable<JaxbPersistentAttribute> getInheritedAttributes() {
		return new CompositeIterable<JaxbPersistentAttribute>(this.getInheritedAttributeSets());
	}

	protected Iterable<Iterable<JaxbPersistentAttribute>> getInheritedAttributeSets() {
		return new TransformationIterable<JaxbAttributesContainer, Iterable<JaxbPersistentAttribute>>(this.getInheritedAttributesContainers()) {
			@Override
			protected Iterable<JaxbPersistentAttribute> transform(JaxbAttributesContainer attributesContainer) {
				return attributesContainer.getAttributes();
			}
		};
	}

	protected Iterable<JaxbAttributesContainer> getInheritedAttributesContainers() {
		return new LiveCloneIterable<JaxbAttributesContainer>(this.inheritedAttributesContainers.values());  // read-only
	}

	public int getInheritedAttributesSize() {
		int size = 0;
		for (JaxbAttributesContainer attributesContainer : getInheritedAttributesContainers()) {
			size += attributesContainer.getAttributesSize();
		}
		return size;
	}

	protected void initializeInheritedAttributes() {
		this.addInheritedAttributesContainer(this.getSuperClass());
	}

	protected void addInheritedAttributesContainer(JaxbClass superClass) {
		if (superClass != null) {
			if (superClass.getKind() == Kind.TRANSIENT) {
				this.inheritedAttributesContainers.put(superClass, this.buildInheritedAttributesContainer(superClass));
				this.addInheritedAttributesContainer(superClass.getSuperClass());
			}
		}
	}

	protected JaxbAttributesContainer buildInheritedAttributesContainer(JaxbClass jaxbClass) {
		return new GenericJavaAttributesContainer(this, buildInheritedAttributesContainerOwner(), jaxbClass.getJavaResourceType());
	}

	protected JaxbAttributesContainer.Owner buildInheritedAttributesContainerOwner() {
		return new JaxbAttributesContainer.Owner() {
			public XmlAccessType getAccessType() {
				return GenericJavaPersistentClass.this.getAccessType();
			}
			
			public void fireAttributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaPersistentClass.this.fireItemAdded(INHERITED_ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void fireAttributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaPersistentClass.this.fireItemRemoved(INHERITED_ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}

	protected void syncInheritedAttributes() {
		for (JaxbAttributesContainer attributesContainer : this.inheritedAttributesContainers.values()) {
			attributesContainer.synchronizeWithResourceModel();
		}
	}

	/**
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateInheritedAttributes() {
		HashSet<JaxbClass> contextSuperclasses = CollectionTools.set(this.inheritedAttributesContainers.keySet());
		for (JaxbClass superClass : getAncestors()) {
			if (superClass.getKind() == Kind.TRANSIENT) {
				boolean match = false;
				for (Iterator<JaxbClass> stream = contextSuperclasses.iterator(); stream.hasNext(); ) {
					JaxbClass contextSuperclass = stream.next();
					if (contextSuperclass == superClass) {
						stream.remove();
						this.inheritedAttributesContainers.get(contextSuperclass).update();
						match = true;
						break;
					}
				}
				if ( ! match) {
					JaxbAttributesContainer container = this.buildInheritedAttributesContainer(superClass);
					this.inheritedAttributesContainers.put(superClass, container);
					this.fireItemsAdded(INHERITED_ATTRIBUTES_COLLECTION, CollectionTools.collection(container.getAttributes()));
				}
			}
		}

		for (JaxbClass superClass : contextSuperclasses) {
			JaxbAttributesContainer container = this.inheritedAttributesContainers.remove(superClass);
			this.fireItemsRemoved(INHERITED_ATTRIBUTES_COLLECTION, CollectionTools.collection(container.getAttributes()));
		}
	}


	public boolean isInherited(JaxbPersistentAttribute attribute) {
		if (attribute.getParent() != this) {
			throw new IllegalArgumentException("The attribute is not owned by this GenericJavaPersistentClass"); //$NON-NLS-1$
		}
		return !CollectionTools.contains(this.getAttributes(), attribute);
	}

	public String getJavaResourceAttributeOwningTypeName(JaxbPersistentAttribute attribute) {
		if (attribute.getParent() != this) {
			throw new IllegalArgumentException("The attribute is not owned by this GenericJavaPersistentClass"); //$NON-NLS-1$
		}
		for (JaxbClass inheritedClass : this.inheritedAttributesContainers.keySet()) {
			if (CollectionTools.contains(this.inheritedAttributesContainers.get(inheritedClass).getAttributes(), attribute)) {
				return inheritedClass.getSimpleName();
			}
		}
		throw new IllegalArgumentException("The attribute is not an inherited attribute"); //$NON-NLS-1$
	}


	//****************** XmlJavaTypeAdapter *********************

	public XmlAdaptable buildXmlAdaptable() {
		return new GenericJavaXmlAdaptable(this, new XmlAdaptable.Owner() {
			public JavaResourceAnnotatedElement getResource() {
				return getJavaResourceType();
			}
			public XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation adapterAnnotation) {
				return GenericJavaPersistentClass.this.buildXmlJavaTypeAdapter(adapterAnnotation);
			}
			public void fireXmlAdapterChanged(XmlJavaTypeAdapter oldAdapter, XmlJavaTypeAdapter newAdapter) {
				GenericJavaPersistentClass.this.firePropertyChanged(XML_JAVA_TYPE_ADAPTER_PROPERTY, oldAdapter, newAdapter);
			}
		});
	}

	public XmlJavaTypeAdapter getXmlJavaTypeAdapter() {
		return this.xmlAdaptable.getXmlJavaTypeAdapter();
	}

	public XmlJavaTypeAdapter addXmlJavaTypeAdapter() {
		return this.xmlAdaptable.addXmlJavaTypeAdapter();
	}

	protected XmlJavaTypeAdapter buildXmlJavaTypeAdapter(XmlJavaTypeAdapterAnnotation xmlJavaTypeAdapterAnnotation) {
		return new GenericJavaTypeXmlJavaTypeAdapter(this, xmlJavaTypeAdapterAnnotation);
	}

	public void removeXmlJavaTypeAdapter() {
		this.xmlAdaptable.removeXmlJavaTypeAdapter();
	}

	// ********** content assist **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (!CollectionTools.isEmpty(result)) {
			return result;
		}
		for (JaxbPersistentAttribute attribute : this.getAttributes()) {
			result = attribute.getJavaCompletionProposals(pos, filter, astRoot);
			if (!CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		return EmptyIterable.instance();
	}

	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.xmlAdaptable.validate(messages, reporter, astRoot);
		this.validateXmlAnyAttributeMapping(messages, astRoot);
		this.validateXmlAnyElementMapping(messages, astRoot);
		this.validateXmlValueMapping(messages, astRoot);
		this.validateXmlIDs(messages, astRoot);
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			attribute.validate(messages, reporter, astRoot);
		}
	}

	protected void validateXmlValueMapping(List<IMessage> messages, CompilationUnit astRoot) {
		String xmlValueMapping = null;
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY) {
				if (xmlValueMapping != null) {
					messages.add(
						DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.MULTIPLE_XML_VALUE_MAPPINGS_DEFINED,
							new String[] {attribute.getName(), xmlValueMapping},
							attribute.getMapping(),
							attribute.getMapping().getValidationTextRange(astRoot)));
				}
				else {
					xmlValueMapping = attribute.getName();
				}
			}
		}
		if (xmlValueMapping != null) {
			for (JaxbPersistentAttribute attribute : getAttributes()) {
				if (attribute.getName() != xmlValueMapping) {
					if (attribute.getMappingKey() != MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY 
						&& attribute.getMappingKey() != MappingKeys.XML_TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
						messages.add(
							DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_VALUE_MAPPING_WITH_NON_XML_ATTRIBUTE_MAPPING_DEFINED,
								new String[] {attribute.getName(), xmlValueMapping},
								attribute.getMapping(),
								attribute.getMapping().getValidationTextRange(astRoot)));					
					}
				}
			}
		}
	}

	protected void validateXmlAnyAttributeMapping(List<IMessage> messages, CompilationUnit astRoot) {
		String xmlAnyAttributeMapping = null;
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY) {
				if (xmlAnyAttributeMapping != null) {
					messages.add(
						DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.MULTIPLE_XML_ANY_ATTRIBUTE_MAPPINGS_DEFINED,
							new String[] {attribute.getName(), xmlAnyAttributeMapping},
							attribute.getMapping(),
							attribute.getMapping().getValidationTextRange(astRoot)));
				}
				else {
					xmlAnyAttributeMapping = attribute.getName();
				}
			}
		}
	}

	protected void validateXmlAnyElementMapping(List<IMessage> messages, CompilationUnit astRoot) {
		String xmlAnyElementMapping = null;
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY) {
				if (xmlAnyElementMapping != null) {
					messages.add(
						DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.MULTIPLE_XML_ANY_ELEMENT_MAPPINGS_DEFINED,
							new String[] {attribute.getName(), xmlAnyElementMapping},
							attribute.getMapping(),
							attribute.getMapping().getValidationTextRange(astRoot)));
				}
				else {
					xmlAnyElementMapping = attribute.getName();
				}
			}
		}
	}

	protected void validateXmlIDs(List<IMessage> messages, CompilationUnit astRoot) {
		String xmlIdMapping = null;
		for (JaxbContainmentMapping containmentMapping : getContainmentMappingsWithXmlID()) {
			if (xmlIdMapping != null) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.MULTIPLE_XML_IDS_DEFINED,
						new String[] {containmentMapping.getParent().getName(), xmlIdMapping},
						containmentMapping,
						containmentMapping.getValidationTextRange(astRoot)));
			}
			else {
				xmlIdMapping = containmentMapping.getParent().getName();
			}
		}
	}

	protected Iterable<JaxbContainmentMapping> getContainmentMappingsWithXmlID(){
		return new FilteringIterable<JaxbContainmentMapping>(this.getContainmentMappings()){
			@Override
			protected boolean accept(JaxbContainmentMapping containmentMapping) {
				return containmentMapping.getXmlID() != null;
			}
		};
	}

	protected Iterable<JaxbContainmentMapping> getContainmentMappings() {
		return new SubIterableWrapper<JaxbAttributeMapping, JaxbContainmentMapping>(this.getContainmentMappings_());
	}

	protected Iterable<JaxbAttributeMapping> getContainmentMappings_(){
		return new FilteringIterable<JaxbAttributeMapping>(this.getAttributeMappings()){
			@Override
			protected boolean accept(JaxbAttributeMapping attributeMapping) {
				return (attributeMapping.getKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY
					|| attributeMapping.getKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
			}
		};
	}

	private Iterable<? extends JaxbAttributeMapping> getAttributeMappings() {
		return new TransformationIterable<JaxbPersistentAttribute, JaxbAttributeMapping>(this.getAttributes()) {
			@Override
			protected JaxbAttributeMapping transform(JaxbPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}

	public boolean containsXmlId() {
		return !CollectionTools.isEmpty(getContainmentMappingsWithXmlID());
	}
}
