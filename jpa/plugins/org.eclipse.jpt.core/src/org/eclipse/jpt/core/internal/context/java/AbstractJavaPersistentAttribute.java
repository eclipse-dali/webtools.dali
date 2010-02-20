/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.CollectionMapping;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.ClassName;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * common state/behavior between Generic and EclipseLink persistent attributes
 */
public abstract class AbstractJavaPersistentAttribute
	extends AbstractJavaJpaContextNode
	implements JavaPersistentAttribute2_0
{
	protected String name;

	protected JavaAttributeMapping defaultMapping;

	protected JavaAttributeMapping specifiedMapping;

	protected AccessType defaultAccess;

	protected JavaResourcePersistentAttribute resourcePersistentAttribute;


	protected AbstractJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute resourcePersistentAttribute) {
		super(parent);
		this.resourcePersistentAttribute = resourcePersistentAttribute;
		this.name = buildName();
		this.defaultMapping = buildDefaultMapping();
		this.specifiedMapping = buildSpecifiedMapping();
		this.defaultAccess = buildDefaultAccess();
	}

	public void update() {
		this.setName(this.buildName());
		this.updateDefaultMapping();
		this.updateSpecifiedMapping();
		this.setDefaultAccess(this.buildDefaultAccess());
	}

	@Override
	public void postUpdate() {
		super.postUpdate();
		getMapping().postUpdate();
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return JavaStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public JpaStructureNode getStructureNode(int textOffset) {
		return this;
	}

	public TextRange getSelectionTextRange() {
		return this.getSelectionTextRange(this.buildASTRoot());
	}

	protected TextRange getSelectionTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.getNameTextRange(astRoot);
	}

	protected CompilationUnit buildASTRoot() {
		return this.resourcePersistentAttribute.getJavaResourceCompilationUnit().buildASTRoot();
	}

	public void dispose() {
		//nothing to dispose
	}


	// ********** AccessHolder implementation **********

	public AccessType getAccess() {
		AccessType access = this.getSpecifiedAccess();
		return (access != null) ? access : this.getDefaultAccess();
	}

	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}
	
	protected void setDefaultAccess(AccessType defaultAccess) {
		AccessType old = this.defaultAccess;
		this.defaultAccess = defaultAccess;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, old, defaultAccess);
	}

	protected AccessType buildDefaultAccess() {
		return this.resourcePersistentAttribute.isField() ? AccessType.FIELD : AccessType.PROPERTY;
	}


	// ********** [Java]PersistentAttribute implementation **********

	public JavaResourcePersistentAttribute getResourcePersistentAttribute() {
		return this.resourcePersistentAttribute;
	}

	public PersistentType getOwningPersistentType() {
		return this.getParent();
	}

	public TypeMapping getOwningTypeMapping() {
		return this.getOwningPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
		return this.getMapping().getPrimaryKeyColumnName();
	}
	
	public String getTypeName() {
		return this.resourcePersistentAttribute.getTypeName();
	}

	public boolean isVirtual() {
		return false;
	}

	public boolean contains(int offset, CompilationUnit astRoot) {
		TextRange fullTextRange = this.getFullTextRange(astRoot);
		// 'fullTextRange' will be null if the attribute no longer exists in the java;
		// the context model can be out of synch with the resource model
		// when a selection event occurs before the context model has a
		// chance to synch with the resource model via the update thread
		return (fullTextRange == null) ? false : fullTextRange.includes(offset);
	}

	protected TextRange getFullTextRange(CompilationUnit astRoot) {
		return this.resourcePersistentAttribute.getTextRange(astRoot);
	}

	public Embeddable getEmbeddable() {
		return (getTypeName() == null) ? 
				null : this.getPersistenceUnit().getEmbeddable(getTypeName());
	}

	public boolean isField() {
		return this.resourcePersistentAttribute.isField();
	}

	public boolean isProperty() {
		return this.resourcePersistentAttribute.isProperty();
	}

	public boolean isPublic() {
		return Modifier.isPublic(this.resourcePersistentAttribute.getModifiers());
	}

	public boolean isFinal() {
		return Modifier.isFinal(this.resourcePersistentAttribute.getModifiers());
	}

	// ***** Basic defaults
	/**
	 * From the JPA spec, when the basic mapping applies:
	 * If the type of the attribute (field or property) is one of the following
	 * it must be mapped as @Basic:
	 *     byte[]
	 *     java.lang.Byte[]
	 *     char[]
	 *     java.lang.Character[]
	 *     primitive types (except 'void')
	 *     primitive wrappers (except 'java.lang.Void')
	 *     java.lang.String
	 *     java.math.BigInteger
	 *     java.math.BigDecimal
	 *     java.util.Date
	 *     java.util.Calendar
	 *     java.sql.Date
	 *     java.sql.Time
	 *     java.sql.Timestamp
	 *     enums
	 *     any other type that implements java.io.Serializable
	 */
	public boolean typeIsBasic() {
		// 'typeName' may include array brackets but not generic type arguments
		String typeName = getTypeName();
		if (typeName == null) {
			return false;
		}

		int arrayDepth = ReflectionTools.getArrayDepthForTypeDeclaration(typeName);
		if (arrayDepth > 1) {
			return false;  // multi-dimensional arrays are not supported
		}

		if (arrayDepth == 1) {
			String elementTypeName = ReflectionTools.getElementTypeNameForTypeDeclaration(typeName, 1);
			return this.elementTypeIsValidForBasicArray(elementTypeName);
		}

		// arrayDepth == 0
		if (ClassName.isVariablePrimitive(typeName)) {
			return true;  // any primitive but 'void'
		}
		if (ClassName.isVariablePrimitiveWrapper(typeName)) {
			return true;  // any primitive wrapper but 'java.lang.Void'
		}
		if (this.typeIsOtherValidBasicType(typeName)) {
			return true;
		}
		if (this.resourcePersistentAttribute.typeIsEnum()) {
			return true;
		}
		if (this.resourcePersistentAttribute.typeIsSubTypeOf(SERIALIZABLE_TYPE_NAME)) {
			return true;
		}
		return false;
	}

	/**
	 * Return whether the specified type is a valid element type for
	 * a one-dimensional array that can default to a Basic mapping:
	 *     byte
	 *     char
	 *     java.lang.Byte
	 *     java.lang.Character
	 */
	protected boolean elementTypeIsValidForBasicArray(String elementTypeName) {
		return ArrayTools.contains(VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES, elementTypeName);
	}

	protected static final String[] VALID_BASIC_ARRAY_ELEMENT_TYPE_NAMES = {
		byte.class.getName(),
		char.class.getName(),
		java.lang.Byte.class.getName(),
		java.lang.Character.class.getName()
	};

	/**
	 * Return whether the specified type is among the various "other" types
	 * that can default to a Basic mapping.
	 */
	protected boolean typeIsOtherValidBasicType(String typeName) {
		return ArrayTools.contains(OTHER_VALID_BASIC_TYPE_NAMES, typeName);
	}

	protected static final String[] OTHER_VALID_BASIC_TYPE_NAMES = {
		java.lang.String.class.getName(),
		java.math.BigInteger.class.getName(),
		java.math.BigDecimal.class.getName(),
		java.util.Date.class.getName(),
		java.util.Calendar.class.getName(),
		java.sql.Date.class.getName(),
		java.sql.Time.class.getName(),
		java.sql.Timestamp.class.getName(),
	};

	protected static final String SERIALIZABLE_TYPE_NAME = java.io.Serializable.class.getName();

	// ***** reference entities
	public String getSingleReferenceTargetTypeName() {
		// 'typeName' may include array brackets ("[]")
		// but not generic type arguments (e.g. "<java.lang.String>")
		String typeName = getTypeName();
		if (typeName == null) {
			return null;
		}
		if (ReflectionTools.getArrayDepthForTypeDeclaration(typeName) != 0) {
			return null;  // arrays cannot be entities
		}
		if (this.typeIsContainer(typeName)) {
			return null;  // "containers" cannot be entities
		}
		return typeName;
	}

	public String getMultiReferenceTargetTypeName() {
		return this.getJpaContainer().getMultiReferenceTargetTypeName(this.resourcePersistentAttribute);
	}

	public String getMultiReferenceMapKeyTypeName() {
		return this.getJpaContainer().getMultiReferenceMapKeyTypeName(this.resourcePersistentAttribute);
	}
	
	/**
	 * return whether the specified type is one of the container
	 * types allowed by the JPA spec
	 */
	protected boolean typeIsContainer(String typeName) {
		return this.getJpaContainer(typeName).isContainer();
	}

	// ***** name
	public String getName() {
		return this.name;
	}

	protected void setName(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	protected String buildName() {
		return this.resourcePersistentAttribute.getName();
	}

	// ***** mapping
	public JavaAttributeMapping getMapping() {
		return (this.specifiedMapping != null) ? this.specifiedMapping : this.defaultMapping;
	}

	public String getMappingKey() {
		return this.getMapping().getKey();
	}


	// ***** default mapping
	public JavaAttributeMapping getDefaultMapping() {
		return this.defaultMapping;
	}

	/**
	 * clients do not set the "default" mapping
	 */
	protected void setDefaultMapping(JavaAttributeMapping defaultMapping) {
		JavaAttributeMapping old = this.defaultMapping;
		this.defaultMapping = defaultMapping;
		this.firePropertyChanged(DEFAULT_MAPPING_PROPERTY, old, defaultMapping);
	}

	protected JavaAttributeMapping buildDefaultMapping() {
		JavaAttributeMappingDefinition mappingDefinition = 
			getJpaPlatform().getDefaultJavaAttributeMappingDefinition(this);
		return buildDefaultMapping(mappingDefinition);
	}
	
	protected JavaAttributeMapping buildDefaultMapping(JavaAttributeMappingDefinition mappingDefinition) {
		Annotation annotation = this.resourcePersistentAttribute.
				buildNullAnnotation(mappingDefinition.getAnnotationName());
		JavaAttributeMapping mapping = mappingDefinition.buildMapping(this, getJpaFactory());
		mapping.initialize(annotation);
		return mapping;
	}
	
	/**
	 * return null if there is no "default" mapping for the attribute
	 */
	public String getDefaultMappingKey() {
		return this.defaultMapping.getKey();
	}

	/**
	 * the mapping might be "default", but it still might be a "null" mapping...
	 */
	public boolean mappingIsDefault(JavaAttributeMapping mapping) {
		return this.defaultMapping == mapping;
	}

	protected void updateDefaultMapping() {
		// There will always be a mapping definition, even if it is a "null" mapping definition ...
		JavaAttributeMappingDefinition mappingDefinition = 
				getJpaPlatform().getDefaultJavaAttributeMappingDefinition(this);
		String mappingKey = mappingDefinition.getKey();
		if (this.valuesAreEqual(this.defaultMapping.getKey(), mappingKey)) {
			this.defaultMapping.update(this.resourcePersistentAttribute.
					buildNullAnnotation(mappingDefinition.getAnnotationName()));
		} 
		else {
			setDefaultMapping(buildDefaultMapping(mappingDefinition));
		}
	}

	// ***** specified mapping
	public JavaAttributeMapping getSpecifiedMapping() {
		return this.specifiedMapping;
	}

	/**
	 * clients do not set the "specified" mapping;
	 * @see #setSpecifiedMappingKey(String)
	 */
	protected void setSpecifiedMapping(JavaAttributeMapping specifiedMapping) {
		JavaAttributeMapping old = this.specifiedMapping;
		this.specifiedMapping = specifiedMapping;
		this.firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, old, specifiedMapping);
	}

	protected JavaAttributeMapping buildSpecifiedMapping() {
		JavaAttributeMappingDefinition mappingDefinition = 
				getJpaPlatform().getSpecifiedJavaAttributeMappingDefinition(this);
		return buildSpecifiedMapping(mappingDefinition);
	}
	
	protected JavaAttributeMapping buildSpecifiedMapping(JavaAttributeMappingDefinition mappingDefinition) {
		Annotation annotation = this.resourcePersistentAttribute.
				getAnnotation(mappingDefinition.getAnnotationName());
		JavaAttributeMapping mapping = mappingDefinition.buildMapping(this, getJpaFactory());
		// specified mappings may be null
		if (mapping != null) {
			mapping.initialize(annotation);
		}
		return mapping;
	}
	
	/**
	 * return the key of the currently specified mapping or null 
	 * if there is no specified mapping for the attribute
	 */
	protected String getSpecifiedMappingKey() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getKey();
	}

	// TODO support morphing mappings, i.e. copying common settings over
	// to the new mapping; this can't be done in the same was as XmlAttributeMapping
	// since we don't know all the possible mapping types
	public void setSpecifiedMappingKey(String key) {
		if (Tools.valuesAreEqual(key, this.getSpecifiedMappingKey())) {
			return;
		}
		JavaAttributeMapping old = this.specifiedMapping;
		JavaAttributeMapping newMapping = this.buildMappingFromMappingKey(key);
		
		this.specifiedMapping = newMapping;
		
		String newAnnotation = (newMapping == null) ? null : newMapping.getAnnotationName();
		JavaAttributeMapping mapping = (newMapping == null) ? this.defaultMapping : newMapping;
		this.resourcePersistentAttribute.setPrimaryAnnotation(newAnnotation, mapping.getSupportingAnnotationNames());
		this.firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, old, newMapping);
	}
	protected static final String[] EMPTY_STRING_ARRAY = new String[0];
	
	/**
	 * return the annotation name of the currently specified mapping or null 
	 * if there is no specified mapping for the attribute
	 */
	protected String getSpecifiedMappingAnnotationName() {
		return (this.specifiedMapping == null) ? null : this.specifiedMapping.getAnnotationName();
	}
	
	protected JavaAttributeMapping buildMappingFromMappingKey(String key) {
		JavaAttributeMappingDefinition mappingDefinition = getJpaPlatform().getSpecifiedJavaAttributeMappingDefinition(key);
		JavaAttributeMapping mapping = mappingDefinition.buildMapping(this, getJpaFactory());
		//no mapping.initialize(JavaResourcePersistentAttribute) call here
		//we do not yet have a mapping annotation so we can't call initialize
		return mapping;
	}

	protected Iterable<String> getSupportingAnnotationNames() {
		JavaAttributeMapping mapping = this.getMapping();
		return (mapping != null) ? mapping.getSupportingAnnotationNames() : EmptyIterable.<String>instance();
	}

	protected void updateSpecifiedMapping() {
		// There will always be a mapping definition, even if it is a "null" mapping provider ...
		JavaAttributeMappingDefinition mappingDefinition = 
				getJpaPlatform().getSpecifiedJavaAttributeMappingDefinition(this);
		String mappingKey = mappingDefinition.getKey();
		if (this.specifiedMapping != null
				&& this.specifiedMapping.getKey().equals(mappingKey)) {
			this.specifiedMapping.update(this.resourcePersistentAttribute.
					getAnnotation(mappingDefinition.getAnnotationName()));
		} 
		else {
			setSpecifiedMapping(buildSpecifiedMapping(mappingDefinition));
		}
	}


	// ********** misc overrides **********

	@Override
	public PersistentType getParent() {
		return (PersistentType) super.getParent();
	}

	@Override
	public void toString(StringBuilder sb) {
		super.toString(sb);
		sb.append(this.name);
	}


	// ********** validation **********

	public TextRange getValidationTextRange(CompilationUnit astRoot) {
		return this.getSelectionTextRange(astRoot);
	}

	@Override
	public void validate(List<IMessage> messages, IReporter reporter, CompilationUnit astRoot) {
		super.validate(messages, reporter, astRoot);

		this.validateModifiers(messages, astRoot);

		if (this.specifiedMapping != null) {
			this.specifiedMapping.validate(messages, reporter, astRoot);
		}
		else if (this.defaultMapping != null) {
			this.defaultMapping.validate(messages, reporter, astRoot);
		}
	}


	protected void validateModifiers(List<IMessage> messages, CompilationUnit astRoot) {
		if (this.getMappingKey() == MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY) {
			return;
		}

		if (this.isField()) {
			if (this.isFinal()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_FINAL_FIELD, astRoot));
			}
			if (this.isPublic()) {
				messages.add(this.buildAttributeMessage(JpaValidationMessages.PERSISTENT_ATTRIBUTE_PUBLIC_FIELD, astRoot));
			}
		}
	}

	protected IMessage buildAttributeMessage(String msgID, CompilationUnit astRoot) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				new String[] {getName()},
				this,
				this.getValidationTextRange(astRoot)
			);
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		return this.getMapping().javaCompletionProposals(pos, filter, astRoot);
	}

	// ********** metamodel **********

	public String getMetamodelContainerFieldTypeName() {
		return this.getJpaContainer().getMetamodelContainerFieldTypeName();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		return this.getJpaContainer().getMetamodelContainerFieldMapKeyTypeName((CollectionMapping) this.getMapping());
	}

	public String getMetamodelTypeName() {
		String typeName = getTypeName();
		if (typeName == null) {
			return MetamodelField.DEFAULT_TYPE_NAME;
		}
		if (ClassName.isPrimitive(typeName)) {
			return ClassName.getWrapperClassName(typeName);  // ???
		}
		return typeName;
	}
	
	
	// ********** JPA containers **********

	public JpaContainer getJpaContainer() {
		// 'typeName' may include array brackets ("[]")
		// but not generic type arguments (e.g. "<java.lang.String>")
		return getJpaContainer(this.resourcePersistentAttribute.getTypeName());
	}
	/**
	 * Return the JPA container corresponding to the specified type;
	 * return a "null" JPA container if the specified type is not one of the
	 * container types allowed by the JPA spec.
	 */
	protected JpaContainer getJpaContainer(String typeName) {
		for (JpaContainer jpaContainer : JPA_CONTAINERS) {
			if (jpaContainer.getTypeName().equals(typeName)) {
				return jpaContainer;
			}
		}
		return JpaContainer.Null.instance();
	}

	protected static final Iterable<JpaContainer> JPA_CONTAINERS =
		new ArrayIterable<JpaContainer>(new JpaContainer[] {
			new CollectionJpaContainer(java.util.Collection.class, JPA2_0.COLLECTION_ATTRIBUTE),
			new CollectionJpaContainer(java.util.Set.class, JPA2_0.SET_ATTRIBUTE),
			new CollectionJpaContainer(java.util.List.class, JPA2_0.LIST_ATTRIBUTE),
			new MapJpaContainer(java.util.Map.class, JPA2_0.MAP_ATTRIBUTE)
		});


	/**
	 * Abstract JPA container
	 */
	protected abstract static class AbstractJpaContainer implements JpaContainer {
		protected final String typeName;
		protected final String metamodelContainerFieldTypeName;

		protected AbstractJpaContainer(Class<?> containerClass, String metamodelContainerFieldTypeName) {
			this(containerClass.getName(), metamodelContainerFieldTypeName);
		}

		protected AbstractJpaContainer(String typeName, String metamodelContainerFieldTypeName) {
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
	 * Collection JPA container
	 */
	protected static class CollectionJpaContainer extends AbstractJpaContainer {
		protected CollectionJpaContainer(Class<?> collectionClass, String staticMetamodelTypeDeclarationTypeName) {
			super(collectionClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceTargetTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 1) ?
						resourcePersistentAttribute.getTypeTypeArgumentName(0) :
						null;
		}

		public String getMultiReferenceMapKeyTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
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
	 * Map JPA container
	 */
	protected static class MapJpaContainer extends AbstractJpaContainer {
		protected MapJpaContainer(Class<?> mapClass, String staticMetamodelTypeDeclarationTypeName) {
			super(mapClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceTargetTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 2) ?
						resourcePersistentAttribute.getTypeTypeArgumentName(1) :
						null;
		}

		public String getMultiReferenceMapKeyTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 2) ?
				resourcePersistentAttribute.getTypeTypeArgumentName(0) :
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
