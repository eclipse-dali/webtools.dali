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

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.filter.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;
import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class GenericJavaAttributesContainer
		extends AbstractJavaContextNode
		implements JaxbAttributesContainer {

	protected JavaResourceType javaResourceType;

	protected JaxbAttributesContainer.Context owner;

	protected final Vector<JaxbPersistentAttribute> attributes = new Vector<JaxbPersistentAttribute>();

	public GenericJavaAttributesContainer(
			JaxbClassMapping parent, JaxbAttributesContainer.Context owner, JavaResourceType resourceType) {
		super(parent);
		this.javaResourceType = resourceType;
		this.owner = owner;
		this.initializeAttributes();
	}
	
	
	public JaxbClassMapping getClassMapping() {
		return (JaxbClassMapping) getParent();
	}
	
	public boolean isFor(JavaResourceType javaResourceType) {
		return this.javaResourceType == javaResourceType;
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

	public Iterable<JaxbPersistentAttribute> getAttributes() {
		return IterableTools.cloneLive(this.attributes);
	}

	public int getAttributesSize() {
		return this.attributes.size();
	}

	protected void addAttribute(JaxbPersistentAttribute attribute) {
		if (this.attributes.add(attribute)) {
			this.owner.attributeAdded(attribute);
		}
	}

	protected void removeAttribute(JaxbPersistentAttribute attribute) {
		if (this.attributes.remove(attribute)) {
			this.owner.attributeRemoved(attribute);
		}
	}

	protected JaxbPersistentAttribute buildField(JavaResourceField resourceField) {
		return getFactory().buildJavaPersistentField(getClassMapping(), resourceField);
	}

	protected JaxbPersistentAttribute buildProperty(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
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
		this.initializeFieldAttributes(PUBLIC_MEMBER_ACCESS_TYPE_RESOURCE_FIELDS_FILTER);
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all persistable resource method getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (methodsArePersistablePublicMemberAccess(getterMethod, setterMethod)) {
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
		this.initializeFieldAttributes(this.buildNonTransientNonStaticResourceFieldsFilter());
		this.initializeAnnotatedPropertyAttributes();
	}

	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void intializePropertyAccessAttributes() {
		this.initializeFieldAttributes(ANNOTATED_RESOURCE_FIELDS_FILTER);

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (methodsArePersistableProperties(getterMethod, setterMethod)) {
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
		this.initializeFieldAttributes(ANNOTATED_RESOURCE_FIELDS_FILTER);
		this.initializeAnnotatedPropertyAttributes();
	}

	private void initializeFieldAttributes(Filter<JavaResourceField> filter) {
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

	private static boolean methodsArePersistableProperties(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		if (setterMethod != null) {
			return true;
		}
		//Lists do not have to have a corresponding setter method
		else if (getterMethod.getTypeBinding().getQualifiedName().equals("java.util.List")) { //$NON-NLS-1$
			return true;
		}
		else if (getterMethod.isAnnotated()) {
			//annotated getter with no corresponding setter, bring into context model for validation purposes
			return true;
		}
		return false;
	}

	private static boolean methodsArePersistablePublicMemberAccess(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		if (getterMethod.isPublic()) {
			if (setterMethod != null) {
				if (setterMethod.isPublic()) {
					return true;
				}
			}
			//Lists do not have to have a corresponding setter method
			else if (getterMethod.getTypeBinding().getQualifiedName().equals("java.util.List")) { //$NON-NLS-1$
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
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
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

	protected Iterable<JavaResourceField> getResourceFields(Filter<JavaResourceField> filter) {
		return IterableTools.filter(getResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> getResourceMethods(Filter<JavaResourceMethod> filter) {
		return IterableTools.filter(getResourceMethods(), filter);
	}

	protected Filter<JavaResourceField> buildNonTransientNonStaticResourceFieldsFilter() {
		return new Filter<JavaResourceField>() {
			public boolean accept(JavaResourceField resourceField) {
				return memberIsNonTransientNonStatic(resourceField) || resourceField.isAnnotated();
			}
		};
	}

	protected static Filter<JavaResourceField> PUBLIC_MEMBER_ACCESS_TYPE_RESOURCE_FIELDS_FILTER = new Filter<JavaResourceField>() {
		public boolean accept(JavaResourceField resourceField) {
			return memberIsPublicNonTransientNonStatic(resourceField) || resourceField.isAnnotated();
		}
	};

	protected Filter<JavaResourceMethod> buildPersistablePropertyGetterMethodsFilter() {
		return new Filter<JavaResourceMethod>() {
			public boolean accept(JavaResourceMethod resourceMethod) {
				return methodIsPersistablePropertyGetter(resourceMethod, getResourceMethods());
			}
		};
	}

	protected static boolean memberIsPublicNonTransientNonStatic(JavaResourceMember resourceMember) {
		return resourceMember.isPublic() && memberIsNonTransientNonStatic(resourceMember);
	}

	protected static boolean memberIsNonTransientNonStatic(JavaResourceMember resourceMember) {
		return !resourceMember.isTransient() && !resourceMember.isStatic();
	}

	protected static Filter<JavaResourceField> ANNOTATED_RESOURCE_FIELDS_FILTER = 
		new Filter<JavaResourceField>() {
			public boolean accept(JavaResourceField resourceField) {
				return resourceField.isAnnotated();
			}
		};

	/**
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateAttributes() {
		if (getClassMapping().isXmlTransient()) {
			for (JaxbPersistentAttribute contextAttribute : getAttributes()) {
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
		HashSet<JaxbPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, PUBLIC_MEMBER_ACCESS_TYPE_RESOURCE_FIELDS_FILTER);

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all persistable resource method getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (methodsArePersistablePublicMemberAccess(getterMethod, setterMethod)) {
				boolean match = false;
				for (Iterator<JaxbPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JaxbPersistentAttribute contextAttribute = stream.next();
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
		HashSet<JaxbPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, this.buildNonTransientNonStaticResourceFieldsFilter());
		this.syncAnnotatedPropertyAttributes(contextAttributes);
	}

	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void syncPropertyAccessAttributes() {
		HashSet<JaxbPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, ANNOTATED_RESOURCE_FIELDS_FILTER);

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (methodsArePersistableProperties(getterMethod, setterMethod)) {
				boolean match = false;
				for (Iterator<JaxbPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JaxbPersistentAttribute contextAttribute = stream.next();
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
		HashSet<JaxbPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, ANNOTATED_RESOURCE_FIELDS_FILTER);
		this.syncAnnotatedPropertyAttributes(contextAttributes);
	}

	private void syncAnnotatedPropertyAttributes(HashSet<JaxbPersistentAttribute> contextAttributes) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (getterMethod.isAnnotated() || (setterMethod != null && setterMethod.isAnnotated())) {
				boolean match = false;
				for (Iterator<JaxbPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JaxbPersistentAttribute contextAttribute = stream.next();
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

	private void syncFieldAttributes(HashSet<JaxbPersistentAttribute> contextAttributes, Filter<JavaResourceField> filter) {
		for (JavaResourceField resourceField : this.getResourceFields(filter)) {
			boolean match = false;
			for (Iterator<JaxbPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext(); ) {
				JaxbPersistentAttribute contextAttribute = stream.next();
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

	private void syncRemainingResourceMethods(HashSet<JaxbPersistentAttribute> contextAttributes, Collection<JavaResourceMethod> resourceMethods) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				boolean match = false;
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				for (Iterator<JaxbPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JaxbPersistentAttribute contextAttribute = stream.next();
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
		for (JaxbPersistentAttribute contextAttribute : contextAttributes) {
			this.removeAttribute(contextAttribute);
		}
	}

	/**
	 * Return whether the specified method is a "getter" method that
	 * represents a property that may be "persisted".
	 */
	protected static boolean methodIsPersistablePropertyGetter(JavaResourceMethod resourceMethod, Iterable<JavaResourceMethod> allMethods) {
		if (methodHasInvalidModifiers(resourceMethod)) {
			return false;
		}
		if (resourceMethod.isConstructor()) {
			return false;
		}

		String returnTypeName = resourceMethod.getTypeBinding().getQualifiedName();
		if (returnTypeName == null) {
			return false;  // DOM method bindings can have a null name
		}
		if (returnTypeName.equals("void")) { //$NON-NLS-1$
			return false;
		}
		if (methodHasParameters(resourceMethod)) {
			return false;
		}

		boolean booleanGetter = methodIsBooleanGetter(resourceMethod);

		// if the type has both methods:
		//     boolean isProperty()
		//     boolean getProperty()
		// then #isProperty() takes precedence and we ignore #getProperty();
		// but only having #getProperty() is OK too
		// (see the JavaBeans spec 1.01)
		if (booleanGetter && methodHasValidSiblingIsMethod(resourceMethod, allMethods)) {
			return false;  // since the type also defines #isProperty(), ignore #getProperty()
		}
		return true;
	}

	private static boolean methodIsBooleanGetter(JavaResourceMethod resourceMethod) {
		String returnTypeName = resourceMethod.getTypeBinding().getQualifiedName();
		String name = resourceMethod.getMethodName();
		boolean booleanGetter = false;
		if (name.startsWith("is")) { //$NON-NLS-1$
			if (returnTypeName.equals("boolean")) { //$NON-NLS-1$
			} else {
				return false;
			}
		} else if (name.startsWith("get")) { //$NON-NLS-1$
			if (returnTypeName.equals("boolean")) { //$NON-NLS-1$
				booleanGetter = true;
			}
		} else {
			return false;
		}
		return booleanGetter;
	}

	/**
	 * Return whether the method's modifiers prevent it
	 * from being a getter or setter for a "persistent" property.
	 */
	private static boolean methodHasInvalidModifiers(JavaResourceMethod resourceMethod) {
		int modifiers = resourceMethod.getModifiers();
		if (Modifier.isStatic(modifiers)) {
			return true;
		}
		return false;
	}

	private static boolean methodHasParameters(JavaResourceMethod resourceMethod) {
		return resourceMethod.getParametersSize() != 0;
	}

	/**
	 * Return whether the method has a sibling "is" method for the specified
	 * property and that method is valid for a "persistable" property.
	 * Pre-condition: the method is a "boolean getter" (e.g. 'public boolean getProperty()');
	 * this prevents us from returning true when the method itself is an
	 * "is" method.
	 */
	private static boolean methodHasValidSiblingIsMethod(JavaResourceMethod getMethod, Iterable<JavaResourceMethod> resourceMethods) {
		String capitalizedAttributeName = StringTools.capitalize(getMethod.getName());
		for (JavaResourceMethod sibling : resourceMethods) {
			if ((sibling.getParametersSize() == 0)
					&& sibling.getMethodName().equals("is" + capitalizedAttributeName)) { //$NON-NLS-1$
				return methodIsValidSibling(sibling, "boolean"); //$NON-NLS-1$
			}
		}
		return false;
	}

	/**
	 * Return whether the method has a sibling "set" method
	 * and that method is valid for a "persistable" property.
	 */
	private static JavaResourceMethod getValidSiblingSetMethod(JavaResourceMethod getMethod, Collection<JavaResourceMethod> resourceMethods) {
		String capitalizedSetAttributeName = "set" + StringTools.capitalize(getMethod.getName());//$NON-NLS-1$
		String parameterTypeErasureName = getMethod.getTypeBinding().getQualifiedName();
		for (JavaResourceMethod sibling : resourceMethods) {
			if ((sibling.getParametersSize() == 1)
				&& sibling.getMethodName().equals(capitalizedSetAttributeName)
				&& sibling.getParameterTypeName(0).equals(parameterTypeErasureName)) {
				return methodIsValidSibling(sibling, "void") ? sibling : null; //$NON-NLS-1$
			}
		}
		return null;
	}

	/**
	 * Return whether the specified method is a valid sibling with the
	 * specified return type.
	 */
	private static boolean methodIsValidSibling(JavaResourceMethod resourceMethod, String returnTypeName) {
		if (resourceMethod == null) {
			return false;
		}
		if (methodHasInvalidModifiers(resourceMethod)) {
			return false;
		}
		if (resourceMethod.isConstructor()) {
			return false;
		}
		String rtName = resourceMethod.getTypeBinding().getQualifiedName();
		if (rtName == null) {
			return false;  // DOM method bindings can have a null name
		}
		return rtName.equals(returnTypeName);
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
