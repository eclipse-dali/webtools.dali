/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbBasicMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbClass;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.JaxbType;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.internal.validation.DefaultValidationMessages;
import org.eclipse.jpt.jaxb.core.internal.validation.JaxbValidationMessages;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.omg.CORBA.PUBLIC_MEMBER;


public class GenericJavaClassMapping
		extends AbstractJavaTypeMapping
		implements JaxbClassMapping {
	
	protected String factoryClass;
	
	protected String factoryMethod;
	
	protected final PropOrderContainer propOrderContainer;
	
	protected JaxbClassMapping superclass;
	
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;
	
	protected boolean hasRootElementInHierarchy_loaded = false;
	protected boolean hasRootElementInHierarchy = false;
	
	protected final JaxbAttributesContainer attributesContainer;
	
	protected final Map<JaxbClassMapping, JaxbAttributesContainer> inheritedAttributesContainers;
	
	public GenericJavaClassMapping(JaxbClass parent) {
		super(parent);
		this.inheritedAttributesContainers = new HashMap<JaxbClassMapping, JaxbAttributesContainer>();
		this.propOrderContainer = new PropOrderContainer();
		
		initFactoryClass();
		initFactoryMethod();
		initPropOrder();
		initSpecifiedAccessType();
		initDefaultAccessType();
		initSpecifiedAccessOrder();
		initDefaultAccessOrder();
		this.attributesContainer = new GenericJavaAttributesContainer(this, buildAttributesContainerOwner(), getJavaResourceType());
		initInheritedAttributes();
	}
	
	
	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}
	
	@Override
	public JaxbClass getJaxbType() {
		return (JaxbClass) super.getJaxbType();
	}
	
	protected JaxbPackageInfo getPackageInfo() {
		JaxbPackage jaxbPackage = getJaxbPackage();
		// jaxb package may be null during initialization/update
		return (jaxbPackage == null) ? null : jaxbPackage.getPackageInfo();
	}
	
	
	// ***** sync/update *****
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		syncFactoryClass();
		syncFactoryMethod();
		syncPropOrder();
		syncSpecifiedAccessType();
		syncSpecifiedAccessOrder();
		this.attributesContainer.synchronizeWithResourceModel();
		syncInheritedAttributes();
	}
	
	@Override
	public void update() {
		super.update();
		updateSuperclass();
		updateDefaultAccessType();
		updateDefaultAccessOrder();
		this.hasRootElementInHierarchy_loaded = false; // triggers that the value must be recalculated on next request
		this.attributesContainer.update();
		updateInheritedAttributes();
	}
	
	
	// ***** factory class *****
	
	public String getFactoryClass() {
		return this.factoryClass;
	}
	
	public void setFactoryClass(String factoryClass) {
		getXmlTypeAnnotation().setFactoryClass(factoryClass);
		setFactoryClass_(factoryClass);	
	}
	
	protected void setFactoryClass_(String factoryClass) {
		String old = this.factoryClass;
		this.factoryClass = factoryClass;
		firePropertyChanged(FACTORY_CLASS_PROPERTY, old, factoryClass);
	}
	
	protected String getResourceFactoryClass() {
		return getXmlTypeAnnotation().getFactoryClass();
	}
	
	protected void initFactoryClass() {
		this.factoryClass = getResourceFactoryClass();
	}
	
	protected void syncFactoryClass() {
		setFactoryClass_(getResourceFactoryClass());
	}
	
	
	// ***** factory method *****
	
	public String getFactoryMethod() {
		return this.factoryMethod;
	}
	
	public void setFactoryMethod(String factoryMethod) {
		getXmlTypeAnnotation().setFactoryMethod(factoryMethod);
		setFactoryMethod_(factoryMethod);	
	}
	
	protected void setFactoryMethod_(String factoryMethod) {
		String old = this.factoryMethod;
		this.factoryMethod = factoryMethod;
		firePropertyChanged(FACTORY_METHOD_PROPERTY, old, factoryMethod);
	}
	
	protected String getResourceFactoryMethod() {
		return getXmlTypeAnnotation().getFactoryMethod();
	}
	
	protected void initFactoryMethod() {
		this.factoryMethod = getResourceFactoryMethod();
	}
	
	protected void syncFactoryMethod() {
		setFactoryMethod_(getResourceFactoryMethod());
	}
	
	
	// ***** prop order *****
	
	public ListIterable<String> getPropOrder() {
		return this.propOrderContainer.getContextElements();
	}
	
	public int getPropOrderSize() {
		return this.propOrderContainer.getContextElementsSize();
	}
	
	public void addProp(int index, String prop) {
		getXmlTypeAnnotation().addProp(index, prop);
		this.propOrderContainer.addContextElement(index, prop);
	}
	
	public void removeProp(String prop) {
		this.removeProp(this.propOrderContainer.indexOfContextElement(prop));
	}
	
	public void removeProp(int index) {
		this.getXmlTypeAnnotation().removeProp(index);
		this.propOrderContainer.removeContextElement(index);
	}
	
	public void moveProp(int targetIndex, int sourceIndex) {
		this.getXmlTypeAnnotation().moveProp(targetIndex, sourceIndex);
		this.propOrderContainer.moveContextElement(targetIndex, sourceIndex);
	}
	
	protected void initPropOrder() {
		this.propOrderContainer.initialize();
	}
	
	protected void syncPropOrder() {
		this.propOrderContainer.synchronizeWithResourceModel();
	}
	
	protected ListIterable<String> getResourcePropOrder() {
		return this.getXmlTypeAnnotation().getPropOrder();
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
		getXmlAccessorTypeAnnotation().setValue(XmlAccessType.toJavaResourceModel(access));
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
	 * Default access type is determined by the following, in order of precedence:
	 * - @XmlAccessorType annotation on a mapped super class
	 * - @XmlAccessorType annotation on the package
	 * - default access type of {@link PUBLIC_MEMBER}
	 */
	protected XmlAccessType buildDefaultAccessType() {
		XmlAccessType accessType = getSuperclassAccessType();
		if (accessType != null) {
			return accessType;
		}
		accessType = getPackageAccessType();
		if (accessType != null) {
			return accessType;
		}
		return XmlAccessType.PUBLIC_MEMBER;
	}
	
	protected XmlAccessType getSuperclassAccessType() {
		return this.superclass == null ? null : this.superclass.getSpecifiedAccessType();
	}
	
	protected XmlAccessType getPackageAccessType() {
		JaxbPackageInfo packageInfo = getPackageInfo();
		return (packageInfo == null) ? null : packageInfo.getAccessType();
	}
	
	protected XmlAccessorTypeAnnotation getXmlAccessorTypeAnnotation() {
		return (XmlAccessorTypeAnnotation) getJavaResourceType().getNonNullAnnotation(JAXB.XML_ACCESSOR_TYPE);
	}
	
	protected XmlAccessType getResourceAccessType() {
		return XmlAccessType.fromJavaResourceModel(getXmlAccessorTypeAnnotation().getValue());
	}
	
	protected void initSpecifiedAccessType() {
		this.specifiedAccessType = getResourceAccessType();
	}
	
	protected void syncSpecifiedAccessType() {
		setSpecifiedAccessType_(getResourceAccessType());
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
		getXmlAccessorOrderAnnotation().setValue(XmlAccessOrder.toJavaResourceModel(accessOrder));
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
	 * Default access order is determined by the following, in order of precedence:
	 * - @XmlAccessorOrder annotation on a mapped super class
	 * - @XmlAccessorOrder annotation on the package
	 * - default access order of {@link UNDEFINED}
	 */
	protected XmlAccessOrder buildDefaultAccessOrder() {
		XmlAccessOrder accessOrder = getSuperclassAccessOrder();
		if (accessOrder != null) {
			return accessOrder;
		}
		accessOrder = getPackageAccessOrder();
		if (accessOrder != null) {
			return accessOrder;
		}
		return XmlAccessOrder.UNDEFINED;
	}
	
	protected XmlAccessOrder getSuperclassAccessOrder() {
		JaxbClassMapping superclass = this.superclass;
		while (superclass != null) {
			XmlAccessOrder accessOrder = superclass.getSpecifiedAccessOrder();
			if (accessOrder != null) {
				return accessOrder;
			}
			superclass = superclass.getSuperclass();
		}
		return null;
	}
	
	protected XmlAccessOrder getPackageAccessOrder() {
		JaxbPackageInfo packageInfo = getPackageInfo();
		return packageInfo == null ? null : packageInfo.getAccessOrder();
	}
	
	protected XmlAccessorOrderAnnotation getXmlAccessorOrderAnnotation() {
		return (XmlAccessorOrderAnnotation) getJavaResourceType().getNonNullAnnotation(JAXB.XML_ACCESSOR_ORDER);
	}
	
	protected XmlAccessOrder getResourceAccessOrder() {
		return XmlAccessOrder.fromJavaResourceModel(getXmlAccessorOrderAnnotation().getValue());
	}
	
	protected void initSpecifiedAccessOrder() {
		this.specifiedAccessOrder = getResourceAccessOrder();
	}
	
	protected void syncSpecifiedAccessOrder() {
		setSpecifiedAccessOrder_(getResourceAccessOrder());
	}
	
	
	// ********** super class **********
	
	public JaxbClassMapping getSuperclass() {
		return this.superclass;
	}
	
	protected void setSuperclass_(JaxbClassMapping superclass) {
		JaxbClassMapping old = this.superclass;
		this.superclass = superclass;
		this.firePropertyChanged(SUPERCLASS_PROPERTY, old, superclass);
	}
	
	protected void updateSuperclass() {
		setSuperclass_(findSuperclass());
	}
	
	protected JaxbClassMapping findSuperclass() {
		JavaResourceType resourceType = getSuperclass(getJavaResourceType());
		while (resourceType != null && resourceType != this) {
			JaxbType jaxbType = getJaxbProject().getContextRoot().getType(resourceType.getQualifiedName());
			
			// if the superclass is not a class, return null
			if (jaxbType == null || jaxbType.getKind() != JaxbType.Kind.CLASS) {
				return null;
			}
			
			JaxbClassMapping jaxbClassMapping = ((JaxbClass) jaxbType).getMapping();
			// rare for a non-null superclass to not be mapped, but potentially possible mid-update
			if (jaxbClassMapping != null) {  
				return jaxbClassMapping;
			}
			else {
				resourceType = getSuperclass(resourceType);
			}
		}
		return null;
	}
	
	protected JavaResourceType getSuperclass(JavaResourceType resourceType) {
		String superclassName = resourceType.getSuperclassQualifiedName();
		if (superclassName == null) {
			return null;
		}
		
		return (JavaResourceType) getJaxbProject().getJavaResourceType(
				superclassName, JavaResourceType.Kind.TYPE);
	}
	
	
	// ***** attributes *****
	
	public Iterable<JaxbPersistentAttribute> getAttributes() {
		return this.attributesContainer.getAttributes();
	}
	
	public int getAttributesSize() {
		return this.attributesContainer.getAttributesSize();
	}
	
	protected JaxbAttributesContainer.Owner buildAttributesContainerOwner() {
		return new JaxbAttributesContainer.Owner() {
			public XmlAccessType getAccessType() {
				return GenericJavaClassMapping.this.getAccessType();
			}
			
			public void fireAttributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemAdded(ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void fireAttributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemRemoved(ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}
	
	
	// ***** inherited attributes *****
	
	public Iterable<JaxbPersistentAttribute> getInheritedAttributes() {
		return new CompositeIterable<JaxbPersistentAttribute>(getInheritedAttributeSets());
	}
	
	protected Iterable<Iterable<JaxbPersistentAttribute>> getInheritedAttributeSets() {
		return new TransformationIterable<JaxbAttributesContainer, Iterable<JaxbPersistentAttribute>>(
				getInheritedAttributesContainers()) {
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
	
	protected void initInheritedAttributes() {
		JaxbClassMapping superclass = this.superclass;
		// only add inherited attributes for superclasses up until a mapped class is encountered
		while (superclass != null && superclass.isXmlTransient()) {
			this.inheritedAttributesContainers.put(superclass, buildInheritedAttributesContainer(superclass));
			superclass = superclass.getSuperclass();
		}
	}
	
	protected void syncInheritedAttributes() {
		for (JaxbAttributesContainer attributesContainer : this.inheritedAttributesContainers.values()) {
			attributesContainer.synchronizeWithResourceModel();
		}
	}
	
	protected void updateInheritedAttributes() {
		HashSet<JaxbClassMapping> oldSuperclasses 
				= CollectionTools.set(this.inheritedAttributesContainers.keySet());
		Set<JaxbPersistentAttribute> oldAttributes = CollectionTools.set(getInheritedAttributes());
		JaxbClassMapping superclass = this.superclass;
		// only add inherited attributes for superclasses up until a mapped class is encountered
		while (superclass != null && superclass.isXmlTransient()) {
			if (this.inheritedAttributesContainers.containsKey(superclass)) {
				this.inheritedAttributesContainers.get(superclass).update();
				oldSuperclasses.remove(superclass);
			}
			else {
				this.inheritedAttributesContainers.put(superclass, buildInheritedAttributesContainer(superclass));
			}
			superclass = superclass.getSuperclass();
		}
		
		for (JaxbClassMapping oldSuperclass : oldSuperclasses) {
			this.inheritedAttributesContainers.remove(oldSuperclass);
		}
		
		Set<JaxbPersistentAttribute> newAttributes = CollectionTools.set(getInheritedAttributes());
		if (CollectionTools.elementsAreDifferent(oldAttributes, newAttributes)) {
			fireCollectionChanged(INHERITED_ATTRIBUTES_COLLECTION, newAttributes);
		}
	}
	
	protected JaxbAttributesContainer buildInheritedAttributesContainer(JaxbClassMapping jaxbClassMapping) {
		return new GenericJavaAttributesContainer(this, buildInheritedAttributesContainerOwner(), jaxbClassMapping.getJaxbType().getJavaResourceType());
	}
	
	protected JaxbAttributesContainer.Owner buildInheritedAttributesContainerOwner() {
		return new JaxbAttributesContainer.Owner() {
			public XmlAccessType getAccessType() {
				return GenericJavaClassMapping.this.getAccessType();
			}
			
			public void fireAttributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemAdded(INHERITED_ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void fireAttributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemRemoved(INHERITED_ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}
	
	public boolean isInherited(JaxbPersistentAttribute attribute) {
		if (attribute.getParent() != this) {
			throw new IllegalArgumentException("The attribute is not owned by this JaxbClassMapping"); //$NON-NLS-1$
		}
		return ! CollectionTools.contains(this.getAttributes(), attribute);
	}
	
	public String getJavaResourceAttributeOwningTypeName(JaxbPersistentAttribute attribute) {
		if (attribute.getParent() != this) {
			throw new IllegalArgumentException("The attribute is not owned by this JaxbClassMapping"); //$NON-NLS-1$
		}
		for (JaxbClassMapping superclass : this.inheritedAttributesContainers.keySet()) {
			if (CollectionTools.contains(this.inheritedAttributesContainers.get(superclass).getAttributes(), attribute)) {
				return superclass.getJaxbType().getSimpleName();
			}
		}
		throw new IllegalArgumentException("The attribute is not an inherited attribute"); //$NON-NLS-1$
	}
	
	
	// ***** subClasses *****
	
	@Override
	public boolean hasRootElementInHierarchy() {
		if (! this.hasRootElementInHierarchy_loaded) {
			this.hasRootElementInHierarchy = calculateHasRootElementInHierarchy();
			this.hasRootElementInHierarchy_loaded = true;
		}
		return this.hasRootElementInHierarchy;
	}
	
	protected boolean calculateHasRootElementInHierarchy() {
		if (this.getXmlRootElement() != null) {
			return true;
		}
		
		for (JaxbType jaxbType : getJaxbProject().getContextRoot().getTypes()) {
			if (jaxbType.getMapping() != null 
					&& ! jaxbType.getMapping().isXmlTransient() 
					&& jaxbType.getMapping().getXmlRootElement() != null
					&& JDTTools.typeIsSubType(
							getJaxbProject().getJavaProject(),
							jaxbType.getFullyQualifiedName(), getJaxbType().getFullyQualifiedName())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	// ***** misc *****
	
	@Override
	protected Iterable<String> getNonTransientReferencedXmlTypeNames() {
		return new CompositeIterable<String>(
				super.getNonTransientReferencedXmlTypeNames(),
				new SingleElementIterable(getJavaResourceType().getSuperclassQualifiedName()),
				new CompositeIterable<String>(
						new TransformationIterable<JaxbPersistentAttribute, Iterable<String>>(getAttributes()) {
							@Override
							protected Iterable<String> transform(JaxbPersistentAttribute o) {
								return o.getMapping().getReferencedXmlTypeNames();
							}
						}));
	}
	
	public boolean containsXmlId() {
		return ! CollectionTools.isEmpty(getBasicMappingsWithXmlID());
	}
	
	protected Iterable<JaxbBasicMapping> getBasicMappingsWithXmlID(){
		return new FilteringIterable<JaxbBasicMapping>(getBasicMappings()){
			@Override
			protected boolean accept(JaxbBasicMapping basicMapping) {
				return basicMapping.getXmlID() != null;
			}
		};
	}
	
	protected Iterable<JaxbBasicMapping> getBasicMappings() {
		return new SubIterableWrapper<JaxbAttributeMapping, JaxbBasicMapping>(getBasicMappings_());
	}
	
	protected Iterable<JaxbAttributeMapping> getBasicMappings_(){
		return new FilteringIterable<JaxbAttributeMapping>(getAttributeMappings()){
			@Override
			protected boolean accept(JaxbAttributeMapping attributeMapping) {
				return (attributeMapping.getKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY
						|| attributeMapping.getKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
			}
		};
	}
	
	protected Iterable<? extends JaxbAttributeMapping> getAttributeMappings() {
		return new TransformationIterable<JaxbPersistentAttribute, JaxbAttributeMapping>(getAttributes()) {
			@Override
			protected JaxbAttributeMapping transform(JaxbPersistentAttribute attribute) {
				return attribute.getMapping();
			}
		};
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getJavaCompletionProposals(
			int pos, Filter<String> filter, CompilationUnit astRoot) {
		
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (! CollectionTools.isEmpty(result)) {
			return result;
		}
		
		// TODO - factory methods?
		
		for (JaxbPersistentAttribute attribute : this.getAttributes()) {
			result = attribute.getJavaCompletionProposals(pos, filter, astRoot);
			if (!CollectionTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		
		// TODO - factory method?
		
		validateXmlAnyAttributeMapping(messages, astRoot);
		validateXmlAnyElementMapping(messages, astRoot);
		validateXmlValueMapping(messages, astRoot);
		validateXmlIDs(messages, astRoot);
		
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
		for (JaxbBasicMapping containmentMapping : getBasicMappingsWithXmlID()) {
			if (xmlIdMapping != null) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.MULTIPLE_XML_IDS_DEFINED,
						new String[] { containmentMapping.getPersistentAttribute().getName(), xmlIdMapping },
						containmentMapping,
						containmentMapping.getValidationTextRange(astRoot)));
			}
			else {
				xmlIdMapping = containmentMapping.getPersistentAttribute().getName();
			}
		}
	}
	
	
	/**
	 * xml prop order container
	 */
	protected class PropOrderContainer
			extends ListContainer<String, String> {
		
		@Override
		protected String getContextElementsPropertyName() {
			return PROP_ORDER_LIST;
		}
		
		@Override
		protected String buildContextElement(String resourceElement) {
			return resourceElement;
		}
		
		@Override
		protected ListIterable<String> getResourceElements() {
			return GenericJavaClassMapping.this.getResourcePropOrder();
		}
		
		@Override
		protected String getResourceElement(String contextElement) {
			return contextElement;
		}
	}
}
