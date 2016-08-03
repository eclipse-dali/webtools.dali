/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context.java;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.core.context.java.JavaPersistentAttribute;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributesContainer
		extends AbstractJavaContextNode
		implements JaxbAttributesContainer<JavaPersistentAttribute> {
	
	protected JavaResourceType javaResourceType;
	
	protected JaxbAttributesContainer.Context owner;
	
	protected final Vector<JavaPersistentAttribute> attributes = new Vector<JavaPersistentAttribute>();
	
	public GenericJavaAttributesContainer(
			JaxbClassMapping parent, JaxbAttributesContainer.Context owner, JavaResourceType resourceType) {
		super(parent);
		this.javaResourceType = resourceType;
		this.owner = owner;
		initializeAttributes();
	}
	
	
	public JaxbClassMapping getClassMapping() {
		return (JaxbClassMapping) getParent();
	}
	
	public boolean isFor(JavaResourceType jrt) {
		return this.javaResourceType == jrt;
	}
	
	
	// ********** synchronize/update **********
	
	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.synchronizeNodesWithResourceModel(this.getAttributes());
	}
	
	@Override
	public void update() {
		super.update();
		this.updateAttributes();
	}
	
	
	// ********** access type **********
	
	protected XmlAccessType getAccessType() {
		return this.owner.getAccessType();
	}
	

	// ********** attributes **********
	
	public Iterable<JavaPersistentAttribute> getAttributes() {
		return IterableTools.cloneLive(this.attributes);
	}
	
	public int getAttributesSize() {
		return this.attributes.size();
	}
	
	protected void addAttribute(JavaPersistentAttribute attribute) {
		if (this.attributes.add(attribute)) {
			this.owner.attributeAdded(attribute);
		}
	}
	
	protected void removeAttribute(JavaPersistentAttribute attribute) {
		if (this.attributes.remove(attribute)) {
			this.owner.attributeRemoved(attribute);
		}
	}
	
	protected JavaPersistentAttribute buildField(JavaResourceField resourceField) {
		return getFactory().buildJavaPersistentField(getClassMapping(), resourceField);
	}
	
	protected JavaPersistentAttribute buildProperty(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return getFactory().buildJavaPersistentProperty(getClassMapping(), resourceGetter, resourceSetter);
	}
	
	protected void initializeAttributes() {
		if (getClassMapping().isXmlTransient()) {
			return;
		}
		if (getAccessType() == XmlAccessType.PUBLIC_MEMBER) {
			this.initializePublicMemberAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.FIELD) {
			this.intializeFieldAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.PROPERTY) {
			this.intializePropertyAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.NONE) {
			this.intializeNoneAccessAttributes();
		}
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.PUBLIC_MEMBER
	 * 1. all public, non-static, non-transient fields (transient modifier, @XmlTransient is brought to the context model)
	 * 2. all annotated fields that aren't public
	 * 3. all public getter/setter javabeans pairs
	 * 4. all annotated methods (some will have a matching getter/setter, some will be standalone)
	 */
	private void initializePublicMemberAccessAttributes() {
		this.initializeFieldAttributes(JavaResourceField.IS_RELEVANT_FOR_PUBLIC_MEMBER_ACCESS);
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all persistable resource method getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (includePublicMemberAccessProperty(getterMethod, setterMethod)) {
				this.attributes.add(this.buildProperty(getterMethod, setterMethod));
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.initializeRemainingResourceMethodAttributes(resourceMethods);
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.FIELD
	 * 1. all non-transient fields
	 * 2. all annotated methods getters/setters
	 */
	private void intializeFieldAccessAttributes() {
		this.initializeFieldAttributes(JavaResourceField.IS_RELEVANT_FOR_FIELD_ACCESS);
		this.initializeAnnotatedPropertyAttributes();
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void intializePropertyAccessAttributes() {
		this.initializeFieldAttributes(JavaResourceAnnotatedElement.ANNOTATED_PREDICATE);
		
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (this.includeProperty(getterMethod, setterMethod)) {
				this.attributes.add(this.buildProperty(getterMethod, setterMethod));
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.initializeRemainingResourceMethodAttributes(resourceMethods);
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.NONE
	 * 1. all annotated fields
	 * 2. all annotated methods getters/setters (some will have a matching getter/setter, some will be standalone)
	 */
	private void intializeNoneAccessAttributes() {
		this.initializeFieldAttributes(JavaResourceAnnotatedElement.ANNOTATED_PREDICATE);
		this.initializeAnnotatedPropertyAttributes();
	}
	
	private void initializeFieldAttributes(Predicate<? super JavaResourceField> filter) {
		for (JavaResourceField resourceField : this.getResourceFields(filter)) {
			this.attributes.add(this.buildField(resourceField));
		}
	}
	
	private void initializeRemainingResourceMethodAttributes(Collection<JavaResourceMethod> resourceMethods) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				this.attributes.add(this.buildProperty(null, resourceMethod));
			}
		}
	}
	
	/**
	 * Return whether the pair of methods (that form a "property") are to be
	 * included in the contailner.
	 * Lists do not need a setter....
	 * An annotated getter with no setter is still brought into the context
	 * model (for validation purposes)....
	 */
	private boolean includeProperty(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (setterMethod != null) ||
				getterMethod.getTypeBinding().getQualifiedName().equals(java.util.List.class.getName()) ||
				getterMethod.isAnnotated();
	}
	
	private boolean includePublicMemberAccessProperty(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		if (getterMethod.isPublic()) {
			if (setterMethod != null) {
				if (setterMethod.isPublic()) {
					return true;
				}
			}
			//Lists do not have to have a corresponding setter method
			else if (getterMethod.getTypeBinding().getQualifiedName().equals(java.util.List.class.getName())) {
				return true;
			}
			else if (getterMethod.isAnnotated()) {
				//annotated getter with no corresponding setter, bring into context model for validation purposes
				return true;
			}
		}
		else if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
			return true;
		}
		return false;
	}
	
	private void initializeAnnotatedPropertyAttributes() {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
				this.attributes.add(this.buildProperty(getterMethod, setterMethod));
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.initializeRemainingResourceMethodAttributes(resourceMethods);
	}
	
	protected Iterable<JavaResourceField> getResourceFields() {
		return this.javaResourceType.getFields();
	}
	
	protected Iterable<JavaResourceMethod> getResourceMethods() {
		return this.javaResourceType.getMethods();
	}

	protected Iterable<JavaResourceField> getResourceFields(Predicate<? super JavaResourceField> filter) {
		return IterableTools.filter(getResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> filterResourceMethods(Predicate<JavaResourceMethod> filter) {
		return IterableTools.filter(getResourceMethods(), filter);
	}

	protected Iterable<JavaResourceMethod> getResourcePropertyGetters() {
		return this.filterResourceMethods(JavaResourceMethod.PROPERTY_GETTER_PREDICATE);
	}
	
	/**
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateAttributes() {
		if (getClassMapping().isXmlTransient()) {
			for (JavaPersistentAttribute contextAttribute : getAttributes()) {
				this.removeAttribute(contextAttribute);
			}
			return;
		}
		if (getAccessType() == XmlAccessType.PUBLIC_MEMBER) {
			this.syncPublicMemberAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.FIELD) {
			this.syncFieldAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.PROPERTY) {
			this.syncPropertyAccessAttributes();
		}
		else if (getAccessType() == XmlAccessType.NONE) {
			this.syncNoneAccessAttributes();
		}
	}
	
	/**
	 * Sync the attributes for XmlAccessType.PUBLIC_MEMBER
	 * 1. all public, non-static, non-transient fields (transient modifier, @XmlTransient is brought to the context model)
	 * 2. all annotated fields that aren't public
	 * 3. all public getter/setter javabeans pairs
	 * 4. all annotated methods (some will have a matching getter/setter, some will be standalone)
	 */
	private void syncPublicMemberAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());
		
		this.syncFieldAttributes(contextAttributes, JavaResourceField.IS_RELEVANT_FOR_PUBLIC_MEMBER_ACCESS);
		
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all persistable resource method getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (includePublicMemberAccessProperty(getterMethod, setterMethod)) {
				boolean match = false;
				for (Iterator<JavaPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(getterMethod, setterMethod)) {
						match = true;
						contextAttribute.update();
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addAttribute(this.buildProperty(getterMethod, setterMethod));
				}
				resourceMethods.remove(getterMethod);
				resourceMethods.remove(setterMethod);
			}
		}
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods);
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.FIELD
	 * 1. all non-transient fields
	 * 2. all annotated methods getters/setters
	 */
	private void syncFieldAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());
		
		this.syncFieldAttributes(contextAttributes, JavaResourceField.IS_RELEVANT_FOR_FIELD_ACCESS);
		this.syncAnnotatedPropertyAttributes(contextAttributes);
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void syncPropertyAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());
		
		this.syncFieldAttributes(contextAttributes, JavaResourceAnnotatedElement.ANNOTATED_PREDICATE);
		
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (this.includeProperty(getterMethod, setterMethod)) {
				boolean match = false;
				for (Iterator<JavaPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(getterMethod, setterMethod)) {
						match = true;
						contextAttribute.update();
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addAttribute(this.buildProperty(getterMethod, setterMethod));
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods);
	}
	
	/**
	 * Initialize the attributes for XmlAccessType.NONE
	 * 1. all annotated fields
	 * 2. all annotated methods getters/setters (some will have a matching getter/setter, some will be standalone)
	 */
	private void syncNoneAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());
		
		this.syncFieldAttributes(contextAttributes, JavaResourceAnnotatedElement.ANNOTATED_PREDICATE);
		this.syncAnnotatedPropertyAttributes(contextAttributes);
	}
	
	private void syncAnnotatedPropertyAttributes(HashSet<JavaPersistentAttribute> contextAttributes) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
				boolean match = false;
				for (Iterator<JavaPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(getterMethod, setterMethod)) {
						match = true;
						contextAttribute.update();
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addAttribute(this.buildProperty(getterMethod, setterMethod));
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods);
	}

	private void syncFieldAttributes(HashSet<JavaPersistentAttribute> contextAttributes, Predicate<? super JavaResourceField> filter) {
		for (JavaResourceField resourceField : this.getResourceFields(filter)) {
			boolean match = false;
			for (Iterator<JavaPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext(); ) {
				JavaPersistentAttribute contextAttribute = stream.next();
				if (contextAttribute.isFor(resourceField)) {
					match = true;
					contextAttribute.update();
					stream.remove();
					break;
				}
			}
			if (!match) {
				// added elements are sync'ed during construction or will be
				// updated during the next "update" (which is triggered by
				// their addition to the model)
				this.addAttribute(this.buildField(resourceField));
			}
		}
	}
	
	private void syncRemainingResourceMethods(HashSet<JavaPersistentAttribute> contextAttributes, Collection<JavaResourceMethod> resourceMethods) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				boolean match = false;
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				for (Iterator<JavaPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(null, resourceMethod)) {
						match = true;
						contextAttribute.update();
						stream.remove();
						break;
					}
				}
				if (!match) {
					this.addAttribute(this.buildProperty(null, resourceMethod));
				}
			}
		}
		
		// remove any leftover context attributes
		for (JavaPersistentAttribute contextAttribute : contextAttributes) {
			this.removeAttribute(contextAttribute);
		}
	}
	
	
	// ********** validation **********
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		for (JaxbPersistentAttribute attribute : getAttributes()) {
			attribute.validate(messages, reporter);
		}
	}
	
	@Override
	public TextRange getValidationTextRange() {
		return getClassMapping().getValidationTextRange();
	}
}
