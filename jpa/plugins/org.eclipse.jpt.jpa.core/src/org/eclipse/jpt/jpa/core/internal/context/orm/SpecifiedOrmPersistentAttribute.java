/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
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
import org.eclipse.jpt.jpa.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmPersistentAttribute2_0;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
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
	 * <li>{@link #cachedJavaPersistentAttribute} if there is no such Java attribute
	 *     (i.e. the Java type's acces type is different)
	 * <li><code>null</code> if there is no matching Java resource attribute
	 * </ul>
	 * @see #buildJavaPersistentAttribute()
	 */
	protected JavaPersistentAttribute javaPersistentAttribute;

	protected JavaPersistentAttribute cachedJavaPersistentAttribute;

	protected AccessType defaultAccess;


	protected SpecifiedOrmPersistentAttribute(OrmPersistentType parent, XmlAttributeMapping xmlMapping) {
		super(parent);
		this.mapping = this.buildMapping(xmlMapping);
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
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
		JavaPersistentType javaType = this.getOwningJavaPersistentType();
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
		JavaResourcePersistentType javaResourceType = this.getOwningJavaPersistentType().getResourcePersistentType();
		JavaResourcePersistentAttribute javaResourceAttribute = this.getJavaResourceAttribute(javaResourceType);
		if (javaResourceAttribute == null) {
			// nothing in the resource inheritance hierarchy matches our name *and* access type
			this.cachedJavaPersistentAttribute = null;
		} else {
			if ((this.cachedJavaPersistentAttribute == null) ||
					(this.cachedJavaPersistentAttribute.getResourcePersistentAttribute() != javaResourceAttribute)) {
				// cache is stale
				this.cachedJavaPersistentAttribute = this.buildJavaPersistentAttribute(javaResourceAttribute);
			}
		}
		return this.cachedJavaPersistentAttribute;
	}

	protected JavaResourcePersistentAttribute getJavaResourceAttribute(JavaResourcePersistentType javaResourceType) {
		for (JavaResourcePersistentAttribute javaResourceAttribute : this.getJavaResourceAttributes(javaResourceType)) {
			if (javaResourceAttribute.getName().equals(this.getName())) {
				return javaResourceAttribute;
			}
		}
		// climb up inheritance hierarchy
		String superclassName = javaResourceType.getSuperclassQualifiedName();
		if (superclassName == null) {
			return null;
		}
		JavaResourcePersistentType superclass = this.getJpaProject().getJavaResourcePersistentType(superclassName);
		if (superclass == null) {
			return null;
		}
		// recurse
		return this.getJavaResourceAttribute(superclass);
	}

	/**
	 * Return the resource attributes with compatible access types.
	 */
	protected Iterable<JavaResourcePersistentAttribute> getJavaResourceAttributes(JavaResourcePersistentType javaResourceType) {
		return CollectionTools.iterable(javaResourceType.persistableAttributes(this.getAccess().getJavaAccessType()));
	}

	protected JavaPersistentAttribute buildJavaPersistentAttribute(JavaResourcePersistentAttribute javaResourceAttribute) {
		// pass in our parent orm persistent type as the parent to the cached Java attribute...
		return this.getJpaFactory().buildJavaPersistentAttribute(this.getOwningPersistentType(), javaResourceAttribute);
	}

	public JavaResourcePersistentAttribute getJavaResourcePersistentAttribute() {
		return (this.javaPersistentAttribute == null) ? null : this.javaPersistentAttribute.getResourcePersistentAttribute();
	}


	// ********** access **********

	/**
	 * Subclasses determine the specified access.
	 */
	public AccessType getAccess() {
		AccessType specifiedAccess = this.getSpecifiedAccess();
		return (specifiedAccess != null) ? specifiedAccess : this.defaultAccess;
	}

	public abstract AccessType getSpecifiedAccess();

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


	// ********** specified/virtual **********

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

	protected JavaPersistentType getOwningJavaPersistentType() {
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
