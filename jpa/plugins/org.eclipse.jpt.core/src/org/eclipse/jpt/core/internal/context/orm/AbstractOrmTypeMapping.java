/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.AbstractXmlContextNode;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractOrmTypeMapping<T extends AbstractXmlTypeMapping>
	extends AbstractXmlContextNode 
	implements OrmTypeMapping
{
	protected String class_;
	
	public AccessType defaultAccess;
	
	protected AccessType specifiedAccess;
	
	public boolean defaultMetadataComplete;
	
	protected Boolean specifiedMetadataComplete;
	
	protected JavaPersistentType javaPersistentType;
	
	protected T resourceTypeMapping;
	
	
	protected AbstractOrmTypeMapping(OrmPersistentType parent) {
		super(parent);
	}
	
	
	// **************** PersistentType.Owner implementation *****************************
	
	public AccessType getOverridePersistentTypeAccess() {
		if (this.specifiedAccess != null) {
			return this.specifiedAccess;
		}

		PersistentType ppt = this.getPersistentType().getParentPersistentType();
		if (ppt instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) ppt).getMapping().getSpecifiedAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		if (this.isMetadataComplete()) {
			AccessType accessType = this.getPersistentType().getOwnerDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		// no override access type
		return null;
	}
	
	public AccessType getDefaultPersistentTypeAccess() {
		PersistentType ppt = this.getPersistentType().getParentPersistentType();
		if (ppt instanceof OrmPersistentType) {
			AccessType accessType = ((OrmPersistentType) ppt).getMapping().getDefaultAccess();
			if (accessType != null) {
				return accessType;
			}
		}

		return this.getPersistentType().getOwnerDefaultAccess();
	}
	
	
	// **************** Type Mapping implementation *****************************
	
	public boolean isMapped() {
		return true;
	}

	public String getPrimaryTableName() {
		return null;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String newClass) {
		String oldClass = this.class_;
		this.class_ = newClass;
		this.resourceTypeMapping.setClassName(newClass);
		firePropertyChanged(CLASS_PROPERTY, oldClass, newClass);
		getPersistentType().classChanged(oldClass, newClass);
	}
	
	public AccessType getDefaultAccess() {
		return this.defaultAccess;
	}

	protected void setDefaultAccess(AccessType newDefaultAccess) {
		AccessType oldDefaultAccess = this.defaultAccess;
		this.defaultAccess = newDefaultAccess;
		firePropertyChanged(DEFAULT_ACCESS_PROPERTY, oldDefaultAccess, newDefaultAccess);
	}

	public AccessType getSpecifiedAccess() {
		return this.specifiedAccess;
	}

	public void setSpecifiedAccess(AccessType newSpecifiedAccess) {
		AccessType oldSpecifiedAccess = this.specifiedAccess;
		this.specifiedAccess = newSpecifiedAccess;
		this.resourceTypeMapping.setAccess(AccessType.toXmlResourceModel(newSpecifiedAccess));
		firePropertyChanged(SPECIFIED_ACCESS_PROPERTY, oldSpecifiedAccess, newSpecifiedAccess);
	}

	public AccessType getAccess() {
		return (this.getSpecifiedAccess() == null) ? this.getDefaultAccess() : this.getSpecifiedAccess();
	}

	public boolean isMetadataComplete() {
		if (isDefaultMetadataComplete()) {
			//entity-mappings/persistence-unit-metadata/xml-mapping-metadata-complete is specified, then it overrides
			//anything set here
			return true;
		}
		return (this.getSpecifiedMetadataComplete() == null) ? this.isDefaultMetadataComplete() : this.getSpecifiedMetadataComplete().booleanValue();
	}

	public boolean isDefaultMetadataComplete() {
		return this.defaultMetadataComplete;
	}
	
	protected void setDefaultMetadataComplete(boolean newDefaultMetadataComplete) {
		boolean oldMetadataComplete = this.defaultMetadataComplete;
		this.defaultMetadataComplete = newDefaultMetadataComplete;
		firePropertyChanged(DEFAULT_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newDefaultMetadataComplete);
	}
	
	public Boolean getSpecifiedMetadataComplete() {
		return this.specifiedMetadataComplete;
	}
	
	public void setSpecifiedMetadataComplete(Boolean newSpecifiedMetadataComplete) {
		Boolean oldMetadataComplete = this.specifiedMetadataComplete;
		this.specifiedMetadataComplete = newSpecifiedMetadataComplete;
		this.resourceTypeMapping.setMetadataComplete(newSpecifiedMetadataComplete);
		firePropertyChanged(SPECIFIED_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newSpecifiedMetadataComplete);
	}

	@Override
	public OrmPersistentType getParent() {
		return (OrmPersistentType) super.getParent();
	}

	public OrmPersistentType getPersistentType() {
		return this.getParent();
	}

	/**
	 * ITypeMapping is changed and various ITypeMappings may have
	 * common settings.  In this method initialize the new ITypeMapping (this)
	 * fromthe old ITypeMapping (oldMapping)
	 */
	public void initializeFrom(OrmTypeMapping oldMapping) {
		this.setClass(oldMapping.getClass_());
		this.setSpecifiedAccess(oldMapping.getSpecifiedAccess());
		this.setSpecifiedMetadataComplete(oldMapping.getSpecifiedMetadataComplete());
		this.setDefaultAccess(oldMapping.getDefaultAccess());
		this.setDefaultMetadataComplete(oldMapping.isDefaultMetadataComplete());
	}
	
	public Table getPrimaryDbTable() {
		return null;
	}

	public Table getDbTable(String tableName) {
		return null;
	}

	public Schema getDbSchema() {
		return null;
	}

	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<OrmPersistentAttribute> overridableAttributes() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}
	
	public Iterator<PersistentAttribute> allOverridableAttributes() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<OrmPersistentAttribute> overridableAssociations() {
		return EmptyIterator.instance();
	}	
	
	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<PersistentAttribute> allOverridableAssociations() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public T getResourceTypeMapping() {
		return this.resourceTypeMapping;
	}

	protected AccessType defaultAccess() {
		if (! isMetadataComplete()) {
			if (getJavaPersistentType() != null) {
				if (getJavaPersistentType().hasAnyAttributeMappingAnnotations()) {
					return getJavaPersistentType().getAccess();
				}
				if (getPersistentType().getParentPersistentType() != null) {
					return getPersistentType().getParentPersistentType().getAccess();
				}
			}
		}
		return getMappingFileRoot().getAccess();
	}
	
	public JavaPersistentType getJavaPersistentType() {
		return this.javaPersistentType;
	}
	
	protected void setJavaPersistentType(JavaPersistentType newJavaPersistentType) {
		JavaPersistentType oldJavaPersistentType = this.javaPersistentType;
		this.javaPersistentType = newJavaPersistentType;
		firePropertyChanged(JAVA_PERSISTENT_TYPE_PROPERTY, oldJavaPersistentType, newJavaPersistentType);
	}
	
	protected void initializeJavaPersistentType() {
		JavaResourcePersistentType persistentTypeResource = getJavaResourcePersistentType();
		if (persistentTypeResource != null) {
			this.javaPersistentType = buildJavaPersistentType(persistentTypeResource);
		}	
	}

	protected JavaResourcePersistentType getJavaResourcePersistentType() {
		// try to resolve by only the locally specified name
		JavaResourcePersistentType jrpt = this.getJpaProject().getJavaResourcePersistentType(this.class_);
		if (jrpt != null) {
			return jrpt;
		}

		// try to resolve by prepending the global package name
		String packageName = this.getPersistentType().getDefaultPackage();
		return this.getJpaProject().getJavaResourcePersistentType(packageName + '.' + this.class_);
	}
	
	protected void updateJavaPersistentType() {
		JavaResourcePersistentType jrpt = getJavaResourcePersistentType();
		if (jrpt == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(jrpt);
			}
			else {
				setJavaPersistentType(buildJavaPersistentType(jrpt));
			}
		}		
	}
	
	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType resourcePersistentType) {
		return getJpaFactory().buildJavaPersistentType(this, resourcePersistentType);
	}

	public void initialize(T mapping) {
		this.resourceTypeMapping = mapping;
		this.class_ = mapping.getClassName();
		this.initializeJavaPersistentType();
		this.specifiedMetadataComplete = this.metadataComplete(mapping);
		this.defaultMetadataComplete = this.getPersistentType().isDefaultMetadataComplete();
		this.specifiedAccess = AccessType.fromXmlResourceModel(mapping.getAccess());
		this.defaultAccess = this.defaultAccess();
	}
	
	public void update(T mapping) {
		this.resourceTypeMapping = mapping;
		this.setClass(mapping.getClassName());
		this.updateJavaPersistentType();
		this.setSpecifiedMetadataComplete(this.metadataComplete(mapping));
		this.setDefaultMetadataComplete(this.getPersistentType().isDefaultMetadataComplete());
		this.setSpecifiedAccess(AccessType.fromXmlResourceModel(mapping.getAccess()));
		this.setDefaultAccess(this.defaultAccess());
	}
	
	protected Boolean metadataComplete(AbstractXmlTypeMapping mapping) {
		return mapping.getMetadataComplete();
	}

	public String getOrmType() {
		return this.getPersistentType().getOrmType();
	}

	
	// *************************************************************************
	
	public JpaStructureNode getStructureNode(int offset) {
		if (this.resourceTypeMapping.containsOffset(offset)) {
			return getPersistentType();
		}
		return null;
	}
	
	public TextRange getSelectionTextRange() {
		return this.resourceTypeMapping.getSelectionTextRange();
	}
	
	public TextRange getClassTextRange() {
		return this.resourceTypeMapping.getClassTextRange();
	}
	
	public TextRange getAttributesTextRange() {
		return this.resourceTypeMapping.getAttributesTextRange();
	}

	public boolean containsOffset(int textOffset) {
		if (this.resourceTypeMapping == null) {
			return false;
		}
		return this.resourceTypeMapping.containsOffset(textOffset);
	}
	
	//************************* validation ************************
	@Override
	public void validate(List<IMessage> messages) {
		super.validate(messages);
		this.validateClass(messages);
	}

	protected void validateClass(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(this.class_)) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS,
					this, 
					this.getClassTextRange()
				)
			);
			return;
		}

		if (this.javaPersistentType == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,
					new String[] {this.class_},
					this, 
					this.getClassTextRange()
				)
			);
		}
	}

	public TextRange getValidationTextRange() {
		return this.resourceTypeMapping.getValidationTextRange();
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getPersistentType().getName());
	}

}
