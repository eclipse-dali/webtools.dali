/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public abstract class AbstractOrmTypeMapping<T extends AbstractTypeMapping> extends AbstractOrmJpaContextNode implements OrmTypeMapping
{

	protected String class_;
	
	public AccessType defaultAccess;

	protected AccessType specifiedAccess;

	public boolean defaultMetadataComplete;

	protected Boolean specifiedMetadataComplete;

	protected JavaPersistentType javaPersistentType;

	protected T typeMapping;
	
	protected AbstractOrmTypeMapping(OrmPersistentType parent) {
		super(parent);
	}

	public boolean isMapped() {
		return true;
	}

	public String tableName() {
		return null;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass(String newClass) {
		String oldClass = this.class_;
		this.class_ = newClass;
		this.typeMappingResource().setClassName(newClass);
		firePropertyChanged(CLASS_PROPERTY, oldClass, newClass);
		persistentType().classChanged(oldClass, newClass);
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
		this.typeMappingResource().setAccess(AccessType.toXmlResourceModel(newSpecifiedAccess));
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
		return (this.getSpecifiedMetadataComplete() == null) ? this.isDefaultMetadataComplete() : this.getSpecifiedMetadataComplete();
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
		this.typeMappingResource().setMetadataComplete(newSpecifiedMetadataComplete);
		firePropertyChanged(SPECIFIED_METADATA_COMPLETE_PROPERTY, oldMetadataComplete, newSpecifiedMetadataComplete);
	}

	public OrmPersistentType persistentType() {
		return (OrmPersistentType) parent();
	}

	/**
	 * ITypeMapping is changed and various ITypeMappings may have
	 * common settings.  In this method initialize the new ITypeMapping (this)
	 * fromthe old ITypeMapping (oldMapping)
	 * @param oldMapping
	 */
	public void initializeFrom(OrmTypeMapping oldMapping) {
		this.setClass(oldMapping.getClass_());
		this.setSpecifiedAccess(oldMapping.getSpecifiedAccess());
		this.setSpecifiedMetadataComplete(oldMapping.getSpecifiedMetadataComplete());
		this.setDefaultAccess(oldMapping.getDefaultAccess());
		this.setDefaultMetadataComplete(oldMapping.isDefaultMetadataComplete());
	}
	
	public Table primaryDbTable() {
		return null;
	}

	public Table dbTable(String tableName) {
		return null;
	}

	public Schema dbSchema() {
		return null;
	}

	/**
	 * @see TypeMapping#attributeMappingKeyAllowed(String)
	 * 
	 * Default implementation:  override where needed
	 */
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return true;
	}

	public Iterator<String> overridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> overridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAssociationNames() {
		return EmptyIterator.instance();
	}

	public Iterator<String> allOverridableAttributeNames() {
		return EmptyIterator.instance();
	}

	public T typeMappingResource() {
		return this.typeMapping;
	}
	
	protected PersistenceUnitMetadata persistenceUnitMetadata() {
		return entityMappings().getPersistenceUnitMetadata();
	}

	protected boolean defaultMetadataComplete() {
		return persistenceUnitMetadata().isXmlMappingMetadataComplete();
	}

	protected AccessType defaultAccess() {
		if (!isMetadataComplete()) {
			if (getJavaPersistentType() != null) {
				if (getJavaPersistentType().hasAnyAttributeMappingAnnotations()) {
					return getJavaPersistentType().access();
				}
				if (persistentType().parentPersistentType() != null) {
					return persistentType().parentPersistentType().access();
				}
			}
		}
		return entityMappings().getAccess();
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
		JavaResourcePersistentType persistentTypeResource = jpaProject().javaPersistentTypeResource(getClass_());
		if (persistentTypeResource != null) {
			this.javaPersistentType = buildJavaPersistentType(persistentTypeResource);
		}	
	}

	protected void updateJavaPersistentType() {
		JavaResourcePersistentType persistentTypeResource = jpaProject().javaPersistentTypeResource(getClass_());
		if (persistentTypeResource == null) {
			setJavaPersistentType(null);
		}
		else { 
			if (getJavaPersistentType() != null) {
				getJavaPersistentType().update(persistentTypeResource);
			}
			else {
				setJavaPersistentType(buildJavaPersistentType(persistentTypeResource));
			}
		}		
	}
	
	protected JavaPersistentType buildJavaPersistentType(JavaResourcePersistentType resourcePersistentType) {
		return jpaFactory().buildJavaPersistentType(this, resourcePersistentType);
	}

	public void initialize(T typeMapping) {
		this.typeMapping = typeMapping;
		this.class_ = typeMapping.getClassName();
		this.initializeJavaPersistentType();
		this.specifiedMetadataComplete = this.metadataComplete(typeMapping);
		this.defaultMetadataComplete = this.defaultMetadataComplete();
		this.specifiedAccess = AccessType.fromXmlResourceModel(typeMapping.getAccess());
		this.defaultAccess = this.defaultAccess();
	}
	
	public void update(T typeMapping) {
		this.typeMapping = typeMapping;
		this.setClass(typeMapping.getClassName());
		this.updateJavaPersistentType();
		this.setSpecifiedMetadataComplete(this.metadataComplete(typeMapping));
		this.setDefaultMetadataComplete(this.defaultMetadataComplete());
		this.setSpecifiedAccess(AccessType.fromXmlResourceModel(typeMapping.getAccess()));
		this.setDefaultAccess(this.defaultAccess());
	}
	
	protected Boolean metadataComplete(AbstractTypeMapping typeMapping) {
		return typeMapping.getMetadataComplete();
	}


	
	// *************************************************************************
	
	public JpaStructureNode structureNode(int offset) {
		if (this.typeMapping.containsOffset(offset)) {
			return persistentType();
		}
		return null;
	}
	
	public TextRange selectionTextRange() {
		return this.typeMapping.selectionTextRange();
	}
	
	public TextRange classTextRange() {
		return this.typeMapping.classTextRange();
	}
	
	public TextRange attributesTextRange() {
		return this.typeMapping.attributesTextRange();
	}

	public boolean containsOffset(int textOffset) {
		if (typeMapping == null) {
			return false;
		}
		return typeMapping.containsOffset(textOffset);
	}
	
	//************************* validation ************************
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		addClassMessages(messages);
	}
	protected void addClassMessages(List<IMessage> messages) {
		addUnspecifiedClassMessage(messages);
		addUnresolvedClassMessage(messages);
	}
	
	protected void addUnspecifiedClassMessage(List<IMessage> messages) {
		if (StringTools.stringIsEmpty(getClass_())) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNSPECIFIED_CLASS,
					this, 
					this.classTextRange())
			);
		}
	}
	
	protected void addUnresolvedClassMessage(List<IMessage> messages) {
		if (! StringTools.stringIsEmpty(getClass_())
				&& getJavaPersistentType() == null) {
			messages.add(
				DefaultJpaValidationMessages.buildMessage(
					IMessage.HIGH_SEVERITY,
					JpaValidationMessages.PERSISTENT_TYPE_UNRESOLVED_CLASS,
					new String[] {getClass_()},
					this, 
					this.classTextRange())
			);
		}
	}

	public TextRange validationTextRange() {
		return this.typeMapping.validationTextRange();
	}
}
