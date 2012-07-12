/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import java.util.List;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * Java persistent attributes
 */
public abstract class AbstractJavaPersistentAttribute
		extends AbstractJavaJpaContextNode
		implements JavaPersistentAttribute2_0 {
	
	protected final Accessor accessor;

	protected AccessType defaultAccess;
	protected AccessType specifiedAccess;

	protected JavaAttributeMapping mapping;  // never null
	protected String defaultMappingKey;

	protected JpaContainerDefinition jpaContainerDefinition = JpaContainerDefinition.Null.instance();

	protected AbstractJavaPersistentAttribute(PersistentType parent, JavaResourceField resourceField) {
		super(parent);
		this.accessor = new FieldAccessor(this, resourceField);
		this.initialize();
	}

	protected AbstractJavaPersistentAttribute(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		super(parent);
		this.accessor = new PropertyAccessor(this, resourceGetter, resourceSetter);
		this.initialize();
	}

	protected AbstractJavaPersistentAttribute(PersistentType parent, Accessor accessor) {
		super(parent);
		this.accessor = accessor;
		this.initialize();
	}

	protected void initialize() {
		// this is determined directly from the resource model
		this.defaultAccess = this.buildDefaultAccess();
		this.specifiedAccess = this.buildSpecifiedAccess();

		// keep non-null at all times
		this.mapping = this.buildMapping();
	}

	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		// this is determined directly from the resource model
		this.setDefaultAccess(this.buildDefaultAccess());
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.syncMapping();
	}

	@Override
	public void update() {
		super.update();
		this.updateJpaContainerDefinition();
		this.updateMapping();
	}

	// ********** name **********

	/**
	 * name will not change in java, a new persistent attribute will be built.
	 */
	public String getName() {
		return this.getResourceAttribute().getName();
	}


	public Accessor getAccessor() {
		return this.accessor;
	}

	public boolean isFor(JavaResourceField resourceField) {
		return this.accessor.isFor(resourceField);
	}

	public boolean isFor(JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter) {
		return this.accessor.isFor(resourceGetter, resourceSetter);
	}
	
	// ********** access **********

	public AccessType getAccess() {
		AccessType access = this.getSpecifiedAccess();
		return (access != null) ? access : this.defaultAccess;
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType access) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildDefaultAccess() {
		return getAccessor().getDefaultAccess();
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	/**
	 * Don't support changing to specified access on a java persistent attribute, this
	 * involves a larger process of moving the annotations to the corresponding field/property
	 * which may or may not exist or might need to be created.
	 */
	public void setSpecifiedAccess(AccessType specifiedAccess) {
		throw new UnsupportedOperationException();
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	/**
	 * a specified Access annotation is only supported by JPA 2.0, but
	 * putting this code here to simplify. A 1.0 persistent attribute
	 * will never have an Access annotation on it, specifiedAccess will
	 * always be null.
	 */
	protected AccessType buildSpecifiedAccess() {
		Access2_0Annotation accessAnnotation = this.getAccessAnnotation();
		return (accessAnnotation == null) ? null : AccessType.fromJavaResourceModel(accessAnnotation.getValue(), this.getJpaPlatform(), this.getResourceType());
	}

	protected Access2_0Annotation getAccessAnnotation() {
		return (Access2_0Annotation) this.getResourceAttribute().getAnnotation(Access2_0Annotation.ANNOTATION_NAME);
	}


	// ********** mapping **********

	public JavaAttributeMapping getMapping() {
		return this.mapping;
	}

	/**
	 * Clients do not set the mapping directly.
	 * @see #setMappingKey(String)
	 */
	protected void setMapping(JavaAttributeMapping mapping) {
		JavaAttributeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	/**
	 * Possible transitions:
	 * <table border>
	 * <th>
	 * <th>null mapping/default<br>
	 *     <code>key = null</code>
	 * <th>specified mapping A<br>
	 *     <code>key = "A"</code>
	 * <th>specified mapping B<br>
	 *     <code>key = "B"</code>
	 * <tr>
	 * <th>[default] null mapping
	 *   <td>do nothing
	 *   <td>add annotation A<br>
	 *       set new mapping A
	 *   <td>add annotation B<br>
	 *       set new mapping B
	 * <tr>
	 * <th>default mapping A
	 *   <td>do nothing
	 *   <td>add annotation A<br>
	 *       <em>re-use</em> default mapping A
	 *   <td>add annotation B<br>
	 *       set new mapping B
	 * <tr>
	 * <th>specified mapping A
	 *   <td>remove annotation A<br>
	 *       set new default or null mapping
	 *   <td>do nothing
	 *   <td>remove annotation A<br>
	 *       add annotation B<br>
	 *       set new mapping B
	 * </table>
	 * The "do nothing" transitions are handled in this method.
	 */
	public JavaAttributeMapping setMappingKey(String key) {
		if (this.mapping.isDefault()) {
			if (key == null) {
				// leave the default mapping unchanged
			} else {
				this.setMappingKey_(key);  // replace the default mapping
			}
		} else {
			if (this.valuesAreEqual(key, this.mapping.getKey())) {
				// leave the specified mapping unchanged
			} else {
				this.setMappingKey_(key);  // replace the specified mapping
			}
		}
		return this.mapping;
	}

	/**
	 * We have either:<ul>
	 * <li>a <em>default</em> mapping and a non-<code>null</code> key
	 * </ul>or<ul>
	 * <li>a <em>specified</em> mapping and a different (possibly
	 *     <code>null</code>) key
	 * </ul>
	 */
	protected void setMappingKey_(String key) {
		JavaAttributeMappingDefinition definition = this.getSpecifiedMappingDefinition(key);
		if (definition == null) {
			// our mapping is "specified" and the key is null;
			// check for a default definition
			definition = this.getDefaultMappingDefinition();
			Iterable<String> supportingAnnotationNames = (definition != null) ? definition.getSupportingAnnotationNames() : EmptyIterable.<String>instance();
			// clear any mapping annotation(s);
			// leave the "default" mapping's supporting annotations;
			// if there is no "default" mapping, clear all supporting annotations too(?)
			this.setMappingAnnotation(null, supportingAnnotationNames);
		} else {
			this.setMappingAnnotation(definition);
		}
		// note: 'definition' can still be null (if the key is null and there is no "default" mapping)
		this.setMapping(this.buildMapping(definition));
	}

	/**
	 * pre-condition: definition is not <code>null</code>
	 */
	protected void setMappingAnnotation(JavaAttributeMappingDefinition definition) {
		this.setMappingAnnotation(definition.getAnnotationName(), definition.getSupportingAnnotationNames());
	}

	protected void setMappingAnnotation(String primaryAnnotationName, Iterable<String> supportingAnnotationNames) {
		this.getResourceAttribute().setPrimaryAnnotation(primaryAnnotationName, supportingAnnotationNames);
	}

	protected JavaAttributeMapping buildMapping(JavaAttributeMappingDefinition definition) {
		return (definition == null) ? this.buildNullMapping() : this.buildMapping_(definition);
	}

	protected JavaAttributeMapping buildNullMapping() {
		return this.getJpaFactory().buildJavaNullAttributeMapping(this);
	}

	/**
	 * pre-condition: definition is not null
	 * <p>
	 * If we are converting a <em>default</em> mapping to its <em>specified</em>
	 * manifestation, we just keep the same mapping and create its annotation.
	 * We do <em>not</em> do the same thing when converting a <em>specified</em>
	 * mapping to its <em>default</em> manifestation. We rebuild the
	 * entire mapping, simplifying the clearing of all its state. We do this
	 * because we allow clients to modify a <em>default</em> mapping (or any of
	 * its components) directly,
	 * modifying its state and triggering a conversion to a <em>specified</em>
	 * mapping. The only way to convert a <em>specified</em> mapping to a
	 * <em>default</em> mapping is by {@link #setMappingKey(String) setting the
	 * mapping key} to <code>null</code>.
	 */
	protected JavaAttributeMapping buildMapping_(JavaAttributeMappingDefinition definition) {
		// 'mapping' is null during construction
		if ((this.mapping != null) && this.mapping.isDefault() && Tools.valuesAreEqual(this.mapping.getKey(), definition.getKey())) {
			this.mapping.updateDefault();  // since nothing here changes, we need to update the mapping's flag
			return this.mapping;
		}
		return definition.buildMapping(this, this.getJpaFactory());
	}

	/**
	 * We only look for a <em>specified</em> mapping here.
	 * We look for a default mapping during <em>update</em>.
	 */
	protected JavaAttributeMapping buildMapping() {
		return this.buildMapping(this.getSpecifiedMappingDefinition());
	}

	/**
	 * Sync existing mapping
	 */
	protected void syncMapping() {
		this.mapping.synchronizeWithResourceModel();
	}
	
	/**
	 * Return the "specified" mapping definition for the specified key.
	 */
	protected JavaAttributeMappingDefinition getSpecifiedMappingDefinition(String key) {
		if (key == null) {
			return null;
		}
		for (JavaAttributeMappingDefinition definition : this.getSpecifiedMappingDefinitions()) {
			if (Tools.valuesAreEqual(definition.getKey(), key)) {
				return definition;
			}
		}
		throw new IllegalArgumentException("invalid mapping key: " + key); //$NON-NLS-1$
	}

	/**
	 * Return the mapping definition for the mapping currently specified in the
	 * source code.
	 */
	protected JavaAttributeMappingDefinition getSpecifiedMappingDefinition() {
		if (this.accessor.getResourceAttribute().isAnnotated()) { //short-circuit for performance
			for (JavaAttributeMappingDefinition definition : this.getSpecifiedMappingDefinitions()) {
				if (definition.isSpecified(this)) {
					return definition;
				}
			}
		}
		return null;
	}

	protected Iterable<JavaAttributeMappingDefinition> getSpecifiedMappingDefinitions() {
		return this.getJpaPlatform().getSpecifiedJavaAttributeMappingDefinitions();
	}


	// ********** default mapping **********

	public String getDefaultMappingKey() {
		return this.defaultMappingKey;
	}

	protected void setDefaultMappingKey(String mappingKey) {
		String old = this.defaultMappingKey;
		this.defaultMappingKey = mappingKey;
		this.firePropertyChanged(DEFAULT_MAPPING_KEY_PROPERTY, old, mappingKey);
	}

	/**
	 * A default mapping may depend on another resource, and the specified mapping
	 * may depend on the default mapping, so we must analyze both here.
	 */
	protected void updateMapping() {
		JavaAttributeMappingDefinition newDefinition = null;
		
		JavaAttributeMappingDefinition defaultDefinition = this.getDefaultMappingDefinition();
		String newDefaultKey = (defaultDefinition == null) ? null : defaultDefinition.getKey();
		if (Tools.valuesAreDifferent(this.mapping.getKey(), newDefaultKey)) {
			newDefinition = defaultDefinition;  // the default mapping has changed - hold on to the definition
		}
		this.setDefaultMappingKey(newDefaultKey);
		
		JavaAttributeMappingDefinition specifiedDefinition = this.getSpecifiedMappingDefinition();
		String newSpecifiedKey = (specifiedDefinition == null) ? null : specifiedDefinition.getKey();
		if (specifiedDefinition != null) {
			newDefinition = null;  // unset definition if it was set from default calculation
			if (this.mapping.isDefault() || Tools.valuesAreDifferent(this.mapping.getKey(), newSpecifiedKey)) {
				newDefinition = specifiedDefinition; // mapping is now specified or a different specified
			}
		}
		
		if (newDefinition != null) {
			setMapping(buildMapping(newDefinition));
		}
		else {
			this.mapping.update();
		}
	}
	
	protected JavaAttributeMappingDefinition getDefaultMappingDefinition() {
		for (DefaultJavaAttributeMappingDefinition definition : this.getDefaultMappingDefinitions()) {
			if (definition.isDefault(this)) {
				return definition;
			}
		}
		return null;
	}

	protected Iterable<DefaultJavaAttributeMappingDefinition> getDefaultMappingDefinitions() {
		return this.getJpaPlatform().getDefaultJavaAttributeMappingDefinitions();
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<JavaPersistentAttribute> getType() {
		return JavaPersistentAttribute.class;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.getSelectionTextRange(this.buildASTRoot());
	}

	protected TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.getResourceAttribute().getNameTextRange(astRoot);
	}

	protected CompilationUnit buildASTRoot() {
		return this.getResourceAttribute().getJavaResourceCompilationUnit().buildASTRoot();
	}

	public void dispose() {
		// nothing to dispose
	}


	// ********** type **********

	/**
	 * From the JPA spec, when the basic mapping applies:<br>
	 * If the type of the attribute (field or property) is one of the following
	 * it must be mapped as <code>@javax.persistence.Basic</code>:<ul>
	 * <li><code>byte[]</code>
	 * <li><code>java.lang.Byte[]</code>
	 * <li><code>char[]</code>
	 * <li><code>java.lang.Character[]</code>
	 * <li>primitive types (except <code>void</code>)
	 * <li>primitive wrappers (except <code>java.lang.Void</code>)
	 * <li><code>java.lang.String</code>
	 * <li><code>java.math.BigInteger</code>
	 * <li><code>java.math.BigDecimal</code>
	 * <li><code>java.util.Date</code>
	 * <li><code>java.util.Calendar</code>
	 * <li><code>java.sql.Date</code>
	 * <li><code>java.sql.Time</code>
	 * <li><code>java.sql.Timestamp</code>
	 * <li><code>enum</code>s
	 * <li>any other type that implements <code>java.io.Serializable</code>
	 * </ul>
	 */
	//Beware of trying to change this to use MappingTools.typeIsBasic, see bug 374831
	public boolean typeIsBasic() {
		// 'typeName' may include array brackets but not generic type arguments
		String typeName = this.getTypeName();
		if (typeName == null) {
			return false;
		}

		int arrayDepth = ReflectionTools.getArrayDepthForTypeDeclaration(typeName);
		if (arrayDepth > 1) {
			return false;  // multi-dimensional arrays are not supported
		}

		if (arrayDepth == 1) {
			String elementTypeName = ReflectionTools.getElementTypeNameForTypeDeclaration(typeName, 1);
			return MappingTools.elementTypeIsValidForBasicArray(elementTypeName);
		}

		// arrayDepth == 0
		if (ClassName.isVariablePrimitive(typeName)) {
			return true;  // any primitive but 'void'
		}
		if (ClassName.isVariablePrimitiveWrapper(typeName)) {
			return true;  // any primitive wrapper but 'java.lang.Void'
		}
		if (MappingTools.typeIsOtherValidBasicType(typeName)) {
			return true;
		}
		if (this.getResourceAttribute().getTypeBinding().isEnum()) {
			return true;
		}
		if (this.getResourceAttribute().getTypeBinding().isSubTypeOf(JDTTools.SERIALIZABLE_CLASS_NAME)) {
			return true;
		}
		return false;
	}

	public String getSingleReferenceTargetTypeName() {
		// 'typeName' may include array brackets ("[]")
		// but not generic type arguments (e.g. "<java.lang.String>")
		String typeName = this.getTypeName();
		if (typeName == null) {
			return null;
		}
		if (ReflectionTools.getArrayDepthForTypeDeclaration(typeName) != 0) {
			return null;  // arrays cannot be entities
		}
		if (this.typeIsContainer()) {
			return null;  // "containers" cannot be entities
		}
		return typeName;
	}

	public String getMultiReferenceTargetTypeName() {
		return this.getJpaContainerDefinition().getMultiReferenceTargetTypeName(this.getResourceAttribute());
	}

	public String getMultiReferenceMapKeyTypeName() {
		return this.getJpaContainerDefinition().getMultiReferenceMapKeyTypeName(this.getResourceAttribute());
	}

	/**
	 * return whether the specified type is one of the container
	 * types allowed by the JPA spec
	 */
	protected boolean typeIsContainer() {
		return this.jpaContainerDefinition.isContainer();
	}


	// ********** misc **********

	@Override
	public PersistentType getParent() {
		return (PersistentType) super.getParent();
	}

	public PersistentType getOwningPersistentType() {
		return this.getParent();
	}

	public TypeMapping getOwningTypeMapping() {
		return this.getOwningPersistentType().getMapping();
	}

	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this;
	}

	public JavaResourceAttribute getResourceAttribute() {
		return this.accessor.getResourceAttribute();
	}

	public String getPrimaryKeyColumnName() {
		return this.mapping.getPrimaryKeyColumnName();
	}

	public String getTypeName() {
		return this.getResourceAttribute().getTypeBinding().getQualifiedName();
	}

	public boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.getResourceAttribute().getTextRange(astRoot);
		// 'fullTextRange' will be null if the attribute no longer exists in the java;
		// the context model can be out of sync with the resource model
		// when a selection event occurs before the context model has a
		// chance to sync with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.isVirtual() ?
				this.getOwningPersistentType().getValidationTextRange() :
				this.getSelectionTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);
		this.validateAttribute(messages, reporter, astRoot);
		this.mapping.validate(messages, reporter, astRoot);
	}

	protected void validateAttribute(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		this.buildAttributeValidator(astRoot).validate(messages, reporter);
	}

	protected abstract JptValidator buildAttributeValidator(CompilationUnit astRoot);

	protected PersistentAttributeTextRangeResolver buildTextRangeResolver(CompilationUnit astRoot) {
		return new JavaPersistentAttributeTextRangeResolver(this, astRoot);
	}

	/**
	 * If the attribute's owning type is an <code>orm.xml</code> persistent
	 * type, the attribute must be one of the type's default attributes.
	 * (Actually, the Java attribute is held by a <em>virtual</em>
	 * <code>orm.xml</code> attribute
	 * (see {@link org.eclipse.jpt.jpa.core.internal.context.orm.VirtualOrmPersistentAttribute})
	 * that is held by the <code>orm.xml</code> type.
	 * The <em>virtual</em> attribute returns the Java attribute's mapping
	 * as its own.)
	 */
	public boolean isVirtual() {
		IContentType persistentTypeContentType = this.getOwningPersistentType().getResourceType().getContentType();
		return ! persistentTypeContentType.isKindOf(JptCommonCorePlugin.JAVA_SOURCE_CONTENT_TYPE)
				&& ! persistentTypeContentType.isKindOf(JptCommonCorePlugin.JAR_CONTENT_TYPE);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return this.mapping.getJavaCompletionProposals(pos, filter, astRoot);
	}


	// ********** metamodel **********

	public String getMetamodelContainerFieldTypeName() {
		return this.getJpaContainerDefinition().getMetamodelContainerFieldTypeName();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		return this.getJpaContainerDefinition().getMetamodelContainerFieldMapKeyTypeName((CollectionMapping) this.mapping);
	}

	public String getMetamodelTypeName() {
		String typeName = this.getTypeName();
		if (typeName == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		if (ClassName.isPrimitive(typeName)) {
			return ClassName.getWrapperClassName(typeName);  // ???
		}
		return typeName;
	}


	// ********** JPA container definition **********

	public JpaContainerDefinition getJpaContainerDefinition() {
		return this.jpaContainerDefinition;
	}

	protected void updateJpaContainerDefinition() {
		this.setJpaContainerDefinition(this.buildJpaContainerDefinition());
	}

	protected void setJpaContainerDefinition(JpaContainerDefinition jpaContainerDefinition) {
		JpaContainerDefinition old = this.jpaContainerDefinition;
		this.jpaContainerDefinition = jpaContainerDefinition;
		firePropertyChanged(JPA_CONTAINER_DEFINITION, old, this.jpaContainerDefinition);
	}

	/**
	 * Return the JPA container definition corresponding to the specified type;
	 * return a "null" definition if the specified type is not a sub-type of one of the
	 * container types allowed by the JPA spec.
	 */
	protected JpaContainerDefinition buildJpaContainerDefinition() {
		for (JpaContainerDefinition definition : this.getJpaContainerDefinitions()) {
			if (this.getResourceAttribute().getTypeBinding().isSubTypeOf(definition.getTypeName())) {
				return definition;
			}
		}
		return JpaContainerDefinition.Null.instance();
	}

	protected Iterable<JpaContainerDefinition> getJpaContainerDefinitions() {
		return JPA_CONTAINER_DEFINITIONS;
	}

	protected static final JpaContainerDefinition[] JPA_CONTAINER_DEFINITION_ARRAY = new JpaContainerDefinition[] {
		new CollectionJpaContainerDefinition(java.util.Set.class, JPA2_0.SET_ATTRIBUTE),
		new CollectionJpaContainerDefinition(java.util.List.class, JPA2_0.LIST_ATTRIBUTE),
		new CollectionJpaContainerDefinition(java.util.Collection.class, JPA2_0.COLLECTION_ATTRIBUTE),
		new MapJpaContainerDefinition(java.util.Map.class, JPA2_0.MAP_ATTRIBUTE)
	};

	protected static final Iterable<JpaContainerDefinition> JPA_CONTAINER_DEFINITIONS = new ArrayIterable<JpaContainerDefinition>(JPA_CONTAINER_DEFINITION_ARRAY);


	/**
	 * Abstract JPA container definition
	 */
	protected abstract static class AbstractJpaContainerDefinition
		implements JpaContainerDefinition
	{
		protected final String typeName;
		protected final String metamodelContainerFieldTypeName;

		protected AbstractJpaContainerDefinition(Class<?> containerClass, String metamodelContainerFieldTypeName) {
			this(containerClass.getName(), metamodelContainerFieldTypeName);
		}

		protected AbstractJpaContainerDefinition(String typeName, String metamodelContainerFieldTypeName) {
			super();
			if ((typeName == null) || (metamodelContainerFieldTypeName == null)) {
				throw new NullPointerException();
			}
			this.typeName = typeName;
			this.metamodelContainerFieldTypeName = metamodelContainerFieldTypeName;
		}

		public String getTypeName() {
			return this.typeName;
		}

		public boolean isContainer() {
			return true;
		}

		public String getMetamodelContainerFieldTypeName() {
			return this.metamodelContainerFieldTypeName;
		}
	}

	/**
	 * Collection JPA container definition
	 */
	protected static class CollectionJpaContainerDefinition
		extends AbstractJpaContainerDefinition
	{
		protected CollectionJpaContainerDefinition(Class<?> collectionClass, String staticMetamodelTypeDeclarationTypeName) {
			super(collectionClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceTargetTypeName(JavaResourceAttribute resourceAttribute) {
			return (resourceAttribute.getTypeBinding().getTypeArgumentNamesSize() == 1) ?
				resourceAttribute.getTypeBinding().getTypeArgumentName(0) :
						null;
		}

		public String getMultiReferenceMapKeyTypeName(JavaResourceAttribute resourceAttribute) {
			return null;
		}

		public String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping) {
			return null;
		}

		public boolean isMap() {
			return false;
		}
	}

	/**
	 * Map JPA container definition
	 */
	protected static class MapJpaContainerDefinition
		extends AbstractJpaContainerDefinition
	{
		protected MapJpaContainerDefinition(Class<?> mapClass, String staticMetamodelTypeDeclarationTypeName) {
			super(mapClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceTargetTypeName(JavaResourceAttribute resourceAttribute) {
			return (resourceAttribute.getTypeBinding().getTypeArgumentNamesSize() == 2) ?
						resourceAttribute.getTypeBinding().getTypeArgumentName(1) :
						null;
		}

		public String getMultiReferenceMapKeyTypeName(JavaResourceAttribute resourceAttribute) {
			return (resourceAttribute.getTypeBinding().getTypeArgumentNamesSize() == 2) ?
						resourceAttribute.getTypeBinding().getTypeArgumentName(0) :
						null;
		}

		public String getMetamodelContainerFieldMapKeyTypeName(CollectionMapping mapping) {
			return mapping.getMetamodelFieldMapKeyTypeName();
		}

		public boolean isMap() {
			return true;
		}
	}

}
