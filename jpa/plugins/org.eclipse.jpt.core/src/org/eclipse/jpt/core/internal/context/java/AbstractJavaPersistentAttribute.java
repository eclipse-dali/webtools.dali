/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaStructureNodes;
import org.eclipse.jpt.core.internal.jpa2.context.SimpleStaticMetamodelField;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.StaticMetamodelField;
import org.eclipse.jpt.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
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

	public PersistentType getPersistentType() {
		return this.getParent();
	}

	public TypeMapping getTypeMapping() {
		return this.getPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
		return this.getMapping().getPrimaryKeyColumnName();
	}

	public boolean isIdAttribute() {
		return this.getMapping().isIdMapping();
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
		String typeName = this.resourcePersistentAttribute.getTypeName();
		return (typeName == null) ? null : this.getPersistenceUnit().getEmbeddable(typeName);
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
		String typeName = this.resourcePersistentAttribute.getTypeName();
		if (typeName == null) {
			return false;
		}

		int arrayDepth = ClassTools.arrayDepthForTypeDeclaration(typeName);
		if (arrayDepth > 1) {
			return false;  // multi-dimensional arrays are not supported
		}

		if (arrayDepth == 1) {
			String elementTypeName = ClassTools.elementTypeNameForTypeDeclaration(typeName, 1);
			return this.elementTypeIsValidForBasicArray(elementTypeName);
		}

		// arrayDepth == 0
		if (ClassTools.classNamedIsVariablePrimitive(typeName)) {
			return true;  // any primitive but 'void'
		}
		if (ClassTools.classNamedIsVariablePrimitiveWrapperClass(typeName)) {
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
	/**
	 * 'typeName' may include array brackets ("[]")
	 * but not generic type arguments (e.g. "<java.lang.String>")
	 */
	public String getSingleReferenceEntityTypeName() {
		String typeName = this.resourcePersistentAttribute.getTypeName();
		if (typeName == null) {
			return null;
		}
		if (ClassTools.arrayDepthForTypeDeclaration(typeName) != 0) {
			return null;  // arrays cannot be entities
		}
		if (this.typeIsContainer(typeName)) {
			return null;  // "containers" cannot be entities
		}
		return typeName;
	}

	public String getMultiReferenceEntityTypeName() {
		// 'typeName' may include array brackets but not generic type arguments
		String typeName = this.resourcePersistentAttribute.getTypeName();
		return (typeName == null) ? null :
				this.getJpaContainer(typeName).getMultiReferenceEntityTypeName(this.resourcePersistentAttribute);
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
				getNullAnnotation(mappingDefinition.getAnnotationName());
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
		// There will always be a mapping definition, even if it is a "null" mapping provider ...
		JavaAttributeMappingDefinition mappingDefinition = 
				getJpaPlatform().getDefaultJavaAttributeMappingDefinition(this);
		String mappingKey = mappingDefinition.getKey();
		if (this.valuesAreEqual(this.defaultMapping.getKey(), mappingKey)) {
			this.defaultMapping.update(this.resourcePersistentAttribute.
					getNullAnnotation(mappingDefinition.getAnnotationName()));
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
		if (key == this.getSpecifiedMappingKey()) {
			return;
		}
		JavaAttributeMapping oldMapping = this.specifiedMapping;
		JavaAttributeMapping newMapping = this.buildMappingFromMappingKey(key);
		
		this.specifiedMapping = newMapping;
		
		String newAnnotation;
		String[] newSupportingAnnotationNames;
		if (newMapping != null) {
			newAnnotation = newMapping.getAnnotationName();
			newSupportingAnnotationNames = 
					ArrayTools.array(newMapping.supportingAnnotationNames(), new String[0]);
		}
		else {
			newAnnotation = null;
			newSupportingAnnotationNames = 
					ArrayTools.array(this.defaultMapping.supportingAnnotationNames(), new String[0]);
		}
		this.resourcePersistentAttribute.setPrimaryAnnotation(
				newAnnotation, newSupportingAnnotationNames);
		this.firePropertyChanged(SPECIFIED_MAPPING_PROPERTY, oldMapping, newMapping);
	}
	
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

	protected Iterator<String> supportingAnnotationNames() {
		JavaAttributeMapping mapping = this.getMapping();
		return (mapping != null) ? mapping.supportingAnnotationNames() : EmptyIterator.<String>instance();
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


	// ********** static metamodel **********

	public StaticMetamodelField getStaticMetamodelField() {
		return new SimpleStaticMetamodelField(
				this.getStaticMetamodelFieldModifiers(),
				this.getStaticMetamodelFieldTypeName(),
				this.getStaticMetamodelFieldTypeArgumentNames(),
				this.getStaticMetamodelFieldName()
			);
	}

	protected Iterable<String> getStaticMetamodelFieldModifiers() {
		return STANDARD_STATIC_METAMODEL_FIELD_MODIFIERS;
	}

	protected String getStaticMetamodelFieldTypeName() {
		String typeName = this.resourcePersistentAttribute.getTypeName();
		if (typeName == null) {
			return JPA2_0.SINGULAR_ATTRIBUTE;
		}
		return this.getJpaContainer(typeName).getStaticMetamodelFieldTypeName();
	}

	protected Iterable<String> getStaticMetamodelFieldTypeArgumentNames() {
		ArrayList<String> typeArgumentNames = new ArrayList<String>(3);
		typeArgumentNames.add(this.getPersistentType().getName());
		this.addStaticMetamodelFieldTypeArgumentNamesTo(typeArgumentNames);
		return typeArgumentNames;
	}

	protected void addStaticMetamodelFieldTypeArgumentNamesTo(ArrayList<String> typeArgumentNames){
		String typeName = this.resourcePersistentAttribute.getTypeName();
		if (typeName == null) {
			typeArgumentNames.add(OBJECT_CLASS_NAME);  // ???
			return;
		}
		if (ClassTools.classNamedIsPrimitive(typeName)) {
			typeArgumentNames.add(ClassTools.wrapperClassName(typeName));  // ???
			return;
		}
		JpaContainer jpaContainer = this.getJpaContainer(typeName);
		if (jpaContainer.isContainer()) {
			jpaContainer.addStaticMetamodelFieldTypeArgumentNamesTo(typeArgumentNames, this.resourcePersistentAttribute);
			return;
		}
		typeArgumentNames.add(typeName);
	}

	protected static final String OBJECT_CLASS_NAME = java.lang.Object.class.getName();

	protected String getStaticMetamodelFieldName() {
		return this.getName();
	}


	// ********** JPA containers **********

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
	 * JPA container interface (and null implementation)
	 */
	protected interface JpaContainer {
		String getTypeName();
		boolean isContainer();
		String getMultiReferenceEntityTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute);
		String getStaticMetamodelFieldTypeName();
		void addStaticMetamodelFieldTypeArgumentNamesTo(List<String> typeArgumentNames, JavaResourcePersistentAttribute resourcePersistentAttribute);

		final class Null implements JpaContainer {
			public static final JpaContainer INSTANCE = new Null();
			public static JpaContainer instance() {
				return INSTANCE;
			}
			// ensure single instance
			private Null() {
				super();
			}
			public String getTypeName() {
				return null;
			}
			public boolean isContainer() {
				return false;
			}
			public String getMultiReferenceEntityTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
				return null;
			}
			public String getStaticMetamodelFieldTypeName() {
				return JPA2_0.SINGULAR_ATTRIBUTE;
			}
			public void addStaticMetamodelFieldTypeArgumentNamesTo(List<String> typeArgumentNames, JavaResourcePersistentAttribute resourcePersistentAttribute) {
				throw new UnsupportedOperationException();
			}
			@Override
			public String toString() {
				return "JpaContainer.Null";  //$NON-NLS-1$
			}
		}

	}

	/**
	 * Abstract JPA container
	 */
	protected abstract static class AbstractJpaContainer implements JpaContainer {
		protected final String typeName;
		protected final String staticMetamodelFieldTypeName;

		protected AbstractJpaContainer(Class<?> containerClass, String staticMetamodelTypeDeclarationTypeName) {
			this(containerClass.getName(), staticMetamodelTypeDeclarationTypeName);
		}

		protected AbstractJpaContainer(String typeName, String staticMetamodelFieldTypeName) {
			super();
			if ((typeName == null) || (staticMetamodelFieldTypeName == null)) {
				throw new NullPointerException();
			}
			this.typeName = typeName;
			this.staticMetamodelFieldTypeName = staticMetamodelFieldTypeName;
		}

		public String getTypeName() {
			return this.typeName;
		}

		public boolean isContainer() {
			return true;
		}

		public String getStaticMetamodelFieldTypeName() {
			return this.staticMetamodelFieldTypeName;
		}

		public void addStaticMetamodelFieldTypeArgumentNamesTo(List<String> typeArgumentNames, JavaResourcePersistentAttribute resourcePersistentAttribute) {
			String elementType = this.getMultiReferenceEntityTypeName(resourcePersistentAttribute);
			typeArgumentNames.add((elementType != null) ? elementType : OBJECT_CLASS_NAME);
		}

	}

	/**
	 * Collection JPA container
	 */
	protected static class CollectionJpaContainer extends AbstractJpaContainer {
		protected CollectionJpaContainer(Class<?> collectionClass, String staticMetamodelTypeDeclarationTypeName) {
			super(collectionClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceEntityTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 1) ?
						resourcePersistentAttribute.getTypeTypeArgumentName(0) :
						null;
		}

	}

	/**
	 * Map JPA container
	 */
	protected static class MapJpaContainer extends AbstractJpaContainer {
		protected MapJpaContainer(Class<?> mapClass, String staticMetamodelTypeDeclarationTypeName) {
			super(mapClass, staticMetamodelTypeDeclarationTypeName);
		}

		public String getMultiReferenceEntityTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 2) ?
						resourcePersistentAttribute.getTypeTypeArgumentName(1) :
						null;
		}

		private String getMultiKeyTypeName(JavaResourcePersistentAttribute resourcePersistentAttribute) {
			return (resourcePersistentAttribute.typeTypeArgumentNamesSize() == 2) ?
						resourcePersistentAttribute.getTypeTypeArgumentName(0) :
						null;
		}

		@Override
		public void addStaticMetamodelFieldTypeArgumentNamesTo(List<String> typeArgumentNames, JavaResourcePersistentAttribute resourcePersistentAttribute) {
			String keyType = this.getMultiKeyTypeName(resourcePersistentAttribute);
			typeArgumentNames.add((keyType != null) ? keyType : OBJECT_CLASS_NAME);
			super.addStaticMetamodelFieldTypeArgumentNamesTo(typeArgumentNames, resourcePersistentAttribute);
		}

	}

}
