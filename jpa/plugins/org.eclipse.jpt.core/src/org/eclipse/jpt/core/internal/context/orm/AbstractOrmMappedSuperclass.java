/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.core.context.java.JavaIdClassReference;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmIdClassReference;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.PrimaryKeyTextRangeResolver;
import org.eclipse.jpt.core.internal.context.PrimaryKeyValidator;
import org.eclipse.jpt.core.internal.jpa1.context.GenericMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.text.edits.ReplaceEdit;
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
		return new GenericOrmIdClassReference(this, getJavaIdClassReferenceForDefaults());
	}
	
	public int getXmlSequence() {
		return 0;
	}
	
	public String getKey() {
		return MappingKeys.MAPPED_SUPERCLASS_TYPE_MAPPING_KEY;
	}
	
	@Override
	public JavaPersistentType getIdClass() {
		return this.idClassReference.getIdClass();
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
	
	protected JavaIdClassReference getJavaIdClassReferenceForDefaults() {
		JavaMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getIdClassReference();
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
		this.idClassReference.update(getJavaIdClassReferenceForDefaults());
	}


	//************************* refactoring ************************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createReplaceTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
					super.createReplaceTypeEdits(originalType, newName),
					this.createIdClassReplaceTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createIdClassReplaceTypeEdits(IType originalType, String newName) {
		return this.idClassReference.createReplaceEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createReplacePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createReplacePackageEdits(originalPackage, newName),
			this.createIdClassReplacePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createIdClassReplacePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.idClassReference.createReplacePackageEdits(originalPackage, newName);
	}


	// **************** validation *********************************************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		validatePrimaryKey(messages, reporter);
	}
	
	protected void validatePrimaryKey(List<IMessage> messages, IReporter reporter) {
		buildPrimaryKeyValidator().validate(messages, reporter);
	}
	
	protected PrimaryKeyValidator buildPrimaryKeyValidator() {
		return new GenericMappedSuperclassPrimaryKeyValidator(this, buildTextRangeResolver());
		// TODO - JPA 2.0 validation
	}
	
	protected PrimaryKeyTextRangeResolver buildTextRangeResolver() {
		return new OrmMappedSuperclassTextRangeResolver(this);
	}
}
