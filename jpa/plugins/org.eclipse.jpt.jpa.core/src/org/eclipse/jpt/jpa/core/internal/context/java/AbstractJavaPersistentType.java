/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedType;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AccessAnnotation2_0;
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
	extends AbstractJavaManagedType<JavaPersistentType.Parent>
	implements JavaPersistentType
{
	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;  // never null

	protected JavaTypeMapping mapping;  // never null

	protected final Vector<JavaSpecifiedPersistentAttribute> attributes = new Vector<>();

	protected final Vector<JavaSpecifiedPersistentAttribute> structureChildren = new Vector<>();


	protected AbstractJavaPersistentType(JavaPersistentType.Parent parent, JavaResourceType resourceType) {
		super(parent, resourceType);
		this.specifiedAccess = this.buildSpecifiedAccess();

		// keep this non-null
		this.defaultAccess = AccessType.FIELD;

		this.mapping = this.buildMapping();
		this.initializeAttributes();
		this.initializeStructureChildren();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncMapping(monitor);
		this.synchronizeModelsWithResourceModel(this.getAttributes(), monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultAccess(this.buildDefaultAccess());
		this.mapping.update(monitor);
		this.updateAttributes(monitor);
		this.updateStructureChildren();
	}
	
	
	// ********** inheritance **********
	
	public PersistentType getSuperPersistentType() {
		TypeMapping superTypeMapping = this.mapping.getSuperTypeMapping();
		return (superTypeMapping == null) ? null : superTypeMapping.getPersistentType();
	}
	
	
	// ********** access annotation **********

	protected AccessAnnotation2_0 getAccessAnnotation() {
		return (AccessAnnotation2_0) this.resourceType.getNonNullAnnotation(this.getAccessAnnotationName());
	}

	protected void removeAccessAnnotationIfUnset() {
		AccessAnnotation2_0 accessAnnotation = this.getAccessAnnotation();
		if ((accessAnnotation != null) && accessAnnotation.isUnset()) {
			this.removeAccessAnnotation();
		}
	}

	protected void removeAccessAnnotation() {
		this.resourceType.removeAnnotation(this.getAccessAnnotationName());
	}

	protected String getAccessAnnotationName() {
		return AccessAnnotation2_0.ANNOTATION_NAME;
	}


	// ********** access **********

	public AccessType getAccess() {
		return (this.specifiedAccess != null) ? this.specifiedAccess : this.defaultAccess;
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		if (ObjectTools.notEquals(this.specifiedAccess, access)) {
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
	 * <li>If still <code>null</code>, check {@link #getSuperPersistentType()} access
	 * <li>If still <code>null</code>, check <code>entity-mappings</code>
	 *     specified access setting if the corresponding <code>persistent-type</code>
	 *     is listed in a mapping (<code>orm.xml</code>) file
	 * <li>If still <code>null</code>, check the <code>persistence-unit</code>
	 *     default Access
	 * <li>Default to {@link AccessType#FIELD FIELD} if all else fails.
	 * </ul>
	 */
	protected AccessType buildDefaultAccess() {
		AccessType accessType = this.getResourceTypeAccess();
		if (accessType != null) {
			return accessType;
		}
		accessType = this.parent.getOverridePersistentTypeAccess();
		if (accessType != null) {
			return accessType;
		}

		PersistentType superType = this.getSuperPersistentType();
		if (superType != null) {
			accessType = superType.getAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		accessType = this.parent.getDefaultPersistentTypeAccess();
		if (accessType != null) {
			return accessType;
		}

		// last ditch attempt to allow the user to annotate *something*
		return AccessType.FIELD;
	}

	/**
	 * Return the access type currently implied by the Java source
	 * code or class file:<ul>
	 * <li>if any fields are annotated =>
	 *     {@link AccessType#FIELD FIELD}
	 * <li>if only properties are annotated =>
	 *     {@link AccessType#PROPERTY PROPERTY}
	 * <li>if neither are annotated =>
	 *     <code>null</code>
	 * </ul>
	 */
	protected AccessType getResourceTypeAccess() {
		for (JavaResourceField field : this.resourceType.getFields()) {
			if (field.isAnnotated()) {
				// any field is annotated => FIELD
				return AccessType.FIELD;
			}
		}

		for (JavaResourceMethod method : this.resourceType.getMethods()) {
			if (method.isAnnotated()) {
				// none of the fields are annotated and any method is annotated => PROPERTY
				return AccessType.PROPERTY;
			}
		}

		// nothing is annotated
		return null;
	}


	// ********** mapping **********

	public JavaTypeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public void setMappingKey(String key) {
		if (ObjectTools.notEquals(key, this.getMappingKey())) {
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

	protected void syncMapping(IProgressMonitor monitor) {
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
			this.mapping.synchronizeWithResourceModel(monitor);
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

	public ListIterable<JavaSpecifiedPersistentAttribute> getAttributes() {
		return IterableTools.cloneLive(this.attributes);
	}

	public int getAttributesSize() {
		return this.attributes.size();
	}

	public Iterable<String> getAttributeNames() {
		return this.convertToNames(this.getAttributes());
	}

	public JavaSpecifiedPersistentAttribute getAttributeNamed(String attributeName) {
		Iterator<JavaSpecifiedPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		return stream.hasNext() ? stream.next() : null;
	}

	public JavaSpecifiedPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute) {
		for (JavaSpecifiedPersistentAttribute javaAttribute : this.getAttributes()) {
			if (javaAttribute.getResourceAttribute() == javaResourceAttribute) {
				return javaAttribute;
			}
		}
		return null;
	}

	public Iterable<PersistentAttribute> getAllAttributes() {
		return IterableTools.children(
					this.getInheritanceHierarchy(),
					PersistentType.ATTRIBUTES_TRANSFORMER
			);
	}

	public Iterable<String> getAllAttributeNames() {
		return this.convertToNames(this.getAllAttributes());
	}

	protected Iterable<JavaSpecifiedPersistentAttribute> getAttributesNamed(String attributeName) {
		return IterableTools.filter(this.getAttributes(), new PersistentAttribute.NameEquals(attributeName));
	}

	public PersistentAttribute resolveAttribute(String attributeName) {
		Iterator<JavaSpecifiedPersistentAttribute> stream = this.getAttributesNamed(attributeName).iterator();
		if (stream.hasNext()) {
			JavaSpecifiedPersistentAttribute attribute = stream.next();
			// return null if we have more than one
			return stream.hasNext() ? null : attribute;
		}
		// recurse
		PersistentType superType = this.getSuperPersistentType();
		return (superType == null) ? null : superType.resolveAttribute(attributeName);
	}

	protected Iterable<String> convertToNames(Iterable<? extends PersistentAttribute> attrs) {
		return IterableTools.transform(attrs, PersistentAttribute.NAME_TRANSFORMER);
	}

	protected JavaSpecifiedPersistentAttribute buildField(JavaResourceField resourceField) {
		return this.getJpaFactory().buildJavaPersistentField(this, resourceField);
	}

	protected JavaSpecifiedPersistentAttribute buildProperty(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return this.getJpaFactory().buildJavaPersistentProperty(this, resourceGetter, resourceSetter);
	}

	public boolean hasAnyAnnotatedAttributes() {
		return this.resourceType.hasAnyAnnotatedFields() || this.resourceType.hasAnyAnnotatedMethods();
	}

	protected void moveAttribute(int index, JavaSpecifiedPersistentAttribute attribute) {
		this.moveItemInList(index, attribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void addAttribute(int index, JavaSpecifiedPersistentAttribute persistentAttribute) {
		this.addItemToList(index, persistentAttribute, this.attributes, ATTRIBUTES_LIST);
	}

	protected void removeAttribute(JavaSpecifiedPersistentAttribute attribute) {
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
	 * 1. all non-transient, non-static fields + annotated fields
	 * 2. all annotated methods (getters/setters)
	 */
	private void intializeFieldAccessAttributes() {
		this.initializeFieldAttributes(JavaResourceField.IS_RELEVANT_FOR_FIELD_ACCESS);
		this.initializeAnnotatedPropertyAttributes();
	}

	private void initializeFieldAttributes(Predicate<? super JavaResourceField> filter) {
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
		this.initializeFieldAttributes(JavaResourceAnnotatedElement.IS_ANNOTATED);

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

	private void initializeAnnotatedPropertyAttributes() {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
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
	protected void updateAttributes(IProgressMonitor monitor) {
		if (this.getAccess() == AccessType.FIELD) {
			this.syncFieldAccessAttributes(monitor);
		}
		else if (this.getAccess() == AccessType.PROPERTY) {
			this.syncPropertyAccessAttributes(monitor);
		}
	}

	/**
	 * Initialize the attributes for AccessType.FIELD
	 * 1. all non-transient, non-static fields + annotated fields
	 * 2. all annotated methods(getters/setters)
	 */
	private void syncFieldAccessAttributes(IProgressMonitor monitor) {
		HashSet<JavaSpecifiedPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, JavaResourceField.IS_RELEVANT_FOR_FIELD_ACCESS, monitor);
		this.syncAnnotatedPropertyAttributes(contextAttributes, monitor);
	}

	/**
	 * Initialize the attributes for XmlAccessType.PROPERTY
	 * 1. all getter/setter javabeans pairs
	 * 2. all annotated fields
	 * 3. all annotated methods getters/setters that don't have a matching pair
	 */
	private void syncPropertyAccessAttributes(IProgressMonitor monitor) {
		HashSet<JavaSpecifiedPersistentAttribute> contextAttributes = CollectionTools.hashSet(this.getAttributes());

		this.syncFieldAttributes(contextAttributes, JavaResourceAnnotatedElement.IS_ANNOTATED, monitor);

		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (this.includeProperty(getterMethod, setterMethod)) {
				boolean match = false;
				for (Iterator<JavaSpecifiedPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaSpecifiedPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(getterMethod, setterMethod)) {
						match = true;
						contextAttribute.update(monitor);
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
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods, monitor);
	}

	private void syncAnnotatedPropertyAttributes(HashSet<JavaSpecifiedPersistentAttribute> contextAttributes, IProgressMonitor monitor) {
		Collection<JavaResourceMethod> resourceMethods = CollectionTools.hashBag(this.getResourceMethods());
		//iterate through all resource methods searching for persistable getters
		for (JavaResourceMethod getterMethod : this.getResourcePropertyGetters()) {
			JavaResourceMethod setterMethod = JavaResourceMethod.SET_METHOD_TRANSFORMER.transform(getterMethod);
			if (getterMethod.isAnnotated() || ((setterMethod != null) && setterMethod.isAnnotated())) {
				boolean match = false;
				for (Iterator<JavaSpecifiedPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaSpecifiedPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(getterMethod, setterMethod)) {
						match = true;
						contextAttribute.update(monitor);
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
		this.syncRemainingResourceMethods(contextAttributes, resourceMethods, monitor);
	}

	private void syncFieldAttributes(HashSet<JavaSpecifiedPersistentAttribute> contextAttributes, Predicate<? super JavaResourceField> filter, IProgressMonitor monitor) {
		for (JavaResourceField resourceField : this.getResourceFields(filter)) {
			boolean match = false;
			for (Iterator<JavaSpecifiedPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext(); ) {
				JavaSpecifiedPersistentAttribute contextAttribute = stream.next();
				if (contextAttribute.isFor(resourceField)) {
					match = true;
					contextAttribute.update(monitor);
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

	private void syncRemainingResourceMethods(HashSet<JavaSpecifiedPersistentAttribute> contextAttributes, Collection<JavaResourceMethod> resourceMethods, IProgressMonitor monitor) {
		//iterate through remaining resource methods and search for those that are annotated.
		//all getter methods will already be used.
		for (JavaResourceMethod resourceMethod : resourceMethods) {
			if (resourceMethod.isAnnotated()) {
				boolean match = false;
				//annotated setter(or other random method) with no corresponding getter, bring into context model for validation purposes
				for (Iterator<JavaSpecifiedPersistentAttribute> stream = contextAttributes.iterator(); stream.hasNext();) {
					JavaSpecifiedPersistentAttribute contextAttribute = stream.next();
					if (contextAttribute.isFor(null, resourceMethod)) {
						match = true;
						contextAttribute.update(monitor);
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
		for (JavaSpecifiedPersistentAttribute contextAttribute : contextAttributes) {
			this.removeAttribute(contextAttribute);
		}
	}


	protected Iterable<JavaResourceField> getResourceFields() {
		return this.resourceType.getFields();
	}

	protected Iterable<JavaResourceMethod> getResourceMethods() {
		return this.resourceType.getMethods();
	}

	protected Iterable<JavaResourceField> getResourceFields(Predicate<? super JavaResourceField> filter) {
		return IterableTools.filter(this.getResourceFields(), filter);
	}

	protected Iterable<JavaResourceMethod> getResourcePropertyGetters() {
		return this.filterResourceMethods(JavaResourceMethod.IS_PROPERTY_GETTER);
	}

	protected Iterable<JavaResourceMethod> filterResourceMethods(Predicate<JavaResourceMethod> predicate) {
		return IterableTools.filter(this.getResourceMethods(), predicate);
	}

	/**
	 * Return whether the pair of methods form a "property" (sorta).
	 * An annotated getter with no setter is still brought into the context
	 * model (for validation purposes)....
	 */
	protected boolean includeProperty(JavaResourceMethod getterMethod, JavaResourceMethod setterMethod) {
		return (setterMethod != null) || getterMethod.isAnnotated();
	}

	/**
	 * Forward notification to parent.
	 * This is relevant only when the parent is an ORM persistent type,
	 * which will update its default attributes.
	 */
	public void attributeChanged(PersistentAttribute attribute) {
		this.parent.attributeChanged((JavaSpecifiedPersistentAttribute) attribute);
	}


	// ********** inheritance **********
	
	public Iterable<PersistentType> getInheritanceHierarchy() {
		return IterableTools.insert(this, this.getAncestors());
	}
	
	public Iterable<PersistentType> getAncestors() {
		return IterableTools.transform(this.getMapping().getAncestors(), TypeMapping.PERSISTENT_TYPE_TRANSFORMER);
	}
	
	protected Iterable<JavaResourceType> getResourceInheritanceHierarchy() {
		return (this.resourceType == null) ?
				IterableTools.<JavaResourceType>emptyIterable() :
				ObjectTools.chain(this.resourceType, new SuperJavaResourceTypeTransformer());
	}
	
	/**
	 * Transform a Java resource type into its super Java resource type.
	 */
	protected class SuperJavaResourceTypeTransformer
		extends TransformerAdapter<JavaResourceType, JavaResourceType>
	{
		// keep track of visited resource types to prevent cyclical inheritance
		private final HashSet<JavaResourceType> visitedResourceTypes = new HashSet<>();

		@Override
		public JavaResourceType transform(JavaResourceType jrt) {
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

	public TypeBinding getAttributeTypeBinding(PersistentAttribute attribute) {
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

	public Class<PersistentType> getManagedTypeType() {
		return PersistentType.class;
	}

	public Class<JavaPersistentType> getStructureType() {
		return JavaPersistentType.class;
	}

	/**
	 * This method is called by <code>JpaTextEditorManager</code> only when the
	 * focus is <em>not</em> in the JPA Details view.
	 * See <code>JptJpaUiPlugin.focusIsNotInDaliView()</code>.
	 * <p>
	 * We are suppressing Java events when the focus is in the JPA Details view
	 * so we don't want to call
	 * {@link org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit#synchronizeWithJavaSourceIfNecessary()}
	 * in that case.
	 * When the user moves from the JPA Details view to the Java source editor
	 * we need to call 
	 * {@link org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit#synchronizeWithJavaSourceIfNecessary()}
	 * in order for our cached text ranges to be updated appropriately.
	 * <p>
	 * We also need to call 
	 * {@link org.eclipse.jpt.common.core.resource.java.JavaResourceCompilationUnit#synchronizeWithJavaSourceIfNecessary()}
	 * when directly editing the Java source, as our text ranges are
	 * updated after the Java delay, which is after we are notified of a selection change.
	 */
	public JpaStructureNode getStructureNode(int offset) {
		this.resourceType.getJavaResourceCompilationUnit().synchronizeWithJavaSourceIfNecessary();
		if (this.containsOffset(offset)) {
			for (JpaStructureNode child : this.getStructureChildren()) {
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

	public void addRootStructureNodesTo(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
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

	protected void initializeStructureChildren() {
		this.structureChildren.addAll(this.attributes);
	}

	protected void updateStructureChildren() {
		this.synchronizeCollection(this.getAttributes(), this.structureChildren, STRUCTURE_CHILDREN_COLLECTION);
	}

	public Iterable<JavaSpecifiedPersistentAttribute> getStructureChildren() {
		return IterableTools.cloneLive(this.structureChildren);
	}

	public int getStructureChildrenSize() {
		return this.structureChildren.size();
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
		for (JavaSpecifiedPersistentAttribute attribute : this.getAttributes()) {
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
		for (JavaSpecifiedPersistentAttribute attribute : this.getAttributes()) {
			this.validateAttribute(attribute, reporter, messages);
		}
	}

	protected void validateAttribute(JavaSpecifiedPersistentAttribute attribute, IReporter reporter, List<IMessage> messages) {
		try {
			attribute.validate(messages, reporter);
		} catch(Throwable t) {
			JptJpaCorePlugin.instance().logError(t);
		}
	}


	// ********** misc **********

	public PersistentType getOverriddenPersistentType() {
		return null;  // Java persistent types do not override anything
	}
}
