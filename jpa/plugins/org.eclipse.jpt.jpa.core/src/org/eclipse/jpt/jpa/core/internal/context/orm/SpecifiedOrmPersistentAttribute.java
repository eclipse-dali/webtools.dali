/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmStructureNodes;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.PersistentAttributeTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaPersistentType;
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAccessHolder;
import org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <em>specified</em> <code>orm.xml</code> persistent attribute
 */
public abstract class SpecifiedOrmPersistentAttribute
	extends AbstractOrmXmlContextNode
	implements OrmPersistentAttribute2_0
{
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
	protected JavaPersistentAttribute javaPersistentAttribute;

	/**
	 * If present, this Java attribute's parent is the <code>orm.xml</code>
	 * type.
	 */
	protected JavaPersistentAttribute cachedJavaPersistentAttribute;

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
		XmlAttributeMapping xmlAttributeMapping = mappingDefinition.buildResourceMapping(this.getResourceNodeFactory());
		this.setMapping(this.buildMapping(xmlAttributeMapping));
	}

	protected final OrmAttributeMapping buildMapping(XmlAttributeMapping xmlAttributeMapping) {
		OrmAttributeMappingDefinition md = this.getMappingFileDefinition().getAttributeMappingDefinition(xmlAttributeMapping.getMappingKey());
		return md.buildContextMapping(this, xmlAttributeMapping, this.getContextNodeFactory());
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

	public JavaPersistentAttribute getJavaPersistentAttribute() {
		return this.javaPersistentAttribute;
	}

	public JavaPersistentAttribute resolveJavaPersistentAttribute() {
		return this.getJavaPersistentAttribute();
	}

	public JavaResourceAttribute getJavaResourceAttribute() {
		return this.javaPersistentAttribute == null ? null : this.javaPersistentAttribute.getResourceAttribute();
	}

	public boolean isFor(JavaResourceField javaResourceField) {
		return getJavaPersistentAttribute() == null ? false : getJavaPersistentAttribute().isFor(javaResourceField);
	}

	public boolean isFor(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		return getJavaPersistentAttribute() == null ? false : getJavaPersistentAttribute().isFor(javaResourceGetter, javaResourceSetter);
	}

	protected void setJavaPersistentAttribute(JavaPersistentAttribute javaPersistentAttribute) {
		JavaPersistentAttribute old = this.javaPersistentAttribute;
		this.javaPersistentAttribute = javaPersistentAttribute;
		this.firePropertyChanged(JAVA_PERSISTENT_ATTRIBUTE_PROPERTY, old, javaPersistentAttribute);
	}

	protected JavaPersistentAttribute buildJavaPersistentAttribute() {
		String name = this.getName();
		if (name == null) {
			return null;
		}
		JavaPersistentType javaType = this.getOwningPersistentTypeJavaType();
		if (javaType == null) {
			return null;
		}

		ReadOnlyPersistentAttribute pAttribute = javaType.resolveAttribute(name);
		JavaPersistentAttribute javaAttribute = (pAttribute == null) ? null : pAttribute.getJavaPersistentAttribute();
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

	protected JavaPersistentAttribute getCachedJavaAttribute() {
		JavaResourceType javaResourceType = this.getOwningPersistentTypeJavaType().getJavaResourceType();
		if (getAccess() == AccessType.FIELD) {
			JavaResourceField javaResourceField = this.getJavaResourceField(javaResourceType);
			if (javaResourceField == null) {
				// nothing in the resource inheritance hierarchy matches our name *and* access type
				this.cachedJavaPersistentAttribute = null;
			} else {
				if ((this.cachedJavaPersistentAttribute == null) ||
						!(this.cachedJavaPersistentAttribute.isFor(javaResourceField))) {
					// cache is stale
					this.cachedJavaPersistentAttribute = this.buildJavaPersistentField(javaResourceField);
				}
			}
		}
		if (getAccess() == AccessType.PROPERTY) {
			JavaResourceMethod javaResourceGetter = this.getJavaResourceGetter(javaResourceType);
			JavaResourceMethod javaResourceSetter = AbstractJavaPersistentType.getValidSiblingSetMethod(javaResourceGetter, javaResourceType.getMethods());
			if (javaResourceGetter == null && javaResourceSetter == null) {
				// nothing in the resource inheritance hierarchy matches our name *and* access type
				this.cachedJavaPersistentAttribute = null;
			} else {
				if ((this.cachedJavaPersistentAttribute == null) ||
						!(this.cachedJavaPersistentAttribute.isFor(javaResourceGetter, javaResourceSetter))) {
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
		JavaResourceType superclass = (JavaResourceType) this.getJpaProject().getJavaResourceType(superclassName, Kind.TYPE);
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
		JavaResourceType superclass = (JavaResourceType) this.getJpaProject().getJavaResourceType(superclassName, Kind.TYPE);
		if (superclass == null) {
			return null;
		}
		// recurse
		return this.getJavaResourceGetter(superclass);
	}

	protected Iterable<JavaResourceMethod> getResourceMethods(final JavaResourceType javaResourceType, Filter<JavaResourceMethod> filter) {
		return new FilteringIterable<JavaResourceMethod>(javaResourceType.getMethods(), filter);
	}

	protected Filter<JavaResourceMethod> buildPersistablePropertyGetterMethodsFilter(final JavaResourceType javaResourceType) {
		return new Filter<JavaResourceMethod>() {
			public boolean accept(JavaResourceMethod resourceMethod) {
				return AbstractJavaPersistentType.methodIsPersistablePropertyGetter(resourceMethod, javaResourceType.getMethods());
			}
		};
	}

	/**
	 * Return the resource attributes with compatible access types.
	 */
	protected Iterable<JavaResourceMethod> getJavaResourceGetters(JavaResourceType javaResourceType) {
		return getResourceMethods(javaResourceType, buildPersistablePropertyGetterMethodsFilter(javaResourceType));
	}

	protected JavaPersistentAttribute buildJavaPersistentField(JavaResourceField javaResourceField) {
		// pass in our parent orm persistent type as the parent to the cached Java attribute...
		return this.getJpaFactory().buildJavaPersistentField(this.getOwningPersistentType(), javaResourceField);
	}

	protected JavaPersistentAttribute buildJavaPersistentProperty(JavaResourceMethod javaResourceGetter, JavaResourceMethod javaResourceSetter) {
		// pass in our parent orm persistent type as the parent to the cached Java attribute...
		return this.getJpaFactory().buildJavaPersistentProperty(this.getOwningPersistentType(), javaResourceGetter, javaResourceSetter);
	}


	// ********** access **********

	public AccessType getAccess() {
		AccessType specifiedAccess = this.getSpecifiedAccess();
		return (specifiedAccess != null) ? specifiedAccess : this.defaultAccess;
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
		AccessType old = this.specifiedAccess;
		this.specifiedAccess = access;
		this.firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, old, access);
	}

	protected AccessType buildSpecifiedAccess() {
		return AccessType.fromOrmResourceModel(this.getXmlAccessHolder().getAccess());
	}

	protected XmlAccessHolder getXmlAccessHolder() {
		return this.getXmlAttributeMapping();
	}


	// ********** specified/default **********

	public boolean isVirtual() {
		return false;
	}

	public OrmReadOnlyPersistentAttribute convertToVirtual() {
		return this.getOwningPersistentType().convertAttributeToVirtual(this);
	}

	public OrmPersistentAttribute convertToSpecified() {
		throw new UnsupportedOperationException();
	}

	public OrmPersistentAttribute convertToSpecified(String mappingKey) {
		throw new UnsupportedOperationException();
	}


	// ********** JpaStructureNode implementation **********

	public String getId() {
		return OrmStructureNodes.PERSISTENT_ATTRIBUTE_ID;
	}

	public JpaStructureNode getStructureNode(int offset) {
		return this;
	}

	public boolean contains(int textOffset) {
		return this.mapping.contains(textOffset);
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
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_ATTRIBUTE_UNRESOLVED_NAME,
					new String[] {
						this.getName(),
						this.getOwningTypeMapping().getClass_()
					},
					this.mapping,
					this.mapping.getNameTextRange()
				)
			);
		} else {
			this.buildAttibuteValidator().validate(messages, reporter);
		}
	}

	protected PersistentAttributeTextRangeResolver buildTextRangeResolver() {
		return new OrmPersistentAttributeTextRangeResolver(this);
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
		JavaPersistentAttribute2_0 javaAttribute = (JavaPersistentAttribute2_0) this.javaPersistentAttribute;
		return (javaAttribute != null) ?
				javaAttribute.getMetamodelTypeName() :
				MetamodelField.DEFAULT_TYPE_NAME;
	}

	protected JavaPersistentAttribute.JpaContainerDefinition getJpaContainerDefinition() {
		JavaPersistentAttribute2_0 javaAttribute = (JavaPersistentAttribute2_0) this.javaPersistentAttribute;
		return (javaAttribute != null) ?
				javaAttribute.getJpaContainerDefinition() :
				JavaPersistentAttribute.JpaContainerDefinition.Null.instance();
	}


	// ********** misc **********

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getOwningPersistentType() {
		return this.getParent();
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
		return (this.javaPersistentAttribute == null) ? null : this.javaPersistentAttribute.getTypeName();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getName());
	}
}
