/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java persistent type:<ul>
 * <li>name
 * <li>access
 * <li>mapping
 * <li>attributes
 * <li>super persistent type
 * </ul>
 */
public abstract class AbstractJavaPersistentType
	extends AbstractJavaManagedType<PersistentType.Owner>
	implements JavaPersistentType
{

	protected PersistentType superPersistentType;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected JavaTypeMapping mapping;  // never null

	protected final Vector<JavaPersistentAttribute> attributes = new Vector<JavaPersistentAttribute>();

	protected final Vector<JavaPersistentAttribute> children = new Vector<JavaPersistentAttribute>();


	protected AbstractJavaPersistentType(PersistentType.Owner parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.specifiedAccess = this.buildSpecifiedAccess();

		// keep this non-null
		this.defaultAccess = AccessType.FIELD;

		this.mapping = this.buildMapping();
		this.initializeAttributes();
		this.initializeChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncMapping();
		this.synchronizeModelsWithResourceModel(this.getAttributes());
	}

	@Override
	public void update() {
		super.update();
		this.setSuperPersistentType(this.buildSuperPersistentType());
		this.setDefaultAccess(this.buildDefaultAccess());
		this.mapping.update();
		this.updateAttributes();
		this.updateChildren();
	}


	// ********** super persistent type **********

	public PersistentType getSuperPersistentType() {
		return this.superPersistentType;
	}

	protected void setSuperPersistentType(PersistentType superPersistentType) {
		PersistentType old = this.superPersistentType;
		this.superPersistentType = superPersistentType;
		this.firePropertyChanged(SUPER_PERSISTENT_TYPE_PROPERTY, old, superPersistentType);
	}

	protected PersistentType buildSuperPersistentType() {
		HashSet<JavaResourceType> visited = new HashSet<JavaResourceType>();
		visited.add(this.resourceType);
		PersistentType spt = this.resolveSuperPersistentType(this.resourceType.getSuperclassQualifiedName(), visited);
		if (spt == null) {
			return null;
		}
		if (IterableTools.contains(spt.getInheritanceHierarchy(), this)) {
			return null;  // short-circuit in this case, we have circular inheritance
		}
		return spt.isMapped() ? spt : spt.getSuperPersistentType();
	}

	/**
	 * The JPA spec allows non-persistent types in a persistent type's
	 * inheritance hierarchy. We check for a persistent type with the
	 * specified name in the persistence unit. If it is not found we use
	 * resource persistent type and look for <em>its</em> super type.
	 * <p>
	 * The <code>visited</code> collection is used to detect a cycle in the
	 * <em>resource</em> type inheritance hierarchy and prevent the resulting
	 * stack overflow. Any cycles in the <em>context</em> type inheritance
	 * hierarchy are handled in {@link #buildSuperPersistentType()}.
	 */
	protected PersistentType resolveSuperPersistentType(String typeName, Collection<JavaResourceType> visited) {
		if (typeName == null) {
			return null;
		}
		JavaResourceType rType = (JavaResourceType) this.getJpaProject().getJavaResourceType(typeName, JavaResourceAnnotatedElement.AstNodeType.TYPE);
		if ((rType == null) || visited.contains(rType)) {
			return null;
		}
		visited.add(rType);
		PersistentType superType = this.resolvePersistentType(typeName);
		return (superType != null) ? superType : this.resolveSuperPersistentType(rType.getSuperclassQualifiedName(), visited);  // recurse
	}

	protected PersistentType resolvePersistentType(String typeName) {
		return this.getPersistenceUnit().getPersistentType(typeName);
	}


	// ********** access annotation **********

	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.resourceType.getNonNullAnnotation(this.getAccessAnnotationName());
	}

	protected void removeAccessAnnotationIfUnset() {
		Access2_0Annotation accessAnnotation = this.getAccessAnnotation();
		if ((accessAnnotation != null) && accessAnnotation.isUnset()) {
			this.removeAccessAnnotation();
		}
	}

	protected void removeAccessAnnotation() {
		this.resourceType.removeAnnotation(this.getAccessAnnotationName());
	}

	protected String getAccessAnnotationName() {
		return Access2_0Annotation.ANNOTATION_NAME;
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		if (this.valuesAreDifferent(this.specifiedAccess, access)) {
			this.getAccessAnnotation().setValue(AccessType.toJavaResourceModel(access));
			this.removeAccessAnnotationIfUnset();
			this.setSpecifiedAccess_(access);
		}
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromJavaResourceModel(this.getAccessAnnotation().getValue(), this.getJpaPlatform(), this.getResourceType());
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}

	/**
	 * Check the access "specified" by the Java resource model:<ul>
	 * <li>Check Java annotations first
	 * <li>If <code>null</code>, check XML mapping specified access
	 * <li>If still <code>null</code>, check {@link #superPersistentType} access
	 * <li>If still <code>null</code>, check <code>entity-mappings</code>
	 *     specified access setting if the corresponding <code>persistent-type</code>
	 *     is listed in a mapping (<code>orm.xml</code>) file
	 * <li>If still <code>null</code>, check the <code>persistence-unit</code>
	 *     default Access
	 * <li>Default to {@link AccessType#FIELD FIELD} if all else fails.
	 * </ul>
	 */
	protected AccessType buildDefaultAccess() {
		AccessType accessType = buildAccess(this.resourceType);
		if (accessType != null) {
			return accessType;
		}
		accessType = this.getOwnerOverrideAccess();
		if (accessType != null) {
			return accessType;
		}

		if (this.superPersistentType != null) {
			accessType = this.superPersistentType.getAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		accessType = this.getOwnerDefaultAccess();
		if (accessType != null) {
			return accessType;
		}

		// last ditch attempt to allow the user to annotate *something*
		return AccessType.FIELD;
	}

	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		if (this.valuesAreDifferent(key, this.getMappingKey())) {
			this.setMapping(this.buildMapping(key));
		}
	}

	protected JavaTypeMapping buildMapping(String key) {
		for (JavaTypeMappingDefinition definition : this.getMappingDefinitions()) {
			if (ObjectTools.equals(definition.getKey(), key)) {
				Annotation annotation = this.resourceType.setPrimaryAnnotation(definition.getAnnotationName(), definition.getSupportingAnnotationNames());
				return definition.buildMapping(this, annotation, this.getJpaFactory());
			}
		}
		this.resourceType.setPrimaryAnnotation(null, EmptyIterable.<String>instance());
		return this.buildNullMapping();
	}

	/**
	 * Clients do not set the mapping directly.
	 * @see #setMappingKey(String)
	 */
	protected void setMapping(JavaTypeMapping mapping) {
		JavaTypeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	protected JavaTypeMapping buildMapping() {
		for (JavaTypeMappingDefinition definition : this.getMappingDefinitions()) {
			Annotation annotation = this.resourceType.getAnnotation(definition.getAnnotationName());
			if (annotation != null) {
				return definition.buildMapping(this, annotation, this.getJpaFactory());
			}
		}
		return this.buildNullMapping();
	}

	protected void syncMapping() {
		JavaTypeMappingDefinition definition = null;
		Annotation annotation = null;
		for (Iterator<JavaTypeMappingDefinition> stream = this.mappingDefinitions(); stream.hasNext(); ) {
			definition = stream.next();
			annotation = this.resourceType.getAnnotation(definition.getAnnotationName());
			if (annotation != null) {
				break;
			}
		}
		// 'annotation' can still be null when we get here
		if (this.mapping.getMappingAnnotation() == annotation) {
			this.mapping.synchronizeWithResourceModel();
		} else {
			this.setMapping(this.buildMapping(annotation, definition));
		}
	}

	protected JavaTypeMapping buildMapping(Annotation annotation, JavaTypeMappingDefinition definition) {
		return (annotation != null) ?
				definition.buildMapping(this, annotation, this.getJpaFactory()) :
				this.buildNullMapping();
	}

	protected Iterator<JavaTypeMappingDefinition> mappingDefinitions() {
		return this.getMappingDefinitions().iterator();
	}

	protected Iterable<JavaTypeMappingDefinition> getMappingDefinitions() {
		return this.getJpaPlatform().getJavaTypeMappingDefinitions();
	}

	protected JavaTypeMapping buildNullMapping() {
		return this.getJpaFactory().buildJavaNullTypeMapping(this);
	}

	public boolean isMapped() {
		return this.mapping.isMapped();
	}


	// ********** attributes **********

	public ListIterable<JavaPersistentAttribute> getAttributes() {
		return IterableTools.cloneLive(this.attributes);
	}

	public int getAttributesSize() {
		return this.attributes.size();
	}

	public Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public JavaPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public JavaPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute) {
		for (JavaPersistentAttribute javaAttribute : this.getAttributes()) {
			if (javaAttribute.getResourceAttribute() == javaResourceAttribute) {
				return javaAttribute;
			}
		}
		return null;
	}

	public Iterable<ReadOnlyPersistentAttribute> getAllAttributes() {
		return IterableTools.children(
					this.getInheritanceHierarchy(),
					PersistentType.ATTRIBUTES_TRANSFORMER
			);
	}

	public Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<JavaPersistentAttribute> getAttributesNamed(String attributeName) {
		return IterableTools.filter(this.getAttributes(), new ReadOnlyPersistentAttribute.NameEquals(attributeName));
	}

	public ReadOnlyPersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		if (stream.hasNext()) {
			JavaPersistentAttribute attribute = stream.next();
			// return null if we have more than one
			return stream.hasNext() ? null : attribute;
		}
		// recurse
		return (this.superPersistentType == null) ? null : this.superPersistentType.resolveAttribute(attributeName);
	}

	protected Iterable<String> convertToNames(Iterable<? extends ReadOnlyPersistentAttribute> attrs) {
		return IterableTools.transform(attrs, ReadOnlyPersistentAttribute.NAME_TRANSFORMER);
	}

	protected JavaPersistentAttribute buildField(JavaResourceField resourceField) {
		return this.getJpaFactory().buildJavaPersistentField(this, resourceField);
	}

	protected JavaPersistentAttribute buildProperty(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return this.getJpaFactory().buildJavaPersistentProperty(this, resourceGetter, resourceSetter);
	}

	public boolean hasAnyAnnotatedAttributes() {
		return this.resourceType.hasAnyAnnotatedFields() || this.resourceType.hasAnyAnnotatedMethods();
	}

	protected void moveAttribute(int index, JavaPersistentAttribute attribute) {
		this.moveItemInList(index, attribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void addAttribute(int index, JavaPersistentAttribute persistentAttribute) {
		this.addItemToList(index, persistentAttribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void removeAttribute(JavaPersistentAttribute attribute) {
		this.removeItemFromList(attribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void initializeAttributes() {
		if (this.getAccess() == AccessType.FIELD) {
			this.intializeFieldAccessAttributes();
		}
		else if (this.getAccess() == AccessType.PROPERTY) {
			this.intializePropertyAccessAttributes();
		}
	}

	/**
	 * Initialize the attributes for AccessType.FIELD
	 * 1. all non-transient, non-static fields
	 * 2. all annotated methods (getters/setters)
	 */
	private void intializeFieldAccessAttributes() {
		this.initializeFieldAttributes(buildNonTransientNonStaticResourceFieldsFilter());
		this.initializeAnnotatedPropertyAttributes();
	}

	private void initializeFieldAttributes(Predicate<JavaResourceField> filter) {
		for (JavaResourceField resourceField : this.getResourceFields(filter)) {
			this.attributes.add(this.buildField(resourceField));
		}
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

	private void initializeAnnotatedPropertyAttributes() {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (getterMethod.isAnnotated() || ((setterMethod != null) && setterMethod.isAnnotated())) {
				this.attributes.add(this.buildProperty(getterMethod, setterMethod));
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.initializeRemainingResourceMethodAttributes(resourceMethods);
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
	 * The attributes are synchronized during the <em>update</em> because
	 * the list of resource attributes is determined by the access type
	 * which can be controlled in a number of different places....
	 */
	protected void updateAttributes() {
		if (this.getAccess() == AccessType.FIELD) {
			this.syncFieldAccessAttributes();
		}
		else if (this.getAccess() == AccessType.PROPERTY) {
			this.syncPropertyAccessAttributes();
		}
	}

	/**
	 * Initialize the attributes for AccessType.FIELD
	 * 1. all non-transient, non-static fields
	 * 2. all annotated methods(getters/setters)
	 */
	private void syncFieldAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, buildNonTransientNonStaticResourceFieldsFilter());
		this.syncAnnotatedPropertyAttributes(contextAttributes);
	}

	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void syncPropertyAccessAttributes() {
		HashSet<JavaPersistentAttribute> contextAttributes = CollectionTools.set(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, ANNOTATED_RESOURCE_FIELDS_FILTER);

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (methodsArePersistableProperties(getterMethod, setterMethod)) {
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
					this.addAttribute(this.getAttributesSize(), this.buildProperty(getterMethod, setterMethod));
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods);
	}

	private void syncAnnotatedPropertyAttributes(HashSet<JavaPersistentAttribute> contextAttributes) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.collection(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourceMethods(this.buildPersistablePropertyGetterMethodsFilter())) {
			JavaResourceMethod setterMethod = getValidSiblingSetMethod(getterMethod, resourceMethods);
			if (getterMethod.isAnnotated() || ((setterMethod != null) && setterMethod.isAnnotated())) {
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
					this.addAttribute(this.getAttributesSize(), this.buildProperty(getterMethod, setterMethod));
				}
			}
			resourceMethods.remove(getterMethod);
			resourceMethods.remove(setterMethod);
		}
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods);
	}

	private void syncFieldAttributes(HashSet<JavaPersistentAttribute> contextAttributes, Predicate<JavaResourceField> filter) {
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
				this.addAttribute(this.getAttributesSize(), this.buildField(resourceField));
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
					this.addAttribute(this.getAttributesSize(), this.buildProperty(null, resourceMethod));
				}
			}
		}

		// remove any leftover context attributes
		for (JavaPersistentAttribute contextAttribute : contextAttributes) {
			this.removeAttribute(contextAttribute);
		}
	}


	protected Iterable<JavaResourceField> getResourceFields() {
		return this.resourceType.getFields();
	}

	protected Iterable<JavaResourceMethod> getResourceMethods() {
		return this.resourceType.getMethods();
	}

	protected Iterable<JavaResourceField> getResourceFields(Predicate<JavaResourceField> filter) {
		return IterableTools.filter(this.getResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> getResourceMethods(Predicate<JavaResourceMethod> filter) {
		return IterableTools.filter(this.getResourceMethods(), filter);
	}

	public static Predicate<JavaResourceField> buildNonTransientNonStaticResourceFieldsFilter() {
		return new Predicate<JavaResourceField>() {
			public boolean evaluate(JavaResourceField resourceField) {
				return memberIsNonTransientNonStatic(resourceField) || resourceField.isAnnotated();
			}
		};
	}

	protected static boolean memberIsNonTransientNonStatic(JavaResourceMember resourceMember) {
		return !resourceMember.isTransient() && !resourceMember.isStatic();
	}

	public static Predicate<JavaResourceField> ANNOTATED_RESOURCE_FIELDS_FILTER =
		new Predicate<JavaResourceField>() {
			public boolean evaluate(JavaResourceField resourceField) {
				return resourceField.isAnnotated();
			}
		};

	protected Predicate<JavaResourceMethod> buildPersistablePropertyGetterMethodsFilter() {
		return new Predicate<JavaResourceMethod>() {
			public boolean evaluate(JavaResourceMethod resourceMethod) {
				return methodIsPersistablePropertyGetter(resourceMethod, AbstractJavaPersistentType.this.getResourceMethods());
			}
		};
	}

	/**
	 * Return whether the specified method is a "getter" method that
	 * represents a property that may be "persisted".
	 */
	public static boolean methodIsPersistablePropertyGetter(JavaResourceMethod resourceMethod, Iterable<JavaResourceMethod> allMethods) {
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
	public static JavaResourceMethod getValidSiblingSetMethod(JavaResourceMethod getMethod, Iterable<JavaResourceMethod> resourceMethods) {
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

	public static boolean methodsArePersistableProperties(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		if (setterMethod != null) {
			return true;
		}
		else if (getterMethod.isAnnotated()) {
			//annotated getter with no corresponding setter, bring into context model for validation purposes
			return true;
		}
		return false;
	}


	// ********** inheritance **********

	public Iterable<PersistentType> getInheritanceHierarchy() {
		return this.buildInheritanceHierarchy(this);
	}

	public Iterable<PersistentType> getAncestors() {
		return this.buildInheritanceHierarchy(this.superPersistentType);
	}

	protected Iterable<PersistentType> buildInheritanceHierarchy(PersistentType start) {
		// using a chain iterable to traverse up the inheritance tree
		return ObjectTools.chain(start, SUPER_PERSISTENT_TYPE_TRANSFORMER);
	}

	protected Iterable<JavaResourceType> getResourceInheritanceHierarchy() {
		return ObjectTools.chain(this.resourceType, new SuperJavaResourceTypeTransformer());
	}

	/**
	 * Transform a Java resource type into its super Java resource type.
	 */
	protected class SuperJavaResourceTypeTransformer
		extends AbstractTransformer<JavaResourceType, JavaResourceType>
	{
		// keep track of visited resource types to prevent cyclical inheritance
		private final HashSet<JavaResourceType> visitedResourceTypes = new HashSet<JavaResourceType>();

		@Override
		protected JavaResourceType transform_(JavaResourceType jrt) {
			this.visitedResourceTypes.add(jrt);
			String superclassName = jrt.getSuperclassQualifiedName();
			if (superclassName == null) {
				return null;
			}
			JavaResourceType superJRT = AbstractJavaPersistentType.this.getJavaResourceType(superclassName);
			return ((superJRT == null) || this.visitedResourceTypes.contains(superJRT)) ?
				null :
				superJRT;
		}
	}

	protected JavaResourceType getJavaResourceType(String jrtName) {
		return (JavaResourceType) this.getJpaProject().getJavaResourceType(jrtName, JavaResourceAbstractType.AstNodeType.TYPE);
	}

	public TypeBinding getAttributeTypeBinding(ReadOnlyPersistentAttribute attribute) {
		JavaResourceAttribute resourceAttribute = attribute.getJavaPersistentAttribute().getResourceAttribute();
		if (resourceAttribute == null) {
			return null;
		}

		for (JavaResourceType jrt : this.getResourceInheritanceHierarchy()) {
			TypeBinding attributeType = jrt.getAttributeTypeBinding(resourceAttribute);
			if (attributeType != null) {
				return attributeType;
			}
		}

		// attribute was not found in this context
		throw new IllegalArgumentException(attribute.toString());
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<JavaPersistentType> getType() {
		return JavaPersistentType.class;
	}

	/**
	 * This method is called by JpaTextEditorManager only when the focus is not in the JPA Details view.
	 * See JptJpaUiPlugin.getFocusIsNonDali()
	 * <p>
	 * We are suppressing java events when the focus is in the JPA Details view so we don't
	 * want this synchronizeWithJavaSource() to be called in that case.
	 * When the user moves from the JPA Details view back to the Java source we need to call
	 * synchronizeWithJavaSource() in order for our cached text ranges to be updated appropriately.
	 * <p>
	 * Also need the synchronizeWithJavaSource() when editing directly in the java source,
	 * the textRange gets updated after the java delay which is after we are notified of a selection change.
	 */
	public JpaStructureNode getStructureNode(int offset) {
		this.resourceType.getJavaResourceCompilationUnit().synchronizeWithJavaSourceIfNecessary(); //TODO this new API? or just check isConsistent() right here?
		if (this.containsOffset(offset)) {
			for (JpaStructureNode child : this.getChildren()) {
				if (child.containsOffset(offset)) {
					return child;
				}
			}
			return this;
		}
		return null;
	}

	public TextRange getFullTextRange() {
		return this.resourceType.getTextRange();
	}

	public boolean containsOffset(int offset) {
		TextRange fullTextRange = this.getFullTextRange();
		// 'fullTextRange' will be null if the type no longer exists in the java;
		// the context model can be out of sync with the resource model
		// when a selection event occurs before the context model has a
		// chance to sync with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		// the type's resource can be null if the resource type is "external"
		if (ObjectTools.equals(this.getResource(), jpaFile.getFile())) {
			for (JpaStructureNode root : rootStructureNodes) {
				// the JPA file is a java file, so the already-added root nodes must be
				// Java managed types
				JavaManagedType jmt = (JavaManagedType) root;
				if (jmt.getName().equals(this.name)) {
					// no duplicates -
					// the first one found is used as a root in the structure view,
					// the others are ignored...
					return;
				}
			}
			rootStructureNodes.add(this);
		}
	}

	protected void initializeChildren() {
		this.children.addAll(this.attributes);
	}

	protected void updateChildren() {
		this.synchronizeCollection(this.attributes, this.children, CHILDREN_COLLECTION);
	}

	public Iterable<JavaPersistentAttribute> getChildren() {
		return IterableTools.cloneLive(this.children);
	}

	public int getChildrenSize() {
		return this.children.size();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		Iterable<String> values = this.mapping.getCompletionProposals(pos);
		if (values != null) {
			return values;
		}
		for (JavaPersistentAttribute attribute : this.getAttributes()) {
			values = attribute.getCompletionProposals(pos);
			if (values != null) {
				return values;
			}
		}
		return EmptyIterable.instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		if (MappingTools.modelIsInternalSource(this, this.resourceType)) {
			this.validateMapping(messages, reporter);
			this.validateAttributes(messages, reporter);
		}
	}

	protected void validateMapping(List<IMessage> messages, IReporter reporter) {
		try {
			this.mapping.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}

	protected void validateAttributes(List<IMessage> messages, IReporter reporter) {
		for (JavaPersistentAttribute attribute : this.getAttributes()) {
			this.validateAttribute(attribute, reporter, messages);
		}
	}

	protected void validateAttribute(JavaPersistentAttribute attribute, IReporter reporter, List<IMessage> messages) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}


	// ********** misc **********

	public AccessType getOwnerOverrideAccess() {
		return this.parent.getOverridePersistentTypeAccess();
	}

	public AccessType getOwnerDefaultAccess() {
		return this.parent.getDefaultPersistentTypeAccess();
	}

	public PersistentType getOverriddenPersistentType() {
		return null;  // Java persistent types do not override anything
	}


	// ********** Access type **********

	/**
	 * Return the access type currently implied by the specified Java source
	 * code or class file:<ul>
	 * <li>if any fields are annotated =>
	 *     {@link AccessType#FIELD FIELD}
	 * <li>if only properties are annotated =>
	 *     {@link AccessType#PROPERTY PROPERTY}
	 * <li>if neither are annotated =>
	 *     <code>null</code>
	 * </ul>
	 */
	public static AccessType buildAccess(JavaResourceType jrType) {
		for (JavaResourceField field : jrType.getFields()) {
			if (field.isAnnotated()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}

		for (JavaResourceMethod method : jrType.getMethods()) {
			if (method.isAnnotated()) {
				// none of the fields are annotated and any method is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}

		// nothing is annotated
		return null;
	}
}
