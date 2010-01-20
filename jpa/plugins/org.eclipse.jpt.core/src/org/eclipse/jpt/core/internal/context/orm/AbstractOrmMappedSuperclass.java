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
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmMappedSuperclass extends AbstractOrmTypeMapping<XmlMappedSuperclass>
	implements OrmMappedSuperclass
{
	protected final OrmIdClassReference idClassReference;
	
	
	protected AbstractOrmMappedSuperclass(OrmPersistentType parent, XmlMappedSuperclass resourceMapping) {
		super(parent, resourceMapping);
		this.idClassReference = buildIdClassReference();
	}
	
	
	protected OrmIdClassReference buildIdClassReference() {
		return new GenericOrmIdClassReference(this);
	}
	
	public int getXmlSequence() {
		return 0;
	}
	
	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	
	// **************** id class **********************************************
	
	public OrmIdClassReference getIdClassReference() {
		return this.idClassReference;
	}
	
	
	// ************************************************************************
	
	public JavaMappedSuperclass getJavaMappedSuperclass() {
		JavaPersistentType javaPersistentType = this.getJavaPersistentType();
		if (javaPersistentType != null && javaPersistentType.getMappingKey() == MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY) {
			return (JavaMappedSuperclass) javaPersistentType.getMapping();
		}
		return null;
	}

	/**
	 * This checks metaDataComplete before returning the JavaMappedSuperclass.
	 * As far as defaults are concerned, if metadataComplete is true, the JavaMappedSuperclass is ignored.
	 */
	protected JavaMappedSuperclass getJavaMappedSuperclassForDefaults() {
		if (isMetadataComplete()) {
			return null;
		}
		return getJavaMappedSuperclass();
	}
	
	@Override
	public boolean specifiesPrimaryKey() {
		return this.idClassReference.getIdClassName() != null
				|| hasPrimaryKeyAttribute();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
	
	public Iterator<String> associatedTableNamesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTables() {
		return EmptyIterator.instance();
	}

	public Iterator<Table> associatedTablesIncludingInherited() {
		return EmptyIterator.instance();
	}

	public void addToResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().add(this.resourceTypeMapping);
	}
	
	public void removeFromResourceModel(XmlEntityMappings entityMappings) {
		entityMappings.getMappedSuperclasses().remove(this.resourceTypeMapping);
	}
	
	@Override
	public void update() {
		super.update();
		this.idClassReference.update();
	}
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validatePrimaryKey(messages, reporter);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		// for JPA portability, a hierarchy must define its primary key on one class 
		// (entity *or* mapped superclass)
		if (primaryKeyIsDefinedOnAncestor()) {
			if (this.idClassReference.getIdClassName() != null) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_CLASS,
							new String[0],
							this,
							this.idClassReference.getValidationTextRange()));
			}
			for (OrmPersistentAttribute each : getPrimaryKeyAttributes()) {
				messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.TYPE_MAPPING_PK_REDEFINED_ID_ATTRIBUTE,
							new String[0],
							each,
							each.getMapping().getValidationTextRange()));
			}
			return;
		}
		
		if (this.idClassReference.getIdClass() != null) {
			validateIdClass(messages, reporter);
		}
	}
	
	// split out to allow different implementations to override
	// assumes id class is not null
	protected void validateIdClass(List<IMessage> messages, IReporter reporter) {		
		JavaPersistentType idClass = this.idClassReference.getIdClass();
		for (JavaPersistentAttribute idClassAttribute : 
				new SubIterableWrapper<PersistentAttribute, JavaPersistentAttribute>(
					CollectionTools.iterable(idClass.allAttributes()))) {
			boolean foundMatch = false;
			for (OrmPersistentAttribute persistentAttribute : 
					CollectionTools.iterable(getPersistentType().attributes())) {
				if (idClassAttribute.getName().equals(persistentAttribute.getName())) {
					foundMatch = true;
					
					// the matching attribute should be a primary key
					if (! persistentAttribute.isPrimaryKeyAttribute()) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NOT_PRIMARY_KEY,
								new String[0],
								persistentAttribute,
								persistentAttribute.getValidationTextRange()));
					}
					
					// the matching attribute's type should agree
					String persistentAttributeTypeName = persistentAttribute.getTypeName();
					if (persistentAttributeTypeName != null 	// if it's null, there should be 
																// another failing validation elsewhere
							&& ! idClassAttribute.getTypeName().equals(persistentAttributeTypeName)) {
						messages.add(DefaultJpaValidationMessages.buildMessage(
								IMessage.HIGH_SEVERITY,
								JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_TYPE_DOES_NOT_AGREE,
								new String[0],
								persistentAttribute,
								persistentAttribute.getValidationTextRange()));
					}
				}
			}
			
			if (! foundMatch) {
				messages.add(DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.TYPE_MAPPING_ID_CLASS_ATTRIBUTE_NO_MATCH,
						new String[] {idClassAttribute.getName()},
						this,
						this.idClassReference.getValidationTextRange()));
			}
		}
	}	
}
