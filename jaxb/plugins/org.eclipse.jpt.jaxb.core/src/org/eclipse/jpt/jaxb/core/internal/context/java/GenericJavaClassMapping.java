/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.collection.Bag;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.CompositeIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyListIterable;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.LiveCloneIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SingleElementIterable;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.MappingKeys;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributeMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer.Context;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPackageInfo;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.XmlNamedNodeMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaClass;
import org.eclipse.jpt.jaxb.core.context.java.JavaClassMapping;
import org.eclipse.jpt.jaxb.core.context.java.JavaType;
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
		implements JavaClassMapping {
	
	protected String specifiedFactoryClass;
	
	protected String factoryMethod;
	
	protected final PropOrderContainer propOrderContainer;
	
	protected String superclassName; // used internally only
	protected static final String SUPERCLASSNAME_PROPERTY = "superclassName"; //$NON-NLS-1$ - used only to trigger update
	protected JaxbClassMapping superclass;
	
	protected XmlAccessType defaultAccessType;
	protected XmlAccessType specifiedAccessType;
	
	protected XmlAccessOrder defaultAccessOrder;
	protected XmlAccessOrder specifiedAccessOrder;
	
	protected boolean hasRootElementInHierarchy_loaded = false;
	protected boolean hasRootElementInHierarchy = false;
	
	protected final JaxbAttributesContainer attributesContainer;
	
	protected final Map<JaxbClassMapping, JaxbAttributesContainer> includedAttributesContainers;
	
	public GenericJavaClassMapping(JavaClass parent) {
		super(parent);
		this.includedAttributesContainers = new Hashtable<JaxbClassMapping, JaxbAttributesContainer>();
		this.propOrderContainer = new PropOrderContainer();
		
		initFactoryClass();
		initFactoryMethod();
		initPropOrder();
		initSpecifiedAccessType();
		initDefaultAccessType();
		initSpecifiedAccessOrder();
		initDefaultAccessOrder();
		initSuperclassName();
		this.attributesContainer = new GenericJavaAttributesContainer(this, buildAttributesContainerOwner(), getJavaResourceType());
	}
	
	
	@Override
	public JavaResourceType getJavaResourceType() {
		return (JavaResourceType) super.getJavaResourceType();
	}
	
	@Override
	public JavaClass getJavaType() {
		return (JavaClass) super.getJavaType();
	}
	
	public JaxbPackageInfo getPackageInfo() {
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
		syncSuperclassName();
		this.attributesContainer.synchronizeWithResourceModel();
		syncIncludedAttributes();
	}
	
	@Override
	public void update() {
		super.update();
		updateSuperclass(); // done first because much depends on it
		updateDefaultAccessType();
		updateDefaultAccessOrder();
		this.hasRootElementInHierarchy_loaded = false; // triggers that the value must be recalculated on next request
		this.attributesContainer.update();
		updateIncludedAttributes();
	}
	
	
	// ***** factory class *****
	
	public String getFactoryClass() {
		return (this.specifiedFactoryClass != null) ? this.specifiedFactoryClass : JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS;
	}
	
	public String getSpecifiedFactoryClass() {
		return this.specifiedFactoryClass;
	}
	
	public void setSpecifiedFactoryClass(String factoryClass) {
		getXmlTypeAnnotation().setFactoryClass(factoryClass);
		setSpecifiedFactoryClass_(factoryClass);	
	}
	
	protected void setSpecifiedFactoryClass_(String factoryClass) {
		String old = this.specifiedFactoryClass;
		this.specifiedFactoryClass = factoryClass;
		firePropertyChanged(SPECIFIED_FACTORY_CLASS_PROPERTY, old, factoryClass);
	}
	
	protected String getResourceFactoryClass() {
		return getXmlTypeAnnotation().getFactoryClass();
	}
	
	protected void initFactoryClass() {
		this.specifiedFactoryClass = getResourceFactoryClass();
	}
	
	protected void syncFactoryClass() {
		setSpecifiedFactoryClass_(getResourceFactoryClass());
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
	
	public String getProp(int index) {
		return this.propOrderContainer.getContextElement(index);
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
		ListIterable<String> result = getXmlTypeAnnotation().getPropOrder();
		if (IterableTools.size(result) == 1 && StringTools.EMPTY_STRING.equals(IterableTools.get(result, 0))) {
			return EmptyListIterable.instance();
		}
		return result;
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
	
	protected void initSuperclassName() {
		this.superclassName = getResourceSuperclassName();
	}
	
	protected void syncSuperclassName() {
		String old = this.superclassName;
		this.superclassName = getResourceSuperclassName();
		firePropertyChanged(SUPERCLASSNAME_PROPERTY, old, this.superclassName);
	}
	
	protected String getResourceSuperclassName() {
		return getJavaResourceType().getSuperclassQualifiedName();
	}
	
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
		if (this.superclassName != null) {
			return getJaxbProject().getContextRoot().getClassMapping(this.superclassName);
		}
		return null;
	}
	
	
	// ***** attributes *****
	
	public Iterable<JaxbPersistentAttribute> getAttributes() {
		return this.attributesContainer.getAttributes();
	}
	
	public int getAttributesSize() {
		return this.attributesContainer.getAttributesSize();
	}
	
	protected GenericJavaAttributesContainer.Context buildAttributesContainerOwner() {
		return new GenericJavaAttributesContainer.Context() {
			public XmlAccessType getAccessType() {
				return GenericJavaClassMapping.this.getAccessType();
			}
			
			public void attributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemAdded(ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void attributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemRemoved(ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}
	
	
	// ***** included attributes *****
	
	public Iterable<JaxbPersistentAttribute> getIncludedAttributes() {
		return IterableTools.compositeIterable(getIncludedAttributesContainers(), JaxbAttributesContainer.ATTRIBUTES_TRANSFORMER);
	}
	
	protected Iterable<JaxbAttributesContainer> getIncludedAttributesContainers() {
		return new LiveCloneIterable<JaxbAttributesContainer>(this.includedAttributesContainers.values());  // read-only
	}
	
	public int getIncludedAttributesSize() {
		int size = 0;
		for (JaxbAttributesContainer attributesContainer : getIncludedAttributesContainers()) {
			size += attributesContainer.getAttributesSize();
		}
		return size;
	}
	
	protected void syncIncludedAttributes() {
		for (JaxbAttributesContainer attributesContainer : this.includedAttributesContainers.values()) {
			attributesContainer.synchronizeWithResourceModel();
		}
	}
	
	protected void updateIncludedAttributes() {
		HashSet<JaxbClassMapping> oldSuperclasses 
				= CollectionTools.set(this.includedAttributesContainers.keySet());
		Set<JaxbPersistentAttribute> oldAttributes = CollectionTools.set(getIncludedAttributes());
		
		if (! isXmlTransient()) {
			JaxbClassMapping superclass = this.superclass;
			// only add inherited attributes for superclasses up until a mapped class is encountered
			while (superclass != null && superclass.isXmlTransient()) {
				if (this.includedAttributesContainers.containsKey(superclass)) {
					this.includedAttributesContainers.get(superclass).update();
					oldSuperclasses.remove(superclass);
				}
				else {
					this.includedAttributesContainers.put(superclass, buildIncludedAttributesContainer(superclass));
				}
				superclass = superclass.getSuperclass();
			}
		}
		
		for (JaxbClassMapping oldSuperclass : oldSuperclasses) {
			this.includedAttributesContainers.remove(oldSuperclass);
		}
		
		Set<JaxbPersistentAttribute> newAttributes = CollectionTools.set(getIncludedAttributes());
		if (IterableTools.elementsAreDifferent(oldAttributes, newAttributes)) {
			fireCollectionChanged(INCLUDED_ATTRIBUTES_COLLECTION, newAttributes);
		}
	}
	
	protected JaxbAttributesContainer buildIncludedAttributesContainer(JaxbClassMapping classMapping) {
		return classMapping.buildIncludedAttributesContainer(this, buildIncludedAttributesContainerOwner());
	}
	
	protected JaxbAttributesContainer.Context buildIncludedAttributesContainerOwner() {
		return new GenericJavaAttributesContainer.Context() {
			public XmlAccessType getAccessType() {
				return GenericJavaClassMapping.this.getAccessType();
			}
			
			public void attributeAdded(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemAdded(INCLUDED_ATTRIBUTES_COLLECTION, attribute);
			}
			
			public void attributeRemoved(JaxbPersistentAttribute attribute) {
				GenericJavaClassMapping.this.fireItemRemoved(INCLUDED_ATTRIBUTES_COLLECTION, attribute);
			}
		};
	}
	
	public JaxbAttributesContainer buildIncludedAttributesContainer(JaxbClassMapping parent, Context context) {
		return new GenericJavaAttributesContainer(parent, context, getJavaResourceType());
	}
	
	
	// ***** misc attributes *****
	
	public Iterable<JaxbPersistentAttribute> getAllLocallyDefinedAttributes() {
		return new CompositeIterable<JaxbPersistentAttribute>(
				getAttributes(),
				getIncludedAttributes());
	}
	
	public Iterable<JaxbPersistentAttribute> getInheritedAttributes() {
		return new CompositeIterable<JaxbPersistentAttribute>(
				getIncludedAttributes(),
				getOtherInheritedAttributes());
	}
	
	public Iterable<JaxbPersistentAttribute> getAllAttributes() {
		return new CompositeIterable<JaxbPersistentAttribute>(
				getAttributes(),
				getIncludedAttributes(),
				getOtherInheritedAttributes());
	}
	
	/**
	 * return those inherited attributes that are not included
	 */
	protected Iterable<JaxbPersistentAttribute> getOtherInheritedAttributes() {
		return IterableTools.compositeIterable(
						IterableTools.chainIterable(getSuperclass(), JaxbClassMapping.SUPER_CLASS_TRANSFORMER),
						JaxbClassMapping.ATTRIBUTES_TRANSFORMER);
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
		
		for (JavaType jaxbType : getJaxbProject().getContextRoot().getJavaTypes()) {
			if (jaxbType.getMapping() != null 
					&& ! jaxbType.getMapping().isXmlTransient() 
					&& jaxbType.getMapping().getXmlRootElement() != null
					&& JDTTools.typeIsSubType(
							getJaxbProject().getJavaProject(),
							jaxbType.getTypeName().getFullyQualifiedName(), getJavaType().getTypeName().getFullyQualifiedName())) {
				return true;
			}
		}
		
		return false;
	}
	
	
	// ***** misc *****
	
	@Override
	protected Iterable<String> getTransientReferencedXmlTypeNames() {
		return new CompositeIterable<String>(
				super.getTransientReferencedXmlTypeNames(),
				new SingleElementIterable(getJavaResourceType().getSuperclassQualifiedName()));
	}
	
	@Override
	protected Iterable<String> getNonTransientReferencedXmlTypeNames() {
		return new CompositeIterable<String>(
				super.getNonTransientReferencedXmlTypeNames(),
				new SingleElementIterable<String>(this.superclassName),
				IterableTools.compositeIterable(getAttributeMappings(), JaxbAttributeMapping.REFERENCED_XML_TYPE_NAMES_TRANSFORMER));
	}
	
	public JaxbAttributeMapping getXmlIdMapping() {
		Iterator<XmlNamedNodeMapping> allXmlIdMappings = 
				new FilteringIterable<XmlNamedNodeMapping>(
						new SubIterableWrapper<JaxbAttributeMapping, XmlNamedNodeMapping>(
								new FilteringIterable<JaxbAttributeMapping>(
										IterableTools.transform(getAllAttributes(), JaxbPersistentAttribute.MAPPING_TRANSFORMER)) {
									@Override
									protected boolean accept(JaxbAttributeMapping o) {
										return (o.getKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY
												|| o.getKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY);
									}
								})) {
					@Override
					protected boolean accept(XmlNamedNodeMapping o) {
						return o.getXmlID() != null;
					}
				}.iterator();
		return (allXmlIdMappings.hasNext()) ? allXmlIdMappings.next() : null;
	}
	
	protected Iterable<? extends JaxbAttributeMapping> getAttributeMappings() {
		return IterableTools.transform(getAttributes(), JaxbPersistentAttribute.MAPPING_TRANSFORMER);
	}
	
	
	// ***** content assist *****
	
	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		
		Iterable<String> result = super.getCompletionProposals(pos);
		if (! IterableTools.isEmpty(result)) {
			return result;
		}
		
		if (propTouches(pos)) {
			return getPropProposals();
		}
		
		// TODO - factory methods?
		
		for (JaxbPersistentAttribute attribute : this.getAttributes()) {
			result = attribute.getCompletionProposals(pos);
			if (!IterableTools.isEmpty(result)) {
				return result;
			}
		}
		
		return EmptyIterable.instance();
	}
	
	protected Iterable<String> getPropProposals() {
		return IterableTools.transform(
				IterableTools.transform(
						getAllLocallyDefinedAttributes(),
						JaxbPersistentAttribute.NAME_TRANSFORMER),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}
	
	
	// ***** validation *****
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		validateConstructor(messages, reporter);
		validatePropOrder(messages, reporter);
		validateXmlAnyAttributeMapping(messages);
		validateXmlAnyElementMapping(messages);
		validateXmlValueMapping(messages);
		validateXmlIDs(messages);
		
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			attribute.validate(messages, reporter);
		}
	}
	
	protected void validateConstructor(List<IMessage> messages, IReporter reporter) {
		// TODO - factory class/method
		
		if (! JAXB.XML_TYPE__DEFAULT_FACTORY_CLASS.equals(getFactoryClass())) {
			if (StringTools.isBlank(getFactoryMethod())) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_TYPE__UNSPECIFIED_FACTORY_METHOD,
								this,
								getFactoryClassTextRange()));
			}
		}
		else {
			if (getFactoryMethod() == null
					&& getJavaType().getXmlJavaTypeAdapter() == null
					&& ! getJavaResourceType().hasPublicOrProtectedNoArgConstructor()) {
				messages.add(
						DefaultValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JaxbValidationMessages.XML_TYPE__NO_PUBLIC_OR_PROTECTED_CONSTRUCTOR,
								this,
								getValidationTextRange()));
			}
		}
		
	}
	
	protected void validatePropOrder(List<IMessage> messages, IReporter reporter) {
		if (IterableTools.isEmpty(getPropOrder())) {
			return;
		}
		
		// no duplicates
		// all attributes/included attributes with XmlElement/s/Ref/s must be listed
		// no nonexistent attributes (attributes mapped otherwise allowed) ...
		// *except* no transient attributes allowed
		
		Bag<String> props = CollectionTools.bag(getPropOrder());
		Set<String> allAttributes = new HashSet<String>();
		Set<String> requiredAttributes = new HashSet<String>();
		Set<String> transientAttributes = new HashSet<String>();
		
		for (JaxbPersistentAttribute attribute : getAllLocallyDefinedAttributes()) {
			allAttributes.add(attribute.getName());
			transientAttributes.add(attribute.getName());
		}
		
		for (JaxbPersistentAttribute attribute : getAllLocallyDefinedAttributes()) {
			if (attribute.getMapping().isParticleMapping()) {
				requiredAttributes.add(attribute.getName());
			}
			
			if (! attribute.getMapping().isTransient()) {
				// remove transients (rather than previous algorithm of adding them)
				// there may be two attributes of the same name (one field, one property) in 
				// a class, which is a correct configuration.  we want to know if *every* attribute 
				// of a given name is transient (or if there's a single non-transient attribute)
				transientAttributes.remove(attribute.getName());
			}
		}
		
		Set<Integer> duplicateProps = new HashSet<Integer>();
		Set<String> missingProps = new HashSet<String>(requiredAttributes);
		Set<Integer> nonexistentProps = new HashSet<Integer>();
		Set<Integer> transientProps = new HashSet<Integer>();
		
		for (int i = 0; i < getPropOrderSize(); i ++ ) {
			String prop = getProp(i);
			
			if (props.count(prop) > 1) {
				duplicateProps.add(i);
			}
			
			if (missingProps.contains(prop)) {
				missingProps.remove(prop);
			}
			
			if (! allAttributes.contains(prop)) {
				nonexistentProps.add(i);
			}
			
			if (transientAttributes.contains(prop)) {
				transientProps.add(i);
			}
		}
		
		for (int i : duplicateProps) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_TYPE__DUPLICATE_PROP,
							new String[] { getProp(i) },
							this,
							getPropTextRange(i)));
		}
		
		for (String missingProp : missingProps) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_TYPE__MISSING_PROP,
							new String[] { missingProp },
							this,
							getPropOrderTextRange()));
		}
		
		for (int i : nonexistentProps) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_TYPE__NONEXISTENT_PROP,
							new String[] { getProp(i) },
							this,
							getPropTextRange(i)));
		}
		
		for (int i : transientProps) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_TYPE__TRANSIENT_PROP,
							new String [] { getProp(i) },
							this,
							getPropTextRange(i)));
		}
	}
	
	protected void validateXmlAnyAttributeMapping(List<IMessage> messages) {
		Set<JaxbPersistentAttribute> localAttributes = new HashSet<JaxbPersistentAttribute>();
		Set<JaxbPersistentAttribute> allAttributes = new HashSet<JaxbPersistentAttribute>();
			
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY) {
				localAttributes.add(attribute);
				allAttributes.add(attribute);
			}
		}
		
		for (JaxbPersistentAttribute attribute : getInheritedAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY) {
				allAttributes.add(attribute);
			}
		}
		
		if (allAttributes.size() > 1) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ANY_ATTRIBUTE__MULTIPLE_MAPPINGS_DEFINED,
							this,
							getValidationTextRange()));
				
			for (JaxbPersistentAttribute anyAttribute : localAttributes) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_ANY_ATTRIBUTE__MULTIPLE_MAPPINGS_DEFINED,
						anyAttribute.getMapping(),
						anyAttribute.getMapping().getValidationTextRange()));
			}
		}
	}
	
	protected void validateXmlAnyElementMapping(List<IMessage> messages) {
		Set<JaxbPersistentAttribute> localAttributes = new HashSet<JaxbPersistentAttribute>();
		Set<JaxbPersistentAttribute> allAttributes = new HashSet<JaxbPersistentAttribute>();
			
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY) {
				localAttributes.add(attribute);
				allAttributes.add(attribute);
			}
		}
		
		for (JaxbPersistentAttribute attribute : getInheritedAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_ANY_ELEMENT_ATTRIBUTE_MAPPING_KEY) {
				allAttributes.add(attribute);
			}
		}
		
		if (allAttributes.size() > 1) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ANY_ELEMENT__MULTIPLE_MAPPINGS_DEFINED,
							this,
							getValidationTextRange()));
				
			for (JaxbPersistentAttribute anyAttribute : localAttributes) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_ANY_ELEMENT__MULTIPLE_MAPPINGS_DEFINED,
						anyAttribute.getMapping(),
						anyAttribute.getMapping().getValidationTextRange()));
			}
		}
	}
	
	protected void validateXmlValueMapping(List<IMessage> messages) {
		Set<JaxbPersistentAttribute> localAttributes = new HashSet<JaxbPersistentAttribute>();
		Set<JaxbPersistentAttribute> allAttributes = new HashSet<JaxbPersistentAttribute>();
			
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY) {
				localAttributes.add(attribute);
				allAttributes.add(attribute);
			}
		}
		
		for (JaxbPersistentAttribute attribute : getInheritedAttributes()) {
			if (attribute.getMappingKey() == MappingKeys.XML_VALUE_ATTRIBUTE_MAPPING_KEY) {
				allAttributes.add(attribute);
			}
		}
		
		if (allAttributes.size() > 1) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_VALUE__MULTIPLE_MAPPINGS_DEFINED,
							this,
							getValidationTextRange()));
				
			for (JaxbPersistentAttribute anyAttribute : localAttributes) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_VALUE__MULTIPLE_MAPPINGS_DEFINED,
						anyAttribute.getMapping(),
						anyAttribute.getMapping().getValidationTextRange()));
			}
		}
	}
	
	protected void validateXmlIDs(List<IMessage> messages) {
		
		Set<JaxbPersistentAttribute> localAttributes = new HashSet<JaxbPersistentAttribute>();
		Set<JaxbPersistentAttribute> allAttributes = new HashSet<JaxbPersistentAttribute>();
			
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			if ((attribute.getMappingKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY
					|| attribute.getMappingKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY)
					&& ((XmlNamedNodeMapping) attribute.getMapping()).getXmlID() != null) {
				localAttributes.add(attribute);
				allAttributes.add(attribute);
			}
		}
		
		for (JaxbPersistentAttribute attribute : getInheritedAttributes()) {
			if ((attribute.getMappingKey() == MappingKeys.XML_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY
					|| attribute.getMappingKey() == MappingKeys.XML_ELEMENT_ATTRIBUTE_MAPPING_KEY)
					&& ((XmlNamedNodeMapping) attribute.getMapping()).getXmlID() != null) {
				allAttributes.add(attribute);
			}
		}
		
		if (allAttributes.size() > 1) {
			messages.add(
					DefaultValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JaxbValidationMessages.XML_ID__MULTIPLE_MAPPINGS_DEFINED,
							this,
							getValidationTextRange()));
				
			for (JaxbPersistentAttribute anyAttribute : localAttributes) {
				messages.add(
					DefaultValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JaxbValidationMessages.XML_ID__MULTIPLE_MAPPINGS_DEFINED,
						anyAttribute.getMapping(),
						anyAttribute.getMapping().getValidationTextRange()));
			}
		}
	}
	
	protected TextRange getFactoryClassTextRange() {
		TextRange result = getXmlTypeAnnotation().getFactoryClassTextRange();
		return (result != null) ? result : getValidationTextRange();
	}
	
	protected TextRange getFactoryMethodTextRange() {
		TextRange result = getXmlTypeAnnotation().getFactoryMethodTextRange();
		return (result != null) ? result : getValidationTextRange();
	}
	
	protected TextRange getPropOrderTextRange() {
		TextRange result = getXmlTypeAnnotation().getPropOrderTextRange();
		return (result != null) ? result : getValidationTextRange();
	}
	
	protected TextRange getPropTextRange(int index) {
		return getXmlTypeAnnotation().getPropTextRange(index);
	}
	
	protected boolean propTouches(int pos) {
		if (getXmlTypeAnnotation().propOrderTouches(pos)) {
			for (int i = 0; i < getXmlTypeAnnotation().getPropOrderSize(); i ++ ) {
				if (getXmlTypeAnnotation().propTouches(i, pos)) {
					return true;
				}
			}
		}
		return false;
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
