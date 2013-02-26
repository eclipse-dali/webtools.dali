/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.Collection;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.TypeBinding;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmModifiablePersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.ModifiablePersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAccessHolder;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <em>specified</em> <code>orm.xml</code> persistent attribute
 */
public abstract class SpecifiedOrmPersistentAttribute
		extends AbstractOrmXmlContextModel<OrmPersistentType>
		implements OrmModifiablePersistentAttribute, ModifiablePersistentAttribute2_0 {
	
	protected OrmAttributeMapping mapping;  // never null

	/**
	 * This will point to one of the following:<ul>
	 * <li>an existing Java attribute (taken from the appropriate Java type)
	 * <li>{@link #cachedJavaPersistentAttribute} if there is no such Java
	 *     attribute (i.e. the Java type's access type is different or it is
	 *     inherited from a non-persistent superclass)
	 * <li><code>null</code> if there is no matching Java resource attribute
	 * </ul>
	 * @see #buildJavaPersistentAttribute()
	 */
	protected JavaModifiablePersistentAttribute javaPersistentAttribute;

	/**
	 * If present, this Java attribute's parent is the <code>orm.xml</code>
	 * type.
	 */
	protected JavaModifiablePersistentAttribute cachedJavaPersistentAttribute;

	protected AccessType specifiedAccess;
	protected AccessType defaultAccess;


	protected SpecifiedOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent);
		this.mapping = this.buildMapping(xmlMapping);
		this.specifiedAccess = this.buildSpecifiedAccess();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedAccess_(this.buildSpecifiedAccess());
		this.mapping.synchronizeWithResourceModel();
		if (this.cachedJavaPersistentAttribute != null) {
			this.cachedJavaPersistentAttribute.synchronizeWithResourceModel();
		}
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultAccess(this.buildDefaultAccess());
		this.setJavaPersistentAttribute(this.buildJavaPersistentAttribute());
		this.mapping.update();
		if (this.cachedJavaPersistentAttribute != null) {
			this.cachedJavaPersistentAttribute.update();
		}
	}

	public void gatherRootStructureNodes(JpaFile jpaFile, Collection<JpaStructureNode> rootStructureNodes) {
		throw new UnsupportedOperationException();
	}


	// ********** mapping **********

	public OrmAttributeMapping getMapping() {
		return this.mapping;
	}

	public String getMappingKey() {
		return this.mapping.getKey();
	}

	public OrmAttributeMapping setMappingKey(String mappingKey) {
		if (this.valuesAreDifferent(this.getMappingKey(), mappingKey)) {
			this.setMappingKey_(mappingKey);
		}
		return this.mapping;
	}

	protected void setMappingKey_(String mappingKey) {
		OrmAttributeMappingDefinition mappingDefinition = this.getMappingFileDefinition().getAttributeMappingDefinition(mappingKey);
		XmlAttributeMapping xmlAttributeMapping = mappingDefinition.buildResourceMapping(this.getResourceModelFactory());
		this.setMapping(this.buildMapping(xmlAttributeMapping));
	}

	protected final OrmAttributeMapping buildMapping(XmlAttributeMapping xmlAttributeMapping) {
		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(xmlAttributeMapping.getMappingKey());
		return md.buildContextMapping(this, xmlAttributeMapping, this.getContextModelFactory());
	}

	protected void setMapping(OrmAttributeMapping mapping) {
		OrmAttributeMapping old = this.mapping;
		this.mapping = mapping;
		this.firePropertyChanged(MAPPING_PROPERTY, old, mapping);
		this.getOwningPersistentType().changeMapping(this, old, mapping);
	}

	/**
	 * <code>orm.xml</code> attributes do not have a default mapping;
	 * they are always specified.
	 */
	public String getDefaultMappingKey() {
		return null;
	}

	protected XmlAttributeMapping getXmlAttributeMapping() {
		return this.mapping.getXmlAttributeMapping();
	}


	// ********** name **********

	public String getName() {
		return this.mapping.getName();
	}

	public void nameChanged(String oldName, String newName) {
		this.firePropertyChanged(NAME_PROPERTY, oldName, newName);
	}


	// ********** Java persistent attribute **********

	public JavaModifiablePersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}

	public JavaModifiablePersistentAttribute resolveJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return (this.javaPersistentAttribute == null) ? null : this.javaPersistentAttribute.getResourceAttribute();
	}

	public boolean isFor(JavaResourceField javaResourceField) {
		return (this.javaPersistentAttribute != null) && this.javaPersistentAttribute.isFor(javaResourceField);
	}

	public boolean isFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return (this.javaPersistentAttribute != null) && this.javaPersistentAttribute.isFor(javaResourceGetter, javaResourceSetter);
	}

	protected void setJavaPersistentAttribute(JavaModifiablePersistentAttribute javaPersistentAttribute) {
		JavaModifiablePersistentAttribute old = this.javaPersistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, old, javaPersistentAttribute);
	}

	protected JavaModifiablePersistentAttribute buildJavaPersistentAttribute() {
		String name = this.getName();
		if (name == null) {
			return null;
		}
		JavaPersistentType javaType = this.getOwningPersistentTypeJavaType();
		if (javaType == null) {
			return null;
		}

		ReadOnlyPersistentAttribute pAttribute = javaType.resolveAttribute(name);
		JavaModifiablePersistentAttribute javaAttribute = (pAttribute == null) ? null : pAttribute.getJavaPersistentAttribute();
		if ((javaAttribute != null) && (javaAttribute.getAccess() == this.getAccess())) {
			// we only want to cache the Java persistent attribute if we built it
			this.cachedJavaPersistentAttribute = null;
			return javaAttribute;
		}

		// If 'javaAttribute' is null, it might exist in a superclass that
		// is not persistent. In that case we need to build it ourselves.
		// If 'javaAttribute' access is different, 'javaType' will not hold
		// a corresponding Java persistent attribute. So, again, we need
		// to build it ourselves.
		return this.getCachedJavaAttribute();
	}

	protected JavaModifiablePersistentAttribute getCachedJavaAttribute() {
		JavaResourceType javaResourceType = this.getOwningPersistentTypeJavaType().getJavaResourceType();
		if (javaResourceType == null) {
			return null; 
		}
		if (this.getAccess() == AccessType.FIELD) {
			JavaResourceField javaResourceField = this.getJavaResourceField(javaResourceType);
			if (javaResourceField == null) {
				// nothing in the resource inheritance hierarchy matches our name *and* access type
				this.cachedJavaPersistentAttribute = null;
			} else {
				if ((this.cachedJavaPersistentAttribute == null) ||
						! this.cachedJavaPersistentAttribute.isFor(javaResourceField)) {
					// cache is stale
					this.cachedJavaPersistentAttribute = this.buildJavaPersistentField(javaResourceField);
				}
			}
		}
		else if (this.getAccess() == AccessType.PROPERTY) {
			JavaResourceMethod javaResourceGetter = this.getJavaResourceGetter(javaResourceType);
			JavaResourceMethod javaResourceSetter = (javaResourceGetter == null) ? null : AbstractJavaPersistentType.getValidSiblingSetMethod(javaResourceGetter, javaResourceType.getMethods());
			if ((javaResourceGetter == null) && (javaResourceSetter == null)) {
				// nothing in the resource inheritance hierarchy matches our name *and* access type
				this.cachedJavaPersistentAttribute = null;
			} else {
				if ((this.cachedJavaPersistentAttribute == null) ||
						! this.cachedJavaPersistentAttribute.isFor(javaResourceGetter, javaResourceSetter)) {
					// cache is stale
					this.cachedJavaPersistentAttribute = this.buildJavaPersistentProperty(javaResourceGetter, javaResourceSetter);
				}
			}
		}
		return this.cachedJavaPersistentAttribute;
	}

	/**
	 * Search the specified Java resource type for the resource attribute
	 * corresponding to this <code>orm.xml</code> attribute (i.e. the Java
	 * resource attribute with the same name). If the specified Java resource
	 * type does not have a corresponding attribute, search up its inheritance
	 * hierarchy.
	 */
	protected JavaResourceField getJavaResourceField(JavaResourceType javaResourceType) {
		if (javaResourceType == null) {//checking null here, had a VIRTUAL hierarchy, change AbstractModel to FIELD access, is there another way??
			return null;
		}
		for (JavaResourceField javaResourceField : this.getJavaResourceFields(javaResourceType)) {
			if (javaResourceField.getName().equals(this.getName())) {
				return javaResourceField;
			}
		}
		// climb up inheritance hierarchy
		String superclassName = javaResourceType.getSuperclassQualifiedName();
		if (superclassName == null) {
			return null;
		}
		JavaResourceType superclass = (JavaResourceType) this.getJpaProject().getJavaResourceType(superclassName, AstNodeType.TYPE);
		if (superclass == null) {
			return null;
		}
		// recurse
		return this.getJavaResourceField(superclass);
	}

	/**
	 * Return the resource attributes with compatible access types.
	 */
	protected Iterable<JavaResourceField> getJavaResourceFields(JavaResourceType javaResourceType) {
		return javaResourceType.getFields();
	}

	/**
	 * Search the specified Java resource type for the resource attribute
	 * corresponding to this <code>orm.xml</code> attribute (i.e. the Java
	 * resource attribute with the same name). If the specified Java resource
	 * type does not have a corresponding attribute, search up its inheritance
	 * hierarchy.
	 */
	protected JavaResourceMethod getJavaResourceGetter(JavaResourceType javaResourceType) {
		for (JavaResourceMethod javaResourceGetter : this.getJavaResourceGetters(javaResourceType)) {
			if (javaResourceGetter.getName().equals(this.getName())) {
				return javaResourceGetter;
			}
		}
		// climb up inheritance hierarchy
		String superclassName = javaResourceType.getSuperclassQualifiedName();
		if (superclassName == null) {
			return null;
		}
		JavaResourceType superclass = (JavaResourceType) this.getJpaProject().getJavaResourceType(superclassName, AstNodeType.TYPE);
		if (superclass == null) {
			return null;
		}
		// recurse
		return this.getJavaResourceGetter(superclass);
	}

	protected Iterable<JavaResourceMethod> getResourceMethods(final JavaResourceType javaResourceType, Predicate<JavaResourceMethod> filter) {
		return IterableTools.filter(javaResourceType.getMethods(), filter);
	}

	protected Predicate<JavaResourceMethod> buildPersistablePropertyGetterMethodsFilter(final JavaResourceType javaResourceType) {
		return new Predicate<JavaResourceMethod>() {
			public boolean evaluate(JavaResourceMethod resourceMethod) {
				return AbstractJavaPersistentType.methodIsPersistablePropertyGetter(resourceMethod, javaResourceType.getMethods());
			}
		};
	}

	/**
	 * Return the resource attributes with compatible access types.
	 */
	protected Iterable<JavaResourceMethod> getJavaResourceGetters(JavaResourceType javaResourceType) {
		return this.getResourceMethods(javaResourceType, this.buildPersistablePropertyGetterMethodsFilter(javaResourceType));
	}

	protected JavaModifiablePersistentAttribute buildJavaPersistentField(JavaResourceField javaResourceField) {
		// pass in our parent orm persistent type as the parent to the cached Java attribute...
		return this.getJpaFactory().buildJavaPersistentField(this.getOwningPersistentType(), javaResourceField);
	}

	protected JavaModifiablePersistentAttribute buildJavaPersistentProperty(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		// pass in our parent orm persistent type as the parent to the cached Java attribute...
		return this.getJpaFactory().buildJavaPersistentProperty(this.getOwningPersistentType(), javaResourceGetter, javaResourceSetter);
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
		AccessType oldAccess = this.getAccess();
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = access;
		this.firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, access);
		if (oldAccess != this.getAccess()) {
			//clear out the cached 'javaAttribute' is the access has changed, it will no longer apply
			if (this.cachedJavaPersistentAttribute != null) {
				this.setJavaPersistentAttribute(null);
				this.cachedJavaPersistentAttribute = null;
			}
		}
	}

	protected AccessType buildDefaultAccess() {
		return this.getOwningPersistentType().getAccess();
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType access) {
		this.setSpecifiedAccess_(access);
		this.getXmlAccessHolder().setAccess(AccessType.toOrmResourceModel(access));
	}

	protected void setSpecifiedAccess_(AccessType access) {
		AccessType oldAccess = this.getAccess();
		AccessType oldSpecifiedAccess = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, access);
		if (oldAccess != this.getAccess()) {
			//clear out the cached 'javaAttribute' is the access has changed, it will no longer apply
			if (this.cachedJavaPersistentAttribute != null) {
				this.setJavaPersistentAttribute(null);
				this.cachedJavaPersistentAttribute = null;
			}
		}
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getXmlAccessHolder().getAccess(), this.getJpaPlatform(), this.getResourceType());
	}

	protected XmlAccessHolder getXmlAccessHolder() {
		return this.getXmlAttributeMapping();
	}


	// ********** specified/default **********

	public boolean isVirtual() {
		return false;
	}

	public OrmReadOnlyPersistentAttribute removeFromXml() {
		return this.getOwningPersistentType().removeAttributeFromXml(this);
	}

	public OrmModifiablePersistentAttribute addToXml() {
		throw new UnsupportedOperationException();
	}

	public OrmModifiablePersistentAttribute addToXml(String mappingKey) {
		throw new UnsupportedOperationException();
	}


	// ********** JpaStructureNode implementation **********

	public ContextType getContextType() {
		return new ContextType(this);
	}

	public Class<OrmModifiablePersistentAttribute> getType() {
		return OrmModifiablePersistentAttribute.class;
	}

	public Iterable<JpaStructureNode> getChildren() {
		return EmptyIterable.instance();
	}

	public int getChildrenSize() {
		return 0;
	}

	public JpaStructureNode getStructureNode(int offset) {
		return this;
	}

	public TextRange getFullTextRange() {
		return this.getXmlAttributeMapping().getFullTextRange();
	}

	public boolean containsOffset(int textOffset) {
		return this.getXmlAttributeMapping().containsOffset(textOffset);
	}

	public TextRange getSelectionTextRange() {
		return this.mapping.getSelectionTextRange();
	}

	public void dispose() {
		// nothing to dispose
	}


	// ********** refactoring **********

	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.mapping.createRenameTypeEdits(originalType, newName);
	}

	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.mapping.createMoveTypeEdits(originalType, newPackage);
	}

	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.mapping.createRenamePackageEdits(originalPackage, newName);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.validateAttribute(messages, reporter);
		this.mapping.validate(messages, reporter);
	}

	protected void validateAttribute(List<IMessage> messages, IReporter reporter) {
		if (this.javaPersistentAttribute == null) {
			this.validateUnresolvedAttribute(messages);
		} else {
			this.buildAttibuteValidator().validate(messages, reporter);
		}
	}

	protected void validateUnresolvedAttribute(List<IMessage> messages) {
		String name = this.getName();
		if (StringTools.isBlank(name)) {
			// if the name null, there will already be an XSD-driven error;
			// if the name is empty or whitespace, there will already be an attribute mapping error
			return;
		}
		JavaPersistentType javaType = this.getOwningPersistentTypeJavaType();
		if (javaType == null) {
			// it's not very helpful to point out that we cannot resolve an attribute
			// of an unresolved type (which already has its own error message)
			return;
		}
		messages.add(
			this.buildValidationMessage(
				this.mapping,
				this.mapping.getNameTextRange(),
				JptJpaCoreValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
				name,
				javaType.getName()
			)
		);
	}

	protected abstract JptValidator buildAttibuteValidator();

	public TextRange getValidationTextRange() {
		return this.mapping.getValidationTextRange();
	}


	// ********** metamodel **********

	public String getMetamodelContainerFieldTypeName() {
		return this.getJpaContainerDefinition().getMetamodelContainerFieldTypeName();
	}

	public String getMetamodelContainerFieldMapKeyTypeName() {
		return this.getJpaContainerDefinition().getMetamodelContainerFieldMapKeyTypeName((CollectionMapping) this.mapping);
	}

	public String getMetamodelTypeName() {
		ModifiablePersistentAttribute2_0 javaAttribute = (ModifiablePersistentAttribute2_0) this.javaPersistentAttribute;
		return (javaAttribute != null) ?
				javaAttribute.getMetamodelTypeName() :
				MetamodelField.DEFAULT_TYPE_NAME;
	}

	protected JavaModifiablePersistentAttribute.JpaContainerDefinition getJpaContainerDefinition() {
		return (this.javaPersistentAttribute != null) ?
				this.javaPersistentAttribute.getJpaContainerDefinition() :
				JavaModifiablePersistentAttribute.JpaContainerDefinition.Null.instance();
	}

	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.mapping.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}

	// ********** misc **********

	public OrmPersistentType getOwningPersistentType() {
		return this.parent;
	}

	protected JavaPersistentType getOwningPersistentTypeJavaType() {
		return this.getOwningPersistentType().getJavaPersistentType();
	}

	public OrmTypeMapping getOwningTypeMapping() {
		return this.getOwningPersistentType().getMapping();
	}

	public String getPrimaryKeyColumnName() {
		return this.mapping.getPrimaryKeyColumnName();
	}

	public String getTypeName() {
		return this.mapping.getFullyQualifiedAttributeType();
	}
	
	public String getTypeName(PersistentType contextType) {
		while (contextType != null) {
			if (contextType == this.getOwningPersistentType()) {
				return this.getTypeName();
			}
			TypeBinding typeBinding = contextType.getAttributeTypeBinding(this);
			if (typeBinding != null) {
				return typeBinding.getQualifiedName();
			}
			contextType = contextType.getSuperPersistentType();
		}
		return null;
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
